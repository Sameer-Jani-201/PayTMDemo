package com.paytmdemo.crypto;

import java.io.PrintStream;

/**
 * Created by Sameer Jani on 29/1/18.
 */
public class SecurityException extends Exception {
    private static final long serialVersionUID = -3956900350777254445L;
    private String errorCode;
    private String errorMessage;
    private Exception exception;

    public SecurityException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public SecurityException(String errorMessage) {
        super(errorMessage);
    }

    public SecurityException(Throwable cause) {
        super(cause);
    }

    public SecurityException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public SecurityException(String errorMessage, Exception exception) {
        this.errorMessage = errorMessage;
        this.exception = exception;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void printStackTrace(PrintStream stream) {
        if (this.exception != null) {
            this.exception.printStackTrace(stream);
        }

    }
}
