package tree.edge;

import data.value.AbstractValue;

public class Branch extends Edge {
	private AbstractValue selectedVal;

	public Branch(AbstractValue jv) {
		super();
		this.selectedVal = jv;
	}
	public Branch() {
		this(null);
	}
}
