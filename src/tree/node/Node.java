package tree.node;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import tree.edge.Edge;

public class Node<L> {
	protected Edge<?> parentEdge;		// このノードの親ノードに続くエッジ
	protected List<Edge<?>> childEdges;	// このノードの子ノードに続くエッジ
	protected L label;					// このノードの任意のクラスのラベル

	/* Constructor */
	public Node(Edge<?> parent, List<Edge<?>> children, L label) {
		this.setParentEdge(parent);
		this.setChildEdges(children);
		this.setLabel(label);
	}
	public Node(Edge<?> parent, List<Edge<?>> children) {
		this(parent, children, null);
	}
	public Node(Node<? extends L> node) {
		this(node.getParentEdge(), node.getChildEdges(), node.getLabel());
	}
	public Node() {
		this(null, new LinkedList<>(), null);
	}

	/* Getter */
	public Edge<?> getParentEdge() {
		return parentEdge;
	}
	public List<Edge<?>> getChildEdges() {
		return childEdges;
	}
	public L getLabel() {
		return label;
	}
	/* Setter */
	// 指定されたEdgeの接続先がこのNodeであることも設定する特別なセッター
	public void setParentEdge(Edge<?> parentEdge) {
		this.parentEdge = parentEdge;
		if (parentEdge != null)
			parentEdge.setToNode(this);
	}
	// 指定されたEdge全ての接続元がこのNodeであることも設定する特別なセッター
	public void setChildEdges(List<Edge<?>> childEdges) {
		this.childEdges = childEdges;
		childEdges.stream().forEach(ce -> ce.setFromNode(this));
	}
	public void setLabel(L label) {
		this.label = label;
	}

	
	public Node<?> getParentNode() {
		return parentEdge.getFromNode();
	}
	public Node<?> getChildNodeAt(int index) {
		return childEdges.get(index).getToNode();
	}
	public List<Node<?>> getChildrenNodes() {
		return childEdges.stream()
				.map(ce -> ce.getToNode())
				.collect(Collectors.toList());
	}

	/* toString */
	@Override
	public String toString() {
		return "n[" + getChildrenNodes().stream().map(n -> n.toString()).collect(Collectors.joining(",")) + "]";
	}


	public boolean isRoot() {
		return (parentEdge == null)? true: false;
	}
	public boolean isLeaf() {
		return (childEdges.isEmpty())? true: false;
	}
	
	public void addChildNode(Edge<?> edge, Node<?> child) {
		edge.setFromNode(this);
		edge.setToNode(child);
		addChildEdge(edge);
	}
	private void addChildEdge(Edge<?> edge) {
		childEdges.add(edge);
	}

}