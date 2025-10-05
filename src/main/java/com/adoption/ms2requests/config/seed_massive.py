import uuid
import random
import csv
from faker import Faker
from datetime import datetime, timedelta

fake = Faker("es_ES")

NUM_USERS = 20000
NUM_REQUESTS = 3000

# --- CSV FILES ---
users_file = "users.csv"
requests_file = "requests.csv"
pets_file = "pets.csv"

# --- Leer pet IDs desde pets.csv ---
pet_ids = []
with open(pets_file, newline="", encoding="utf-8") as f:
    reader = csv.DictReader(f)
    for row in reader:
        pet_ids.append(uuid.UUID(row["id"]))

print(f"Retrieved {len(pet_ids)} pet IDs from {pets_file}.")

# --- Insert users ---
users = []
with open(users_file, "w", newline="", encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(["id", "name", "age", "phone", "city", "email", "password"])
    for _ in range(NUM_USERS):
        user_id = uuid.uuid4()
        name = fake.name()
        age = random.randint(18, 70)
        phone = fake.phone_number()
        city = fake.city()
        email = fake.unique.email()
        password = fake.password()

        users.append(user_id)
        writer.writerow([user_id, name, age, phone, city, email, password])

print(f"{NUM_USERS} users saved to {users_file}.")

# --- Insert requests ---
statuses = ["pending", "approved", "rejected"]

with open(requests_file, "w", newline="", encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(["id", "user_id", "pet_id", "requestDate", "status", "statusDate", "message"])

    for _ in range(NUM_REQUESTS):
        request_id = uuid.uuid4()
        user_id = random.choice(users)
        pet_id = random.choice(pet_ids)  # ahora coincide con IDs reales
        request_date = datetime.now() - timedelta(days=random.randint(0, 30))
        status = random.choice(statuses)
        status_date = datetime.now()
        message = fake.sentence()

        writer.writerow([request_id, user_id, pet_id, request_date, status, status_date, message])

print(f"{NUM_REQUESTS} requests saved to {requests_file}.")
