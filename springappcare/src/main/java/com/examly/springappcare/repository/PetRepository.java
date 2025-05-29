package com.examly.springappcare.repository;
import com.examly.springappcare.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PetRepository extends JpaRepository<Pet, Integer> {}