package nu.mrpi.wordfeudsolver.log;

import nu.mrpi.wordfeudapi.domain.Game;
import org.apache.log4j.*;

/**
 * @author Pierre Ingmansson
 */
public class GameLogger {
    private Logger log;

    public GameLogger(final Logger log) {
        this.log = log;
    }

    public void debug(final long gameId, String logMessage) {
        log.debug(composeLogMessage(gameId, logMessage));
    }

    public void debug(final Game game, String logMessage) {
        debug(game.getId(), logMessage);
    }

    public void info(final long gameId, String logMessage) {
        log.info(composeLogMessage(gameId, logMessage));
    }

    public void info(final Game game, String logMessage) {
        info(game.getId(), logMessage);
    }

    public void warn(final long gameId, String logMessage) {
        log.warn(composeLogMessage(gameId, logMessage));
    }

    public void warn(final Game game, String logMessage) {
        warn(game.getId(), logMessage);
    }

    private String composeLogMessage(long gameId, String logMessage) {
        return "[" + gameId + "] " + logMessage;
    }


    public void trace(Object message) {
        log.trace(message);
    }

    public void trace(Object message, Throwable t) {
        log.trace(message, t);
    }

    public void debug(Object message) {
        log.debug(message);
    }

    public void debug(Object message, Throwable t) {
        log.debug(message, t);
    }

    public void error(Object message) {
        log.error(message);
    }

    public void error(Object message, Throwable t) {
        log.error(message, t);
    }

    public void fatal(Object message) {
        log.fatal(message);
    }

    public void fatal(Object message, Throwable t) {
        log.fatal(message, t);
    }

    public String getName() {
        return log.getName();
    }

    public Category getParent() {
        return log.getParent();
    }

    public Level getLevel() {
        return log.getLevel();
    }

    public void info(Object message) {
        log.info(message);
    }

    public void info(Object message, Throwable t) {
        log.info(message, t);
    }

    public boolean isAttached(Appender appender) {
        return log.isAttached(appender);
    }

    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    public boolean isEnabledFor(Priority level) {
        return log.isEnabledFor(level);
    }

    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    public void log(Priority priority, Object message, Throwable t) {
        log.log(priority, message, t);
    }

    public void log(Priority priority, Object message) {
        log.log(priority, message);
    }

    public void log(String callerFQCN, Priority level, Object message, Throwable t) {
        log.log(callerFQCN, level, message, t);
    }

    public void setLevel(Level level) {
        log.setLevel(level);
    }

    public void warn(Object message) {
        log.warn(message);
    }

    public void warn(Object message, Throwable t) {
        log.warn(message, t);
    }
}
