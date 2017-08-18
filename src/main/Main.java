package main;

import java.nio.file.Path;
import java.nio.file.Paths;

import tree.DecisionTree;

public class Main {

	public static void main(String[] args) {
		/*
		Path datasetPath = Paths.get("dataset/carEvaluation/car.data.txt");
		Path attrlistPath = Paths.get("dataset/carEvaluation/car.attrlist.txt");
		 //*/
		///*
		Path datasetPath = Paths.get("dataset/example/exDataset.txt");
		Path attrlistPath = Paths.get("dataset/example/exAttrlist.txt");
		//*/
		Classifier classifier = new Classifier();
		DecisionTree tree = classifier.run(datasetPath, attrlistPath);

		System.out.println(tree);//TODO
	}

}