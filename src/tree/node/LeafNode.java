package tree.node;

import java.util.LinkedList;
import java.util.stream.Collectors;

import data.value.AbstractValue;
import tree.edge.Branch;

public class LeafNode extends Node<AbstractValue<?>> {

	/* コンストラクタ */
	public LeafNode(Branch parent, AbstractValue<?> classValue) {
		super(parent, new LinkedList<>(), classValue);
	}
	public LeafNode(AbstractValue<?> classValue) {
		this(null, classValue);
	}
	public LeafNode() {
		this((AbstractValue<?>)null);
	}

	/** toString */
	@Override
	public String toString() {
		return "(" + label + ")\n" +
				childEdges.stream()
				.map(e -> e.toString()+e.getToNode())
				.collect(Collectors.joining(","));
	}

	@Override
	public boolean isLeaf() {
		return true;
	}
	
}