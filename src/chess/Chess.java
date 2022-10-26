package chess;

import chess.piece.*;

public class Chess {
    private Piece[][] board = new Piece[8][8];
    
    
    public Chess() {
        for (int i = 0; i < 8; i++)
            board[1][i] = new Pawn(Team.Black);
    }
    
    
    public void play() {
        
    }
    
    
    public void getMovement(Position piecePosition) {
        
    }
    
    public void move(Position from, Position to) {
        
    }
}
