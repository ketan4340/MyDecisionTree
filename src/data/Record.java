package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import data.attribute.AbstractAttribute;
import data.attribute.Attributelist;
import data.attribute.NominalAttribute;
import data.value.AbstractValue;
import data.value.NominalValue;

public class Record {
	private Tuple tuple;
	private AbstractValue classValue;

	public Record(Tuple t, AbstractValue v) {
		this.tuple = t;
		this.classValue = v;
	}
	public Record(String values_str, Attributelist attrlist) {
		List<AbstractValue> valueList = string2ValueList(values_str, attrlist);
		this.tuple = new Tuple(valueList.subList(0, valueList.size()-1));	// 最後尾以外が属性ベクトル
		this.classValue = valueList.get(valueList.size()-1);	// 最後尾がクラス属性値
	}
	private List<AbstractValue> string2ValueList(String values_st, Attributelist attrlist) {
		List<String> valueList_st = Arrays.asList(values_st.split(","));
		if (valueList_st.size() != attrlist.size()) return null;

		List<AbstractValue> valueList = new ArrayList<>();
		Iterator<AbstractAttribute> itrAttr = attrlist.getList().iterator();
		Iterator<String> itrValue = valueList_st.iterator();
		while (itrAttr.hasNext() && itrValue.hasNext()) {
			NominalAttribute nomAttr = (NominalAttribute) itrAttr.next();
			String val_st = itrValue.next();
			valueList.add(new NominalValue(nomAttr, val_st));	// 最初は全て離散変数として扱う
		}
		return valueList;
	}
	/* List用メソッド */
	public int size() {
		return tuple.size();
	}

	/* getter */
	public Tuple getTuple() {
		return tuple;
	}
	public AbstractValue getClassValue() {
		return classValue;
	}
}