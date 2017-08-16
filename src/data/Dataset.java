package data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import data.attribute.AbstractAttribute;
import data.attribute.AttributeType;
import data.attribute.Attributelist;
import data.attribute.NominalAttribute;
import data.value.AbstractValue;

public class Dataset {
	private Set<Record> records;
	private List<AttributeType> types;

	public Dataset(Set<Record> rs, Attributelist attrlist) {
		this.records = rs;
		this.types = judgeAttributeTypes(attrlist);
	}
	public Dataset() {
		this(new HashSet<Record>(), new Attributelist());
	}
	public Dataset(Path path, Attributelist attrlist) {
		try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
			this.records = stream
					.map(values -> new Record(values, attrlist))
					.collect(Collectors.toSet());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.types = judgeAttributeTypes(attrlist);
	}

	private List<AttributeType> judgeAttributeTypes(Attributelist attrlist) {
		List<AttributeType> types = new ArrayList<>(attrlist.size());
		for (ListIterator<AbstractAttribute> lsitr = attrlist.getList().listIterator(); lsitr.hasNext(); ) {
			NominalAttribute na = (NominalAttribute) lsitr.next();
			if(na.hasOnlyNumber()) {			// 全ての属性値が連続値なら
				lsitr.set(na.toContinuous());	// 数値属性に置き換える
				types.add(AttributeType.Continuous);
			} else {
				types.add(AttributeType.Nominal);
			}
		}
		return types;
	}

	/**
	 * このデータセットのRecordのSetを属性値のListにして返します．行の集まりを列の集まりにする．
	 * @return このデータセット内の属性の値のリスト
	 */
	private List<AbstractValue> getColumn(int index) {
		return records.stream()
				.map(r -> r.getTuple())			// レコードからタプル取り出し
				.map(t -> t.get(index))			// タプルのindex番目の値取り出し
				.collect(Collectors.toList());	// リスト化
	}

	/* getter */
	public Set<Record> getSet() {
		return records;
	}
	public List<AttributeType> getTypes() {
		return types;
	}
}