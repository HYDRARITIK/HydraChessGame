package org.hydra.chessGame.AllPieces;


import org.hydra.chessGame.Piece;
import org.hydra.chessGame.utils.PieceColor;
import org.hydra.chessGame.utils.Position;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class King extends Piece {

  public King(PieceColor color, Position position) {
    super(color, position);
  }

  private boolean isLeftCastle = false;
  private boolean isRightCastle = false;

  public boolean isLeftCastle() {
    return isLeftCastle;
  }

  public boolean isRightCastle() {
    return isRightCastle;
  }

  public void setLeftCastle(boolean leftCastle) {
    isLeftCastle = leftCastle;
  }

  

  public void setRightCastle(boolean rightCastle) {
    isRightCastle = rightCastle;
  }

  @Override
  public List<Position> allPossibleMoves(Position curr , Piece[][] board) {
    // king move in any direction by 1 step
    int x = this.getPosition().getRow();
    int y = this.getPosition().getColumn();

    List<Position> possibleMoves = new ArrayList<>(List.of(
            new Position(x - 1, y - 1),
            new Position(x - 1, y),
            new Position(x - 1, y + 1),
            new Position(x, y - 1),
            new Position(x, y + 1),
            new Position(x + 1, y - 1),
            new Position(x + 1, y),
        new Position(x + 1, y + 1)
            ,
            new Position(x, y + 2),
            new Position(x, y - 2)
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
    int row_diff = newPosition.getRow() - this.getPosition().getRow();
    int col_diff = newPosition.getColumn() - this.getPosition().getColumn();

    //king move only 1 step in any direction

    if (this.sameColourEntityPresent(newPosition, board)) {
      return false;
    }

    if (abs(row_diff) <= 1 && abs(col_diff) <= 1) {
      return true;
    }

    //castling

    if (isLeftCastle) {
      if (row_diff == 0 && col_diff == -2 && newPosition.getColumn() == 2) {
        return true;
      }
    }
    
    if (isRightCastle) {
      if (row_diff == 0 && col_diff == 2 && newPosition.getColumn() == 6) {
        return true;
      }
    }



    return false;
  }
}
