package umons.projet.gui;

	
	import javax.swing.*;

import umons.projet.game.Team;
import umons.projet.game.Player.Player;
import umons.projet.gui.Table.PlayerType;

import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;

	class GameSetup extends JDialog {

	    private PlayerType redPlayerType;
	    private PlayerType bluePlayerType;

	    private static final String HUMAN_TEXT = "Human";
	    private static final String COMPUTER_TEXT = "Computer";

	    GameSetup(final JFrame frame,
	              final boolean modal) {
	        super(frame, modal);
	        final JPanel myPanel = new JPanel(new GridLayout(0, 1));
	        final JRadioButton redHumanButton = new JRadioButton(HUMAN_TEXT);
	        final JRadioButton redComputerButton = new JRadioButton(COMPUTER_TEXT);
	        final JRadioButton blueHumanButton = new JRadioButton(HUMAN_TEXT);
	        final JRadioButton blueComputerButton = new JRadioButton(COMPUTER_TEXT);
	        redHumanButton.setActionCommand(HUMAN_TEXT);
	        final ButtonGroup redGroup = new ButtonGroup();
	        redGroup.add(redHumanButton);
	        redGroup.add(redComputerButton);
	        redHumanButton.setSelected(true);

	        final ButtonGroup blueGroup = new ButtonGroup();
	        blueGroup.add(blueHumanButton);
	        blueGroup.add(blueComputerButton);
	        blueHumanButton.setSelected(true);

	        getContentPane().add(myPanel);
	        myPanel.add(new JLabel("Red"));
	        myPanel.add(redHumanButton);
	        myPanel.add(redComputerButton);
	        myPanel.add(new JLabel("Blue"));
	        myPanel.add(blueHumanButton);
	        myPanel.add(blueComputerButton);


	        final JButton cancelButton = new JButton("Cancel");
	        final JButton okButton = new JButton("OK");

	        okButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                redPlayerType = redComputerButton.isSelected() ? PlayerType.COMPUTER : PlayerType.HUMAN;
	                bluePlayerType = blueComputerButton.isSelected() ? PlayerType.COMPUTER : PlayerType.HUMAN;
	                GameSetup.this.setVisible(false);
	            }
	        });

	        cancelButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                System.out.println("Cancel");
	                GameSetup.this.setVisible(false);
	            }
	        });

	        myPanel.add(cancelButton);
	        myPanel.add(okButton);

	        setLocationRelativeTo(frame);
	        pack();
	        setVisible(false);
	    }

	    void promptUser() {
	        setVisible(true);
	        repaint();
	    }

	    boolean isAIPlayer(final Player player) {
	        if(player.getTeam() == Team.RED) {
	            return getRedPlayerType() == PlayerType.COMPUTER;
	        }
	        return getBluePlayerType() == PlayerType.COMPUTER;
	    }

	    PlayerType getRedPlayerType() {
	        return this.redPlayerType;
	    }

	    PlayerType getBluePlayerType() {
	        return this.bluePlayerType;
	    }

}
