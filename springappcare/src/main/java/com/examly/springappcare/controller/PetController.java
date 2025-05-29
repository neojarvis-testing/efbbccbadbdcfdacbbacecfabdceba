package com.examly.springappcare.controller;

import com.examly.springappcare.model.Pet;
import com.examly.springappcare.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private JPetService petService;

    @GetMapping
    @PreAuthorize("hasAnyRole('CLINICMANAGER', 'VETERINARIAN', 'PETOWNER')")
    public ResponseEntity<List<Pet>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLINICMANAGER', 'VETERINARIAN', 'PETOWNER')")
    public ResponseEntity<Pet> getPetById(@PathVariable int id) {
        return ResponseEntity.ok(petService.getPetById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('PETOWNER')")
    public ResponseEntity<Pet> addPet(@RequestBody Pet pet) {
        return ResponseEntity.ok(petService.addPet(pet));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PETOWNER')")
    public ResponseEntity<Pet> updatePet(@PathVariable int id, @RequestBody Pet pet) {
        return ResponseEntity.ok(petService.updatePet(id, pet));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PETOWNER')")
    public ResponseEntity<Void> deletePet(@PathVariable int id) {
        petService.deletePet(id);
        return ResponseEntity.ok().build();
    }
}
