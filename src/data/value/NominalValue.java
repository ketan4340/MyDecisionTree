package data.value;

import data.attribute.NominalAttribute;

public class NominalValue extends AbstractValue<String>{

	/** コンストラクタ */
	public NominalValue(String e, NominalAttribute na) {
		super(e, na);
	}
	public NominalValue(String e) {
		super(e);
	}

	@Override
	public String toString() {
		return "NV:" + elem;
	}
}