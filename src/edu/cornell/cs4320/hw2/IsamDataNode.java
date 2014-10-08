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
		for (int key_index = 0; key_index < getSize(); key_index++) {
			if (keys[key_index] != null && keys[key_index].intValue() == key.intValue())
				return values[key_index];
		}
		
		if (hasOverflow())
			return getOverflowPage().search(key);
		else
			return null;
	}

	public boolean hasOverflow() {
		return successor != null;
	}

	public IsamDataNode getOverflowPage() {
		return successor;
	}

	
	private void doInsert(Integer key, String value){
		for(int key_index = 0; key_index < getSize(); key_index++){
			if (keys[key_index] == null){
				keys[key_index] = key;
				values[key_index] = value;
				return;
			}
		}

		if (hasOverflow()) {
			getOverflowPage().doInsert(key, value);
		}
		else{
			successor = new IsamDataNode(getSize());
			successor.doInsert(key, value);
		}
	}
	
	@Override	
	public boolean insert(Integer key, String value) {
		if(search(key) != null)
			return false;
		
		doInsert(key, value);
		return true;
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
