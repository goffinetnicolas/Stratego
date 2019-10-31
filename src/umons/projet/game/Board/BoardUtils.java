package umons.projet.game.Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;


/**
 * @author goffinet nicolas
 * Classe repésentant certaines notions dans le plateau
 *
 */
public class BoardUtils {
	
	public static final ArrayList<Integer> lake = new ArrayList<Integer>(Arrays.asList(42,43,52,53,46,47,56,57));
	
	public final static List<Boolean> FIRST_COLUMN = initColumn(0); //exclusion 1ere colonne
	public final static List<Boolean> TENTH_COLUMN = initColumn(9); //exclusion 10eme colonne 
	
	public final static List<Boolean> TENTH_ROW = initRow(0);
    public final static List<Boolean> NINTH_ROW = initRow(10);
    public final static List<Boolean> EIGHTH_ROW = initRow(20);
    public final static List<Boolean> SEVENTH_ROW = initRow(30);
    public final static List<Boolean> SIXTH_ROW = initRow(40);
    public final static List<Boolean> FIFTH_ROW = initRow(50);
    public final static List<Boolean> FOURTH_ROW = initRow(60);
    public final static List<Boolean> THIRD_ROW = initRow(70);
    public final static List<Boolean> SECOND_ROW = initRow(80);
    public final static List<Boolean> FIRST_ROW = initRow(90);
    
    public final static List<String> ALGEBRAIC_NOTATION = initializeAlgebraicNotation();
    public final static Map<String, Integer> POSITION_TO_COORDINATE = initializePositionToCoordinateMap(); 

    /**
     * 
     * @param columnNumber, le nombre de la colonne
     * @return une liste de boolean allant de 1 à 100 ou touts les elements sont mis en false fauf les elements situés dans colonne du nombre
     */
     
    private static List<Boolean> initColumn(int columnNumber) {
		
		final Boolean[] column = new Boolean[100];
        for(int i = 0; i < column.length; i++) {
            column[i] = false;
        }
        do {
            column[columnNumber] = true;
            columnNumber += 10;
        } while(columnNumber < 100);
        return ImmutableList.copyOf(column);
    }
    
    /**
     * 
     * @return un objet Map de 100 elements ou les cles correspondent au coordonnee mises en valeur numerique du plateau 
     * et les valeurs des cles correspondent a un String representant la coordonnee en lettre/chiffre
     * 
     * @see initializeAlgebraicNotation()
     */
	
	private static Map<String, Integer> initializePositionToCoordinateMap() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            positionToCoordinate.put(ALGEBRAIC_NOTATION.get(i), i);
        }
        return positionToCoordinate;
    }
	
	/**
	 * 
	 * @return une liste immuable contenant 100 String representant la coordonnee en lettre/chiffre
	 */
	
	private static List<String>initializeAlgebraicNotation() {
		
		return ImmutableList.copyOf(new String[]{
                "a10", "b10", "c10", "d10", "e8", "f8", "g8", "h8", "i10", "j10",
                "a9", "b9", "c9", "d9", "e9", "f9", "g9", "h9", "i9", "j9",    
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8", "i8", "j8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7", "i7", "j7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6", "i6", "j6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5", "i5", "j5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4", "i4", "j4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3", "i3", "j3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2", "i2", "j2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", "i1", "j1",
        });
	}
	
	/**
	 * 
	 * @param rowNumber le numero de la rangee dans la grille
	 * @return une liste immuable de 100 boolean de facon a ce que les coordonees de la rangée indique en argument apparaissent en true et le reste des coordonees en false
	 */

	private static List<Boolean> initRow(int rowNumber) {
        final Boolean[] row = new Boolean[100];
        for(int i = 0; i < row.length; i++) {
            row[i] = false;
        }
        do {
            row[rowNumber] = true;
            rowNumber++;
        } while(rowNumber % 10 != 0);
        return ImmutableList.copyOf(row);
    }
	
	/**
	 * @throws RuntimeException 
	 */
	
	private BoardUtils() {
		
		throw new RuntimeException("You cannot instantiate me");
	}
	
	/**
	 * 
	 * @param coordinate la coordonee qui va etre testee 
	 * @return true si la coordonne est valide pour un deplacement
	 */

	public static boolean isValidSquareCoordinate(int coordinate) {
		
		if(lake.contains(coordinate)) { // verification du lac
		
			return false;
		
		} else { 
			
			return coordinate >= 0 && coordinate < 100; //verification si coordonee comprise entre 0 et 99
		}
		
	}
	
	/**
	 * 
	 * @param position un String representant la position en lettre/chiffre
	 * @return la position en argument mais en valeur numerique
	 */
	
	public static int getCoordinateAtPosition(final String position) {
        return POSITION_TO_COORDINATE.get(position);
    }
	
	/**
	 * 
	 * @param coordinate un int representant la position en valeur numerique
	 * @return la position en argument mais en String representant la position en lettre/chiffre
	 */
	
	public static String getPositionAtCoordinate(final int coordinate) {
        
		return ALGEBRAIC_NOTATION.get(coordinate);
    }
	
	/**
	 * 
	 * @param board un plateau
	 * @return un boolean qui affirme si la partie est terminee
	 */
	
	public static boolean isEndGame(final Board board) {
		
		return board.currentPlayer().isFlagCaptured();
	}

}
