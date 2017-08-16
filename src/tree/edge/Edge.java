package tree.edge;

import tree.node.Node;

public class Edge {
	protected Node fromNode;
	protected Node toNode;

	public Edge(Node from, Node to) {
		this.fromNode = from;
		this.toNode = to;
	}
	public Edge() {
		this(null, null);
	}

	/** getter/setter */
	public Node getFromNode() {
		return fromNode;
	}
	public void setFromNode(Node fromNode) {
		this.fromNode = fromNode;
	}
	public Node getToNode() {
		return toNode;
	}
	public void setToNode(Node toNode) {
		this.toNode = toNode;
	}
}