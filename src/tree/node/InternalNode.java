package tree.node;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import data.Tuple;
import data.attribute.AbstractAttribute;
import data.value.AbstractValue;
import tree.edge.Branch;
import tree.edge.Edge;

/**
 * 内部ノード．必ず子ノードをもつ．決定木がルートノードのみの場合はLeafNodeを使う．
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
	
	/**
	 * 渡されたタプルがもつ属性値と同じ枝の先につながる子ノードを得る．
	 * @param tuple 参照するタプル
	 * @return タプルが進む先の子ノード
	 */
	public Node<?> getChildMatchTuple(Tuple tuple) {
		AbstractValue<?> val = tuple.getValueInAttr(label);
		Branch matchedBranch = getBranchHaveValue(val);
		return matchedBranch.getToNode();
	}
	/**
	 * 与えられた属性値をもつ枝を探す
	 * @param val 属性値
	 * @return 指定の属性値をもつ枝
	 */
	private Branch getBranchHaveValue(AbstractValue<?> val) {
		for (Edge<?> edge : childEdges) {
			Branch branch = (Branch) edge;
			if (val.equals(branch.getLabel()))
				return branch;
		}
		return null;
	}
}