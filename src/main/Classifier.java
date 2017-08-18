package main;

import java.nio.file.Path;

import data.Dataset;
import data.Record;
import data.attribute.AbstractAttribute;
import data.attribute.Attributelist;
import data.value.AbstractValue;
import tree.DecisionTree;
import tree.node.LeafNode;

public class Classifier {
	public Classifier() {
	}

	public DecisionTree run(Path datasetPath, Path attrlistPath) {
		Attributelist attrlist = new Attributelist(attrlistPath);
		Dataset trainData = new Dataset(datasetPath, attrlist);
		return run(trainData);
	}
	public DecisionTree run(Dataset trainData) {
		Attributelist attrlist = trainData.getAttrlist();
		// 0.全TupleとAttributeListの次元の一致を確認
		if (!isReady(trainData)) {
			System.err.println("Input Error.");
			return null;
		}

		DecisionTree tree = new DecisionTree();

		// 1.ルートNode作成
		LeafNode node = new LeafNode();
		tree.setRoot(node);

		// 2.全てのTupleのクラス属性値が同じCならルートにCをラベル付けして終了
		AbstractValue<?> commonClassValue = trainData.getCommonClassValue();
		if (commonClassValue != null) {
			node.setClassValue(commonClassValue);
			return tree;
		}

		// 3.Attributelistが空ならノードに全Tuple中最も多い属性値をラベルづけして終了
		if (attrlist.isEmpty()) {
			node.setClassValue(trainData.getMajorityClassValue());
			return tree;
		}

		// 4.利得率から判定する属性を選び，分岐させる
		AbstractAttribute<?> bestAttr = trainData.getJudgeAttrByGainRation();

		return null;
	}

	private boolean isReady(Dataset trainData) {
		int dimension = trainData.getAttrlist().size();
		boolean b = true;
		for (Record r : trainData.getSet())
			if (r.size() != dimension+1){	// 次元の不一致
				System.err.println("ErrorRecord: " + r);
				b = false;
			}
		return b;
	}
}
