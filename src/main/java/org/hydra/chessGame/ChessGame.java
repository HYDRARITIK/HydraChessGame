package org.hydra.chessGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.hydra.chessGame.AllPieces.King;
import org.hydra.chessGame.utils.PieceColor;
import org.hydra.chessGame.utils.Position;

public class ChessGame {



  @Getter
  private ChessBoard board;

  private boolean isWhiteTurn;

  public ChessGame() {
    board = new ChessBoard();
    isWhiteTurn = true;
  }

  public boolean isCorrectPersonMove(Position start) {
    PieceColor color = board
      .getPiece(start.getRow(), start.getColumn())
      .getColor();

    return (
      (isWhiteTurn && color == PieceColor.WHITE) ||
      (!isWhiteTurn && color == PieceColor.BLACK)
    );
  }

  public void movePiece(Position start, Position end) {
    Piece piece = board.getPiece(start.getRow(), start.getColumn());
    if (piece == null) {
      throw new IllegalArgumentException(
        "No piece at the given start position"
      );
    }

    if (!isCorrectPersonMove(start)) {
      throw new IllegalArgumentException("It is not your turn");
    }

    if (!piece.isValidMove(end, board.getBoard())) {
      throw new IllegalArgumentException("Invalid move");
    }

    if (isKingInCheckAfterMove(start, end)) {
      throw new IllegalArgumentException("King is in check");
    }

    board.setPiece(end.getRow(), end.getColumn(), piece);
    board.setPiece(start.getRow(), start.getColumn(), null);

    isWhiteTurn = !isWhiteTurn;
  }

  public boolean isKingInCheck() {
    Position kingPosition = findKingPosition(
      isWhiteTurn ? PieceColor.WHITE : PieceColor.BLACK
    );
    Piece[][] tempBoard = board.getBoard();
    PieceColor opponentColor = isWhiteTurn
      ? PieceColor.BLACK
      : PieceColor.WHITE;

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Piece piece = tempBoard[i][j];
        if (piece != null && piece.getColor() == opponentColor) {
          List<Position> possibleMoves = piece.allPossibleMoves(
            piece.getPosition(),
            tempBoard
          );
          if (possibleMoves.contains(kingPosition)) {
            return true;
          }
        }
      }
    }

    return false;
  }

  public Position findKingPosition(PieceColor color) {
    Position kingPosition = null;

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Piece piece = board.getPiece(i, j);
        if (piece instanceof King && piece.getColor() == color) {
          kingPosition = new Position(i, j);
        }
      }
    }

    return kingPosition;
  }

  public boolean isCheckMate() {
    PieceColor color = isWhiteTurn ? PieceColor.WHITE : PieceColor.BLACK;
    Piece[][] tempBoard = board.getBoard();

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Piece piece = tempBoard[i][j];
        if (piece != null && piece.getColor() == color) {
          List<Position> possibleMoves = piece.allPossibleMoves(
            piece.getPosition(),
            tempBoard
          );
          for (Position move : possibleMoves) {
            if (!isKingInCheckAfterMove(piece.getPosition(), move)) {
              return false;
            }
          }
        }
      }
    }

    return true;
  }

  public boolean isKingInCheckAfterMove(Position start, Position end) {
    Piece[][] tempBoard = board.getBoard();
    Piece startPiece = tempBoard[start.getRow()][start.getColumn()];
    Piece endPiece = tempBoard[end.getRow()][end.getColumn()];

    tempBoard[end.getRow()][end.getColumn()] = startPiece;
    tempBoard[start.getRow()][start.getColumn()] = null;

    Position kingPosition = findKingPosition(startPiece.getColor());
    boolean isKingInCheck = isKingInCheck();

    tempBoard[start.getRow()][start.getColumn()] = startPiece;
    tempBoard[end.getRow()][end.getColumn()] = endPiece;

    return isKingInCheck;
  }

  public void resetGame() {
    this.board = new ChessBoard(); // Re-initialize the board
    this.isWhiteTurn = true; // Reset turn to white
  }

  public PieceColor getCurrentPlayerColor() {
    return isWhiteTurn ? PieceColor.WHITE : PieceColor.BLACK;
  }

  private Position currSelectedPosition;

  public boolean isPieceSelected(Position pos) {
    return currSelectedPosition != null && currSelectedPosition.equals(pos);
  }

  public boolean handleSquareSelection(int r, int c, Color initialColor,component[][] chessBoard) {
    // if square color is green than move the piece

      System.out.println("pahle color tha "+initialColor);

    if (initialColor == Color.GREEN) {
      //move the piece
        System.out.println("moving the piece");
        movePiece(currSelectedPosition, new Position(r, c));
       currSelectedPosition = null;
//        new ChessGui.refreshBoard();
//      resetColor
      resetBackGround(chessBoard);
      return true;
  } else {
      // Select the piece
      Piece piece = board.getPiece(r, c);
      if (isCorrectPersonMove(new Position(r, c))) {
          currSelectedPosition = new Position(r, c);
          higlightLegalMoves(r, c,initialColor);
          return true;
      }

      return false;
  }
    
  

  }
  public void resetBackGround(component[][] chessBoard) {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if ((i + j) % 2 == 0) {
          chessBoard[i][j].setBackground(Color.WHITE);
        } else {
          chessBoard[i][j].setBackground(Color.BLACK);
        }
      }
    }
  }


  public List<Position> higlightLegalMoves(int r, int c,Color initialColor) {
    System.out.println("row->" + r + "---col--" + c);
    Piece piece = board.getPiece(r, c);
    if (piece == null) return new ArrayList<>();
    List<Position> legalMoves = piece.allPossibleMoves(
      piece.getPosition(),
      board.getBoard()
    );
    //        for (Position move : legalMoves) {
    //            board.getSquare(move.getRow(), move.getColumn()).setHighlighted(true);
    //        }

    return legalMoves;
  }

//  public void handleSquareClick(int r, int c,Component[][] chessBoard) {
//    boolean isMoveMade = handleSquareSelection(r, c,);
//  }

  public void checkGameStatus() {
    if (isCheckMate()) {
      System.out.println(" game is end");
      if (isWhiteTurn) {
        System.out.println("black won");
      } else {
        System.out.println("white won");
      }
    }
  }
}
