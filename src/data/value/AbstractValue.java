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
		AbstractValue other = (AbstractValue) obj;
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

	/** toString */
	@Override
	public String toString() {
		return "V:" + elem;
	}
}