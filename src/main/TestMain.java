package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.MyCollections;

public class TestMain {
	public static void main(String[] args) {
		List<String> list = new ArrayList<>(Arrays.asList("a", "o", "j", "bi", "fy", "x", "iu", "sds","z"));
	
		List<String> subList = (List<String>) MyCollections.randomSamplingList(list, 4);
		
		System.out.println("list: " + list);
		System.out.println("subl: " + subList);
		
		
	}
}