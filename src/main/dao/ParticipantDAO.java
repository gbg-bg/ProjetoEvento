package main.dao;

import main.config.DatabaseConnection;
import main.model.CoffeRoom;
import main.model.Participant;
import main.model.ParticipantInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParticipantDAO {

    private final Connection connection;

    public ParticipantDAO() throws SQLException {
        this.connection = DatabaseConnection.connect();
    }

    public int save(Participant participant) throws SQLException{
        final String sql = "INSERT INTO participant(" +
                "id, name, surname, coffeeroom_id) " +
                "VALUES (?,?,?,?)";
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, participant.getId());
        statement.setString(2, participant.getName());
        statement.setString(3, participant.getSurname());
        this.insertCoffeeRoomId(statement, participant);
        final int result = statement.executeUpdate();
        statement.close();
        return result;
    }

    private void insertCoffeeRoomId(PreparedStatement statement, Participant participant) throws SQLException {
        if (participant.getCoffeRoom() != null) {
            statement.setInt(4, participant.getCoffeRoom().getId());
        } else {
            statement.setNull(4, java.sql.Types.INTEGER);
        }
    }

    public Participant findById(int id) throws SQLException {
        final String sql = "SELECT * FROM participant WHERE id=?";
        PreparedStatement statement = this.connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        Participant participant = null;
        if (result.first()) {
            participant = new Participant();
            participant.setId(result.getInt("id"));
            participant.setName(result.getString("name"));
            participant.setSurname(result.getString("surname"));
        }
        result.close();
        statement.close();
        return participant;
    }

    public List<ParticipantInfo> findByRooms(int id) throws SQLException {
        final String sql = "SELECT P.id," +
                " P.name," +
                " P.surname," +
                " C.name as coffeeroom_name," +
                " T.name as trainningroom_name," +
                " TP.stage" +
                " FROM coffeeroom C" +
                " JOIN participant P" +
                " ON (C.id = P.coffeeroom_id)" +
                " JOIN trainningroom_participant TP" +
                " ON (P.id = TP.participant_id)" +
                " JOIN trainningroom T" +
                " ON (T.id = TP.trainningroom_id)" +
                " WHERE P.id = ?";
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
            participant.setCoffeeRoomName(result.getString("coffeeroom_name"));
            participant.setTrainningRoomName(result.getString("trainningroom_name"));
            participant.setStage(result.getInt("stage"));
            participantInfo.add(participant);
        }
        result.close();
        statement.close();
        return participantInfo;
    }
}
