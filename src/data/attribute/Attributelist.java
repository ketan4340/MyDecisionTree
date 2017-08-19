package data.attribute;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import data.value.AbstractValue;

public class Attributelist implements Cloneable{
	private List<AbstractAttribute<?>> attrs;

	/** コンストラクタ */
	public Attributelist(List<AbstractAttribute<?>> al) {
		this.attrs = al;
	}
	public Attributelist() {
		this(new ArrayList<AbstractAttribute<?>>());
	}
	public Attributelist(Path path) {
		try {
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			this.attrs = lines.stream()							// 1行ずつ確認(大抵1行で十分書き切れる)
					.map(line -> line.split(","))				// カンマで分割
					.flatMap(attrs -> Arrays.stream(attrs))		// 分割後の配列を展開
					.map(attr -> attr.replaceAll("[ \t]", ""))	// ラベルのスペースやタブを削除
					.map(label -> new NominalAttribute(label))	// 離散属性としてインスタンス生成(連続値はあとで変える)
					.collect(Collectors.toList());				// リスト化
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** getter */
	public List<AbstractAttribute<?>> getList() {
		return attrs;
	}

	/* Listの基本メソッド */
	public int size() {
		return attrs.size();
	}
	public boolean isEmpty() {
		return attrs.isEmpty();
	}
	public ListIterator<AbstractAttribute<?>> listIterator() {
		return attrs.listIterator();
	}

	public boolean removeAttr(AbstractAttribute<?> removeAttr) {
		return attrs.remove(removeAttr);
	}

	/** clone */
	@Override
	public Attributelist clone() {
		try {
			Attributelist c = (Attributelist) super.clone();
	    	c.attrs = new LinkedList<>(this.attrs);	// 中身は複製しない(同じオブジェクトを参照する)
	    	return c;
	    } catch (CloneNotSupportedException ce) {
            ce.printStackTrace();
	    }
	    return null;
	}
	/** toString */
	@Override
	public String toString() {
		return "Attributelist [attrs=" + attrs.stream().map(at -> at.toString()).collect(Collectors.joining(",")) + "]";
	}

}