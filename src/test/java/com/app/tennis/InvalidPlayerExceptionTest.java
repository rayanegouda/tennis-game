package com.app.tennis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * Exception personnalisée pour gérer les entrées de joueur invalides.
 */
class InvalidPlayerExceptionTest extends Exception {
	/**
	 * Constructeur de l'exception InvalidPlayerException.
	 *
	 * @param message Le message d'erreur.
	 */
	@Test
	public void testExceptionMessage() {
		String errorMessage = "Player is invalid";
		InvalidPlayerException exception = new InvalidPlayerException(errorMessage);

		// Vérifier que le message de l'exception est correct
		assertEquals(errorMessage, exception.getMessage());
	}

	@Test
	public void testExceptionInheritance() {
		InvalidPlayerException exception = new InvalidPlayerException("Player is invalid");

		// Vérifier que InvalidPlayerException hérite bien de Exception
		assertInstanceOf(Exception.class, exception);
	}
}
