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
	/** カンマで値が区切られたString型の属性ベクトルをAbstractValueのリストにする。 */
	private List<AbstractValue<?>> string2ValueList(String values_st, Attributelist attrlist) {
		List<String> valueList_st = Arrays.asList(values_st.split(","));
		if (valueList_st.size() != attrlist.size()) return null;

		List<AbstractValue<?>> valueList = new LinkedList<>();
		Iterator<AbstractAttribute<?>> itrAttr = attrlist.getList().iterator();
		Iterator<String> itrValue = valueList_st.iterator();
		while (itrAttr.hasNext() && itrValue.hasNext()) {
			NominalAttribute nomAttr = (NominalAttribute) itrAttr.next();
			String val_st = itrValue.next();
			valueList.add(new NominalValue(val_st, nomAttr));	// 最初は全て離散変数として扱う
		}
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
		return tuple.size();
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

	/** このレコードの指定された属性に対応する値を返す。 */
	public AbstractValue<?> getValueInAttr(AbstractAttribute<?> splitAttr) {
		return tuple.getValueInAttr(splitAttr);
	}
	public boolean removeValueInAttr(AbstractAttribute<?> splitAttr) {
		return tuple.removeValueInAttr(splitAttr);
	}
}