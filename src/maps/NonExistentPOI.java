package maps;
/**
 * NonExistentPOI class is a custom exception.
 * Used to identify when a POI is being searched for that does not exist.
 * @author leomurphy
 *
 */
public class NonExistentPOI extends Exception {
    /**
     * Constructor class for exception.
     * @param message Allows user to create a custom message when exception is caught.
     */
	public NonExistentPOI(String message) {
        super(message);
    }
}
