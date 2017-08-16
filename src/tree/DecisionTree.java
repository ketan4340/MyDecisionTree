package tree;

import java.util.HashSet;
import java.util.Set;

import tree.edge.Edge;
import tree.node.Node;

public class DecisionTree {
	private Node root;
	/*
	private Set<Node> nodes;
	private Set<Edge> edges;
	 */
	public DecisionTree(Node r/*, Set<Node> ns, Set<Edge> es*/) {
		this.root = r;
		/*
		this.nodes = ns;
		this.edges = es;
		 */
	}
	public DecisionTree() {
		this(null/*, new HashSet<Node>(), new HashSet<Edge>()*/);
	}

	/*
	public boolean addNode(Node n) {
		return nodes.add(n);
	}
	public boolean addEdges(Edge e) {
		return edges.add(e);
	}
	 */
	public Node getRoot() {
		return root;
	}
	public void setRoot(Node rn) {
		this.root = rn;
	}
}