package com.gl.reader.dto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import static com.gl.reader.FileReaderHashApplication.auddbName;

@Repository
public class ModulesAudit {
    static Logger logger = LogManager.getLogger(ModulesAudit.class);

    public static int insertModuleAudit(Connection conn, String featureName, String processName, String servername) {
        int generatedKey = 0;
        String query = " insert into  " + auddbName + ".modules_audit_trail " + "(status_code,status,feature_name,"
                + "info, count2,action," + "server_name,execution_time,module_name,failure_count) "
                + "values('201','Initial', '" + featureName + "', '" + processName + "' ,'0','Insert', '"
                + servername + "','0','ETL','0')";
        logger.info(query);
        try {
            PreparedStatement ps = null;
            if (conn.toString().contains("oracle")) {
                ps = conn.prepareStatement(query, new String[]{"ID"});
            } else {
                ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            }
            logger.debug("Going to execute  ");
            ps.execute();
            logger.debug("Going for getGenerated key  ");
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            logger.info("Inserted record's ID: " + generatedKey);
            rs.close();
        } catch (Exception e) {
            logger.error("Failed  " + e);

        }
        return generatedKey;
    }

    public static void updateModuleAudit(Connection conn, int statusCode, String status, String errorMessage, int id, long executionStartTime, long numberOfRecord, long totalFileCount) {
         long milliseconds = (new Date().getTime()) - executionStartTime;
       
       String exec_time =  (milliseconds / 1000) +""  ;
        try (Statement stmt = conn.createStatement()) {
            String query = "update   " + auddbName + ".modules_audit_trail set status_code='" + statusCode + "',status='" + status + "',error_message='" + errorMessage + "', count='" + numberOfRecord + "',"
                    + "action='insert', execution_time = " + exec_time + "  ,  modified_on = CURRENT_TIMESTAMP , failure_count='0' , count2='" + totalFileCount + "'     where  id = " + id;
            logger.info(query);
            stmt.executeUpdate(query);
        } catch (Exception e) {
            logger.error("Failed  " + e);
        }
    }
}
// String executionFinishTiime = (((milliseconds / 1000) / 60) / 60) + ":" + (((milliseconds / 1000) / 60) % 60) + ":" + ((milliseconds / 1000) % 60);
// for Oracle
//   String executionFinishTiime = (((milliseconds / 1000) / 60) / 60) + ":" + (((milliseconds / 1000) / 60) % 60) + ":" + ((milliseconds / 1000) % 60);
// for Mysql execution_time = TIMEDIFF(now() ,created_on)
