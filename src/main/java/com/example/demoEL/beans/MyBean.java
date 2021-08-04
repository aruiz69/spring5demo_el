package com.example.demoEL.beans;

import org.springframework.stereotype.Component;

@Component("myBean")
public class MyBean {
    public String getNameBean(){
        return "MyBean";
    }

    public String getValue(String key){
        return key;
    }

    public int getDataInt(){
        return 4;
    }

    public boolean evalPredicate(Boolean aBoolean){
        return  aBoolean;
    }
}
