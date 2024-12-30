package com.gl.reader.dto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Objects;

import static com.gl.reader.FileReaderHashApplication.*;
import static com.gl.reader.FileReaderHashApplication.conn;

@Repository
public class Alert {
    static Logger logger = LogManager.getLogger(Alert.class);

    public static void raiseTheAlert(String alertId, Map<String, String> bodyPlaceHolderMap, Integer userId) {

        try (Statement stmt = conn.createStatement();) {
            String alertDescription = getAlertbyId(conn, alertId);
            if (Objects.nonNull(bodyPlaceHolderMap)) {
                for (Map.Entry<String, String> entry
                        : bodyPlaceHolderMap.entrySet()) {
                    logger.info("Placeholder key : " + entry.getKey() + " value : " + entry.getValue());
                    alertDescription = alertDescription.replaceAll(entry.getKey(), entry.getValue());
                }
            }
            logger.info("alert message: " + alertDescription);
            String sql = "Insert into " + appdbName + ".sys_generated_alert (alert_id,description,status,user_id)" + "values('"
                    + alertId + "' ,'" + alertDescription + "',0," + userId + ")";
            logger.info("Inserting alert into running alert db" + sql);
            stmt.executeUpdate(sql);
            // Sy stem.exi t(0);
        } catch (Exception e) {
            logger.error("Error in raising Alert. So, doing nothing." + e);
            //  Sys tem.ex it(0);
        }
    }

    public static String getAlertbyId(Connection conn, String alertId) {
        String description = "";
        try (Statement stmt = conn.createStatement();) {
            String sql = "select description from " + appdbName + ".cfg_feature_alert where alert_id='" + alertId + "'";
            logger.info("Fetching alert message by alert id from alertDb " + sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                description = rs.getString("description");
            }
        } catch (Exception e) {
            logger.info("Not able to get alert by id ." + e);
        }
        return description;
    }


    private static RestTemplate restTemplate = null;

    public static void  raiseAlert(String alertId, String alertMessage) {
        AlertDto alertDto = new AlertDto();
        alertDto.setAlertId(alertId);
        alertDto.setAlertMessage(alertMessage);
        alertDto.setAlertProcess("CDR_pre_processor");


        long start = System.currentTimeMillis();
        try {
            SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
            clientHttpRequestFactory.setConnectTimeout(1000);
            clientHttpRequestFactory.setReadTimeout(1000);
            restTemplate = new RestTemplate(clientHttpRequestFactory);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<AlertDto> request = new HttpEntity<AlertDto>(alertDto, headers);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(alertUrl, request, String.class);
            logger.info("Alert Sent Request:{}, TimeTaken:{} Response:{}", alertDto, responseEntity, (System.currentTimeMillis() - start));
        } catch (org.springframework.web.client.ResourceAccessException resourceAccessException) {
            logger.error("Error while Sending Alert resourceAccessException:{} Request:{}", resourceAccessException.getMessage(), alertDto, resourceAccessException);
        } catch (Exception e) {
            logger.error("Error while Sending Alert Error:{} Request:{}", e.getMessage(), alertDto, e);
        }

    }

}

class AlertDto {
    private String alertId;
    private String alertMessage;
    private String alertProcess;

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public String getAlertProcess() {
        return alertProcess;
    }

    public void setAlertProcess(String alertProcess) {
        this.alertProcess = alertProcess;
    }
}
