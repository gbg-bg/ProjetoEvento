package main.view.trainningroom;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TrainningRoomFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				TrainningRoomFrame frame = new TrainningRoomFrame();
				frame.setTitle("Trainning Rooms");
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TrainningRoomFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.addRegisterButton();
		this.addSearchButton();
	}

	private void addRegisterButton() {
		JButton btnRegisterTrainningRoom = new JButton("Register");
		btnRegisterTrainningRoom.addActionListener(arg0 -> EventQueue.invokeLater(() -> {
			try {
					RegisterTrainningRoomDialog dialog = new RegisterTrainningRoomDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}));
		btnRegisterTrainningRoom.setBounds(36, 23, 110, 23);
		contentPane.add(btnRegisterTrainningRoom);
	}

	private void addSearchButton() {
		JButton btnSearchTrainningRoom = new JButton("Search");
		btnSearchTrainningRoom.addActionListener(arg0 -> EventQueue.invokeLater(() -> {
			try {
								SearchTrainningRoomDialog dialog = new SearchTrainningRoomDialog();
								dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
								dialog.setLocationRelativeTo(null);
								dialog.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}));
		btnSearchTrainningRoom.setBounds(36, 57, 110, 23);
		contentPane.add(btnSearchTrainningRoom);
	}
}
