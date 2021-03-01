package main.model;

public class CoffeRoom {

    private Integer id;

    private String name;

    private int capacity;

    public CoffeRoom() {
    }

    public CoffeRoom(Integer id) {
        this.id = id;
    }

    public CoffeRoom(Integer id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
