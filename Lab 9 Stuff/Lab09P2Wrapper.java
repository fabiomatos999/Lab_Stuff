import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.lang.model.util.ElementScanner6;

public class Lab09P2Wrapper {
	
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

	}
	
	public static interface TreeNode<E> {
		public E getValue();
	}

	public static class BinaryTreeNode<E> implements TreeNode<E> {

		private E value;
		private BinaryTreeNode<E> leftChild;
		private BinaryTreeNode<E> rightChild;
		private BinaryTreeNode<E> parent;

		
		public BinaryTreeNode(E value) {
			super();
			this.value = value;
			this.leftChild = null;
			this.rightChild = null;
			this.parent = null;

		}

		
		public BinaryTreeNode(E value, BinaryTreeNode<E> parent, BinaryTreeNode<E> leftChild, BinaryTreeNode<E> rightChild) {
			super();
			this.value = value;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
			this.parent = parent;
		}

		@Override
		public E getValue() {
			return this.value;

		}


		public BinaryTreeNode<E> getLeftChild() {
			return leftChild;
		}


		public void setLeftChild(BinaryTreeNode<E> leftChild) {
			this.leftChild = leftChild;
		}


		public BinaryTreeNode<E> getRightChild() {
			return rightChild;
		}


		public void setRightChild(BinaryTreeNode<E> rightChild) {
			this.rightChild = rightChild;
		}


		public void setValue(E value) {
			this.value = value;
		}


		public BinaryTreeNode<E> getParent() {
			return parent;
		}


		public void setParent(BinaryTreeNode<E> parent) {
			this.parent = parent;
		}

	}

	public static interface Tree<E> {
		public TreeNode<E> root();
		public TreeNode<E> left(TreeNode<E> p);
		public TreeNode<E> right(TreeNode<E> p);
		public TreeNode<E> sibling(TreeNode<E> p); 
		public boolean isEmpty();
		public int size();
		public List<E> getLevelElements(int level);
	}	

	public static class BinaryTree<E> implements Tree<E> {
		
		private BinaryTreeNode<E> root;
        public List<E> ret = new SinglyLinkedList<E>(); 

		
		public BinaryTree(BinaryTreeNode<E> root) {
			super();
			this.root = root;
		}
		
		public BinaryTree(BinaryTreeNode<E> root, BinaryTree<E> T1, BinaryTree<E> T2) {
			super();
			this.root = root;
			if (T1 != null) {
				BinaryTreeNode<E> temp = (BinaryTreeNode<E>)T1.root();
				this.root.setLeftChild(temp);
				temp.setParent(this.root);
				
			}
			if (T2 != null) {
				BinaryTreeNode<E> temp = (BinaryTreeNode<E>)T2.root();

				this.root.setRightChild(temp);
				temp.setParent(this.root);

			}

		}



		@Override
		public TreeNode<E> root() {
			return this.root;
		}


		private void check(TreeNode<E> p) {
			if (p==null) {
				throw new IllegalArgumentException();
			}
		}
		@Override
		public TreeNode<E> left(TreeNode<E> p) {
			this.check(p);
			BinaryTreeNode<E> temp = (BinaryTreeNode<E>)p;
			return temp.getLeftChild();
		}


		@Override
		public TreeNode<E> right(TreeNode<E> p) {
			this.check(p);
			BinaryTreeNode<E> temp = (BinaryTreeNode<E>)p;
			return temp.getRightChild();

		}

		@Override
		public TreeNode<E> sibling(TreeNode<E> p) {
			this.check(p);
			BinaryTreeNode<E> temp = (BinaryTreeNode<E>)p;
			if (temp.getParent().getLeftChild() != temp) {
				return temp.getRightChild();
			}
			else {
				return temp.getLeftChild();
			}

		}
		
		@Override
		public boolean isEmpty() {
			return this.size() == 0;
		}

		@Override
		public int size() {
			// ADD YOUR CODE HERE
			return this.sizeAux(this.root);

			//return 0;
		}

		private int sizeAux(BinaryTreeNode<E> N) {
			if (N == null) {
				return 0;
			}
			else {
				return 1 + this.sizeAux(N.getLeftChild()) + this.sizeAux(N.getRightChild());
			}
			
		}

		
		public void print() {
			this.printAux(this.root, 0);
		}

		private void printAux(BinaryTreeNode<E> N, int i) {
			if (N != null) {
				this.printAux(N.getRightChild(), i + 4);
				for (int j=0; j < i; ++j) {
					System.out.print(" ");
				}
				System.out.println(N.getValue());
				this.printAux(N.getLeftChild(), i + 4);
			}
			
		}
		
		private BinaryTreeNode<E> find(E e, BinaryTreeNode<E> N) {
			if (N == null) {
				return null;
			}
			else if (N.getValue().equals(e)) {
				return N;
			}
			else {
				BinaryTreeNode<E> temp = find(e, N.getLeftChild());
				if (temp != null) {
					return temp;
				}
				else {
					return find(e, N.getRightChild());
				}
	 		}
		}
		
		
		@Override
		public List<E> getLevelElements(int level) {
			/*ADD YOUR CODE HERE*/
            if(level <= 0)
			{
     	    	throw new IllegalArgumentException("Level Cannot be less than or equal to zero");
			}
            getRecLevelElements(root, level, 1);
            return ret;
		}
		
		/*RECURSIVE AUXILIARY METHOD*/
		private List<E> getRecLevelElements(BinaryTreeNode<E> root, int level, int currLevel) {
			/*ADD YOUR CODE HERE*/
			if(root == null)
            {
				return null;
            }
			if(root.getLeftChild() != null)
			{
				getRecLevelElements(root, level, currLevel);
			}
			if(currLevel == level)
			{
				ret.add(root.value);
			}
			if(root.getRightChild() != null)
			{
				getRecLevelElements(root, level, currLevel);
			}
        	return ret;
		}	

	}

}