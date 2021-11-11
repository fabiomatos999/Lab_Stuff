import java.util.ArrayList;
public class Lab09P1Wrapper {
    public static void main(String[] args) {
        Lab09P1Wrapper.BinaryTreeNode<String> node = new Lab09P1Wrapper.BinaryTreeNode<String>("Lee");
        Lab09P1Wrapper.BinaryTree<String> uwu = new Lab09P1Wrapper.BinaryTree<String>(node);
        Lab09P1Wrapper.BinaryTreeNode<String> curr = node;
		node = new Lab09P1Wrapper.BinaryTreeNode<String>("Joe");
		curr.setLeftChild(node);
		curr = node;
		node = new Lab09P1Wrapper.BinaryTreeNode<String>("Apu");
		curr.setLeftChild(node);
		curr = node;
		node = new Lab09P1Wrapper.BinaryTreeNode<String>("Cal");
		curr.setRightChild(node);
		curr = node;
		node = new Lab09P1Wrapper.BinaryTreeNode<String>("Ken");
		curr.setRightChild(node);
		node = new Lab09P1Wrapper.BinaryTreeNode<String>("Ron");
		curr = uwu.root;
		curr.setRightChild(node);
		curr = node;
		node = new Lab09P1Wrapper.BinaryTreeNode<String>("Ned");
		curr.setLeftChild(node);
		node = new Lab09P1Wrapper.BinaryTreeNode<String>("Zoe");
		curr.setRightChild(node);
		uwu.getLevelCount(3);
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
		public int getLevelCount(int level);
	}	

	public static class BinaryTree<E> implements Tree<E> {
		
		private BinaryTreeNode<E> root;
		private ArrayList<Integer> ret = new ArrayList<Integer>();
		

		
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
		public int getLevelCount(int level) {
			/*ADD YOUR CODE HERE*/
            if(level <= 0)
			{
     	    	throw new IllegalArgumentException("Level Cannot be less than or equal to zero");
			}
			getRecLevelCount(this.root, level, 1);
			return ret.size();

		}
		
		/*RECURSIVE AUXILIARY METHOD*/
		private void getRecLevelCount(BinaryTreeNode<E> root, int level,int currLevel) {
			if(root == null)
				return;
			if(root.getLeftChild() != null)
			{
				getRecLevelCount(root.getLeftChild(), level, currLevel+1);
			}
			if(currLevel == level)
			{
				ret.add(currLevel);
			}
			if(root.getRightChild() != null)
			{
				getRecLevelCount(root.getRightChild(), level, currLevel+ 1);
			}
		}
	}

}