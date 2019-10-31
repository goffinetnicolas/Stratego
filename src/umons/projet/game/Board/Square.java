package umons.projet.game.Board;

import umons.projet.game.Pieces.*;

import java.util.Map;
import java.util.HashMap;

import com.google.common.collect.ImmutableMap;

/**
 * 
 * @author goffinet nicolas
 * Classe mere des cases du plateau
 *
 */

public abstract class Square {  //classe qui représente un carré dans la grille de jeu
	
	protected final int squareCoordinate;
	
	private static final Map<Integer, EmptySquare>EMPTY_SQUARE_CACHE= createAllPossibleEmptySquare(); //Permet de creer une sorte de dictionnaire avec Integer comme Key et EmptySquare comme Value
	
	/**
	 * 
	 * @return un objet Map de 100 elements ou les cles correspondent a des int representant la coordonnes et 
	 * les valeurs des cles sont des Objets EmptySquare
	 */
	
	private static Map<Integer, EmptySquare> createAllPossibleEmptySquare() { 
		
		final Map<Integer, EmptySquare> emptySquareMap = new HashMap<>();
		
		for (int x=0; x<100; x++) { //création d'une grille de 10x10 cases
			
			emptySquareMap.put(x, new EmptySquare(x)); 
		}
		
		return ImmutableMap.copyOf(emptySquareMap); // la librairie guava permet d'utiliser cette methode visant a creer les listes immuables
	}
	
	/**
	 * 
	 * @param squareCoordinate la coordonnee de la case
	 * @param piece une piece du tableau
	 * @return un objet OccupiedSquare si une piece est sur la coordonnee ou un objet EmptySquare si la coordonnee n'a pas de piece
	 */
	
	public static Square createSquare(final int squareCoordinate, final Piece piece) {
		
		return piece != null ? new OccupiedSquare(squareCoordinate, piece) : EMPTY_SQUARE_CACHE.get(squareCoordinate);
	}

	protected Square(final int squareCoordinate) { 

	    this.squareCoordinate=squareCoordinate;
	}
	
	public int getSquareCoordinate() { //accesseur
		
		return this.squareCoordinate;
	}
	
	public abstract boolean isSquareOccupied(); //méthode qui détermine si la case est occupée
	
	public abstract Piece getPiece(); // méthode pour obtenir le pion

}
