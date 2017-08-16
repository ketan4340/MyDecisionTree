package data.value;

import data.attribute.ContinuousAttribute;

public class ContinuousValue extends AbstractValue {
	private double value;

	public ContinuousValue(ContinuousAttribute ca, double v) {
		super(ca);
		this.value = v;
		ca.addValue(this);
	}

	@Override
	public Double getElem() {
		return value;
	}
}