package es.ucm.fdi.tp.practica5.moveControllers;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectn.ConnectNMove;

@SuppressWarnings("serial")
public class ConnectNMoveController extends MoveController {
	
	private int row;
	private int column;
	
	

	@Override
	public Integer getSelectedRow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getSelectedColumn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		return new ConnectNMove(row, column, p);
	}

	@Override
	public List<Pair<Integer, Integer>> getFilterOnCells(Board board) {
		List<Pair<Integer, Integer>> filterOnCellsList = new ArrayList<Pair<Integer, Integer>>();
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getCols(); j++) {
				if (board.getPosition(i, j) == null) {
					filterOnCellsList.add(new Pair<Integer, Integer>(i, j));
				}
			}
		}
		return filterOnCellsList;
	}

	@Override
	public Integer manageClicks(Board board, int row, int column, Piece turn,
			Piece viewPiece, MouseEvent mouseEvent,
			MoveStateChangeListener moveStateChangeListener) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String notifyMoveStartInstructions() {
		return null;
	}

}
