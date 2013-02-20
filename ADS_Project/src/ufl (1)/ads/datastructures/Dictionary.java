/***
 * File Name 				: 		Dictionary.java
 * Author Name 			: 		Aditya Shrotri
 * Date 						:		October 13,2012
 * Version					:		1.0
 * Purpose					:       Program Begins here to Print All types of DataStructures e.g. AVL Tree,Red Black Tree. 
 * 									This file has main function and it initiates the execution. 
 */
package ufl.ads.datastructures;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Dictionary {
	

	// time calculation variables
	private static long start = 0;   //start time
	private static long stop = 0;	//stop time
	private static double time = 0; // stop-start time

	// Hash Code Variables
	private static int hashCode = 0;//hashCode to be calculated.
	private static int key = 0;
	private static int value = 0;
	private static int range = 1000000;//n=1000000

	// Random Number List	
	private static ArrayList<Integer> randomArrayList;//arraylist of random numbers.

	// To Read File
	private static BufferedReader br; 
	private static String str3;
	private static String[] strArray;

	// To write File,Print writer objects are taken
	public static PrintWriter pwAVLInorder;
	public static PrintWriter pwAVLPostorder;
	public static PrintWriter pwBTreeSorted;
	public static PrintWriter pwBTreeLevel;
	public static PrintWriter pwRedBlackInorder;

	// Modes
	private static String randomMode = "-r";	//Random Mode
	private static String userMode = "-u";		// User Mode
	private static String runningMode = "";		//running mode

	// HashTables
	private static AVLHashTable avlhash;
	private static RedBlackHashTable redBlackHash;
	private static BTreeHashTable bTreeHash;	

	// Main method
	public static void main(String[] args) {

		try {

			System.out.println("PROGRAM STARTED");
			
			//Assignment of Running Mode whether Random/User
			runningMode = args[0];
			
			//Checking for valid Input Mode
			if(!runningMode.equals(randomMode))
			{
				if(!runningMode.equals(userMode))
				{
					System.out.println("Please select a valid input mode. (-r/-u)");
					return;
				}
			}
			
			//Instantiating PrintWriter Object to Create OUTPUT.txt
			pwAVLInorder = new PrintWriter(new BufferedWriter(new FileWriter(
					"AVLHash_inorder.out")), true);
			pwAVLPostorder = new PrintWriter(new BufferedWriter(new FileWriter(
					"AVLHash_postorder.out")), true);
			pwBTreeSorted = new PrintWriter(new BufferedWriter(new FileWriter(
					"BTreeHash_sorted.out")), true);
			pwBTreeLevel = new PrintWriter(new BufferedWriter(new FileWriter(
					"BTreeHash_level.out")), true);
			pwRedBlackInorder = new PrintWriter(new BufferedWriter(new FileWriter(
					"RedBlackHash_inorder.out")), true);
			
			//Filling and shuffling the arraylist.
			if(runningMode.equals(randomMode))
			{
				
				randomArrayList = new ArrayList<Integer>(range);
				for (int idx = 1; idx <= range; ++idx) {
					randomArrayList.add(idx);
				}	
				Collections.shuffle(randomArrayList);
			}			

			//Call Functions of respective trees.
			System.out.println("Call AVL Tree Function");
			callAVLTree(args);
			System.out.println("Call Red Black Tree Function");
			callRedBlackTree(args);			
			System.out.println("Call BTree Function");
			callBTree(args);
			
			//Searching is begin called only during Random mode.
			if(runningMode.equals(randomMode))
			{
				System.out.println("Call AVL Search Function");
				searchAVLTree();
				System.out.println("Call Red Black Tree Search Function");
				searchRedBlackTree();
				System.out.println("Call BTree  Search Function");
				searchBTree();
			}
			else if(runningMode.equals(userMode))
			{
				System.out.println(" ");
				System.out.println("Output Files have been created in the Default Project Directory.Please Check the Output.");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	/*
	 * Checks for the Running Mode and calls the respective functions for the insert and search of key in BTree.
	 */
	private static void callBTree(String[] args) {
		try {
			// BTree IMPLEMENTATION
	
			//start of clock
			start = System.currentTimeMillis();

			if (args[0].equals(randomMode)) {
				// RANDOM MODE
			//	pw.println("User Has Selected Random Mode.");
				bTreeHash = new BTreeHashTable(Integer.parseInt(args[1]),Integer.parseInt(args[2]));

				for (int e : randomArrayList) {
					key = e;
					value = 2 * key;
					hashCode = bTreeHash.generateHashcode(key);				   
				    bTreeHash.addKeyIntoArray(hashCode, key, value);			
				}			
		

			} else if (args[0].equals(userMode)) {
				// USER MODE
			//	pw.println("User Has Selected User Mode.");
				bTreeHash = new BTreeHashTable(3,3);
				br = new BufferedReader(new FileReader(args[1]));
				

				//Reading total Numbers
				String numbers = br.readLine();
				int inputCount = Integer.parseInt(numbers);
				int count =0;
				
				//Reading key and value
				str3 = br.readLine();
				
				while (str3 != null) {
					strArray = str3.split(" ");
					
					try {
						//Read the file until input count is reached.
						if(inputCount == count)
						{
							break;
						}						
						
						//reading key and values
						key = Integer.parseInt(strArray[0]);
						value = Integer.parseInt(strArray[1]);
						count = count+1;
					} catch (Exception e) {
						break;
					}
					
					//generating hashcode
					hashCode = bTreeHash.generateHashcode(key);
					//insert into hashtable
					bTreeHash.addKeyIntoArray(hashCode, key, value);
					str3 = br.readLine();
				}
			}

			// execution time for BTree algorithm.
			//pw.println("Execution time for BTree algorithm MODE "
		//			+ runningMode);
			stop = System.currentTimeMillis();
			time = (stop - start) ;
		//	pw.println(time + " Milliseconds");
			System.out.println("Execution time for BTree algorithm MODE "
					+ runningMode + "  " + time + " Milliseconds");
		

			if (runningMode.equals(userMode)) {
				pwBTreeSorted.println("*************Sorted Tree Walk For B Tree*************");
				bTreeHash.inorderTreeWalk();
				pwBTreeLevel.println("*************Level Tree Walk For B Tree*************");
				bTreeHash.levelOrderTreeWalk();
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	/*
	 * Compute the Hashcode and time required to to search all the elements in RedBlack HashTable
	 */	
	private static void searchRedBlackTree() {
		try {
			ArrayList<Integer> totalElements = new ArrayList<Integer>();
			long searchStart= System.currentTimeMillis();
			for(int key1 : randomArrayList)
			{
				if( redBlackHash.searchIntoHashTable(redBlackHash.generateHashcode(key1),key1) == true)
				{
					totalElements.add(key1);
				}
			}
			long searchStop = System.currentTimeMillis();
	//		Dictionary.pw.println("***************Search Time For RedBlack Tree*****************");
			System.out.println(totalElements.size() + " Elements have been found after " + (searchStop- searchStart) + " Miliseconds.");
	//		Dictionary.pw.println(totalElements.size() + " Elements have been found after " + (searchStop- searchStart) + " Miliseconds.");			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	/*
	 * Compute the Hashcode and time required to to search all the elements in AVL HashTable
	 */
	private static void searchAVLTree() {
		
		try {
			ArrayList<Integer> totalElements = new ArrayList<Integer>();
			long searchStart= System.currentTimeMillis();
			for(int key1 : randomArrayList)
			{
				if( avlhash.searchIntoHashTable(avlhash.generateHashcode(key1),key1) == true)
				{
					totalElements.add(key1);
				}
			}
			long searchStop = System.currentTimeMillis();
		//	Dictionary.pw.println("***************Search Time For AVL Tree*****************");
			System.out.println(totalElements.size() + " Elements have been found after " + (searchStop- searchStart) + " Miliseconds.");
	//		Dictionary.pw.println(totalElements.size() + " Elements have been found after " + (searchStop- searchStart) + " Miliseconds.");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/*
	 * Compute the Hashcode and time required to to search all the elements in BTree HashTable
	 */
	private static void searchBTree() {
		
		try {
			ArrayList<Integer> totalElements = new ArrayList<Integer>();
			
			//start time
			long searchStart= System.currentTimeMillis();
			for(int key1 : randomArrayList)
			{
				if( bTreeHash.searchIntoHashTable(bTreeHash.generateHashcode(key1),key1) == true)
				{
					totalElements.add(key1);
				}
			}
			//end time
			long searchStop = System.currentTimeMillis();		
			System.out.println(totalElements.size() + " Elements have been found after " + (searchStop- searchStart) + " Miliseconds.");			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Checks for the Running Mode and calls the respective functions for the insert and search of key in RedBlackTree.
	 */
	private static void callRedBlackTree(String[] args) {

		try {					

			start = System.nanoTime();

			if (args[0].equals(randomMode)) {
				// RANDOM MODE
				redBlackHash = new RedBlackHashTable(Integer.parseInt(args[1]));
				for (int idx = 0; idx < randomArrayList.size(); idx++) {										
					key = randomArrayList.get(idx);
					value = 2 * key;
					hashCode = redBlackHash.generateHashcode(key);
					redBlackHash.addKeyIntoArray(hashCode, key, value);
				}					
				
			} else {
				// USER MODE
				redBlackHash = new RedBlackHashTable(1);
				br = new BufferedReader(new FileReader(args[1]));
				
				//Reading total Numbers
				String numbers = br.readLine();
				int inputCount = Integer.parseInt(numbers);
				int count =0;
				
				str3 = br.readLine();
				while (str3 != null) {
					strArray = str3.split(" ");
					
					try {
						//Read the file until input count is reached.
						if(inputCount == count)
						{
							break;
						}						
						
						key = Integer.parseInt(strArray[0]);
						value = Integer.parseInt(strArray[1]);
						count=count+1;
					} catch (Exception e) {
						break;
					}
					
					hashCode = redBlackHash.generateHashcode(key);
					redBlackHash.addKeyIntoArray(hashCode, key, value);
					str3 = br.readLine();
				}
			}

			// execution time for Red Black Tree algorithm.
		
			stop = System.nanoTime();
			time = (stop - start) / 1000000;
			//pw.println(time + " Milliseconds");
			System.out
					.println("Execution time for Red Black Tree algorithm MODE "
							+ runningMode + "  " + time + " Milliseconds");

			if (runningMode.equals(userMode)) {
				pwRedBlackInorder.println("*************Inorder Tree Walk For RedBlack Tree*************");
				redBlackHash.inorderTreeWalk();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	/*
	 * Checks for the Running Mode and calls the respective functions for the insert and search of key in AVLTree.
	 */
	public static void callAVLTree(String[] args) {
		try {
			// AVLTREE IMPLEMENTATION			
			start = System.currentTimeMillis();

			if (args[0].equals(randomMode)) {
				// RANDOM MODE
		
				avlhash = new AVLHashTable(Integer.parseInt(args[1]));			

				for (int e : randomArrayList) {
					key = e;
					value = 2 * key;
					hashCode = avlhash.generateHashcode(key);				   
				    avlhash.addKeyIntoArray(hashCode, key, value);			
				}			
		

			} else if (args[0].equals(userMode)) {
				// USER MODE
		
				//Define size of HashTable
				avlhash = new AVLHashTable(3);
				br = new BufferedReader(new FileReader(args[1]));
				
				//Reading total Numbers
				String numbers = br.readLine();
				int inputCount = Integer.parseInt(numbers);
				int count =0;
				//Reading Key and value
				str3 = br.readLine();
				while (str3 != null) {
					strArray = str3.split(" ");
					
					try {
						//Read the file until input count is reached.
						if(inputCount == count)
						{
							break;
						}
						//Reading Key and value
						key = Integer.parseInt(strArray[0]);
						value = Integer.parseInt(strArray[1]);
						count = count + 1;
						
						
					} catch (Exception e) {
						break;
					}
					hashCode = avlhash.generateHashcode(key);
					avlhash.addKeyIntoArray(hashCode, key, value);
					
					str3 = br.readLine();
				}
			}

			// execution time for AVL Tree algorithm.

			stop = System.currentTimeMillis();
			time = (stop - start) ;
		
			System.out.println("Execution time for AVLTree algorithm MODE "
					+ runningMode + "  " + time + " Milliseconds");


			if (runningMode.equals(userMode)) {
				pwAVLInorder.println("*************Inorder Tree Walk For AVL Tree*************");
				avlhash.inorderTreeWalk();
				pwAVLPostorder.println("*************Postorder Tree Walk For AVL Tree*************");
				avlhash.postOrderTreeWalk();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Prints the Message only in User Mode with respective PrintWriter.
	 */
	public static void printMessage(String msg,PrintWriter pw) {
		try {
			if (runningMode.equals(userMode)) {
				pw.println(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}// end of class Dictionary