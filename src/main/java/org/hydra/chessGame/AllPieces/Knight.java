package org.hydra.chessGame.AllPieces;


import org.hydra.chessGame.Piece;
import org.hydra.chessGame.utils.PieceColor;
import org.hydra.chessGame.utils.Position;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Knight extends Piece {

  public Knight(PieceColor color, Position position) {
    super(color, position);
  }

    @Override
    public List<Position> allPossibleMoves(Position curr ,Piece[][] board) {
        // knight move in L shape

        int x = curr.getRow();
        int y = curr.getColumn();

        List<Position> possibleMoves = new ArrayList<>(List.of(
                new Position(x - 2, y - 1),
                new Position(x - 2, y + 1),
                new Position(x - 1, y - 2),
                new Position(x - 1, y + 2),
                new Position(x + 1, y - 2),
                new Position(x + 1, y + 2),
                new Position(x + 2, y - 1),
                new Position(x + 2, y + 1)
        ));

        List<Position> copyMoves = List.copyOf(possibleMoves);

        for (Position move : copyMoves) {
            if (!isValidMove(move, board)) {
                possibleMoves.remove(move);
            }
        }

        return possibleMoves;
    }

    @Override
  public boolean isValidMove(Position newPosition, Piece[][] board) {
        //    check whether new position is out of bound
        if(!this.InsideChessBoard(newPosition.getRow(), newPosition.getColumn())){
            return false;
        }
    //knight move in L shape

    if (this.sameColourEntityPresent(newPosition, board)) {
      return false;
    }

    int row_diff = abs(newPosition.getRow() - this.getPosition().getRow());
    int col_diff = abs(
      newPosition.getColumn() - this.getPosition().getColumn()
    );

    if ((row_diff == 2 && col_diff == 1) || (row_diff == 1 && col_diff == 2)) {
      return true;
    }

    return false;
  }
}
