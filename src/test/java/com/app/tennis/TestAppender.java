package com.app.tennis;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * TestAppender pour les tests unitaires sur les sorties de logs.
 */
public class TestAppender extends AbstractAppender {

	private final List<String> logMessages = new ArrayList<>();

	protected TestAppender(String name, Filter filter) {
		super(name, filter, PatternLayout.createDefaultLayout(), true);
	}

	@Override
	public void append(LogEvent event) {
		logMessages.add(event.getMessage().getFormattedMessage());
	}

	public List<String> getLogMessages() {
		return new ArrayList<>(logMessages);
	}
}
