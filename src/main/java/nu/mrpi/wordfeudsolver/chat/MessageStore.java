package nu.mrpi.wordfeudsolver.chat;

import nu.mrpi.wordfeudsolver.domain.Difficulty;

import static nu.mrpi.util.MathUtil.random;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * @author Pierre Ingmansson
 */
public class MessageStore {

    public static final String BUNDLE = "chat";

    public String getChatCommand(final Locale locale, final String command) {
        return getString(locale, "chat.command." + command);
    }

    public String getRandomBragMessage(final Locale locale) {
        return getString(locale, "brag." + random(1, 3));
    }

    public String getHelp(final Locale locale) {
        return getString(locale, "help");
    }

    public String getGreeting(final Locale locale, final String username) {
        return getString(locale, "greeting.nightmare", username);
    }

    public String getLocalizedDifficulty(final Locale locale, final Difficulty difficulty) {
        return getString(locale, "chat.command." + difficulty.toString().toLowerCase());
    }

    public String getDifficultySetToMessage(Locale locale, Difficulty difficulty) {
        return getString(locale, "chat.response.difficulty_set", getLocalizedDifficulty(locale, difficulty));
    }

    public String getDifficultyLevelMessage(final Locale locale, Difficulty difficulty) {
        return getString(locale, "chat.response.difficulty_level", getLocalizedDifficulty(locale, difficulty));
    }

    public static String getString(final Locale locale, final String key) {
        try {
            return new String(ResourceBundle.getBundle(BUNDLE, locale).getString(key).getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return ResourceBundle.getBundle(BUNDLE, locale).getString(key);
        }
    }

    public static String getString(final Locale locale, final String key, final Object... params) {
        try {
            return MessageFormat.format(getString(locale, key), params);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
