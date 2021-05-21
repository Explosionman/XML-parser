package ru.rybinskov.service.ParsingService;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import ru.rybinskov.MockStarter;
import ru.rybinskov.entity.*;
import ru.rybinskov.service.SubscribeService.EventManager;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReportHandler extends DefaultHandler {
    private static final String DATE_TIME_FORMATTER = "HH:mm:ss dd.MM.yyyy";
    private static final String TIME_FORMATTER = "HH:mm:ss";
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER);
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMATTER);
    private StringBuilder sb;
    private String currentElement = "";
    private Report report;
    private DeviceInfo deviceInfo;
    private boolean isCorrectReport;
    public EventManager events;

    public ReportHandler() {
        this.events = new EventManager();
    }

    @Override
    public void startDocument() {
        isCorrectReport = true;
        sb = new StringBuilder();
        report = new Report();
        deviceInfo = new DeviceInfo();
    }

    @Override
    public void endDocument() {
        if (isCorrectReport) {
            report.setDeviceInfo(deviceInfo);
            MockStarter.getReportList().add(report);
        }
        events.notify(sb.toString());
        report = null;
        deviceInfo = null;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentElement = qName;
        if ("ПЗС".equals(currentElement)) {
            Addressee sender = checkSender(attributes.getValue("Отправитель"));
            Addressee receiver = checkReceiver(attributes.getValue("Получатель"));
            String name = checkName(attributes.getValue("Название"));
            Type type = checkType(attributes.getValue("Тип"));
            LocalDateTime sendingDateTime = checkDateTime(attributes.getValue("Дата_время_отправления"));
            if (isCorrectReport) {
                report.setSender(sender);
                report.setReceiver(receiver);
                report.setName(name);
                report.setType(type);
                report.setSendingDateTime(sendingDateTime);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        currentElement = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String text = new String(ch, start, length);
        if (text.contains("<") || currentElement == null) {
            return;
        }
        if ("Название".equals(currentElement)) {
            String name = checkName(text);
            deviceInfo.setName(name);
        } else if ("Шифр".equals(currentElement)) {
            checkCipher(text);
        } else if ("Начало_СС".equals(currentElement)) {
            checkStartTime(text);
        } else if ("Окончание_СС".equals(currentElement)) {
            checkEndTime(text);
        } else if ("Режим_работы_ТС".equals(currentElement)) {
            checkMode(text);
        }
    }

    private void changeStatus() {
        if (isCorrectReport) isCorrectReport = false;
    }

    private Addressee checkSender(String senderFromFile) {
        Addressee sender = MockStarter.findAddresseeByName(senderFromFile);
        if (sender == null) {
            sb.append("Некорректно указано имя отправителя. Отправитель: \"").append(senderFromFile)
                    .append("\" не найден в базе;\n");
            changeStatus();
            return null;
        }
        return sender;
    }

    private Addressee checkReceiver(String receiverFromFile) {
        Addressee receiver = MockStarter.findAddresseeByName(receiverFromFile);
        if (receiver == null) {
            sb.append("Некорректно указано имя получателя. Получатель \"").append(receiverFromFile)
                    .append("\" не найден в базе;\n");
            changeStatus();
            return null;
        }
        return receiver;
    }

    private String checkName(String nameFromFile) {
        if (nameFromFile.isBlank()) {
            sb.append("Некорректно указано название. Оно не может быть не проинициализированным или пустым;\n");
            changeStatus();
            return null;
        }
        return nameFromFile;
    }

    private Type checkType(String typeFromFile) {
        Type type = MockStarter.findTypeByName(typeFromFile);
        if (type == null) {
            sb.append("Некорректно указан тип. Тип \"").append(typeFromFile).append("\" не найден в базе;\n");
            changeStatus();
            return null;
        }
        return type;
    }

    private LocalDateTime checkDateTime(String dateTimeFromFile) {
        LocalDateTime sendingDateTime = null;
        try {
            sendingDateTime = LocalDateTime.parse(dateTimeFromFile, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            sb.append("Некорректно указана <Дата_время_отправления>. Значение \"").
                    append(dateTimeFromFile).append("\" не устраивает ").append("согласованный шаблон HH:mm:ss dd.MM.yyyy;\n");
            changeStatus();
        }
        return sendingDateTime;
    }

    private void checkCipher(String cipherFromFile) {
        Cipher cipher = MockStarter.findCipherByName(cipherFromFile);
        if (cipher == null) {
            sb.append("Некорректно указан <Шифр>. Шифр \"").append(cipherFromFile).append("\" не найден в базе;\n");
            changeStatus();
        }
        deviceInfo.setCipher(cipher);
    }

    private void checkStartTime(String timeFromFile) {
        LocalTime time = null;
        try {
            time = LocalTime.parse(timeFromFile, timeFormatter);
        } catch (DateTimeParseException e) {
            sb.append("Некорректно указано время в <Начало_СС>. Значение \"").
                    append(timeFromFile).append("\" не устраивает ").append("согласованный шаблон HH:mm:ss;\n");
            changeStatus();
        }
        deviceInfo.setStartTime(time);
    }

    private void checkEndTime(String timeFromFile) {
        LocalTime time = null;
        try {
            time = LocalTime.parse(timeFromFile, timeFormatter);
        } catch (DateTimeParseException e) {
            sb.append("Некорректно указано время в <Окончание_СС>. Значение \"").
                    append(timeFromFile).append("\" не устраивает ").append("согласованный шаблон HH:mm:ss;\n");
        }
        deviceInfo.setStartTime(time);
    }

    private WorkingMode checkMode(String modeFromFile) {
        Integer modeNumber = null;
        try {
            modeNumber = Integer.parseInt(modeFromFile);
        } catch (NumberFormatException e) {
        }
        WorkingMode mode = MockStarter.findModeByNumber(modeNumber);
        if (mode == null) {
            sb.append("Некорректно указан <Режимы_работы_ТС>. Режимы_работы_ТС \"").append(modeFromFile).append("\" не найден в базе;\n");
            changeStatus();
            return null;
        }
        return mode;
    }
}
