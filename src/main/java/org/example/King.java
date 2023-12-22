package org.example;

public class King implements Piece {
    private final Color color;

    public King(Color color) {
        this.color = color;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);
        if (rowDiff != colDiff) return false;  // Move must be diagonal

        int rowDirection = (toRow - fromRow) / rowDiff;  // +1 or -1
        int colDirection = (toCol - fromCol) / colDiff;  // +1 or -1

        // Check each square on the path for a potential capture
        for (int r = fromRow + rowDirection, c = fromCol + colDirection; r != toRow; r += rowDirection, c += colDirection) {
            Piece piece = board.getPiece(r, c);
            if (piece != null) {
                // If it's the same color, the move is blocked
                if (piece.getColor() == this.color) return false;

                // If a piece has already been captured in this move, it's invalid because only one capture is allowed at a time
                if (board.getPiece(toRow, toCol) == null) {
                    board.removePiece(r, c);  // Remove the captured piece
                    return true;  // Capture is valid, return true immediately
                } else {
                    return false;  // A piece is blocking the path to the destination
                }
            }
        }

        // No captures, but the path is clear for the move
        return board.getPiece(toRow, toCol) == null;  // Ensure the destination square is empty
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