package data.value;

import data.attribute.ContinuousAttribute;

public class ContinuousValue extends AbstractValue<Double> implements Comparable<ContinuousValue>{

	/** コンストラクタ */
	public ContinuousValue(double e, ContinuousAttribute ca) {
		super(e, ca);
	}
	public ContinuousValue(double e) {
		super(e);
	}

	@Override
	public String toString() {
		return "CV:" + elem;
	}

	@Override
	public int compareTo(ContinuousValue cv) {
		return Double.compare(super.elem, cv.elem);
	}
}