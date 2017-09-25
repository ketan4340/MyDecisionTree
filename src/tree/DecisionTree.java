package tree;

import data.Record;
import data.Tuple;
import data.attribute.Attributelist;
import data.value.NominalValue;
import tree.edge.Branch;
import tree.node.InternalNode;
import tree.node.Node;

public class DecisionTree {
	private Node<?> root;	// ルートノード

	/* コンストラクタ */
	public DecisionTree(Node<?> root) {
		this.root = root;
	}
	public DecisionTree() {
		this(null);
	}

	
	/* getter/setter */
	public Node<?> getRoot() {
		return root;
	}
	public void setRoot(Node<?> rootNode) {
		this.root = rootNode;
	}
	@Override
	public String toString() {
		return "DecisionTree [" + root + "]";
	}
	
	public boolean applyRecord(Record record, Attributelist attrlist) {
		NominalValue decisionClassValue = applyTuple(record.getTuple(), attrlist);
		NominalValue actualClassValue = record.getClassValue();
		return false;
	}
	private NominalValue applyTuple(Tuple tuple, Attributelist attrlist) {
		for (Node<?> node = root; !node.isLeaf(); ) {
			InternalNode inNode = (InternalNode) node;
						
			Node<?> nextNode = inNode.getChildMatchTuple(tuple, attrlist);
		}
		return null;
	}
	
}