package org.hydra.chessGame.AllPieces;



import org.hydra.chessGame.Piece;
import org.hydra.chessGame.utils.PieceColor;
import org.hydra.chessGame.utils.Position;

import java.util.List;

import static java.lang.Math.abs;

public class Queen extends Piece {

  public Queen(PieceColor color, Position position) {
    super(color, position);
  }

  @Override
  public List<Position> allPossibleMoves(Position curr, Piece[][] board) {
    //queen move like rook and bishop

    int x = curr.getRow();
    int y = curr.getColumn();
    Piece Bishop = new Bishop(this.getColor(), this.getPosition());
    Piece Rook = new Rook(this.getColor(), this.getPosition());

    List<Position> possibleMoves = Rook.allPossibleMoves(curr, board);
    possibleMoves.addAll(Bishop.allPossibleMoves(curr, board));

    return possibleMoves;
  }

  @Override
  public boolean isValidMove(Position newPosition, Piece[][] board) {
    //    check whether new position is out of bound
    if(!this.InsideChessBoard(newPosition.getRow(), newPosition.getColumn())){
      return false;
    }
    //Queen move like rook and bishop

    if (this.sameColourEntityPresent(newPosition, board)) {
      return false;
    }

    int row_diff = abs(newPosition.getRow() - this.getPosition().getRow());
    int col_diff = abs(
      newPosition.getColumn() - this.getPosition().getColumn()
    );

    Piece Bishop = new Bishop(this.getColor(), this.getPosition());
    Piece Rook = new Rook(this.getColor(), this.getPosition());

    if (row_diff == 0 || col_diff == 0) {
      return Rook.isValidMove(newPosition, board);
    } else if (row_diff == col_diff) {
      return Bishop.isValidMove(newPosition, board);
    }

    return false;
  }
}
