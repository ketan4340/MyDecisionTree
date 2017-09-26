package main;

import java.util.List;

import data.Dataset;
import data.Record;
import data.attribute.AbstractAttribute;
import data.value.AbstractValue;
import tree.DecisionTree;

public class DecisionTreeEvaluator {

	private DecisionTree decisionTree;
	
	private List<AbstractAttribute<?>> classList;

	
	public DecisionTreeEvaluator(DecisionTree tree) {
		this.decisionTree = tree;
	}
	
	public void evaluate(Dataset testData) {
		for (Record record : testData.getRecordSet()) {
			boolean decisionResult = decisionTree.classifiedCorrectly(record, testData.getAttrlist());
		}
	}
	
	private int truePositives(AbstractAttribute<AbstractValue<?>> attr) {		
		return 0;	
	}
	
}