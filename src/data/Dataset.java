package data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import data.attribute.AbstractAttribute;
import data.attribute.AttributeType;
import data.attribute.Attributelist;
import data.attribute.ContinuousAttribute;
import data.attribute.NominalAttribute;
import data.value.AbstractValue;
import data.value.ContinuousValue;
import data.value.NominalValue;

public class Dataset implements Cloneable{
	private Set<Record> records;
	private Attributelist attrlist;

	/** コンストラクタ */
	public Dataset(Set<Record> recordSet, Attributelist attrlist) {
		this.records = recordSet;
		// 可能なら離散値から連続値に置き換える
		this.attrlist = attrlist;
		replaceContinuousAttribute();
	}
	public Dataset(Attributelist attrlist) {
		this.attrlist = attrlist;
	}
	public Dataset() {
		this.records = new HashSet<Record>();
	}
	public Dataset(Path path, Attributelist attrlist) {
		try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
			this.records = stream
					.map(values -> new Record(values, attrlist))
					.collect(Collectors.toSet());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.attrlist = attrlist;
		// Recordを全て用意したあと，属性リストから連続値属性を探し出して置き換える処理を呼ぶ
		replaceContinuousAttribute();
	}

	/** getter */
	public Set<Record> getSet() {
		return records;
	}

	/** Set用メソッド */
	public int size() {
		return records.size();
	}
	public boolean add(Record rcd) {
		return records.add(rcd);
	}

	/** clone */
	@Override
	public Dataset clone() {
		try {
			Dataset c = (Dataset) super.clone();
	    	c.records = new HashSet<>(this.records);
	    	c.attrlist = this.attrlist.clone();
	    	return c;
	    } catch (CloneNotSupportedException ce) {
            ce.printStackTrace();
	    }
	    return null;
	}

	/**
	 * 各属性をみて値が連続値なら数値属性(ContinuousAttribute)に置き換える
	 * @return
	 */
	public List<AttributeType> replaceContinuousAttribute() {
		List<AttributeType> types = new ArrayList<>(size());
		ListIterator<AbstractAttribute<?>> attrItr = attrlist.getList().listIterator();
		while (attrItr.hasNext()) {
			AbstractAttribute<?> attr = attrItr.next();
			if (!(attr instanceof NominalAttribute)) continue;
			NominalAttribute nomAttr = (NominalAttribute) attr;
			if(nomAttr.hasOnlyNumber()) {			// 全ての属性値が連続値なら
				attrItr.set(nomAttr.toContinuous());	// 数値属性に置き換える
				types.add(AttributeType.Continuous);
			} else {
				types.add(AttributeType.Nominal);
			}
		}
		return types;
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

	/**
	 * 指定された属性の値ごとにデータセットを分割する。
	 * @param splitAttr 分割基準の属性
	 * @return 指定属性の値と分割されたサブデータセットのマップ
	 */
	public Map<AbstractValue<?>, Dataset> splitByAttr(AbstractAttribute<?> splitAttr) {
		Map<AbstractValue<?>, Dataset> subsetsMap = new HashMap<>();
		if (splitAttr instanceof NominalAttribute) {
			NominalAttribute splitNA = (NominalAttribute) splitAttr;
			subsetsMap.putAll(splitByNominalAttr(splitNA));
		} else if (splitAttr instanceof ContinuousAttribute) {
			ContinuousAttribute splitCA = (ContinuousAttribute) splitAttr;
			subsetsMap.putAll(splitByContinuousAttr(splitCA));
		}
		return subsetsMap;
	}
	private Map<NominalValue, Dataset> splitByNominalAttr(NominalAttribute splitNA) {
		Map<NominalValue, Dataset> subsetsMap = new HashMap<>();
		// 該当属性を削除した属性リストを用意
		Attributelist subAttrlist = attrlist.clone();
		subAttrlist.removeAttr(splitNA);
		// 該当属性の値の分だけ空のサブデータセットを用意
		for (NominalValue nomVal : splitNA.getAllValues())
			subsetsMap.put(nomVal, new Dataset(subAttrlist));

		for (Record rcd : records) {
			// key
			NominalValue nomVal = (NominalValue) rcd.getValueInAttr(splitNA);
			// value
			Dataset subDS = subsetsMap.get(nomVal);

			// 該当属性の値を削除したレコードをサブデータセットに追加
			Record reducedRcd = rcd.clone();
			reducedRcd.removeValueInAttr(splitNA);
			subDS.add(rcd.clone());
		}
		return subsetsMap;
	}
	private Map<ContinuousValue, Dataset> splitByContinuousAttr(ContinuousAttribute splitCA) {
		Map<ContinuousValue, Dataset> subsetsMap = new HashMap<>();
		// TODO
		return null;
	}
	private double splitInfo() {

	}

}