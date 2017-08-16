package data.value;

import data.attribute.AbstractAttribute;

public abstract class AbstractValue {
	protected AbstractAttribute attr;

	public AbstractValue(AbstractAttribute a) {
		this.attr = a;
	}

	public abstract <E> E getElem();

	/** hashCode/equals */
	/** hashCode */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attr == null) ? 0 : attr.hashCode());
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
		AbstractValue other = (AbstractValue) obj;
		if (attr == null) {
			if (other.attr != null)
				return false;
		} else if (!attr.equals(other.attr))
			return false;
		return true;
	}
}