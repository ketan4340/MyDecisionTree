package tree;

import java.util.HashSet;
import java.util.Set;

import tree.edge.Branch;
import tree.node.InternalNode;
import tree.node.LeafNode;

public class DecisionTreeComponents {
	private Set<InternalNode> internalNodes;
	private Set<LeafNode> leafNodes;
	private Set<Branch> branches;
	
	public DecisionTreeComponents(Set<InternalNode> internalNodes, Set<LeafNode> leafNodes, Set<Branch> branches) {
		this.setInternalNodes(internalNodes);
		this.setLeafNodes(leafNodes);
		this.setBranches(branches);
	}
	public DecisionTreeComponents() {
		this(new HashSet<>(), new HashSet<>(), new HashSet<>());
	}
	
	/* Getter */
	public Set<InternalNode> getInternalNodes() {
		return internalNodes;
	}
	public Set<LeafNode> getLeafNodes() {
		return leafNodes;
	}
	public Set<Branch> getBranches() {
		return branches;
	}
	/* Setter */
	public void setInternalNodes(Set<InternalNode> internalNodes) {
		this.internalNodes = internalNodes;
	}
	public void setLeafNodes(Set<LeafNode> leafNodes) {
		this.leafNodes = leafNodes;
	}
	public void setBranches(Set<Branch> branches) {
		this.branches = branches;
	}
	
	
}