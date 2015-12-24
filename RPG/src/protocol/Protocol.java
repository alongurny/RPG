package protocol;

public interface Protocol<S, T> {
	
	T encode(S d);
	
	S decode(T n);

	public static class ProtocolException extends RuntimeException {
		
		private static final long serialVersionUID = 1L;

		public ProtocolException(String msg) {
			super(msg);
		}
		
	}

	public static class ProtocolEncodingException extends ProtocolException {
		
		private static final long serialVersionUID = 1L;

		public ProtocolEncodingException(String msg) {
			super(msg);
		}

	}

	public static class ProtocolDecodingException extends ProtocolException {
		
		private static final long serialVersionUID = 1L;

		public ProtocolDecodingException(String msg) {
			super(msg);
		}
		
	}
}
