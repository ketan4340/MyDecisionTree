package tree.edge;

import tree.node.Node;

public class Edge<L> {
	protected Node<?> fromNode;	// このエッジの接続元のノード
	protected Node<?> toNode;		// このエッジの接続先のノード
	protected L label;

	public Edge(Node<?> from, Node<?> to, L lb) {
		this.fromNode = from;
		this.toNode = to;
		this.label = lb;
	}
	public Edge(Node<?> from, Node<?> to) {
		this(from, to, null);
	}
	public Edge() {
		this(null, null, null);
	}

	/** getter/setter */
	public Node<?> getFromNode() {
		return fromNode;
	}
	public Node<?> getToNode() {
		return toNode;
	}
	public void setFromNode(Node<?> fromNode) {
		this.fromNode = fromNode;
	}
	public void setToNode(Node<?> toNode) {
		this.toNode = toNode;
	}
}