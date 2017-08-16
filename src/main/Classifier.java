package main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import data.Dataset;
import data.Record;
import data.attribute.AttributeType;
import data.attribute.Attributelist;
import tree.DecisionTree;
import tree.node.InternalNode;

public class Classifier {
	private Dataset trainData;
	private Attributelist attrlist;

	public Classifier(Dataset ds, Attributelist al) {
		this.trainData = ds;
		this.attrlist = al;
	}
	public Classifier(Path datasetPath, Path attrlistPath) {
		this.attrlist = new Attributelist(attrlistPath);
		this.trainData = new Dataset(datasetPath, attrlist);
	}

	private DecisionTree run() {
		// 0.入力の存在，全TupleとAttributeListの次元の一致を確認
		if (!isReady()) {
			System.err.println("Input Error.");
			return null;
		}

		// 1.ルートNode作成
		InternalNode rootNode = new InternalNode();
		DecisionTree tree = new DecisionTree(rootNode);

		// 2.全てのTupleのクラスAttributeValueが同じならルートNodeにCをラベル付けして終了


	}

	private boolean isReady() {
		if (trainData == null || attrlist == null)	// データセットか属性リストの欠如
			return false;
		int dimension = attrlist.size();
		for (Record r : trainData.getSet())
			if (r.getTuple().size() != dimension)	// 次元の不一致
				return false;
		return true;
	}
}
