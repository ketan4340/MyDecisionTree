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

	public Dataset(Set<Record> rs, Attributelist attrlist) {
		this.records = rs;
		attrlist.replaceContinuousAttribute(this);
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
		// Recordを全て用意したあと，属性リストから連続値属性を探し出して置き換える処理を呼ぶ
		attrlist.replaceContinuousAttribute(this);
	}

	/* getter */
	public Set<Record> getSet() {
		return records;
	}

	/**
	 * 全てのレコードが同じクラス属性値をもつならtrueを、2種類以上のクラス属性値があればfalseを返す。
	 * @return 全てのレコードが同じクラス属性値をもつ場合はtrue
	 */
	public boolean matchAllValue() {
		AbstractValue v = null;
		for (Record r : records) {
			if (v!=null)
				if (!v.equals(r.getClassValue()))
					return false;
			v = r.getClassValue();
		}
		return true;
	}
}