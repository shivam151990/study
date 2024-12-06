package lld.logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogSubject {
    private Map<Integer, List<LogObserver>> logObservers = new HashMap<>();
    void addObserver(int level, LogObserver logObserver) {
        logObservers.putIfAbsent(level, new ArrayList<>());
        logObservers.get(level).add(logObserver);
    }

    void notifyObservers(int level, String message) {
        for (Map.Entry<Integer, List<LogObserver>> entry: logObservers.entrySet()) {
            if (level == entry.getKey()) {
                entry.getValue().forEach(obs -> obs.log(message));
            }
        }
    }


}
