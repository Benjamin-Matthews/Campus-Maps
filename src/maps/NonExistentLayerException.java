package maps;
/**
 * NonExistentLayerException class is a custom exception.
 * Used when a layer that does not exist is searched for.
 * @author leomurphy
 *
 */
public class NonExistentLayerException extends Exception {
	/**
	 * Constructor for exception.
	 * @param message Allows user to create a custom message when exception is caught.
	 */
	public NonExistentLayerException(String message)
	{
		super(message);
	}
}
