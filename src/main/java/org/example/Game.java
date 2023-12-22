package org.example;

import java.util.Scanner;

public class Game {
    private Board board;
    private Player[] players;
    private boolean gameOver;

    public Board getBoard() {
        return board;
    }

    public Game() {
        board = new Board();
        players = new Player[]{new Player('b', true), new Player('w', false)};
        gameOver = false;
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        boolean blackPiecesLeft = true;
        boolean whitePiecesLeft = true;

        while (!gameOver) {
            printBoard();

            char playerType = currentPlayer().getType();
            System.out.println("Player " + playerType + "'s turn.");

            System.out.println("Enter the coordinates of the piece you want to move (e.g., a2): ");
            String fromInput = scanner.next();
            int fromCol = fromInput.charAt(0) - 'a';
            int fromRow = Character.getNumericValue(fromInput.charAt(1)) - 1;

            System.out.println("Enter the coordinates to move the piece to (e.g., b3): ");
            String toInput = scanner.next();
            int toCol = toInput.charAt(0) - 'a';
            int toRow = Character.getNumericValue(toInput.charAt(1)) - 1;

            if (board.getPiece(fromRow, fromCol).isValidMove(board, fromRow, fromCol, toRow, toCol)) {
                makeMove(fromRow, fromCol, toRow, toCol);

                blackPiecesLeft = false;
                whitePiecesLeft = false;

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board.getPiece(i, j) != null) {
                            char pieceType = (board.getPiece(i, j).getColor() == Color.WHITE ? 'w' : 'b');
                            if (pieceType == 'b' || pieceType == 'B') {
                                blackPiecesLeft = true;
                            } else if (pieceType == 'w' || pieceType == 'W') {
                                whitePiecesLeft = true;
                            }
                        }
                    }
                }

                if (!blackPiecesLeft || !whitePiecesLeft) {
                    gameOver = true;
                }

                switchPlayerTurn();
            } else {
                System.out.println("Invalid move. Try again.");
            }

            if (gameOver) {
                break;
            }
        }

        printBoard();
        System.out.println("Game Over!");
        if (!blackPiecesLeft) {
            System.out.println("White player wins!");
        } else {
            System.out.println("Black player wins!");
        }
        scanner.close();
    }

    private void printBoard() {
        System.out.println("  a b c d e f g h");
        for (int i = 0; i < 8; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(i, j);
                if (piece == null) {
                    System.out.print("  ");
                } else {
                    System.out.print(piece.getColor() + " ");
                }
            }
            System.out.println(i + 1);
        }
        System.out.println("  a b c d e f g h");
    }

    private Player currentPlayer() {
        for (Player player : players) {
            if (player.isTurn()) {
                return player;
            }
        }
        return null;
    }

    private void switchPlayerTurn() {
        for (Player player : players) {
            player.setTurn(!player.isTurn());
        }
    }


    private void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
        Piece piece = board.getPiece(fromRow, fromCol);
        Piece toPiece = board.getPiece(toRow, toCol);

        // Проверяем, что на конечной позиции нет фигуры (если есть, она будет съедена)
        if (toPiece != null) {
            int captureRow = (fromRow + toRow) / 2;
            int captureCol = (fromCol + toCol) / 2;
            board.setPiece(captureRow, captureCol, null); // Убираем съеденную шашку с доски
        }

        board.setPiece(fromRow, fromCol, null);
        board.setPiece(toRow, toCol, piece);
    }

}
