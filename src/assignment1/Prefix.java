package assignment1;

import java.util.ArrayList;

public class Prefix {
	
	private String prefix;
	//not sure if he wants an array or something different..
	private ArrayList<String> followers = new ArrayList();
	//forms a linked list of prefixes with same hashCode
	private Prefix sameHash = null;
	
	public Prefix(String string){
		this.prefix = string;
	}
	
	//allows for adding a follower to the list of followers
	//this should prob be a hashMap too
	public void addFollower(String follower){
		int count = 0;
		for(String f : followers){
			if(f.equals(follower)){
				break;
			}
			count ++;
		}
		if(count == followers.size()){
			followers.add(follower);
		}
	}
	
	//search linked-list for a particular prefix
	public Prefix searchPrefix(String word){
		//if the prefix equals searchword, return this object
		if(prefix.equals(word)){
			return this;
		}
		else{
			//while there is a linked object with same hash
			if(sameHash != null){
				//carry out searchPrefix on this object
				return sameHash.searchPrefix(word);
			}
			else{
				//create a new object with a prefix of the searchword
				Prefix newPrefix = new Prefix(word);
				//make sameHash of the last in linked-list the new prefix (ie extending the linked-list)
				sameHash = newPrefix;
				return newPrefix;
			}
		}
	}

	//getters and setters
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public ArrayList<String> getFollowers() {
		return followers;
	}

	public void setFollowers(ArrayList<String> followers) {
		this.followers = followers;
	}

	public Prefix getSameHash() {
		return sameHash;
	}

	public void setSameHash(Prefix sameHash) {
		this.sameHash = sameHash;
	}
	
	

}
