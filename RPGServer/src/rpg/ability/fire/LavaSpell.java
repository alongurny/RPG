package rpg.ability.fire;

import rpg.ability.EntityTargetAbility;
import rpg.element.Element;
import rpg.element.ability.Lava;
import rpg.element.entity.Entity;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.Sprite;
import rpg.graphics.TileDrawer;
import rpg.logic.level.Game;

public class LavaSpell extends EntityTargetAbility {

	private Sprite sprite = TileDrawer.sprite(0, 13, 49, 52);

	public LavaSpell() {
		super(15, 15);
	}

	@Override
	public Drawer getSelfDrawer() {
		return sprite;
	}

	@Override
	protected boolean isCastable(Entity caster, Entity target) {
		return Element.distance(caster, target) <= 120;
	}

	@Override
	protected void onCast(Game game, Entity caster, Entity entity) {
		for (int x = -1; x <= 1; x++) {
			Vector2D v = entity.getLocation().add(new Vector2D(x, 0).multiply(32));
			if (game.getElementsAt(v).stream().allMatch(e -> e.isPassable(game, entity))) {
				Lava lava = new Lava(v, caster);
				game.addDynamicElement(lava);
				game.addTimer(5.0, () -> game.removeDynamicElement(lava));
			}
		}
	}

}
