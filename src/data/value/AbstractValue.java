package data.value;

import data.attribute.AbstractAttribute;

/**
 * 属性値．
 * @author tanabekentaro
 * @param <E> 属性値の型
 */
public abstract class AbstractValue<E extends Comparable<E>> implements Comparable<AbstractValue<E>>{
	protected E elem;					// 値
	protected AbstractAttribute<E> attr;	// 所属する属性

	/* コンストラクタ */
	/**
	 * 属性に所属する値．
	 * @param elem 値
	 * @param attribute 所属する属性
	 */
	protected AbstractValue(E elem, AbstractAttribute<E> attribute) {
		this.elem = elem;
		this.attr = attribute;
		this.attr.addValue(this);
	}
	/**
	 * 未所属の値．クラス値に使用．
	 * @param elem 値
	 */
	protected AbstractValue(E elem) {
		this.elem = elem;
	}

	/* getter */
	public E getElem() {
		return elem;
	}
	public AbstractAttribute<E> getAttr() {
		return attr;
	}
	/* setter */
	public void setElem(E elem) {
		this.elem = elem;
	}
	public void setAttr(AbstractAttribute<E> attr) {
		this.attr = attr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attr == null) ? 0 : attr.hashCode());
		result = prime * result + ((elem == null) ? 0 : elem.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractValue<?> other = (AbstractValue<?>) obj;
		if (attr == null) {
			if (other.attr != null)
				return false;
		} else if (!attr.equals(other.attr))
			return false;
		if (elem == null) {
			if (other.elem != null)
				return false;
		} else if (!elem.equals(other.elem))
			return false;
		return true;
	}
	@Override
	public int compareTo(AbstractValue<E> v) {
		return elem.compareTo(v.elem);
	}
	@Override
	public String toString() {
		return "V:" + elem;
	}
	public String toOriginalString() {
		return elem.toString();
	}
}