package org.hydra.chessGame.AllPieces;


import org.hydra.chessGame.Piece;
import org.hydra.chessGame.utils.PieceColor;
import org.hydra.chessGame.utils.Position;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Rook extends Piece {

  public Rook(PieceColor color, Position position) {
    super(color, position);
  }

  @Override
  public List<Position> allPossibleMoves(Position curr ,Piece[][] board) {
    // rook move horizontally or vertically

    int x = curr.getRow();
    int y = curr.getColumn();

    List<Position> possibleMoves = new ArrayList<>();

    List<Integer> x_direction = List.of(0, 0, 1, -1);
    List<Integer> y_direction = List.of(1, -1, 0, 0);

    for (int i = 0; i < 4; i++) {
      int temp_x = x + x_direction.get(i);
      int temp_y = y + y_direction.get(i);

      while (this.InsideChessBoard(temp_x, temp_y)) {
        if (board[temp_x][temp_y] == null) {
          possibleMoves.add(new Position(temp_x, temp_y));
        } else {
          if (board[temp_x][temp_y].getColor() != this.getColor()) {
            possibleMoves.add(new Position(temp_x, temp_y));
          }
          break;
        }
        temp_x += x_direction.get(i);
        temp_y += y_direction.get(i);
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
    //rook move horizontally or vertically
    int row_diff = abs(newPosition.getRow() - this.getPosition().getRow());
    int col_diff = abs(
      newPosition.getColumn() - this.getPosition().getColumn()
    );

    if (sameColourEntityPresent(newPosition, board)) {
      return false;
    }

    if (row_diff == 0 || col_diff == 0) {
      int row = this.getPosition().getRow();
      int col = this.getPosition().getColumn();
      int newRow = newPosition.getRow();
      int newCol = newPosition.getColumn();
      int row_diff1 = newRow - row;
      int col_diff1 = newCol - col;
      int row_diff2 = row_diff1 > 0 ? 1 : -1;
      int col_diff2 = col_diff1 > 0 ? 1 : -1;
      if (row_diff == 0) {
        col += col_diff2;
        while (col != newCol) {
          if (board[row][col] != null) {
            //peice is already present in path
            return false;
          }
          col += col_diff2;
        }
      } else {
        row += row_diff2;
        while (row != newRow) {
          if (board[row][col] != null) {
            //peice is already present in path
            return false;
          }
          row += row_diff2;
        }
      }
      return true;
    }

    return false;
  }
}
