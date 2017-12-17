package nu.mrpi.wordfeudsolver;

import java.util.LinkedList;
import java.util.List;

import nu.mrpi.wordfeudsolver.chat.MessageStore;
import nu.mrpi.wordfeudsolver.domain.WFAccount;
import nu.mrpi.wordfeudsolver.persistance.GameDAO;
import nu.mrpi.wordfeudsolver.persistance.redis.GameDAORedis;
import nu.mrpi.wordfeudsolver.service.GameService;
import nu.mrpi.wordfeudsolver.service.SettingsService;
import nu.mrpi.wordfeudsolver.solver.calculator.WFCalculator;
import nu.mrpi.wordfeudsolver.worker.AccountWorker;
import nu.mrpi.wordfeudsolver.worker.WorkManager;
import nu.mrpi.wordfeudsolver.worker.Worker;

import org.apache.log4j.Logger;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.se)
 */
@SuppressWarnings({ "InfiniteLoopStatement" })
public class WordFeudSolverMain {
    private static final Logger log = Logger.getLogger(WordFeudSolverMain.class);
    private static SettingsService settingsService;

    public static void main(final String[] args) {
        try {
            final WorkManager workManager = createGameWorker();

            while (true) {
                try {
                    workManager.work();
                } catch (Exception e) {
                    log.error("Error while working", e);
                }
                sleep(settingsService.getSleepTime());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static WorkManager createGameWorker() {
        settingsService = new SettingsService();

        final GameDAO gameDAO = new GameDAORedis(settingsService);
        final GameService gameService = new GameService(gameDAO);
        final MessageStore messageStore = new MessageStore();

        final WFCalculator calculator = createInstance(settingsService.getWFCalculatorClass());

        List<Worker> workers = new LinkedList<>();

        List<WFAccount> accounts = settingsService.getWordFeudAccounts();
        if (accounts.isEmpty()) {
            throw new RuntimeException("No accounts found in settings!");
        }

        for (WFAccount account : accounts) {
            workers.add(new AccountWorker(account, gameService, calculator, messageStore, settingsService));
        }

        return new WorkManager(workers);
    }

    private static WFCalculator createInstance(Class wfCalculatorClass) {
        try {
            return (WFCalculator) wfCalculatorClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Could not instantiate " + wfCalculatorClass);
        }
    }

    private static void sleep(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
}
