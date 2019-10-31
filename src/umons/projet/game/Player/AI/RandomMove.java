package umons.projet.game.Player.AI;

import java.util.ArrayList;
import java.util.Random;

import umons.projet.game.Board.Board;
import umons.projet.game.Board.Move;
import umons.projet.game.Pieces.Piece;

/**
 * 
 * @author goffinet nicolas
 * Classe pour l'IA aleatoire
 *
 */

public class RandomMove implements MoveStrategy {
	
    @Override
    public Move execute(Board board) {
    	return getRandomMove(board);
    	
    }
    
    /**
     * 
     * @param board un plateau
     * @return une ArrayList contenant les pieces qui peuvent bouger legalement
     */
    
    public ArrayList<Piece> getMovablePieces(Board board) {
    	
    	final ArrayList<Piece> movablePieces= new ArrayList<>();
    	for(Piece piece : board.currentPlayer().getActivePieces()) {
    		
    		if(piece.calculateLegalMoves(board).isEmpty()) {
    			
    			continue;
    		}
    		movablePieces.add(piece);
    	}
    	return movablePieces;
    }
    
    /**
     * 
     * @param board un plateau
     * @return un objet Move de facon aleatoire
     * on prend une piece au hasard dans les pieces qui peauvent bouger 
     * et on prend un objet Move au hasard dans la liste des mouvements legaux
     * si le joueur n'as plus de pieces qui peuvent attaquer, on retourne un nullMove
     */
    
    public Move getRandomMove(Board board) {
    	
    	final ArrayList<Piece> movablePieces = getMovablePieces(board);
    	if(movablePieces.isEmpty()) {
    		
    		Move move = new Move.nullMove();
    		return move;
    	}
    	Random rand = new Random();
    	int n = rand.nextInt(movablePieces.size());
    	Piece piece = movablePieces.get(n);
    	int size = piece.calculateLegalMoves(board).size();
    	Random rand2 = new Random();
    	int o = rand2.nextInt(size);
    	Object[] res = piece.calculateLegalMoves(board).toArray();
        return (Move) res[o];
        
        
    }
}

