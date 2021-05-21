package ru.rybinskov;

import ru.rybinskov.entity.*;
import ru.rybinskov.service.ParsingService.ParsingServiceImpl;
import ru.rybinskov.service.SubscribeService.ParseResultListener;

import java.util.ArrayList;
import java.util.List;

public final class MockStarter {
    private static final List<Report> reportList = new ArrayList<>();
    private static final List<Addressee> addresseeList = new ArrayList<>();
    private static final List<Type> typeList = new ArrayList<>();
    private static final List<Cipher> cipherList = new ArrayList<>();
    private static final List<WorkingMode> workingModeList = new ArrayList<>();

    static {
        Addressee addressee1 = new Addressee("ЦУП");
        Addressee addressee2 = new Addressee("РКС");
        addresseeList.add(addressee1);
        addresseeList.add(addressee2);

        Type type1 = new Type("хор");
        Type type2 = new Type("изм");
        typeList.add(type1);
        typeList.add(type2);

        Cipher cipher1 = new Cipher("Шифр-366");
        Cipher cipher2 = new Cipher("Шифр-1");
        cipherList.add(cipher1);
        cipherList.add(cipher2);

        WorkingMode mode1 = new WorkingMode(190);
        WorkingMode mode2 = new WorkingMode(150);
        WorkingMode mode3 = new WorkingMode(30);
        workingModeList.add(mode1);
        workingModeList.add(mode2);
        workingModeList.add(mode3);
    }

    public static void main(String[] args) {
        String filePath;
        try {
            for (int i = 1; i < 5; i++) {
                filePath = "src/main/resources/file" + i + ".xml";
                ParsingServiceImpl parsingService = new ParsingServiceImpl(filePath, "Report");
                parsingService.getReportHandler().events.subscribe(new ParseResultListener());
                parsingService.parseXML();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
//        Оставил для просмотра сохранённых объектов если возникнет желание посмотреть
//        getReportList().forEach(System.out::println);
    }

    public static List<Report> getReportList() {
        return reportList;
    }

    public static Addressee findAddresseeByName(String name) {
        for (Addressee addressee : addresseeList) {
            if (addressee.getAddressee().equals(name)) {
                return addressee;
            }
        }
        return null;
    }

    public static Type findTypeByName(String name) {
        for (Type type : typeList) {
            if (type.getReportType().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public static Cipher findCipherByName(String name) {
        for (Cipher cipher : cipherList) {
            if (cipher.getCipher().equals(name)) {
                return cipher;
            }
        }
        return null;
    }

    public static WorkingMode findModeByNumber(Integer number) {
        for (WorkingMode mode : workingModeList) {
            if (mode.getMode().equals(number)) {
                return mode;
            }
        }
        return null;
    }
}
