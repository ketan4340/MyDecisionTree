package tree.node;

import java.util.LinkedList;
import java.util.List;

import data.attribute.AbstractAttribute;
import data.value.AbstractValue;
import tree.edge.Edge;

public class LeafNode extends Node {
	private AbstractValue<?> classValue;

	/** コンストラクタ */
	public LeafNode(Edge parent, List<Edge> children, AbstractValue<?> classValue) {
		super(parent, children);
		this.classValue = classValue;
	}
	public LeafNode(AbstractValue<?> classValue) {
		this(null, new LinkedList<>(), classValue);
	}
	public LeafNode() {
		this((AbstractValue<?>)null);
	}
	public LeafNode(Node n, AbstractValue<?> classValue) {
		super(n);
		this.classValue = classValue;
	}

	/** getter/setter */
	public AbstractValue<?> getClassValue() {
		return classValue;
	}
	public void setClassValue(AbstractValue<?> classValue) {
		this.classValue = classValue;
	}

	/**
	 * このLeafNodeをInternalNodeに置き換える。置き換えるInternalNodeはこのLeafNodeに接続されているEdgeを引き継ぐ。
	 * @param judgeAttr 置き換えるInternalNodeの判定属性
	 * @return 置き換えたInternalNode
	 */
	public InternalNode toInternal(AbstractAttribute<?> judgeAttr) {
		return new InternalNode(
				this.getParentEdge(),
				this.getChildEdges(),
				judgeAttr);
	}
}