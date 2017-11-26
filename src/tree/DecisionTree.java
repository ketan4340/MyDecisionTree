package tree;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import data.Tuple;
import data.value.NominalValue;
import tree.edge.Branch;
import tree.node.InternalNode;
import tree.node.LeafNode;
import tree.node.Node;

/**
 * 決定木．メンバは決定木の根ノードのみ．
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
	
	/**
	 * この{@code DecisionTree}を構成する全ての要素({@code InternalNode}, {@code LeafNode}, {@code Branch})をまとめた，
	 * {@code DecisionTreeComponents}のインスタンスを返す.
	 * @return 決定木の構成要素
	 */
	public DecisionTreeComponents components() {
		Set<InternalNode> internalNodes = new HashSet<>();
		Set<LeafNode> leafNodes = new HashSet<>();
		Set<Branch> branches = new HashSet<>();
		
		List<Node<?>> uncheckedNodes = new LinkedList<>();	// 未検査ノードリスト
		uncheckedNodes.add(root);

		while (!uncheckedNodes.isEmpty()) {		// 未検査ノードリストが空になるまで
			Node<?> node = uncheckedNodes.remove(0);
		
			if (node.isLeaf()) {		// 葉ノードまで辿り着いたら
				leafNodes.add((LeafNode) node);
			}else {					// 内部ノードなら
				internalNodes.add((InternalNode) node);
				node.getChildEdges().stream().forEach(e -> branches.add((Branch) e));
				// 未検査ノードリストに子ノードを追加
				uncheckedNodes.addAll(node.childrenNodes());	
			}
		}
		
		return new DecisionTreeComponents(internalNodes, leafNodes, branches);
	}
	
	/**
	 * この決定木にタプルを適用した分類結果を得る.
	 * @param tuple タプル
	 * @param attrlist タプルに対応した属性リスト
	 * @return この決定木でタプルが分類されたクラス値．
	 */
	public NominalValue classify(Tuple tuple) {
		Node<?> node = root;
		while (node != null) {
			if (node.isLeaf())	// 葉ノードまで辿り着いたら
				// 葉ノードがもつクラス値を返す
				return (NominalValue) ((LeafNode) node).getLabel();
			else					// 内部ノードなら
				// タプルの値に合う子ノードへ進む
				node = ((InternalNode) node).findChildNodeMatching(tuple);
		}
		return null;
	}
}