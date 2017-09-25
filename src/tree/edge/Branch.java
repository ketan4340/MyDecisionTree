package tree.edge;

import data.value.AbstractValue;
import tree.node.Node;

public class Branch extends Edge<AbstractValue<?>> {
	
	public Branch(Node<AbstractValue<?>> from, Node<AbstractValue<?>> to, AbstractValue<?> jv) {
		super(from, to, jv);
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
		return "-" + label + "->";
	}
	
}