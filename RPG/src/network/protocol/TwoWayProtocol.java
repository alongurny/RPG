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

	S decode(T n);

	T encode(S d);

}
