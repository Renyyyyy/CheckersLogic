package org.example;

public interface Piece {
    PieceType getPieceType();
    Color getColor();
    boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol);
    boolean performCapture(Board board, int fromRow, int fromCol, int toRow, int toCol) ;
}
