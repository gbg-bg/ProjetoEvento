package main.model;

public class ParticipantInfo {

    private Integer id;

    private String name;

    private String surname;

    private String coffeeRoomName;

    private String trainningRoomName;

    private Integer stage;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCoffeeRoomName() {
        return coffeeRoomName;
    }

    public void setCoffeeRoomName(String coffeeRoomName) {
        this.coffeeRoomName = coffeeRoomName;
    }

    public String getTrainningRoomName() {
        return trainningRoomName;
    }

    public void setTrainningRoomName(String trainningRoomName) {
        this.trainningRoomName = trainningRoomName;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }
}
