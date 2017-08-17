package data.attribute;

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

	/** getter */
	public String getLabel() {
		return label;
	}

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
		AbstractAttribute other = (AbstractAttribute) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
}