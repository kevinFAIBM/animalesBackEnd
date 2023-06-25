package com.animalesBackEnd.animalesBackEnd.resource;

import com.animalesBackEnd.animalesBackEnd.model.Animal;

import java.util.HashMap;
import java.util.List;

public class Util {
    private static final HashMap<String, List<Animal>> map = new HashMap<>();
    private static Util instance;
    public static synchronized Util getInstance(){
        if (instance == null){
            instance =  new Util();
        }
        return instance;
    }

    public List<Animal> getValue(String key){
        return map.get(key);
    }

    public List<Animal> getByName(String key){
        for (List<Animal> listaAnimales : map.values()) {
            for (Animal animal : listaAnimales) {
                if (animal.getAnimalName().equals(key)){
                    return getValue(String.valueOf(animal.getAnimalId()));
                }
            }
        }
        return null;  // Si no se encuentra el animal, se retorna null
    }

    public void add(String key,List<Animal> animal){
        map.put(key, animal);
    }
}
