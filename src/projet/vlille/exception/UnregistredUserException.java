package projet.vlille.exception;

public class UnregistredUserException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
     * The exception that signifies an unregistered user
     */
	public UnregistredUserException() {
	      super();
	}
    
	/**
	 * The exception that signifies an unregistered user
	 * @param arg the message to be displayed
	 */
	public UnregistredUserException(String arg) {
	      super(arg);
	}
}
