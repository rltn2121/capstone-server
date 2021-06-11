package com.capstone.mountain;

import org.python.util.PythonInterpreter;

public class JythonTest {
    private PythonInterpreter interpreter;

    public void test(){
        interpreter = new PythonInterpreter();
        interpreter.exec("print(s)");

    }
}
