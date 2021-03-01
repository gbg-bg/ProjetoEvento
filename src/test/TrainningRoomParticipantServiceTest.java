package test;

import main.config.DatabaseConnection;
import main.model.ParticipantsGroupByRooms;
import main.service.TrainningRoomParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainningRoomParticipantServiceTest {

    private final TrainningRoomParticipantService trainningRoomParticipantService;

    public TrainningRoomParticipantServiceTest() throws SQLException {
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

    @Test
    void shouldReturnTrainningRoomList() {
        List<ParticipantsGroupByRooms> participantsGroupByRooms = new ArrayList<>();
        participantsGroupByRooms.add(new ParticipantsGroupByRooms(1, 5,2));
        participantsGroupByRooms.add(new ParticipantsGroupByRooms(2, 4,3));
        participantsGroupByRooms.add(new ParticipantsGroupByRooms(3, 6,2));
        participantsGroupByRooms.add(new ParticipantsGroupByRooms(4, 8,5));

        Integer nextId = trainningRoomParticipantService.returnNextRoomToParticipant(participantsGroupByRooms);
        assertEquals(1, nextId);
    }
}
