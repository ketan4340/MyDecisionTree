package util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MyCollections{

	private MyCollections() {
	}
	
	/**
	 * コレクションから無作為に指定された数だけ要素を非復元抽出する.
	 * @param list 抽出元のコレクション.非復元抽出なのでこのコレクションも変化する.
	 * @param sampleNum 抽出する数
	 * @return 抽出した要素のコレクション
	 */
	public static <E> List<E> randomSamplingList(List<E> list, int sampleNum) {
		if (sampleNum > list.size())
			throw new IllegalArgumentException("抽出する要素数(" + sampleNum + ")が母集団の要素数(" + list.size() + ")を超えています．");
		
		List<E> sampleList = new ArrayList<>(sampleNum);
		
		/* Knuthの非復元抽出アルゴリズム */
		int t = 0; // total input records dealt with (0..popSize)
		Iterator<E> itr = list.iterator();
		while (sampleList.size() < sampleNum) {
			E elem = itr.next();
			if (Math.random() * (list.size() - t) < (sampleNum - sampleList.size())) {
				sampleList.add(elem);
				itr.remove();
			} else {
				t++;
			}
		}
		return sampleList;
	}
	
	public static <E> Set<E> randomSamplingSet(Set<E> set, int sampleNum) {
		if (sampleNum > set.size())
			throw new IllegalArgumentException("抽出する要素数(" + sampleNum + ")が母集団の要素数(" + set.size() + ")を超えています．");
		
		Set<E> sampleSet = new HashSet<>(sampleNum);
		
		/* Knuthの非復元抽出アルゴリズム */
		int t = 0; // total input records dealt with (0..popSize)
		Iterator<E> itr = set.iterator();
		while (sampleSet.size() < sampleNum) {
			E elem = itr.next();
			if (Math.random() * (set.size() - t) < (sampleNum - sampleSet.size())) {
				sampleSet.add(elem);
				itr.remove();
			} else {
				t++;
			}
		}
		return sampleSet;
	}
}	