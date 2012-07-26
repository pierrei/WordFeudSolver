package nu.mrpi.wordfeudsolver.worker;

import nu.mrpi.wordfeudapi.WordFeudClient;
import nu.mrpi.wordfeudapi.domain.Game;
import nu.mrpi.wordfeudapi.domain.Invite;
import nu.mrpi.wordfeudapi.domain.Status;
import nu.mrpi.wordfeudsolver.chat.MessageStore;
import nu.mrpi.wordfeudsolver.solver.calculator.WFCalculator;

/**
 *
 */
public class InvitationsWorker extends AbstractWorker implements Worker {
    private final WordFeudClient wordFeudClient;
    private final WFCalculator calculator;
    private final MessageStore messageStore;

    public InvitationsWorker(WordFeudClient wordFeudClient, WFCalculator calculator, MessageStore messageStore) {
        this.wordFeudClient = wordFeudClient;
        this.calculator = calculator;
        this.messageStore = messageStore;
    }

    public void doWork() {
        final Status status = wordFeudClient.getStatus();
        if (status.getInvitesReceived().length > 0) {
            log.info("Found " + status.getInvitesReceived().length + " pending invitations");

            for (final Invite invite : status.getInvitesReceived()) {
                if (calculator.isRulesetSupported(invite.getRuleset())) {
                    acceptInvitation(invite);

                } else {
                    log.info("Rejecting invite from " + invite.getInviter() + ", because of unsupported ruleset " + invite.getRuleset());
                    wordFeudClient.rejectInvite(invite.getId());
                }
            }
        }
    }

    private void acceptInvitation(final Invite invite) {
        log.info("Accepting invite from " + invite.getInviter());
        final int gameId = wordFeudClient.acceptInvite(invite.getId());
        final Game game = wordFeudClient.getGame(gameId);

        log.info(game, "Sending greeting message to " + invite.getInviter());
        wordFeudClient.chat(gameId, messageStore.getGreeting(game.getLanguageLocale(), invite.getInviter()));
    }

}
