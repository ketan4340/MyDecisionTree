package main;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import data.value.NominalValue;
import manage.evaluate.MultiConfusionMatrix;

public class EvaluatorTest {

	public static void main(String[] args) {
		List<NominalValue> classes = Stream.of("みかん", "りんご", "ぶどう").map(NominalValue::new).collect(Collectors.toList());

		int[][] matrix = {
				{91, 1, 3}, 
				{5, 17, 1},
				{4, 2, 1}
		};
		
		
		MultiConfusionMatrix mcm = new MultiConfusionMatrix(matrix, classes);

		System.out.println(mcm.toString());
		
		System.out.println("micro precision,\tmacro precision");
		System.out.println(mcm.microPrecision() + ",\t" + mcm.macroPrecision());
		System.out.println("micro recall,\tmacro recall");
		System.out.println(mcm.microRecall() + ",\t" + mcm.macroRecall());
		System.out.println("micro F-measure,\tmacro F-measure");
		System.out.println(mcm.microF_score() + ",\t" + mcm.macroF_score());
		System.out.println("micro accuracy,\tmacro accuracy");
		System.out.println(mcm.microAccuracy() + ",\t" + mcm.macroAccuracy());
	}
}