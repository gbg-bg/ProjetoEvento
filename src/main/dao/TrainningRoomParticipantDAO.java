package main.dao;

import main.config.DatabaseConnection;
import main.model.ParticipantsGroupByRooms;
import main.model.TrainningRoomParticipant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TrainningRoomParticipantDAO {

    private final Connection connection;

    public TrainningRoomParticipantDAO() throws SQLException {
        this.connection = DatabaseConnection.connect();
    }

    public int save(TrainningRoomParticipant trainningRoomParticipant) throws SQLException{
        final String sql = "INSERT INTO trainningroom_participant(" +
                "participant_id, trainningroom_id, stage) VALUES (?,?,?)";
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, trainningRoomParticipant.getParticipantId());
        statement.setInt(2, trainningRoomParticipant.getTrainningroomId());
        statement.setInt(3, trainningRoomParticipant.getStage());
        final int result = statement.executeUpdate();
        statement.close();
        return result;
    }

    public void updateTrainningRoom(TrainningRoomParticipant trainningRoomParticipant) throws SQLException{
        final String sql = "UPDATE trainningroom_participant SET trainningroom_id = ?" +
                " WHERE participant_id = ? AND stage = ?";
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, trainningRoomParticipant.getTrainningroomId());
        statement.setInt(2, trainningRoomParticipant.getParticipantId());
        statement.setInt(3, trainningRoomParticipant.getStage());
        statement.executeUpdate();
        statement.close();
    }

    public List<ParticipantsGroupByRooms> findParticipantsGroupByRooms() throws SQLException {
        final String sql = "SELECT T.id as trainningroom_id, " +
                " count(P.id) as participants," +
                " T.capacity" +
                " FROM trainningroom T LEFT JOIN trainningroom_participant TP" +
                " ON (T.id = TP.trainningroom_id)" +
                " LEFT JOIN participant P" +
                " ON (P.id = TP.participant_id)" +
                " GROUP BY T.id";
        PreparedStatement statement = this.connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet result = statement.executeQuery();
        List<ParticipantsGroupByRooms> resultList = new ArrayList<>();
        while (result.next()) {
            ParticipantsGroupByRooms participantsGroupByRooms = new ParticipantsGroupByRooms();
            participantsGroupByRooms.setTrainningroomId(result.getInt("trainningroom_id"));
            participantsGroupByRooms.setParticipants(result.getInt("participants"));
            participantsGroupByRooms.setCapacity(result.getInt("capacity"));
            resultList.add(participantsGroupByRooms);
        }
        result.close();
        statement.close();
        return resultList;
    }

    public List<TrainningRoomParticipant> findAll() throws SQLException {
        final String sql = "SELECT * FROM trainningroom_participant";
        PreparedStatement statement = this.connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet result = statement.executeQuery();
        List<TrainningRoomParticipant> rooms = new ArrayList<>();
        while (result.next()) {
            TrainningRoomParticipant trainningRoomParticipant = new TrainningRoomParticipant();
            trainningRoomParticipant.setTrainningroomId(result.getInt("trainningroom_id"));
            trainningRoomParticipant.setParticipantId(result.getInt("participant_id"));
            trainningRoomParticipant.setStage(result.getInt("stage"));
            rooms.add(trainningRoomParticipant);
        }
        result.close();
        statement.close();
        return rooms;
    }

    public List<TrainningRoomParticipant> findByStage(Integer stage) throws SQLException {
        final String sql = "SELECT * FROM trainningroom_participant WHERE stage = ?";
        PreparedStatement statement = this.connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, stage);
        ResultSet result = statement.executeQuery();
        List<TrainningRoomParticipant> rooms = new ArrayList<>();
        while (result.next()) {
            TrainningRoomParticipant trainningRoomParticipant = new TrainningRoomParticipant();
            trainningRoomParticipant.setTrainningroomId(result.getInt("trainningroom_id"));
            trainningRoomParticipant.setParticipantId(result.getInt("participant_id"));
            trainningRoomParticipant.setStage(result.getInt("stage"));
            rooms.add(trainningRoomParticipant);
        }
        result.close();
        statement.close();
        return rooms;
    }

    public void truncateTable() throws SQLException {
        Statement firstStatement = this.connection.createStatement();
        firstStatement.executeUpdate("SET FOREIGN_KEY_CHECKS=OFF;");
        firstStatement.close();

        final String sql = "DELETE FROM trainningroom_participant";
        PreparedStatement statement = this.connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.executeUpdate();
        statement.close();

        firstStatement = this.connection.createStatement();
        firstStatement.executeUpdate("SET FOREIGN_KEY_CHECKS=ON;");
        firstStatement.close();
    }

}
