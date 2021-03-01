package test;

import main.config.DatabaseConnection;
import main.model.Participant;
import main.model.ParticipantInfo;
import main.model.TrainningRoomParticipant;
import main.service.ParticipantService;
import main.service.TrainningRoomParticipantService;
import main.exception.NoRoomAvailableException;
import main.exception.RoomsNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParticipantServiceTest {

    private final ParticipantService participantService;

    private final TrainningRoomParticipantService trainningRoomParticipantService;

    public ParticipantServiceTest() throws SQLException {
        this.participantService = new ParticipantService();
        this.trainningRoomParticipantService = new TrainningRoomParticipantService();
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

    private void mockParticipantWithTrainningRoom() throws SQLException {
        final Connection connection = DatabaseConnection.connect();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("INSERT INTO coffeeroom(id, name, capacity) VALUES (1,'Coffee Room 1',10)");
        statement.addBatch("INSERT INTO trainningroom(id, name, capacity) VALUES (1,'Room 1',10)");
        statement.addBatch("INSERT INTO trainningroom(id, name, capacity) VALUES (2,'Room 2',10)");
        statement.addBatch("INSERT INTO participant(id, name, surname, coffeeroom_id) VALUES (1,'Participant 1','Test',1)");
        statement.addBatch("INSERT INTO trainningroom_participant(participant_id, trainningroom_id, stage) VALUES (1,1,1)");
        statement.addBatch("INSERT INTO trainningroom_participant(participant_id, trainningroom_id, stage) VALUES (1,2,2)");
        statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        statement.close();
    }

    private void mockParticipantWithoutRoomCapacity() throws SQLException {
        final Connection connection = DatabaseConnection.connect();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("INSERT INTO coffeeroom(id, name, capacity) VALUES (1,'Coffee Room 1',1)");
        statement.addBatch("INSERT INTO trainningroom(id, name, capacity) VALUES (1,'Room 1',1)");
        statement.addBatch("INSERT INTO participant(id, name, surname, coffeeroom_id) VALUES (1,'Participant 1','Test',1)");
        statement.addBatch("INSERT INTO trainningroom_participant(participant_id, trainningroom_id, stage) VALUES (1,1,1)");
        statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        statement.close();
    }

    private void mockParticipantWithoutRooms() throws SQLException {
        final Connection connection = DatabaseConnection.connect();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("INSERT INTO coffeeroom(id, name, capacity) VALUES (1,'Coffee Room 1',1)");
        statement.addBatch("INSERT INTO participant(id, name, surname, coffeeroom_id) VALUES (1,'Participant 1','Test',1)");
        statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        statement.close();
    }

    private void mockParticipantRoomsList() throws SQLException {
        final Connection connection = DatabaseConnection.connect();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("INSERT INTO coffeeroom(id, name, capacity) VALUES (1,'Coffee Room 1',1)");
        statement.addBatch("INSERT INTO trainningroom(id, name, capacity) VALUES (1,'Room 1',10)");
        statement.addBatch("INSERT INTO trainningroom(id, name, capacity) VALUES (2,'Room 2',10)");
        statement.addBatch("INSERT INTO participant(id, name, surname, coffeeroom_id) VALUES (1,'Participant 1','Test',1)");
        statement.addBatch("INSERT INTO participant(id, name, surname, coffeeroom_id) VALUES (2,'Participant 2','Test',1)");
        statement.addBatch("INSERT INTO participant(id, name, surname, coffeeroom_id) VALUES (3,'Participant 3','Test',1)");
        statement.addBatch("INSERT INTO participant(id, name, surname, coffeeroom_id) VALUES (4,'Participant 4','Test',1)");
        statement.addBatch("INSERT INTO participant(id, name, surname, coffeeroom_id) VALUES (5,'Participant 5','Test',1)");
        statement.addBatch("INSERT INTO participant(id, name, surname, coffeeroom_id) VALUES (6,'Participant 6','Test',1)");
        statement.addBatch("INSERT INTO trainningroom_participant(participant_id, trainningroom_id, stage) VALUES (1,1,1)");
        statement.addBatch("INSERT INTO trainningroom_participant(participant_id, trainningroom_id, stage) VALUES (2,2,1)");
        statement.addBatch("INSERT INTO trainningroom_participant(participant_id, trainningroom_id, stage) VALUES (3,1,1)");
        statement.addBatch("INSERT INTO trainningroom_participant(participant_id, trainningroom_id, stage) VALUES (4,2,1)");
        statement.addBatch("INSERT INTO trainningroom_participant(participant_id, trainningroom_id, stage) VALUES (5,1,1)");
        statement.addBatch("INSERT INTO trainningroom_participant(participant_id, trainningroom_id, stage) VALUES (6,2,1)");
        statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        statement.close();
    }

    @Test
    void shouldSaveParticipant() throws SQLException {
        mockParticipantWithTrainningRoom();
        Participant participant = new Participant(999, "Participant x", "Test");
        int result = participantService.save(participant);
        assertTrue(result > 0);
    }

    @Test
    void shouldThrowNoRoomAvailableException() throws SQLException {
        mockParticipantWithoutRoomCapacity();
        Participant participant = new Participant(999, "Participant x", "Test");
        assertThrows(NoRoomAvailableException.class,
                () -> participantService.save(participant));
    }

    @Test
    void shouldThrowRoomsNotFoundException() throws SQLException {
        mockParticipantWithoutRooms();
        Participant participant = new Participant(999, "Participant x", "Test");
        assertThrows(RoomsNotFoundException.class,
                () -> participantService.save(participant));
    }

    @Test
    void shouldReturnParticipant() throws SQLException {
        mockParticipantWithTrainningRoom();
        List<ParticipantInfo> participant = participantService.findByRooms(1);
        assertNotNull(participant);
        assertFalse(participant.isEmpty());
        assertEquals(1, participant.get(0).getId());
        assertEquals(1, participant.get(1).getId());
        assertEquals("Participant 1", participant.get(0).getName());
        assertEquals("Participant 1", participant.get(1).getName());
        assertEquals("Test", participant.get(0).getSurname());
        assertEquals("Test", participant.get(1).getSurname());
        assertEquals("Coffee Room 1", participant.get(0).getCoffeeRoomName());
        assertEquals("Coffee Room 1", participant.get(1).getCoffeeRoomName());
        assertEquals("Room 1", participant.get(0).getTrainningRoomName());
        assertEquals("Room 2", participant.get(1).getTrainningRoomName());
        assertEquals(1, participant.get(0).getStage());
        assertEquals(2, participant.get(1).getStage());
    }

    @Test
    void shouldSwitchParticipantStageRooms() throws SQLException {
        mockParticipantRoomsList();
        participantService.switchTrainningRoom();
        List<TrainningRoomParticipant> participantRoomList = trainningRoomParticipantService.findAll();
        assertFalse(participantRoomList.isEmpty());
        assertEquals(6, participantRoomList.size());
    }
}
