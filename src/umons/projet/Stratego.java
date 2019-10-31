package umons.projet;

import umons.projet.game.Board.Board;
import umons.projet.gui.Table;

/**
 * 
 * @author goffinet nicolas
 * Classe principale a executer
 *
 */

public class Stratego { 
	
	public static void main(String[] args) {
		
		Board board = Board.createStandardBoard();
		System.out.println(board);
		Table.get().show();
	}

}
