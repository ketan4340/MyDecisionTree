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

}
