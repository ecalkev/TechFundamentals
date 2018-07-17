package markvshaney;

public class LinkedHashMap {
	
	private static int LARGE_PRIME_NUMBER = 10000651;
	private LinkedKeyValuePair[] prefixes = new LinkedKeyValuePair[LARGE_PRIME_NUMBER];
    
	/*
	 * Returns the KeyValuePair with a matching prefix or null if there is no match.
	 */
	public LinkedKeyValuePair get(String prefix){
		int hash = hashCode(prefix);
		if(prefixes[hash] != null) return prefixes[hash].get(prefix);
		return null;
	}
	
	/*
	 * Returns the KeyValuePair with a matching hashcode or null if there is no match.
	 */
	public LinkedKeyValuePair get(int hashcode){
		return prefixes[hashcode];
	}
	
	/*
	 * Puts the key into the HashMap and returns the entered LinkedKeyValuePair object. If there already exists a LinkedKeyValuePair with @prefix, returns this object.
	 */
	public LinkedKeyValuePair putKey(String prefix){
		int hash = hashCode(prefix);
		if(prefixes[hash] == null){
			prefixes[hash] = new LinkedKeyValuePair(prefix);
			return prefixes[hash];
		}
		return prefixes[hash].addNextKeyWithSameHash(prefix);
	}
	
	/*
	 * Returns the length of the array holding the hashmap.
	 */
	public int getLength(){
		return prefixes.length;
	}
	
	//creates a hashCode within the bounds of the array boundaries
	private int hashCode(String prefix){
		int hash = prefix.hashCode();
		hash = hash % prefixes.length;
		hash = Math.abs(hash);
		return hash;
	}
}
