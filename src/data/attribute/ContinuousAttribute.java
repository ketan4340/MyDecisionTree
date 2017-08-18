package data.attribute;

import java.util.Collections;
import java.util.Set;

import data.value.ContinuousValue;

/** 数値属性(連続値) */
public class ContinuousAttribute extends AbstractAttribute<ContinuousValue> {
	//private static ContinuousValue MIN = new ContinuousValue(Double.MIN_VALUE);
	//private static ContinuousValue MAX = new ContinuousValue(Double.MAX_VALUE);

	public ContinuousAttribute(String label, Set<ContinuousValue> values) {
		super(label, values);
	}
	public ContinuousAttribute(String label) {
		super(label);
	}

	@Override
	public String toString() {
		return "CnAt:" + label;
	}
	public ContinuousValue getMin() {
		return Collections.min(allValues);
	}
	public ContinuousValue getMax() {
		return Collections.max(allValues);
	}
}