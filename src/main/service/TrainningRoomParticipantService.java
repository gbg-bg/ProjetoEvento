package main.service;

import main.dao.TrainningRoomParticipantDAO;
import main.model.ParticipantsGroupByRooms;
import main.model.TrainningRoomParticipant;
import main.exception.NoRoomAvailableException;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static main.util.MessageUtil.NO_ROOMS_AVAILABLE;

public class TrainningRoomParticipantService {

    private final TrainningRoomParticipantDAO trainningRoomParticipantDAO;

    public TrainningRoomParticipantService() throws SQLException {
        trainningRoomParticipantDAO = new TrainningRoomParticipantDAO();
    }

    public int save(TrainningRoomParticipant trainningRoomParticipant) throws SQLException {
        return trainningRoomParticipantDAO.save(trainningRoomParticipant);
    }

    public void updateTrainningRoom(TrainningRoomParticipant trainningRoomParticipant) throws SQLException {
        trainningRoomParticipantDAO.updateTrainningRoom(trainningRoomParticipant);
    }

    public List<ParticipantsGroupByRooms> findParticipantGroupByRooms() throws SQLException {
        return trainningRoomParticipantDAO.findParticipantsGroupByRooms();
    }

    public Integer returnNextRoomToParticipant(List<ParticipantsGroupByRooms> participantsGroupByRooms) {
        Optional<ParticipantsGroupByRooms> result = Optional.of(participantsGroupByRooms.stream()
                .filter(participantRoom -> participantRoom.getCapacity() > participantRoom.getParticipants())
                .min(Comparator.comparing(ParticipantsGroupByRooms::getParticipants))
                .orElseThrow(() -> new NoRoomAvailableException(NO_ROOMS_AVAILABLE)));

        return result.get().getTrainningroomId();
    }

    public List<TrainningRoomParticipant> findAll() throws SQLException {
        return trainningRoomParticipantDAO.findAll();
    }

    public List<TrainningRoomParticipant> findByStage(Integer stageId) throws SQLException {
        return trainningRoomParticipantDAO.findByStage(stageId);
    }

    public void truncate() throws SQLException {
        trainningRoomParticipantDAO.truncateTable();
    }
}
