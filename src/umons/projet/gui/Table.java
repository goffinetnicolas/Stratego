package umons.projet.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import com.google.common.collect.Lists;

import umons.projet.game.Board.Board;
import umons.projet.game.Board.BoardUtils;
import umons.projet.game.Board.Move;
import umons.projet.game.Board.Square;
import umons.projet.game.Pieces.Piece;
import umons.projet.game.Player.MoveTransition;
import umons.projet.game.Player.AI.MoveStrategy;
import umons.projet.game.Player.AI.RandomMove;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;
import static javax.swing.SwingUtilities.*;

/**
 * 
 * @author goffinet nicolas
 * Classe principale de l'interface graphique
 *
 */

public class Table extends Observable {
	
	private final JFrame gameFrame; 
	private final BoardPanel boardPanel;
	private static Board strategoBoard;
	private final MoveLog moveLog;
	private static GameSetup gameSetup;
	
	private Square sourceSquare;
	private Square destinationSquare;
	private Piece humanMovedPiece;
	private BoardDirection boardDirection;
	
	private Move computerMove;
	public boolean setupMode;
	
	private TakenPiecesPanel takenPiecesPanel;
	private final GameHistoryPanel gameHistoryPanel;
	private final DebugPanel debugPanel;
	
	private boolean highlightLegalMoves;
	
	private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(700, 700);
	private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(500, 450);
	private final static Dimension SQUARE_PANEL_DIMENSION = new Dimension(10, 10);
	
	 private final Color darkGreenSquareColor = Color.decode("#006400");
	 private final Color yellowGreenSquareColor = Color.decode("#ADFF2F");
	 private final Color blueSquareColor = Color.decode("#0000FF");
	 
	 private static String defaultPieceImagesPath = "assets/pieces/plain/";
	 
	 private static final Table INSTANCE = new Table(); 

	
	private Table() {
		
		this.gameFrame = new JFrame("UMons Stratego");
		this.gameFrame.setLayout(new BorderLayout());
		final JMenuBar tableMenuBar = createTableMenuBar();
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		this.strategoBoard = Board.createStandardBoard(); 
		this.gameHistoryPanel = new GameHistoryPanel();
		this.debugPanel = new DebugPanel();
		this.setupMode=false;
		this.takenPiecesPanel = new TakenPiecesPanel(); 
		this.boardPanel = new BoardPanel();
		this.moveLog= new MoveLog();
		this.addObserver(new TableGameAIWatcher());
		this.gameSetup= new GameSetup(this.gameFrame, true);
		this.gameFrame.add(debugPanel, BorderLayout.SOUTH);
		setDefaultLookAndFeelDecorated(true);
		this.boardDirection = BoardDirection.NORMAL;
		this.highlightLegalMoves = false;
		this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
		this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
		center(this.gameFrame);
		this.gameFrame.setVisible(true);
	}
	
	/**
	 * 
	 * @return une instance Table
	 */
	
	public static Table get() {
		
		return INSTANCE;
	}
	
	public void show() {
        Table.get().getMoveLog().clear();
        Table.get().getGameHistoryPanel().redo(strategoBoard, Table.get().getMoveLog());
        Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
        Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
        Table.get().getDebugPanel().redo();
  
    }
	
	private DebugPanel getDebugPanel() { //accesseur
        
		return this.debugPanel;
    }
	
	private GameSetup getGameSetup() { //accesseur
		
		return this.gameSetup;
	}
	
	private boolean getHighlightLegalMoves() { //accesseur
        return this.highlightLegalMoves;
    }
	
	private Board getGameBoard() { //accesseur
		
		return this.strategoBoard;
	}
	
	private static void center(final JFrame frame) { // centrer la fenetre
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final int w = frame.getSize().width;
        final int h = frame.getSize().height;
        final int x = (dim.width - w) / 2;
        final int y = (dim.height - h) / 2;
        frame.setLocation(x, y);
    }
	
   /**
    * 
    * @return un Objet JMenuBar permettant de creer le menu horizontal
    * on ajoute dans cette methode tout les differents menus
    */
	
	private JMenuBar createTableMenuBar() {
		
		final JMenuBar tableMenuBar = new JMenuBar();
		tableMenuBar.add(createFileMenu());
		tableMenuBar.add(createPreferencesMenu());
		tableMenuBar.add(createOptionMenu());
		return tableMenuBar;
	}
	
	/**
	 * 
	 * @return un Objet JMenu qui permet de creer le File Menu
	 */
	
	private JMenu createFileMenu() {
		
		final JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		final JMenuItem openPGN = new JMenuItem("Load PGN file", KeyEvent.VK_O); 
		openPGN.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser();
                int option = chooser.showOpenDialog(Table.get().getGameFrame());
                if (option == JFileChooser.APPROVE_OPTION) {
                    loadPGNFile(chooser.getSelectedFile());
                }
			}
		});
		fileMenu.add(openPGN);
		
		final JMenuItem saveToPGN = new JMenuItem("Save Game in PGN file", KeyEvent.VK_S);
        saveToPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileFilter() {
                    @Override
                    public String getDescription() {
                        return ".pgn";
                    }
                    @Override
                    public boolean accept(final File file) {
                        return file.isDirectory() || file.getName().toLowerCase().endsWith("pgn");
                    }
                });
                final int option = chooser.showSaveDialog(Table.get().getGameFrame());
                if (option == JFileChooser.APPROVE_OPTION) {
                    savePGNFile(chooser.getSelectedFile());
                }
            }
        });
        fileMenu.add(saveToPGN);
		
		final JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});
		fileMenu.add(exitMenuItem);
		return fileMenu;
	}
	
	private static void savePGNFile(final File pgnFile) { // inacheve
        /*try {
            writeGameToPGNFile(pgnFile, Table.get().getMoveLog());  
        }
        catch (final IOException e) {
            e.printStackTrace();
        }*/
    }
	
	private static void loadPGNFile(final File pgnFile) {
        /*try {
            persistPGNFile(pgnFile);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }*/
    }
	
	/**
	 * 
	 * @return un objet JMenu avec les preferences
	 */
	
	private JMenu createPreferencesMenu() {
		
		final JMenu preferencesMenu = new JMenu("Preferences");
		final JMenuItem flipBoardMenuItem = new JMenuItem("FlipBoard");
		flipBoardMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				
				boardDirection = boardDirection.opposite();
				boardPanel.drawBoard(strategoBoard);
			}
		});
		preferencesMenu.add(flipBoardMenuItem);
		preferencesMenu.addSeparator();
		
		final JCheckBoxMenuItem legalMoveHighlighterCheckbox = new JCheckBoxMenuItem("Highlight Legal Moves", false);
		legalMoveHighlighterCheckbox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				highlightLegalMoves = legalMoveHighlighterCheckbox.isSelected();
			}
		});
		preferencesMenu.add(legalMoveHighlighterCheckbox);
		return preferencesMenu;
	}
	
	/**
	 * 
	 * @author goffinet nicolas
	 * permet d'enumerer les types de directions du tableau
	 *
	 */
	
	public enum BoardDirection {
		
		NORMAL {
			
			@Override
			List<SquarePanel> traverse(final List<SquarePanel> boardSquares) {
				
				return boardSquares;
			}
			
			@Override
			BoardDirection opposite() {
				
				return FLIPPED;
			}
		},
		
		FLIPPED {
			
			@Override
			List<SquarePanel> traverse(final List<SquarePanel> boardSquares) {
				
				return Lists.reverse(boardSquares);
		    }
			
			@Override
			BoardDirection opposite() {
				
				return NORMAL;
			}
	    };
		
		/**
		 * 
		 * @param boardSquares une liste contenant des objets SquarePanel
		 * @return une liste contenant les objets SquarePanel mais a l'envers
		 */
		
		abstract List<SquarePanel> traverse(final List<SquarePanel> boardSquares);
		
		/**
		 * 
		 * @return un objet BoardDirection qui correspond au type oppose
		 */
		
	    abstract BoardDirection opposite();
	}
	
	/**
	 * 
	 * @return un objet JMenu contenant le menu des options 
	 */
	
	private JMenu createOptionMenu() {
		
		final JMenu optionsMenu = new JMenu("Option");
		optionsMenu.setMnemonic(KeyEvent.VK_O);
		
		final JMenuItem playerType = new JMenuItem("Player Type", KeyEvent.VK_S);
		playerType.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Table.get().getGameSetup().promptUser();
				Table.get().setupUpdate(Table.get().getGameSetup());
			}
		});
		
		final JMenuItem back = new JMenuItem("Go back to the beginning" + "", KeyEvent.VK_P);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                undoAllMoves();
            }

        });
        
        final JMenuItem resetMenuItem = new JMenuItem("New game" + "", KeyEvent.VK_P);
        resetMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
            	strategoBoard = Board.createStandardBoard();
                Table.get().getMoveLog().clear();
                Table.get().getGameHistoryPanel().redo(strategoBoard, Table.get().getMoveLog());
                Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
                Table.get().getBoardPanel().drawBoard(strategoBoard);
                Table.get().getDebugPanel().redo();
                gameSetup= new GameSetup(getGameFrame(), true);
            }

        });
        optionsMenu.add(back);
        optionsMenu.add(resetMenuItem);
        optionsMenu.addSeparator();
		optionsMenu.add(playerType);
		return optionsMenu;
	}
	
	
	private void  setupUpdate(final GameSetup gameSetup) {
		
		setChanged();
		notifyObservers(gameSetup);
	}
	
    /**
     * permet d'annuler tout les mouvements effectue jusqu'a present
     */

    private void undoAllMoves() {
        for(int i = Table.get().getMoveLog().size() - 1; i >= 0; i--) {
            final Move lastMove = Table.get().getMoveLog().removeMove(Table.get().getMoveLog().size() - 1);
            this.strategoBoard = this.strategoBoard.currentPlayer().unMakeMove(lastMove).getToBoard();
        }
        this.computerMove = null;
        Table.get().getMoveLog().clear();
        Table.get().getGameHistoryPanel().redo(strategoBoard, Table.get().getMoveLog());
        Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
        Table.get().getBoardPanel().drawBoard(strategoBoard);
        Table.get().getDebugPanel().redo();
    }
    
    /**
     * 
     * @author goffinet nicolas
     * Classe interne qui incarne l'IA
     *
     */
	
	private static class TableGameAIWatcher implements Observer {
		
		@Override
		public void update(final Observable o, final Object arg) {
			
			if(Table.get().getGameSetup().isAIPlayer(Table.get().getGameBoard().currentPlayer()) && 
			    !Table.get().getGameBoard().currentPlayer().isFlagCaptured()) {
				
				//cree un ia
				//execute l'ia
				final AIThinkTank thinktank = new AIThinkTank();
				thinktank.execute();
			}
			
			if(Table.get().getGameBoard().currentPlayer().isFlagCaptured()) {
				
				JOptionPane.showMessageDialog(null, "The " + Table.get().getGameBoard().currentPlayer().getTeam().toString() + "'s flag has been captured !");
				strategoBoard = Board.createStandardBoard();
			}
		}
	}
	
	private JFrame getGameFrame() { //accesseur
        
		return this.gameFrame;
    }
	
	public void updateGameBoard(final Board board) { //accesseur 
		
		this.strategoBoard = board;
	}
	
	public void updateComputerMove(final Move move) { //accesseur
		
		this.computerMove = move;
	}
	
	private MoveLog getMoveLog() { //accesseur
		
		return this.moveLog;
	}
	
	private GameHistoryPanel getGameHistoryPanel() { //accesseur
		
		return this.gameHistoryPanel;
	}
	
	private TakenPiecesPanel getTakenPiecesPanel() { //accesseur
		
		return this.takenPiecesPanel;
	}
	
	private BoardPanel getBoardPanel() { //accesseur
		
		return this.boardPanel;
	}
	
	private void moveMadeUpdate(final PlayerType playerType) { //accesseur
		
		setChanged();
		notifyObservers(playerType);
	}
	
	private static class AIThinkTank extends SwingWorker<Move, String> { //accesseur
		
		private AIThinkTank() {
			
			
		}
		
		/**
		 * @return un objet Move aleatoire pour ensuite l'executer 
		 */
		
		@Override
		protected Move doInBackground() throws Exception {
			
			final MoveStrategy random = new RandomMove();
			final Move randomMove = random.execute(Table.get().getGameBoard());
			return randomMove;
		}
		
		@Override
		public void done() {
			
			try {
				
				final Move randomMove = get();
				if(randomMove.isNullMove()) { //condition si joueur n'as plus de pieces mobiles
					
					JOptionPane.showMessageDialog(null, "The " + Table.get().getGameBoard().currentPlayer().getTeam().toString() + 
							"team have no more pieces, so they are eliminated !");
					strategoBoard = Board.createStandardBoard();
			        Table.get().getMoveLog().clear();
			        Table.get().getGameHistoryPanel().redo(strategoBoard, Table.get().getMoveLog());
			        Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
			        Table.get().getBoardPanel().drawBoard(strategoBoard);
			        Table.get().getDebugPanel().redo();
			        gameSetup= new GameSetup(Table.get().getGameFrame(), true);
				}
				
				Table.get().updateComputerMove(randomMove);
				Table.get().updateGameBoard(Table.get().getGameBoard().currentPlayer().makeMove(randomMove).getTransitionBoard());
				Table.get().getMoveLog().addMove(randomMove);
				Table.get().getGameHistoryPanel().redo(Table.get().getGameBoard(), Table.get().getMoveLog());
				Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
				Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
				Table.get().moveMadeUpdate(PlayerType.COMPUTER);
			
			} catch(InterruptedException e) {
				
				e.printStackTrace();
			
			} catch(ExecutionException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	/** 
	 * 
	 * @author goffinet nicolas
	 * Classe qui va construire le plateau graphiquement
	 *
	 */
	
	private class BoardPanel extends JPanel {
		
		final List<SquarePanel> boardSquares;
		BoardPanel() {
			
			super(new GridLayout(10,10));
			this.boardSquares = new ArrayList<>();
			for(int i=0 ; i < 100 ; i++) {
				
				final SquarePanel squarePanel = new SquarePanel(this, i); 			
				this.boardSquares.add(squarePanel);
				add(squarePanel);
			}
			setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			setPreferredSize(BOARD_PANEL_DIMENSION);
			setBackground(Color.decode("#8B4726"));
			validate();
		}
		
		/**
		 * 
		 * @param board un plateau
		 * on construit chaque case
		 */
		
		public void drawBoard(final Board board) {
			
			removeAll();
			for(final SquarePanel squarePanel : boardDirection.traverse(boardSquares)) {
				
				squarePanel.drawSquare(board);
				add(squarePanel);
			}
			validate();
			repaint();
		}
	}
	
	/**
	 * 
	 * @author goffinet nicolas
	 * Classe qui represente l'historique des mouvements effecues
	 *
	 */
	
	public static class MoveLog {
		
		private final List<Move> moves;
		
		MoveLog() {
			
			this.moves = new ArrayList<>();
		}
		
		public List<Move> getMoves() {
			
			return this.moves;
		}
		
		public void addMove(final Move move) {
			
			this.moves.add(move);
		}
		
		public int size() {
			
			return this.moves.size();
		}
		
		public void clear() {
			
			this.moves.clear();
		}
		
		public boolean removeMove(final Move move) {
			
			return this.moves.remove(move);
		}
		
		public Move removeMove(int index) {
			
			return this.moves.remove(index);
		}
	}
	
	enum PlayerType {
		
		HUMAN,
		COMPUTER
	}
	
	/**
	 * 
	 * @author goffinet nicolas
	 * Classe qui va definir chaque case
	 *
	 */
	
    private class SquarePanel extends JPanel {
		
		private final int squareID;
		
		SquarePanel(final BoardPanel boardPanel, final int squareID) {
			
			super(new GridBagLayout());
			this.squareID=squareID;
			setPreferredSize(SQUARE_PANEL_DIMENSION);
			assignSquarePieceIcon(strategoBoard); // on assigne la piece a sa case
			assignSquareColor(); // on colorie les cases
			
			addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(final MouseEvent e) {
					// TODO Auto-generated method stub
					
					//condition de fin de jeu si drapeau capture
					if(BoardUtils.isEndGame(Table.get().getGameBoard())) {
						JOptionPane.showMessageDialog(null, "The " + Table.get().getGameBoard().currentPlayer().getTeam().toString() + "'s flag has been captured !");
						strategoBoard = Board.createStandardBoard();
						Table.get().getMoveLog().clear();
				        Table.get().getGameHistoryPanel().redo(strategoBoard, Table.get().getMoveLog());
				        Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
				        Table.get().getBoardPanel().drawBoard(strategoBoard);
				        Table.get().getDebugPanel().redo();
				        gameSetup= new GameSetup(getGameFrame(), true);
		                }
					
					if(isRightMouseButton(e)) { //rien ne se passe
						sourceSquare = null;
						humanMovedPiece = null;
						destinationSquare = null;
						
					} else if(isLeftMouseButton(e)) { 
						
						//condition d'avoir clique d'abord sur la piece que l'on voulait bouger
						if(sourceSquare == null || strategoBoard.currentPlayer().isFlagCaptured()) {
							
							sourceSquare = strategoBoard.getSquare(squareID);
							humanMovedPiece = sourceSquare.getPiece();
							if(humanMovedPiece == null) {
								sourceSquare = null;
							}
						} else {
							
							//dans ce cas on peut effectuer un mouvement
							destinationSquare = strategoBoard.getSquare(squareID);
							final Move move = Move.MoveFactory.createMove(strategoBoard, //creation du mouvement
									          sourceSquare.getSquareCoordinate(), 
									          destinationSquare.getSquareCoordinate());
							final MoveTransition transition = strategoBoard.currentPlayer().makeMove(move); //transition
							if(transition.getMoveStatus().isDone()) {
								strategoBoard = transition.getTransitionBoard(); 
								moveLog.addMove(move);
							}
							sourceSquare = null;
							destinationSquare = null;
							humanMovedPiece = null;
						}
						SwingUtilities.invokeLater(new Runnable() {
							
							@Override
							public void run() {
								
								gameHistoryPanel.redo(strategoBoard, moveLog);
								takenPiecesPanel.redo(moveLog);
								
								if(gameSetup.isAIPlayer(strategoBoard.currentPlayer())) {
									
									Table.get().moveMadeUpdate(PlayerType.HUMAN);
								}
								boardPanel.drawBoard(strategoBoard);
							} 
						});
					}
				}

				@Override
				public void mouseEntered(final MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(final MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(final MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(final MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			validate();
		}
		
		
		
	public void drawSquare(final Board board) {
			
		assignSquareColor();
		assignSquarePieceIcon(board);
		highlightLegals(board);
		validate();
		repaint();
	}
	
	/**
	 * 
	 * @param board un plateau
	 * cette methode va aller chercher pour chaque piece en jeu l'image .gif correspondante
	 */
		
	private void assignSquarePieceIcon(final Board board) {
            this.removeAll();
            
            if(board.getSquare(this.squareID).isSquareOccupied()) {
                if(board.getSquare(this.squareID).getPiece().getPieceTeam().isRed()) {
                    
                    try{
                        final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath +
                                board.getSquare(this.squareID).getPiece().getPieceTeam().toString().substring(0, 1) +
                                board.getSquare(this.squareID).getPiece().toString() +
                                ".gif"));
                        add(new JLabel(new ImageIcon(image)));
                    } catch(final IOException e) {
                        e.printStackTrace();
                    }
                
                } else if(board.getSquare(this.squareID).getPiece().getPieceTeam().isBlue() && board.getSquare(this.squareID).getPiece().isPieceRevealed()) {
                	
                	try{
                        final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath +
                                board.getSquare(this.squareID).getPiece().getPieceTeam().toString().substring(0, 1) +
                                board.getSquare(this.squareID).getPiece().toString() +
                                ".gif"));
                        add(new JLabel(new ImageIcon(image)));
                    } catch(final IOException e) {
                        e.printStackTrace();
                    }
                	
                } else {
                    
                    try {
                        final BufferedImage image = ImageIO.read(new File("assets/misc/B.gif"));
                        add(new JLabel(new ImageIcon(image)));
                    } catch(final IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
		
	    private void highlightLegals(final Board board) {
			
			if(Table.get().getHighlightLegalMoves()) { 
				
				for(final Move move : pieceLegalMoves(board)) {
					
					if (move.getDestinationCoordinate() == this.squareID) {
					
					    try {
						
						    add(new JLabel(new ImageIcon(ImageIO.read(new File("assets/misc/purple_square.png")))));
					    } catch (Exception e) {
						
						    e.printStackTrace();
					    }    
					}
				}
			}
		}
	    
	    /**
	     * 
	     * @param un plateau board 
	     * @return une collection d'objet Move qui sont autorise a bouger
	     */
		
		private Collection<Move> pieceLegalMoves(final Board board) {
			
			if(humanMovedPiece != null && humanMovedPiece.getPieceTeam() == board.currentPlayer().getTeam()) {
				
				return humanMovedPiece.calculateLegalMoves(board);
			}
			return Collections.emptyList();
		}
		
		/**
		 * permet de colorier le plateau en vert, vert fonce et bleu
		 */
	
	    private void assignSquareColor() { //coloriage plateau 
		
	        if(this.squareID == 42 || //zone du lac en bleu
	           this.squareID == 43 ||
	           this.squareID == 46 ||
	           this.squareID == 47 ||
	           this.squareID == 52 ||
	           this.squareID == 53 ||
	           this.squareID == 56 ||
	           this.squareID == 57) {
	        	
	        	setBackground(blueSquareColor);
	        	
	        } else {
	        	
	        	//Coloriage du plateau en vert mais de façon a ce qu'une case sur deux soit mise en vert clair
	        	
	        	if (BoardUtils.FIRST_ROW.get(this.squareID) ||      
	                    BoardUtils.THIRD_ROW.get(this.squareID) ||
	                    BoardUtils.FIFTH_ROW.get(this.squareID) ||
	                    BoardUtils.SEVENTH_ROW.get(this.squareID) ||
	                    BoardUtils.NINTH_ROW.get(this.squareID)) {
	                    setBackground(this.squareID % 2 == 0 ? darkGreenSquareColor : yellowGreenSquareColor);
	                } else if(BoardUtils.SECOND_ROW.get(this.squareID) ||
	                          BoardUtils.FOURTH_ROW.get(this.squareID) ||
	                          BoardUtils.SIXTH_ROW.get(this.squareID)  ||
	                          BoardUtils.EIGHTH_ROW.get(this.squareID) ||
	                          BoardUtils.TENTH_ROW.get(this.squareID)) {
	                    setBackground(this.squareID % 2 != 0 ? darkGreenSquareColor : yellowGreenSquareColor);;
	            }        
	        }
	    }
	}
}
