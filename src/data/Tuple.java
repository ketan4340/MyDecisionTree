package data;

import java.util.LinkedList;
import java.util.List;

import data.attribute.AbstractAttribute;
import data.value.AbstractValue;

public class Tuple implements Cloneable{
	private List<AbstractValue<?>> vector;

	/** コンストラクタ */
	public Tuple(List<AbstractValue<?>> al) {
		this.vector = al;
	}

	/** getter */
	public List<AbstractValue<?>> getValueList() {
		return vector;
	}

	/* List用 */
	public AbstractValue<?> get(int index) {
		return vector.get(index);
	}
	public int size() {
		return vector.size();
	}

	@Override
	public Tuple clone() {
		try {
			Tuple c = (Tuple) super.clone();
	    	c.vector = new LinkedList<>(this.vector);	// 中身は複製しない(同じオブジェクトを参照する)
	    	return c;
	    } catch (CloneNotSupportedException ce) {
            ce.printStackTrace();
	    }
	    return null;
	}

	/**
	 * 指定の属性に対応する値を返す。
	 * @param splitAttr
	 * @return 指定の属性に対応する値
	 */
	public AbstractValue<?> getValueInAttr(AbstractAttribute<?> splitAttr) {
		for (AbstractValue<?> val : vector)
			if (val.getAttr().equals(splitAttr))
				return val;
		return null;
	}
	/**
	 * 指定の属性に対応する値を削除する。
	 * @param splitAttr
	 * @return 指定された属性に対応する値がこのタプルに含まれていた場合はtrue
	 */
	public boolean removeValueInAttr(AbstractAttribute<?> splitAttr) {
		for (AbstractValue<?> val : vector)
			if (val.getAttr().equals(splitAttr))
				return vector.remove(val);
		return false;
	}
}