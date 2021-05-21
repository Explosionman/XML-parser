package ru.rybinskov.service.ParsingService;

import ru.rybinskov.MockStarter;
import ru.rybinskov.entity.*;

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

    @Override
    public Addressee checkSender(String senderFromFile) {
        if (senderFromFile == null) {
            sb.append("Не указан Отправитель.\n");
            changeStatus();
            return null;
        }
        Addressee sender = MockStarter.findAddresseeByName(senderFromFile);
        if (sender == null) {
            sb.append("Некорректно указано имя отправителя. Отправитель: \"").append(senderFromFile)
                    .append("\" не найден в базе;\n");
            changeStatus();
            return null;
        }
        return sender;
    }

    @Override
    public Addressee checkReceiver(String receiverFromFile) {
        if (receiverFromFile == null) {
            sb.append("Не указан Получатель.\n");
            changeStatus();
            return null;
        }
        Addressee receiver = MockStarter.findAddresseeByName(receiverFromFile);
        if (receiver == null) {
            sb.append("Некорректно указано имя получателя. Получатель \"").append(receiverFromFile)
                    .append("\" не найден в базе;\n");
            changeStatus();
            return null;
        }
        return receiver;
    }

    @Override
    public String checkName(String nameFromFile) {
        if (nameFromFile == null || nameFromFile.isBlank()) {
            sb.append("Не указано Название.\n");
            changeStatus();
            return null;
        }
        return nameFromFile;
    }

    @Override
    public Type checkType(String typeFromFile) {
        if (typeFromFile == null) {
            sb.append("Не указан Тип\n");
            changeStatus();
            return null;
        }
        Type type = MockStarter.findTypeByName(typeFromFile);
        if (type == null) {
            sb.append("Некорректно указан тип. Тип \"").append(typeFromFile).append("\" не найден в базе;\n");
            changeStatus();
            return null;
        }

        return type;
    }

    @Override
    public LocalDateTime checkDateTime(String dateTimeFromFile) {
        LocalDateTime sendingDateTime = null;
        try {
            if (dateTimeFromFile != null) {
                sendingDateTime = LocalDateTime.parse(dateTimeFromFile, dateTimeFormatter);
            } else {
                sb.append("Не указана Дата_время_отправления;\n");
                changeStatus();
            }
        } catch (DateTimeParseException e) {
            sb.append("Некорректно указана Дата_время_отправления. Значение \"").
                    append(dateTimeFromFile).append("\" не устраивает ").append("согласованный шаблон HH:mm:ss dd.MM.yyyy;\n");
            changeStatus();
        }
        return sendingDateTime;
    }

    @Override
    public Cipher checkCipher(String cipherFromFile) {
        Cipher cipher = MockStarter.findCipherByName(cipherFromFile);
        if (cipher == null) {
            sb.append("Некорректно указан <Шифр>. Шифр \"").append(cipherFromFile).append("\" не найден в базе;\n");
            changeStatus();
            return null;
        }
        return cipher;
    }

    @Override
    public LocalTime checkStartTime(String timeFromFile) {
        LocalTime time;
        try {
            time = LocalTime.parse(timeFromFile, timeFormatter);
        } catch (DateTimeParseException e) {
            sb.append("Некорректно указано <Начало_СС>. Значение \"").
                    append(timeFromFile).append("\" не устраивает ").append("согласованный шаблон HH:mm:ss;\n");
            changeStatus();
            return null;
        }
        return time;
    }

    @Override
    public LocalTime checkEndTime(String timeFromFile) {
        LocalTime time;
        try {
            time = LocalTime.parse(timeFromFile, timeFormatter);
        } catch (DateTimeParseException e) {
            sb.append("Некорректно указано <Окончание_СС>. Значение \"").
                    append(timeFromFile).append("\" не устраивает ").append("согласованный шаблон HH:mm:ss;\n");
            return null;
        }
        return time;
    }

    @Override
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

    @Override
    public void checkDeviceInfoBeforeAdd(DeviceInfo deviceInfo) {
        if (deviceInfo.getName() == null) {
            sb.append("Не указано <Название> в <СС>\n");
            changeStatus();
        }
        if (deviceInfo.getCipher() == null) {
            sb.append("Не указан <Шифр>\n");
            changeStatus();
        }
        if (deviceInfo.getStartTime() == null) {
            sb.append("Не указано <Начало_СС>\n");
            changeStatus();
        }
        if (deviceInfo.getEndTime() == null) {
            sb.append("Не указано <Окончание_СС>\n");
            changeStatus();
        }
        if (deviceInfo.getModeList() == null) {
            sb.append("Не указано <Режимы_работы_ТС>");
            changeStatus();
        }

    }
}
