package org.example;

public class Checker implements Piece {
    private final Color color;

    public Checker(Color color) {
        this.color = color;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CHECKER;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        // Basic diagonal movement check
        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);
        if (rowDiff != colDiff) return false;  // Must move diagonally

        // Direction check based on color
        if ((color == Color.BLACK && toRow <= fromRow) || (color == Color.WHITE && toRow >= fromRow)) {
            return false;
        }

        // Capture logic
        // Capture logic
        if (rowDiff == 2) {
            int middleRow = (fromRow + toRow) / 2;
            int middleCol = (fromCol + toCol) / 2;
            Piece middlePiece = board.getPiece(middleRow, middleCol);
            if (middlePiece == null || middlePiece.getColor() == this.color) {
                return false;  // No opponent piece to capture
            }
            if (board.getPiece(toRow, toCol) == null) {  // Landing square must be empty
                board.removePiece(middleRow, middleCol);  // Remove the captured piece
                return true;
            }
            return false;
        }

        // Regular move
        return rowDiff == 1 && board.getPiece(toRow, toCol) == null;
    }
    public boolean performCapture(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        // Assuming there's a method in Board to remove a piece: board.removePiece(row, col);
        // And a method to check for possible captures: board.hasCaptureAvailable(row, col, this.color);

        int midRow = (fromRow + toRow) / 2;
        int midCol = (fromCol + toCol) / 2;
        board.removePiece(midRow, midCol);  // Remove the captured piece

        return board.hasCaptureAvailable(toRow, toCol, this.getColor());  // Check if more captures are possible
    }





}
