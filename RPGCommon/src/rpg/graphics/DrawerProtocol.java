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
				paramTypes[i] = s[1].equals("int") ? int.class
						: s[1].equals("double") ? double.class
								: s[1].equals("boolean") ? boolean.class : Class.forName(s[1]);
				paramValues[i] = paramTypes[i] == int.class ? Integer.parseInt(s[0])
						: paramTypes[i] == double.class ? Double.parseDouble(s[0])
								: paramTypes[i] == boolean.class ? Boolean.parseBoolean(s[0]) : s[0];
			}
			return (Drawer) cls.getConstructor(paramTypes).newInstance(paramValues);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
