package maps;

/**
 * DuplicatePOIName class is a custom exception.
 * Used to identify when a user has entered a duplicate POI.
 * @author leomurphy
 *
 */
public class DuplicatePOIName extends Exception {
    /**
     * Constructor for the exception.
     * @param message used to output a specific message for the exception.
     */
	public DuplicatePOIName(String message) {
        super(message);
    }
}