package main.service;

import main.dao.ParticipantDAO;
import main.exception.NoRoomAvailableException;
import main.model.CoffeRoom;
import main.model.Participant;
import main.model.ParticipantInfo;
import main.model.ParticipantsGroupByCoffeeRooms;
import main.model.ParticipantsGroupByRooms;
import main.model.TrainningRoom;
import main.model.TrainningRoomParticipant;
import main.exception.RoomsNotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static main.util.MessageUtil.ROOMS_NOT_FOUND;

public class ParticipantService {

    private final ParticipantDAO participantDAO;

    private final TrainningRoomService trainningRoomService;

    private final TrainningRoomParticipantService trainningRoomParticipantService;

    private final CoffeRoomService coffeRoomService;

    public ParticipantService() throws SQLException {
        participantDAO = new ParticipantDAO();
        trainningRoomService = new TrainningRoomService();
        trainningRoomParticipantService = new TrainningRoomParticipantService();
        coffeRoomService = new CoffeRoomService();
    }

    public int save(Participant participant) throws SQLException {
        this.validateRoomsExist();
        final Integer coffeeRoomId = this.getCoffeeRoomId();
        participant.setCoffeRoom(new CoffeRoom(coffeeRoomId));
        int result = participantDAO.save(participant);
        this.saveParticipantInTrainningRoom(result, participant);
        this.switchTrainningRoom();
        return result;
    }

    private void validateRoomsExist() throws SQLException {
        final List<TrainningRoom> trainningRooms = trainningRoomService.findAll();
        final List<CoffeRoom> coffeeRooms = coffeRoomService.findAll();

        if (trainningRooms.isEmpty() || coffeeRooms.isEmpty()) {
            throw new RoomsNotFoundException(ROOMS_NOT_FOUND);
        }
    }

    public void switchTrainningRoom() throws SQLException {
        final List<TrainningRoomParticipant> trainningRoomParticipants =
                trainningRoomParticipantService.findByStage(2);
        if (trainningRoomParticipants.size() % 2 == 0) {
            final int switchPoint = (int) Math.ceil(trainningRoomParticipants.size() / (double)2);
            final List<TrainningRoomParticipant> stageOneList = trainningRoomParticipants.subList(0, switchPoint);
            final List<TrainningRoomParticipant> stageTwoList = trainningRoomParticipants.subList(switchPoint,
                    trainningRoomParticipants.size());

            for (int i = 0; i < stageTwoList.size(); i++) {
                TrainningRoomParticipant aux = stageOneList.get(i);
                stageOneList.get(i).setTrainningroomId(stageTwoList.get(i).getTrainningroomId());
                stageTwoList.get(i).setTrainningroomId(aux.getTrainningroomId());
            }

            stageOneList.addAll(stageTwoList);
            for (TrainningRoomParticipant trainningRoomParticipant : stageOneList) {
                trainningRoomParticipantService.updateTrainningRoom(trainningRoomParticipant);
            }
        }
    }

    private Integer getCoffeeRoomId() throws SQLException {
        List<ParticipantsGroupByCoffeeRooms> participantsGroupByCoffeeRooms =
                coffeRoomService.findParticipantGroupByCoffeeRooms();
        if (participantsGroupByCoffeeRooms.isEmpty()) {
            final List<CoffeRoom> coffeRoomList = coffeRoomService.findAll();
            if (coffeRoomList.isEmpty()) {
                throw new RoomsNotFoundException(ROOMS_NOT_FOUND);
            }
            return coffeRoomList.get(0).getId();
        }
       return coffeRoomService.returnNextCoffeeRoomToParticipant(participantsGroupByCoffeeRooms);
    }

    private void saveParticipantInTrainningRoom(
            int resultParticipant, Participant participant) throws SQLException {
        if (resultParticipant > 0) {
            final List<ParticipantsGroupByRooms> participantsGroupByRoomsList =
                    trainningRoomParticipantService.findParticipantGroupByRooms();
            Integer nextTrainningRoomId=null;
            if (participantsGroupByRoomsList.isEmpty()) {
                final List<TrainningRoom> trainningRoomList = trainningRoomService.findAll();
                if (trainningRoomList.isEmpty()) {
                    throw new RoomsNotFoundException(ROOMS_NOT_FOUND);
                }

                if (trainningRoomList.size()==1) {
                    nextTrainningRoomId = trainningRoomList.get(0).getId();
                }
            } else {
                nextTrainningRoomId = trainningRoomParticipantService
                        .returnNextRoomToParticipant(participantsGroupByRoomsList);
            }
            trainningRoomParticipantService.save(
                    new TrainningRoomParticipant(participant.getId(), nextTrainningRoomId, 1));
            trainningRoomParticipantService.save(
                    new TrainningRoomParticipant(participant.getId(), nextTrainningRoomId, 2));
        }
    }

    public Participant find(int id) throws SQLException {
        return participantDAO.findById(id);
    }

    public List<ParticipantInfo> findByRooms(int id) throws SQLException {
        return participantDAO.findByRooms(id);
    }
}
