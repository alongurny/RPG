package rpg.network.tcp.chat.message;

public class Message {

	public enum Type {
		DATA, METADATA
	}

	public static abstract class Source {

		public abstract String getHostName();

		public static final Source SERVER = new Source() {
			@Override
			public String toString() {
				return "SERVER";
			};

			@Override
			public String getHostName() {
				return "<<server>>";
			};
		};

		public static boolean isServer(Source source) {
			return source == SERVER;
		}

		public static boolean isFriend(Source source) {
			return source instanceof FriendSource;
		}
	}

	private static class FriendSource extends Source {

		private String host;

		@Override
		public String getHostName() {
			return host;
		}
	}

	public static abstract class Target {

		public static final Target SERVER = new Target() {
			@Override
			public String toString() {
				return "SERVER";
			}

			@Override
			public String getHostName() {
				return "<<server>>";
			}
		};
		public static final Target BROADCAST = new Target() {
			@Override
			public String toString() {
				return "BROADCAST";
			}

			@Override
			public String getHostName() {
				return "<<broadcast>>";
			}
		};

		public static FriendTarget friend(String host) {
			return new FriendTarget(host);
		}

		public static Target valueOf(String str) {
			switch (str) {
			case "SERVER":
				return SERVER;
			case "BROADCAST":
				return BROADCAST;
			default:
				if (str.startsWith("FRIEND ")) {
					return new FriendTarget(str.replace("FRIEND ", ""));
				}
				throw new RuntimeException("No match");
			}
		}

		public abstract String getHostName();

		private Target() {

		}

		public static boolean isBroadcast(Target target) {
			return target == BROADCAST;
		}

		public static boolean isServer(Target target) {
			return target == SERVER;
		}

		public static boolean isFriend(Target target) {
			return target instanceof FriendTarget;
		}
	}

	private static class FriendTarget extends Target {
		private String host;

		private FriendTarget(String host) {
			this.host = host;
		}

		public String getHostName() {
			return host;
		}

		@Override
		public String toString() {
			return "FRIEND " + host;
		}
	}

	private String data;
	private long created;
	private Type type;
	private Target target;

	private Message(Target target, Type type, String data, long created) {
		this.target = target;
		this.type = type;
		this.data = data;
		this.created = created;
	}

	private Message(Target target, Type type, String data) {
		this(target, type, data, System.currentTimeMillis());
	}

	public static Message create(Target target, Type type, String data) {
		return new Message(target, type, data);
	}

	public static Message metadata(Target target, String metadata) {
		return new Message(target, Type.METADATA, metadata);
	}

	public String getData() {
		return data;
	}

	public long getCreationTime() {
		return created;
	}

	public Type getType() {
		return type;
	}

	public Target getTarget() {
		return target;
	}

	@Override
	public String toString() {
		return String.format("Message { type: %s, data: %s }", type, data);
	}

	public static Message data(Target target, String text) {
		return new Message(target, Type.DATA, text);
	}

	public static Message data(String text) {
		return new Message(Target.BROADCAST, Type.DATA, text);
	}

}
