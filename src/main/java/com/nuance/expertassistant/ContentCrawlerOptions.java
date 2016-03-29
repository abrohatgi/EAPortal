package com.nuance.expertassistant;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public final class ContentCrawlerOptions {
	private static final String INPUT_TYPE = "inputType";
	private static final String INPUT = "input";
	private static final String OUTPUT = "output";

	private static final Options options = new Options();
	private static CommandLine cmd = null;

	public ContentCrawlerOptions(String args[]) {
		@SuppressWarnings("static-access")
		final Option inputTypeOption = OptionBuilder.withArgName(INPUT_TYPE)
				.hasArg().withDescription("Input type (file/folder/url)")
				.create(INPUT_TYPE);
		options.addOption(inputTypeOption);

		@SuppressWarnings("static-access")
		final Option inputOption = OptionBuilder.withArgName(INPUT).hasArg()
				.withDescription("Input").create(INPUT);
		options.addOption(inputOption);

		@SuppressWarnings("static-access")
		final Option outputOption = OptionBuilder.withArgName(OUTPUT).hasArg()
				.withDescription("Output").create(OUTPUT);
		options.addOption(outputOption);

		final CommandLineParser parser = new PosixParser();
		try {
			cmd = parser.parse(options, args);
		} catch (final ParseException e) {
			System.out.println("Parsing failed. Reason: " + e.getMessage());
			exitWithHelpMessage();
		}
	}

	public static ContentCrawlerInputTypes getInputType() {
		if (cmd.hasOption(INPUT_TYPE)) {
			final String inputTypeValString = cmd.getOptionValue(INPUT_TYPE);
			if (ContentCrawlerInputTypes.contains(inputTypeValString)) {
				return ContentCrawlerInputTypes.get(inputTypeValString);
			} else {
				exitWithHelpMessage();
				return null;
			}
		} else {
			return ContentCrawlerInputTypes.URL;
		}
	}

	public static String getInput() {
		if (cmd.hasOption(INPUT)) {
			return cmd.getOptionValue(INPUT);
		} else {
			return exitWithHelpMessage();
		}
	}

	public static String getOutput() {
		if (cmd.hasOption(OUTPUT)) {
			return cmd.getOptionValue(OUTPUT);
		} else {
			return exitWithHelpMessage();
		}
	}

	protected static String exitWithHelpMessage() {
		final HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("Content Crawler:", options);
		System.exit(1);
		return null;
	}

}
