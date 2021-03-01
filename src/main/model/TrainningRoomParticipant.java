package main.model;

public class TrainningRoomParticipant {

    private Integer participantId;

    private Integer trainningroomId;

    private Integer stage;

    public TrainningRoomParticipant() {
    }

    public TrainningRoomParticipant(Integer participantId, Integer trainningroomId, Integer stage) {
        this.participantId = participantId;
        this.trainningroomId = trainningroomId;
        this.stage = stage;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public Integer getTrainningroomId() {
        return trainningroomId;
    }

    public void setTrainningroomId(Integer trainningroomId) {
        this.trainningroomId = trainningroomId;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }
}
