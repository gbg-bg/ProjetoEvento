package main.dao;

import main.config.DatabaseConnection;
import main.model.CoffeRoom;
import main.model.ParticipantInfo;
import main.model.TrainningRoom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainningRoomDAO {

    private final Connection connection;

    public TrainningRoomDAO() throws SQLException {
        this.connection = DatabaseConnection.connect();
    }

    public int save(TrainningRoom trainningRoom) throws SQLException{
        final String sql = "INSERT INTO trainningroom(id, name, capacity) VALUES (?,?,?)";
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, trainningRoom.getId());
        statement.setString(2, trainningRoom.getName());
        statement.setInt(3, trainningRoom.getCapacity());
        final int result = statement.executeUpdate();
        statement.close();
        return result;
    }

    public TrainningRoom findById(int id) throws SQLException {
        final String sql = "SELECT * FROM trainningroom WHERE id=?";
        PreparedStatement statement = this.connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        TrainningRoom trainningRoom = null;
        if (result.first()) {
            trainningRoom = new TrainningRoom();
            trainningRoom.setId(result.getInt("id"));
            trainningRoom.setName(result.getString("name"));
            trainningRoom.setCapacity(result.getInt("capacity"));
        }
        result.close();
        statement.close();
        return trainningRoom;
    }

    public List<ParticipantInfo> findByIdWithParticipants(int id) throws SQLException {
        final String sql = "SELECT" +
                " P.id," +
                " P.name," +
                " P.surname," +
                " T.name as trainningroom_name," +
                " TP.stage" +
                " FROM participant P" +
                " JOIN trainningroom_participant TP" +
                " ON (P.id = TP.participant_id)" +
                " JOIN trainningroom T" +
                " ON (T.id = TP.trainningroom_id)" +
                " WHERE T.id = ?" +
                " ORDER BY TP.stage ASC";
        PreparedStatement statement = this.connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        List<ParticipantInfo> participantInfo = new ArrayList<>();
        while (result.next()) {
            ParticipantInfo participant = new ParticipantInfo();
            participant.setId(result.getInt("id"));
            participant.setName(result.getString("name"));
            participant.setSurname(result.getString("surname"));
            participant.setTrainningRoomName(result.getString("trainningroom_name"));
            participant.setStage(result.getInt("stage"));
            participantInfo.add(participant);
        }
        result.close();
        statement.close();
        return participantInfo;
    }

    public List<TrainningRoom> findAll() throws SQLException {
        final String sql = "SELECT * FROM trainningroom";
        PreparedStatement statement = this.connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet result = statement.executeQuery();
        List<TrainningRoom> rooms = new ArrayList<>();
        while (result.next()) {
            TrainningRoom trainningRoom = new TrainningRoom();
            trainningRoom.setId(result.getInt("id"));
            trainningRoom.setName(result.getString("name"));
            trainningRoom.setCapacity(result.getInt("capacity"));
            rooms.add(trainningRoom);
        }
        result.close();
        statement.close();
        return rooms;
    }

}
