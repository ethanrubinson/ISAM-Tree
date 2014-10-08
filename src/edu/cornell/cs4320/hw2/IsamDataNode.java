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
	
	private boolean isEmpty(){
		for(int key_index = 0; key_index < getSize(); key_index++)
			if (keys[key_index] != null)
				return false;
		
		return true;
	}
	
	private int doDelete(Integer key, int depth){
		
		for(int key_index = 0; key_index < getSize(); key_index++){
			if (keys[key_index] != null && keys[key_index].intValue() == key.intValue()) {
				keys[key_index] = null;
				values[key_index] = null;
				return depth;
			}
		}
		
		return getOverflowPage().doDelete(key, depth + 1);
		
	}
	
	@Override
	public boolean delete(Integer key) {
		if(search(key) == null)
			return false;
		
		int depth = doDelete(key, 0);
		
		// If there are no overflow pages then no merge is possible
		if(!hasOverflow())
			return true;
		
		// Get a reference to the page where the element was removed
		IsamDataNode nodeWithSpace = this;
		for(int iter = 0; iter < depth; iter++){
			nodeWithSpace = nodeWithSpace.getOverflowPage();
		}
		
		// Count the total number of overflow pages and a reference to the last page
		int numOverflowPages = 0;
		IsamDataNode lastNode = this;
		IsamDataNode prevLastNode = this;
		while(lastNode.hasOverflow()){
			prevLastNode = lastNode;
			lastNode = lastNode.getOverflowPage();
			numOverflowPages++;
		}
		
		
		// CASE 1: The element was removed from the last overflow page
		if(numOverflowPages == depth) {
			// If the overflow page is now empty, remove it
			if(nodeWithSpace.isEmpty()) {
				lastNode = null;
				prevLastNode.successor = null;
			}
			
			return true;
		}
		
		// CASE 2: The element was removed from the first page or a middle page
		Integer movedKey;
		String movedValue;
		
		// Find the last data object on the last page to move and delete it
		for(int key_index = getSize() - 1; key_index >= 0; key_index--){
			if (lastNode.keys[key_index] != null) {
				movedKey = lastNode.keys[key_index];
				lastNode.keys[key_index] = null;
				
				movedValue = lastNode.values[key_index];
				lastNode.values[key_index] = null;
				
				// If the last page is now empty, delete it
				if(lastNode.isEmpty()){
					prevLastNode.successor = null;
				}
				
				// Re insert the moved value
				nodeWithSpace.doInsert(movedKey, movedValue);
				break;
			}
		}
		
		return true;
	}
}
