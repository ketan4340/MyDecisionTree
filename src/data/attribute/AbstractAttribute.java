package data.attribute;

import java.util.HashSet;
import java.util.Set;

import data.value.AbstractValue;

public abstract class AbstractAttribute<V extends AbstractValue<?>> {
	protected String label;
	protected Set<V> allValues;

	protected AbstractAttribute(String l, Set<V> values) {
		this.label = l;
		this.allValues = values;
	}
	protected AbstractAttribute(String l) {
		this(l, new HashSet<V>());
	}

	public void addValue(V value) {
		allValues.add(value);
	}

	/** getter */
	public String getLabel() {
		return label;
	}
	public Set<V> getAllValues() {
		return allValues;
	}

	/** Object基本メソッド */
	/** hashCode */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}
	/** equals */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractAttribute<V> other = (AbstractAttribute<V>) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	/** toString */
	@Override
	public String toString() {
		return "At:" + label;
	}
}