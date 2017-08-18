package tree.edge;

import data.value.AbstractValue;
import tree.node.Node;

public class Branch extends Edge {
	private AbstractValue<?> selectedVal;

	public Branch(Node from, Node to, AbstractValue<?> jv) {
		super(from, to);
		this.selectedVal = jv;
	}
	public Branch(AbstractValue<?> jv) {
		this(null, null, jv);
	}
	public Branch() {
		this(null);
	}

	/** toString */
	@Override
	public String toString() {
		return "-" + selectedVal + "->";
	}

}