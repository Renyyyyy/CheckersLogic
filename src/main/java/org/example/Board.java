package org.example;

public class Board {
    private Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        initializeBoard();
    }

    public boolean hasCaptureAvailable(int row, int col, Color pieceColor) {
        // Get the piece at the given location
        Piece piece = getPiece(row, col);
        if (piece == null || piece.getColor() != pieceColor) {
            return false; // No piece at the location or wrong color
        }

        // Define the potential move directions for captures
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}}; // Diagonal directions

        for (int[] dir : directions) {
            int checkRow = row + dir[0];
            int checkCol = col + dir[1];
            int targetRow = checkRow + dir[0];
            int targetCol = checkCol + dir[1];

            // Check if the target position is within the bounds of the board
            if (isValidPosition(targetRow, targetCol)) {
                Piece adjacentPiece = getPiece(checkRow, checkCol);
                Piece targetPiece = getPiece(targetRow, targetCol);

                // Check if there is an opponent piece in the adjacent square and the target square is empty
                if (adjacentPiece != null && adjacentPiece.getColor() != pieceColor && targetPiece == null) {
                    return true;
                }
            }
        }

        // No captures available
        return false;
    }


    public void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 1 && i < 3) {
                    board[i][j] = new Checker(Color.BLACK); // Black piece
                } else if ((i + j) % 2 == 1 && i > 4) {
                    board[i][j] = new Checker(Color.WHITE); // White piece
                } else {
                    board[i][j] = null; // Empty cell
                }
            }
        }
    }

    public void removePiece(int row, int col) {
        if (isValidPosition(row, col)) {
            board[row][col] = null; // Remove the piece by setting the cell to null
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[row].length;
    }

    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        Piece piece = board[fromRow][fromCol];
        board[fromRow][fromCol] = null;
        board[toRow][toCol] = piece;
    }

    public boolean isValidCell(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
    public void placePiece(int row, int col, Piece piece) {
        if (isValidPosition(row, col)) {
            board[row][col] = piece; // Place the piece at the specified location
        }
    }



}
