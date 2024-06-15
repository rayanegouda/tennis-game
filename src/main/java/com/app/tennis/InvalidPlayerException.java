package com.app.tennis;

/**
 * Exception personnalisée pour gérer les entrées de joueur invalides.
 */
class InvalidPlayerException extends Exception {
	/**
	 * Constructeur de l'exception InvalidPlayerException.
	 *
	 * @param message Le message d'erreur.
	 */
	public InvalidPlayerException(String message) {
		super(message);
	}
}
