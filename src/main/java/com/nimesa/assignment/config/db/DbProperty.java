package com.nimesa.assignment.config.db;

import lombok.Data;

@Data
public class DbProperty {
    public String driverClassName;
    public String url;
    public String username;
    public String password;
    public int initialSize;
    public int maxActive;
    public int minIdle;
    public Boolean testOnConnect;
    public Boolean testOnBorrow;
    public Boolean removeAbandoned;
    public Boolean testOnReturn;
    public Boolean testWhileIdle;
    public String validationQuery;
    public int timeBetweenEvictionRunsMillis;
    public int minEvictableIdleTimeMillis;
    public int validationInterval;

}
