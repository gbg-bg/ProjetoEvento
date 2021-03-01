package main.view.participant;


import main.exception.NoRoomAvailableException;
import main.exception.RoomsNotFoundException;
import main.model.CoffeRoom;
import main.model.Participant;
import main.service.ParticipantService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static main.util.MessageUtil.INSERT_ERROR;
import static main.util.MessageUtil.INSERT_FAILED;
import static main.util.MessageUtil.INSERT_SUCCESS;
import static main.util.MessageUtil.RECORD_CONFLICT;

public class RegisterParticipantDialog extends JDialog {

	private final JTextField txtId;
	private final JTextField txtName;
	private final JTextField txtSurname;

	/**
	 * Create the dialog.
	 */
	public RegisterParticipantDialog() {
		setTitle("Insert Participant Details");
		setBounds(100, 100, 500, 220);
		getContentPane().setLayout(new BorderLayout());
		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			txtId = new JTextField();
			txtId.setBounds(230, 7, 100, 20);
			contentPanel.add(txtId);
			txtId.setColumns(10);
		}
		{
			txtName = new JTextField();
			txtName.setBounds(230, 40, 100, 20);
			contentPanel.add(txtName);
			txtName.setColumns(10);
		}
		{
			txtSurname = new JTextField();
			txtSurname.setBounds(230, 73, 100, 20);
			contentPanel.add(txtSurname);
			txtSurname.setColumns(10);
		}

		JLabel lblId = new JLabel("ID");
		lblId.setBounds(10, 12, 70, 14);
		contentPanel.add(lblId);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 45, 70, 14);
		contentPanel.add(lblName);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(10, 78, 70, 14);
		contentPanel.add(lblSurname);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(arg0 -> {
					Participant participant=new Participant();
					participant.setId(Integer.parseInt(txtId.getText()));
					participant.setName(txtName.getText());
					participant.setSurname(txtSurname.getText());
					final ParticipantService service;
					try {
						service = new ParticipantService();
						final int result = service.save(participant);
						if (result > 0) {
							JOptionPane.showMessageDialog(null, INSERT_SUCCESS);
						} else {
							JOptionPane.showMessageDialog(null, INSERT_FAILED);
						}
					} catch (NoRoomAvailableException | RoomsNotFoundException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null, ex.getMessage());
					} catch (SQLIntegrityConstraintViolationException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null, RECORD_CONFLICT);
					} catch (SQLException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null, INSERT_ERROR);
					}
					dispose();
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(arg0 -> dispose());
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
												
		}
				
	}
}
