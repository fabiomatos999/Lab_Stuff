import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class Lab05P2Wrapper {
    public static void main(String[] args) {
        Lab05P2Wrapper.List<String> uwu = new Lab05P2Wrapper.SinglyLinkedList<String>();
        uwu.add("Bob");
        uwu.add("Sherry");
        uwu.add("Herald");
        uwu.add("Timmy");
        uwu.add("Jerry");
		uwu.changeOddEven();
    }
	public static interface List<E> extends Iterable<E> {
		
		public int size();
		
		public boolean isEmpty();
		
		public boolean isMember(E e);
		
		public int firstIndexOf(E e);
		
		public int lastIndexOf(E e);

		public void add(E e);
		
		public void add(E e, int index);
		
		public E get(int index);
		
		public E remove(int index);
		
	    public boolean remove(E e);
	    
	    public int removeAll(E e);
	    
		public E set(int index, E newElement);
		
		public void clear();
				
		public void changeOddEven();

	}
	
	public static class SinglyLinkedList<E> implements List<E> {

		@SuppressWarnings("hiding")
		private class SinglyLinkedListIterator<E> implements Iterator<E>{
			private Node<E> nextNode;
			
			

			@SuppressWarnings("unchecked")
			public SinglyLinkedListIterator() {
				this.nextNode = (Node<E>) header.getNext();
			}
			@Override
			public boolean hasNext() {
				return nextNode != null;
			}

			@Override
			public E next() {
				if (this.hasNext()) {
					E result = this.nextNode.getElement();
					this.nextNode = this.nextNode.getNext();
					return result;
				}
				else {
					throw new NoSuchElementException();
				}
			}
			
		}
		
		private static class Node<E> {
			private E element;
			private Node<E> next;
			
			public Node(E element, Node<E> next) {
				super();
				this.element = element;
				this.next = next;
			}
			public Node() {
				super();
			}
			
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

			
		}

		private Node<E> header;
		private int currentSize;
		
		public SinglyLinkedList() {
			this.header = new Node<>();
			this.currentSize = 0;
		}
		
		
		@Override
		public int size() {
			return this.currentSize;
		}

		@Override
		public boolean isEmpty() {
			return this.size() == 0;
		}

		@Override
		public boolean isMember(E e) {
			return this.firstIndexOf(e) >= 0;
		}

		@Override
		public int firstIndexOf(E e) {
			int i = 0;
			for (Node<E> temp = this.header.getNext(); temp != null; 
					temp = temp.getNext(), ++i) {
				if (temp.getElement().equals(e)) {
					return i;
				}
			}
			// not found
			return -1;
		}

		@Override
		public void add(E e) {
			if (this.isEmpty()) {
				this.header.setNext(new Node<E>(e, null));
				this.currentSize++;
			}
			else {
				Node<E>temp= this.header.getNext();
				while (temp.getNext() != null) {
					temp = temp.getNext();
				}
				Node<E> newNode = new Node<>(e, null);
				temp.setNext(newNode);
				this.currentSize++;
			}
			
		}

		@Override
		public void add(E e, int index) {
			if ((index < 0) || (index > this.currentSize)) {
				throw new IndexOutOfBoundsException();
			}
			if (index == this.currentSize) {
				this.add(e);
			}
			else {
				Node<E> temp = null;
				if (index == 0) {
					temp = this.header;
				}
				else {
					temp = this.getPosition(index -1);
				}
				Node<E> newNode = new Node<>();
				newNode.setElement(e);
				newNode.setNext(temp.getNext());			
				temp.setNext(newNode);
				this.currentSize++;
			}
		}

		@Override
		public E get(int position) {
			if ((position < 0) || position >= this.currentSize) {
				throw new IndexOutOfBoundsException();
			}
			
			Node<E> temp  = this.getPosition(position);
			return temp.getElement();
			
		}

		private Node<E> getPosition(int index){
			int currentPosition=0;
			Node<E> temp = this.header.getNext();
			
			while(currentPosition != index) {
				temp = temp.getNext();
				currentPosition++;
			}
			return temp;

		}
		
		@Override
		public E remove(int index) {
			if ((index < 0) || (index >= this.currentSize)){
				throw new IndexOutOfBoundsException();
			}
			else {
				Node<E> temp = this.header;
				int currentPosition =0;
				Node<E> target = null;
				
				while (currentPosition != index) {
					temp = temp.getNext();
					currentPosition++;
				}
				E result = null;
				target = temp.getNext();
				result = target.getElement();
				temp.setNext(target.getNext());
				target.setElement(null);
				target.setNext(null);
				this.currentSize--;
				return result;
				
			}
		}

		@Override
		public E set(int position, E newElement) {
			if ((position < 0) || position >= this.currentSize) {
				throw new IndexOutOfBoundsException();
			}
			Node<E> temp  = this.getPosition(position);
			E result = temp.getElement();
			temp.setElement(newElement);
			return result;
			
		}

		@Override
		public void clear() {
			while(!this.isEmpty()) {
				this.remove(0);
			}
		}

		@Override
		public Iterator<E> iterator() {
			return new SinglyLinkedListIterator<E>();
		}


		@Override
		public int lastIndexOf(E e) {
			int i = 0, result = -1;
			for (Node<E> temp = this.header.getNext(); temp != null; 
					temp = temp.getNext(), ++i) {
				if (temp.getElement().equals(e)) {
					result = i;
				}
			}
			// not found
			return result;
		}


		@Override
		public boolean remove(E e) {
			int i = this.firstIndexOf(e);
			if (i < 0) {
				return false;
			}else {
				this.remove(i);
				return true;
			}
		}


		@Override
		public int removeAll(E e) {
			int count = 0;
			while (this.remove(e)) {
				count++;
			}
			return count;
		}


		/*
		 * Write a member method changeOddEven which takes all elements in odd positions 
		 * in the this list and moves them after all the even elements in the list. 
		 * The relative order between the odd elements is maintained. The same applies to
		 * the even elements. Remember that the first position is 0 which is considered even.
		 * For example, if L = {Bob, Ned, Jil, Amy, Kim} then L.changeOddEven() turns L 
		 * into L = {Bob, Jil, Kim, Ned, Amy}.
		 */

		@Override
		public void changeOddEven() {
			// ADD YOU CODE HERE
			Node<E> temp = header.next;
			ArrayList<E> testEven = new ArrayList<E>();
			ArrayList<E> testOdd = new ArrayList<E>();
			int testEvenIndex = 0;
			int testOddIndex = 0;
			int currIndex = 0;
			while(currIndex < currentSize)
			{
				if(currIndex%2 == 0)
				{
					testEven.add(temp.element);
					currIndex++;
					temp = temp.getNext();
				}
				else
				{
					testOdd.add(temp.element);
					currIndex++;
					temp = temp.getNext();
				}
			}
			currIndex = 0;
			temp = header.getNext();
			while(currIndex < testEven.size())
			{
				temp.setElement(testEven.get(testEvenIndex));
				temp = temp.getNext();
				testEvenIndex++;
				currIndex++;
			}
			currIndex = 0;
			while(currIndex < testOdd.size())
			{
				temp.setElement(testOdd.get(testOddIndex));
				temp = temp.getNext();
				testOddIndex++;
				currIndex++;
			}

		
		}

	}
}