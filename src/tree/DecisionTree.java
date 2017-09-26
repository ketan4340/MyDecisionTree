package tree;

import data.Record;
import data.Tuple;
import data.attribute.Attributelist;
import data.value.NominalValue;
import tree.edge.Branch;
import tree.node.InternalNode;
import tree.node.Node;

public class DecisionTree {
	private Node<?> root;	// ルートノード

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
	 * 渡されたレコードをこの決定木に適用したときクラス値が一致するか確認する．
	 * @param record レコード
	 * @param attrlist レコードに対応した属性リスト
	 * @return 分類結果のクラス値と実際のレコードのクラス値が一致すればtrueを返す．
	 */
	public boolean classifiedCorrectly(Record record, Attributelist attrlist) {
		NominalValue decisionClassValue = applyTuple(record.getTuple(), attrlist);
		NominalValue actualClassValue = record.getClassValue();
		return decisionClassValue.equals(actualClassValue);
	}
	/**
	 * この決定木にタプルを適用した分類結果を得る．
	 * @param tuple タプル
	 * @param attrlist タプルに対応した属性リスト
	 * @return この決定木でタプルが分類されたクラス値．
	 */
	private NominalValue applyTuple(Tuple tuple, Attributelist attrlist) {
		while (!node.isLeaf()) {
			Node<?> node = root;
			InternalNode inNode = (InternalNode) node;
					
			Node<?> nextNode = inNode.getChildMatchTuple(tuple, attrlist);
		}
	}
	
}
