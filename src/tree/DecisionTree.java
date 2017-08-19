package tree;

import tree.node.Node;

public class DecisionTree {
	private Node root;	// ルートノード

	public DecisionTree(Node root) {
		this.root = root;
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
	@Override
	public String toString() {
		return "DecisionTree [" + root + "]";
	}
}