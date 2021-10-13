public class Lab07Queue1Wrapper {
	public static void main(String[] args) {
		binaryNumberSequence(8);
	}
	
	public static class EmptyQueueException extends RuntimeException {
		public EmptyQueueException(String message) {
	        super(message);
	    }
	}
	
	public static interface Queue<E> {		  
		   int size();
		   boolean isEmpty();	   
		   E getFront() throws EmptyQueueException;
		   void enqueue(E element);
		   E dequeue() throws EmptyQueueException;
	}
	
	public static class DoublyLinkedQueue<E> implements Queue<E> {

		private static class Node<E> {    
			private E element; 
			private Node<E> next, prev; 


			public Node(E elm, Node<E> nextNode, Node<E> prevNode) {
				this.element = elm;
				this.next = nextNode;
				this.prev = prevNode;
			}
			
			public Node(E elm, Node<E> next) {
				this(elm, next, null);
			}

			public Node(E elm) {
				this(elm, null, null);
			}
			
			public Node() {
				this(null, null, null);
			}

			public E getElement() {
				return this.element;
			}

			public Node<E> getNext(){
				return this.next;
			}

			public void setNext(Node<E> next) {
				this.next = next;
			}
			
			public Node<E> getPrev(){
				return this.prev;
			}

			public void setPrev(Node<E> prev) {
				this.prev = prev;
			}

			public void setElement(E elm) {
				this.element = elm;
			}

			public void clear() {
				this.element = null;
				this.next = null;
				this.prev = null;
			}
		} // END CLASS NODE

		private Node<E> header, trailer;   // references to first and last node
		private int currentSize; 

		public DoublyLinkedQueue() {         // initializes instance as empty queue
			header = new Node<>(null, trailer, null);
			trailer = new Node<>(null, null, header);
			currentSize = 0; 
		}
		public int size() {
			return currentSize;
		}
		public boolean isEmpty() {
			return size() == 0;
		}

		public E getFront() throws EmptyQueueException {
			if (isEmpty()) 
				throw new EmptyQueueException("Queue is empty");
			return header.getNext().getElement(); 
		}

		public E dequeue() throws EmptyQueueException {
			if (this.isEmpty()) {
				return null;
			}
			else {
				Node<E> target = this.header.getNext();
				E result = getFront();
				
				header.setNext(target.getNext());
				target.getNext().setPrev(header);
				
				target.clear();
				target = null;
				
				this.currentSize--;
				return result;
			}
		}

		public void enqueue(E e) {
			Node<E> newNode = new Node<E>(e, trailer, trailer.getPrev());
			this.trailer.setPrev(newNode);
			newNode.getPrev().setNext(newNode);
			this.currentSize++;
		}
		
		public void clear() {
			while(!this.isEmpty())
				this.dequeue();
		}
	}
	
	public static Queue<String> binaryNumberSequence(int N){
		/*ADD YOUR CODE HERE*/
        DoublyLinkedQueue<String> binQueue = new DoublyLinkedQueue<String>();
        DoublyLinkedQueue<Integer> decQueue = new DoublyLinkedQueue<Integer>();
        String tempStr = "";
        for(int i=1; i <= N; i++)
        {
            decQueue.enqueue(i);
        }
        while(!decQueue.isEmpty())
        {
            int num = decQueue.dequeue();

            while(num > 0)
            {
                int mod = num%2;
                tempStr = String.valueOf(mod) + tempStr;
                num = num/2;
            }
            binQueue.enqueue(tempStr);
            tempStr = "";
        }

		return binQueue;
	}	
}