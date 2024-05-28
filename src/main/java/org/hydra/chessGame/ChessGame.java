package org.hydra.chessGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.hydra.chessGame.AllPieces.King;
import org.hydra.chessGame.AllPieces.Rook;
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
    Piece piece = board.getPiece(start.getRow(), start.getColumn());

    PieceColor color = null;
    if (piece != null) {
      color = piece.getColor();
    }

    return (
      (isWhiteTurn && color == PieceColor.WHITE) ||
      (!isWhiteTurn && color == PieceColor.BLACK)
    );
  }

  public void movePiece(Position start, Position end) {
    if(start==null || end==null){
      return;
    }
   if(start.getRow()== end.getRow() && start.getColumn()==end.getColumn()){
     return;
   }

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

    boolean isCastlingMove = castlingMove(start, end);

    if (isCastlingMove) {
      isWhiteTurn = !isWhiteTurn;
      return;
    }

    board.setPiece(end.getRow(), end.getColumn(), piece);
    board.setPiece(start.getRow(), start.getColumn(), null);

    // after every move check if the king is in castling position
    Position kingPosition = findKingPosition(
      isWhiteTurn ? PieceColor.WHITE : PieceColor.BLACK
    );
    Piece king = board.getPiece(
      kingPosition.getRow(),
      kingPosition.getColumn()
    );

    if (checkIfKingInLeftCastlingPosition()) {
      ((King) king).setLeftCastle(true);
      // System.out.println("left castle" + "color" + king.getColor());
    } else {
      // System.out.println("no left castle" + "color" + king.getColor());
      ((King) king).setLeftCastle(false);
    }

    if (checkIfKingInRightCastlingPosition()) {
      ((King) king).setRightCastle(true);
      // System.out.println("right castle" + "color" + king.getColor());
    } else {
      ((King) king).setRightCastle(false);
      // System.out.println("no right castle" + "color" + king.getColor());
    }

    isWhiteTurn = !isWhiteTurn;
  }

  public boolean checkIfKingInLeftCastlingPosition() {
    PieceColor color = isWhiteTurn ? PieceColor.WHITE : PieceColor.BLACK;

    Position start = isWhiteTurn ? new Position(7, 4) : new Position(0, 4);

    if (isKingInCheck()) {
      return false;
    }

    // check if the king has moved
    Position kingPosition = findKingPosition(color);

    boolean isKingMove = true;

    if (kingPosition.getRow() == start.getRow() && kingPosition.getColumn() == start.getColumn()) {
      isKingMove = false;
    }

    if (isKingMove) {
      return false;
    }

    Position leftRookPosition = new Position(kingPosition.getRow(), 0);

    // check if the left rook has moved
    if (board.getPiece(leftRookPosition.getRow(), leftRookPosition.getColumn()) == null) {
      return false;
    }

    // check whether it is same color rook or not

    if (board
        .getPiece(leftRookPosition.getRow(), leftRookPosition.getColumn())
        .getColor() != color) {
      return false;
    }

    //check whether it is rook or not

    if (!(board.getPiece(
        leftRookPosition.getRow(),
        leftRookPosition.getColumn()) instanceof Rook)) {
      return false;
    }

    // check if there are any pieces between the king and the rook
    for (int i = 1; i < 4; i++) {
      if (board.getPiece(start.getRow(), i) != null) {
        return false;
      }
    }

    // check if the king passes through or finishes on a square that is attacked by an enemy piece
    for (int i = 2; i < 5; i++) {
      if (isKingInCheckAfterMove(start, new Position(start.getRow(), i))) {
        return false;
      }
    }
    return true;
  }
  
  public boolean checkIfKingInRightCastlingPosition() {
    PieceColor color = isWhiteTurn ? PieceColor.WHITE : PieceColor.BLACK;

    Position start = isWhiteTurn ? new Position(7, 4) : new Position(0, 4);
// System.out.println("here1");
    if (isKingInCheck()) {
      return false;
    }

    // check if the king has moved
    Position kingPosition = findKingPosition(color);

    boolean isKingMove = true;

    if (kingPosition.getRow() == start.getRow() && kingPosition.getColumn() == start.getColumn()) {
      isKingMove = false;
    }

    // System.out.println("king Position" + kingPosition.getRow() + " " + kingPosition.getColumn() );

    // System.out.println("here2");

    if (isKingMove) {
      return false;
    }

    Position rightRookPosition = new Position(kingPosition.getRow(), 7);

    // System.out.println("here3");
    // check if the right rook has moved
    if (board.getPiece(rightRookPosition.getRow(), rightRookPosition.getColumn()) == null) {
      return false;
    }

    // check whether it is same color rook or not
    // System.out.println("here4");
    if (board
        .getPiece(rightRookPosition.getRow(), rightRookPosition.getColumn())
        .getColor() != color) {
      return false;
    }

    //check whether it is rook or not
    // System.out.println("here5");
    if (!(board.getPiece(
        rightRookPosition.getRow(),
        rightRookPosition.getColumn()) instanceof Rook)) {
      return false;
    }

    // System.out.println("here6");
    // check if there are any pieces between the king and the rook
    for (int i = 5; i < 7; i++) {
      if (board.getPiece(start.getRow(), i) != null) {
        return false;
      }
    }


    // System.out.println("here7");
    // check if the king passes through or finishes on a square that is attacked by an enemy piece
    for (int i = 4; i < 7; i++) {
      if (isKingInCheckAfterMove(start, new Position(start.getRow(), i))) {
        return false;
      }
    }
    return true;
  }

  public boolean isKingInCheck() {
    Position kingPosition = findKingPosition(
        isWhiteTurn ? PieceColor.WHITE : PieceColor.BLACK);
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
              tempBoard);
          if (possibleMoves.contains(kingPosition)) {
            return true;
          }
        }
      }
    }

    return false;
  }
  
  

  public boolean castlingMove(Position start, Position end) {
    //     Neither the king nor the rook has previously moved.
    // There are no pieces between the king and the rook.
    // The king is not currently in check.
    // The king does not pass through or finish on a square that is attacked by an enemy piece.

    // check if the king is in check

    if (isKingInCheck()) {
      return false;
    }

    PieceColor color = isWhiteTurn ? PieceColor.WHITE : PieceColor.BLACK;

    Piece king = board.getPiece(start.getRow(), start.getColumn());

    if(!(king instanceof King)){
      return false;
    }

    if (((King) king).isLeftCastle() && end.getColumn() == 2) {
      //move the left rook
      Position leftRookPosition = new Position(start.getRow(), 0);
      Piece leftRook = board.getPiece(leftRookPosition.getRow(), leftRookPosition.getColumn());
      board.setPiece(start.getRow(), 3, leftRook);
      board.setPiece(leftRookPosition.getRow(), leftRookPosition.getColumn(), null);
      board.setPiece(start.getRow(), 2, king);
      board.setPiece(start.getRow(), 4, null);
      return true;

    }
    
    if (((King) king).isRightCastle() && end.getColumn() == 6) {
      //move the right rook
      Position rightRookPosition = new Position(start.getRow(), 7);
      Piece rightRook = board.getPiece(rightRookPosition.getRow(), rightRookPosition.getColumn());
      board.setPiece(start.getRow(), 5, rightRook);
      board.setPiece(rightRookPosition.getRow(), rightRookPosition.getColumn(), null);
      board.setPiece(start.getRow(), 6, king);
      board.setPiece(start.getRow(), 4, null);
      return true;
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
    
    Piece startPiece = board.getPiece(start.getRow(), start.getColumn());
    Piece endPiece = board.getPiece(end.getRow(), end.getColumn());

    board.setPiece(end.getRow(), end.getColumn(), startPiece);
    board.setPiece(start.getRow(), start.getColumn(), null);

    // Position kingPosition = findKingPosition(startPiece.getColor());
//    boolean isKingInCheck = false;

    Position kingPosition = findKingPosition(
      isWhiteTurn ? PieceColor.WHITE : PieceColor.BLACK
    );

    System.out.println("king Position :::" ,kingPosition.getRow() +" "+ kingPosition.getColumn());

    // Piece[][] tempBoard = board.getBoard();

    PieceColor opponentColor = isWhiteTurn
      ? PieceColor.BLACK
        : PieceColor.WHITE;
      
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Piece piece = board.getPiece(i,j);
        if (piece != null && piece.getColor() == opponentColor) {
          List<Position> possibleMoves = piece.allPossibleMoves(
              piece.getPosition(),
              board.getBoard());
          if (possibleMoves.contains(kingPosition)) {
            //revert back changes
            board.setPiece(start.getRow(), start.getColumn(), startPiece);
            board.setPiece(end.getRow(), end.getColumn(), endPiece);
            return true;
          }
        }
      }
    }
    //revert back changes
    board.setPiece(start.getRow(), start.getColumn(), startPiece);
    board.setPiece(end.getRow(), end.getColumn(), endPiece);

    //find king position in board
   
 

    return false;
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

  public boolean handleSquareSelection(
    int r,
    int c,
    Color initialColor,
    component[][] chessBoard
  ) {
    // if square color is green than move the piece

    // System.out.println("pahle color tha " + initialColor);

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
        higlightLegalMoves(r, c, initialColor);
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

  public List<Position> higlightLegalMoves(int r, int c, Color initialColor) {
    // System.out.println("row->" + r + "---col--" + c);
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
