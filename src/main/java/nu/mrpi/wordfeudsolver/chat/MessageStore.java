package nu.mrpi.wordfeudsolver.chat;

import java.nio.charset.StandardCharsets;
import nu.mrpi.wordfeudsolver.domain.Difficulty;

import static nu.mrpi.util.MathUtil.random;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import nu.mrpi.wordfeudsolver.domain.DifficultyStats;
import nu.mrpi.wordfeudsolver.domain.PlayerStats;
import org.apache.commons.lang.StringUtils;


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

    public String getDifficultySetMessage(Locale locale, Difficulty difficulty) {
        return getString(locale, "chat.response.difficulty_set", getLocalizedDifficulty(locale, difficulty));
    }

    public String getInitialDifficultySetMessage(Locale locale, Difficulty difficulty) {
        return getString(locale, "chat.response.initial_difficulty_set", getLocalizedDifficulty(locale, difficulty));
    }

    public String getDifficultyLevelMessage(final Locale locale, Difficulty difficulty) {
        return getString(locale, "chat.response.difficulty_level", getLocalizedDifficulty(locale, difficulty));
    }

    public String getNoStatsFound(final Locale locale) {
        return getString(locale, "chat.stats.not_found");
    }

    public String getStatsInfoMessage(final Locale locale) {
        return getString(locale, "chat.stats.info");
    }

    public String getDifficultyStats(final Locale locale, final DifficultyStats difficultyStats) {
        final String capitalizedDifficulty =
            StringUtils.capitalize(getLocalizedDifficulty(locale, difficultyStats.difficulty()));

        if (difficultyStats.wins() == 0 && difficultyStats.losses() > 0) {
            return getString(locale,
                "chat.stats.difficulty_losses_only",
                capitalizedDifficulty,
                difficultyStats.losses(),
                difficultyStats.ties(),
                difficultyStats.maxLoss(),
                difficultyStats.minLoss()
                );
        } else if (difficultyStats.losses() == 0 && difficultyStats.wins() > 0) {
            return getString(locale,
                "chat.stats.difficulty_wins_only",
                capitalizedDifficulty,
                difficultyStats.wins(),
                difficultyStats.ties(),
                difficultyStats.maxWin(),
                difficultyStats.minWin()
                );
        }

        return getString(locale,
            "chat.stats.difficulty_both",
            capitalizedDifficulty,
            difficultyStats.wins(),
            difficultyStats.losses(),
            difficultyStats.ties(),
            difficultyStats.maxWin(),
            difficultyStats.minWin(),
            difficultyStats.maxLoss(),
            difficultyStats.minLoss()
            );
    }

    public static String getString(final Locale locale, final String key) {
        return new String(ResourceBundle
            .getBundle(BUNDLE, locale)
            .getString(key)
            .getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    public static String getString(final Locale locale, final String key, final Object... params) {
        try {
            return MessageFormat.format(getString(locale, key), params);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

}
