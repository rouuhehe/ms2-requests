import uuid
import random
import mysql.connector
import requests
from faker import Faker
from datetime import datetime, timedelta

fake = Faker("es_ES")

conn = mysql.connector.connect(
    host="ms2-requests",
    user="mysql",
    password="mysql",
    database="requests"
)
cursor = conn.cursor()

NUM_USERS = 20000
NUM_REQUESTS = 50000

print("Fetching pets from ms1...")
MS1_URL = "http://ms1-pets:8000/mascotas"
page = 1
limit = 1000
pet_ids = []

while True:
    resp = requests.get(MS1_URL, params={"page": page, "limit": limit})
    if resp.status_code != 200:
        raise RuntimeError(f"Failed to fetch pets from MS1: {resp.status_code}")

    pets = resp.json()
    if not pets:
        break  # stop when no more data

    for p in pets:
        pet_ids.append(uuid.UUID(p["id"]))

    print(f"  ➡️ Fetched page {page}, total pets so far: {len(pet_ids)}")
    page += 1

if not pet_ids:
    raise RuntimeError(" No pets found in MS1")

print(f"Retrieved {len(pet_ids)} pets from MS1.")


print("Inserting users...")
users = []
for _ in range(NUM_USERS):
    user_id = uuid.uuid4()
    name = fake.name()
    age = random.randint(18, 70)
    phone = fake.phone_number()
    city = fake.city()
    email = fake.unique.email()
    password = fake.password()

    users.append((user_id.bytes, name, age, phone, city, email, password))

cursor.executemany("""
                   INSERT INTO users (id, name, age, phone, city, email, password)
                   VALUES (%s, %s, %s, %s, %s, %s, %s)
                   """, users)
conn.commit()
print(f"{NUM_USERS} users inserted.")

# --- Fetch user IDs for requests ---
cursor.execute("SELECT id FROM users")
user_ids = [row[0] for row in cursor.fetchall()]

# --- Insert requests ---
print("Inserting requests...")
statuses = ["pending", "approved", "rejected"]
requests = []

for _ in range(NUM_REQUESTS):
    request_id = uuid.uuid4()
    user_id = random.choice(user_ids)
    pet_id = random.choice(pet_ids)
    request_date = datetime.now() - timedelta(days=random.randint(0, 30))
    status = random.choice(statuses)
    status_date = datetime.now()
    message = fake.sentence()

    requests.append((request_id.bytes, user_id, pet_id.bytes, request_date, status, status_date, message))

cursor.executemany("""
                   INSERT INTO requests (id, user_id, pet_id, requestDate, status, statusDate, message)
                   VALUES (%s, %s, %s, %s, %s, %s, %s)
                   """, requests)
conn.commit()
print(f"{NUM_REQUESTS} requests inserted.")

cursor.close()
conn.close()
print(" Seeding complete.")
