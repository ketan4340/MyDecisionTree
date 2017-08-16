package tree.node;

import data.value.AbstractValue;

public class LeafNode extends Node {
	private AbstractValue classValue;

	public LeafNode(AbstractValue cv) {
		super();
		this.classValue = cv;
	}
	public LeafNode() {
		this(null);
	}
}
