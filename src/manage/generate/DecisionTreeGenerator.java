package manage.generate;

import java.util.Map;

import data.Dataset;
import data.Record;
import data.attribute.AbstractAttribute;
import data.attribute.Attributelist;
import data.value.AbstractValue;
import tree.DecisionTree;
import tree.edge.Branch;
import tree.node.InternalNode;
import tree.node.LeafNode;
import tree.node.Node;

public class DecisionTreeGenerator {
	/**
	 * 分割されたデータセットのサイズに基づく閾値
	 */
	public double sizeThreshold;
	/**
	 * 情報利得率に基づく閾値
	 */
	public double gainRateThreshold;

	public DecisionTreeGenerator(double sizeThreshold, double gainRateThreshold) {
		this.sizeThreshold = sizeThreshold;
		this.gainRateThreshold = gainRateThreshold;
	}

	public DecisionTree generateTree(Dataset trainData) {
		double baseSize = trainData.size();
		return new DecisionTree(generateNode(trainData, baseSize));
	}

	private Node<?> generateNode(Dataset trainData, double baseSize) {
		Attributelist attrlist = trainData.getAttrlist();
		// 0.全TupleとAttributeListの次元の一致を確認
		if (!isReady(trainData)) {
			System.err.println("Input Error.");
			return null;
		}

		// 1.ルートNode作成
		Node<?> node;

		// 2.全てのTupleのクラス属性値が同じCならルートにCをラベル付けして終了
		AbstractValue<?> commonClassValue = trainData.getCommonClassValue();
		if (commonClassValue != null)
			return new LeafNode(commonClassValue);

		// 3.Attributelistが空ならノードに全Tuple中最も多いクラス値をラベルづけして終了
		if (attrlist.isEmpty())
			return new LeafNode(trainData.getMajorityClassValue());

		// x1.枝刈り。現データセットのサイズが元のデータセットの閾値s倍未満の場合，最多属性値をラベルづけして終了
		if (trainData.size() < sizeThreshold * baseSize)
			return new LeafNode(trainData.getMajorityClassValue());

		// 4.利得率から判定する属性を選び，分岐させる
		AbstractAttribute<?> bestAttr = trainData.bestAttrByGainRation(gainRateThreshold);
		if (bestAttr == null)
			return new LeafNode(trainData.getMajorityClassValue());
		node = new InternalNode(bestAttr);
		Map<AbstractValue<?>, Dataset> subDatasets = trainData.splitByAttr(bestAttr);
		for (Map.Entry<AbstractValue<?>, Dataset> valDataEntry : subDatasets.entrySet()) {
			AbstractValue<?> branchVal = valDataEntry.getKey();
			Branch branch = new Branch(branchVal);
			Dataset subDS = valDataEntry.getValue();

			if (subDS.isEmpty()) {
				AbstractValue<?> freqVal = trainData.getMajorityClassValue();
				LeafNode freqChild = new LeafNode(freqVal);
				node.addChild(branch, freqChild);
			} else {
				// 再帰的にノードを生成し繋げていく
				Node<?> recursionChild = generateNode(subDS, baseSize);
				node.addChild(branch, recursionChild);
			}
		}
		return node;
	}

	/**
	 * データセットと属性リストの長さの一致を確かめる。
	 * @param trainData
	 * @return
	 */
	private boolean isReady(Dataset trainData) {
		int dimension = trainData.getAttrlist().size();
		for (Record r : trainData.getRecordSet()) {
			if (r.size() != dimension+1) {	// 次元の不一致
				System.err.println("ErrorRecord: " + r);
				return false;
			}
		}
		return true;
	}
}