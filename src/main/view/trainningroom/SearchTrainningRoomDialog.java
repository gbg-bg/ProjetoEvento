package main.view.trainningroom;

import main.model.ParticipantInfo;
import main.model.TrainningRoom;
import main.service.TrainningRoomService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static main.util.MessageUtil.RECORD_NOT_FOUND;

public class SearchTrainningRoomDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtId;

	/**
	 * Create the dialog.
	 */
	public SearchTrainningRoomDialog() {
		setTitle("Search Trainning Room");
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
				TrainningRoomService service = new TrainningRoomService();
				int id=Integer.parseInt(txtId.getText());
				List<ParticipantInfo> participantInfo = service.findByIdWithParticipants(id);
				if (participantInfo.isEmpty()) {
					TrainningRoom trainningRoom = service.find(id);
					if (trainningRoom != null) {
						contentPanel.remove(txtId);
						contentPanel.remove(btnSearch);
						btnSearch.setVisible(false);

						List<TrainningRoom> trainningRoomList = new ArrayList<>();
						trainningRoomList.add(trainningRoom);
						String[] cols = {"ID", "Name", "Capacity"};
						Object[][] data = new Object[1][3];
						data[0][0] = trainningRoomList.get(0).getId();
						data[0][1] = trainningRoomList.get(0).getName();
						data[0][2] = trainningRoomList.get(0).getCapacity();
						JTable tb = new JTable(data, cols);
						JScrollPane roll = new JScrollPane(tb);
						getContentPane().add(roll);
						setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, RECORD_NOT_FOUND);
					}
				} else {
					String [] cols = {"Trainning Room", "Participant", "Stage"};
					Object [][] data = new Object[participantInfo.size()][3];
					for (int i=0;i<participantInfo.size();i++) {
						data[i][0] = participantInfo.get(i).getTrainningRoomName();
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
