package edu.cornell.cs4320.hw2;

public class IsamDataNode extends IsamNode {
	protected final Integer[] keys = new Integer[getSize()];
	protected final String[] values = new String[getSize()];

	private IsamDataNode successor = null;

	IsamDataNode(int size) {
		super(size);
	}

	@Override
	public String search(Integer key) {

		Integer index = 0;

		while (index <= getSize()-1) {
			if (keys[index] == key) {
				return values[index];
			}
			index++;
		}
		if (hasOverflow()) {
			return getOverflowPage().search(key);
		}

		return null;
	}

	public boolean hasOverflow() {
		return successor != null;
	}

	public IsamDataNode getOverflowPage() {
		return successor;
	}

	@Override
	public boolean insert(Integer key, String value) {

		Integer index = 0;

		while (index <= getSize()-1) {
			if (keys[index] == key) {
				return false;
			}
			else if (keys[index] == null){
				keys[index] = key;
				values[index] = value;
				return true;
			}
			index++;
		}

		if (hasOverflow()) {
			return getOverflowPage().insert(key, value);
		}

		successor = new IsamDataNode(getSize());

		return successor.insert(key, value);
	}

	@Override
	public String toString() {
		String data = "[ ";

		for(int i = 0; i < getSize(); ++i) {
			Integer key = keys[i];

			if(key == null) {
				data += "E ";
			} else {
				data += keys[i] + " ";
			}
		}

		if(hasOverflow()) {
			return data + successor.toString() + " ]";
		} else {
			return data + "]";
		}
	}

	@Override
	public boolean delete(Integer key) {
		/*
		 * TODO: delete a value from this node (or one of its successors)
		 */

		return false;
	}
}
