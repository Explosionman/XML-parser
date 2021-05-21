package ru.rybinskov.service.ParsingService;

import ru.rybinskov.MockStarter;
import ru.rybinskov.entity.Addressee;
import ru.rybinskov.entity.Cipher;
import ru.rybinskov.entity.Type;
import ru.rybinskov.entity.WorkingMode;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReportCheckerImpl implements ReportChecker {
    private boolean correctReport;
    private static final String DATE_TIME_FORMATTER = "HH:mm:ss dd.MM.yyyy";
    private static final String TIME_FORMATTER = "HH:mm:ss";
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER);
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMATTER);
    private StringBuilder sb;

    public ReportCheckerImpl() {
        this.correctReport = true;
        this.sb = new StringBuilder();
    }

    private void changeStatus() {
        if (correctReport) correctReport = false;
    }

    public boolean isCorrectReport() {
        return correctReport;
    }

    public String getCheckResult() {
        return sb.toString();
    }

    public Addressee checkSender(String senderFromFile) {
        Addressee sender = MockStarter.findAddresseeByName(senderFromFile);
        if (sender == null) {
            sb.append("Некорректно указано имя отправителя. Отправитель: \"").append(senderFromFile)
                    .append("\" не найден в базе;\n");
            changeStatus();
            return null;
        }
        return sender;
    }

    public Addressee checkReceiver(String receiverFromFile) {
        Addressee receiver = MockStarter.findAddresseeByName(receiverFromFile);
        if (receiver == null) {
            sb.append("Некорректно указано имя получателя. Получатель \"").append(receiverFromFile)
                    .append("\" не найден в базе;\n");
            changeStatus();
            return null;
        }
        return receiver;
    }

    public String checkName(String nameFromFile) {
        if (nameFromFile.isBlank()) {
            sb.append("Некорректно указано название. Оно не может быть не проинициализированным или пустым;\n");
            changeStatus();
            return null;
        }
        return nameFromFile;
    }

    public Type checkType(String typeFromFile) {
        Type type = MockStarter.findTypeByName(typeFromFile);
        if (type == null) {
            sb.append("Некорректно указан тип. Тип \"").append(typeFromFile).append("\" не найден в базе;\n");
            changeStatus();
            return null;
        }
        return type;
    }

    public LocalDateTime checkDateTime(String dateTimeFromFile) {
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

    public Cipher checkCipher(String cipherFromFile) {
        Cipher cipher = MockStarter.findCipherByName(cipherFromFile);
        if (cipher == null) {
            sb.append("Некорректно указан <Шифр>. Шифр \"").append(cipherFromFile).append("\" не найден в базе;\n");
            changeStatus();
            return null;
        }
        return cipher;
    }

    public LocalTime checkStartTime(String timeFromFile) {
        LocalTime time;
        try {
            time = LocalTime.parse(timeFromFile, timeFormatter);
        } catch (DateTimeParseException e) {
            sb.append("Некорректно указано время в <Начало_СС>. Значение \"").
                    append(timeFromFile).append("\" не устраивает ").append("согласованный шаблон HH:mm:ss;\n");
            changeStatus();
            return null;
        }
        return time;
    }

    public LocalTime checkEndTime(String timeFromFile) {
        LocalTime time;
        try {
            time = LocalTime.parse(timeFromFile, timeFormatter);
        } catch (DateTimeParseException e) {
            sb.append("Некорректно указано время в <Окончание_СС>. Значение \"").
                    append(timeFromFile).append("\" не устраивает ").append("согласованный шаблон HH:mm:ss;\n");
            return null;
        }
        return time;
    }

    public WorkingMode checkMode(String modeFromFile) {
        Integer modeNumber;
        try {
            modeNumber = Integer.parseInt(modeFromFile);
        } catch (NumberFormatException e) {
            sb.append("Некорректно указан <Режимы_работы_ТС>. Режимы_работы_ТС \"").append(modeFromFile).append("\" не является числом;\n");
            changeStatus();
            return null;
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
