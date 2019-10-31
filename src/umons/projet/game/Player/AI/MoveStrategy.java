package umons.projet.game.Player.AI;

import umons.projet.game.Board.Board;
import umons.projet.game.Board.Move;

public interface MoveStrategy {
	
	/**
	 * 
	 * @param board un plateau
	 * @return un Objet Move
	 */
	
	Move execute(Board board);

}
