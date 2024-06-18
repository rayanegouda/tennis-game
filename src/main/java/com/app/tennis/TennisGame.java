package com.app.tennis;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * La classe TennisGame implémente un jeu de tennis simple avec un système de score.
 * Le jeu suit les règles classiques de scoring du tennis, y compris les règles
 * de deuce et d'avantage.
 */
public class TennisGame {
	private static final Logger logger = LogManager.getLogger(TennisGame.class);
	@Getter
	@Setter
	private int playerAScore;
	@Getter
	@Setter
	private int playerBScore;
	@Getter
	@Setter
	private boolean playerAAdvantage;
	@Getter
	@Setter
	private boolean playerBAdvantage;

	public static boolean endOfGame;

	/**
	 * Constructeur par défaut qui initialise les scores et les avantages des joueurs.
	 */
	public TennisGame() {
		this.playerAScore = 0;
		this.playerBScore = 0;
		this.playerAAdvantage = false;
		this.playerBAdvantage = false;
		endOfGame = false;
	}

	/**
	 * Met à jour le score en fonction du joueur qui a gagné le point.
	 *
	 * @param player Le caractère représentant le joueur ('A' pour le joueur A, 'B' pour le joueur B).
	 * @throws InvalidPlayerException si l'entrée du joueur est invalide.
	 */
	public String pointWonBy(char player) throws InvalidPlayerException {
		try {
			validatePlayerInput(player);
			if (player == 'A') {
				handlePointWonByA();
			} else if (player == 'B') {
				handlePointWonByB();
			}
			return printScore();
		} catch (InvalidPlayerException e) {
			logger.error(e.getMessage());
			return e.getMessage();
		}
	}

	/**
	 * Gère le point gagné par le joueur A et met à jour le score en conséquence.
	 */
	public void handlePointWonByA() {
		if (isDeuce()) {
			if (playerAAdvantage) {
				logger.info("Player A wins the game");
				endOfGame = true;
			} else if (playerBAdvantage) {
				playerBAdvantage = false;
			} else {
				playerAAdvantage = true;
			}
		} else if (playerAScore == 40) {
			logger.info("Player A wins the game");
			endOfGame = true;
		} else {
			playerAScore = nextScore(playerAScore);
		}
	}

	/**
	 * Gère le point gagné par le joueur B et met à jour le score en conséquence.
	 */
	public void handlePointWonByB() {
		if (isDeuce()) {
			if (playerBAdvantage) {
				logger.info("Player B wins the game");
				endOfGame = true;
			} else if (playerAAdvantage) {
				playerAAdvantage = false;
			} else {
				playerBAdvantage = true;
			}
		} else if (playerBScore == 40) {
			logger.info("Player B wins the game");
			endOfGame = true;
		} else {
			playerBScore = nextScore(playerBScore);
		}
	}

	/**
	 * Vérifie si le score est en situation de deuce (égalité à 40-40).
	 *
	 * @return true si les deux joueurs ont 40 points, sinon false.
	 */
	public boolean isDeuce() {
		return playerAScore == 40 && playerBScore == 40 && !endOfGame;
	}

	/**
	 * Retourne le score suivant basé sur le score actuel.
	 *
	 * @param currentScore Le score actuel du joueur.
	 * @return Le score suivant du joueur.
	 */
	public int nextScore(int currentScore) {
		switch (currentScore) {
			case 0:
				return 15;
			case 15:
				return 30;
			case 30:
				return 40;
			default:
				return currentScore;
		}
	}

	/**
	 * Affiche le score actuel des deux joueurs.
	 */
	public String printScore() {
		String score;
		if (endOfGame) {
			score = "";
		} else if (isDeuce()) {
			if (playerAAdvantage) {
				logger.info("Player A : ADV / Player B : 40");
				score = "Player A : ADV / Player B : 40";
			} else if (playerBAdvantage) {
				logger.info("Player A : 40 / Player B : ADV");
				score = "Player A : 40 / Player B : ADV";
			} else {
				logger.info("Player A : 40 / Player B : 40 (Deuce)");
				score = "Player A : 40 / Player B : 40 (Deuce)";
			}
		} else {
			logger.info("Player A : " + playerAScore + " / Player B : " + playerBScore);
			score = "Player A : " + playerAScore + " / Player B : " + playerBScore;
		}
		return score;
	}

	/**
	 * Valide l'entrée du joueur.
	 *
	 * @param player Le caractère représentant le joueur ('A' ou 'B').
	 * @throws InvalidPlayerException si l'entrée du joueur est invalide.
	 */
	public void validatePlayerInput(char player) throws InvalidPlayerException {
		if (player != 'A' && player != 'B') {
			throw new InvalidPlayerException("Invalid player input: " + player);
		}
	}

}

