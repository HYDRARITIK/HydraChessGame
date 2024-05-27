package org.hydra.chessGame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.io.IOException;

@SpringBootApplication
public class ChessGameApplication {


	public static void main(String[] args) throws IOException {
		ChessGui chessGui = new ChessGui();
		chessGui.setVisible(true);

		SwingUtilities.invokeLater(
				() -> {
					try {
						new ChessGui();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		);

		SpringApplication.run(ChessGameApplication.class, args);
	}
}
