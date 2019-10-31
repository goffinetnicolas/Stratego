package umons.projet.game.Player;

import java.util.Collection;

import umons.projet.game.Team;
import umons.projet.game.Board.Board;
import umons.projet.game.Board.Move;
import umons.projet.game.Pieces.Piece;

/**
 * 
 * @author goffinet nicolas
 * Classe du joueur bleu
 *
 */

public class BluePlayer extends Player {
	
	public BluePlayer(final Board board, final Collection<Move> redStandardLegalMoves, final Collection<Move> blueStandardLegalMoves) {
		
		super(board, blueStandardLegalMoves, redStandardLegalMoves);
	}
	
	@Override
	public Collection<Piece> getActivePieces() {
		
		return this.board.getBluePieces();
	}
	
	@Override
	public Team getTeam() {
		
		return Team.BLUE;
	}
	
	@Override
	public Player getOpponent() {
		
		return this.board.redPlayer();
	}

}
