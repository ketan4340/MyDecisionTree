package tree;

import data.Record;
import data.Tuple;
import data.value.NominalValue;
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

	
	/* getter/setter */
	public Node<?> getRoot() {
		return root;
	}
	public void setRoot(Node<?> rootNode) {
		this.root = rootNode;
	}
	@Override
	public String toString() {
		return "DecisionTree [" + root + "]";
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