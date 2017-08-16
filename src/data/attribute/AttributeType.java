package data.attribute;

public enum AttributeType {
	Nominal("名義属性"),
	//Ordinal("順序属性"),
	Continuous("数値属性");

	/** フィールド変数 */
	private String label;

	/** コンストラクタ */
	AttributeType(String label) {
		this.label = label;
	}

	/** 名称取得メソッド */
	public String getLabel() {
		return this.label;
	}
}
