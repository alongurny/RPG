package rpg.element.entity;

/**
 * This class represents an attribute of an entity. Currently four attributes
 * are supported: {@link #STR}, {@link #DEX}, {@link #INT} and {@link #CON}.
 * Each one determines other parts of an entity's abilities and properties.
 * 
 * 
 * @author Alon
 *
 */
public enum Attribute {

	/**
	 * STRENGTH is measure of how strong an entity is. Determines their physical
	 * power.
	 */
	STR,
	/**
	 * DEXTERITY is a measure of how quick an entity is. Determines their speed.
	 */
	DEX,
	/**
	 * INTELLIGENCE is a measure of how smart an entity is. Determines their
	 * mana.
	 */
	INT,
	/**
	 * CONSTITUTION is a measure of how healthy an entity is. Determines their
	 * health.
	 */
	CON;

}
