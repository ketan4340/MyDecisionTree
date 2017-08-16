package data.value;

import data.attribute.AbstractAttribute;

public abstract class AbstractValue {
	protected AbstractAttribute attr;

	public AbstractValue(AbstractAttribute a) {
		this.attr = a;
	}

	public abstract <E> E getElem();
}