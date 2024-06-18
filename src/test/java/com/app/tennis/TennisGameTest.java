package com.app.tennis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TennisGameTest {

	private TennisGame tennisGame;
	private static final Logger logger = LogManager.getLogger(TennisGame.class);
	private TestAppender testAppender;


	@BeforeEach
	public void setUp() {
		tennisGame = new TennisGame();
		testAppender = new TestAppender("TestAppender", null);
		LoggerContext context = (LoggerContext) LogManager.getContext(false);
		org.apache.logging.log4j.core.config.Configuration config = context.getConfiguration();
		config.getRootLogger().addAppender(testAppender, null, null);
		testAppender.start();
	}

	@AfterEach
	public void tearDown() {
		LoggerContext context = (LoggerContext) LogManager.getContext(false);
		org.apache.logging.log4j.core.config.Configuration config = context.getConfiguration();
		config.getRootLogger().removeAppender(String.valueOf(testAppender.getName()));
		testAppender.stop();
	}

	@Test
	public void testTennisGame() {
		assertEquals(tennisGame.getPlayerAScore(), 0);
		assertEquals(tennisGame.getPlayerBScore(), 0);
		assertFalse(tennisGame.isPlayerAAdvantage());
		assertFalse(tennisGame.isPlayerBAdvantage());
	}

	@Test
	public void testPointWonBy() throws InvalidPlayerException {
		/*Print Score*/
		String expectedScore = "Player A : 15 / Player B : 0";
		assertEquals(expectedScore, tennisGame.pointWonBy('A'));
		expectedScore = "Player A : 15 / Player B : 15";
		assertEquals(expectedScore, tennisGame.pointWonBy('B'));

		/*Deuce Test*/
		tennisGame.pointWonBy('A');
		tennisGame.pointWonBy('A');
		tennisGame.pointWonBy('B');
		expectedScore = "Player A : 40 / Player B : 40 (Deuce)";
		assertEquals(expectedScore, tennisGame.pointWonBy('B'));

		/*A Advantage*/
		expectedScore = "Player A : ADV / Player B : 40";
		assertEquals(expectedScore, tennisGame.pointWonBy('A'));

		/*B Advantage*/
		expectedScore = "Player A : 40 / Player B : ADV";
		tennisGame.pointWonBy('B');
		assertEquals(expectedScore, tennisGame.pointWonBy('B'));

	}


	@Test
	public void testHandlePointWonByA() {
		String expectedMessage;
		/*A Advantage*/
		tennisGame.setPlayerBScore(40);
		tennisGame.setPlayerAScore(40);
		tennisGame.handlePointWonByA();
		assertTrue(tennisGame.isPlayerAAdvantage());
		assertFalse(tennisGame.isPlayerBAdvantage());

		/*A Win With an advantage*/
		tennisGame = new TennisGame();
		tennisGame.setPlayerAScore(40);
		tennisGame.setPlayerBScore(40);
		tennisGame.handlePointWonByA();
		tennisGame.handlePointWonByA();
		expectedMessage = "Player A wins the game";
		assertEquals(expectedMessage, testAppender.getLogMessages().get(0));

		/*A Win Without an advantage*/
		tennisGame = new TennisGame();
		tennisGame.setPlayerAScore(40);
		assertEquals(expectedMessage, testAppender.getLogMessages().get(0));
	}

	@Test
	public void testHandlePointWonByB() {
		String expectedMessage;
		/*B Advantage*/
		tennisGame.setPlayerBScore(40);
		tennisGame.setPlayerAScore(40);
		tennisGame.handlePointWonByB();
		assertTrue(tennisGame.isPlayerBAdvantage());
		assertFalse(tennisGame.isPlayerAAdvantage());

		/*B Win With an advantage*/
		tennisGame = new TennisGame();
		tennisGame.setPlayerBScore(40);
		tennisGame.setPlayerAScore(40);
		tennisGame.handlePointWonByB();
		tennisGame.handlePointWonByB();
		expectedMessage = "Player B wins the game";
		assertEquals(expectedMessage, testAppender.getLogMessages().get(0));

		/*B Win Without an advantage*/
		tennisGame = new TennisGame();
		tennisGame.setPlayerBScore(40);
		assertEquals(expectedMessage, testAppender.getLogMessages().get(0));
	}

	@Test
	public void testIsDeuce() throws InvalidPlayerException {
		TennisGame tennisGameDeuce = new TennisGame();
		tennisGameDeuce.setPlayerBScore(40);
		tennisGameDeuce.setPlayerAScore(40);
		assertTrue(tennisGameDeuce.isDeuce());
	}

	@Test
	public void testNextScore() {
		assertEquals(15, tennisGame.nextScore(0));
		assertEquals(30, tennisGame.nextScore(15));
		assertEquals(40, tennisGame.nextScore(30));
		assertEquals(40, tennisGame.nextScore(40));
	}

	@Test
	public void testPrintScore() {
		String expectedScore;
		/*B WIN WITHOUT ADV*/
		tennisGame.setPlayerBAdvantage(true);
		tennisGame.setPlayerBScore(40);
		expectedScore = "Player A : " + 0 + " / Player B : " + 40;
		assertEquals(expectedScore, tennisGame.printScore());

		/*A WIN WITHOUT ADV*/
		tennisGame = new TennisGame();
		tennisGame.setPlayerAAdvantage(true);
		tennisGame.setPlayerAScore(40);
		expectedScore = "Player A : " + 40 + " / Player B : " + 0;
		assertEquals(expectedScore, tennisGame.printScore());

		/*DEUCE*/
		tennisGame = new TennisGame();
		tennisGame.setPlayerAAdvantage(false);
		tennisGame.setPlayerAScore(40);
		tennisGame.setPlayerBAdvantage(false);
		tennisGame.setPlayerBScore(40);
		expectedScore = "Player A : 40 / Player B : 40 (Deuce)";
		assertEquals(expectedScore, tennisGame.printScore());
	}


	@Test
	public void testInvalidPlayerInput() {
		char c = 'C';
		String expected = "Invalid player input: C";
		InvalidPlayerException exception = assertThrows(InvalidPlayerException.class, () -> {
			tennisGame.validatePlayerInput(c);
		});
		assertEquals(expected, exception.getMessage());
	}
}
