package data.attribute;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import data.value.ContinuousValue;

/** 数値属性(連続値) */
public class ContinuousAttribute extends AbstractAttribute<Double> {
	/** コンストラクタ */
	public ContinuousAttribute(String label, Set<ContinuousValue> values) {
		super(label, new HashSet<>(values));
	}
	public ContinuousAttribute(String label) {
		super(label);
	}

	@Override
	public String toString() {
		return "CnAt:" + label;
	}
	public ContinuousValue getMin() {
		return (ContinuousValue) Collections.min(allValues);
	}
	public ContinuousValue getMax() {
		return (ContinuousValue) Collections.max(allValues);
	}
}