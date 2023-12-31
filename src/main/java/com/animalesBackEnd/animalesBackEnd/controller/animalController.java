package com.animalesBackEnd.animalesBackEnd.controller;

import com.animalesBackEnd.animalesBackEnd.model.Animal;
import com.animalesBackEnd.animalesBackEnd.resource.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




@Controller
public class animalController {

    @Autowired(required = true)
    private RestTemplate restTemplate;

    private String veteUri = "http://localhost:8082/Vete/name";

    @GetMapping("/Animal")
    public ResponseEntity<Animal> getAnimals(@RequestParam String animalName){
        Util.getInstance();
        Animal animal = Util.getInstance().getByName(animalName);
        if (animal == null){
            return new ResponseEntity<>(null,HttpStatus.OK);
        }else {
            animal = setVetName(animal);
            return new ResponseEntity<>(animal,HttpStatus.OK);
        }
    }

    /*
    http://localhost:8083/Animal?animalName=perro2
    */


    @GetMapping("/Animals")
    public ResponseEntity<Animal> getAnimal(@RequestParam int animalId){
        Util.getInstance();
        Animal animal = Util.getInstance().getValuebyName(String.valueOf(animalId));
        if (animal == null){
            return new ResponseEntity<>(null,HttpStatus.OK);
        }else {
            animal = setVetName(animal);
            return new ResponseEntity<>(animal, HttpStatus.OK);
        }
    }
    /*
    http://localhost:8083/Animals?animalId=207340818
    */

    @PutMapping("/Animals/save/{animalId}")
    public ResponseEntity<String> animalSave(@PathVariable int animalId, @RequestBody Animal animal){
    List<Animal> animals = Util.getInstance().getValue(animalId);
    if (animals == null){
        animals = new ArrayList<Animal>();
    }
    animals.add(new Animal(animal.getAnimalId(),animal.getAnimalName(),animal.getType(),LocalDate.now(),animal.getBreed(),animal.getOwnerName(),animal.getVetId()));
    Util.getInstance().add(String.valueOf(animalId),animals);
    return new ResponseEntity<>("Success",HttpStatus.OK);
    }

/*
    http://localhost:8081/Animals/save/207340817

    {
        "animalId" : "207340818",
            "animalName" : "pepe",
            "type" : "Sagua",
            "breed" : "Sagua",
            "ownerName" : "Kevin",
            "vetId" : "207340817"
    }

*/

    private Animal setVetName(Animal animal){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(veteUri)
                .queryParam("VeterinaryId", "{VeterinaryId}")
                .encode()
                .toUriString();
        Map<String, String> params = new HashMap<String, String>();
        params.put("VeterinaryId", String.valueOf(animal.getVetId()));

        HttpEntity<String> response = restTemplate.exchange(
                urlTemplate,
                HttpMethod.GET,
                entity,
                String.class,
                params
        );

        if (response.getBody().isEmpty()){
            return null;
        }
        animal.setVetName(response.getBody());
        return animal;
    }

}
