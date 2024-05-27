package org.hydra.chessGame.AllPieces;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.List;
import org.hydra.chessGame.Piece;
import org.hydra.chessGame.utils.PieceColor;
import org.hydra.chessGame.utils.Position;

public class Pawn extends Piece {

  public Pawn(PieceColor color, Position position) {
    super(color, position);
  }

  @Override
  public List<Position> allPossibleMoves(Position curr, Piece[][] board) {
    // pawn moves either 1 step or 2 step in forward direction and 1 step in diagonal direction if there is opponent piece

    int x = curr.getRow();
    int y = curr.getColumn();
    System.out.println("Pawn row->" + x + "---col--" + y);
    List<Position> possibleMoves;

    if (this.getColor() == PieceColor.WHITE) {
      possibleMoves =
        new ArrayList<>(
          List.of(
            new Position(x - 1, y),
            new Position(x - 2, y),
            new Position(x - 1, y - 1),
            new Position(x - 1, y + 1)
          )
        );
    } else {
      possibleMoves =
        new ArrayList<>(
          List.of(
            new Position(x + 1, y),
            new Position(x + 2, y),
            new Position(x + 1, y - 1),
            new Position(x + 1, y + 1)
          )
        );
    }

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
    if (!this.InsideChessBoard(newPosition.getRow(), newPosition.getColumn())) {
      return false;
    }

    int row_diff = newPosition.getRow() - this.getPosition().getRow();
    int col_diff = newPosition.getColumn() - this.getPosition().getColumn();

    //pawn move either 1 step or 2 step in forward direction and 1 step in diagonal direction if there

    if (this.sameColourEntityPresent(newPosition, board)) {
      return false;
    }

    if (this.getColor() == PieceColor.WHITE) {
      if (row_diff == -1 && col_diff == 0) {
        //check whether opposite color piece is present at new position

        if (board[newPosition.getRow()][newPosition.getColumn()] != null) {
          return false;
        }
        return true;
      } else if (
        row_diff == -2 && col_diff == 0 && this.getPosition().getRow() == 6
      ) {
        //check whether opposite color piece is present at new position
        if (board[newPosition.getRow()][newPosition.getColumn()] != null) {
          return false;
        }
        return true;
      } else if (
        row_diff == -1 &&
        abs(col_diff) == 1 &&
        board[newPosition.getRow()][newPosition.getColumn()] != null
      ) {
        return true;
      }
    } else {
      if (row_diff == 1 && col_diff == 0) {
        //check whether opposite color piece is present at new position
        if (board[newPosition.getRow()][newPosition.getColumn()] != null) {
          return false;
        }
        return true;
      } else if (
        row_diff == 2 && col_diff == 0 && this.getPosition().getRow() == 1
        ) {
        //check whether opposite color piece is present at new position
        if (board[newPosition.getRow()][newPosition.getColumn()] != null) {
          return false;
        }
        return true;
      } else if (
        row_diff == 1 &&
        abs(col_diff) == 1 &&
        board[newPosition.getRow()][newPosition.getColumn()] != null
      ) {
        return true;
      }
    }

    return false;
  }
}
