package umons.projet.game.Player;

import umons.projet.game.Board.Board;
import umons.projet.game.Board.Move;

/**
 * 
 * @author goffinet nicolas
 * Classe des transitions de mouvement
 *
 */

public class MoveTransition {
	
	private final Board transitionBoard;
	private final Move move;
	private final MoveStatus moveStatus;
	private final Board fromBoard;
	private final Board toBoard;
	
	public MoveTransition(final Board transitionBoard, final Move move, final MoveStatus moveStatus) {
		
		this.transitionBoard=transitionBoard;
		this.move=move;
		this.moveStatus=moveStatus;
		this.fromBoard=null;
		this.toBoard=null;
	}
	
	public MoveTransition(final Board fromBoard, final Board toBoard, final Move move, final MoveStatus moveStatus) {
		
		this.fromBoard = fromBoard;
        this.toBoard = toBoard;
        this.move = move;
        this.moveStatus = moveStatus;
        this.transitionBoard=null;
	}
	
	public Move getTransitionMove() { //accesseur
		
		return this.move;
	}
	
	public MoveStatus getMoveStatus() { //accesseur
		
		return this.moveStatus;
	}
	
	public Board getTransitionBoard() { //accesseur
		
		return this.transitionBoard;
	}
	
	public Board getFromBoard() { //accesseur
        
		return this.fromBoard;
    }

    public Board getToBoard() { //accesseur
         
    	return this.toBoard;
    }
}