package com.example.codeexecutor;

import com.example.codeexecutor.CodeEntry;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api")
public class CodeController {

    private final List<CodeEntry> savedCodes = new ArrayList<>();
    private final AtomicInteger classCounter = new AtomicInteger();

    @PostMapping("/saveCode")
    public List<CodeEntry> saveCode(@RequestBody CodeEntry codeData) {
        savedCodes.add(new CodeEntry(codeData.getName(), codeData.getCode()));
        return savedCodes;
    }

    @GetMapping("/getCodes")
    public List<CodeEntry> getCodes() {
        return savedCodes;
    }

    @DeleteMapping("/deleteCode/{index}")
    public List<CodeEntry> deleteCode(@PathVariable int index) {
        if (index >= 0 && index < savedCodes.size()) {
            savedCodes.remove(index);
        }
        return savedCodes;
    }

    @PostMapping("/executeCode")
    public String executeCode(@RequestBody String code) {
        try {
            Parser parser = new Parser(code);
            boolean success = parser.parseAndEvaluate();
            if (success) {
                return "Результат выполнения: " + parser.getVariableValue("x");
            } else {
                return "Ошибка: Неправильный формат выражения";
            }
        } catch (IllegalArgumentException | ArithmeticException e) {
            return "Ошибка выполнения кода: " + e.getMessage();
        }
    }
}
