package com.app.tennis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest {
	private static final Logger logger = LogManager.getLogger(App.class);
	private TennisGame tennisGame;
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
	public void testMain() {
		String input = "ABABAA"; // 'Z' is an invalid input to test the exception
		String expected = "Player A wins the game";
		for (char c : input.toCharArray()) {
			try {
				tennisGame.pointWonBy(c);
			} catch (InvalidPlayerException e) {
				logger.error(e.getMessage());
			}
			if (TennisGame.endOfGame) {
				break;
			}
		}
		List<String> logMessages = testAppender.getLogMessages();
		assertEquals(expected, logMessages.get(logMessages.size() - 1));
	}

}
