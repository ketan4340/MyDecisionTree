package tree.graphviz;

public enum NodeShape {
	BOX,
	ELLIPSE;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}