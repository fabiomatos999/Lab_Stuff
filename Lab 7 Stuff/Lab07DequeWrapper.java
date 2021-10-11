public class Lab07DequeWrapper {
	
	public static void main(String[] args) {
		//FOR TESTING PURPOSES
		
		String[] names = {"Ned", "Joe", "Apu", "Jil"};
		String[] names1 = {"Joe", "Apu"};
		String[] names2 = {"Ned", "Ned", "Joe", "Apu", "Jil", "Jil"};
		
		Deque<String> q = new CircularDoublyLinkedQueque<>();
		q.addFirst("Jil");	
		q.addFirst("Apu");	
		q.addFirst("Joe");	
		q.addFirst("Ned");	
		q.removeLastOccurrence("UwU");
		q.removeFirst();
		q.removeFirst();
		q.removeFirst();
		q.removeFirst();
		boolean is = q.isEmpty();
		//Use the toArray() method to compare your solutions with the String[] provided
	}
	
	public static interface Deque<E> {
		public void addFirst(E elm);
		public void addLast(E elm);
		public E removeFirst();
		public E removeLast();
		public E getFirst();
		public E getLast();
		public boolean removeFirstOccurrence(E elm);
		public boolean removeLastOccurrence(E elm);
		public int size();
		public boolean isEmpty();
		
		//DO NOT USE THIS IN EXERCISES
		public String[] toArray(); //DO NOT REMOVE, TEST WILL FAIL
	}

	/**
	 * Implementation of Deque ADT using a Circular Doubly-Linked Queue
	 * @author Fernando J. Bermudez & Juan O. Lopez Gerena
	 * @param <E>
	 */
	public static class CircularDoublyLinkedQueque<E> implements Deque<E>{

		private class Node<E>{
			private E element;
			private Node<E> next, prev;

			public Node(E elm, Node<E> next, Node<E> prev) {
				this.element = elm;
				this.next = next;
				this.prev = prev;
			}
			public Node(E elm, Node<E> next) {this(elm, next, null);}

			public Node(E elm) {this(elm, null, null);}

			public Node() {this(null, null, null);}
			
			public E getElement() {
				return element;
			}
			public void setElement(E element) {
				this.element = element;
			}
			public Node<E> getNext() {
				return next;
			}
			public void setNext(Node<E> next) {
				this.next = next;
			}
			public Node<E> getPrev() {
				return prev;
			}
			public void setPrev(Node<E> prev) {
				this.prev = prev;
			}
			public void clear() {
				next = null;
				prev = null;
				element = null;
			}
		} //End of Node Class
		
		

		private Node<E> header; 
		private int currentSize;

		public CircularDoublyLinkedQueque() {
			header = new Node<>(null, header, header);
			currentSize = 0;
		}

		@Override
		public void addFirst(E elm) {
			/*ADD YOUR CODE HERE*/
			currentSize++;
			if(header.getNext() != null)
			{
				Node<E> temp = header.getNext();
				Node<E> add = new Node<E>(elm,temp,header);
				temp.setPrev(add);
				header.setNext(add);
			}
			else
			{
				Node<E> add = new Node<E>(elm,header,header);
				header.setNext(add);
				header.setPrev(add);
			}
		}

		@Override
		public void addLast(E elm) {
			/*ADD YOUR CODE HERE*/
			currentSize++;
			if(header.getPrev() != null)
			{
				Node<E> prev = header.getPrev();
				Node<E> add = new Node<E>(elm,header,prev); 
				prev.setNext(add);
				header.setPrev(add);
			}
			else
			{
				Node<E> add = new Node<E>(elm,header,header);
				header.setNext(add);
				header.setPrev(add);
			}
		}

		@Override
		public E removeFirst() {	
			/*ADD YOUR CODE HERE*/
			if(header.getNext() != null)
			{
				currentSize--;
				Node<E> rm = header.getNext();
				rm.getNext().setPrev(header);
				header.setNext(rm.getNext());
				E rmcpy = rm.getElement();
				rm.clear();
				rm = null;
				return rmcpy;
			}
			else
			{
				return null;
			}
		}

		@Override
		public E removeLast() {
			/*ADD YOUR CODE HERE*/
			if(header.getPrev() != null)
			{
				Node<E> rm = header.getPrev();
				rm.getPrev().setNext(header);
				header.setPrev(rm.getPrev());
				E rmElm = rm.getElement();
				rm.clear();
				rm = null;
				currentSize--;
				return rmElm;
			}
			return null;
		}

		@Override
		public E getFirst() {
			/*ADD YOUR CODE HERE*/
			return header.getNext().getElement();
		}

		@Override
		public E getLast() {
			/*ADD YOUR CODE HERE*/
			return header.getPrev().getElement();
		}

		@Override
		public boolean removeFirstOccurrence(E elm) {
			/*ADD YOUR CODE HERE*/
			Node<E> temp = header.getNext();
			while(temp != header)
			{
				if(temp.getElement() == elm)
				{
					Node <E> prev = temp.getPrev();
					Node<E> next = temp.getNext();
					prev.setNext(next);
					next.setPrev(prev);
					currentSize--;
					temp.clear();
					temp = null;
					return true;
				}
				else
				{
					temp = temp.getNext();
				}
			}
			return false;
		}

		@Override
		public boolean removeLastOccurrence(E elm) {
			/*ADD YOUR CODE HERE*/
			Node<E> temp = header.getPrev();
			while(temp != header)	
			{
				if(temp.getElement() == elm)
				{
					Node <E> prev = temp.getPrev();
					Node<E> next = temp.getNext();
					prev.setNext(next);
					next.setPrev(prev);
					currentSize--;
					temp.clear();
					temp = null;
					return true;
				}
				else
				{
					temp = temp.getPrev();
				}
			}
			return false;
		}

		@Override
		public int size() {
			/*ADD YOUR CODE HERE*/
			return currentSize;
		}

		@Override
		public boolean isEmpty() {
			/*ADD YOUR CODE HERE*/
			if(currentSize == 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		//DO NOT USE THIS IN EXERCISES
		//DO NOT DELETE, TESTS WILL FAILS
		@Override
		public String[] toArray() {
			String[] arr = new String[size()];

			Node<E> curNode = header.getNext();
			for (int i = 0; curNode != header; curNode = curNode.getNext(), i++) {
				arr[i] = (String) curNode.getElement();
			}
			return arr;
		}

	}
}
