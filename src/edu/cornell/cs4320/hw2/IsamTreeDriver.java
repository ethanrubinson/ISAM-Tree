package edu.cornell.cs4320.hw2;

import java.util.HashSet;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public class IsamTreeDriver {

	
	public static void main(String [] args){
		int pageSize = 3;
		Set<Entry<Integer,String>> data = new HashSet<Entry<Integer,String>>();
	
		data.add(new SimpleEntry<Integer,String>(13, "the"));
		data.add(new SimpleEntry<Integer,String>(2, "cake"));
		data.add(new SimpleEntry<Integer,String>(4, "is"));
		data.add(new SimpleEntry<Integer,String>(3, "a"));
		data.add(new SimpleEntry<Integer,String>(5, "lie"));
		data.add(new SimpleEntry<Integer,String>(6, "lie"));
		data.add(new SimpleEntry<Integer,String>(7, "lie"));
		data.add(new SimpleEntry<Integer,String>(8, "lie"));
		data.add(new SimpleEntry<Integer,String>(9, "lie"));
		data.add(new SimpleEntry<Integer,String>(10, "lie"));
		data.add(new SimpleEntry<Integer,String>(11, "lie"));
		data.add(new SimpleEntry<Integer,String>(12, "lie"));
		data.add(new SimpleEntry<Integer,String>(1, "lie"));
		data.add(new SimpleEntry<Integer,String>(14, "lie"));
		data.add(new SimpleEntry<Integer,String>(15, "lie"));
		data.add(new SimpleEntry<Integer,String>(16, "lie"));
		data.add(new SimpleEntry<Integer,String>(17, "lie"));
		
		/*data.add(new SimpleEntry<Integer,String>(10, "is"));
		data.add(new SimpleEntry<Integer,String>(15, "a"));
		data.add(new SimpleEntry<Integer,String>(20, "lie"));
		data.add(new SimpleEntry<Integer,String>(25, "lie"));
		data.add(new SimpleEntry<Integer,String>(30, "lie"));
		data.add(new SimpleEntry<Integer,String>(35, "lie"));
		data.add(new SimpleEntry<Integer,String>(40, "lie"));
		data.add(new SimpleEntry<Integer,String>(45, "lie"));*/
		
		
		IsamTree tree = new IsamTree(pageSize);
		tree.create(data);
		System.out.println();
		System.out.println("Our tree thingy:");
		System.out.println(tree.toString());
	}
}
