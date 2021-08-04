package com.example.demoEL.beans;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
public class BeanForArray {

    public List<Names> getNames(){
     return    Arrays.asList(new Names("Alfonso", "Perez"), new Names("Pedro", "Chavez"));
    }

    public List<String> getValuObject(){
        return    Arrays.asList("Red", "Blue");
    }

    public List<Names> getNames(String secondName){
        return    Arrays.asList(new Names("Alfonso", secondName), new Names("Pedro", secondName));
    }
}
