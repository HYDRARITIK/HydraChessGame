package org.hydra.chessGame;

import lombok.Getter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class component extends JButton{
   
    private  int row;
    private  int col;
    




    public component(int row, int col) {
        this.row = row;
        this.col = col;


    }




    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }





    public void initButton(){
        setPreferredSize(new Dimension(100, 100));

        //set background color

        if((row + col) % 2 == 0){
            setBackground(Color.WHITE);
        } else {
            setBackground(Color.BLACK);
        }

        //text alignment
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.TOP);

        //font size

        setFont(new Font("Arial", Font.PLAIN, 50));

        //text color

        setForeground(Color.BLACK);

    }

    public void setPieceSymbol(String symbol, Color color){
        setText(symbol);
        // increase text size




        setForeground(color);
    }

    public void setPieceIcon(String Path, Color color) throws IOException {
        int width = 80;
        int height = 80;


        
//         String tempPath = "src/main/java/org/hydra/chessGame/Icons/black_bishop.png";
        String tempPath=Path;

        Image image = ImageIO.read(new File(tempPath)).getScaledInstance(width, height, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(image));

        //reduce dimension of icon


        setForeground(color);
    }

    public void clearPiece(){
        setText("");
        setIcon(null);
    }

    public void refresh(){

    }
}
