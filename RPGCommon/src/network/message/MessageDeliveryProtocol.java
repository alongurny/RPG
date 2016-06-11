package network.message;

import network.protocol.Protocol;

/**
 * This protocol is used to encode and decode messages.
 * 
 * @author Alon
 *
 */
public class MessageDeliveryProtocol implements Protocol<Message, String> {

	@Override
	public Message decode(String data) {
		try {
			String[] arr = data.split(" ::: ");
			if (arr.length == 1) {
				arr = new String[] { arr[0], "" };
			}
			return Message.create(Message.Type.valueOf(arr[0]), arr[1]);
		} catch (RuntimeException e) {
			throw new RuntimeException("Wrong data: " + data, e);
		}
	}

	@Override
	public String encode(Message message) {
		return String.format("%s ::: %s", message.getType().toString(), message.getData());
	}
}
