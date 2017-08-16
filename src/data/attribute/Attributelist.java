package data.attribute;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Attributelist {
	private List<AbstractAttribute> attrlist;

	public Attributelist(List<AbstractAttribute> al) {
		this.attrlist = al;
	}
	public Attributelist() {
		this(new ArrayList<AbstractAttribute>());
	}
	public Attributelist(Path path) {
		try {
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			this.attrlist = lines.stream()						// 1行ずつ確認(大抵1行で十分書き切れる)
					.map(line -> line.split(","))				// カンマで分割
					.flatMap(attrs -> Arrays.stream(attrs))		// 分割後の配列を展開
					.map(attr -> attr.replaceAll("[ \t]", ""))	// ラベルのスペースやタブを削除
					.map(label -> new NominalAttribute(label))	// 離散属性としてインスタンス生成(連続値はあとで変える)
					.collect(Collectors.toList());				// リスト化
			this.attrlist.add(new NominalAttribute("class"));	// 最後尾にクラス属性の分を追加
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* getter */
	public List<AbstractAttribute> getList() {
		return attrlist;
	}

	/* Listの基本メソッド */
	public int size() {
		return attrlist.size();
	}

}
