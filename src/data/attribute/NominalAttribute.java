package data.attribute;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import data.value.AbstractValue;
import data.value.NominalValue;

/** 名義属性(離散値) */
public class NominalAttribute extends AbstractAttribute {
	private Set<String> allKinds;

	public NominalAttribute(String label, Set<String> s) {
		super(label);
		this.allKinds = s;
	}
	public NominalAttribute(String label) {
		this(label, new HashSet<String>());
	}

	/**
	 * 属性値をStringからDoubleに変えた連続値属性を返す。連続値属性の最大値・最小値も計算して属性の情報に加える。先に全ての属性値が数値に変換できることを確かめる必要がある。
	 * @return 最大値・最小値設定済みの連続値属性
	 */
	public ContinuousAttribute toContinuous() {
		Set<Double> allNumbers = allKinds.stream()
				.map(n_st -> Double.parseDouble(n_st))
				.collect(Collectors.toSet());
		return new ContinuousAttribute(label, Collections.min(allNumbers), Collections.max(allNumbers));
	}
	/**
	 * 全ての属性値が数値なら真を，1つでも数値でない属性値があれば偽を返す。数値は整数と小数。符号の有無は問わない。小数(0.1など)を除き，先頭に0が並ぶことを許さない。
	 * @return 属性値が全て数値ならtrue
	 */
	public boolean hasOnlyNumber() {
		Pattern pattern = Pattern.compile("[+\\-]?([1-9]\\d*|0)(\\.\\d+)?");
		for (String val_st : allKinds) {
			Matcher matcher = pattern.matcher(val_st);
			if(!matcher.matches()) return false;
		}
		return true;
	}

	@Override
	public void addValue(AbstractValue av) {
		NominalValue nv = (NominalValue) av;
		String v = nv.getElem();
		allKinds.add(v);
	}

	/* getter */
	public Set<String> getAllValues() {
		return allKinds;
	}
}