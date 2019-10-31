package JUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

import umons.projet.game.Team;
import umons.projet.game.Board.Board;
import umons.projet.game.Board.BoardUtils;
import umons.projet.game.Board.Move;
import umons.projet.game.Pieces.Flag;
import umons.projet.game.Pieces.Marshal;
import umons.projet.game.Pieces.Piece;
import umons.projet.game.Pieces.Scout;

public class PieceTest {


    @Test
    public void testLegalMoveAllAvailable() {

        final Board.Builder boardBuilder = new Board.Builder();
        // Blue Layout
        boardBuilder.setPiece(new Flag(Team.BLUE, 4));
        boardBuilder.setPiece(new Marshal(Team.BLUE, 28));
        // Red Layout
        boardBuilder.setPiece(new Flag(Team.RED, 4));
        boardBuilder.setPiece(new Marshal(Team.RED, 60));
        // Set the current player
        boardBuilder.setMoveMaker(Team.RED);
        final Board board = boardBuilder.build();
        final Collection<Move> redLegals = board.redPlayer().getLegalMoves();
        assertEquals(redLegals.size(), 13);
        final Move wm1 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("d6"));
        final Move wm2 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("f6"));
        final Move wm3 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("c5"));
        final Move wm4 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("g5"));
        final Move wm5 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("c3"));
        final Move wm6 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("g3"));
        final Move wm7 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("d2"));
        final Move wm8 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("f2"));

        assertTrue(redLegals.contains(wm1));
        assertTrue(redLegals.contains(wm2));
        assertTrue(redLegals.contains(wm3));
        assertTrue(redLegals.contains(wm4));
        assertTrue(redLegals.contains(wm5));
        assertTrue(redLegals.contains(wm6));
        assertTrue(redLegals.contains(wm7));
        assertTrue(redLegals.contains(wm8));

        final Board.Builder boardBuilder2 = new Board.Builder();
        // Black Layout
        boardBuilder.setPiece(new Flag(Team.BLUE, 4));
        boardBuilder.setPiece(new Marshal(Team.BLUE, 28));
        // White Layout
        boardBuilder.setPiece(new Flag(Team.RED, 4));
        boardBuilder.setPiece(new Marshal(Team.RED, 60));
        // Set the current player
        boardBuilder2.setMoveMaker(Team.BLUE);
        final Board board2 = boardBuilder2.build();
        final Collection<Move> blueLegals = board.bluePlayer().getLegalMoves();
        
        final Move bm1 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("d7"));
        final Move bm2 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("f7"));
        final Move bm3 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("c6"));
        final Move bm4 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("g6"));
        final Move bm5 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("c4"));
        final Move bm6 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("g4"));
        final Move bm7 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("d3"));
        final Move bm8 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("f3"));

        assertEquals(blueLegals.size(), 13);

        assertTrue(blueLegals.contains(bm1));
        assertTrue(blueLegals.contains(bm2));
        assertTrue(blueLegals.contains(bm3));
        assertTrue(blueLegals.contains(bm4));
        assertTrue(blueLegals.contains(bm5));
        assertTrue(blueLegals.contains(bm6));
        assertTrue(blueLegals.contains(bm7));
        assertTrue(blueLegals.contains(bm8));
    }

    @Test
    public void testMiddleScoutEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new Flag(Team.BLUE, 4));
        // White Layout
        builder.setPiece(new Scout(Team.RED, 55));
        builder.setPiece(new Flag(Team.RED, 80));
        // Set the current player
        builder.setMoveMaker(Team.RED);
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.redPlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.bluePlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 18);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e8"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("a4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("b4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("c4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("f4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("g4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("h4"))));
    }

    


    @Test
    public void testHashCode() {
        final Board board = Board.createStandardBoard();
        final Set<Piece> pieceSet = Sets.newHashSet(board.getAllPieces());
        final Set<Piece> redPieceSet = Sets.newHashSet(board.getRedPieces());
        final Set<Piece> bluePieceSet = Sets.newHashSet(board.getBluePieces());
        assertTrue(pieceSet.size() == 32);
        assertTrue(redPieceSet.size() == 16);
        assertTrue(bluePieceSet.size() == 16);
    }

}
