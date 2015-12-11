package rpg.network.tcp.chat.message;

import rpg.network.protocol.Protocol;

public class MessageDeliveryProtocol implements Protocol<Message, String> {

	@Override
	public String encode(Message message) {
		return String.format("%s :: %s :: %s", message.getTarget(), message
				.getType().toString(), message.getData());
	}

	@Override
	public Message decode(String data) {
		try {
			String[] arr = data.split(" :: ");
			if (arr.length == 2) {
				arr = new String[] { arr[0], arr[1], "" };
			}
			return Message.create(Message.Target.valueOf(arr[0]),
					Message.Type.valueOf(arr[1]), arr[2]);
		} catch (RuntimeException e) {
			throw new RuntimeException("Error, data = " + data, e);
		}
	}
}
