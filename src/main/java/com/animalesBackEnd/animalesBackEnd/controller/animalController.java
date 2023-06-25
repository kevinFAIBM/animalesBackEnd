package com.animalesBackEnd.animalesBackEnd.controller;

import com.animalesBackEnd.animalesBackEnd.model.Animal;
import com.animalesBackEnd.animalesBackEnd.resource.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class animalController {

    @GetMapping("/Animal")
    public ResponseEntity<List<Animal>> getAnimals(@RequestParam String animalName){
        Util.getInstance();
        return new ResponseEntity<>(Util.getInstance().getByName(animalName), HttpStatus.OK);
    }

    @GetMapping("/Animals")
    public ResponseEntity<List<Animal>> getAnimal(@RequestParam int animalId){
        Util.getInstance();
        return new ResponseEntity<>(Util.getInstance().getValue(String.valueOf(animalId)), HttpStatus.OK);
    }

    @PutMapping("/Animals/save/{animalId}")
    public ResponseEntity<String> animalSave(@PathVariable int animalId, @RequestBody Animal animal){
    List<Animal> animals = Util.getInstance().getValue(String.valueOf(animalId));
    if (animals == null){
        animals = new ArrayList<Animal>();
    }
    animals.add(new Animal(animal.getAnimalId(),animal.getAnimalName(),animal.getType(),LocalDate.now(),animal.getBreed(),animal.getOwnerName(),animal.getVetId()));
    Util.getInstance().add(String.valueOf(animalId),animals);
    return new ResponseEntity<>("Seccess",HttpStatus.OK);
    }


}
