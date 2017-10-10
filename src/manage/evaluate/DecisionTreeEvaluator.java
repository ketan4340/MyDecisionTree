package manage.evaluate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import data.Dataset;
import data.Record;
import data.value.NominalValue;
import tree.DecisionTree;

/**
 * 決定木評価器．
 * @author tanabekentaro
 */
public class DecisionTreeEvaluator {
	/**
	 * 評価する決定木．
	 */
	private DecisionTree decisionTree;
	/**
	 * 元のデータセットに含まれる全てのクラス値を持つリスト．
	 */
	private List<NominalValue> classList;
	/**
	 * マルチ混同行列の集合．交差検証のために複数保管．
	 */
	private Set<MultiConfusionMatrix> multiConfMatrixSet;
	
	/* Constructor */
	public DecisionTreeEvaluator(DecisionTree tree, List<NominalValue> classList) {
		setDecisionTree(tree);
		setClassList(classList);
		setMultiConfMatrixSet(new HashSet<MultiConfusionMatrix>());
	}
	
	
	/* Getter */
	public DecisionTree getDecisionTree() {
		return decisionTree;
	}
	public List<NominalValue> getClassList() {
		return classList;
	}
	public Set<MultiConfusionMatrix> getMultiConfMatrixSet() {
		return multiConfMatrixSet;
	}
	/* Setter */
	public void setDecisionTree(DecisionTree decisionTree) {
		this.decisionTree = decisionTree;
	}
	public void setClassList(List<NominalValue> classList) {
		this.classList = classList;
	}
	public void setMultiConfMatrixSet(Set<MultiConfusionMatrix> multiConfMatrixSet) {
		this.multiConfMatrixSet = multiConfMatrixSet;
	}

	/**
	 * 決定木を受け取ったテストデータで評価する．このメソッドの実行後，マルチ混同行列から精度を計算できるようになる．
	 * @param testData テストデータ
	 */
	public void evaluate(Dataset testData) {
		countConfusionMatrix(testData);
	}
	/**
	 * マルチ混同行列を数え上げる．
	 * @param testData テストデータ
	 */
	private void countConfusionMatrix(Dataset testData) {
		MultiConfusionMatrix mcm = new MultiConfusionMatrix(classList);
		for (Record record : testData.getRecordSet()) {
			NominalValue actualClass = record.getClassValue();		// このレコードのクラス値
			NominalValue predictedClass = decisionTree.predictClassOfRecord(record);	// 予測されたクラス値
			mcm.count(predictedClass, actualClass);
		}
		multiConfMatrixSet.add(mcm);
	}
	
	
	public double averageF_measure() {
		
	}
}