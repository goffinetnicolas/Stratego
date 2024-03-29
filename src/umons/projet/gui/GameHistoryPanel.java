package umons.projet.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import umons.projet.game.Board.Board;
import umons.projet.game.Board.Move;
import umons.projet.gui.Table.MoveLog;

/**
 * 
 * @author goffinet nicolas
 * Classe de l'interface graphique pour l'historique des mouvements de pieces
 *
 */

class GameHistoryPanel extends JPanel {

    private final DataModel model;
    private final JScrollPane scrollPane;
    private static final Dimension HISTORY_PANEL_DIMENSION = new Dimension(100, 40);

    GameHistoryPanel() {
        this.setLayout(new BorderLayout());
        this.model = new DataModel();
        final JTable table = new JTable(model);
        table.setRowHeight(15);
        this.scrollPane = new JScrollPane(table);
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.setPreferredSize(HISTORY_PANEL_DIMENSION);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    void redo(final Board board, 
              final MoveLog moveHistory) {
        int currentRow = 0;
        this.model.clear();
        for (final Move move : moveHistory.getMoves()) {
            final String moveText = move.toString();
            if (move.getMovedPiece().getPieceTeam().isRed()) {
                this.model.setValueAt(moveText, currentRow, 0);
            }
            else if (move.getMovedPiece().getPieceTeam().isBlue()) {
                this.model.setValueAt(moveText, currentRow, 1);
                currentRow++;
            }
        }

        if(moveHistory.getMoves().size() > 0) {
            final Move lastMove = moveHistory.getMoves().get(moveHistory.size() - 1);
            final String moveText = lastMove.toString();

            if (lastMove.getMovedPiece().getPieceTeam().isRed()) {
                this.model.setValueAt(moveText, currentRow, 0);       
            }
            else if (lastMove.getMovedPiece().getPieceTeam().isBlue()) {
                this.model.setValueAt(moveText, currentRow - 1, 1);
            }
        }

        final JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());

    }


    private static class Row {

        private String redMove;
        private String blueMove;

        Row() {
        }

        public String getRedMove() {
            return this.redMove;
        }

        public String getBlueMove() {
            return this.blueMove;
        }

        public void setWhiteMove(final String move) {
            this.redMove = move;
        }

        public void setBlackMove(final String move) {
            this.blueMove = move;
        }

    }

    private static class DataModel extends DefaultTableModel {

        private final List<Row> values;
        private static final String[] NAMES = {"Red", "Blue"};

        DataModel() {
            this.values = new ArrayList<>();
        }

        public void clear() {
            this.values.clear();
            setRowCount(0);
        }

        @Override
        public int getRowCount() {
            if(this.values == null) {
                return 0;
            }
            return this.values.size();
        }

        @Override
        public int getColumnCount() {
            return NAMES.length;
        }

        @Override
        public Object getValueAt(final int row, final int col) {
            final Row currentRow = this.values.get(row);
            if(col == 0) {
                return currentRow.getRedMove();
            } else if (col == 1) {
                return currentRow.getBlueMove();
            }
            return null;
        }

        @Override
        public void setValueAt(final Object aValue,
                               final int row,
                               final int col) {
            final Row currentRow;
            if(this.values.size() <= row) {
                currentRow = new Row();
                this.values.add(currentRow);
            } else {
                currentRow = this.values.get(row);
            }
            if(col == 0) {
                currentRow.setWhiteMove((String) aValue);
                fireTableRowsInserted(row, row);
            } else  if(col == 1) {
                currentRow.setBlackMove((String)aValue);
                fireTableCellUpdated(row, col);
            }
        }

        @Override
        public Class<?> getColumnClass(final int col) {
            return Move.class;
        }

        @Override
        public String getColumnName(final int col) {
            return NAMES[col];
        }
    }
}
