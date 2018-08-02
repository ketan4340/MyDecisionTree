package main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import data.Dataset;
import data.Record;
import data.attribute.Attributelist;
import data.value.NominalValue;
import manage.evaluate.DecisionTreeEvaluator;
import manage.generate.DecisionTreeGenerator;
import tree.DecisionTree;
import tree.graphviz.GraphVizWriter;
import util.MyCollections;

public class Main {

	public static void main(String[] args) {
		/*
		Path datasetPath = Paths.get("dataset/example/exDataset.txt");
		Path attrlistPath = Paths.get("dataset/example/exAttrlist.txt");
		//*/
		///*
		Path datasetPath = Paths.get("dataset/carEvaluation/car.data.txt");
		Path attrlistPath = Paths.get("dataset/carEvaluation/car.attrlist.txt");
		 //*/
		/*
		Path datasetPath = Paths.get("dataset/rdfProperty/dataset2.csv");
		Path attrlistPath = Paths.get("dataset/rdfProperty/attrlist2.txt");
		//*/

		
		/* 訓練データセット, 属性リスト, クラス値リスト生成 */
		Attributelist attrlist = new Attributelist(attrlistPath);
		Dataset trainData = new Dataset(datasetPath, attrlist);
		List<NominalValue> classList = new ArrayList<>(trainData.classValues());

		/* テストデータセット確保 */
		Set<Record> testRecords = MyCollections.randomSamplingSet(trainData.getRecordSet(), trainData.size()/10);
		Dataset testData = new Dataset(testRecords, attrlist);
		
		/* 決定木生成 */
		DecisionTreeGenerator generator = new DecisionTreeGenerator(0.1, 1.0);
		DecisionTree tree = generator.generateTree(trainData);
		GraphVizWriter gvw = new GraphVizWriter(datasetPath.resolveSibling("decisionTree.dot"));
		gvw.writeoutDotFile(tree);
		//TODO
		//コマンドプロンプトで生成
		//hoge
		System.out.println(tree);//TODO
		System.out.println("\n----------------------------------------\n");
		
		
		/* 決定木評価 */
		DecisionTreeEvaluator evaluator = new DecisionTreeEvaluator(tree, classList);
		evaluator.evaluate(testData);
		System.out.println("MacroF: "+evaluator.averageMacroF_measure());
		System.out.println("MicroF: "+evaluator.averageMicroF_measure());
		System.out.println("Overall Accuracy: "+evaluator.averageMicroAccuracy());
		System.out.println("Average Accuracy: "+evaluator.averageMacroAccuracy());
	}
}
