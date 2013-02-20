/***
 * File Name 				: 		BTreeHashTable.java
 * Author Name 			: 		Aditya Shrotri
 * Date 						:		October 16,2012
 * Version					:		1.0
 * Purpose					:       This class front ends the B tree with B Tree Hash Table.
 * 									It has functions to calculate the hashcode, insert the B tree
 * 									in the hash table, search the B Tree Node in the Hash table.
 */

package ufl.ads.datastructures;

public class BTreeHashTable {

	private static BTree[] BTreeArray; // Array of BTree to be used as HashTable

	/*
	 * Constructor of BTreeHashTable initializes the size,order of BTreeArray.
	 */
	public BTreeHashTable(int size, int order) {

		try {
			// check for the size of the hashtable,if even make it odd
			if (size % 2 == 0) {
				size = size + 1;
			}

			// Initializing BTreeArray with size and order
			BTreeArray = new BTree[size];

			for (int lint = 0; lint < BTreeArray.length; lint++) {
				BTreeArray[lint] = new BTree(order);

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/*
	 * Generates the Hashcode
	 */
	public int generateHashcode(int key) {
		int hashCode = 0;// hashcode to be returned.
		try {

			hashCode = key % BTreeArray.length; // Calculation of hashCode

		} catch (Exception e) {

			e.printStackTrace();
		}
		return hashCode;
	}

	/*
	 * To find the key into respective BTree.
	 */
	public boolean searchIntoHashTable(int hash, int key) {
		return BTreeArray[hash].find(BTreeArray[hash].getRoot(), key);
	}

	/*
	 * Adds key into Array by calling Insert Function.
	 */
	public void addKeyIntoArray(int hashCode, int key, int value) {
		try {

			if (BTreeArray[hashCode] == null) {
				// AVLArray[hashCode] = new AVLTree();
				BTreeArray[hashCode].insertIntoBTree(key, value);

			} else {

				if (!BTreeArray[hashCode].insertIntoBTree(key, value)) {
					Dictionary
							.printMessage(
									"Key "
											+ key
											+ " is already present.Keys are required to be distinct.",
									Dictionary.pwBTreeSorted);
					Dictionary
							.printMessage(
									"------------------------------------------------------------",
									Dictionary.pwBTreeSorted);
					Dictionary
							.printMessage(
									"Key "
											+ key
											+ " is already present.Keys are required to be distinct.",
									Dictionary.pwBTreeLevel);
					Dictionary
							.printMessage(
									"------------------------------------------------------------",
									Dictionary.pwBTreeLevel);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Inorder Tree Walk of B TREE
	 */
	public void inorderTreeWalk() {

		try {
			for (int lint = 0; lint < BTreeArray.length; lint++) {
				if (BTreeArray[lint] == null) {

					Dictionary.pwBTreeSorted.println("Node " + lint
							+ " does not contain any keys.");
					Dictionary.pwBTreeSorted
							.println("----------------------------------------------------------------------");
					continue;
				}
				Dictionary.pwBTreeSorted.println("Inorder Tree Walk of Node "
						+ lint);
				Dictionary.pwBTreeSorted
						.println("----------------------------------------------------------------------");
			//	System.out.println(" ");
			//	System.out.println("InOrder Btree Traversal ");
				BTreeArray[lint].inorderBTreeTraversal(BTreeArray[lint]
						.getRoot());
				Dictionary.pwBTreeSorted.println("   ");
				// System.out.println(" +++ ");
				Dictionary.pwBTreeSorted
						.println("----------------------------------------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * Post Tree Walk of B TREE
	 */
	public void levelOrderTreeWalk() {

		try {
			for (int lint = 0; lint < BTreeArray.length; lint++) {
				if (BTreeArray[lint] == null) {

					Dictionary.pwBTreeLevel.println("Node " + lint
							+ " does not contain any keys.");
					Dictionary.pwBTreeLevel
							.println("----------------------------------------------------------------------");
					continue;
				}
				Dictionary.pwBTreeLevel
						.println("Level Order Tree Walk of Node " + lint);
				Dictionary.pwBTreeLevel
						.println("----------------------------------------------------------------------");
				BTreeArray[lint]
						.levelOrderTraversal(BTreeArray[lint].getRoot());
				Dictionary.pwBTreeLevel.println("   ");
				// System.out.println(" +++ ");
				Dictionary.pwBTreeLevel
						.println("----------------------------------------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}// end of class BTreeHashTable
