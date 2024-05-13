package maps;
/**
 * DuplicateLayerException class is a custom exception.
 * Used to identify when program enters a layer that alredy exists. 
 * @author leomurphy
 *
 */
public class DuplicateLayerException extends Exception {
	/**
	 * Constructor for custom exception.
	 * @param message Allows user to create a custom message when the exception is caught.
	 */
	public DuplicateLayerException(String message)
	{
		super(message);
	}
}
