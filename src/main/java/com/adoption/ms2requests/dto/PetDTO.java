package com.adoption.ms2requests.dto;

import java.util.UUID;

public class PetDTO {
    private UUID id;
    private String name;
    private String species;
    private String breed;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
}
