package com.examly.springappcare.service;

import com.examly.springappcare.model.Pet;
import com.examly.springappcare.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository repository;

    public List<Pet> getAllPets() { return repository.findAll(); }

    public Pet getPetById(int id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Pet not found"));
    }

    public Pet addPet(Pet pet) { return repository.save(pet); }

    public Pet updatePet(int id, Pet pet) {
        Pet existing = getPetById(id);
        existing.setName(pet.getName());
        existing.setAge(pet.getAge());
        existing.setType(pet.getType());
        existing.setOwnerId(pet.getOwnerId());
        return repository.save(existing);
    }

    public void deletePet(int id) { repository.deleteById(id); }
}
