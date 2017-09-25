package tree.node;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import tree.edge.Edge;

public class Node<L> {
	protected Edge<?> parentEdge;					// 親ノードに続くエッジ
	protected List<Edge<?>> childEdges;	// 子ノードに続くエッジ
	protected L label;

	/** コンストラクタ */
	public Node(Edge<?> parent, List<Edge<?>> children, L label) {
		setParentEdge(parent);
		setChildEdges(children);
		this.label = label;
	}
	public Node() {
		this(null, new LinkedList<>(), null);
	}
	public Node(Node<L> n) {
		this(n.getParentEdge(), n.getChildEdges(), n.getLabel());
	}


	/** getter */
	public Edge<?> getParentEdge() {
		return parentEdge;
	}
	// 指定されたEdgeの接続先がこのNodeであることも設定する特別なセッター
	public void setParentEdge(Edge<?> parentEdge) {
		this.parentEdge = parentEdge;
		if (parentEdge != null)
			parentEdge.setToNode(this);
	}
	public List<Edge<?>> getChildEdges() {
		return childEdges;
	}
	// 指定されたEdge全ての接続元がこのNodeであることも設定する特別なセッター
	public void setChildEdges(List<Edge<?>> childEdges) {
		this.childEdges = childEdges;
		childEdges.stream().forEach(ce -> ce.setFromNode(this));
	}
	public L getLabel() {
		return label;
	}

	public Node<?> getParent() {
		return parentEdge.getFromNode();
	}
	public Node<?> getChildAt(int index) {
		return childEdges.get(index).getToNode();
	}
	public List<Node<?>> getChildren() {
		return childEdges.stream()
				.map(ce -> ce.getToNode())
				.collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "n[" + getChildren().stream().map(n -> n.toString()).collect(Collectors.joining(",")) + "]";
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
	private <E extends Edge<?>> void addChildEdge(E edge) {
		childEdges.add(edge);
	}

}