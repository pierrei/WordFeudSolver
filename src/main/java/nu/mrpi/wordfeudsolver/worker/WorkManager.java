package nu.mrpi.wordfeudsolver.worker;

import java.util.List;


/**
 * @author Pierre Ingmansson (pierre@ingmansson.se)
 */
public class WorkManager {
    private List<? extends Worker> workers;

    public WorkManager(List<? extends Worker> workers) {
        this.workers = workers;
    }

    public void work() {
        for (Worker worker : workers) {
            worker.doWork();
        }
    }
}
