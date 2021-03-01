package main.view.participant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ParticipantFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ParticipantFrame frame = new ParticipantFrame();
				frame.setTitle("Participants");
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
	public ParticipantFrame() {
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
		JButton btnRegisterParticipant = new JButton("Register");
		btnRegisterParticipant.addActionListener(arg0 -> EventQueue.invokeLater(() -> {
			try {
					RegisterParticipantDialog dialog = new RegisterParticipantDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}));
		btnRegisterParticipant.setBounds(36, 23, 110, 23);
		contentPane.add(btnRegisterParticipant);
	}

	private void addSearchButton() {
		JButton btnSearchParticipant = new JButton("Search");
		btnSearchParticipant.addActionListener(arg0 -> EventQueue.invokeLater(() -> {
			try {
					SearchParticipantDialog dialog = new SearchParticipantDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}));
		btnSearchParticipant.setBounds(36, 57, 110, 23);
		contentPane.add(btnSearchParticipant);
	}
}
