package com.alexcomeau.config;

public class Config {
    private Database[] relational;
    private Database[] keyValue;
    private int port;
    private String passwordSha256;
    private int defaultRelationalIndex;
    private int defaultKeyValueIndex;

    public Database[] getRelational() {
        return this.relational;
    }

    public void setRelational(Database[] relational) {
        this.relational = relational;
    }

    public Database[] getKeyValue() {
        return this.keyValue;
    }

    public void setKeyValue(Database[] keyValue) {
        this.keyValue = keyValue;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPasswordSha256() {
        return this.passwordSha256;
    }

    public void setPasswordSha256(String passwordSha256) {
        this.passwordSha256 = passwordSha256;
    }

    public int getDefaultRelationalIndex() {
        return this.defaultRelationalIndex;
    }

    public void setDefaultRelationalIndex(int defaultRelationalIndex) {
        this.defaultRelationalIndex = defaultRelationalIndex;
    }

    public int getDefaultKeyValueIndex() {
        return this.defaultKeyValueIndex;
    }

    public void setDefaultKeyValueIndex(int defaultKeyValueIndex) {
        this.defaultKeyValueIndex = defaultKeyValueIndex;
    }

}
