package main.view.coffeeroom;

import main.model.CoffeRoom;
import main.model.ParticipantInfo;
import main.service.CoffeRoomService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.util.MessageUtil.RECORD_NOT_FOUND;

public class SearchCoffeeRoomDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtId;

	/**
	 * Create the dialog.
	 */
	public SearchCoffeeRoomDialog() {
		setTitle("Search Coffee Room");
		setBounds(100, 100, 350, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(10, 14, 46, 14);
		contentPanel.add(lblId);
		
		txtId = new JTextField();
		txtId.setColumns(10);
		txtId.setBounds(85, 11, 86, 20);
		contentPanel.add(txtId);

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(181, 10, 89, 23);
		contentPanel.add(btnSearch);

		btnSearch.addActionListener(e -> {
			try {
				CoffeRoomService service = new CoffeRoomService();
				int id=Integer.parseInt(txtId.getText());
				List<ParticipantInfo> participantInfo = service.findWithParticipants(id);
				if (participantInfo.isEmpty()) {
					CoffeRoom coffeRoom = service.find(id);
					if (coffeRoom != null) {
						contentPanel.remove(txtId);
						contentPanel.remove(btnSearch);

						List<CoffeRoom> coffeRoomList = new ArrayList<>();
						coffeRoomList.add(coffeRoom);
						String[] cols = {"ID", "Name", "Capacity"};
						Object[][] data = new Object[1][3];
						data[0][0] = coffeRoomList.get(0).getId();
						data[0][1] = coffeRoomList.get(0).getName();
						data[0][2] = coffeRoomList.get(0).getCapacity();
						JTable tb = new JTable(data, cols);
						JScrollPane roll = new JScrollPane(tb);
						getContentPane().add(roll);
						setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, RECORD_NOT_FOUND);
					}
				} else {
					String [] cols = {"Coffee Room", "Participant", "Stage"};
					Object [][] data = new Object[participantInfo.size()][3];
					for (int i=0;i<participantInfo.size();i++) {
						data[i][0] = participantInfo.get(i).getCoffeeRoomName();
						data[i][1] = participantInfo.get(i).getName();
						data[i][2] = participantInfo.get(i).getStage();
					}
					JTable tb = new JTable(data, cols);
					JScrollPane roll = new JScrollPane(tb);
					getContentPane().add(roll);
					setVisible(true);
				}
			} catch (SQLException throwables) {
				throwables.printStackTrace();
				JOptionPane.showMessageDialog(null, RECORD_NOT_FOUND);
			}
		});
	}
}
