package com.gl.reader.configuration;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Component
//@PropertySource("classpath:application.properties")

@PropertySources({
    @PropertySource(value = {"file:application.properties"}, ignoreResourceNotFound = true),
    @PropertySource(value = {"file:configuration.properties"}, ignoreResourceNotFound = true)
})

public class  PropertiesReader {

    @Value("${appdbName}")
    public String appdbName;

    @Value("${repdbName}")
    public String repdbName;

    @Value("${auddbName}")
    public String auddbName;

    @Value("${oamdbName}")
    public String oamdbName;

    @Value("${COMMA-DELIMITER}")
    public String commaDelimiter;

    @Value("${SEMI-COLON-DELIMITER}")
    public String semiColonDelimiter;

    @Value("${NEW-LINE-SEPARATOR}")
    public String newLineSeprator;

    @Value("${FILE-HEADER}")
    public String fileHeader;

    @Value("${TYPE-OF-PROCESS}")
    public String typeOfProcess;

    @Value("${FILES-COUNT-PER-REPORT}")
    public Long filesCount;

    @Value("${serverName}")
    public String servername;

    @Value("${EXTENSION}")
    public String extension;

    @Value("${SLEEP-TIME}")
    public Integer sleepTime;

    @Value("${INPUT-LOCATION}")
    public String inputLocation;

    @Value("${OUTPUT-LOCATION}")
    public String outputLocation;

    @Value("${ERROR-REPORT-FLAG}")
    public String errorReportFlag;

    @Value("${ROW-COUNT-FOR-SPLIT}")
    public Integer rowCountForSplit;

    @Value("#{'${REPORT-TYPE}'.split(',')}")
    public Set<String> reportType;
    
    
    @Value("#{'${IMS-SOURCE}'.split(',')}")
    public List<String> imsSources;

    @Value("#{'${yyMMddSource}'.split(',')}")
    public List<String> yyMMddSource;

    @Value("#{'${ddMMyySource}'.split(',')}")
    public List<String> ddMMyySource;

    @Value("#{'${ddMMyyyySource}'.split(',')}")
    public List<String> ddMMyyyySource;

    @Value("${yyyyMMddSource}")
    public String yyyyMMddSource;

    @Value("${ATTRIBUTE-SEPARATOR}")
    public String attributeSeperator;
}
