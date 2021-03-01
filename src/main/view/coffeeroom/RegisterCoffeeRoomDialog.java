package main.view.coffeeroom;

import main.model.CoffeRoom;
import main.service.CoffeRoomService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static main.util.MessageUtil.INSERT_ERROR;
import static main.util.MessageUtil.INSERT_FAILED;
import static main.util.MessageUtil.INSERT_SUCCESS;
import static main.util.MessageUtil.RECORD_CONFLICT;

public class RegisterCoffeeRoomDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtId;
	private JTextField txtName;
	private JTextField txtRoomCapacity;

	/**
	 * Create the dialog.
	 */
	public RegisterCoffeeRoomDialog() {
		setTitle("Insert Coofee Room Details");
		setBounds(100, 100, 450, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			txtRoomCapacity = new JTextField();
			txtRoomCapacity.setBounds(120, 77, 100, 20);
			contentPanel.add(txtRoomCapacity);
			txtRoomCapacity.setColumns(10);
		}
		{
			txtName = new JTextField();
			txtName.setBounds(120, 44, 100, 20);
			contentPanel.add(txtName);
			txtName.setColumns(10);
		}
		{
			txtId = new JTextField();
			txtId.setBounds(120, 11, 100, 20);
			contentPanel.add(txtId);
			txtId.setColumns(10);
		}

		JLabel lblId = new JLabel("Id");
		lblId.setBounds(10, 14, 100, 14);
		contentPanel.add(lblId);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 47, 100, 14);
		contentPanel.add(lblName);

		JLabel lblRoomCapacity = new JLabel("Room Capacity");
		lblRoomCapacity.setBounds(10, 80, 100, 14);
		contentPanel.add(lblRoomCapacity);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(arg0 -> {
					try {
						CoffeRoomService service = new CoffeRoomService();
						CoffeRoom coffeRoom = new CoffeRoom();
						coffeRoom.setId(Integer.parseInt(txtId.getText()));
						coffeRoom.setName(txtName.getText());
						coffeRoom.setCapacity(Integer.parseInt(txtRoomCapacity.getText()));
						int result = service.save(coffeRoom);
						if (result > 0) {
							JOptionPane.showMessageDialog(null, INSERT_SUCCESS);
						} else {
							JOptionPane.showMessageDialog(null, INSERT_FAILED);
						}
					} catch (SQLIntegrityConstraintViolationException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null, RECORD_CONFLICT);
					} catch (SQLException throwables) {
						throwables.printStackTrace();
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
