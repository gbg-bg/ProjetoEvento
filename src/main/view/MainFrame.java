package main.view;

import main.view.coffeeroom.CoffeeRoomFrame;
import main.view.participant.ParticipantFrame;
import main.view.trainningroom.TrainningRoomFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame  {

    private JPanel contentPane;

    public static void runApp() {
        EventQueue.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setTitle("Event Management Software");
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 200);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        this.addParticipantButton();
        this.addTrainningRoomButton();
        this.addCoffeeRoomButton();
    }

    private void addParticipantButton() {
        JButton btnParticipant = new JButton("Participant");
        btnParticipant.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            ParticipantFrame frame = new ParticipantFrame();
                            frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                            frame.setLocationRelativeTo(null);
                            frame.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        btnParticipant.setBounds(36, 23, 150, 23);
        contentPane.add(btnParticipant);
    }

    private void addTrainningRoomButton() {
        JButton btnTrainningRoom = new JButton("Trainning Room");
        btnTrainningRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            TrainningRoomFrame frame = new TrainningRoomFrame();
                            frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                            frame.setLocationRelativeTo(null);
                            frame.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        btnTrainningRoom.setBounds(36, 57, 150, 23);
        contentPane.add(btnTrainningRoom);
    }

    private void addCoffeeRoomButton() {
        JButton btnCoffeeRoom = new JButton("Coffee Room");
        btnCoffeeRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            CoffeeRoomFrame frame = new CoffeeRoomFrame();
                            frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                            frame.setLocationRelativeTo(null);
                            frame.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        btnCoffeeRoom.setBounds(36, 92, 150, 23);
        contentPane.add(btnCoffeeRoom);
    }
}
