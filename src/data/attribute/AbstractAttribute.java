package data.attribute;

import java.util.HashSet;
import java.util.Set;

import data.value.AbstractValue;

public abstract class AbstractAttribute {
	protected String label;

	public AbstractAttribute(String l) {
		this.label = l;
	}
	public AbstractAttribute() {
		this("");
	}

	public abstract void addValue(AbstractValue av);

	/* getter */
	public String getLabel() {
		return label;
	}
}