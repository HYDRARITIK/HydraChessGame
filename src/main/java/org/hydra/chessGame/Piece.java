package org.hydra.chessGame;


import org.hydra.chessGame.utils.PieceColor;
import org.hydra.chessGame.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Piece {
    private PieceColor color;
    private Position position;

    public PieceColor getColor() {
        return color;
    }

    public void setColor(PieceColor color) {
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Piece(PieceColor color, Position position) {
        this.color = color;
        this.position = position;
    }

    public boolean sameColourEntityPresent(Position pos, Piece[][] board) {

        if(board[pos.getRow()][pos.getColumn()]==null)return  false;
        return board[pos.getRow()][pos.getColumn()].getColor() == this.getColor();
    }

    public boolean anyEntityPresent(Position pos, Piece[][] board) {
        return board[pos.getRow()][pos.getColumn()] != null;
    }

    public boolean InsideChessBoard(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public boolean isValidMove(Position newPosition , Piece[][] board ){


        return true;
    }

    public List<Position> allPossibleMoves(Position curr , Piece[][] board){
        return new ArrayList<>();
    }

}
