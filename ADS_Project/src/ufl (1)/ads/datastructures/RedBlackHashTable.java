/***
 * File Name 				: 		RedBlackHashTable.java
 * Author Name 			: 		Aditya Shrotri
 * Date 						:		October 13,2012
 * Version					:		1.0
 * Purpose					:       This class front ends the RedBlack tree with RedBlack Hash Table. 
 * 								    It has functions to calculate the hashcode, insert the RedBlack tree 
 * 									in the hash table, search the RedBlack Node in the Hash table.
 */

package ufl.ads.datastructures;

import java.util.Map;
import java.util.TreeMap;

public class RedBlackHashTable {

	private static TreeMap[] RBTreeArray;// Array of RB Treemap

	/*
	 * Constructor of RedBlackHashTable initializes the size of RBTreeArray.
	 */
	public RedBlackHashTable(int size) {
		try {
			// check for the size of the hashtable,if even make it odd
			if (size % 2 == 0) {
				size = size + 1;
			}

			// Initializing RBTreeArray with size
			RBTreeArray = new TreeMap[size];

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/*
	 * Generates the Hashcode
	 */
	public int generateHashcode(int key) {
		int hashCode = 0;
		try {

			hashCode = key % RBTreeArray.length;// hashcode to be returned.

		} catch (Exception e) {

			e.printStackTrace();
		}
		return hashCode;
	}

	/*
	 * Adds key into Array by calling Put Function.
	 */
	public void addKeyIntoArray(int hashCode, int key, int value) {
		try {
			if (RBTreeArray[hashCode] == null) {
				RBTreeArray[hashCode] = new TreeMap();
				RBTreeArray[hashCode].put(key, value);

			} else {
				if (RBTreeArray[hashCode].containsKey(key) == false) {
					RBTreeArray[hashCode].put(key, value);
				} else {
					Dictionary
							.printMessage(
									"Key "
											+ key
											+ " is already present.\nKeys are required to be distinct.",
									Dictionary.pwRedBlackInorder);
					Dictionary
							.printMessage(
									"------------------------------------------------------------",
									Dictionary.pwRedBlackInorder);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void inorderTreeWalk() {

		try {
			for (int lint = 0; lint < RBTreeArray.length; lint++) {
				if (RBTreeArray[lint] == null) {

					Dictionary.pwRedBlackInorder.println("Node " + lint
							+ " of Hashtable does not contain any keys.");
					Dictionary.pwRedBlackInorder
							.println("----------------------------------------------------------------------");
					continue;
				}

				Dictionary.pwRedBlackInorder
						.println("Inorder Tree Walk of Node " + lint
								+ " of HashTable");
				Dictionary.pwRedBlackInorder
						.println("----------------------------------------------------------------------");
				inOrderWalk(RBTreeArray[lint]);
				Dictionary.pwRedBlackInorder.println("  ");
				Dictionary.pwRedBlackInorder
						.println("----------------------------------------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void inOrderWalk(TreeMap<Integer, Integer> treeMap) {
		// this will loop through the values in the map in sorted order (inorder
		// traversal)
		try {
			Integer value = 0;
			Integer key = 0;
			for (Map.Entry<Integer, Integer> entry : treeMap.entrySet()) {
				value = entry.getValue();
				key = entry.getKey();
				//System.out.print(key + " ");
				Dictionary.pwRedBlackInorder.println(key + " "+value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * To find the key into respective RBTree.
	 */
	public boolean searchIntoHashTable(int hash, int key1) {
		boolean result = false;
		try {
			if (RBTreeArray[hash].containsKey(key1)) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}// end of class RedBlackHashTable
