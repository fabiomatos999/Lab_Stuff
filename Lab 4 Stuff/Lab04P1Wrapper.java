import java.util.Iterator;

public class Lab04P1Wrapper {

	public static void main(String[] args) {
		Lab04P1Wrapper.List<String> l = new Lab04P1Wrapper.ArrayList<>();

		l.add("Fer");
		l.add("Bob");
		l.add("Bob");
		l.add("Fabio");
		l.contains("Bob");
		l.firstIndex("Fabio");
		l.clear();
		l.isEmpty();
	}


	public static interface List<E>{

		/*METHODS SPECIFIED IN SECTION 2.4*/
		public void add(E elm);
		public void add(E elm, int index);
		public boolean remove(int index);
		public E get(int index);
		public E set(E elm, int index);
		public int size();


		/*METHODS TODO FOR THIS EXERCISE*/
		public boolean remove(E elm);
		public int removeAll(E elm);
		public void clear();
		public boolean contains(E elm);
		public E first();
		public E last();
		public int firstIndex(E elm);
		public int lastIndex(E elm);
		public boolean isEmpty();



	}

	public static class ArrayList<E> implements List<E>{
		
/////////////////*DO NOT ALTER THIS CODE//////////////////////////////////////
		private static final int DEFAULT_CAPACITY = 25;
		private E[] elements;
		private int currentSize;


		@SuppressWarnings("unchecked")
		public ArrayList(int initialCapacity) {
			if(initialCapacity < 1) throw new IllegalArgumentException("Size must be at least 1");

			elements = (E[]) new Object[initialCapacity];
			currentSize = 0;
		}

		public ArrayList() {this(DEFAULT_CAPACITY);} //Delegate to other constructor to init with default capacity of 25

/////////////////*DO NOT ALTER THIS CODE//////////////////////////////////////
		@SuppressWarnings("unchecked")
		private void reallocate() {
			E[] newElms = (E[]) new Object[this.currentSize * 2];
			for (int i = 0; i < elements.length; i++) 
				newElms[i] = elements[i];

			elements = newElms;
		}

		@Override
		public void add(E elm) {
			if(currentSize == elements.length) 
				reallocate();
			elements[currentSize] = elm;
			currentSize++;
		}

		@Override
		public void add(E elm, int index) {
			if(index < 1 || index >= currentSize) 
				throw new ArrayIndexOutOfBoundsException("Index must be between 0 and size() - 1");
			if(currentSize == elements.length) reallocate();

			// Shift data in elements from index to size ‐ 1
			for (int i = size(); i > index; i--)
				elements[i] = elements[i-1];

			// Insert the new item.
			elements[index] = elm;
			currentSize++;

		}

		@Override
		public boolean remove(int index) {
			if (index >= 0 && index < this.currentSize) {
				// move everybody one spot to the front
				for (int i = index; i < this.currentSize - 1; i++)
					this.elements[i] = this.elements[i + 1];
				this.elements[--this.currentSize] = null;
				return true;
			}
			else
				return false;
		}


		@Override
		public E get(int index) {
			if(index < 1 || index >= currentSize) 
				throw new ArrayIndexOutOfBoundsException("Index must be between 0 and size() - 1");
			return elements[index];
		}

		@Override
		public E set(E elm, int index) {
			if(index < 1 || index >= currentSize) 
				throw new ArrayIndexOutOfBoundsException("Index must be between 0 and size() - 1");

			E elmToReplace = elements[index];
			elements[index] = elm;
			return elmToReplace;
		}

		@Override
		public int size() {
			return currentSize;
		}
		
//////////*TODO ADD YOUR CODE HERE TO EXTEND IMPLEMENTATION*//////////////////////

		/**
		 * Method that removes the first copy of the given element on the list
		 * 
		 * @param elm - element to search for and remove
		 * @return true if element was found and removed, false otherwise
		 */
		@Override
		public boolean remove(E elm) {
			// TODO Auto-generated method stub
            for(int i=0; i < elements.length -1;i++)
			{
				if(elements[i] != null && elements[i].equals(elm))
				{
					for(int j = i; j < elements.length -1; j++)
					{
						elements[j] = elements[j+1];
					}
					return true;
				}
			}
			return false;
		}
		
		/**
		 * Method that removes all copies of a given element
		 * @param elm - Element to remove
		 * @return the number of copies it removed
		 */
		@Override
		public int removeAll(E elm) {
			// TODO Auto-generated method stub
			int copies = 0;
			for(int i = 0; i < size(); i++)
			{
				if(elements[i] != null && elements[i].equals(elm))
				{
					remove(elm);
					copies++;
					i--;
				}
			}
			return copies;
		}

		/**
		 * Method that empties the list and resets currentSize to 0
		 */
		@Override
		public void clear() {
			for(int i = 0; i < elements.length ; i++)
			{
				elements[i] = null;
			}
			currentSize = 0;
			// TODO Auto-generated method stub
		}

		/**
		 * Method that return true if and only if the given element is in the list
		 * This method CANNOT alter the contents of the list, nor move them out of order.
		 * 
		 * @param elm - Element to search for
		 * @return True if element is in the list, false otherwise
		 */
		@Override
		public boolean contains(E elm) {
			// TODO Auto-generated method stub
			for(int i = 0; i  < elements.length; i++)
			{
				if(elements[i] != null && elements[i].equals(elm))
				{
					return true;
				}
			}
			return false;
		}

		/**
		 * Method that return the first element in the list 
		 * @return First element in the list
		 */
		@Override
		public E first() {
			return elements[0];
		}

		/**
		 * Method that return the last element in the list 
		 * @return Last element in the list
		 */
		@Override
		public E last() {
			// TODO Auto-generated method stub
			return elements[elements.length -1];
		}

		/**
		 * Method that return the index of the first occurrence 
		 * of the element given as parameter in the list.
		 * 
		 *  If the element is not present, return -1.
		 *  
		 * @param elm - Element to search for
		 * @return  Index of the first occurrence of element in the list
		 */
		@Override
		public int firstIndex(E elm) {
			// TODO Auto-generated method stub
			for(int i = 0; i < size(); i++)
			{
				if(elements[i] != null && elements[i].equals(elm))
				{
					return i;
				}
			}
			return -1;
		}
		
		/**
		 * Method that return the index of the last occurrence 
		 * of the element given as parameter in the list.
		 * 
		 *  If the element is not present, return -1.
		 *  
		 * @param elm - Element to search for
		 * @return  Index of the last occurrence of element in the list
		 */

		@Override
		public int lastIndex(E elm) {
			// TODO Auto-generated method stub
			for(int i =elements.length -1; i > 0; i--)
			{
				if(elements[i] != null && elements[i].equals(elm))
				{
					return i;
				}
			}
			return -1;
		}

		/**
		 * Method that returns true if there are no elements added in the list
		 * @return True if list is empty
		 */
		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			if(currentSize > 0)
			{
				for(int i = 0; i < elements.length; i++)
				{
					if(elements[i] != null)
					{
						return false;
					}
				}
			}
			else if(currentSize == 0)
			{
				return true;
			}
			return false;
		}
	}
}
