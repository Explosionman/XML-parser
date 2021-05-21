package ru.rybinskov.service.ParsingService;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import ru.rybinskov.entity.Report;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class ParsingServiceImpl {
    private String filePath;
    private ReportHandler reportHandler;

    public ParsingServiceImpl(String filePath, String className) {
        this.filePath = filePath;
        if (Report.class.getSimpleName().toLowerCase().equals(className.toLowerCase())) {
            this.reportHandler = new ReportHandler();
        } else {
            throw new IllegalArgumentException("Некорректно указано имя объекта");
        }
    }

    public void parseXML() {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = spf.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(reportHandler);
            xmlReader.parse(filePath);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public ReportHandler getReportHandler() {
        return reportHandler;
    }
}