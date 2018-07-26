package manage.evaluate;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
		this.matrix = matrix;
		this.classes = classes;
	}
	public MultiConfusionMatrix(List<NominalValue> classList) {
		this(matrixFilled0(classList.size()), classList);
	}
	

	/**
	 * 混同行列の初期化．全ての要素を0で埋める．
	 */
	private static int[][] matrixFilled0(int size) {
		return Stream.generate(() -> IntStream.generate(() -> 0).limit(size).toArray())
				.limit(size).toArray(int[][]::new);
	}
	
	/**
	 * この混合行列のサイズと同じ個数の整数のストリームを生成する. 
	 * @return 0から{@code matrix.length}-1までの整数ストリーム
	 */
	private IntStream sizeStream() {
		return IntStream.range(0, matrix.length);
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
		return sizeStream()
				.mapToObj(i -> new ConfusionMatrix(truePositive(i), trueNegative(i), falsePositive(i), falseNegative(i)))
				.collect(Collectors.toSet());
	}
	
	private int sumAll() {
		return Arrays.stream(matrix).flatMapToInt(Arrays::stream).sum();
	}
	private int sumOfPredictClass(int predictClassIndex) {
		return Arrays.stream(matrix[predictClassIndex]).sum();
	}
	private int sumOfActualClass(int actualClassIndex) {
		return Arrays.stream(matrix).mapToInt(row -> row[actualClassIndex]).sum();
	}
	
	private int truePositive(int classValueIndex) {
		return matrix[classValueIndex][classValueIndex];
	}
	private int falsePositive(int predictClassIndex) {
		return sumOfPredictClass(predictClassIndex) - truePositive(predictClassIndex);
	}
	private int falseNegative(int actualClassIndex) {
		return sumOfActualClass(actualClassIndex) - truePositive(actualClassIndex);
	}
	private int trueNegative(int classValueIndex) {
		return sumAll() - truePositive(classValueIndex) - falsePositive(classValueIndex) - falseNegative(classValueIndex);
	}
	
	/**
	 * 正解率MicroAccuracy (Overall Accuracy)
	 * @return MicroAccuracy=SUM(TP)/ALL
	 */
	public double microAccuracy() {
		int tpSum = sizeStream().map(this::truePositive).sum();
		int all = sumAll();
		return (double) tpSum / all;
	}
	/**
	 * 正解率MacroAccuracy (Average Accuracy)
	 * @return MacroAccuracy=AVG(TP/(TP+FN)) (MacroRecallと同じになっちゃうんですけどー)
	 */
	public double macroAccuracy() {
		return sizeStream()
				.mapToDouble(i -> (double) truePositive(i)/(truePositive(i)+falseNegative(i)) )
				.average().orElse(0.0);
	}
	/**
	 * 精度MicroPrecision
	 * @return MicroPrecision=SUM(TP)/SUM(TP+FP)
	 */
	public double microPrecision() {
		Set<ConfusionMatrix> cmset = splitMatrix4EachClass();
		int tpSum = cmset.stream().mapToInt(ConfusionMatrix::truePositive).sum();
		int tpfpSum = cmset.stream().mapToInt(ConfusionMatrix::classifiedPositives).sum();
		return (double) tpSum / tpfpSum;
	}
	/**
	 * 精度MacroPrecision
	 * @return MacroPrecision=AVG(TP/(TP+FP))
	 */
	public double macroPrecision() {
		return splitMatrix4EachClass().stream()
				.mapToDouble(ConfusionMatrix::precision)
				.average().orElse(0.0);
	}	
	/**
	 * 再現率(検出率)MicroRecall
	 * @return Recall=SUM(TP)/SUM(TP+FN)
	 */
	public double microRecall() {
		Set<ConfusionMatrix> cmset = splitMatrix4EachClass();
		int tpSum = cmset.stream().mapToInt(ConfusionMatrix::truePositive).sum();
		int tpfnSum = cmset.stream().mapToInt(ConfusionMatrix::positives).sum();
		return (double) tpSum / tpfnSum;
	}
	/**
	 * 再現率(検出率)MacroRecall
	 * @return Recall=AVG(TP/(TP+FN))
	 */
	public double macroRecall() {
		return splitMatrix4EachClass().stream()
				.mapToDouble(ConfusionMatrix::recall)
				.average().orElse(0.0);
	}
	/**
	 * F値(精度と再現率の調和平均)MicroF-score
	 * @return F-score=2*Precision*Recall/(Precision+Recall)
	 */
	public double microF_score() {
		return microFw_score(1.0);
	}
	/**
	 * F値(精度と再現率の調和平均)MacroF-score
	 * @return F-score=2*Precision*Recall/(Precision+Recall)
	 */
	public double macroF_score() {
		return macroFw_score(1.0);
	}
	/**
	 * 重み付きF値MicroWeighted F-score
	 * @param beta 重み
	 * @return Fw-score=(1+b^2)*Precision*Recall/(b^2*Precision+Recall)
	 */
	public double microFw_score(double beta) {
		double precision = microPrecision();
		double recall = microRecall();
		double b2 = beta * beta;
		return (1 + b2) * precision * recall / (b2 * precision + recall);
	}
	/**
	 * 重み付きF値MacroWeighted F-score
	 * @param beta 重み
	 * @return Fw-score=(1+b^2)*Precision*Recall/(b^2*Precision+Recall)
	 */
	public double macroFw_score(double beta) {
		double precision = macroPrecision();
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
						.boxed()				// IntStream -> Stream<Integer>
						.map(String::valueOf)	// Stream<Integer> -> Stream<String>
						.collect(Collectors.joining("\t,", "\t", "")))
				.collect(Collectors.joining("\n", "", "\n"));
	}
}