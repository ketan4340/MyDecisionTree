package tree.node;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import tree.edge.Edge;

public class Node {
	protected Edge parentEdge;
	protected List<Edge> childEdges;

	public Node(Edge pe, Collection <? extends Edge> cec) {
		this.parentEdge = pe;
		this.childEdges = new LinkedList<>(cec);
	}
	public Node() {
		this(new Edge(), new LinkedList<>());
	}


	/** getter/setter */
	public Edge getParentEdge() {
		return parentEdge;
	}
	public void setParentEdge(Edge parentEdge) {
		this.parentEdge = parentEdge;
	}
	public List<Edge> getChildEdges() {
		return childEdges;
	}
	public void setChildEdges(List<Edge> childEdges) {
		this.childEdges = childEdges;
	}

	public Node getParent() {
		return parentEdge.getFromNode();
	}
	public Node getChildAt(int index) {
		return childEdges.get(index).getFromNode();
	}
	public List<Node> getChildren() {
		return childEdges.stream()
				.map(ce -> ce.getFromNode())
				.collect(Collectors.toList());
	}

	public boolean isRoot() {
		return (parentEdge == null)? true: false;
	}
}