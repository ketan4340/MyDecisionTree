package manage.evaluate;

/**
 * 混同行列．
 * @author tanabekentaro
 */
public class ConfusionMatrix {
	/**
	 * 真陽性(クラスCのデータをCに分類した)
	 */
	private int truePositive;
	/**
	 * 真陰性(クラスCではないデータをC以外のクラスに分類した)
	 */
	private int trueNegative;
	/**
	 * 偽陽性(クラスC以外のデータをCに分類した)
	 */
	private int falsePositive;
	/**
	 * 偽陰性(クラスCのデータをC以外のクラスに分類した)
	 */
	private int falseNegative;

	/* Constructor */
	public ConfusionMatrix(int tp, int tn, int fp, int fn) {
		this.truePositive = tp;
		this.trueNegative = tn;
		this.falsePositive = fp;
		this.falseNegative = fn;
	}
	public ConfusionMatrix() {
		this(0,0,0,0);
	}
	
	/* Getter */
	public int getTP() {
		return truePositive;
	}
	public int getTN() {
		return trueNegative;
	}
	public int getFP() {
		return falsePositive;
	}
	public int getFN() {
		return falseNegative;
	}
	
	@Override
	public String toString() {
		return "(TP:"+ truePositive +",TN:"+ trueNegative +",FP:"+ falsePositive +",FN:"+ falseNegative +")";
	}
	
	public void countTP() {
		truePositive++;
	}
	public void countTN() {
		trueNegative++;
	}
	public void countFP() {
		falsePositive++;
	}
	public void countFN() {
		falseNegative++;
	}
	
	/**
	 * 全データ数
	 * @return TP+TN+FP+FN
	 */
	public int sum() {
		return truePositive + trueNegative + falsePositive + falseNegative;
	}
	/**
	 * 正しく分類されたデータ数
	 * @return TP+TN
	 */
	public int trues() {
		return truePositive + trueNegative;
	}
	/**
	 * 誤って分類されたデータ数
	 * @return FP+FN
	 */
	public int falses() {
		return falsePositive + falseNegative;
	}
	/**
	 * 正データ数
	 * @return TP+FN
	 */
	public int positives() {
		return truePositive + falseNegative;
	}
	/**
	 * 負データ数
	 * @return TN+FP
	 */
	public int negatives() {
		return trueNegative + falsePositive;
	}
	/**
	 * 正データに分類された数
	 * @return TP+FP
	 */
	public int classifiedPositives() {
		return truePositive + falsePositive;
	}
	/**
	 * 負データに分類された数
	 * @return TN+FN
	 */
	public int classifiedNegatives() {
		return trueNegative + falseNegative;
	}
	
	
	/**
	 * 陽性適中率PPV(Positive Predictive Value)
	 * @return PPV=TP/(TP+FP)
	 */
	public double positivePredictiveValue() {
		return (double) truePositive / classifiedPositives();
	}
	/**
	 * 偽発見率FDR(False Discovery Rate)
	 * @return FDR=FP/(TP+FP)=1-PPV
	 */
	public double falseDiscoveryRate() {
		return (double) falsePositive / classifiedPositives();
	}
	/**
	 * 陰性適中率NPV(Negative Predictive Value)
	 * @return NPV=TN/(TN+FN)
	 */
	public double negativePredictiveValue() {
		return (double) trueNegative / classifiedNegatives();
	}
	/**
	 * 陰性偽発見率(仮)NFDR(Negative False Discovery Rate)
	 * @return FN/(TN+FN)=1-NPV
	 */
	public double negativeFalseDiscoveryRate() {
		return (double) falseNegative / classifiedNegatives();
	}
	/**
	 * 真陽性率TPR(True Positive Rate)
	 * @return TPR=TP/(TP+FN)
	 */
	public double truePositiveRate() {
		return (double) truePositive / positives();
	}
	/**
	 * 真陰性率TNR(True Negative Rate)
	 * @return TNR=TN/(TN+FP)
	 */
	public double trueNegativeRate() {
		return (double) trueNegative / negatives();
	}
	/**
	 * 偽陽性率FPR(False Positive Rate)
	 * @return FPR=FP/(TN+FP)=1-TNR
	 */
	public double falsePositiveRate() {
		return (double) falsePositive / negatives();
	}
	/**
	 * 偽陰性率FNR(False Negative Rate)
	 * @return FNR=FN/(TP+FN)=1-TPR
	 */
	public double falseNegativeRate() {
		return (double) falseNegative / positives();
	}

	
	
	/**
	 * 正解率(認識率)Accuracy
	 * @return ACC=(TP+TN)/(TP+TN+FP+FN)
	 */
	public double accuracy() {
		return (double) trues() / sum();
	}
	/**
	 * 誤り率(誤分類率,不正解率)Error Rate
	 * @return ERR=(FP+FN)/(TP+TN+FP+FN)=1-ACC
	 */
	public double errorRate() {
		return 1-accuracy();
	}
	/**
	 * 精度Precision(=PPV)
	 * @return Precision=TP/(TP+FP)=PPV
	 */
	public double precision() {
		return positivePredictiveValue();
	}
	/**
	 * 再現率(検出率)Recall(=TPR=Sensitivity)
	 * @return Recall=TP/(TP+FN)=TPR
	 */
	public double recall() {
		return truePositiveRate();
	}
	/**
	 * 感度Sensitivity(=TPR=Recall)
	 * @return Sensitivity=TP/(TP+FN)=TPR
	 */
	public double sensitivity() {
		return truePositiveRate();
	}
	/**
	 * 特異度Specificity(=TNR)
	 * @return Specificity=TN/(TN+FP)=TNR
	 */
	public double specificity() {
		return trueNegativeRate();
	}
	/**
	 * F値(精度と再現率の調和平均)F-measure
	 * @return F-measure=2*Precision*Recall/(Precision+Recall)
	 */
	public double f_measure() {
		return fw_measure(1.0);
	}
	/**
	 * 重み付きF値Weighted F-measure
	 * @param beta 重み
	 * @return Fw-measure=(1+b^2)*Precision*Recall/(b^2*Precision+Recall)
	 */
	public double fw_measure(double beta) {
		double precision = precision();
		double recall = recall();
		double b2 = beta * beta;
		return (1 + b2) * precision * recall / (b2 * precision + recall);
	}
	
}