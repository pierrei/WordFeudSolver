package nu.mrpi.wordfeudsolver.worker;

import org.apache.log4j.Logger;

import nu.mrpi.wordfeudsolver.log.GameLogger;

/**
 *
 */
public abstract class AbstractWorker implements Worker {
    protected final GameLogger log = new GameLogger(Logger.getLogger(getClass()));
}
