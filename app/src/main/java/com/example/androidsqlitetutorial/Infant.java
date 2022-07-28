package com.example.androidsqlitetutorial;

public class Infant {
    private String name;
    private String weight;
    private int id;

    public Infant (int newId, String newName, String newWeight){
        id = newId;
        name = newName;
        weight = newWeight;
    }

    public void setId(int newId){
        this.id = newId;
    }

    public int getId(){
        return id;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public String getName(){
        return name;
    }

    public void setWeight(String newWeight){
        this.weight = newWeight;
    }

    public String getWeight(){
        return weight;
    }
}
