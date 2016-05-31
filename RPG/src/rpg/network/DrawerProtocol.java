package rpg.network;

import protocol.Protocol;
import rpg.graphics.draw.Drawer;

public class DrawerProtocol implements Protocol<Drawer, String> {

	@Override
	public String encode(Drawer d) {
		return d.represent();
	}

	@Override
	public Drawer decode(String str) {
		String[] arr = str.split(" ");
		String type = arr[0];
		try {
			Class<?> cls = Class.forName("rpg.graphics." + type);
			Class<?>[] paramTypes = new Class<?>[arr.length - 1];
			Object[] paramValues = new Object[arr.length - 1];
			for (int i = 0; i < arr.length - 1; i++) {
				String[] s = arr[i + 1].split(":");
				paramTypes[i] = s[1].equals("int") ? int.class
						: s[1].equals("double") ? double.class : Class.forName(s[1]);
				paramValues[i] = paramTypes[i] == int.class ? Integer.parseInt(s[0])
						: paramTypes[i] == double.class ? Double.parseDouble(s[0]) : s[0];
			}
			return (Drawer) cls.getConstructor(paramTypes).newInstance(paramValues);
		} catch (Exception e) {
			e.printStackTrace();
			return Drawer.empty();
		}
	}

}
