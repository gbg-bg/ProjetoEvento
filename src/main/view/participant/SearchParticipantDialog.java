package main.view.participant;


import main.model.Participant;
import main.model.ParticipantInfo;
import main.service.ParticipantService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static main.util.MessageUtil.RECORD_NOT_FOUND;

public class SearchParticipantDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtId;

	/**getStage
	 * Create the dialog.
	 */
	public SearchParticipantDialog() {
		setTitle("Search Participant");
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
				ParticipantService service = new ParticipantService();
				int id=Integer.parseInt(txtId.getText());
				List<ParticipantInfo> participantInfo = service.findByRooms(id);
				if (participantInfo.isEmpty()) {
					Participant participant = service.find(id);
					if (participant != null) {
						contentPanel.remove(txtId);
						contentPanel.remove(btnSearch);
						btnSearch.setVisible(false);
						contentPanel.revalidate();
						contentPanel.repaint();

						List<Participant> participantList = new ArrayList<>();
						participantList.add(participant);
						String[] cols = {"ID", "Name", "Surname"};
						Object[][] data = new Object[1][3];
						data[0][0] = participantList.get(0).getId();
						data[0][1] = participantList.get(0).getName();
						data[0][2] = participantList.get(0).getSurname();
						JTable tb = new JTable(data, cols);
						JScrollPane roll = new JScrollPane(tb);
						getContentPane().add(roll);
						setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, RECORD_NOT_FOUND);
					}
				} else {
					String [] cols = {"Participant", "Trainning Room", "Coffee Room", "Stage"};
					Object [][] data = new Object[participantInfo.size()][4];
					for (int i=0;i<participantInfo.size();i++) {
						data[i][0] = participantInfo.get(i).getName();
						data[i][1] = participantInfo.get(i).getTrainningRoomName();
						data[i][2] = participantInfo.get(i).getCoffeeRoomName();
						data[i][3] = participantInfo.get(i).getStage();
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
