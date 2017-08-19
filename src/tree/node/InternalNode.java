package tree.node;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import data.attribute.AbstractAttribute;
import data.value.AbstractValue;
import tree.edge.Edge;

public class InternalNode extends Node{
	private AbstractAttribute<?> judgeAttr;

	/** コンストラクタ */
	public InternalNode(Edge parent, List<Edge> children, AbstractAttribute<?> judgeAttr) {
		super(parent, children);
		this.judgeAttr = judgeAttr;
	}
	public InternalNode(AbstractAttribute<?> judgeAttr) {
		this(null, new LinkedList<>(), judgeAttr);
	}
	public InternalNode() {
		this((AbstractAttribute<?>)null);
	}
	public InternalNode(Node n, AbstractAttribute<?> judgeAttr) {
		super(n);
		this.judgeAttr = judgeAttr;
	}

	/* getter/setter */
	public AbstractAttribute<?> getJudgeAttr() {
		return judgeAttr;
	}
	public void setJudgeAttr(AbstractAttribute<?> judgeAttr) {
		this.judgeAttr = judgeAttr;
	}
	/** toString */
	@Override
	public String toString() {
		return "[" + judgeAttr + "](" +
	childEdges.stream()
	.map(e -> e.toString()+e.getToNode())
	.collect(Collectors.joining(",")) + ")";
	}
	/**
	 * このInternalNodeをLeafNodeに置き換える。置き換えるLeafNodeはこのInternalNodeに接続されているEdgeを引き継ぐ。
	 * @param classValue 置き換えるLeafNodeのクラス属性値
	 * @return 置き換えたLeafNode
	 */
	public LeafNode toLeaf(AbstractValue<?> classValue) {
		return new LeafNode(
				this.getParentEdge(),
				this.getChildEdges(),
				classValue);
	}
}