package com.examly.springappcare.service;

import com.examly.springappcare.model.Pet;
import java.util.List;

public interface PetService {
    List<Pet> getAllPets();
    Pet getPetById(int id);
    Pet addPet(Pet pet);
    Pet updatePet(int id, Pet pet);
    void deletePet(int id);
}
