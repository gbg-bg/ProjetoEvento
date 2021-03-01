package main.view.coffeeroom;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CoffeeRoomFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CoffeeRoomFrame frame = new CoffeeRoomFrame();
					frame.setTitle("Coffee Rooms");
					frame.setLocationRelativeTo(null); 
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CoffeeRoomFrame() {
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
		JButton btnRegisterCoffeeRoom = new JButton("Register");
		btnRegisterCoffeeRoom.addActionListener(arg0 -> EventQueue.invokeLater(() -> {
			try {
					RegisterCoffeeRoomDialog dialog = new RegisterCoffeeRoomDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}));
		btnRegisterCoffeeRoom.setBounds(36, 23, 110, 23);
		contentPane.add(btnRegisterCoffeeRoom);
	}

	private void addSearchButton() {
		JButton btnSearchCoffeeRoom = new JButton("Search");
		btnSearchCoffeeRoom.addActionListener(arg0 -> EventQueue.invokeLater(() -> {
			try {
					SearchCoffeeRoomDialog dialog = new SearchCoffeeRoomDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}));
		btnSearchCoffeeRoom.setBounds(36, 57, 110, 23);
		contentPane.add(btnSearchCoffeeRoom);
	}
}
