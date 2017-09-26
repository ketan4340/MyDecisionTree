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

	/** コンストラクタ */
	public InternalNode(Branch parent, List<Branch> children, AbstractAttribute<?> judgeAttr) {
		super(parent, new LinkedList<>(children), judgeAttr);
	}
	public InternalNode(AbstractAttribute<?> judgeAttr) {
		this(null, new LinkedList<>(), judgeAttr);
	}
	public InternalNode() {
		this((AbstractAttribute<?>) null);
	}
	
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