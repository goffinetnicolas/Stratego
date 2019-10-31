package umons.projet.game.Pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import umons.projet.game.Team;
import umons.projet.game.Board.Board;
import umons.projet.game.Board.Move;

/**
 * 
 * @author goffinet nicolas 
 * Classe dediee a la piece bombe
 *
 */

public class Bomb extends Piece {
	
	public Bomb(final Team pieceTeam, final int piecePosition ) {
		
		super(PieceType.BOMB, piecePosition, pieceTeam);
		this.pieceRank=10;
	}
	
	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
	    
		final List<Move> legalMoves= new ArrayList<>();
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public String toString() {
		
		return PieceType.BOMB.toString();
	}
	
	@Override
	public Bomb movePiece(Move move) {
		
		return null;
	}

}
