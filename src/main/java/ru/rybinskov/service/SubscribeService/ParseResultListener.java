package ru.rybinskov.service.SubscribeService;

public class ParseResultListener implements EventListener {

    @Override
    public void update(String notification) {
        if (notification.length() == 0) {
            System.out.println(true);
        } else {
            System.out.println("Не удалось распарсить файл по причине:\n" + notification);
        }
    }
}
