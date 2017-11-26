package data;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import data.attribute.AbstractAttribute;
import data.attribute.Attributelist;
import data.attribute.NominalAttribute;
import data.value.AbstractValue;
import data.value.NominalValue;

/**
 * レコード．タプルとクラス属性値をもつ．現在離散値属性にしか対応していない．
 * @author tanabekentaro
 */
public class Record implements Cloneable{
	private Tuple tuple;
	private NominalValue classValue;

	/** コンストラクタ */
	public Record(Tuple t, NominalValue v) {
		this.tuple = t;
		this.classValue = v;
	}
	public Record(String values_str, Attributelist attrlist) {
		List<AbstractValue<?>> valueList = string2ValueList(values_str, attrlist);
		this.tuple = new Tuple(valueList.subList(0, valueList.size()-1));	// 最後尾以外が属性ベクトル
		this.classValue = (NominalValue) valueList.get(valueList.size()-1);	// 最後尾がクラス属性値
	}
	
	/**
	 * カンマで値が区切られたString型の属性ベクトルをAbstractValueのリストにする．
	 * @param values_st 文字列で表現された属性値ベクトル
	 * @param attrlist 属性リスト
	 * @return 属性値のリスト
	 */
	private List<AbstractValue<?>> string2ValueList(String values_st, Attributelist attrlist) {
		List<String> valueList_st = Arrays.asList(values_st.split(","));
		if (valueList_st.size() != attrlist.size() +1) return null;

		List<AbstractValue<?>> valueList = new LinkedList<>();
		Iterator<AbstractAttribute<?>> attrItr = attrlist.getList().iterator();
		Iterator<String> valItr = valueList_st.iterator();
		while (attrItr.hasNext() && valItr.hasNext()) {
			NominalAttribute nomAttr = (NominalAttribute) attrItr.next();
			String val_st = valItr.next();
			valueList.add(new NominalValue(val_st, nomAttr));	// 最初は全て離散変数として扱う
		}
		valueList.add(new NominalValue(valItr.next()));	// 最後尾のクラス属性値も離散変数として扱う
		return valueList;
	}

	/** getter */
	public Tuple getTuple() {
		return tuple;
	}
	public NominalValue getClassValue() {
		return classValue;
	}

	/* List用メソッド */
	public int size() {
		return tuple.size() + 1;
	}

	/** clone */
	@Override
	public Record clone() {
		try {
			Record c = (Record) super.clone();
	    	c.tuple = this.tuple.clone();
	    	c.classValue = this.classValue;		// 複製元と同じオブジェクトを参照
	    	return c;
	    } catch (CloneNotSupportedException ce) {
            ce.printStackTrace();
	    }
	    return null;
	}

	@Override
	public String toString() {
		return "Record [" + tuple + ", c:" + classValue + "]";
	}
	public String toOriginalString() {
		return "("+tuple.toOriginalString() + " :" + classValue.toOriginalString()+")";
	}

	/** このレコードの指定された属性に対応する値を返す。 */
	public AbstractValue<?> getValueInAttr(AbstractAttribute<?> attr) {
		return tuple.getValueInAttr(attr);
	}
	public boolean removeValueInAttr(AbstractAttribute<?> removeAttr) {
		return tuple.removeValueInAttr(removeAttr);
	}
}