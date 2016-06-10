package rpg.ability.damage;

import rpg.element.entity.Entity;

/**
 * Some abilities do damage to targets. This enumeration provides several types
 * of damage. Some entities are resistant or immune to certain types of damage.
 * 
 * @see Entity#damage(double, DamageType) Entity.damage
 * @see Entity#getResistance(DamageType) Entity.getResistance
 * 
 * @author Alon
 *
 */
public enum DamageType {
	PHYSICAL, FIRE, COLD, ELECTRICAL, FORCE
}
