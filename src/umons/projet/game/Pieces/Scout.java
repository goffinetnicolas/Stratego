package umons.projet.game.Pieces;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import com.google.common.collect.ImmutableList;

import umons.projet.game.Team;
import umons.projet.game.Board.Board;
import umons.projet.game.Board.BoardUtils;
import umons.projet.game.Board.Move;
import umons.projet.game.Board.Square;

/**
 * 
 * @author goffinet nicolas 
 * Classe dediee a la piece eclaireur
 *
 */

public class Scout extends Piece {
	
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES= {-10,-1,1,10}; // decalages de la position de la piece, liste des mouvements autorises
                                                                                 // -1 correspond à aller vers la gauche
	public Scout(Team pieceTeam, int piecePosition) {                            //  1 correspond à aller vers la droite
		super(PieceType.SCOUT, piecePosition, pieceTeam);                        // -10 correspond à aller vers l'arriere
		this.pieceRank=1;                                                        //  10 correspond à aller vers l'avant
	} 
	
	@Override
	public Collection<Move> calculateLegalMoves(final Board board) { // cette methode permet de calculer la liste des deplacements autorises
		
		final List<Move> legalMoves= new ArrayList<>();
		for(final int candidateCoordinationOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
			
		    int candidateDestinationCoordinate=this.piecePosition;
			
			while(BoardUtils.isValidSquareCoordinate(candidateDestinationCoordinate)) {
				
				if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinationOffset)||
				    isTenthColumnExclusion(candidateDestinationCoordinate, candidateCoordinationOffset)) {
					
					break;
				}
				
				candidateDestinationCoordinate += candidateCoordinationOffset;
				
				if(BoardUtils.isValidSquareCoordinate(candidateDestinationCoordinate)) {
					
					final Square candidateDestinationSquare = Board.getSquare(candidateDestinationCoordinate);
					
					if(!candidateDestinationSquare.isSquareOccupied()) {
						
						legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
					} else {
						
						final Piece pieceAtDestination = candidateDestinationSquare.getPiece();
						final Team pieceTeam = pieceAtDestination.getPieceTeam();
						
						if(this.pieceTeam != pieceTeam) {
							
							legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination ));
						}
						break;
					}
				}
			}
		}
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public String toString() {
		
		return PieceType.SCOUT.toString();
	}
	
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		
		return BoardUtils.FIRST_COLUMN.get(currentPosition) && (candidateOffset==-1);
	}
	
	private static boolean isTenthColumnExclusion(final int currentPosition, final int candidateOffset) {
		
		return BoardUtils.TENTH_COLUMN.get(currentPosition) && (candidateOffset==1);
	}
	
	@Override
	public Scout movePiece(Move move) {
		
		return new Scout(move.getMovedPiece().getPieceTeam(), move.getDestinationCoordinate());
	}
}
			
			
		
