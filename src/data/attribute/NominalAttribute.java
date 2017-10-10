package data.attribute;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.value.AbstractValue;
import data.value.ContinuousValue;
import data.value.NominalValue;

/** 名義属性(離散値) */
public class NominalAttribute extends AbstractAttribute<String> {

	public NominalAttribute(String label, Set<NominalValue> values) {
		super(label, new HashSet<>(values));
	}
	public NominalAttribute(String label) {
		super(label);
	}

	/**
	 * 属性値を離散値(Nominal)から連続値(Continuous)に変えた連続値属性を返す。事前に全ての属性値が数値に変換できることを確かめる必要がある。
	 * @return 最大値・最小値設定済みの連続値属性
	 */
	public ContinuousAttribute toContinuous() {
		ContinuousAttribute ca = new ContinuousAttribute(label);
		allValues.stream()							// この離散値属性の全ての値に対し
		.map(nv -> nv.getElem())					// 文字列(String)を取得
		.map(val_st -> Double.parseDouble(val_st))	// 数値(double)に変換
		.map(dbl -> new ContinuousValue(dbl, ca))	// 数値から連続値(ContinuousValue)を生成
		.forEach(cv -> ca.addValue(cv));			// 連続値属性に追加
		return ca;
	}
	/**
	 * 全ての属性値が数値なら真を，1つでも数値でない属性値があれば偽を返す。数値は整数と小数。符号の有無は問わない。小数(0.1など)を除き，先頭に0が並ぶことを許さない。
	 * @return 属性値が全て数値ならtrue
	 */
	public boolean hasOnlyNumber() {
		Pattern pattern = Pattern.compile("[+\\-]?([1-9]\\d*|0)(\\.\\d+)?");
		for (AbstractValue<String> abstractValue : allValues) {
			String val_st = abstractValue.getElem();
			Matcher matcher = pattern.matcher(val_st);
			if(!matcher.matches()) return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return "NmAt:" + label;
	}

}