package nu.mrpi.wordfeudsolver.worker;

import nu.mrpi.wordfeudapi.WordFeudClient;
import nu.mrpi.wordfeudapi.domain.NotificationEntry;
import nu.mrpi.wordfeudapi.domain.Notifications;
import nu.mrpi.wordfeudsolver.chat.ChatWorker;

/**
 *
 */
public class NotificationsWorker extends AbstractWorker implements Worker {
    private final WordFeudClient wordFeudClient;
    private final ChatWorker chatWorker;

    public NotificationsWorker(WordFeudClient wordFeudClient, ChatWorker chatWorker) {
        this.wordFeudClient = wordFeudClient;
        this.chatWorker = chatWorker;
    }

    @Override
    public void doWork() {
        final Notifications notifications = wordFeudClient.getNotifications();
        for (final NotificationEntry entry : notifications.getEntries()) {
            if (entry.isChatMessage()) {
                processChatMessage(entry);
            } else {
                log.info("Received notification: " + entry);
            }
        }
    }

    private void processChatMessage(final NotificationEntry entry) {
        chatWorker.processChatMessage(entry.getGameId(), entry.getUsername(), entry.getMessage());
    }

}
