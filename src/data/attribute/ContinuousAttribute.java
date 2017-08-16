package data.attribute;

import data.value.AbstractValue;
import data.value.ContinuousValue;

/** 数値属性(連続値) */
public class ContinuousAttribute extends AbstractAttribute {
	private double min;
	private double max;

	public ContinuousAttribute(String label, double min, double max) {
		super(label);
		this.min = min;
		this.max = max;
	}
	public ContinuousAttribute(String label) {
		this(label, Double.MAX_VALUE, Double.MIN_VALUE);
	}

	@Override
	public void addValue(AbstractValue av) {
		ContinuousValue cv = (ContinuousValue) av;
		double v = cv.getElem();
		if (v < min) min = v;
		if (v > max) max = v;
	}

	/* getter */
	public double getMin() {
		return min;
	}
	public double getMax() {
		return max;
	}
}