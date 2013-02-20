/***
 * File Name 				: 		BTree.java
 * Author Name 			: 		Aditya Shrotri
 * Date 						:		October 16,2012
 * Version					:		1.0
 * Purpose					:       The class implements functions for insert and search in the B tree. 
 * 									Order of B tree is calculated in the Constructor. The function Fix is
 * 									used for balancing the B tree. Split function is used for splitting the 
 * 									node after overflow. Insert is used to insert a node into B tree. For
 * 								    searching, there is a function called Find.  The class also has display
 * 									functions for inorder and level order Traversal of the B tree.
 */

package ufl.ads.datastructures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BTree {

	private BTreeNode root;// root of the BTree
	private int order; // order of BTree

	private ArrayList<BTreeSubNode> leftArray = new ArrayList<BTreeSubNode>(); // left
																				// subarray
																				// to
																				// store
																				// left
																				// subtree
																				// after
																				// splitting
	private ArrayList<BTreeSubNode> middleArray = new ArrayList<BTreeSubNode>(); // middle
																					// subarray
																					// to
																					// store
																					// middle
																					// subtree
																					// after
																					// splitting
	private ArrayList<BTreeSubNode> rightArray = new ArrayList<BTreeSubNode>(); // right
																				// subarray
																				// to
																				// store
																				// right
																				// subtree
																				// after
																				// splitting

	private boolean duplicateFound = false; // to check if duplicate element is
											// found in BTree.

	/*
	 * Constructor of BTree to assign order by user
	 */
	public BTree(int order) {
		this.order = order;
	}

	/*
	 * To get order of BTree
	 */
	public int getOrder() {
		return order;
	}

	/*
	 * To get root of BTree
	 */
	public BTreeNode getRoot() {
		return root;
	}

	/*
	 * Insert the key into BTree by finding the location and fixing or melding
	 * of subtrees if needed.
	 */
	public boolean insertIntoBTree(int key, int value) {

		boolean found = false; // made true when exact position of insertion of
								// element is found.
		boolean fixingDone = false; // made true when exact fixing after
									// spliting and melding of element is done.
		duplicateFound = false;
		boolean inserted = false; // returned true when duplicate element is
									// found.

		try {

			BTreeNode current;// dummy node to traverse down the BTree.

			if (root == null) // no node in root
			{
				// if root is null ,directly add into root
				root = new BTreeNode();
				BTreeSubNode btsubNode = new BTreeSubNode(key, value, null);
				root.BTreeArray.add(btsubNode);
			} else // root occupied
			{

				current = root; // start at root and walk down the tree

				// A S is a stack that stores BTreeNodes. It will be used
				// to store the search path

				Stack<BTreeNode> S = new Stack<BTreeNode>();

				// Loop to walk down the tree, until pathNode becomes null
				// The nodes visited in this walk are stored in Stack of
				// BTreeNodes.

				BTreeNode pathNode;
				pathNode = current;
				BTreeSubNode btsubNode;

				// position while loop
				while (!found) {
					S.push(pathNode);
					pathNode = findLocation(key, pathNode);

					if (duplicateFound) {
						inserted = false;
						return inserted;
					}

					if (pathNode == null) {
						found = true;
					}
				}// end while

				// fixing while loop
				while (!fixingDone) {

					// /first add the element then check for split and fix
					if (pathNode == null) {
						pathNode = S.pop();

						btsubNode = new BTreeSubNode(key, value, null);
						pathNode.BTreeArray.add(
								pathNode.findIndexTobeInserted(key), btsubNode);
					}

					// Check for the stack to be empty.
					if (S.isEmpty()) {
						// if stack is empty then it is root case.check order of
						// root and split function is called as per the
						// requirement.
						if (pathNode.BTreeArray.size() == order) {
							current = splitBTreeNode(pathNode);
						}
						root = current;
						fixingDone = true;
					} else {

						if (pathNode.BTreeArray.size() == order) {
							// if node gets overflow then fixing function is
							// called.
							current = fix(S.pop(), pathNode);
							pathNode = current;
						} else {
							// else fixing is done.
							fixingDone = true;
						}

					}

				}

			}
			inserted = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return inserted;
	}

	/*
	 * Fixing of the BTree done by spliting and adjusting the pointers.
	 */
	private BTreeNode fix(BTreeNode parentNode, BTreeNode pathNode) {

		BTreeNode fixedNode = new BTreeNode();// fixed node to be returned.
		BTreeNode tempBTreeNode = new BTreeNode();// temp Node for split and
													// meld
		BTreeSubNode tempBTreeSubNode = new BTreeSubNode(0, 0, null);
		List<BTreeSubNode> parent_list = parentNode.BTreeArray;
		int index = 0;

		try {
			// split the node
			tempBTreeNode = splitBTreeNode(pathNode);
			// find root index
			tempBTreeSubNode = tempBTreeNode.BTreeArray.get(0);

			for (BTreeSubNode e : parent_list) {
				if (e.key < tempBTreeSubNode.key) {
					index = index + 1;
				}
			}

			// adjust the pointers.
			if (index < parent_list.size()) {
				parentNode.BTreeArray.add(index, tempBTreeSubNode);
				parentNode.BTreeArray.get(index + 1).leftBTreeNode = tempBTreeNode.rightBTreeNode;
			} else {
				parentNode.BTreeArray.add(index, tempBTreeSubNode);
				parentNode.rightBTreeNode = tempBTreeNode.rightBTreeNode;
			}

			// fixed node to be returned.
			fixedNode = parentNode;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fixedNode;
	}

	/*
	 * Finds the correct location by traversing linear and left/right side.
	 */
	private BTreeNode findLocation(int key, BTreeNode current) {

		BTreeNode btNode = null;
		int lastEntry = 0;
		try {

			// for loop to traverse down the tree.
			for (BTreeSubNode e : current.BTreeArray) {
				if (e.key < key) {
					lastEntry = e.key;
					continue;
				} else if (e.key > key) {
					// left found
					btNode = e.leftBTreeNode;
					return btNode;
				} else {
					// equal found
					duplicateFound = true;
					return btNode;
				}
			}

			if (lastEntry < key) {
				// right found.
				btNode = current.rightBTreeNode;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return btNode;
	}

	/*
	 * Splits the BTree Node In 3 nodes left,middle(root) and right subtree.
	 */
	private BTreeNode splitBTreeNode(BTreeNode current) {

		BTreeNode newBTreeNode = new BTreeNode();

		try {
			int index = current.BTreeArray.size() / 2;

			// for split purpose 3 arrays are created.

			leftArray = new ArrayList<BTreeSubNode>(current.BTreeArray.subList(
					0, index));
			middleArray = new ArrayList<BTreeSubNode>(
					current.BTreeArray.subList(index, index + 1));
			rightArray = new ArrayList<BTreeSubNode>(
					current.BTreeArray.subList(index + 1,
							current.BTreeArray.size()));

			// adjusting the pointers.

			BTreeNode newLeftBTreeNode = middleArray.get(0).leftBTreeNode;

			newBTreeNode.BTreeArray = middleArray;

			newBTreeNode.BTreeArray.get(0).leftBTreeNode = new BTreeNode();
			newBTreeNode.BTreeArray.get(0).leftBTreeNode.BTreeArray = leftArray;
			newBTreeNode.BTreeArray.get(0).leftBTreeNode.rightBTreeNode = newLeftBTreeNode;

			newBTreeNode.rightBTreeNode = new BTreeNode();
			newBTreeNode.rightBTreeNode.BTreeArray = rightArray;
			newBTreeNode.rightBTreeNode.rightBTreeNode = current.rightBTreeNode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newBTreeNode;

	}

	/*
	 * Inorder traversal performed by linear and left and right going into
	 * subtree.
	 */
	public void inorderBTreeTraversal(BTreeNode btNode) {

		try {

			if (btNode == null) {
				return;
			}

			for (BTreeSubNode btSubNode : btNode.BTreeArray) {
				inorderBTreeTraversal(btSubNode.leftBTreeNode);
				Dictionary.pwBTreeSorted.println(btSubNode.key + " "+btSubNode.value);
				//System.out.print(btSubNode.key + " ");
			}

			if (btNode.rightBTreeNode == null) {
				return;
			}

			inorderBTreeTraversal(btNode.rightBTreeNode);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * Level traversal performed by linear and left and right going into
	 * subtree.
	 */
	public void levelOrderTraversal(BTreeNode btNode) {
		try {
			if (btNode == null) {
				return;
			}
		//	System.out.println(" ");
		//	System.out.println("Level Order BTree Traversal ");

			Queue<BTreeNode> btQueue = new LinkedList<BTreeNode>();
			btQueue.add(btNode);

			while (!btQueue.isEmpty()) {
				btNode = btQueue.poll();

				if (btNode == null) {
					return;
				}

				for (BTreeSubNode e : btNode.BTreeArray) {
					//System.out.print(e.key + " ");
					Dictionary.pwBTreeLevel.println(e.key + " "+e.value);
					btQueue.add(e.leftBTreeNode);
				}
				btQueue.add(btNode.rightBTreeNode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Finds the key if already present.
	 */
	public boolean find(BTreeNode root1, int key) {

		boolean positionfound = false;
		boolean elementfound = false;
		Stack<BTreeNode> searchStack = new Stack<BTreeNode>();
		try {

			if (root1 == null) {
				return elementfound;
			}

			// if present in root
			for (BTreeSubNode e : root1.BTreeArray) {
				if (e.key == key) {
					elementfound = true;
					return elementfound;
				}
			}

			// if not then search in subtrees.
			while (!positionfound) {

				searchStack.push(root1);
				root1 = findLocation(key, root1);

				if (root1 == null) {
					positionfound = true;
					break;
				} else {
					for (BTreeSubNode e : root1.BTreeArray) {
						if (e.key == key) {
							elementfound = true;
							return elementfound;
						}
					}
				}
			}

			// postion found then search in that specific destination node BTree
			// Array.
			if (root1 == null) {
				root1 = searchStack.pop();
				for (BTreeSubNode e : root1.BTreeArray) {
					if (e.key == key) {
						elementfound = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return elementfound;
	}

}// end of class BTree
