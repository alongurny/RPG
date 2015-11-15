package rpg;

import java.util.HashMap;
import java.util.Map;

public class AbilityHandler {

	private Map<Ability, Long> timePassed;

	public AbilityHandler() {
		timePassed = new HashMap<>();
		new Thread(() -> {
			while (true) {
				timePassed.forEach((action, time) -> timePassed.put(action, time + 30));
				try {
					Thread.sleep(30);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
