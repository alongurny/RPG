package network.protocol;

/**
 * This interface represents a protocol - in this case, a way to convert a value
 * of some type to a value of another type and back.
 * 
 * @author Alon
 *
 * @param <S>
 *            the type to encode
 * @param <T>
 *            the type that is received after encoding
 */
public interface TwoWayProtocol<S, T> {

	/**
	 * Decodes a value.
	 * 
	 * @param the
	 *            value to decode
	 * @return the decoded value
	 */
	S decode(T n);

	/**
	 * Encodes a value.
	 * 
	 * @param the
	 *            value to encode
	 * @return the encoded value
	 */
	T encode(S d);

}
