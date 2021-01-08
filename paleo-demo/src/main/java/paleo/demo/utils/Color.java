package paleo.demo.utils;

public enum Color {
	LIGHT_RED("\033[1;31m"),
	LIGHT_BLUE("\033[1;34m"),
	LIGHT_GREEN("\033[1;32m"),
	LIGHT_PURPLE("\033[1;35m"),
	LIGHT_CYAN("\033[1;36m"),
	CYAN("\033[0;36m"),
	BLUE("\033[0;34m"),
	GREEN("\033[0;32m"),
	PURPLE("\033[0;35m"),
	NORMAL("\033[0m");

	public static void printWith(final String content, final Color color) {
		System.out.print(color + content + NORMAL);
	}

	public static void printlnWith(final String content, final Color color) {
		System.out.println(color + content + NORMAL);
	}

	private final String value;

	private Color(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
