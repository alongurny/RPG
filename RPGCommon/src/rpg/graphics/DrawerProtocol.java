package rpg.graphics;

import java.util.Arrays;

import network.protocol.Protocol;

public class DrawerProtocol implements Protocol<Drawer, String> {

	@Override
	public Drawer decode(String str) {
		return Arrays.stream(str.split(";;")).map(this::decodeOne).reduce((a, b) -> a.andThen(b)).get();
	}

	@Override
	public String encode(Drawer d) {
		return d.toString();
	}

	private Drawer decodeOne(String str) {
		String[] arr = str.split(" ");
		String type = arr[0];
		Class<?>[] paramTypes = null;
		Object[] paramValues = null;
		try {
			Class<?> cls = Class.forName(type);
			paramTypes = new Class<?>[arr.length - 1];
			paramValues = new Object[arr.length - 1];
			for (int i = 0; i < arr.length - 1; i++) {
				String[] s = arr[i + 1].split(":");
				switch (s[1]) {
				case "int":
					paramTypes[i] = int.class;
					paramValues[i] = Integer.parseInt(s[0]);
					break;
				case "double":
					paramTypes[i] = double.class;
					paramValues[i] = Double.parseDouble(s[0]);
					break;
				case "boolean":
					paramTypes[i] = boolean.class;
					paramValues[i] = Boolean.parseBoolean(s[0]);
					break;
				default:
					paramTypes[i] = String.class;
					paramValues[i] = s[0];
					break;
				}
			}
			return (Drawer) cls.getConstructor(paramTypes).newInstance(paramValues);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
