package assignment2;

public class CacheNode {
	
	private int tag;
	private FullyAssociativeCache cache;
	private CacheNode nextNode;
	private CacheNode previousNode;
	
	public CacheNode(int tag, FullyAssociativeCache cache){
		this.tag = tag;
		this.cache = cache;
	}
	
	//for adding cache entries before the cache is full
	public void addFollower(int tag){
		//if it isn't the end of list
		if(nextNode != null){
			nextNode.addFollower(tag);
		}
		else{
			//create a new node and add it to end of list
			CacheNode newNode = new CacheNode(tag, cache);
			nextNode = newNode;
			newNode.setPreviousNode(this);
		}
	}
	
	//search linked-list for a particular tag
	public boolean searchCache(int tag){
		//if the tag equals the searched tag, return this object
		if(this.tag == tag){
			//if its not the first in the linked list
			if(previousNode != null){
				if(nextNode != null){
					previousNode.setNextNode(nextNode);
					nextNode.setPreviousNode(previousNode);
					setPreviousNode(null);
					setNextNode(cache.getMostRecentNode());
					cache.getMostRecentNode().setPreviousNode(this);
					cache.setMostRecentNode(this);
				}
				else{
					previousNode.setNextNode(null);
					setPreviousNode(null);
					setNextNode(cache.getMostRecentNode());
					cache.getMostRecentNode().setPreviousNode(this);
					cache.setMostRecentNode(this);
				}
			}
			return true;

		}
		else{
			//while there is another linked object 
			if(nextNode != null){
				//carry out searchCache on this object
				return nextNode.searchCache(tag);
			}
			//if it reaches the end of the list without a hit
			else{
				//replaces the last in list with search tag and moves it to top of list
				this.tag = tag;
				previousNode.setNextNode(null);
				setPreviousNode(null);
				setNextNode(cache.getMostRecentNode());
				cache.getMostRecentNode().setPreviousNode(this);
				cache.setMostRecentNode(this);
				return false;
			}
		}
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public CacheNode getNextNode() {
		return nextNode;
	}

	public void setNextNode(CacheNode nextNode) {
		this.nextNode = nextNode;
	}

	public CacheNode getPreviousNode() {
		return previousNode;
	}

	public void setPreviousNode(CacheNode previousNode) {
		this.previousNode = previousNode;
	}

}
