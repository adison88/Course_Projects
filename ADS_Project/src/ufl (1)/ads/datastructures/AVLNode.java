/***
 * File Name 				: 		AVLNode.java
 * Author Name 			: 		Aditya Shrotri
 * Date 						:		October 13,2012
 * Version					:		1.0
 * Purpose					:       The class implements a AVL Tree node. 
 * 									It stores the data as well as link to its left 
 * 									and right children. It also stores the height and Balance factor of the tree.
 */

package ufl.ads.datastructures;

class AVLNode {
	public int iKey; // (key)
	public int iValue; // value = 2 * key
	public AVLNode leftChild; // node's left child
	public AVLNode rightChild; // node's right child
	public int balance; // balance to be calculated from the height.
	public int height; // height of each AVLNode.

} // end class AVLNode