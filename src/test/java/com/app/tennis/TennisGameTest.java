package com.app.tennis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TennisGameTest {

	private TennisGame tennisGame;

	@BeforeEach
	public void setUp() {
		tennisGame = new TennisGame();
	}

	@Test
	public void testLoveAll() throws InvalidPlayerException {
		String expectedScore = "Player A : 15 / Player B : 0";
		assertEquals(expectedScore, tennisGame.pointWonBy('A') );
		expectedScore = "Player A : 15 / Player B : 15";
		assertEquals(expectedScore, tennisGame.pointWonBy('B') );
	}

	@Test
	public void testFifteenAll() throws InvalidPlayerException {
		tennisGame.pointWonBy('A');
		tennisGame.pointWonBy('A');
		tennisGame.pointWonBy('B');
		String expectedScore = "Player A : 30 / Player B : 30";
		assertEquals(expectedScore, tennisGame.pointWonBy('B'));
	}


	@Test
	public void testHandlePointWonByA() {
		TennisGame tennisGame = new TennisGame();

		// Test winning after reaching 40 points
		tennisGame.setPlayerAScore(30);
		tennisGame.handlePointWonByA();
		assertEquals(40, tennisGame.getPlayerAScore());

/*		// Test winning the game from 40 points
		tennisGame.setPlayerAScore(40);
		tennisGame.setPlayerBScore(0);
		tennisGame.handlePointWonByA();
		assertEquals(", tennisGame.getPlayerAScore());*/

		// Test advantage scenario (assuming player B already has advantage)
		tennisGame.setPlayerBAdvantage( true);
		tennisGame.setPlayerAScore(40);
		tennisGame.handlePointWonByA();
		assertEquals(false, tennisGame.isPlayerAAdvantage());
		assertEquals(0, tennisGame.getPlayerBScore());

		// Test deuce scenario (player A gains advantage)
		tennisGame.setPlayerBAdvantage(false);
		tennisGame.setPlayerAScore(30);
		tennisGame.setPlayerBScore (30);
		tennisGame.handlePointWonByA();
		tennisGame.handlePointWonByA();
		assertEquals(false, tennisGame.isPlayerAAdvantage());
		assertEquals(30, tennisGame.getPlayerBScore());

		// Test basic point update (not reaching 40 or deuce)
		tennisGame.setPlayerAAdvantage(false);
		tennisGame.setPlayerAScore(15);
		tennisGame.handlePointWonByA();
		assertEquals(30, tennisGame.getPlayerAScore());
	}

	@Test
	public void testPlayerBWins() throws InvalidPlayerException {
		tennisGame.pointWonBy('B');
		tennisGame.pointWonBy('B');
		tennisGame.pointWonBy('B');
		tennisGame.pointWonBy('B');

		String expectedScore = "Player B wins the game";
		assertEquals(expectedScore, tennisGame.toString());
	}

	@Test
	public void testDeuce() throws InvalidPlayerException {
		tennisGame.pointWonBy('A');
		tennisGame.pointWonBy('A');
		tennisGame.pointWonBy('A');
		tennisGame.pointWonBy('B');
		tennisGame.pointWonBy('B');
		tennisGame.pointWonBy('B');
		tennisGame.pointWonBy('A');

		String expectedScore = "Player A : ADV / Player B : 40";
		assertEquals(expectedScore, tennisGame.toString());
	}

	@Test
	public void testAdvantageWin() throws InvalidPlayerException {
		tennisGame.pointWonBy('A');
		tennisGame.pointWonBy('A');
		tennisGame.pointWonBy('A');
		tennisGame.pointWonBy('B');
		tennisGame.pointWonBy('B');
		tennisGame.pointWonBy('B');
		tennisGame.pointWonBy('A');
		tennisGame.pointWonBy('A');

		String expectedScore = "Player A wins the game";
		assertEquals(expectedScore, tennisGame.toString());
	}

	@Test
	public void testInvalidPlayerInput() {
		assertThrows(InvalidPlayerException.class, () -> tennisGame.pointWonBy('C'));
	}
}
