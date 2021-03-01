package main.model;

public class ParticipantsGroupByRooms {

    private Integer trainningroomId;

    private Integer capacity;

    private Integer participants;

    public ParticipantsGroupByRooms() {
    }

    public ParticipantsGroupByRooms(Integer trainningroomId, Integer participants) {
        this.trainningroomId = trainningroomId;
        this.participants = participants;
    }

    public ParticipantsGroupByRooms(Integer trainningroomId, Integer capacity, Integer participants) {
        this.trainningroomId = trainningroomId;
        this.capacity = capacity;
        this.participants = participants;
    }

    public Integer getTrainningroomId() {
        return trainningroomId;
    }

    public void setTrainningroomId(Integer trainningroomId) {
        this.trainningroomId = trainningroomId;
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
