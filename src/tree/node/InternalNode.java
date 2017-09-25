package tree.node;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import data.Tuple;
import data.attribute.AbstractAttribute;
import data.attribute.Attributelist;
import data.value.AbstractValue;
import tree.edge.Branch;
import tree.edge.Edge;

/**
 * 内部ノード。現状ルートノードのみの場合にも使えるが、やめたほうがきれいかも。
 * @author tanabekentaro
 */
public class InternalNode extends Node<AbstractAttribute<?>>{
	//private AbstractAttribute<?> judgeAttr;	// 判定に使う属性

	/** コンストラクタ */
	public InternalNode(Branch parent, List<Branch> children, AbstractAttribute<?> judgeAttr) {
		//super(parent, children, judgeAttr);
		super();
		this.parentEdge = parent;
		this.childEdges.addAll(children);
	}
	public InternalNode(AbstractAttribute<?> judgeAttr) {
		this(null, new LinkedList<>(), judgeAttr);
	}
	public InternalNode() {
		this((AbstractAttribute<?>) null);
	}

	/* getter/setter */
	/*
	public AbstractAttribute<?> getJudgeAttr() {
		return judgeAttr;
	}
	public void setJudgeAttr(AbstractAttribute<?> judgeAttr) {
		this.judgeAttr = judgeAttr;
	}
	*/
	
	/** toString */
	@Override
	public String toString() {
		return "[" + label + "](" +
	childEdges.stream()
	.map(e -> e.toString()+e.getToNode())
	.collect(Collectors.joining(",")) + ")";
	}
	
	@Override
	public boolean isLeaf() {
		return false;
	}
	
	/**
	 * このInternalNodeをLeafNodeに置き換える。置き換えるLeafNodeはこのInternalNodeに接続されているEdgeを引き継ぐ。
	 * @param classValue 置き換えるLeafNodeのクラス属性値
	 * @return 置き換えたLeafNode
	 */
	/*
	public LeafNode toLeaf(AbstractValue<?> classValue) {
		return new LeafNode(getParentEdge(), getChildEdges(), classValue);
	}
	*/
	public Node<?> getChildMatchTuple(Tuple tuple, Attributelist attrlist) {
		int attrIndex = attrlist.indexOf(label);
		AbstractValue<?> val = tuple.get(attrIndex);
		Branch matchedBranch = getMatchedBranch(val);
		
		return null;
	}
	private Branch getMatchedBranch(AbstractValue<?> val) {
		for (Edge<?> edge : childEdges) {
			Branch branch = (Branch) edge;
		}
		return null;
	}
}