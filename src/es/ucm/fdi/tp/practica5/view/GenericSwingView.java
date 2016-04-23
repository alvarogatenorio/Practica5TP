package es.ucm.fdi.tp.practica5.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.boardpanel.Cell.CellClickedListener;
import es.ucm.fdi.tp.practica5.controller.SwingController;
import es.ucm.fdi.tp.practica5.lateralpanel.AutomaticMovesPanel.IntelligentButtonListener;
import es.ucm.fdi.tp.practica5.lateralpanel.AutomaticMovesPanel.RandomButtonListener;
import es.ucm.fdi.tp.practica5.lateralpanel.PieceColorsPanel.ColorChangeListener;
import es.ucm.fdi.tp.practica5.lateralpanel.PlayerModesPanel.PlayerModesChangeListener;
import es.ucm.fdi.tp.practica5.lateralpanel.QuitRestartPanel.QuitButtonListener;
import es.ucm.fdi.tp.practica5.lateralpanel.QuitRestartPanel.RestartButtonListener;
import es.ucm.fdi.tp.practica5.moveControllers.MoveController;
import es.ucm.fdi.tp.practica5.utils.PieceColorMap;

public class GenericSwingView implements GameObserver {

	private static final String startingMessage = "Starting ";
	private static final String changeTurnMessage = "Turn for ";
	private static final String gameOverMessage = "Game Over!!\n";
	private static final String gameStatusMessage = "Game Status: ";
	private static final String winnerMessage = "Winner: ";
	private static final String titleMessage = "Board Games: ";
	private static final String youMessage = " You ";

	private PieceColorMap colorChooser;
	private SwingController controller;
	private Piece viewPiece;
	private Piece actualTurn;
	private GUI gui;
	private MoveController moveController;
	private Player random;
	private Player ai;

	public GenericSwingView(Observable<GameObserver> g, SwingController c,
			final Piece viewPiece, MoveController moveController, Player random,
			Player ai) {

		this.random = random;
		this.ai = ai;
		this.moveController = moveController;
		this.controller = c;
		this.viewPiece = viewPiece;
		g.addObserver(this);
		colorChooser = new PieceColorMap();
	}

	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces,
			Piece turn) {

		if (gui != null)
			gui.dispose();

		this.actualTurn = turn;

		gui = new GUI(board, pieces, colorChooser, turn, moveController,
				this.viewPiece, controller,
				this.getQuitButtonListener(controller),
				this.getRestartButtonListener(controller),
				this.getRandomButtonListener(board),
				this.getIntelligentButtonListener(board),
				this.getColorChangeListener(board),
				this.getPlayerModesChangeListener(board),
				this.getCellClickedListener(board));

		setGUITitle(gameDesc);
		checkForDisablingButtons();
		gui.update(moveController.getSelectedRow(),
				moveController.getSelectedColumn(),
				moveController.getFilterOnCells(board), turn);
		gui.appendToStatusMessagePanel(
				startingMessage + "'" + gameDesc + "'\n");
		if (this.viewPiece == this.actualTurn) {
			gui.appendToStatusMessagePanel(
					changeTurnMessage + youMessage + this.actualTurn + "\n");
		} else {
			gui.appendToStatusMessagePanel(
					changeTurnMessage + this.actualTurn + "\n");
		}

		setGUIvisible();
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {

		/* distinguir multiples vistas */

		gui.update(moveController.getSelectedRow(),
				moveController.getSelectedColumn(),
				moveController.getFilterOnCells(board), this.actualTurn);
		gui.appendToStatusMessagePanel(gameOverMessage);
		gui.appendToStatusMessagePanel(gameStatusMessage + state + "\n");
		if (winner != null) {
			gui.appendToStatusMessagePanel(winnerMessage + winner + "\n");
			JOptionPane.showMessageDialog(new JFrame(), winnerMessage + winner,
					gameOverMessage, JOptionPane.PLAIN_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(new JFrame(), gameStatusMessage,
					gameOverMessage, JOptionPane.PLAIN_MESSAGE);
		}
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		this.actualTurn = turn;
		checkForDisablingButtons();
		appendChangeTurnMessage();
		gui.update(moveController.getSelectedRow(),
				moveController.getSelectedColumn(),
				moveController.getFilterOnCells(board), this.actualTurn);
		checkForAutomaticMoves(board);
	}

	@Override
	public void onError(String msg) {
		JOptionPane.showMessageDialog(new JFrame(), msg, "Game error",
				JOptionPane.ERROR_MESSAGE);
		gui.appendToStatusMessagePanel(msg + "\n");
	}

	private void randomMakeMove(Board board) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				controller.makeMove(random);
				gui.update(moveController.getSelectedRow(),
						moveController.getSelectedColumn(),
						moveController.getFilterOnCells(board), actualTurn);
			}
		});
	}

	private void intelligentMakeMove(Board board) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				controller.makeMove(ai);
				gui.update(moveController.getSelectedRow(),
						moveController.getSelectedColumn(),
						moveController.getFilterOnCells(board), actualTurn);
			}
		});
	}

	private QuitButtonListener getQuitButtonListener(
			SwingController controller) {
		return new QuitButtonListener() {

			@Override
			public void QuitButtonClicked() {
				controller.stop();

				JFrame ventanaQuit = new JFrame();
				int n = JOptionPane.showConfirmDialog(ventanaQuit,
						"Are you sure you want to quit?", "Quit",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					System.exit(0);
				} else {
					ventanaQuit.dispose();
				}
			}

		};
	}

	private RestartButtonListener getRestartButtonListener(
			SwingController controller) {
		return new RestartButtonListener() {

			@Override
			public void RestartButtonClicked() {
				gui.dispose();
				gui = null;
				controller.restart();
			}

		};
	}

	private RandomButtonListener getRandomButtonListener(Board board) {
		return new RandomButtonListener() {

			@Override
			public void RandomButtonClicked() {
				randomMakeMove(board);
			}

		};
	}

	private IntelligentButtonListener getIntelligentButtonListener(
			Board board) {
		return new IntelligentButtonListener() {

			@Override
			public void IntelligentButtonClicked() {
				intelligentMakeMove(board);
			}

		};
	}

	private ColorChangeListener getColorChangeListener(Board board) {
		return new ColorChangeListener() {

			@Override
			public void colorChanged(Piece piece, Color color) {
				colorChooser.setColorFor(piece, color);
				gui.update(moveController.getSelectedRow(),
						moveController.getSelectedColumn(),
						moveController.getFilterOnCells(board), actualTurn);
			}

		};
	}

	/*
	 * Por alguna raz�n extra�a se piensan que todos son el modo al que se ha
	 * cambiado.
	 */

	private PlayerModesChangeListener getPlayerModesChangeListener(
			Board board) {
		return new PlayerModesChangeListener() {

			@Override
			public void SetButtonClicked(Piece piece, String mode) {
				if (controller.getPlayerType(piece) != mode)
					controller.setPlayerType(piece, mode);
				gui.update(moveController.getSelectedRow(),
						moveController.getSelectedColumn(),
						moveController.getFilterOnCells(board), piece);
			}

		};
	}

	private CellClickedListener getCellClickedListener(Board board) {
		return new CellClickedListener() {

			@Override
			public void cellWasClicked(int row, int column, MouseEvent e) {
				Integer answer = moveController.manageClicks(board, row, column,
						actualTurn, viewPiece, e);
				if (answer == MoveController.REPAINT_AND_MOVE) {
					controller.makeMove(moveController);
					gui.update(moveController.getSelectedRow(),
							moveController.getSelectedColumn(),
							moveController.getFilterOnCells(board), actualTurn);
				} else if (answer == MoveController.SOMETHING_TO_REPAINT) {
					gui.update(moveController.getSelectedRow(),
							moveController.getSelectedColumn(),
							moveController.getFilterOnCells(board), actualTurn);
				}
			}

		};

	}

	private void setGUITitle(String gameDesc) {
		if (viewPiece == null) {
			gui.setTitle(titleMessage + gameDesc);
		} else {
			gui.setTitle(titleMessage + gameDesc + " (" + viewPiece + ")");
		}
	}

	private void setGUIvisible() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				gui.setVisible(true);
			}
		});
	}

	private void appendChangeTurnMessage() {
		if (this.viewPiece == this.actualTurn) {
			gui.appendToStatusMessagePanel(
					changeTurnMessage + youMessage + this.actualTurn + "\n");
		} else {
			gui.appendToStatusMessagePanel(
					changeTurnMessage + this.actualTurn + "\n");
		}
	}

	private void checkForAutomaticMoves(Board board) {
		if (viewPiece == null || actualTurn == viewPiece) {
			if (controller.isPlayerOfType(this.actualTurn,
					controller.getPlayerModeString(SwingController.RANDOM))) {
				randomMakeMove(board);
			} else if (controller
					.isPlayerOfType(this.actualTurn, controller
							.getPlayerModeString(SwingController.INTELLIGENT))) {
				intelligentMakeMove(board);
			}
		}
	}

	private void checkForDisablingButtons() {
		if (this.viewPiece != null && this.viewPiece != this.actualTurn) {
			gui.disableAutomaticMoves(true);
		} else if (viewPiece == this.actualTurn) {
			gui.disableAutomaticMoves(false);
		}
	}
}
