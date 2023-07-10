package com.animalesBackEnd.animalesBackEnd.resource;

import com.animalesBackEnd.animalesBackEnd.model.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    private static final HashMap<String, List<Animal>> map = new HashMap<>();
    private static Util instance;

    public static synchronized Util getInstance(){
        if (instance == null){
            instance =  new Util();
        }
        return instance;
    }

    public Animal getValuebyName(String animalId){
        Animal animal = map.get(animalId).get(0);

        return animal;
    }

    public List<Animal> getValue(int key){
        return map.get(key);
    }

    public Animal getByName(String key){
        for (List<Animal> listaAnimales : map.values()) {
            for (Animal animal : listaAnimales) {
                if (animal.getAnimalName().equals(key)){
                    return getValuebyName(String.valueOf(animal.getAnimalId()));
                }
            }
        }
        return null;  // Si no se encuentra el animal, se retorna null
    }

    public void add(String key,List<Animal> animal){
        map.put(key, animal);
    }
}
