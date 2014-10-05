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
		/*
		 * TODO: Search for an entry with the specified key
		 */
		
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
		/*
		 * TODO: insert an new value into this node
		 */
		
		return false;
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
