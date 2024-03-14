package projet.vlille.exception;

public class StationFullException extends Exception{
    private static final long serialVersionUID = 1L;

    /**
     * The exception that signifies that a station is full
     */
	public StationFullException() {
        super();
    }
    
    /**
     * The exception that signifies that a station is full
     * @param arg the message to be displayed
     */
    public StationFullException(String arg) {
        super(arg);
    }
}
