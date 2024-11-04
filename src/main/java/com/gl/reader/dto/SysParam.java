package com.gl.reader.dto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gl.reader.FileReaderHashApplication.*;


@Repository
public class SysParam {
    static Logger logger = LogManager.getLogger(SysParam.class);

    public static Map<String, String> getCdrImeiLengthValues(Connection conn) {
        Map<String, String> cdrImeiCheckMap = new HashMap<String, String>();
        String sql = "select tag , value  from " + appdbName + ".sys_param where tag in  " +
                " ('CDR_IMEI_LENGTH_CHECK' ,'CDR_IMEI_LENGTH_VALUE','CDR_NULL_IMEI_CHECK','CDR_NULL_IMEI_REPLACE_PATTERN'   , 'CDR_ALPHANUMERIC_IMEI_CHECK')";
        logger.info("Fetching details " + sql);
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                cdrImeiCheckMap.put(rs.getString("tag"), rs.getString("value"));
            }
        } catch (Exception e) {
            logger.error("Not able to access details from sys_param " + e);
            Alert.raiseAlert( "alert006", Map.of("<e>", "not able to  access details from sys_param " + e.toString() + ". in   ", "<process_name>", "CDR_pre_processor"), 0);
            System.exit(0);
        }
        return cdrImeiCheckMap;
    }


    public static List<String> getFilePatternByOperatorSource(Connection conn, String operator, String sourceName) {
        String sql = "select  value  from " + appdbName + ".sys_param where tag =  '" + operator.toUpperCase() + "_" + sourceName.toUpperCase() + "_FILE_PATTERN'   ";
        logger.info("Fetching details for FILE_PATTERN " + sql);
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {
            String response = "null";
            while (rs.next()) {
                response = rs.getString("value");
            }
            logger.info("Fetching response  " + response);
            return Arrays.asList(response.split(","));
        } catch (Exception e) {
            logger.error("Not able to access details from sys_param " + e);
            Alert.raiseAlert( "alert006", Map.of("<e>", "file pattern not access " + e.toString() + ". in   ", "<process_name>", "CDR_pre_processor"), 0);
            System.exit(0);
        }
        return null;
    }


}
