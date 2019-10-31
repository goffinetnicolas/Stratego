package umons.projet.game.Player;

import java.util.Collection;

import umons.projet.game.Team;
import umons.projet.game.Board.Board;
import umons.projet.game.Board.Move;
import umons.projet.game.Pieces.Piece;

/**
 * @author goffinet nicolas 
 * Classe du joueur rouge
 *
 */

public class RedPlayer extends Player {
	
	public RedPlayer(final Board board, final Collection<Move> redStandardLegalMoves, final Collection<Move> blueStandardLegalMoves) {
		
		super(board, redStandardLegalMoves, blueStandardLegalMoves);
	}
	
	@Override
	public Collection<Piece> getActivePieces() {
		
		return this.board.getRedPieces();
	}
	
	@Override
	public Team getTeam() {
		
		return Team.RED;
	}
	
	@Override
	public Player getOpponent() {
		
		return this.board.bluePlayer();
	}
}
