package projet.vlille.exception;

public class OutOfServiceException extends Exception{
	 private static final long serialVersionUID = 1L;

	/**
     * The exception that is thrown when a mean of transport is out of service
     */
	public OutOfServiceException() {
	    super();
	}
    
	/**
	 * The exception that is thrown when a mean of transport is out of service
	 * @param arg the message to be displayed
	 */
	public OutOfServiceException(String arg) {
	    super(arg);
	}
}
