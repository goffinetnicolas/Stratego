package umons.projet.game.Board;

import umons.projet.game.Pieces.Piece;

/**
 * 
 * @author goffinet nicolas
 * Classe dediee a un carre occupe, un carre qui contient une piece
 */
public class OccupiedSquare extends Square { //méthode qui représente une case occupée
	
    private final Piece pieceOnSquare;

	protected OccupiedSquare(int squareCoordinate, Piece pieceOnSquare) { 
		
		super(squareCoordinate);
		this.pieceOnSquare=pieceOnSquare;
	}
	
	protected OccupiedSquare(int squareCoordinate) {
		
		super(squareCoordinate);
		this.pieceOnSquare = null;
	}

	@Override
	public boolean isSquareOccupied() { //test si case occupée, retourne toujours vrai
		
		return true;
	}
	
	@Override
	public Piece getPiece() { //obtenir la piece située sur la case
		
		return this.pieceOnSquare;
	}
	
	@Override
	public String toString() {
		
		return getPiece().toString();
	}
	
}