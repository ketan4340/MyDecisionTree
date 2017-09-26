package tree.edge;

import tree.node.Node;

public class Edge<L> {
	protected Node<?> fromNode;	// このエッジの接続元のノード
	protected Node<?> toNode;	// このエッジの接続先のノード
	protected L label;			// このエッジの任意のクラスのラベル

	/* Constructor */
	public Edge(Node<?> from, Node<?> to, L label) {
		this.setFromNode(from);
		this.setToNode(to);
		this.setLabel(label);
	}
	public Edge(Node<?> from, Node<?> to) {
		this(from, to, null);
	}
	public Edge() {
		this(null, null, null);
	}

	/* Getter */
	public Node<?> getFromNode() {
		return fromNode;
	}
	public Node<?> getToNode() {
		return toNode;
	}
	public L getLabel() {
		return label;
	}
	/* Setter */
	public void setFromNode(Node<?> fromNode) {
		this.fromNode = fromNode;
	}
	public void setToNode(Node<?> toNode) {
		this.toNode = toNode;
	}
	public void setLabel(L label) {
		this.label = label;
	}
}