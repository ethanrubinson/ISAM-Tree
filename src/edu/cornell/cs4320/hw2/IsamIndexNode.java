package edu.cornell.cs4320.hw2;

public class IsamIndexNode extends IsamNode {
	protected final Integer[] keys = new Integer[getSize()];
	protected final IsamNode[] children = new IsamNode[getSize() + 1];

	IsamIndexNode(int size) {
		super(size);
	}

	public Integer getIndex(int pos) {
		return keys[pos];
	}

	@Override
	public String search(Integer key) {

		Integer index = 0;

		while (index <= getSize()-1) {
			if (keys[index] == null) {
				break;
			}
			if (key < keys[index]) {
				break;
			}
			index++;
		}

		return children[index].search(key);
	}

	@Override
	public boolean insert(Integer key, String value) {

		Integer index = 0;

		while (index < getSize()) {
			if (keys[index] == null || key < keys[index]) {
				break;
			}
			
			index++;
		}

		return children[index].insert(key, value);

	}

	/**
	 * Create a child at a specific position and give the height of the subtree
	 * Only for internal use
	 */
	protected void createChild(int i, int height) {
		/*
		 * TODO: create the correct type of child depending on height and position
		 */
	}

	/**
	 * Get child at a specific position
	 */
	public IsamNode getChild(int pos) {
		return children[pos];
	}

	/**
	 * Get the to string for a specific child
	 * (this is a helper function for toString)
	 */
	private String getChildInOrderString(int pos) {
		IsamNode child = getChild(pos);

		if(child == null) {
			return "()";
		} else {
			return child.toString();
		}
	}

	@Override
	public String toString() {
		// Dont change this. This already does everything it is supposed to do
		String output = "";

		output += "(";

		int i = 0; 

		for(; i < getSize(); ++i) {
			output += getChildInOrderString(i);

			Integer index = getIndex(i);						
			if(index != null) {
				output += " " + index + " ";
			} else {
				output += " E ";
			}
		}

		output += getChildInOrderString(i);
		output += ")";

		return output;
	}

	@Override
	public boolean delete(Integer key) {

		Integer index = 0;

		while (index <= getSize()-1) {
			if (keys[index] == null) {
				break;
			}
			if (keys[index] < key) {
				break;
			}
			index++;
		}

		return children[index].delete(key); 
	}
}
