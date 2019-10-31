package umons.projet.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.google.common.primitives.Ints;

import umons.projet.game.Board.Move;
import umons.projet.game.Pieces.Piece;
import umons.projet.gui.Table.MoveLog;

public class TakenPiecesPanel extends JPanel{
	
	private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
	private final Color PANEL_COLOR = Color.decode("0xFDFE6");
	private final Dimension TAKEN_PIECE_DIMENSION = new Dimension(100, 200);
	
	private final JPanel northPanel;
	private final JPanel southPanel;
	
	public TakenPiecesPanel() {
		
		super(new BorderLayout());
		setBackground(Color.decode("0xFDF5E6"));
		setBorder(PANEL_BORDER);
		this.northPanel = new JPanel(new GridLayout(8, 2));
		this.southPanel = new JPanel(new GridLayout(8, 2));
		this.northPanel.setBackground(PANEL_COLOR);
		this.southPanel.setBackground(PANEL_COLOR);
		add(this.northPanel, BorderLayout.NORTH);
		add(this.southPanel, BorderLayout.SOUTH);
		setPreferredSize(TAKEN_PIECE_DIMENSION);
	}
	
	public void redo(final MoveLog moveLog) {
		
		southPanel.removeAll();
		northPanel.removeAll();
		
		List<Piece> redTakenPieces = new ArrayList<>();
		List<Piece> blueTakenPieces = new ArrayList<>();
		
		for(final Move move : moveLog.getMoves()) {
			
			if(move.isAttack()) {
				
				final Piece movedPiece = move.getMovedPiece();
				final Piece takenPiece = move.getAttackedPiece();
				
				if(takenPiece.getPieceTeam().isRed()) {
					
					redTakenPieces.add(takenPiece);
				
				} else if(takenPiece.getPieceTeam().isBlue()) {
					
					blueTakenPieces.add(takenPiece);
			
				} else if(movedPiece.getPieceRank()==takenPiece.getPieceRank() && takenPiece.getPieceTeam().isBlue()) {
					
					redTakenPieces.add(movedPiece);
					blueTakenPieces.add(takenPiece);
				
                } else if(movedPiece.getPieceRank()==takenPiece.getPieceRank() && takenPiece.getPieceTeam().isRed()) {
					
					redTakenPieces.add(takenPiece);
					blueTakenPieces.add(movedPiece);
			
					
				} else {
					
					throw new RuntimeException("Should not reach here !");
				}
			}
		}
		Collections.sort(redTakenPieces, new Comparator<Piece>() {
			
			@Override
			public int compare(Piece o1, Piece o2) {
				
				return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
			}
		});
		
        Collections.sort(blueTakenPieces, new Comparator<Piece>() {
			
			@Override
			public int compare(Piece o1, Piece o2) {
				
				return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
			}
		});
		
		for(final Piece takenPiece : redTakenPieces) {
			
			try {
				
				final BufferedImage image = ImageIO.read(new File("assets/pieces/plain/" + takenPiece.getPieceTeam().toString().substring(0,1) + "" +
				takenPiece.toString() + ".gif"));
				final ImageIcon icon = new ImageIcon(image);
				final JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(
                        icon.getIconWidth() - 15, icon.getIconWidth() - 15, Image.SCALE_SMOOTH)));
				this.southPanel.add(imageLabel);
			} catch (final IOException e) {
				
				e.printStackTrace();
			}
		}
		
        for(final Piece takenPiece : blueTakenPieces) {
			
			try {
			
				final BufferedImage image = ImageIO.read(new File("assets/pieces/plain/" + takenPiece.getPieceTeam().toString().substring(0,1) + "" +
				takenPiece.toString() +".gif"));
				final ImageIcon icon = new ImageIcon(image);
				final JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(
                        icon.getIconWidth() - 15, icon.getIconWidth() - 15, Image.SCALE_SMOOTH)));
				this.northPanel.add(imageLabel);
			} catch (final IOException e) {
				
				e.printStackTrace();
			}
		}
        
        validate();
	}
}
