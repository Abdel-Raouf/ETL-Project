package com.ripple.etl.scheduler;

// Custom exceptions provide you the flexibility to add attributes and methods that are not part of a standard Java exception.
// These can store additional information, like an application-specific error code,
// or provide utility methods that can be used to handle or present the exception to a user.
public class InvalidSourceException extends Throwable {
    public InvalidSourceException(String message) {
        // you create the instance of subclass, an instance of parent class is created implicitly
        // which is referred by super reference variable.
        super();
    }
}
