package umons.projet.game.Board;


import umons.projet.game.Pieces.Piece;
import umons.projet.game.Team;
import umons.projet.game.Board.Board.Builder;

/**
 * 
 * @author goffinet nicolas
 * Classe representant les differents types de mouvements
 * 
 *
 */

public abstract class Move { //classe qui va permettre de faire bouger les pieces
	
	final Board board; // le plateau
	Piece movedPiece; // la piece qui va bouger
	final int destinationCoordinate; // la coordonnee ou on veut se deplacer
	
	private Move(Board board, Piece movedPiece, int destinationCoordinate) {
		
		this.board=board;
		this.movedPiece=movedPiece;
		this.destinationCoordinate=destinationCoordinate;
	}
	
    private Move(Board board, int destinationCoordinate) {
		
		this.board=board;
		this.movedPiece=null;
		this.destinationCoordinate=destinationCoordinate;
	}
	
	public static final Move NULL_MOVE = new nullMove(); // instancie un mouvement nul
	
	@Override
	public int hashCode () {
		
		final int prime = 31;
		int res = 1;
		res = prime * this.destinationCoordinate;
		res = prime * this.movedPiece.hashCode();
		return res;
	}
	
	@Override
	public boolean equals(final Object other) {
		
		if(this == other) {
			
			return true;
		}
		if(!(other instanceof Move)) {
			
			return false;
		}
		final Move otherMove = (Move) other;
		return getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
			   getMovedPiece().equals(otherMove.getMovedPiece());
	}
	
	public int getDestinationCoordinate() { //accesseur
		
		return this.destinationCoordinate;
	}
	
	public Board getBoard() { //accesseur
        return this.board;
    }

    public int getCurrentCoordinate() { //accesseur
        return this.movedPiece.getPiecePosition();
    }
	
    /**
     * 
     * @return false car classe mere de tout les mouvements
     */
    
	public boolean isAttack() {
		
		return false;
	}
	
	/**
	 * 
	 * @return null car classe mere de tout les mouvements
	 */
	
	public Piece getAttackedPiece() {
		
		return null;
	}
	
	/**
	 * 
	 * @return false car classe mere de tout les mouvements
	 */
	
	public boolean isNullMove() {
		
		return false;
	}
	
	/**
	 * 
	 * @return false car classe mere de tout les mouvements
	 */
	
	public boolean isExchange() {
		
		return false;
	}
	
	/**
	 * 
	 * @return null car classe mere de tout les mouvements
	 */
	
	public Piece getOppositePiece() {
		
		return null;
	}
	
	public Piece getMovedPiece() { //accesseur
		
		return this.movedPiece;
	}
	
	public Board undo() {
        
		final Board.Builder builder = new Builder();
        for (final Piece piece : this.board.getAllPieces()) {
            builder.setPiece(piece);
        }
        builder.setMoveMaker(this.board.currentPlayer().getTeam());
        return builder.build();
    }
	
	/**
	 * 
	 * @return un nouveau plateau Board ou les pieces sont identiques sauf pour la piece qui a bouge
	 */
	
    public Board execute() { 
    	
		final Builder builder = new Builder(); //pour chaque deplacement, on recrée un nouveau plateau
		
		for(final Piece piece : this.board.currentPlayer().getActivePieces()) { //on remet les pieces du joueur qui a la permission de jouer
			
	        if(!this.movedPiece.equals(piece)) { // on remet toutes les pieces sauf celle qui bouge
	        	
	        	builder.setPiece(piece);
	        }
		}
		
		for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) { // on remet les pieces adverses 
			
			builder.setPiece(piece);
		}
		
		//déplacement de la piece qui bouge
		builder.setPiece(this.movedPiece.movePiece(this));
		builder.setMoveMaker(this.board.currentPlayer().getOpponent().getTeam());
		
		return builder.build();
	}
		
	public static final class MajorMove extends Move {

		public MajorMove(final Board board, final Piece movedPiece, final int destinationCoordinate) {
			
			super(board, movedPiece, destinationCoordinate);
		}
		
		@Override
        public boolean equals(final Object other) {
            
			return this == other || other instanceof MajorMove && super.equals(other);
        }
		
		
		@Override
        public String toString() {
            
			return movedPiece.getPieceType().toString() + " " + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate); 
        }
	}
	
	public static final class ExchangeMove extends Move {
		
		/* classe censee pouvoir faire echanger les pieces mais abandon */
		
		final Piece oppositePiece;
		
		public ExchangeMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece oppositePiece) {
			
			super(board, movedPiece, destinationCoordinate);
			this.oppositePiece=oppositePiece;
		}
		
		@Override
		public int hashCode() {
			
			return this.oppositePiece.hashCode() + super.hashCode();
		}
		
		@Override
		public boolean equals(final Object other) {
			
			if(this == other) {
				
				return true;
			}
			
			if(!(other instanceof AttackMove)) {
				
				return false;
			}
			final ExchangeMove otherExchangeMove = (ExchangeMove) other;
			return super.equals(otherExchangeMove) && getOppositePiece().equals(otherExchangeMove.getOppositePiece()); 
		}
	
		
		@Override
		public boolean isExchange() {
			
			return true;
		}
		
		@Override
		public Piece getOppositePiece() {
			
			return this.oppositePiece;
		}
		
		@Override
        public String toString() {
            
			return movedPiece.getPieceType().toString() + " " + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate); 
        }
		
		@Override
		public Board execute() { 
	    	
			final Builder builder = new Builder(); //pour chaque deplacement, on recrée un nouveau plateau
			
			for(final Piece piece : this.board.currentPlayer().getActivePieces()) { //on remet les pieces du joueur qui a la permission de jouer
				
		        if(!this.movedPiece.equals(piece) || !this.oppositePiece.equals(piece)) { // on remet toutes les pieces sauf celle qui bouge
		        	
		        	builder.setPiece(piece);
		        }
			}
			
			for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) { // on remet les pieces adverses 
				
				builder.setPiece(piece);
			}
			
			//déplacement de la piece qui bouge
			builder.setPiece(this.movedPiece.movePiece(this));
			builder.setPiece(this.oppositePiece.movePiece(this));
			builder.setMoveMaker(Team.RED);
			return builder.build();
		}
	}
	
	/**
	 * @author goffinet nicolas
	 * Classe representant un mouvement d'attaque 
	 * 
	 */
	
	public static final class AttackMove extends Move {
		
		Piece attackedPiece;

		public AttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece) {
			
			super(board, movedPiece, destinationCoordinate);
			this.attackedPiece=attackedPiece;
		}
		
		@Override
		public int hashCode() {
			
			return this.attackedPiece.hashCode() + super.hashCode();
		}
		
		@Override
		public boolean equals(final Object other) {
			
			if(this == other) {
				
				return true;
			}
			
			if(!(other instanceof AttackMove)) {
				
				return false;
			}
			final AttackMove otherAttackMove = (AttackMove) other;
			return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece()); 
		}
		
		@Override
		public boolean isAttack() {
			
			return true;
		}
		
		@Override
		public Piece getAttackedPiece() {
			
			return this.attackedPiece;
		}
		
		@Override
        public String toString() {
            
			return movedPiece.getPieceType().toString() + " " + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate); 
        }
		
		@Override
		public Board execute() { 
			
		    attackedPiece.isRevealed = true; //revelation de la piece attaquee
		    this.movedPiece.isRevealed = true; //revelation de la piece qui attaque
			
		  //confrontation rang identique, on recree un nouveau plateau mais sans la piece attaquee et sans la piece qui attaque
    	    if(attackedPiece.getPieceRank() == this.movedPiece.getPieceRank()) { 
	    	    
    	    	 System.out.println("The attacked piece's rank is " + attackedPiece.getPieceRank() + "  and the attack piece's rank is " + this.movedPiece.getPieceRank());
    	    	
			    final Builder builder = new Builder(); //pour chaque deplacement, on recrée un nouveau plateau
			
			    for(final Piece piece : this.board.currentPlayer().getActivePieces()) {

		            if(!this.movedPiece.equals(piece)) { // on remplace toutes les pieces du joueur sauf celle qui va bouger
		        	
		        	    builder.setPiece(piece);
		            }
			    }
			
			    for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
				
			    	if(!this.attackedPiece.equals(piece)) { // on remplace toutes les pieces du joueur sauf celle qui va bouger
			        	
		        	    builder.setPiece(piece);
		            }
			    }
			    
			    builder.setMoveMaker(this.board.currentPlayer().getOpponent().getTeam());
				
			    return builder.build();
			    
			} else {
			    
				//recreation du plateau comme avec le execute() de la classe Move
				final Builder builder = new Builder(); //pour chaque deplacement, on recrée un nouveau plateau
				
				for(final Piece piece : this.board.currentPlayer().getActivePieces()) {

			        if(!this.movedPiece.equals(piece)) { // on remplace toutes les pieces du joueur sauf celle qui va bouger
			        	
			        	builder.setPiece(piece);
			        }
				}
				
				for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
					
					builder.setPiece(piece);
				}
				
				//cas de la bombe attaquee
				if(attackedPiece.getPieceType().isBomb()) {
				    
					//cas de la bombe attaquee mais avec le demineur comme piece attaquante
				    if(this.movedPiece.getPieceType().isMineSweeper()) {
					
					    System.out.println("The attacked piece's rank is " + attackedPiece.getPieceRank() + "  and the attack piece's rank is " + this.movedPiece.getPieceRank());
					    builder.setPiece(this.movedPiece.movePiece(this));
					    builder.setMoveMaker(this.board.currentPlayer().getOpponent().getTeam());
					
					    return builder.build();
					
					//si la piece qui attaque n'est pas un demineur
				    } else {
				    	
				    	attackedPiece=this.movedPiece;
					    System.out.println("Boom");
					    builder.setMoveMaker(this.board.currentPlayer().getOpponent().getTeam());
					
					return builder.build();
				    }
				
			    }
			    
				//cas de l'espion qui attaque le marechal
			    else if(this.movedPiece.getPieceType().isSpy() && attackedPiece.getPieceType().isMarshal()) {
				
			    	System.out.println("The attacked piece's rank is " + attackedPiece.getPieceRank() + "  and the attack piece's rank is " + this.movedPiece.getPieceRank());
				    builder.setPiece(this.movedPiece.movePiece(this));
				    builder.setMoveMaker(this.board.currentPlayer().getOpponent().getTeam());
				
				    return builder.build();
			
			    } else {
				    //cas de base ou la piece qui attaque a un rang plus fort que celle qui se fait attaquee
			        if(attackedPiece.getPieceRank() < this.movedPiece.getPieceRank()) {
					
			        	System.out.println("The attacked piece's rank is " + attackedPiece.getPieceRank() + "  and the attack piece's rank is " + this.movedPiece.getPieceRank());
					    builder.setPiece(this.movedPiece.movePiece(this));
					    builder.setMoveMaker(this.board.currentPlayer().getOpponent().getTeam());
					
					    return builder.build();
				    
					//cas de base ou la piece qui attaque a un rang plus faible que celle qui se fait attaquee
				    } else {
				    	attackedPiece=this.movedPiece;
				    	System.out.println("The attacked piece's rank is " + attackedPiece.getPieceRank() + "  and the attack piece's rank is " + this.movedPiece.getPieceRank());
					    builder.setMoveMaker(this.board.currentPlayer().getOpponent().getTeam());
					
					    return builder.build();
				    }
				}
			}
		}
		
	}
	
	/**
	 * 
	 * @author goffinet nicolas
	 * Classe representant un mouvement nul donc aucune piece bouge
	 *
	 */
    
    public static final class nullMove extends Move {
    	
    	
    	public nullMove() {
    		
    		super(null, 65);
    	}
    	
    	@Override
    	public boolean isNullMove() {
    		
    		return true;
    	}
    	
    	/**
    	 * @throws RuntimeException car on ne peut pas executer un mouvement nul
    	 */
    	
    	@Override
    	public Board execute() {
    		
    		throw new RuntimeException("Cannot execute the null move !");
    	}
    	
    	@Override
    	public int getCurrentCoordinate() {
    		
    		return -1;
    	}
    	
    	@Override
        public String toString() {
            return "null Move";
        }
    }
    
    /**
     * 
     * @author goffinet nicolas
     * Classe qui va servir a faire bouger les pieces
     *
     */
    public static class MoveFactory {
    	
    	/**
    	 * @throws RuntimeException, pas instanciable
    	 */
    	
    	private MoveFactory() {
    		
    		throw new RuntimeException("Not instantiable");
    	}
    	
    	public static Move getNullMove() { //accesseur
            
    		return NULL_MOVE;
        }
    	
    	/**
    	 * 
    	 * @param board un plateau
    	 * @param currentCoordinate la coordonnee de la piece avant de bouger
    	 * @param destinationCoordinate la coordonne de la destination
    	 * @return un objet Move, on va chercher dans tout les mouvements autorisés du plateau si la coordonnee de la piece avant de bouger correspond a celle entree en argument
    	 * et si la coordonne de la destination correspond a celle entree en argument
    	 */
    	
    	public static Move createMove(final Board board, final int currentCoordinate, final int destinationCoordinate) {
    		
    		for(final Move move : board.getAllLegalMoves()) {
    			
    			if(move.getCurrentCoordinate() == currentCoordinate && move.getDestinationCoordinate() == destinationCoordinate) {
    				
    				return move;
    			}
    		}
    		return NULL_MOVE; //si on ne trouve pas d'objet Move dans la boucle
    	}
    }

}
