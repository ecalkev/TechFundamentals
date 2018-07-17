package markvshaney;

public class LinkedListNode<T> {
	
	private T data;
	private LinkedListNode<T> next;
	private int length;
	
	public LinkedListNode(T data){
		this.data = data;
		length = 1;
	}
	
	public LinkedListNode<T> add(T t){
		LinkedListNode<T> temp = this;
		while(temp.next != null){
			if(temp.data.equals(t)) return temp;
			temp = temp.next;
		}
		if(temp.data.equals(t)) return temp;
		temp.next = new LinkedListNode<T>(t);
		length++;
		return temp.next;
	}
	
	public T get(int index){
		LinkedListNode<T> temp = this;
		for(int i = 0; i < index; i++){
			temp = temp.next;
			if(temp == null) return null;
		}
		return temp.data;
	}
	
	public int getLength(){
		return length;
	}

}
