package main.service;

import main.dao.CoffeRoomDAO;
import main.model.CoffeRoom;
import main.model.ParticipantInfo;
import main.model.ParticipantsGroupByCoffeeRooms;
import main.exception.NoRoomAvailableException;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static main.util.MessageUtil.NO_ROOMS_AVAILABLE;

public class CoffeRoomService {

    private final CoffeRoomDAO coffeRoomDAO;

    public CoffeRoomService() throws SQLException {
        coffeRoomDAO = new CoffeRoomDAO();
    }

    public int save(CoffeRoom coffeRoom) throws SQLException {
        return coffeRoomDAO.save(coffeRoom);
    }

    public List<CoffeRoom> findAll() throws SQLException {
        return coffeRoomDAO.findAll();
    }

    public CoffeRoom find(int id) throws SQLException {
        return coffeRoomDAO.findById(id);
    }

    public List<ParticipantInfo> findWithParticipants(int id) throws SQLException {
        return coffeRoomDAO.findByIdWithParticipants(id);
    }

    public List<ParticipantsGroupByCoffeeRooms> findParticipantGroupByCoffeeRooms() throws SQLException {
        return coffeRoomDAO.findParticipantsGroupByCoffeeRoom();
    }

    public Integer returnNextCoffeeRoomToParticipant(
            List<ParticipantsGroupByCoffeeRooms> participantsGroupByCoffeeRooms) {
        Optional<ParticipantsGroupByCoffeeRooms> result = Optional.of(participantsGroupByCoffeeRooms.stream()
                .filter(participantRoom -> participantRoom.getCapacity() > participantRoom.getParticipants())
                .min(Comparator.comparing(ParticipantsGroupByCoffeeRooms::getParticipants))
                .orElseThrow(() -> new NoRoomAvailableException(NO_ROOMS_AVAILABLE)));

        return result.get().getCoffeeRoomId();
    }
}
