package projet.vlille.exception;

public class StationEmptyException extends Exception{
    private static final long serialVersionUID = 1L;

    /**
     * The exception that signifies that a station is empty
     */
	public StationEmptyException() {
        super();
    }
    
    /**
     * The exception that signifies that a station is empty
     * @param arg the message to be displayed
     */
    public StationEmptyException(String arg) {
        super(arg);
    }
}
