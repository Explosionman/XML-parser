package ru.rybinskov.service.ParsingService;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import ru.rybinskov.MockStarter;
import ru.rybinskov.entity.*;
import ru.rybinskov.service.SubscribeService.EventManager;

import java.time.LocalDateTime;

public class ReportHandler extends DefaultHandler {
    private ReportCheckerImpl reportChecker;
    private String currentElement = "";
    private Report report;
    private DeviceInfo deviceInfo;

    public EventManager events;

    public ReportHandler() {
        this.events = new EventManager();
    }

    @Override
    public void startDocument() {
        reportChecker = new ReportCheckerImpl();
        report = new Report();
        deviceInfo = new DeviceInfo();
    }

    @Override
    public void endDocument() {
        reportChecker.checkDeviceInfoBeforeAdd(deviceInfo);
        if (reportChecker.isCorrectReport()) {
            report.setDeviceInfo(deviceInfo);
            MockStarter.getReportList().add(report);
        }
        events.notify(reportChecker.getCheckResult());
        report = null;
        deviceInfo = null;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentElement = qName;
        if ("ПЗС".equals(currentElement)) {
            Addressee sender = reportChecker.checkSender(attributes.getValue("Отправитель"));
            Addressee receiver = reportChecker.checkReceiver(attributes.getValue("Получатель"));
            String name = reportChecker.checkName(attributes.getValue("Название"));
            Type type = reportChecker.checkType(attributes.getValue("Тип"));
            LocalDateTime sendingDateTime = reportChecker.checkDateTime(attributes.getValue("Дата_время_отправления"));
            if (reportChecker.isCorrectReport()) {
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
        if (currentElement == null) {
            return;
        }
        if ("Название".equals(currentElement)) {
            deviceInfo.setName(reportChecker.checkName(text));
        } else if ("Шифр".equals(currentElement)) {
            deviceInfo.setCipher(reportChecker.checkCipher(text));
        } else if ("Начало_СС".equals(currentElement)) {
            deviceInfo.setStartTime(reportChecker.checkStartTime(text));
        } else if ("Окончание_СС".equals(currentElement)) {
            deviceInfo.setEndTime(reportChecker.checkEndTime(text));
        } else if ("Режим_работы_ТС".equals(currentElement)) {
            deviceInfo.addMode(reportChecker.checkMode(text));
        }
    }
}
