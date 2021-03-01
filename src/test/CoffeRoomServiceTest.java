package test;

import main.config.DatabaseConnection;
import main.model.CoffeRoom;
import main.model.ParticipantInfo;
import main.service.CoffeRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoffeRoomServiceTest {

    private final CoffeRoomService coffeRoomService;

    public CoffeRoomServiceTest() throws SQLException {
        this.coffeRoomService = new CoffeRoomService();
    }

    @BeforeEach
    void clearTables() throws SQLException {
        final Connection connection = DatabaseConnection.connect();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("DELETE FROM trainningroom_participant");
        statement.addBatch("DELETE FROM participant");
        statement.addBatch("DELETE FROM coffeeroom");
        statement.addBatch("DELETE FROM trainningroom");
        statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        statement.close();
    }

    private void mockParticipantWithCoffeeRoom() throws SQLException {
        final Connection connection = DatabaseConnection.connect();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("INSERT INTO coffeeroom(id, name, capacity) VALUES (1,'Coffee Room 1',10)");
        statement.addBatch("INSERT INTO trainningroom(id, name, capacity) VALUES (1,'Room 1',10)");
        statement.addBatch("INSERT INTO trainningroom(id, name, capacity) VALUES (2,'Room 2',10)");
        statement.addBatch("INSERT INTO participant(id, name, surname, coffeeroom_id) VALUES (1,'Participant 1','Test',1)");
        statement.addBatch("INSERT INTO participant(id, name, surname, coffeeroom_id) VALUES (2,'Participant 2','Test',1)");
        statement.addBatch("INSERT INTO trainningroom_participant(participant_id, trainningroom_id, stage) VALUES (1,1,1)");
        statement.addBatch("INSERT INTO trainningroom_participant(participant_id, trainningroom_id, stage) VALUES (2,2,2)");
        statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        statement.close();
    }

    @Test
    void shouldSaveCoffeRoom() throws SQLException {
        CoffeRoom coffeRoom = new CoffeRoom(1, "Room 1", 5);
        int result = coffeRoomService.save(coffeRoom);
        assertTrue(result > 0);
    }

    @Test
    void shouldReturnCoffeeRoom() throws SQLException {
        mockParticipantWithCoffeeRoom();
        List<ParticipantInfo> coffeRoomParticipants = coffeRoomService.findWithParticipants(1);
        assertNotNull(coffeRoomParticipants);
        assertFalse(coffeRoomParticipants.isEmpty());
        assertEquals(1, coffeRoomParticipants.get(0).getId());
        assertEquals(2, coffeRoomParticipants.get(1).getId());
        assertEquals("Coffee Room 1", coffeRoomParticipants.get(0).getCoffeeRoomName());
        assertEquals("Coffee Room 1", coffeRoomParticipants.get(1).getCoffeeRoomName());
    }
}
