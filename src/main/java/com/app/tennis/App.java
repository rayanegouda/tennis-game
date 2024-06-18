package com.app.tennis;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
	private static final Logger logger = LogManager.getLogger(App.class);

	/**
	 * Point d'entrée principal pour exécuter le jeu de tennis avec une séquence d'entrées.
	 *
	 * @param args Arguments de la ligne de commande.
	 */
	public static void main(String[] args) {
		TennisGame game = new TennisGame();
		String input = args[0]; // "ABAZBAA" is an invalid input to test the exception
		for (char c : input.toCharArray()) {
			try {
				game.pointWonBy(c);
			} catch (InvalidPlayerException e) {
				logger.error(e.getMessage());
			}
			if (TennisGame.endOfGame) {
				break;
			}
		}
	}
}
