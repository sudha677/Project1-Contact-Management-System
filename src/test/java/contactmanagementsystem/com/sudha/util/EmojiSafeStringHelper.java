package contactmanagementsystem.com.sudha.util;

import java.text.Normalizer;

public class EmojiSafeStringHelper {

    /**
     * Normalize string for safe Unicode comparison.
     * This removes potential Unicode composition differences.
     */
    public static String normalize(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFC).trim();
    }

    /**
     * Compare two strings for equality after normalization.
     */
    public static boolean equalsNormalized(String actual, String expected) {
        return normalize(actual).equals(normalize(expected));
    }

    /**
     * Check if actual string contains expected substring after normalization.
     */
    public static boolean containsNormalized(String actual, String expectedSubstring) {
        return normalize(actual).contains(normalize(expectedSubstring));
    }

    /**
     * Debug-friendly print showing the actual code points.
     */
    public static void printCodePoints(String text) {
        System.out.print("Code points: ");
        text.codePoints().forEach(cp -> System.out.print(String.format("U+%04X ", cp)));
        System.out.println();
    }
}
