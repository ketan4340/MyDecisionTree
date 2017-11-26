package manage.evaluate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import data.value.NominalValue;

/**
 * マルチ混同行列
 * @author tanabekentaro
 */
public class MultiConfusionMatrix {
	/**
	 * 多クラス混同行列．第一要素が予測されたクラス，第二要素が実際のクラスを表す．
	 */
	private int[][] matrix;
	/**
	 * クラス値リスト．混同行列に対応
	 */
	private List<NominalValue> classes;
	
	/* Constractor */
	public MultiConfusionMatrix(int[][] matrix, List<NominalValue> classes) {
		this.setMatrix(matrix);
		this.setClasses(classes);
		matrixInit();
	}
	public MultiConfusionMatrix(int size) {
		this(new int[size][size], new ArrayList<NominalValue>(size));
	}
	public MultiConfusionMatrix(List<NominalValue> classList) {
		this(new int[classList.size()][classList.size()], classList);
	}
	
	
	/* Getter */
	public int[][] getMatrix() {
		return matrix;
	}
	public List<NominalValue> getClasses() {
		return classes;
	}
	/* Setter */
	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
	public void setClasses(List<NominalValue> classes) {
		this.classes = classes;
	}
	/**
	 * 混同行列の初期化．全ての要素を0で埋める．
	 */
	private void matrixInit() {
		for (int[] line : matrix) {
			Arrays.fill(line, 0);		
		}
	}
	
	private int freqAt(NominalValue predictedClass, NominalValue actualClass) {
		int index_prd = classes.indexOf(predictedClass);
		int index_act = classes.indexOf(actualClass);
		return matrix[index_prd][index_act];
	}
	
	/**
	 * 予測されたクラス値と実際のクラス値を受け取り，混同行列の対応する要素に1を加える．
	 * @param predictedClass 予測されたクラス値
	 * @param actualClass 実際のクラス値
	 */
	public void count(NominalValue predictedClass, NominalValue actualClass) {
		int index_prd = classes.indexOf(predictedClass);
		int index_act = classes.indexOf(actualClass);
		matrix[index_prd][index_act]++;
	}
	
	/**
	 * このマルチ混同行列をクラス値毎の混同行列に分ける．
	 * @return 分割した混同行列の集合
	 */
	private Set<ConfusionMatrix> splitMatrix4EachClass() {
		Set<ConfusionMatrix> cmset = new HashSet<>();
		for (NominalValue classVal : classes) {
			int tp = truePositive(classVal);
			int tn = trueNegative(classVal);
			int fp = falsePositive(classVal);
			int fn = falseNegative(classVal);
			cmset.add(new ConfusionMatrix(tp, tn, fp, fn));
		}
		return cmset;
	}
	
	private int sumAll() {
		return Arrays.stream(matrix)
				.flatMapToInt(prds -> Arrays.stream(prds))
				.sum();
	}
	private int sumOfPredictClass(NominalValue predict) {
		return Arrays.stream(matrix[classes.indexOf(predict)])
				.sum();
	}
	private int sumOfActualClass(NominalValue actual) {
		return Arrays.stream(matrix)
				.mapToInt(row -> row[classes.indexOf(actual)])
				.sum();
	}
	
	private int truePositive(NominalValue classVal) {
		return freqAt(classVal, classVal);
	}
	private int falsePositive(NominalValue predictClass) {
		return sumOfPredictClass(predictClass) - truePositive(predictClass);
	}
	private int falseNegative(NominalValue actualClass) {
		return sumOfActualClass(actualClass) - truePositive(actualClass);
	}
	private int trueNegative(NominalValue classVal) {
		return sumAll() - truePositive(classVal) - falsePositive(classVal) - falseNegative(classVal);
	}
	
	/**
	 * 正解率MicroAccuracy (Overall Accuracy)
	 * @return MicroAccuracy=SUM(TP)/ALL
	 */
	public double microAccuracy() {
		int tpSum = classes.stream().mapToInt(cls -> truePositive(cls)).sum();
		int all = sumAll();
		return (double) tpSum / all;
	}
	/**
	 * 正解率MacroAccuracy (Average Accuracy)
	 * @return MacroAccuracy=AVG(TP/(TP+FN)) (MacroRecallと同じになっちゃうんですけどー)
	 */
	public double macroAccuracy() {
		return classes.stream()
				.mapToDouble(cls -> (double) truePositive(cls)/(truePositive(cls)+falseNegative(cls)) )
				.average().getAsDouble();
	}
	/**
	 * 精度MicroPrecision
	 * @return MicroPrecision=SUM(TP)/SUM(TP+FP)
	 */
	public double microPrecision() {
		Set<ConfusionMatrix> cmset = splitMatrix4EachClass();
		int tpSum = cmset.stream().mapToInt(cm -> cm.getTP()).sum();
		int tpfpSum = cmset.stream().mapToInt(cm -> cm.classifiedPositives()).sum();
		return (double) tpSum / tpfpSum;
	}
	/**
	 * 精度MacroPrecision
	 * @return MacroPrecision=AVG(TP/(TP+FP))
	 */
	public double macroPrecision() {
		return splitMatrix4EachClass().stream()
				.mapToDouble(cm -> cm.precision())
				.average().getAsDouble();
	}	
	/**
	 * 再現率(検出率)MicroRecall
	 * @return Recall=SUM(TP)/SUM(TP+FN)
	 */
	public double microRecall() {
		Set<ConfusionMatrix> cmset = splitMatrix4EachClass();
		int tpSum = cmset.stream().mapToInt(cm -> cm.getTP()).sum();
		int tpfnSum = cmset.stream().mapToInt(cm -> cm.positives()).sum();
		return (double) tpSum / tpfnSum;
	}
	/**
	 * 再現率(検出率)MacroRecall
	 * @return Recall=AVG(TP/(TP+FN))
	 */
	public double macroRecall() {
		return splitMatrix4EachClass().stream()
				.mapToDouble(cm -> cm.recall())
				.average().getAsDouble();
	}
	/**
	 * F値(精度と再現率の調和平均)MicroF-measure
	 * @return F-measure=2*Precision*Recall/(Precision+Recall)
	 */
	public double microF_measure() {
		return microFw_measure(1.0);
	}
	/**
	 * F値(精度と再現率の調和平均)MacroF-measure
	 * @return F-measure=2*Precision*Recall/(Precision+Recall)
	 */
	public double macroF_measure() {
		return macroFw_measure(1.0);
	}
	/**
	 * 重み付きF値MicroWeighted F-measure
	 * @param beta 重み
	 * @return Fw-measure=(1+b^2)*Precision*Recall/(b^2*Precision+Recall)
	 */
	public double microFw_measure(double beta) {
		double precision = microPrecision();
		double recall = microRecall();
		double b2 = beta * beta;
		return (1 + b2) * precision * recall / (b2 * precision + recall);
	}
	/**
	 * 重み付きF値MacroWeighted F-measure
	 * @param beta 重み
	 * @return Fw-measure=(1+b^2)*Precision*Recall/(b^2*Precision+Recall)
	 */
	public double macroFw_measure(double beta) {
		double precision = macroPrecision();
		System.out.println("pre: " + precision);
		double recall = macroRecall();
		double b2 = beta * beta;
		return (1 + b2) * precision * recall / (b2 * precision + recall);
	}
	
	/**
	 * 行が予測されたクラス，列が実際のクラス.
	 */
	@Override
	public String toString() {
		return classes.stream().map(nv -> nv.getElem().toString()).collect(Collectors.joining("\t,", "\t", "\n")) + 
				Arrays.stream(matrix).map(arr -> Arrays.stream(arr)
						.boxed()						// IntStream -> Stream<Integer>
						.map(i -> String.valueOf(i))	// Stream<Integer> -> Stream<String>
						.collect(Collectors.joining("\t,", "\t", "")))
				.collect(Collectors.joining("\n", "", "\n"));
	}
}