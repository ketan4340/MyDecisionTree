package data.value;

import data.attribute.NominalAttribute;

public class NominalValue extends AbstractValue{
	private String value;

	public NominalValue(NominalAttribute na, String v) {
		super(na);
		this.value = v;
		na.addValue(this);
	}

	@Override
	public String getElem() {
		return value;
	}
	/** hashCode */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		NominalValue other = (NominalValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}