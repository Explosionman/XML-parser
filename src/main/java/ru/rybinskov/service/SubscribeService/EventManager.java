package ru.rybinskov.service.SubscribeService;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    List<EventListener> subscribers = new ArrayList();

    public EventManager() {
    }

    public void subscribe(EventListener listener) {
        subscribers.add(listener);
    }

    public void unsubscribe(EventListener listener) {
        subscribers.remove(listener);
    }

    public void notify(String notification) {
        for (EventListener listener : subscribers) {
            listener.update(notification);
        }
    }
}
