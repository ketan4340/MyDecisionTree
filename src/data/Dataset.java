package data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import data.attribute.AbstractAttribute;
import data.attribute.Attributelist;
import data.attribute.ContinuousAttribute;
import data.attribute.NominalAttribute;
import data.value.AbstractValue;
import data.value.NominalValue;

public class Dataset {
	private Set<Record> records;

	public Dataset(Set<Record> rs, Attributelist attrlist) {
		this.records = rs;
		attrlist.replaceContinuousAttribute(this);
	}
	public Dataset() {
		this(new HashSet<Record>(), new Attributelist());
	}
	public Dataset(Path path, Attributelist attrlist) {
		try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
			this.records = stream
					.map(values -> new Record(values, attrlist))
					.collect(Collectors.toSet());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Recordを全て用意したあと，属性リストから連続値属性を探し出して置き換える処理を呼ぶ
		attrlist.replaceContinuousAttribute(this);
	}

	/** getter */
	public Set<Record> getSet() {
		return records;
	}

	/** Set用メソッド */
	public int size() {
		return records.size();
	}


	/**
	 * 全てのレコードが同じクラス属性値をもつならその属性値を、2種類以上のクラス属性値があればnullを返す。
	 * @return 全てのレコードの同じクラス属性値。不一致の場合null
	 */
	public NominalValue getCommonClassValue() {
		NominalValue nv = null;
		for (Record r : records) {
			if (nv!=null)
				if (!nv.equals(r.getClassValue()))
					return null;
			nv = r.getClassValue();
		}
		return nv;
	}

	/**
	 * 全てのタプルのクラス属性値でもっとも多いものを返す。
	 * @return 最頻度のクラス属性値
	 */
	public NominalValue getMajorityClassValue() {
		// カウントパート
		Map<NominalValue, Integer> valueFreqency = countClassFreqency();
		// 最頻度属性値選出パート
		NominalValue majValue = null;
		int highFreq = 0;
		for (Map.Entry<NominalValue, Integer> entry : valueFreqency.entrySet()) {
			int freq = entry.getValue();
			if (freq > highFreq) {
				majValue = entry.getKey();
				highFreq = freq;
			}
		}
		return majValue;
	}
	/**
	 * クラス属性値ごとのデータセットでの出現頻度をMapにして返す
	 * @return クラス属性値の出現頻度
	 */
	private Map<NominalValue, Integer> countClassFreqency() {
		Map<NominalValue, Integer> classValFreq = new HashMap<>();
		for (Record r : records) {
			NominalValue nv = r.getClassValue();
			int count = classValFreq.getOrDefault(nv, 0) + 1;
			classValFreq.put(nv, count);
		}
		return classValFreq;
	}

	/**
	 * 情報利得率で分割属性を決める。
	 * @return
	 */
	public AbstractAttribute<?> getJudgeAttrByGainRation() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	/**
	 * 情報量。エントロピー。
	 * @return
	 */
	private double info() {
		double info = 0;
		int datasetSize = size();
		Map<NominalValue, Integer> classValFreq = countClassFreqency();
		for (Map.Entry<NominalValue, Integer> entry : classValFreq.entrySet()) {
			int freq = entry.getValue();
			double prob = freq/datasetSize;
			info += prob * Math.log(prob)/Math.log(2);
		}
		return info;
	}
	private double infoByAttr(AbstractAttribute attr) {
		double infoA = 0;
		int datasetSize = size();
		Map<NominalValue, Integer> classValFreq = countClassFreqency();
		for (Map.Entry<NominalValue, Integer> entry : classValFreq.entrySet()) {
			int freq = entry.getValue();
			double prob = freq/datasetSize;
			infoA += prob * Math.log(prob)/Math.log(2);
		}
		return infoA;
	}

	public Map<AbstractValue<?>, Dataset> splitSetBy(AbstractAttribute<?> splitAttr) {
		Map<AbstractValue<?>, Dataset> subsets = new HashMap<>();
		if (splitAttr instanceof NominalAttribute) {
			NominalAttribute splitNA = (NominalAttribute) splitAttr;
			for (NominalValue nv : splitNA.getAllValues()) {

			}
		} else if (splitAttr instanceof ContinuousAttribute) {

		}
	}

	private double splitInfo() {

	}

}