
package org.mule.module.google.calendar.connection;

import javax.annotation.Generated;


/**
 * Exception thrown when the connection needed for executing an
 *  operation is null.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-18T03:26:10-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class UnableToAcquireConnectionException
    extends Exception
{

    /**
     * Create a new exception
     */
    public UnableToAcquireConnectionException() {
    }

    /**
     * Create a new exception
     */
    public UnableToAcquireConnectionException(String message) {
        super(message);
    }

    /**
     * Create a new exception
     *
     * @param throwable Inner exception
     */
    public UnableToAcquireConnectionException(Throwable throwable) {
        super(throwable);
    }
}
