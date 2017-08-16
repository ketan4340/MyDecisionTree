package main;

import java.nio.file.Path;

import data.Dataset;
import data.Record;
import data.attribute.Attributelist;
import tree.DecisionTree;
import tree.node.InternalNode;
import tree.node.Node;

public class Classifier {
	public Classifier() {
	}

	public DecisionTree run(Path datasetPath, Path attrlistPath) {
		Attributelist attrlist = new Attributelist(attrlistPath);
		Dataset trainData = new Dataset(datasetPath, attrlist);
		return run(trainData, attrlist);
	}
	public DecisionTree run(Dataset trainData, Attributelist attrlist) {
		// 0.全TupleとAttributeListの次元の一致を確認
		if (!isReady(trainData, attrlist)) {
			System.err.println("Input Error.");
			return null;
		}

		DecisionTree tree = new DecisionTree();

		// 1.ルートNode作成
		Node node = new InternalNode();
		tree.setRoot(node);

		// 2.全てのTupleのクラス属性値が同じCならルートにCをラベル付けして終了
		if (trainData.matchAllValue()) {

			return tree;
		}

		// 3.Attributelistが空ならノードに全Tuple中最も多い属性値をラベルづけして終了
		if (attrlist.isEmpty()) {

			return tree;
		}

	}

	private boolean isReady(Dataset trainData, Attributelist attrlist) {
		int dimension = attrlist.size();
		for (Record r : trainData.getSet())
			if (r.getTuple().size() != dimension)	// 次元の不一致
				return false;
		return true;
	}
}
