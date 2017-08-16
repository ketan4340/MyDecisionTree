package data;

import java.util.List;

import data.value.AbstractValue;

public class Tuple {
	private List<AbstractValue> attrs;

	public Tuple(List<AbstractValue> al) {
		this.attrs = al;
	}


	/* getter */
	public List<AbstractValue> getValueList() {
		return attrs;
	}

	/* List用 */
	public AbstractValue get(int index) {
		return attrs.get(index);
	}
	public int size() {
		return attrs.size();
	}
}