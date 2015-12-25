package tcp.chat.message;

import protocol.Protocol;

public class MessageDeliveryProtocol implements Protocol<Message, String> {

	@Override
	public String encode(Message message) {
		return String.format("%s ::: %s ::: %s ::: %s", message.getSource(), message.getTarget(),
				message.getType().toString(), message.getData());
	}

	@Override
	public Message decode(String data) {
		try {
			String[] arr = data.split(" ::: ");
			if (arr.length == 3) {
				arr = new String[] { arr[0], arr[1], arr[2], "" };
			}
			return Message.create(Message.Source.valueOf(arr[0]), Message.Target.valueOf(arr[1]),
					Message.Type.valueOf(arr[2]), arr[3]);
		} catch (RuntimeException e) {
			throw new RuntimeException("Wrong data: " + data, e);
		}
	}
}
