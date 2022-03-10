package com.demo.aerospike.entity;

import org.springframework.data.aerospike.annotation.Expiration;
import org.springframework.data.annotation.Id;
import java.io.Serializable;

public class User implements Serializable {

    @Id
    private String id;

    private String envelope;

    private String testString;

    @Expiration
    private long expiration;

    public User(String id) {
        this.id = id;
    }

    public User(String id, String envelope, String testString, long expiration) {
        this.id = id;
        this.envelope = envelope;
        this.testString = testString;
        this.expiration= expiration;
    }

    public String getId() {
        return id;
    }

    public String getEnvelope() {
        return envelope;
    }

    public void setEnvelope(String envelope) {
        this.envelope = envelope;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", envelope='" + envelope + '\'' +
                ", testString='" + testString + '\'' +
                '}';
    }
}
