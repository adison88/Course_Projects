/***
 * File Name 				: 		AVLHashTable.java
 * Author Name 			: 		Aditya Shrotri
 * Date 						:		October 13,2012
 * Version					:		1.0
 * Purpose					:       This class front ends the AVL tree with AVL Hash Table. 
 * 									It has functions to calculate the hashcode, insert the AVL tree
 * 									in the hash table, search the AVL Node in the Hash table.
 */

package ufl.ads.datastructures;

public class AVLHashTable {

	private static AVLTree[] AVLArray;// Array of AVLTrees.

	/*
	 * Constructor of AVLHashTable initializes the size of AVLArray.
	 */
	public AVLHashTable(int size) {

		try {

			// check for the size of the hashtable,if even make it odd
			if (size % 2 == 0) {
				size = size + 1;
			}

			// Initializing AVLArray with size
			AVLArray = new AVLTree[size];

			for (int lint = 0; lint < AVLArray.length; lint++) {
				AVLArray[lint] = new AVLTree();

			}

		} catch (Exception e) {

			e.printStackTrace();

		}// end of constructor
	}

	/*
	 * Generates the Hashcode
	 */
	public int generateHashcode(int key) {
		int hashCode = 0;// hashcode to be returned.
		try {

			// Calculation of hashCode
			hashCode = key % AVLArray.length;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return hashCode;
	}

	/*
	 * To find the key into respective AVLTree.
	 */
	public boolean searchIntoHashTable(int hash, int key) {
		boolean result = false;
		try {
			// Calling the find function to check whether element is present in
			// AVLTree
			if (AVLArray[hash].find(AVLArray[hash].getRoot(), key) != null) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * Adds key into Array by calling Insert Function.
	 */
	public void addKeyIntoArray(int hashCode, int key, int value) {
		try {

			// Null check
			if (AVLArray[hashCode] == null) {
				// AVLArray[hashCode] = new AVLTree();
				AVLArray[hashCode].insert(key, value);

			} else {
				// If element is not present then add otherwise print Message.
				if (AVLArray[hashCode].find(AVLArray[hashCode].getRoot(), key) == null) {
					AVLArray[hashCode].insert(key, value);
				} else {
					Dictionary
							.printMessage(
									"Key "
											+ key
											+ " is already present.Keys are required to be distinct.",
									Dictionary.pwAVLInorder);
					Dictionary
							.printMessage(
									"------------------------------------------------------------",
									Dictionary.pwAVLInorder);
					Dictionary
							.printMessage(
									"Key "
											+ key
											+ " is already present.Keys are required to be distinct.",
									Dictionary.pwAVLPostorder);
					Dictionary
							.printMessage(
									"------------------------------------------------------------",
									Dictionary.pwAVLPostorder);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Inorder Tree Walk of AVL TREE
	 */
	public void inorderTreeWalk() {

		try {
			for (int lint = 0; lint < AVLArray.length; lint++) {
				if (AVLArray[lint] == null) {

					Dictionary.pwAVLInorder.println("Node " + lint
							+ " does not contain any keys.");
					Dictionary.pwAVLInorder
							.println("----------------------------------------------------------------------");
					continue;
				}
				Dictionary.pwAVLInorder.println("Inorder Tree Walk of Node "
						+ lint);
				Dictionary.pwAVLInorder
						.println("----------------------------------------------------------------------");
				AVLArray[lint].inorderTreeWalk(AVLArray[lint].getRoot());
				Dictionary.pwAVLInorder.println("   ");
				// System.out.println(" +++ ");
				Dictionary.pwAVLInorder
						.println("----------------------------------------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * PostOrder Tree Walk of AVL TREE
	 */
	public void postOrderTreeWalk() {

		try {
			for (int lint = 0; lint < AVLArray.length; lint++) {
				if (AVLArray[lint] == null) {

					Dictionary.pwAVLPostorder.println("Node " + lint
							+ " does not contain any keys.");
					Dictionary.pwAVLPostorder
							.println("----------------------------------------------------------------------");
					continue;
				}
				Dictionary.pwAVLPostorder
						.println("Postorder Tree Walk of Node " + lint);
				Dictionary.pwAVLPostorder
						.println("----------------------------------------------------------------------");
				AVLArray[lint].postTreeWalk(AVLArray[lint].getRoot());
				Dictionary.pwAVLPostorder.println("  ");
				Dictionary.pwAVLPostorder
						.println("----------------------------------------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}// end of class AVLHashTable
