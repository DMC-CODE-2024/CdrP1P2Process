package com.gl.reader.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.gl.reader.FileReaderHashApplication.file_patterns;
import static com.gl.reader.FileReaderHashApplication.propertiesReader;


@Service
public class FileReaderService {
    static Logger logger = LogManager.getLogger(FileReaderService.class);

    static List<String> pattern = new ArrayList<>();

    public static String getArrivalTimeFromFilePattern(String source, String fileName) {
        String date = "";

        if (!source.contains("all")) {
            pattern.addAll(file_patterns);
        }

        for (String filePattern : pattern) {
            String[] attributes = filePattern.split("-", -1);
            if (fileName.contains(attributes[0])) {
                date = fileName.substring(fileName.indexOf(attributes[0]) + Integer.parseInt(attributes[1]),
                        fileName.indexOf(attributes[0]) + Integer.parseInt(attributes[1])
                                + Integer.parseInt(attributes[2]));
            }
        }
        String imei_arrivalTime = null;
        try {
            String dateType = "yyyyMMdd";
            if (propertiesReader.ddMMyyyySource.contains(source)) {
                dateType = "ddMMyyyy";
            } else if (propertiesReader.yyMMddSource.contains(source)) {
                dateType = "yyMMdd";
            } else if (propertiesReader.ddMMyySource.contains(source)) {
                dateType = "ddMMddyy";
            }
            imei_arrivalTime = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat(dateType).parse(date));
        } catch (Exception e) {
            logger.error(fileName + " Unable to parse Date ,Defined Pattern" + date + ", Error " + e);
        }
        return imei_arrivalTime;
    }
}
