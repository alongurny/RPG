package network.protocol;

/**
 * This interface represents a unidirection protocol - in this case, a way to
 * convert a value of some type to a value of another type.
 * 
 * @author Alon
 *
 * @param <S>
 *            the type to encode
 */
public interface OneWayProtocol<S, T> {

	T encode(S d);

}
