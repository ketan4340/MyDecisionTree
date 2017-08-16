package tree.node;

import data.attribute.AbstractAttribute;

public class InternalNode extends Node{
	private AbstractAttribute judgeAttr;

	public InternalNode(AbstractAttribute ja) {
		super();
		this.judgeAttr = ja;
	}
	public InternalNode() {
		this(null);
	}
}
