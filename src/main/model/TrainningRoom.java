package main.model;

public class TrainningRoom {

    private Integer id;

    private String name;

    private int capacity;

    public TrainningRoom() {
    }

    public TrainningRoom(Integer id) {
        this.id = id;
    }

    public TrainningRoom(Integer id, String name, int capacity) {
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
