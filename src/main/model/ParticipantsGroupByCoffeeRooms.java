package main.model;

public class ParticipantsGroupByCoffeeRooms {

    private Integer coffeeRoomId;

    private Integer capacity;

    private Integer participants;

    public ParticipantsGroupByCoffeeRooms() {
    }

    public ParticipantsGroupByCoffeeRooms(Integer coffeeRoomId, Integer participants) {
        this.coffeeRoomId = coffeeRoomId;
        this.participants = participants;
    }

    public Integer getCoffeeRoomId() {
        return coffeeRoomId;
    }

    public void setCoffeeRoomId(Integer coffeeRoomId) {
        this.coffeeRoomId = coffeeRoomId;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
