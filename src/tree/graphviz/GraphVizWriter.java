package tree.graphviz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tree.DecisionTree;
import tree.DecisionTreeComponents;
import tree.edge.Edge;
import tree.node.Node;

public class GraphVizWriter {
	/**
	 * 記述するファイル
	 */
	private Path path;
	
	private Map<Node<?>, String> nodeVariables; 
	
	private static final String[] HEAD = {"digraph DecisionTree {"};
	private static final String[] TAIL = {"}"};
	private static final String[] SETTING = {
			"// graph settings",
			"graph [", 
			"layout = dot", 
			"];", 
			"// node settings", 
			"node [", 
			"fontname = Gosic,", 
			"fontsize = 16", 
			"];", 
			"// edge settings", 
			"edge [", 
			"fontname = Gosic,", 
			"fontsize = 16", 
			"];",
			""
			};
	
	
	/* Constractor */
	public GraphVizWriter(Path path) {
		this.path = path;
		nodeVariables = new HashMap<>();
	}
	
	public void writeoutDotFile(DecisionTree tree) {
		writeHead();
		writeLayoutSetting();

		DecisionTreeComponents dtc = tree.components();
		Set<Node<?>> internalNodes = new HashSet<>(dtc.getInternalNodes());
		Set<Node<?>> leafNodes = new HashSet<>(dtc.getLeafNodes());
		Set<Edge<?>> edges = new HashSet<>(dtc.getBranches());
		
		setVariableName(internalNodes);
		setVariableName(leafNodes);
		
		writeNodes(internalNodes, NodeShape.BOX);
		writeNodes(leafNodes, NodeShape.ELLIPSE);
		writeEdges(edges, null);
		
		writeTail();
	}
	
	private void writeNew(String[] lineArray) {
		List<String> lines = Stream.of(lineArray).collect(Collectors.toList());
		writeNew(lines);
	}
	private void writeNew(List<String> lines) {
		try {
			Files.write(path,
					lines,
					StandardOpenOption.WRITE,
					StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void writeAppend(String[] lineArray) {
		List<String> lines = Stream.of(lineArray).collect(Collectors.toList());
		writeAppend(lines);
	}
	private void writeAppend(List<String> lines) {
		try {
			Files.write(path, lines, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeHead() {
		writeNew(HEAD);
	}
	private void writeLayoutSetting() {
		writeAppend(SETTING);
	}
	private void writeTail() {
		writeAppend(TAIL);
	}
	private void writeNodes(Collection<? extends Node<?>> nodes, NodeShape shape) {
		writeAppend(nodes.stream()
				.map(n -> defineNode(nodeVariables.get(n), n.getLabel().toString(), shape))
				.collect(Collectors.toList()));
	}
	private String defineNode(String varName, String label, NodeShape shape) {
		String define = "\t" + varName + "\t[label = \"" + label + "\"";
		if (shape != null)
			define += ", shape = " + shape;
		define += "];";
		return define;
	}
	private void writeEdges(Collection<? extends Edge<?>> edges, EdgeStyle style) {
		writeAppend(edges.stream()
				.map(e -> defineEdge(
						nodeVariables.get(e.getFromNode()),
						nodeVariables.get(e.getToNode()),
						e.getLabel().toString(),
						style))
				.collect(Collectors.toList()));
	}
	private String defineEdge(String fromVar, String toVar, String label, EdgeStyle style) {
		String define = "\t" + fromVar + "\t-> " + toVar + "\t[label = \"" + label + "\"";
		if (style != null)
			define += ", style = " + style;
		define += "];";
		return define;
	}
	
	private void setVariableName(Collection<? extends Node<?>> nodes) {
		nodes.stream().forEach(n -> nodeVariables.put(n, "node"+nodeVariables.size()));
	}
}