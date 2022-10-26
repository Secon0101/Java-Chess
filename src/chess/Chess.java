package chess;

import chess.piece.*;

public class Chess {
    private final Piece[][] board = new Piece[8][8];
    
    
    public Chess() {
        for (int i = 0; i < 8; i++)
            board[6][i] = new Pawn(Team.Black);
        for (int i = 0; i < 8; i++)
            board[1][i] = new Pawn(Team.White);
    }
    
    
    public void play() {
        
    }
    
    
    public Piece getPiece(int x, int y) { return board[y][x]; }
    public Piece getPiece(Position pos) { return board[pos.y][pos.x]; }
    
    public void move(Position from, Position to) {
        
    }
    
    
    // debug
    public static void main(String[] args) {
        Chess chess = new Chess();
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                if (chess.getPiece(x, y) != null) {
                    System.out.print(chess.getPiece(x, y));
                }
                System.out.print("\t");
            }
            System.out.println();
        }
    }
}
