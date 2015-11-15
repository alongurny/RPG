package rpg;

public class Bar {

	private double value, maximum;

	public Bar(double maximum) {
		this(maximum, maximum);
	}

	public Bar(double value, double maximum) {
		this.value = value;
		this.maximum = maximum;
	}

	public double getValue() {
		return value;
	}

	public void addValue(double dvalue) {
		setValue(value + dvalue);
	}

	public void setValue(double value) {
		this.value = Math.min(value, maximum);
	}

	public double getMaximum() {
		return maximum;
	}

	public void addMaximum(double dmaximum) {
		setMaximum(maximum + dmaximum);
	}

	public void setMaximum(double maximum) {
		this.maximum = maximum;
		this.value = Math.min(value, maximum);
	}
}
