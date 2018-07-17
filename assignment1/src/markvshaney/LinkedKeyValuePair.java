package markvshaney;

public class LinkedKeyValuePair {
	
	private String prefix;
	private LinkedListNode<String> follower;
	private LinkedKeyValuePair nextKeyWithSameHash;
	
	public LinkedKeyValuePair(String prefix) {
		this.prefix = prefix;
	}
	
	public LinkedKeyValuePair get(String prefixCheck){
		if(prefix.equals(prefixCheck)) return this;
		if(nextKeyWithSameHash == null) return null;
		return nextKeyWithSameHash.get(prefixCheck);
	}

	public void addFollower(String followerText){
		if(follower == null){
			follower = new LinkedListNode<String>(followerText);
		}
		follower.add(followerText);
	}
	
	public LinkedKeyValuePair addNextKeyWithSameHash(String prefixCheck){
		if(prefix.equals(prefixCheck)) return this;
		else if(nextKeyWithSameHash == null){
			nextKeyWithSameHash = new LinkedKeyValuePair(prefixCheck);
			return nextKeyWithSameHash;
		}
		return nextKeyWithSameHash.addNextKeyWithSameHash(prefixCheck);
	}
	
	public String getPrefix(){
		return prefix;
	}
	
	public LinkedListNode<String> getFollower(){
		return follower;
	}
	
	public int getNumFollowers(){
		return follower.getLength();
	}
}
