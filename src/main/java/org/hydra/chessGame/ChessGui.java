package org.hydra.chessGame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

import org.hydra.chessGame.AllPieces.*;
import org.hydra.chessGame.utils.PieceColor;
import org.hydra.chessGame.utils.Position;


public class ChessGui extends JFrame {

  private component[][] chessBoard;
  private ChessGame chessGame;

private String IconDir = "src/main/java/org/hydra/chessGame/Icons/";

    private final Map<Class<? extends Piece>, String> pieceUnicodeMapBlack = new HashMap<>() {
        {
            put(Pawn.class, IconDir + "black_pawn.png");
            put(Rook.class, IconDir + "black_rook.png");
            put(Knight.class, IconDir + "black_horse.png");
            put(Bishop.class, IconDir + "black_bishop.png");
            put(Queen.class, IconDir + "black_queen.png");
            put(King.class, IconDir + "black_king.png");
        }

    };


    private final Map<Class<? extends Piece>, String> pieceUnicodeMapWhite = new HashMap<>() {
        {
            put(Pawn.class, IconDir + "white_pawn.png");
            put(Rook.class, IconDir + "white_rook.png");
            put(Knight.class, IconDir + "white_horse.png");
            put(Bishop.class, IconDir + "white_bishop.png");
            put(Queen.class, IconDir + "white_queen.png");
            put(King.class, IconDir + "white_king.png");
        }

    };

  public ChessGui() throws IOException {
    chessGame = new ChessGame();
    chessBoard = new component[8][8];

    setTitle("Chess Game");
    setSize(700, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new java.awt.GridLayout(8, 8));
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        // chessBoard[i][j] = new component(i, j);
        // add(chessBoard[i][j]);
        component c = new component(i, j);
        c.addMouseListener(
            new MouseAdapter() {
              @Override
              public void mouseClicked(MouseEvent e) {
                // System.out.println("row->" + c.getRow() + "---col--" + c.getCol());
                try {
                  handleSquareCLick(c.getRow(), c.getCol());
                } catch (IOException ex) {
                  throw new RuntimeException(ex);
                }
              }
            });

        chessBoard[i][j] = c;

        add(c);

      }

    }



//    chessBoard[0][0].setIcon(
//        new ImageIcon("src/main/resources/Icons/pawn.jpg")
//      );
      resetBackGround();
    refreshBoard();
  }

  private void refreshBoard() throws IOException {
    ChessBoard board = chessGame.getBoard();
    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
            Piece piece = board.getPiece(row, col); // Assuming ChessBoard has a getPiece method
            if (piece != null) {
                // If using Unicode symbols:
//                String path = pieceUnicodeMap.get(piece.getClass());
                Color color = (piece.getColor() == PieceColor.WHITE) ? Color.WHITE : Color.BLACK;
                // chessBoard[row][col].setPieceIcon(path, color);

                if(color == Color.WHITE){

                    chessBoard[row][col].setPieceIcon(pieceUnicodeMapWhite.get(piece.getClass()), color);
                } else {

                    chessBoard[row][col].setPieceIcon(pieceUnicodeMapBlack.get(piece.getClass()), color);
                }

                // Or, if updating with icons or any other graphical representation
            } else {
                chessBoard[row][col].clearPiece(); // Ensure this method clears the square
            }
        }
    }
//    resetBackGround();
  }

  public void handleSquareCLick(int r, int c) throws IOException {
    Color initialColor=chessBoard[r][c].getBackground();
      resetBackGround();
      // System.out.println("row->"+r + "---col--"+c);
      List<Position> LegalMoves = chessGame.higlightLegalMoves(r, c,initialColor);
      //color the legal moves with green color

      for (Position p : LegalMoves) {
          chessBoard[p.getRow()][p.getColumn()].setBackground(java.awt.Color.GREEN);

      }
       chessGame.handleSquareSelection(r,c,initialColor,chessBoard);
      refreshBoard();
//      resetBackGround();
      // refreshBoard();
  }
  
  public void resetBackGround() {
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

  public void checkGameStatus() {
    chessGame.checkGameStatus();
  }

}
