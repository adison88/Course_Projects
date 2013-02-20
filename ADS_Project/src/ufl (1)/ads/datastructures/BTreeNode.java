/***
 * File Name 				: 		BTreeNode.java
 * Author Name 			: 		Aditya Shrotri
 * Date 						:		October 16,2012
 * Version					:		1.0
 * Purpose					:       This class instantiate B tree array. It has functions to find 
 * 									the exact location of the key to be 
 * 									inserted called as FindIndexTobeInserted. 
 */

package ufl.ads.datastructures;

import java.util.ArrayList;
import java.util.List;

public class BTreeNode {

	public List<BTreeSubNode> BTreeArray = new ArrayList<BTreeSubNode>();// Array
																			// of
																			// BTree
																			// to
																			// be
																			// used
																			// as
																			// hashtable
	public BTreeNode rightBTreeNode;// right BTree pointer.

	/*
	 * Finds exact index in the BTree array.
	 */
	public int findIndexTobeInserted(int key) {

		int index = 0;
		try {

			for (int lint = 0; lint < BTreeArray.size(); lint++) {
				if (BTreeArray.get(lint).key < key) {
					index = index + 1;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return index;
	}

}// end of class BTree Node
