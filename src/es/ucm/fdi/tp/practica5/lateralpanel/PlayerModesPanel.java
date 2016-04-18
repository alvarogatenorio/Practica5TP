package es.ucm.fdi.tp.practica5.lateralpanel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class PlayerModesPanel extends JPanel {
	private static final String setButtonText = "Set";
	private static final String panelNameText = "Player Modes";
	private static final String manualText = "Manual";
	private static final String randomText = "Random";
	// private static final String intelligentText = "Intelligent";

	private JButton setButton;
	private JComboBox<Piece> playerName;
	private JComboBox<String> playerGameModes;

	public PlayerModesPanel(List<Piece> pieces) {
		super(new FlowLayout());

		Piece pieceArray[] = new Piece[pieces.size()];
		for (int i = 0; i < pieces.size(); i++) {
			pieceArray[i] = pieces.get(i);
		}

		String playerModesArray[] = { manualText, randomText };

		this.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), panelNameText));
		setButton = new JButton(setButtonText);
		playerName = new JComboBox<Piece>(pieceArray);
		playerGameModes = new JComboBox<String>(playerModesArray);
		this.add(playerName);
		this.add(playerGameModes);
		this.add(setButton);
		setButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (playerGameModes.getSelectedIndex()) {
				case 0:
					
					break;
				case 1:
					
					break;
				}
			}
		});

	}

}