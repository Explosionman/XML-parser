package ru.rybinskov.service.ParsingService;

import ru.rybinskov.entity.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

public interface ReportChecker {
    Addressee checkSender(String senderFromFile);

    Addressee checkReceiver(String receiverFromFile);

    String checkName(String nameFromFile);

    Type checkType(String typeFromFile);

    LocalDateTime checkDateTime(String dateTimeFromFile);

    Cipher checkCipher(String cipherFromFile);

    LocalTime checkStartTime(String timeFromFile);

    LocalTime checkEndTime(String timeFromFile);

    WorkingMode checkMode(String modeFromFile);

    void checkDeviceInfoBeforeAdd(DeviceInfo deviceInfo);

}
