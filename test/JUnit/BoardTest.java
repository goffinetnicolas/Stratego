package JUnit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.google.common.collect.Iterables;

import umons.projet.game.Team;
import umons.projet.game.Board.Board;
import umons.projet.game.Board.Board.Builder;
import umons.projet.game.Board.BoardUtils;
import umons.projet.game.Board.Move;
import umons.projet.game.Board.Move.MoveFactory;
import umons.projet.game.Pieces.Bomb;
import umons.projet.game.Pieces.Captain;
import umons.projet.game.Pieces.Colonel;
import umons.projet.game.Pieces.Commander;
import umons.projet.game.Pieces.Flag;
import umons.projet.game.Pieces.General;
import umons.projet.game.Pieces.Lieutenant;
import umons.projet.game.Pieces.Marshal;
import umons.projet.game.Pieces.Minesweeper;
import umons.projet.game.Pieces.Piece;
import umons.projet.game.Pieces.Scout;
import umons.projet.game.Pieces.Sergeant;
import umons.projet.game.Pieces.Spy;
import umons.projet.game.Player.MoveTransition;

public class BoardTest {
	
    
    @Test
    public void initialBoard() {

        final Board board = Board.createStandardBoard();
        assertEquals(board.currentPlayer().getLegalMoves().size(), 20);
        assertEquals(board.currentPlayer().getOpponent().getLegalMoves().size(), 20);
        assertEquals(board.currentPlayer(), board.redPlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.bluePlayer());
        assertTrue(board.redPlayer().toString().equals("Red"));
        assertTrue(board.bluePlayer().toString().equals("Blue"));

        final Iterable<Piece> allPieces = board.getAllPieces();
        final Iterable<Move> allMoves = Iterables.concat(board.redPlayer().getLegalMoves(), board.bluePlayer().getLegalMoves());
        for(final Move move : allMoves) {
            assertFalse(move.isAttack());
            
        }

        assertEquals(Iterables.size(allMoves), 40);
        assertEquals(Iterables.size(allPieces), 32);
        assertFalse(BoardUtils.isEndGame(board));
        assertEquals(board.getPiece(35), null);
    }

    @Test
    public void testFlag() {

        final Builder builder = new Builder();
        // Blue Layout
        builder.setPiece(new Flag(Team.BLUE, 4));
        builder.setPiece(new Scout(Team.BLUE, 12));
        // Red Layout
        builder.setPiece(new Scout(Team.RED, 52));
        builder.setPiece(new Flag(Team.RED, 60));
        builder.setMoveMaker(Team.RED);
        // Set the current player
        final Board board = builder.build();
        System.out.println(board);

        assertEquals(board.redPlayer().getLegalMoves().size(), 6);
        assertEquals(board.bluePlayer().getLegalMoves().size(), 6);
        assertFalse(board.currentPlayer().isFlagCaptured());
        assertFalse(board.currentPlayer().getOpponent().isFlagCaptured());
        assertEquals(board.currentPlayer(), board.redPlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.bluePlayer());

        final Move move = MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e1"),
                BoardUtils.getCoordinateAtPosition("f1"));

        final MoveTransition moveTransition = board.currentPlayer()
                .makeMove(move);

        assertEquals(moveTransition.getTransitionMove(), move);
        assertEquals(moveTransition.getFromBoard(), board);
        assertEquals(moveTransition.getToBoard().currentPlayer(), moveTransition.getToBoard().bluePlayer());

        assertTrue(moveTransition.getMoveStatus().isDone());
        assertEquals(moveTransition.getToBoard().redPlayer().establishFlag().getPiecePosition(), 61);
        System.out.println(moveTransition.getToBoard());

    }

    public static List<Integer> randomRed() { // génère une arrayList contenant 40 nombres aléatoires non répétitifs compris entre 60 et 99
    	
		List<Integer> res= new ArrayList<>();
		while(res.size()!=40) {
		    
			Random rand = new Random();
		    int c = rand.nextInt(40);
		    c=c+60;
		    if(!res.contains(c)) {
			    res.add(c);
		    }
		}
		return res;
	}
    
    public static List<Integer> randomBlue() { // génère une arrayList contenant 40 nombres aléatoires non répétitifs compris entre 0 et 39
    	
		List<Integer> res= new ArrayList<>();
		while(res.size()!=40) {
		    
			Random rand = new Random();
		    int c = rand.nextInt(40);
		    if(!res.contains(c)) {
			    res.add(c);
		    }
		}
		return res;
	}

    @Test(expected=RuntimeException.class)
    public void testInvalidBoard() {

    	final Builder builder = new Builder();
	    //RED
		List createdlist = randomRed();
		
		builder.setPiece(new Marshal(Team.RED, (int) createdlist.get(0)));  //1  //génèration d'un tableau avec pièces placées aléatoirement dans chaque camp
		builder.setPiece(new General(Team.RED, (int) createdlist.get(1)));  //2
		builder.setPiece(new Colonel(Team.RED, (int) createdlist.get(2)));  //3
		builder.setPiece(new Colonel(Team.RED, (int) createdlist.get(3)));  //4
		builder.setPiece(new Commander(Team.RED, (int) createdlist.get(4)));  //5
		builder.setPiece(new Commander(Team.RED, (int) createdlist.get(5)));  //6
		builder.setPiece(new Commander(Team.RED, (int) createdlist.get(6)));  //7
		builder.setPiece(new Captain(Team.RED, (int) createdlist.get(7)));  //8
		builder.setPiece(new Captain(Team.RED, (int) createdlist.get(8)));  //9
		builder.setPiece(new Captain(Team.RED, (int) createdlist.get(9)));  //10
		builder.setPiece(new Captain(Team.RED, (int) createdlist.get(10)));  //11
		builder.setPiece(new Lieutenant(Team.RED, (int) createdlist.get(11)));  //12
		builder.setPiece(new Lieutenant(Team.RED, (int) createdlist.get(12)));  //13
		builder.setPiece(new Lieutenant(Team.RED, (int) createdlist.get(13)));  //14
		builder.setPiece(new Lieutenant(Team.RED, (int) createdlist.get(14)));  //15
		builder.setPiece(new Sergeant(Team.RED, (int) createdlist.get(15)));  //16
		builder.setPiece(new Sergeant(Team.RED, (int) createdlist.get(16)));  //17
		builder.setPiece(new Sergeant(Team.RED, (int) createdlist.get(17)));  //18
		builder.setPiece(new Sergeant(Team.RED, (int) createdlist.get(18)));  //19
		builder.setPiece(new Minesweeper(Team.RED, (int) createdlist.get(19)));  //20
		builder.setPiece(new Minesweeper(Team.RED, (int) createdlist.get(20)));  //21
		builder.setPiece(new Minesweeper(Team.RED, (int) createdlist.get(21)));  //22
		builder.setPiece(new Minesweeper(Team.RED, (int) createdlist.get(22)));  //23
		builder.setPiece(new Minesweeper(Team.RED, (int) createdlist.get(23)));  //24
		builder.setPiece(new Scout(Team.RED, (int) createdlist.get(24)));  //25
		builder.setPiece(new Scout(Team.RED, (int) createdlist.get(25)));  //26
		builder.setPiece(new Scout(Team.RED, (int) createdlist.get(26)));  //27
		builder.setPiece(new Scout(Team.RED, (int) createdlist.get(27)));  //28
		builder.setPiece(new Scout(Team.RED, (int) createdlist.get(28)));  //29
		builder.setPiece(new Scout(Team.RED, (int) createdlist.get(29)));  //30
		builder.setPiece(new Scout(Team.RED, (int) createdlist.get(30)));  //31
		builder.setPiece(new Scout(Team.RED, (int) createdlist.get(31)));  //32
		builder.setPiece(new Spy(Team.RED, (int) createdlist.get(32)));  //33
		builder.setPiece(new Flag(Team.RED, (int) createdlist.get(33)));  //34
		builder.setPiece(new Bomb(Team.RED, (int) createdlist.get(34)));  //35
		builder.setPiece(new Bomb(Team.RED, (int) createdlist.get(35)));  //36
		builder.setPiece(new Bomb(Team.RED, (int) createdlist.get(36)));  //37
		builder.setPiece(new Bomb(Team.RED, (int) createdlist.get(37)));  //38
		builder.setPiece(new Bomb(Team.RED, (int) createdlist.get(38)));  //39
		builder.setPiece(new Bomb(Team.RED, (int) createdlist.get(39)));  //40
		
		//BLUE
		List createdlist2 = randomBlue();
				
		builder.setPiece(new Marshal(Team.BLUE, (int) createdlist2.get(0)));  //1
		builder.setPiece(new General(Team.BLUE, (int) createdlist2.get(1)));  //2
		builder.setPiece(new Colonel(Team.BLUE, (int) createdlist2.get(2)));  //3
		builder.setPiece(new Colonel(Team.BLUE, (int) createdlist2.get(3)));  //4
		builder.setPiece(new Commander(Team.BLUE, (int) createdlist2.get(4)));  //5
		builder.setPiece(new Commander(Team.BLUE, (int) createdlist2.get(5)));  //6
		builder.setPiece(new Commander(Team.BLUE, (int) createdlist2.get(6)));  //7
		builder.setPiece(new Captain(Team.BLUE, (int) createdlist2.get(7)));  //8
		builder.setPiece(new Captain(Team.BLUE, (int) createdlist2.get(8)));  //9
		builder.setPiece(new Captain(Team.BLUE, (int) createdlist2.get(9)));  //10
		builder.setPiece(new Captain(Team.BLUE, (int) createdlist2.get(10)));  //11
		builder.setPiece(new Lieutenant(Team.BLUE, (int) createdlist2.get(11)));  //12
		builder.setPiece(new Lieutenant(Team.BLUE, (int) createdlist2.get(12)));  //13
		builder.setPiece(new Lieutenant(Team.BLUE, (int) createdlist2.get(13)));  //14
		builder.setPiece(new Lieutenant(Team.BLUE, (int) createdlist2.get(14)));  //15
		builder.setPiece(new Sergeant(Team.BLUE, (int) createdlist2.get(15)));  //16
		builder.setPiece(new Sergeant(Team.BLUE, (int) createdlist2.get(16)));  //17
		builder.setPiece(new Sergeant(Team.BLUE, (int) createdlist2.get(17)));  //18
		builder.setPiece(new Sergeant(Team.BLUE, (int) createdlist2.get(18)));  //19
		builder.setPiece(new Minesweeper(Team.BLUE, (int) createdlist2.get(19)));  //20
		builder.setPiece(new Minesweeper(Team.BLUE, (int) createdlist2.get(20)));  //21
		builder.setPiece(new Minesweeper(Team.BLUE, (int) createdlist2.get(21)));  //22
		builder.setPiece(new Minesweeper(Team.BLUE, (int) createdlist2.get(22)));  //23
		builder.setPiece(new Minesweeper(Team.BLUE, (int) createdlist2.get(23)));  //24
		builder.setPiece(new Scout(Team.BLUE, (int) createdlist2.get(24)));  //25
		builder.setPiece(new Scout(Team.BLUE, (int) createdlist2.get(25)));  //26
		builder.setPiece(new Scout(Team.BLUE, (int) createdlist2.get(26)));  //27
		builder.setPiece(new Scout(Team.BLUE, (int) createdlist2.get(27)));  //28
		builder.setPiece(new Scout(Team.BLUE, (int) createdlist2.get(28)));  //29
		builder.setPiece(new Scout(Team.BLUE, (int) createdlist2.get(29)));  //30
		builder.setPiece(new Scout(Team.BLUE, (int) createdlist2.get(30)));  //31
		builder.setPiece(new Scout(Team.BLUE, (int) createdlist2.get(31)));  //32
		builder.setPiece(new Spy(Team.BLUE, (int) createdlist2.get(32)));  //33
		builder.setPiece(new Flag(Team.BLUE, (int) createdlist2.get(33)));  //34
		builder.setPiece(new Bomb(Team.BLUE, (int) createdlist2.get(34)));  //35
		builder.setPiece(new Bomb(Team.BLUE, (int) createdlist2.get(35)));  //36
		builder.setPiece(new Bomb(Team.BLUE, (int) createdlist2.get(36)));  //37
		builder.setPiece(new Bomb(Team.BLUE, (int) createdlist2.get(37)));  //38
		builder.setPiece(new Bomb(Team.BLUE, (int) createdlist2.get(38)));  //39
		builder.setPiece(new Bomb(Team.BLUE, (int) createdlist2.get(39)));  //40
		
		builder.setMoveMaker(Team.RED);
	    builder.build();
    }

    @Test
    public void testAlgebreicNotation() {
        assertEquals(BoardUtils.getPositionAtCoordinate(0), "a8");
        assertEquals(BoardUtils.getPositionAtCoordinate(1), "b8");
        assertEquals(BoardUtils.getPositionAtCoordinate(2), "c8");
        assertEquals(BoardUtils.getPositionAtCoordinate(3), "d8");
        assertEquals(BoardUtils.getPositionAtCoordinate(4), "e8");
        assertEquals(BoardUtils.getPositionAtCoordinate(5), "f8");
        assertEquals(BoardUtils.getPositionAtCoordinate(6), "g8");
        assertEquals(BoardUtils.getPositionAtCoordinate(7), "h8");
    }

    @Test
    public void mem() {
        final Runtime runtime = Runtime.getRuntime();
        long start, end;
        runtime.gc();
        start = runtime.freeMemory();
        Board board = Board.createStandardBoard();
        end =  runtime.freeMemory();
        System.out.println("That took " + (start-end) + " bytes.");

    }
    private static int calculatedActivesFor(final Board board,
                                            final Team team) {
        int count = 0;
        for (final Piece p : board.getAllPieces()) {
            if (p.getPieceTeam().equals(team)) {
                count++;
            }
        }
        return count;
    }
   
} 
