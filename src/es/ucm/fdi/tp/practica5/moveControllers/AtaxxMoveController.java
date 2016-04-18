package es.ucm.fdi.tp.practica5.moveControllers;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class AtaxxMoveController extends Player {

	/*Igual deber�amos tener aqu� una interfacita para mandar los mensajes
	 * al status...*/
	
	/**
	private AtaxxMove ataxxMove;
	private boolean somethingSelected = false;

	private int oldRow;
	private int oldColumn;

	@Override
	public boolean generateMove(int row, int column, Board board, Piece turn, List<Piece> pieces, Observable<GameObserver> g) {
		boolean repaint = false;
		if (somethingSelected) {
			/* We are selecting an empty position... */
			/**if (board.getPosition(row, column) == null) {
				try {
					this.ataxxMove = new AtaxxMove(this.oldRow, this.oldColumn, row, column, turn);
					repaint = true;
				} catch (Exception e) {
					/* Nothing has to be done but stopping the exception... */
				/**}
			}
		} else {
			if(board.getPosition(row, column) == turn){
				this.oldRow = row;
				this.oldColumn = column;
				somethingSelected = true;
				//repaint keep being false...
			}
		}
		return repaint;
	}
*/
	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		return null;
	}
}