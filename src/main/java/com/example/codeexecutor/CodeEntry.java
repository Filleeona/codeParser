package com.example.codeexecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CodeEntry {
    private String name;
    private String code;
    private String timestamp;

    public CodeEntry(String name, String code) {
        this.name = name;
        this.code = code;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
