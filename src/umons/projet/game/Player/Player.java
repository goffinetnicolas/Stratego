package umons.projet.game.Player;

/**
 * 
 * @author goffinet nicolas 
 * Classe dediee au joueur
 *
 */

import java.util.Collection;
import com.google.common.collect.ImmutableList;
import umons.projet.game.Team;
import umons.projet.game.Board.Board;
import umons.projet.game.Board.Move;
import umons.projet.game.Pieces.Flag;
import umons.projet.game.Pieces.Piece;

public abstract class Player {

	protected final Board board;
	protected final Flag playerFlag;
	protected final Collection<Move> legalMoves;
	
	Player(final Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
		
		this.board=board;
		this.playerFlag=establishFlag();
		this.legalMoves=ImmutableList.copyOf(legalMoves);
	}
	

	public Flag getPlayerFlag() { //accesseur
		
		return this.playerFlag;
	}
	
	public Collection<Move> getLegalMoves() { //accesseur
		
		return this.legalMoves;
	}
	
	/**
	 * 
	 * @return true si le drapeau n'est pas elimine et null si il est elimine
	 */
	
	public Flag establishFlag() {
		
        for(final Piece piece : getActivePieces()) {
	
	        if(piece.getPieceType().isFlag()) {   
		
		        return (Flag) piece;
	
	        } else {
	        	
	        	return null;
	        }
        }
        return null;
	}
	
	/**
	 * 
	 * @return true si le drapeau a ete elimine et false si il est encore dans la partie
	 */

    public boolean isFlagCaptured() {	

        for(final Piece piece : getActivePieces()) {
	
	        if(piece.getPieceType().isFlag()) {
		
		        return false;
		    }
        }
        return true;
    }
    
    /**
     * 
     * @param move un objet Move
     * @return true si l'argument move est legal 
     */
	
	public boolean isMoveLegal(final Move move) {
		
		return this.legalMoves.contains(move); 
	}
	
	/**
	 * 
	 * @param move un objet Move
	 * @return un objet MoveTansition 
	 * si le mouvement est illegal, on retourne un objet MoveTansition avec MoveStatus illegal donc pas d'execution
	 * sinon on cree un nouveau plateau et on execute le mouvement puis on retourne un objet MoveTansition
	 */
	
	public MoveTransition makeMove(final Move move) {
		
		if(!isMoveLegal(move)) {
			
			return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE); //ici
		}
		
		final Board transitionBoard = move.execute();
	
		return new MoveTransition(transitionBoard, move, MoveStatus.DONE); 
	}
	
	/**
	 * 
	 * @param move un objet Move
	 * @return un MoveTransition mais en version undo()
	 */
	
	public MoveTransition unMakeMove(final Move move) {
        
		return new MoveTransition(this.board, move.undo(), move, MoveStatus.DONE);
    }
	
	/**
	 * 
	 * @return une collection des pieces actives donc non eliminee
	 */
	
	public abstract Collection<Piece> getActivePieces();
	
	/**
	 * 
	 * @return un Objet Team 
	 */
	
	public abstract Team getTeam();
	
	/**
	 * 
	 * @return un objet Player, le joueur adverse 
	 */
	
	public abstract Player getOpponent();
}
