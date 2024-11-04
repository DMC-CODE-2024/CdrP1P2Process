package com.gl.reader.dto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Objects;

import static com.gl.reader.FileReaderHashApplication.appdbName;
import static com.gl.reader.FileReaderHashApplication.conn;

@Repository
public class Alert {
    static Logger logger = LogManager.getLogger(Alert.class);

    public static void raiseAlert( String alertId, Map<String, String> bodyPlaceHolderMap, Integer userId) {

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

}
