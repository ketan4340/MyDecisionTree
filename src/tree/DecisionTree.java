package tree;

import java.util.HashSet;
import java.util.Set;

import tree.edge.Edge;
import tree.node.Node;

public class DecisionTree {
	private Node root;

	public DecisionTree(Node r) {
		this.root = r;
	}
	public DecisionTree() {
		this(null);
	}

	/** getter/setter */
	public Node getRoot() {
		return root;
	}
	public void setRoot(Node rn) {
		this.root = rn;
	}
}