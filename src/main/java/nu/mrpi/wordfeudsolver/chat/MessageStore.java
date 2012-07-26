package nu.mrpi.wordfeudsolver.chat;

import static nu.mrpi.util.MathUtil.random;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * @author Pierre Ingmansson
 */
public class MessageStore {

    public static final String BUNDLE = "chat";

    public String getRandomBragMessage(final Locale locale) {
        return getString(locale, "brag." + random(1, 3));
    }

    public String getGreeting(final Locale locale, final String username) {
        return getString(locale, "greeting.nightmare", username);
    }

    public static String getString(final Locale locale, final String key) {
        return ResourceBundle.getBundle(BUNDLE, locale).getString(key);
    }

    public static String getString(final Locale locale, final String key, final Object... params) {
        try {
            return MessageFormat.format(getString(locale, key), params);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
