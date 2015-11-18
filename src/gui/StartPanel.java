package gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class SomePanel extends JPanel{

	private static final long serialVersionUID = -6249447845824621020L;

	public SomePanel() {
		setBackground(new Color(25, 25, 112));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("WELCOME TO SPACEFIGHTER!!!\r\n");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setForeground(new Color(127, 255, 212));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 4;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblSelectYourGame = new JLabel("Select your game below:");
		lblSelectYourGame.setForeground(new Color(0, 250, 154));
		lblSelectYourGame.setBackground(new Color(0, 139, 139));
		GridBagConstraints gbc_lblSelectYourGame = new GridBagConstraints();
		gbc_lblSelectYourGame.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectYourGame.gridx = 4;
		gbc_lblSelectYourGame.gridy = 2;
		add(lblSelectYourGame, gbc_lblSelectYourGame);
		
		JButton btnNewButton = new JButton("ONSLAUGHT");
		btnNewButton.setBackground(new Color(0, 0, 255));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 4;
		add(btnNewButton, gbc_btnNewButton);
		
		JButton btnNewButton_1 = new JButton("DEFENDER");
		btnNewButton_1.setBackground(new Color(0, 0, 255));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 4;
		gbc_btnNewButton_1.gridy = 5;
		add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("TURRET SLAYER");
		btnNewButton_2.setBackground(new Color(0, 0, 255));
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = 4;
		gbc_btnNewButton_2.gridy = 6;
		add(btnNewButton_2, gbc_btnNewButton_2);
	}


}
