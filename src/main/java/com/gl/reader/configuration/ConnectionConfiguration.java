package com.gl.reader.configuration;

import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Repository;

import jakarta.persistence.*;
import jakarta.persistence.PersistenceContext;
import java.sql.Connection;


@Repository
public class ConnectionConfiguration {

    @PersistenceContext
    private EntityManager em;

    public Connection getConnection() {
        EntityManagerFactoryInfo info = (EntityManagerFactoryInfo) em.getEntityManagerFactory();
        try {
            return info.getDataSource().getConnection();
        } catch (Exception e) {
            return null;
        }
    }

}