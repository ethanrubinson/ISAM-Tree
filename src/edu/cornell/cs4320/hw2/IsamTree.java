package edu.cornell.cs4320.hw2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A very simple ISAM tree that makes a few assumptions
 * 
 * - Key is always an Integer
 * - Value is always a String
 */
public class IsamTree {	
	public IsamTree(int pageSize) {
		assert(pageSize > 0);
		
		this.pageSize = pageSize;
	}
	
	/**
	 * Create initial tree from a data set
	 */
	public void create(Set<Entry<Integer,String>> entries) {
		assert(entries.size() > this.pageSize);
		
		// Calculate the height of the tree
		int branchingFactor = pageSize + 1;
		int dataPagesRequired = (int) Math.ceil(entries.size() / (double) pageSize);
		height = (int) Math.ceil(Math.log(dataPagesRequired) / Math.log(branchingFactor));
		
	
		SortedSet<Entry<Integer,String>> sortedEntries = new TreeSet<Entry<Integer,String>>(new CustomEntryComparator());
		sortedEntries.addAll(entries);
		
		// Build the Data Buckets
		int curBucketSize = 0;
		IsamDataNode curDataNode = new IsamDataNode(pageSize);
		ArrayList<IsamNode> dataNodes = new ArrayList<IsamNode>();
		for(Entry<Integer,String> entry : sortedEntries) {
			System.out.println("Processing data entry: " + entry.toString());
			
			curDataNode.keys[curBucketSize] = entry.getKey();
			curDataNode.values[curBucketSize] = entry.getValue();
			
			curBucketSize++;
			
			if(curBucketSize == pageSize){
				dataNodes.add(curDataNode);
				curDataNode = new IsamDataNode(pageSize);
				curBucketSize = 0;
			}
		}
		// Make sure we still add the final data node if it wasn't filled all the way
		if(curBucketSize != 0){
			dataNodes.add(curDataNode);
		}
		
		System.out.println();
		System.out.println("Data Nodes in Their Corresponding Buckets");
		System.out.println(dataNodes.toString());
		
		// Build the indexes levels
		/* 
		 * The correct value for an indexNode key is the lowest value of the bucket
		 * found by finding the lowest value of its child whose index is = key + 1 
		 */
		
		ArrayList<IsamNode> prevIndexNodes = dataNodes;
		int currentHeight = 1;
		while(currentHeight <= height){
			ArrayList<IsamNode> nextIndexNodes = new ArrayList<IsamNode>();
			IsamIndexNode curIndexNode = new IsamIndexNode(pageSize);
			curBucketSize = 0;
			// Add the children for the index nodes
			for(IsamNode node : prevIndexNodes){
				curIndexNode.children[curBucketSize] = node;
				
				curBucketSize++;
				
				if(curBucketSize == pageSize + 1){
					nextIndexNodes.add(curIndexNode);
					curIndexNode = new IsamIndexNode(pageSize);
					curBucketSize = 0;
				}
			}
			// Make sure we still add the final index node if it wasn't filled all the way 
			if(curBucketSize != 0){
				nextIndexNodes.add(curIndexNode);
			}
			
			System.out.println("Built a row of index Nodes");
			System.out.println(nextIndexNodes.toString());
			System.out.println();
			
			
			// Compute the keys for each
			for (IsamNode node : nextIndexNodes){
				int curKeyPos = 0;
				
				while(curKeyPos < pageSize){
					IsamIndexNode indexNode = (IsamIndexNode)node;
					// Make sure it should get a key in the first place
					if (indexNode.children[curKeyPos + 1] != null) {
						// Iterate through the tree to find the dataNode
						IsamNode curIsamNode = indexNode.children[curKeyPos + 1];
						while(! (curIsamNode instanceof IsamDataNode)){
							curIsamNode = ((IsamIndexNode)curIsamNode).children[0];
						}
						indexNode.keys[curKeyPos] = ((IsamDataNode)curIsamNode).keys[0];
						curKeyPos++;
					}
					else{
						break;
					}
				}
			}
			
			System.out.println("Added Propper keys for Nodes");
			System.out.println(nextIndexNodes.toString());
			System.out.println();
			
			// Get ready for the next iteration
			prevIndexNodes = nextIndexNodes;
			currentHeight++;
		}
		
		assert(prevIndexNodes.size() == 1);
		System.out.println(prevIndexNodes.size());
		this.root = (IsamIndexNode)prevIndexNodes.get(0);
	}
	
	/**
	 * Get the height of this tree
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Get the root of this tree
	 * Note: Should only be used internally and/or by helper classes
	 */
	protected IsamIndexNode getRoot() {
		return root;
	}
	
	/**
	 * Get a in-order string representation of the tree
	 * 
	 * Note:
	 *  - this only prints the indexes
	 *  - index nodes should be shown by curly braces
	 *  - data nodes by square brackets
	 *  - empty indexes by the letter 'E'
	 *  - and empty nodes/subtrees by ()
	 *  
	 *  See the unit tests for examples
	 */
	public String toString() {
		return root.toString();
	}
	
	/**
	 * Search for a specific entry
	 * Returns the entry if found and null otherwise
	 */
	public String search(Integer key) {
		return root.search(key);
	}
	
	/**
	 * Insert a new value
	 * This will return false if the value already exists
	 */
	public boolean insert(Integer key, String value) {
		return root.insert(key, value);
	}
	
	/**
	 * Remove the entry with the specified key
	 * This will return false if the value wasn't found
	 */
	public boolean delete(Integer key) {
		return root.delete(key);
	}
	
	/**
	 * Get the size of one page/node
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * Root of the tree
	 * It is assumed that this is never a data node
	 */
	private IsamIndexNode root;

	/**
	 * Size of each node/page 
	 * This is set via the constructor
	 */
	private final int pageSize;
	
	/**
	 * The height of the tree
	 * Should be calculated by create()
	 */
	private int height;
	
	
	private class CustomEntryComparator implements Comparator<Entry<Integer,String>>{

		@Override
		public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2) {
			if (o1.getKey() < o2.getKey()) return -1;
			
			else if(o1.getKey() > o2.getKey()) return 1;
			
			return 0;
		}
		
	}
}
