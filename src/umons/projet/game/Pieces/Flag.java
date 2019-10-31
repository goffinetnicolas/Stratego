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
 * Classe dediee a la piece drapeau
 *
 */

public class Flag extends Piece {
	
	public Flag(final Team pieceTeam, final int piecePosition) {
		
		super(PieceType.FLAG,piecePosition, pieceTeam);
		this.pieceRank=0;
	}
	
	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
	    
		final List<Move> legalMoves= new ArrayList<>();
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public String toString() {
		
		return PieceType.FLAG.toString();
	}
	
	@Override
	public Flag movePiece(Move move) {
		
		return null;
	}

}

