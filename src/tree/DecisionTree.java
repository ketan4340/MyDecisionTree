package tree;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import data.Record;
import data.Tuple;
import data.value.NominalValue;
import tree.edge.Branch;
import tree.node.InternalNode;
import tree.node.LeafNode;
import tree.node.Node;

/**
 * 決定木．決定木の根ノードをもつ．
 * @author tanabekentaro
 */
public class DecisionTree {
	/**
	 * ルートノード
	 */
	private Node<?> root;

	/* コンストラクタ */
	public DecisionTree(Node<?> root) {
		this.root = root;
	}
	public DecisionTree() {
		this(null);
	}

	
	/* Getter */
	public Node<?> getRoot() {
		return root;
	}
	/* Setter */
	public void setRoot(Node<?> rootNode) {
		this.root = rootNode;
	}
	@Override
	public String toString() {
		return "DecisionTree [" + root + "]";
	}
	
	public DecisionTreeComponents components() {
		Set<InternalNode> internalNodes = new HashSet<>();
		Set<LeafNode> leafNodes = new HashSet<>();
		Set<Branch> branches = new HashSet<>();
		
		List<Node<?>> uncheckedNodes = new LinkedList<>();
		uncheckedNodes.add(root);
		
		while (!uncheckedNodes.isEmpty()) {
			Node<?> node = uncheckedNodes.remove(0);
		
			if (node.isLeaf()) {		// 葉ノードまで辿り着いたら
				leafNodes.add((LeafNode) node);
			}else {					// 内部ノードなら
				internalNodes.add((InternalNode) node);
				branches.addAll(node.getChildEdges().stream().map(e -> (Branch) e).collect(Collectors.toList()));

				uncheckedNodes.addAll(node.getChildrenNodes());	
			}
		}
		
		return new DecisionTreeComponents(internalNodes, leafNodes, branches);
	}
	
	/**
	 * 渡されたレコードをこの決定木に適用して，予測されるクラス値を返す．
	 * @param record レコード
	 * @return 分類結果のクラス値．
	 */
	public NominalValue predictClassOfRecord(Record record) {
		return applyTuple(record.getTuple());
	}
	/**
	 * この決定木にタプルを適用した分類結果を得る．
	 * @param tuple タプル
	 * @param attrlist タプルに対応した属性リスト
	 * @return この決定木でタプルが分類されたクラス値．
	 */
	private NominalValue applyTuple(Tuple tuple) {
		Node<?> node = root;
		while (node != null) {
			if (node.isLeaf())	// 葉ノードまで辿り着いたら
				return (NominalValue) ((LeafNode) node).getLabel();	// 葉ノードがもつクラス値を返す
			else					// 内部ノードなら
				node = ((InternalNode) node).getChildMatchTuple(tuple);	// タプルの値に合う子ノードへ進む				
		}
		return null;
	}

	
}