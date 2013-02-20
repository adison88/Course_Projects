/***
 * File Name 				: 		BTreeSubNode.java
 * Author Name 			: 		Aditya Shrotri
 * Date 						:		October 16,2012
 * Version					:		1.0
 * Purpose					:        This class has 3 attributes. Key, value and left B Tree node pointer.
 */

package ufl.ads.datastructures;

public class BTreeSubNode {

	public int key; // key to be stored.
	public int value; // value = 2* key.
	public BTreeNode leftBTreeNode; // left BTreeNode pointer.

	/*
	 * Constructor of BTreesubnode to initialize key value and Node
	 */
	public BTreeSubNode(int key, int value, BTreeNode node) {
		this.key = key;
		this.value = value;
		this.leftBTreeNode = node;
	}

}// end of class BTreeSubNode
