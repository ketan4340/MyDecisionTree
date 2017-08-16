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
	/** hashCode */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	/** equals */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContinuousValue other = (ContinuousValue) obj;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}
}