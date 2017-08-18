package tree.node;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import tree.edge.Edge;

public class Node {
	private Edge parentEdge;
	private List<Edge> childEdges;

	public Node(Edge parent, List<Edge> children) {
		setParentEdge(parent);
		setChildEdges(children);
	}
	public Node() {
		this(null, new LinkedList<>());
	}
	public Node(Node n) {
		this(n.getParentEdge(), n.getChildEdges());
	}


	/** getter */
	public Edge getParentEdge() {
		return parentEdge;
	}
	@Override
	public String toString() {
		return "n[" + getChildren().stream().map(n -> n.toString()).collect(Collectors.joining(",")) + "]";
	}


	// 指定されたEdgeの接続先がこのNodeであることも設定する特別なセッター
	public void setParentEdge(Edge parentEdge) {
		this.parentEdge = parentEdge;
		if (parentEdge != null)
			parentEdge.setToNode(this);
	}
	public List<Edge> getChildEdges() {
		return childEdges;
	}
	// 指定されたEdge全ての接続元がこのNodeであることも設定する特別なセッター
	public void setChildEdges(List<Edge> childEdges) {
		this.childEdges = childEdges;
		childEdges.stream().forEach(ce -> ce.setFromNode(this));
	}

	public Node getParent() {
		return parentEdge.getFromNode();
	}
	public Node getChildAt(int index) {
		return childEdges.get(index).getToNode();
	}
	public List<Node> getChildren() {
		return childEdges.stream()
				.map(ce -> ce.getToNode())
				.collect(Collectors.toList());
	}

	public boolean isRoot() {
		return (parentEdge == null)? true: false;
	}
}