package fullyassociativecache;

public class CacheNode {
	
	private int tag;
	private FullyAssociativeCache cache;
	private CacheNode nextNode;
	private CacheNode previousNode;
	
	public CacheNode(int tag, FullyAssociativeCache cache){
		this.tag = tag;
		this.cache = cache;
	}
	
	public void addFollower(int tag){
		if(nextNode != null){
			nextNode.addFollower(tag);
		}
		else{
			CacheNode newNode = new CacheNode(tag, cache);
			nextNode = newNode;
			newNode.setPreviousNode(this);
		}
	}
	
	public boolean searchCache(int tag){
		if(this.tag == tag){
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
			if(nextNode != null){
				return nextNode.searchCache(tag);
			}
			else{
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
