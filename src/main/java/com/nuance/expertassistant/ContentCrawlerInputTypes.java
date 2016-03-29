package com.nuance.expertassistant;

public enum ContentCrawlerInputTypes {
	FILE("file"), FOLDER("folder"), URL("url");

	private final String name;

	private ContentCrawlerInputTypes(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}

	@Override
	public String toString() {
		return name;
	}

	public static boolean contains(String test) {

		for (final ContentCrawlerInputTypes c : ContentCrawlerInputTypes
				.values()) {
			if (c.name.equals(test)) {
				return true;
			}
		}

		return false;
	}

	public static ContentCrawlerInputTypes get(String test) {

		for (final ContentCrawlerInputTypes c : ContentCrawlerInputTypes
				.values()) {
			if (c.name.equals(test)) {
				return c;
			}
		}

		return null;
	}

}
