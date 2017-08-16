package tree.node;

import data.value.AbstractValue;

public class LeafNode extends Node {
	private AbstractValue classAttrValue;

	public LeafNode(AbstractValue cav) {
		super();
		this.classAttrValue = cav;
	}
	public LeafNode() {
		this(null);
	}
}
