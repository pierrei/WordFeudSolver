package nu.mrpi.wordfeudsolver.worker;

import java.util.Arrays;
import java.util.List;

import nu.mrpi.wordfeudapi.RestWordFeudClient;
import nu.mrpi.wordfeudapi.WordFeudClient;
import nu.mrpi.wordfeudapi.exception.WordFeudLoginRequiredException;
import nu.mrpi.wordfeudsolver.chat.ChatWorker;
import nu.mrpi.wordfeudsolver.chat.MessageStore;
import nu.mrpi.wordfeudsolver.domain.WFAccount;
import nu.mrpi.wordfeudsolver.service.GameService;
import nu.mrpi.wordfeudsolver.service.SettingsService;
import nu.mrpi.wordfeudsolver.solver.GameDifficultyCompositeSolver;
import nu.mrpi.wordfeudsolver.solver.Solver;
import nu.mrpi.wordfeudsolver.solver.calculator.WFCalculator;

/**
 *
 */
public class AccountWorker extends AbstractWorker implements Worker {
    private final WFAccount account;
    private final WordFeudClient client = new RestWordFeudClient();
    private final List<? extends Worker> workers;

    private boolean firstRun = true;

    public AccountWorker(WFAccount account, GameService gameService, WFCalculator calculator, MessageStore messageStore, SettingsService settingsService) {
        this.account = account;

        final ChatWorker chatWorker = new ChatWorker(client, gameService, messageStore);

        final Solver solver = new GameDifficultyCompositeSolver(gameService, calculator);

        workers = Arrays.asList(new InvitationsWorker(client, calculator, messageStore), new NotificationsWorker(client, chatWorker),
                                new MoveMakerWorker(client, solver, messageStore, gameService, settingsService));
    }

    @Override
    public void doWork() {
        log.debug("Starting to work for account " + account.getEmail());

        checkFirstRun();

        try {
            for (Worker worker : workers) {
                worker.doWork();
            }
        } catch (WordFeudLoginRequiredException e) {
            logon(e.getClient());
        } catch (Exception e) {
            log.error("Error while working", e);
        }
    }

    private void checkFirstRun() {
        if (firstRun) {
            logon(client);
            firstRun = false;
        }
    }

    private void logon(WordFeudClient client) {
        try {
            client.logon(account.getEmail(), account.getPassword());
        } catch (Exception e) {
            log.error("Problem logging in", e);
        }
    }
}
