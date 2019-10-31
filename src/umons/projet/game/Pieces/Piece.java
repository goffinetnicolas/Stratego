package umons.projet.game.Pieces;

import java.util.Collection;

import umons.projet.game.*;
import umons.projet.game.Board.Board;
import umons.projet.game.Board.Move;

/**
 * 
 * @author goffinet nicolas
 * Classe mere de toutes les differentes pieces
 */

public abstract class Piece {
	
	protected final int piecePosition;
	protected Team pieceTeam;
	protected int pieceRank;
	protected final PieceType pieceType;
	private final int cachedHashCode;
	public boolean isRevealed;
	
	protected Piece(final PieceType pieceType, final int piecePosition, final Team pieceTeam) {
		
		this.pieceType= pieceType;
		this.piecePosition=piecePosition;
		this.pieceTeam=pieceTeam;
		this.pieceRank=0;
		this.cachedHashCode=computeHashCode();
		this.isRevealed=false;
	}
	
	/**
	 * @return un nombre entier correspondant au hashcode de la piece
	 */
	
	private int computeHashCode() {
		
		int res = pieceType.hashCode();
		res = 31 * res + pieceTeam.hashCode();
		res = 31 * res + piecePosition;
		res = 31 * res + pieceRank;
		return res; 
	}
	
	@Override
	public boolean equals(final Object other) {
		
		if(this == other) {
			
			return true;
		}
		
		if(!(other instanceof Piece)) {
			
			return false;
		}
		final Piece otherPiece = (Piece) other;
		return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() && 
			   pieceTeam == otherPiece.getPieceTeam() && pieceRank == otherPiece.getPieceRank();
	}
	

	
	public boolean isPieceRevealed() { //accesseur
		
		return this.isRevealed;
	}
	
	public int getPieceRank() { //accesseur
		
		return this.pieceRank;
	}

	@Override
	public int hashCode() { //accesseur
		
        return this.cachedHashCode;
	}
	
	public PieceType getPieceType() { //accesseur
		
		return this.pieceType;
	}
	
	public int getPieceValue() { //accesseur
		
		return this.pieceType.getPieceValue();
	}

	public Team getPieceTeam() { //accesseur
		
		return this.pieceTeam;
	}
	
	/** 
	 * 
	 * @param board un plateau
	 * @return une Collection d'objet Move qui sont autorisés sur le plateau
	 */

    public abstract Collection<Move> calculateLegalMoves(final Board board);
    
    public int getPiecePosition() { //accesseur
    	
    	return this.piecePosition;
    }
    
    /**
     * 
     * @param move
     * un objet Move
     * @return un objet Piece mais avec des coordonnes differentes
     */
    
    public abstract Piece movePiece(Move move);

    /**
     * 
     * @author goffinet nicolas
     * Classe pour les types de pieces
     *
     */
    
	public enum PieceType {
    	
    	MARSHAL(900, "9") {
    		
    		@Override
    		public boolean isFlag() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isBomb() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isSpy() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMineSweeper() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMarshal() {
    			
    			return true;
    		}
    	},
    	GENERAL(800, "8") {
    		
    		@Override
    		public boolean isFlag() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isBomb() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isSpy() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMineSweeper() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMarshal() {
    			
    			return false;
    		}
    	},
    	COLONEL(700, "7") {
    		
    		@Override
    		public boolean isFlag() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isBomb() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isSpy() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMineSweeper() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMarshal() {
    			
    			return false;
    		}
    	},
    	COMMANDER(600, "6") {
    		
    		@Override
    		public boolean isFlag() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isBomb() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isSpy() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMineSweeper() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMarshal() {
    			
    			return false;
    		}
    	},
    	CAPTAIN(500, "5") {
    		
    		@Override
    		public boolean isFlag() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isBomb() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isSpy() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMineSweeper() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMarshal() {
    			
    			return false;
    		}
    	},
    	LIEUTENANT(400, "4") {
    		
    		@Override
    		public boolean isFlag() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isBomb() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isSpy() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMineSweeper() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMarshal() {
    			
    			return false;
    		}
    	},
    	SERGEANT(300, "3") {
    		
    		@Override
    		public boolean isFlag() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isBomb() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isSpy() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMineSweeper() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMarshal() {
    			
    			return false;
    		}
    	},
    	MINESWEEPER(200, "2") {
    		
    		@Override
    		public boolean isFlag() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isBomb() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isSpy() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMineSweeper() {
    			
    			return true;
    		}
    		
    		@Override
    		public boolean isMarshal() {
    			
    			return false;
    		}
    	},
    	SCOUT(100, "1") {
    		
    		@Override
    		public boolean isFlag() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isBomb() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isSpy() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMineSweeper() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMarshal() {
    			
    			return false;
    		}
    	},
    	SPY(100, "S") {
    		
    		@Override
    		public boolean isFlag() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isBomb() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isSpy() {
    			
    			return true;
    		}
    		
    		@Override
    		public boolean isMineSweeper() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMarshal() {
    			
    			return false;
    		}
    	},
    	BOMB(100, "B") {
    		
    		@Override
    		public boolean isFlag() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isBomb() {
    			
    			return true;
    		}
    		
    		@Override
    		public boolean isSpy() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMineSweeper() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMarshal() {
    			
    			return false;
    		}
    	},
    	FLAG(10000, "F") {
    		
    		@Override
    		public boolean isFlag() {
    			
    			return true;
    		}
    		
    		@Override
    		public boolean isBomb() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isSpy() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMineSweeper() {
    			
    			return false;
    		}
    		
    		@Override
    		public boolean isMarshal() {
    			
    			return false;
    		}
    	};
   
    	
    	private String pieceName;
    	private int pieceValue;
    	
    	PieceType(final int pieceValue, String pieceName) {
    		
    		this.pieceName= pieceName;
    		this.pieceValue=pieceValue;
    	}
    	
		@Override
    	public String toString() {
    		
    		return this.pieceName;
    	}
    	
    	public int getPieceValue() { //accesseur
    		
    		return this.pieceValue;
    	}
    	
    	/**
    	 * 
    	 * @return true si la piece est un drapeau
    	 */
    	
    	public abstract boolean isFlag();
    	
    	/**
    	 * 
    	 * @return true si la piece est une bombe
    	 */
    	
    	public abstract boolean isBomb();
    	
    	/**
    	 * 
    	 * @return true si la piece est un espion
    	 */
    	
    	public abstract boolean isSpy();
    	
    	/**
    	 * 
    	 * @return true si la piece est un demineur
    	 */
    	
    	public abstract boolean isMineSweeper();
    	
    	/**
    	 * 
    	 * @return true si la piece est un marechal
    	 */
    	
    	public abstract boolean isMarshal();
    	
    }
}
