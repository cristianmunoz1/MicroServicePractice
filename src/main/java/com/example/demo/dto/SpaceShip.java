package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class SpaceShip {

    private String name, message;
    private int size;

    private List<Persona> occupants;

    public String toString(){
        return "Name: " + name + ", size: " + size;
    }
}
