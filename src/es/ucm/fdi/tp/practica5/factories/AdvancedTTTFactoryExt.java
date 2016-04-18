package es.ucm.fdi.tp.practica5.factories;

import es.ucm.fdi.tp.basecode.attt.AdvancedTTTFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.GenericSwingView;

@SuppressWarnings("serial")
public class AdvancedTTTFactoryExt extends AdvancedTTTFactory implements SwingPlayable {

	@Override
	public void createSwingView(final Observable<GameObserver> g, final Controller c, final Piece viewPiece,
			Player random, Player ai) {
		new GenericSwingView(g, c);
	}

	@Override
	public Player createSwingManualPlayer() {
		// TODO Auto-generated method stub
		return null;
	}
}
