package rpg.ability.fire;

import rpg.ability.EntitySpell;
import rpg.element.Entity;
import rpg.element.Lava;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.Sprite;
import rpg.graphics.TileDrawer;
import rpg.logic.level.Game;

public class LavaSpell extends EntitySpell {

	private Sprite sprite = TileDrawer.sprite(0, 13, 49, 52);

	public LavaSpell() {
		super(15, 15);
	}

	@Override
	protected void afterCast(Game game, Entity caster, Entity entity) {
		for (int y = -1; y <= 1; y++) {
			for (int x = -1; x <= 1; x++) {
				Vector2D v = entity.getLocation().add(new Vector2D(x, y).multiply(32));
				if (game.getElements(v).stream().allMatch(e -> e.isPassable(game, entity))) {
					Lava lava = new Lava(v, caster);
					game.addDynamicElement(lava);
					game.addTimer(5.0, () -> game.removeDynamicElement(lava));
				}
			}
		}
	}

	@Override
	public Drawer getSelfDrawer() {
		return sprite;
	}

}
