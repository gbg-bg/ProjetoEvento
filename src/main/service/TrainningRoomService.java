package main.service;

import main.dao.TrainningRoomDAO;
import main.model.ParticipantInfo;
import main.model.TrainningRoom;

import java.sql.SQLException;
import java.util.List;

public class TrainningRoomService {

    private final TrainningRoomDAO trainningRoomDAO;

    public TrainningRoomService() throws SQLException {
        trainningRoomDAO = new TrainningRoomDAO();
    }

    public int save(TrainningRoom trainningRoom) throws SQLException {
        return trainningRoomDAO.save(trainningRoom);
    }

    public TrainningRoom find(int id) throws SQLException {
        return trainningRoomDAO.findById(id);
    }

    public List<ParticipantInfo> findByIdWithParticipants(int id) throws SQLException {
        return trainningRoomDAO.findByIdWithParticipants(id);
    }

    public List<TrainningRoom> findAll() throws SQLException {
        return trainningRoomDAO.findAll();
    }
}
