package main;

import java.nio.file.Path;
import java.nio.file.Paths;

import data.Dataset;
import data.attribute.Attributelist;
import tree.DecisionTree;

public class Main {

	public static void main(String[] args) {
		/*
		Path datasetPath = Paths.get("dataset/carEvaluation/car.data.txt");
		Path attrlistPath = Paths.get("dataset/carEvaluation/car.attrlist.txt");
		 //*/
		///*
		Path datasetPath = Paths.get("dataset/rdfProperty/dataset2.csv");
		Path attrlistPath = Paths.get("dataset/rdfProperty/attrlist2.txt");
		//*/

		/* 訓練データセット, 属性リスト生成 */
		Attributelist attrlist = new Attributelist(attrlistPath);
		Dataset trainData = new Dataset(datasetPath, attrlist);
		/* テストデータセット確保 */
		int testDataSize = (int) (0.1 * trainData.size());
		Dataset testData = trainData.pickOutTestData(testDataSize);
		
		/* 決定木生成 */
		DecisionTreeGenerator generator = new DecisionTreeGenerator(0.0, 1.0);
		DecisionTree tree = generator.generateTree(trainData);

		System.out.println(tree);//TODO
		
		/* 決定木評価 */
		DecisionTreeEvaluator evaluator = new DecisionTreeEvaluator(tree);
		evaluator.evaluate(testData);
		
	}
}