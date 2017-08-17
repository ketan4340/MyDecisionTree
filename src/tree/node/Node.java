package tree.node;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import tree.edge.Edge;

public class Node {
	private Edge parentEdge;
	private List<Edge> childEdges;

	public Node(Edge pe, List<Edge> cec) {
		setParentEdge(pe);
		setChildEdges(cec);
	}
	public Node() {
		this(null, new LinkedList<>());
	}
	public Node(Node n) {
		this(n.getParentEdge(), n.getChildEdges());
	}


	/** getter/setter */
	public Edge getParentEdge() {
		return parentEdge;
	}
	// 指定されたEdgeの接続先がこのNodeであることも設定する特別なセッター
	public void setParentEdge(Edge parentEdge) {
		this.parentEdge = parentEdge;
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