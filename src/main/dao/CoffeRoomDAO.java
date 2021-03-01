package main.dao;

import main.config.DatabaseConnection;
import main.model.CoffeRoom;
import main.model.ParticipantInfo;
import main.model.ParticipantsGroupByCoffeeRooms;
import main.model.TrainningRoom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoffeRoomDAO {

    private final Connection connection;

    public CoffeRoomDAO() throws SQLException {
        this.connection = DatabaseConnection.connect();
    }

    public int save(CoffeRoom coffeRoom) throws SQLException{
        final String sql = "INSERT INTO coffeeroom(id, name, capacity) VALUES (?,?,?)";
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, coffeRoom.getId());
        statement.setString(2, coffeRoom.getName());
        statement.setInt(3, coffeRoom.getCapacity());
        final int result = statement.executeUpdate();
        statement.close();
        return result;
    }

    public List<CoffeRoom> findAll() throws SQLException {
        final String sql = "SELECT * FROM coffeeroom";
        PreparedStatement statement = this.connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet result = statement.executeQuery();
        List<CoffeRoom> rooms = new ArrayList<>();
        while (result.next()) {
            CoffeRoom coffeRoom = new CoffeRoom();
            coffeRoom.setId(result.getInt("id"));
            coffeRoom.setName(result.getString("name"));
            coffeRoom.setCapacity(result.getInt("capacity"));
            rooms.add(coffeRoom);
        }
        result.close();
        statement.close();
        return rooms;
    }

    public CoffeRoom findById(int id) throws SQLException {
        final String sql = "SELECT * FROM coffeeroom WHERE id=?";
        PreparedStatement statement = this.connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        CoffeRoom coffeRoom = null;
        if (result.first()) {
            coffeRoom = new CoffeRoom();
            coffeRoom.setId(result.getInt("id"));
            coffeRoom.setName(result.getString("name"));
            coffeRoom.setCapacity(result.getInt("capacity"));
        }
        result.close();
        statement.close();
        return coffeRoom;
    }

    public List<ParticipantInfo> findByIdWithParticipants(int id) throws SQLException {
        final String sql = "SELECT" +
                " P.id," +
                " P.name," +
                " P.surname," +
                " C.name as coffeeroom_name," +
                " TP.stage" +
                " FROM coffeeroom C" +
                " JOIN participant P" +
                " ON (C.id = P.coffeeroom_id)" +
                " JOIN trainningroom_participant TP" +
                " ON (P.id = TP.participant_id)" +
                " WHERE C.id = ?" +
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
            participant.setCoffeeRoomName(result.getString("coffeeroom_name"));
            participant.setStage(result.getInt("stage"));
            participantInfo.add(participant);
        }
        result.close();
        statement.close();
        return participantInfo;
    }

    public List<ParticipantsGroupByCoffeeRooms> findParticipantsGroupByCoffeeRoom() throws SQLException {
        final String sql = "SELECT C.id as coffeeroom_id," +
                " count(P.id) as participants," +
                " C.capacity" +
                " FROM coffeeroom C" +
                " LEFT JOIN participant P" +
                " ON (P.coffeeroom_id = C.id)" +
                " GROUP BY C.id";
        PreparedStatement statement = this.connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet result = statement.executeQuery();
        List<ParticipantsGroupByCoffeeRooms> resultList = new ArrayList<>();
        while (result.next()) {
            ParticipantsGroupByCoffeeRooms participantsGroupByCoffeeRooms = new ParticipantsGroupByCoffeeRooms();
            participantsGroupByCoffeeRooms.setCoffeeRoomId(result.getInt("coffeeroom_id"));
            participantsGroupByCoffeeRooms.setParticipants(result.getInt("participants"));
            participantsGroupByCoffeeRooms.setCapacity(result.getInt("capacity"));
            resultList.add(participantsGroupByCoffeeRooms);
        }
        result.close();
        statement.close();
        return resultList;
    }
}
