package com.ripple.etl.common.transcoding;

public class RecipeDeserializationException extends Throwable {
    //The Throwable class is the superclass of all errors and exceptions in the Java language.
    // Only objects that are instances of this class (or one of its subclasses)
    // are thrown by the Java Virtual Machine or can be thrown by the Java throw statement.
}

// If a client can reasonably be expected to recover from an exception, make it a checked exception.
// If a client cannot do anything to recover from the exception, make it an unchecked exception.

// Custom exceptions are very useful when we need to handle specific exceptions related to the business logic.
//  When used properly, they can serve as a practical tool for better exception handling and logging.