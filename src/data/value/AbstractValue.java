package data.value;

import data.attribute.AbstractAttribute;

public abstract class AbstractValue<E> {
	protected E elem;									// 値
	protected AbstractAttribute<AbstractValue<E>> attr;	// 所属する属性

	/** コンストラクタ */
	protected <V extends AbstractValue<E>> AbstractValue(E e, AbstractAttribute<V> aa) {
		this.elem = e;
		this.attr = (AbstractAttribute<AbstractValue<E>>) aa;
		this.attr.addValue(this);
	}
	protected AbstractValue(E e) {
		this.elem = e;
	}

	/** getter */
	public E getElem() {
		return elem;
	}
	public AbstractAttribute<AbstractValue<E>> getAttr() {
		return attr;
	}
	/** setter */
	public void setElem(E elem) {
		this.elem = elem;
	}
	public void setAttr(AbstractAttribute<AbstractValue<E>> attr) {
		this.attr = attr;
	}

	/** Object基本メソッド */
	/** hashCode */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attr == null) ? 0 : attr.hashCode());
		return result;
	}
	/** equals */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractValue<E> other = (AbstractValue<E>) obj;
		if (attr == null) {
			if (other.attr != null)
				return false;
		} else if (!attr.equals(other.attr))
			return false;
		return true;
	}
}