package com.pb.kanivets.bki.core.wrappers;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionWrapper {

    private String message;
    private String exceptionType;
    private String stackTrace;

    public ExceptionWrapper() {

    }

    public ExceptionWrapper(Throwable t) {
        message = t.getMessage();
        exceptionType = t.getClass().toString();
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));

        stackTrace = sw.toString();
    }

    @Override
    public String toString() {
        return "ExceptionWrapper{" + "message=" + message + ", exceptionType=" + exceptionType + ", stackTrace=" + stackTrace + '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

}
