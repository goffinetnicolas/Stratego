package umons.projet.game.Board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import umons.projet.game.Team;
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
import umons.projet.game.Player.BluePlayer;
import umons.projet.game.Player.Player;
import umons.projet.game.Player.RedPlayer;


/**
 * @author goffinet nicolas
 *Classe representante le plateau 
 */

public class Board {
	
	private static List<Square> gameBoard;
	private final Collection<Piece> bluePieces;
	private final Collection<Piece> redPieces;
	
	private final RedPlayer redPlayer;
	private final BluePlayer bluePlayer;
	private final Player currentPlayer;
	private final Map<Integer, Piece> boardConfig;
	
	
	/**
	 * @param builder
	 * un objet builder qui cree les bases du plateau
	 */
	
	private Board(Builder builder) {
		
		this.gameBoard= createGameBoard(builder);
		this.bluePieces= calculateActivePieces(this.gameBoard, Team.BLUE);
		this.redPieces= calculateActivePieces(this.gameBoard, Team.RED);
		
		final Collection<Move> redStandardLegalMoves= calculateLegalMoves(this.redPieces);
		final Collection<Move> blueStandardLegalMoves= calculateLegalMoves(this.bluePieces);
		
		this.redPlayer = new RedPlayer(this, redStandardLegalMoves, blueStandardLegalMoves);
		this.bluePlayer = new BluePlayer(this, redStandardLegalMoves, blueStandardLegalMoves);
		this.currentPlayer=builder.nextMoveMaker.choosePlayer(this.redPlayer, this.bluePlayer);
		this.boardConfig=builder.boardConfig;
		
	}
	
	
	/**
	 *@return un objet builder mais en String
	 *
	 *
	 */
	
	@Override
	public String toString() {
		
		final StringBuilder builder = new StringBuilder();
		
		for(int i=0; i<100; i++) {
			
			final String squareText = this.gameBoard.get(i).toString();
			builder.append(String.format("%3s", squareText));
			if((i+1)%10==0) {
				
				builder.append("\n");
			}
		}
		return builder.toString();
	}
	
	/**
	 * @return une collection des pieces rouges et bleues de type iterable
	 *
	 */
	
	public Iterable<Piece> getAllPieces() {
        
		return Iterables.unmodifiableIterable(Iterables.concat(this.redPieces, this.bluePieces));
    }
	/**
	 * @param coordinate de type int 
	 * @return permet de trouver la piece qui se trouve a la coordonee indiquee
	 * 
	 */
	
	public Piece getPiece(final int coordinate) {
        
		return this.boardConfig.get(coordinate);
    }
	
	/**
	 * @return 
	 * permet de savoir qui est le joueur qui a la permission de jouer 
	 */
	
	public Player currentPlayer() {
		
		return this.currentPlayer;
	}
	
	/**
	 * @return
	 * permet de trouver le joueur rouge
	 */
	
	public Player redPlayer() {
		
		return this.redPlayer;
	}
	
	/**
	 * @return
	 * permet de trouver le joueur bleu
	 */
	
	public Player bluePlayer() {
		
		return this.bluePlayer;
	}
	
	/**
	 * @return 
	 * une collection des pieces bleues
	 */
	
	public Collection<Piece> getBluePieces() {
		
		return this.bluePieces;
	}
	
	/**
	 * @return 
	 * une collection des pieces rouges
	 */
	
    public Collection<Piece> getRedPieces() {
		
		return this.redPieces;
	}
	
    /**
     * @param une collection de pieces
     * @return une collection des mouvements autorises pour une piece
     */

	private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {
		
		final List<Move> legalMoves= new ArrayList<>();
		
		for(final Piece piece : pieces) {
			
			legalMoves.addAll(piece.calculateLegalMoves(this));
		}
		return ImmutableList.copyOf(legalMoves);
	}
	
	/**
	 * @param une liste d'objet Square et une team 
	 * @return une collection des pieces qui sont encore en vie dans la team indiquee
	 */

	private static Collection<Piece> calculateActivePieces(final List<Square> gameBoard, final Team team) {
		
		final List<Piece> activePieces= new ArrayList<>();
		
		for(final Square square : gameBoard) {
			
			if(square.isSquareOccupied()) {
				
				final Piece piece= square.getPiece();
				
				if(piece.getPieceTeam() == team) {
					
					activePieces.add(piece);
				}
			}
		}
		return ImmutableList.copyOf(activePieces);
	}
	
	/**
	 * @param squareCoordinate 
	 * un int correspondant a la position de la piece
	 * @return 
	 * un objet de type Square correspondant a la coordonnee indiquee
	 */

	public static Square getSquare(final int squareCoordinate) {
		
		return gameBoard.get(squareCoordinate);
	}
	
	/**
	 * @param builder
	 * un objet builder
	 * @return 
	 * une liste inmmuable contenant des objets de type Square 
	 * on cree 100 objets Square
	 */
	
	private static List<Square> createGameBoard(final Builder builder) {
		
		final Square[] squares = new Square[100];
		for(int i=0; i<100; i++) {
			squares[i]= Square.createSquare(i, builder.boardConfig.get(i));
			
		}
		return ImmutableList.copyOf(squares);
	}
	
	/**
	 * @return une ArrayList contenant 40 nombres aleatoires non repetitifs allant de 60 jusqu'a 99
	 */
	
    public static List<Integer> randomRed() { 
    	
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
    
    /**
	 * @return une ArrayList contenant 40 nombres aleatoires non repetitifs allant de 0 jusqu'a 39
	 */
    
    public static List<Integer> randomBlue() { 
    	
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
    
    /*
     * @return un objet Board dans lequel toutes les pieces sont posees aleatoirement 
     */
	
	public static Board createStandardBoard() {
		
		final Builder builder = new Builder();
	    //RED
		List createdlist = randomRed();
		
		builder.setPiece(new Marshal(Team.RED, (int) createdlist.get(0)));  //1 
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
		return builder.build();
	} 
	
	/*
	 * @return un objet Iterable contenant tout les deplacements possible des 2 equipes
	 */
	
	public Iterable<Move> getAllLegalMoves() {
		
		return Iterables.unmodifiableIterable(Iterables.concat(this.redPlayer.getLegalMoves(), this.bluePlayer.getLegalMoves()));
	}
	/*
	 * classe interne Builder qui va aider a construire les objets Board
	 */
	
	public static class Builder {
		
		Map<Integer, Piece> boardConfig;
		Team nextMoveMaker;
		
	    public Builder() {
	    	
	    	this.boardConfig = new HashMap<>();
	    }
	    
	    /*
	     * @param une piece
	     * @return un objet builder ou la piece est posee
	     */
	    
	    public Builder setPiece(final Piece piece) {
	    	
	    	this.boardConfig.put(piece.getPiecePosition(), piece);
	    	return this;
	    }
	    
	    /*
	     * @param une equipe 
	     * @return un objet Builder avec le joueur qui a la permission de jouer
	     */
	    
	    public Builder setMoveMaker(final Team nextMoveMaker) {
	    	
	    	this.nextMoveMaker=nextMoveMaker;
	    	return this;
	    }
	   
	    /*
	     * @return un objet Board construit a l'aide de Builder
	     */
		
		public Board build() {
			
			return new Board(this);
		}
	}

}
