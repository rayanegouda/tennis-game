package com.app.tennis;

/**
 * Exception personnalisée pour gérer les entrées de joueur invalides.
 */
class InvalidPlayerExceptionTest extends Exception {
	/**
	 * Constructeur de l'exception InvalidPlayerException.
	 *
	 * @param message Le message d'erreur.
	 */
	public InvalidPlayerExceptionTest(String message) {
		super(message);
	}
}
