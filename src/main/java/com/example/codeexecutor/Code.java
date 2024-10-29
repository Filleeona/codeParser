package com.example.codeexecutor;

import java.time.LocalDateTime;

public class Code {
    private String name;
    private String code;
    private LocalDateTime timestamp;

    public Code() {
        // Пустой конструктор
    }

    public Code(String name, String code, LocalDateTime timestamp) {
        this.name = name;
        this.code = code;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
