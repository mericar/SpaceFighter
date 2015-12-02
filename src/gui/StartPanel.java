package gui;

import javax.swing.JPanel;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import resources.Constants;

public class StartPanel extends JPanel implements Constants{

	private static final long serialVersionUID = -6249447845824621020L;

	public StartPanel() {

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
		setBackground(new Color(10, 55, 112));
        setSize(BOARD_WIDTH, BOARD_HEIGHT);

		
		JLabel wlcmLabel = new JLabel("WELCOME TO SPACEFIGHTER!!!\r\n");
		wlcmLabel.setFont(new Font("Arial", Font.BOLD, 28));
		wlcmLabel.setForeground(new Color(117, 200, 23));
		GridBagConstraints gbcWlcmLabel = new GridBagConstraints();
		gbcWlcmLabel.anchor = GridBagConstraints.NORTH;
		gbcWlcmLabel.insets = new Insets(0, 0, 5, 5);
		gbcWlcmLabel.gridx = 4;
		gbcWlcmLabel.gridy = 1;
		add(wlcmLabel, gbcWlcmLabel);
		
		JLabel lblSelectYourGame = new JLabel("Select your game below:");
		lblSelectYourGame.setForeground(new Color(0, 250, 154));
		GridBagConstraints gbc_lblSelectYourGame = new GridBagConstraints();
		gbc_lblSelectYourGame.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectYourGame.gridx = 4;
		gbc_lblSelectYourGame.gridy = 3;
		add(lblSelectYourGame, gbc_lblSelectYourGame);
		
		JButton onslaughtButton = new JButton("ONSLAUGHT");
		onslaughtButton.setBackground(new Color(0, 0, 255));
        onslaughtButton.setFont(new Font("Courier", Font.BOLD, 20));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 5;
		onslaughtButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//
			}
		});
		add(onslaughtButton, gbc_btnNewButton);
		
		JButton defenderButton = new JButton("DEFENDER");
		defenderButton.setBackground(new Color(0, 0, 255));
        defenderButton.setFont(new Font("Courier", Font.BOLD, 20));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 4;
		gbc_btnNewButton_1.gridy = 7;
		defenderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//
                new SpaceFighter();
			}
		});
		add(defenderButton, gbc_btnNewButton_1);
		
		JButton turretGameButton = new JButton("TURRET SLAYER");
		turretGameButton.setBackground(new Color(0, 0, 255));
        turretGameButton.setFont(new Font("Courier", Font.BOLD, 20));
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = 4;
		gbc_btnNewButton_2.gridy = 9;
		turretGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//
			}
		});
		add(turretGameButton, gbc_btnNewButton_2);
	}


}
