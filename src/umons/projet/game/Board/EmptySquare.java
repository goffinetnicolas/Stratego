package umons.projet.game.Board;

import umons.projet.game.Pieces.*;

/**
 * 
 * @author goffinet nicolas
 * classe representant une case vide
 *
 */

public class EmptySquare extends Square{ //représente une case vide
	
	protected EmptySquare(int coordinate) {
		
		super(coordinate);
	}
	
	@Override
	public boolean isSquareOccupied() { //test si case occupeé, retourne toujours faux
		
		return false;
	}
	
	@Override
	public Piece getPiece() { //obtenir la piece sur la case
		return null;
	}
	
	@Override
	public String toString() {
		
		return "[]";
	}

}
