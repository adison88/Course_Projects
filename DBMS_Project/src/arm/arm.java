/***
 * File Name 				: 		arm.java
 * Author Name 			: 		Aditya Shrotri
 * Date 						:		October 28,2012
 * Version					:		1.0
 * Purpose					:       The class accomplishes implementation of  the association rule data mining task using the Apriori algorithm
 * 							:		using Java and connection to Oracle database.Program takes input from file "system.in" and writes output in 
 * 							:		files named "system.out.1","system.out.2","system.out.3","system.out.4" respectively for Task 1,Task 2,Task 3
 * 							:		and Task 4.
 */

package arm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class arm {
	

	//static objects of PrintWriter to write output in respective files.
	private static PrintWriter pwTask1;
	private static PrintWriter pwTask2;
	private static PrintWriter pwTask3;
	private static PrintWriter pwTask4;

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String args[]) throws SQLException {
		
		// Connect to the database
				// You must put a database name after the @ sign in the connection URL.
				// You can use either the fully specified SQL*net syntax or a short cut
				// syntax as <host>:<port>:<sid>. The example uses the short cut syntax.
		
		
		Connection conn=null;				//Connection object to make connection with database.
		String userName=null;				//user name to log in into database.
		String passWord=null;				//password  to log in into database.
		String task1Support=null;			//input support for the task1	
		String task2Support=null;			//input support for the task2
		String task3Support=null;			//input support for the task3
		String task3Size=null;				//input size for the task3
		String task4Support=null;			//input support for the task3
		String task4Size=null;				//input size for the task4
		String task4Confidence=null; 		//input confidence for the task4
		String newline = " \r\n";				//newline character to write into file.
	
		try {
			System.out.println("Program Started");
			
			BufferedReader br = null;
			

			
			
			//Instantiating PrintWriter Object to Create "system.out.1","system.out.2","system.out.3","system.out.4"
			pwTask1 = new PrintWriter(new BufferedWriter(new FileWriter(
					"system.out.1")), true);
			pwTask2 = new PrintWriter(new BufferedWriter(new FileWriter(
					"system.out.2")), true);
			pwTask3 = new PrintWriter(new BufferedWriter(new FileWriter(
					"system.out.3")), true);
			pwTask4 = new PrintWriter(new BufferedWriter(new FileWriter(
					"system.out.4")), true);
			
			
		
			
//			pwTask1.println("Output for TASK1:"+newline);			
//			pwTask2.println("Output for TASK2:"+newline);
//			pwTask3.println("Output for TASK3:"+newline);
//			pwTask4.println("Output for TASK4:"+newline);
			
			
			try {
				String currentLine;
				String[] items = new String[2]; 
				
				//reading input
				BufferedReader brInput =new BufferedReader(new FileReader("system.in"));
				
				currentLine = brInput.readLine();
				userName = currentLine.split(",")[0].split("=")[1];
				passWord = currentLine.split(",")[1].split("=")[1];
				
				currentLine = brInput.readLine();
				task1Support = currentLine.split("=")[1].split("%")[0];
				task1Support = Double.toString(Double.parseDouble(task1Support));
				
				currentLine = brInput.readLine();
				task2Support = currentLine.split("=")[1].split("%")[0];
				task2Support = Double.toString(Double.parseDouble(task2Support));
				
				currentLine = brInput.readLine();
				task3Support = currentLine.split(",")[0].split("=")[1].split("%")[0];
				task3Support = Double.toString(Double.parseDouble(task3Support));
				
				task3Size = currentLine.split(",")[1].split("=")[1];
				task3Size = Double.toString(Double.parseDouble(task3Size));
				
				currentLine = brInput.readLine();
				task4Support = currentLine.split(",")[0].split("=")[1].split("%")[0];
				task4Support = Double.toString(Double.parseDouble(task4Support));
				
				task4Confidence = currentLine.split(",")[1].split("=")[1].split("%")[0];
				task4Confidence = Double.toString(Double.parseDouble(task4Confidence));
				
				task4Size = currentLine.split(",")[2].split("=")[1];
				task4Size = Double.toString(Double.parseDouble(task4Size));
				
				
				//Printing Input Parameters on Console
				System.out.println("Input Parameters: ");
				System.out.println("username ="+userName+",password ="+passWord);
				System.out.println("TASK1: support = "+task1Support+"%");
				System.out.println("TASK2: support = "+task2Support+"%");
				System.out.println("TASK3: support = "+task3Support +"%, size = "+task3Size);
				System.out.println("TASK4: support = "+task4Support +"%, confidence = "+task4Confidence+", size = "+task4Size);
				
				
			} catch (FileNotFoundException e1) {
				
				e1.printStackTrace();
			}
			 
			
			
			// Load the Oracle JDBC driver
			System.out.println("Registering connection...");
			 DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			 //Class.forName ("oracle.jdbc.OracleDriver");
			System.out.println("Registered..");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@oracle.cise.ufl.edu:1521:orcl", userName,
					passWord);
			
			
			// Create a Statement to execute queries
			Statement stmt = conn.createStatement();
			
			// Create a Statement to execute insert queries
			Statement insertItemsStmt = conn.createStatement();
			Statement insertTransStmt = conn.createStatement();
			
			// Create a Prepared Statement to execute insert queries faster
			PreparedStatement prepItemStmt = conn.prepareStatement("Insert into items values(?,?)");
			PreparedStatement prepTransStmt = conn.prepareStatement("Insert into trans values(?,?)");
			long start = System.currentTimeMillis();
			
			try {
				 
				String sCurrentLine;
				String[] items = new String[2]; 
				
				//Creating Items table and Inserting data into it using BufferedReader
				try {
					insertItemsStmt.executeQuery("Drop Table items");
					insertItemsStmt.executeQuery("Create Table items (itemid integer,itemname varchar(50))");
				} catch (Exception e) {
					insertItemsStmt.executeQuery("Create Table items (itemid integer,itemname varchar(50))");
				//	e.printStackTrace();
				}	
						
				br = new BufferedReader(new FileReader("items.dat"));
 
				while ((sCurrentLine = br.readLine()) != null)
				{					
					items = sCurrentLine.split(",");
					prepItemStmt.setString(1,items[0]);
					prepItemStmt.setString(2,items[1]);
					prepItemStmt.addBatch();
					//insertItemsStmt.executeQuery("Insert into items values("+sCurrentLine+")");
					//System.out.println(sCurrentLine);
				}
				
				 int[] updateCounts = prepItemStmt.executeBatch();
				 
			     //   System.out.println(updateCounts);
 
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
//			try {
//				if (br != null)br.close();
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
			}

			
			//Creating Trans table and Inserting data into it using BufferedReader
			try {
				 
				String sCurrentLine;
				String[] items = new String[2]; 
				
				try {
					insertTransStmt.executeQuery("Drop Table trans");
					insertTransStmt.executeQuery("CREATE TABLE TRANS ( TransID INT,ItemID INT )");
				} catch (Exception e) {
					insertTransStmt.executeQuery("CREATE TABLE TRANS ( TransID INT,ItemID INT )");
				//	e.printStackTrace();
				}	
						
				br = new BufferedReader(new FileReader("trans.dat"));
 
				while ((sCurrentLine = br.readLine()) != null)
				{
					items = sCurrentLine.split(",");
					prepTransStmt.setString(1,items[0]);
					prepTransStmt.setString(2,items[1]);
					prepTransStmt.addBatch();
					
					//insertTransStmt.addBatch("Insert into trans values("+sCurrentLine+")");
				//	insertTransStmt.executeQuery("Insert into trans values("+sCurrentLine+")");
					//System.out.println(sCurrentLine);
				}
				
				 int[] updateCounts = prepTransStmt.executeBatch();
			     //   System.out.println(updateCounts);
 
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
//			try {
//				if (br != null)br.close();
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
			}
			
			
			
			ResultSet dummyTask1;
			ResultSet dummyTask2;
			
			// Select the distinct transID column from the trans table
			ResultSet rset = stmt.executeQuery("select count(distinct transId) from trans");
			rset.next();
			System.out.println("Distinct Records: "+rset.getString(1));
			int distinctCount = Integer.parseInt(rset.getString(1));
			
			//String fiSize = args[1];
			
			/*
			 * ***********TASK 1**************
			 * In Task 1, given a specific support level(such as s = 45%), your program should re-
				turn all of the (single)items in the database that appear at least s% of the time. So
				your output might be:The following items appear in 45% of the database transactions:
				fMerlot Cheddarg, fRomanog, fRed Potatoesg.
			 */
			
			System.out.println("***********TASK 1**************");
			System.out.println("   ");
			String support = task1Support;
			
			double count = Double.parseDouble(rset.getString(1)) * Double.parseDouble(support) / 100;
			String task1Query="";
			String query = "select  distinct i.itemname ,count(t.itemid) * 100/ " + Integer.parseInt(rset.getString(1))+" "+
					"from items i,trans t "+
					"where t.itemid = i.itemid "+
					"group by i.itemname "+
					"having count(*) >= " + count;
			
			//Query to take input dynamically for task1
			task1Query="select  distinct i.itemname ,count(t.itemid) * 100/ " + Integer.parseInt(rset.getString(1))+" "+
					"from items i,trans t "+
					"where t.itemid = i.itemid "+
					"group by i.itemname "+
					"having count(*) >= ";
			 rset = stmt.executeQuery(query);
	        dummyTask1 = rset;
			 
			 System.out.println("Items having support Greater than " + support+"%");
			// Iterate through the result and print the result names
			while (rset.next())
			{
				String temp = "{"+rset.getString(1) +"},s="+rset.getString(2)+"%";
				System.out.println(temp);
				pwTask1.println(temp+newline);				
			}
			
//	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//			
			
			/*
			 * ***********TASK 2**************
			 * In Task 2, given a specific support level(such as s= 45%), your program should return
				all of the single items or pairs of items that appear in the database at least s% of the
				time. So your output might be: The following items appear in 45% of the database
				transactions: fMerlot Cheddarg, fRomanog, fRed Potatoesg, fMerlot Cheddar, Ro-
				manog, fMerlot Cheddar, Red Potatoesg
			 */
			
			System.out.println("   ");
			System.out.println("***********TASK 2**************");
			System.out.println("   ");
			
			
			//count for task2Support
			count = distinctCount * Double.parseDouble(task2Support) / 100;
		    query = "select  distinct i.itemname ,count(t.itemid) * 100/ " +distinctCount+" "+
					"from items i,trans t "+
					"where t.itemid = i.itemid "+
					"group by i.itemname "+
					"having count(*) >= " + count;
			 rset = stmt.executeQuery(query);
	
			 System.out.println("Items having support Greater than " + task2Support+"%");
			// Iterate through the result and print the result the (single)items 
			while (rset.next())
			{
				String temp="{"+rset.getString(1) +"},s="+rset.getString(2)+"%";
				System.out.println(temp);
				pwTask2.println(temp+newline);
			}
			
		
		//	stmt.executeQuery("DROP TABLE IF EXISTS temp");
			//stmt.executeQuery("CREATE TABLE temp (itemid INT)");
			try {
				insertTransStmt.executeQuery("DROP TABLE temp");
				insertTransStmt.executeQuery("CREATE TABLE temp (itemid INT)");
			} catch (Exception e) {
				insertTransStmt.executeQuery("CREATE TABLE temp (itemid INT)");
			//	e.printStackTrace();
			}
			
			query = "Insert into temp ( " + "select items.itemid from items, trans where items.itemid=trans.itemid group by items.itemname, items.itemid having count(*)>= "
						+count+")";
			stmt.executeQuery(query);
			
			query = "select i1.itemname, i2.itemname ,count(*) * 100/ " + distinctCount+" "+ 
					"from temp t1, temp t2, trans r1, trans r2, items i1, items i2 "+
					"where t1.itemid>t2.itemid and i1.itemid=t1.itemid and i2.itemid=t2.itemid "+
					"and t1.itemid=r1.itemid and t2.itemid=r2.itemid "+
					"and r1.transid=r2.transid "+
					"group  by i1.itemname, i2.itemname "+
					"having count(*) >= "+count;
			
			//Que	ry to take input dynamically for task2
			String task2Query="select i1.itemname, i2.itemname ,count(*) * 100/ " + distinctCount+" "+ 
					"from temp t1, temp t2, trans r1, trans r2, items i1, items i2 "+
					"where t1.itemid>t2.itemid and i1.itemid=t1.itemid and i2.itemid=t2.itemid "+
					"and t1.itemid=r1.itemid and t2.itemid=r2.itemid "+
					"and r1.transid=r2.transid "+
					"group  by i1.itemname, i2.itemname "+
					"having count(*) >= ";
			rset = stmt.executeQuery(query);
		//	dummyTask2 = rset;
			
			
			// Iterate through the result and print the the (pair)items
			while (rset.next())
			{
				String temp = "{"+rset.getString(1)+","+rset.getString(2) +"},s="+rset.getString(3)+"%";//,s="+rset.getString(2).substring(0,1)+"%"
				System.out.println(temp);
				pwTask2.println(temp+newline);
			}
			
			
			/*
			 * ***********TASK 3**************
			 * In Task 3, given a specific support level and the maximum FI size, your program should
				and all of the frequent itemsets that are no larger (in terms of the number of items	
				that they contain) than the user-specified size.
			 */
			
			System.out.println("   ");
			System.out.println("***********TASK 3**************");
			
			 System.out.println("Items having support Greater than " + task3Support+"% and size equal to "+ task3Size);
			
			Statement insert = conn.createStatement();
			Statement create = conn.createStatement();		
			Statement alter = conn.createStatement();
			Statement delete = conn.createStatement();
			
			
			//Temporary table R2final To collect all possible combinations derived from R2
			try {
				create.executeQuery("Drop Table R2final");
				create.executeQuery("create table R2final(transid int,itemid1 int,itemid2 int) ");
			} catch (Exception e) {
				create.executeQuery("create table R2final(transid int,itemid1 int,itemid2 int) ");
			//	e.printStackTrace();
			}
			
			//Temporary table C2 To retrieve all possible tuples for input size and support 
			try {
				create.executeQuery("Drop Table c2");
				create.executeQuery("create table c2(count1 int,itemid1 int,itemid2 int)");
			} catch (Exception e) {
				create.executeQuery("create table c2(count1 int,itemid1 int,itemid2 int)");
			//	e.printStackTrace();
			}
			
			//Temporary table R2 To collect all possible combinations derived from C2 and R2final which will be in turn used to derive R2final in next iteration
			try {
				create.executeQuery("Drop Table R2");
				create.executeQuery("create table R2(transid int,itemid1 int,itemid2 int)");
			} catch (Exception e) {
				create.executeQuery("create table R2(transid int,itemid1 int,itemid2 int)");
			//	e.printStackTrace();
			}
			
			//Table outputNames to take output tuples in this table in order to print output
			try {
				create.executeQuery("Drop Table outputNames");
				create.executeQuery("CREATE TABLE outputNames (count INT, ItemName1 varchar(50),ItemName2 varchar(50) )");
			} catch (Exception e) {
				create.executeQuery("CREATE TABLE outputNames (count INT, ItemName1 varchar(50),ItemName2 varchar(50) )");
			//	e.printStackTrace();
			}	
			
			
			count = distinctCount * Double.parseDouble(task3Support) / 100;
			insert.executeQuery("INSERT INTO R2final SELECT p.transId, p.ItemId, q.ItemId FROM trans  p, trans  q WHERE p.transId = q.transId AND q.Itemid > p.Itemid ");
			insert.executeQuery("INSERT INTO C2 SELECT COUNT(*),p.Itemid1, p.Itemid2 FROM R2final  p GROUP BY p.Itemid1, p.Itemid2 HAVING COUNT(*) >= "+count);
		
			insert.executeQuery("INSERT INTO R2 (SELECT p.transId, p.Itemid1, p.Itemid2"
                    + " FROM R2final p, C2 q WHERE p.Itemid1 = q.itemid1 AND p.Itemid2 = q.itemid2)");
			insert.executeQuery("Insert into outputNames select c2.count1,i1.itemname,i2.itemname from items i1,items i2,c2  where c2.itemid1=i1.itemid and c2.itemid2=i2.itemid ");
			
			query="";	
			
			String tempAppend="";
			String tempAppend1="";
			String tempAppend2="";
			String tempAppend3="";
			String tempAppend4="";
			
			//for loop to output all frequent item sets.
			for(int lint=1;lint<=Double.parseDouble (task3Size);lint++)
			{
				if(lint==1)//for single item
				{
					task1Query = task1Query+count;
					dummyTask1 = stmt.executeQuery(task1Query);
					while (dummyTask1.next())
					{
						String temp="{"+dummyTask1.getString(1) +"},s="+dummyTask1.getString(2)+"%";
						System.out.println(temp);
						pwTask3.println(temp+newline);
					}
				}
				else if(lint == 2)//for pair
				{
					task2Query = task2Query+count;
					dummyTask2 = stmt.executeQuery(task2Query);
					while (dummyTask2.next())
					{
						String temp = "{"+dummyTask2.getString(1)+","+dummyTask2.getString(2) +"},s="+dummyTask2.getString(3)+"%";//,s="+rset.getString(2).substring(0,1)+"%"
						System.out.println(temp);
						pwTask3.println(temp+newline);
					}
				}
				else
				{
					 tempAppend="";
					 tempAppend1="";
					 tempAppend2="";
					 tempAppend3="";
					 tempAppend4="";				
					
					delete.executeQuery("delete from r2final");
					alter.executeQuery("alter table R2final add itemid"+lint+" int ");
					
					query = "INSERT INTO R2final "+
							"SELECT p.*, q.Itemid "+
							"FROM R2 p, Trans q "+
							"WHERE p.transId = q.transId AND "+
							"q.Itemid > p.Itemid"+(lint-1);
					insert.executeQuery(query);
					
					//delete tuples and add a column for next iteration
					delete.executeQuery("delete from c2");
					alter.executeQuery("alter table c2 add itemid"+lint+" int ");
					
					for(int i=1;i<=lint;i++)
					{
						tempAppend = tempAppend + "p.itemid"+i+",";
					}
					tempAppend = tempAppend.substring(0,tempAppend.length()-1);
					query = "INSERT INTO C2 "+
							"SELECT COUNT(*),"+tempAppend+" "+
							"FROM R2final p "+
							"GROUP BY "+tempAppend+" "+
							"HAVING COUNT(*) >= "+count;
					insert.executeQuery(query);
					
					//delete tuples and add a column for next iteration
					delete.executeQuery("delete from outputNames");
					alter.executeQuery("alter table outputNames add itemname"+lint+" varchar(50) ");
					
					for(int i=1;i<=lint;i++)
					{
						tempAppend1 = tempAppend1 + "i"+i+".itemname,";
						tempAppend2 = tempAppend2 + " items i"+i+",";
						tempAppend3 = tempAppend3 + "c2.itemid"+i+"=i"+i+".itemid and ";
					}
					tempAppend1 = tempAppend1.substring(0,tempAppend1.length()-1);
					tempAppend3 = tempAppend3.substring(0,tempAppend3.length()-4);
					
					
					//Display Final names of the Frequent Item sets
					query = "Insert into outputNames select c2.count1, "+tempAppend1+" from "+tempAppend2+" c2 where "+tempAppend3;
				//	"Insert into outputNames select c2.count1,i1.itemname,i2.itemname from items i1,items i2,c2  where c2.itemid1=i1.itemid and c2.itemid2=i2.itemid "
					insert.executeQuery(query)	;	
				    ResultSet output1 = stmt.executeQuery("select * from outputNames");
				    
				    while(output1.next())
				    {
				    	System.out.print("{");
				    	pwTask3.print("{");
				    	String temp="";
				    	for(int cnt=2;cnt<=lint+1;cnt++)
				    	{
				    	    temp = temp + output1.getString(cnt)+",";
				    		
				    	}
				    	temp = temp.substring(0, temp.length()-1);
				    	System.out.print(temp);
			    		pwTask3.print(temp);
				    	String temp1= "},s="+Double.toString( (output1.getDouble(1) *100/1731))+"%";
				    	System.out.println(temp1);
				    	pwTask3.println(temp1+newline);
				    }
					
				  //delete tuples and add a column for next iteration
				    delete.executeQuery("delete from r2");
					alter.executeQuery("alter table r2 add itemid"+lint+" int ");
				    
					for(int i=1;i<=lint;i++)
					{
						tempAppend4 = tempAppend4 + "p.itemid"+i+" = q.itemid"+i+" and ";
					}
					tempAppend4 = tempAppend4.substring(0, tempAppend4.length()-4);
					tempAppend = tempAppend.substring(0,tempAppend.length()-1);
					query ="INSERT INTO R2 "+
							"SELECT p.* FROM R2final p, C2 q WHERE "+
							tempAppend4;
					insert.executeQuery(query);
				}		
			}	
			
				
//444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444
			
			/*
			 * ***********TASK 4**************
			 * In Task 4, this task is about mining association rules. Given a specific support level,
				a specific confidence and the maximum number of items in an association rule, your
				program would then first and all frequent itemsets at the user-specifed size, and then
				use those FIs to generate all possible association rules.
			 */
			System.out.println("   ");
			System.out.println("***********TASK 4**************");
			
			System.out.println("Support " + task4Support+"% Confidence "+task4Confidence +"% Size "+ task4Size);
			
			
			double confidence = Double.parseDouble(task4Confidence);
			
			//Temporary table R2final To collect all possible combinations derived from R2
			try {
				create.executeQuery("Drop Table R2final");
				create.executeQuery("create table R2final(transid int,itemid1 int,itemid2 int) ");
			} catch (Exception e) {
				create.executeQuery("create table R2final(transid int,itemid1 int,itemid2 int) ");
			//	e.printStackTrace();
			}
			
			//Temporary table C2 To retrieve all possible tuples for input size and support
			try {
				create.executeQuery("Drop Table c2");
				create.executeQuery("create table c2(count1 int,itemid1 int,itemid2 int)");
			} catch (Exception e) {
				create.executeQuery("create table c2(count1 int,itemid1 int,itemid2 int)");
			//	e.printStackTrace();
			}
			
			//Temporary table C1 To retrieve all possible tuples for input size and support for single item
			try {
				create.executeQuery("Drop Table c1");
				create.executeQuery("create table c1(count1 int,itemid1 int)");
			} catch (Exception e) {
				create.executeQuery("create table c1(count1 int,itemid1 int)");
			//	e.printStackTrace();
			}
			
			try {
				create.executeQuery("Drop Table R2");
				create.executeQuery("create table R2(transid int,itemid1 int,itemid2 int)");
			} catch (Exception e) {
				create.executeQuery("create table R2(transid int,itemid1 int,itemid2 int)");
			//	e.printStackTrace();
			}
			
			try {
				create.executeQuery("Drop Table outputNames");
				create.executeQuery("CREATE TABLE outputNames (count INT, ItemName1 varchar(50),ItemName2 varchar(50) )");
			} catch (Exception e) {
				create.executeQuery("CREATE TABLE outputNames (count INT, ItemName1 varchar(50),ItemName2 varchar(50) )");
			//	e.printStackTrace();
			}	
			
			
			count = distinctCount * Double.parseDouble(task4Support) / 100;
			insert.executeQuery("INSERT INTO R2final SELECT p.transId, p.ItemId, q.ItemId FROM trans  p, trans  q WHERE p.transId = q.transId AND q.Itemid != p.Itemid ");
			insert.executeQuery("INSERT INTO C2 SELECT COUNT(*),p.Itemid1, p.Itemid2 FROM R2final  p GROUP BY p.Itemid1, p.Itemid2 HAVING COUNT(*) >= "+count);
		
			insert.executeQuery("INSERT INTO R2 (SELECT p.transId, p.Itemid1, p.Itemid2"
                    + " FROM R2final p, C2 q WHERE p.Itemid1 = q.itemid1 AND p.Itemid2 = q.itemid2)");
			insert.executeQuery("Insert into outputNames select c2.count1,i1.itemname,i2.itemname from items i1,items i2,c2  where c2.itemid1=i1.itemid and c2.itemid2=i2.itemid ");
			
			query="";	
			
			 tempAppend="";
			 tempAppend1="";
			 tempAppend2="";
			 tempAppend3="";
			 tempAppend4="";
			 String tempAppend5="";
			
			//for loop to output all frequent item sets.
			//generation all possible permutations which are relative to the size in the input
			for(int lint=1;lint<=Double.parseDouble(task4Size);lint++)
			{
				if(lint==1)//for single item
				{
					task1Query = "Insert into C1 ( " + "select count(*),items.itemid from items, trans where items.itemid=trans.itemid group by items.itemname, items.itemid having count(*)>= "
							+count+")";				
					dummyTask1 = insert.executeQuery(task1Query);

				}
				else if(lint == 2)//for pair
				{

				}
				else
				{
					 tempAppend="";
					 tempAppend1="";
					 tempAppend2="";
					 tempAppend3="";
					 tempAppend4="";		
					 tempAppend5="";
					
					delete.executeQuery("delete from r2final");
					alter.executeQuery("alter table R2final add itemid"+lint+" int ");
					
					for(int m=1;m<=lint-1;m++)
					{
						tempAppend5 = tempAppend5 + " q.Itemid != p.Itemid"+(m)+" and";
					}
					tempAppend5 = tempAppend5.substring(0,tempAppend5.length()-4);
					query = "INSERT INTO R2final "+
							"SELECT p.*, q.Itemid "+
							"FROM R2 p, Trans q "+
							"WHERE p.transId = q.transId AND "+
							tempAppend5;				
					
					insert.executeQuery(query);
					
					//Generation of different C3 to C<task4Size> tables to collect all association rules.
					String append="";
					try {
						create.executeQuery("Drop Table c"+lint);
						 append="";
						for(int k=1;k<=lint;k++)
						{
							append = append + " itemid"+k+" int,";
						}
						append = append.substring(0, append.length()-1);
						create.executeQuery("create table c"+lint+" (count1 int,"+append+")");
					} catch (Exception e) {
						    append="";
							for(int k=1;k<=lint;k++)
							{
								append = append + " itemid"+k+" int,";
							}
							append = append.substring(0, append.length()-1);
							create.executeQuery("create table c"+lint+" (count1 int,"+append+")");
					//	e.printStackTrace();
					}
					
					for(int i=1;i<=lint;i++)
					{
						tempAppend = tempAppend + "p.itemid"+i+",";
					}
					tempAppend = tempAppend.substring(0,tempAppend.length()-1);
					query = "INSERT INTO C"+lint+
							" SELECT COUNT(*),"+tempAppend+" "+
							"FROM R2final p "+
							"GROUP BY "+tempAppend+" "+
							"HAVING COUNT(*) >= "+count;
					insert.executeQuery(query);			

				    delete.executeQuery("delete from r2");
					alter.executeQuery("alter table r2 add itemid"+lint+" int ");
				    
					for(int i=1;i<=lint;i++)
					{
						tempAppend4 = tempAppend4 + "p.itemid"+i+" = q.itemid"+i+" and ";
					}
					tempAppend4 = tempAppend4.substring(0, tempAppend4.length()-4);
					tempAppend = tempAppend.substring(0,tempAppend.length()-1);
					query ="INSERT INTO R2 "+
							"SELECT p.* FROM R2final p, C"+lint+" q WHERE "+
							tempAppend4;
					insert.executeQuery(query);
				}		
			}				
			
			//finding association rules by generation all possible permutations
			
			for(int lint=1;lint<= Double.parseDouble(task4Size);lint++)
			{
				
				//Subquery Strings to build the entire query
				String append="";
				String select ="";
				String where="";
				String from ="";
				String condition1="";
				String condition2="";
				String condition3="";
				String supportCondition="";
				String confidenceCondition="";
				
				//Creation of output tables after joining of C2--Cn tables with Items table to get Item names
				try {
					create.executeQuery("Drop Table Outputc"+lint);
					 append="";
					for(int k=1;k<=lint;k++)
					{
						append = append + " itemid"+k+" int,";
					}
					//append = append.substring(0, append.length()-1);
					create.executeQuery("create table Outputc"+lint+" (leftItems int, "+append+" 	support float,confidence float"+")");
				} catch (Exception e) {
					 append="";
						for(int k=1;k<=lint;k++)
						{
							append = append + " itemid"+k+" int,";
						}
						//append = append.substring(0, append.length()-1);
						create.executeQuery("create table Outputc"+lint+" (leftItems int, "+append+" 	support float,confidence float"+")");
				}
				
				select="";			
				
				for(int mint=1;mint<lint;mint++)
				{
					select = "select distinct "+ mint +" as leftItems,";
					append ="";
					for(int k=1;k<=lint;k++)
					{
						append = append + " C"+lint+".itemid"+k+", ";
					}
					
					append = append + " C"+lint+".count1 as support,"+" C"+lint+".count1/"+"c"+mint+".count1 as confidence ";
					select = select + append;
					from="";
					from = from + " from C"+lint+" , C"+mint + " where ";
					
					condition1="";
					for(int k=1;k<=mint;k++)
					{
						condition1 = condition1 + " C"+lint+".itemid"+k+" =  "+ " C"+mint+".itemid"+k+" and ";
					}
					//condition1 = condition1.substring(0,condition1.length()-4);
					
					condition2="";
					for(int k=2;k<=mint;k++)
					{
						condition2 = condition2 + " c"+mint+".itemid"+(k-1)+" < "+" c"+mint+".itemid"+(k)+" and ";
					}
					condition3="";
					for(int k=mint+2;k<=lint;k++)
					{
						condition3 = condition3 + " c"+lint+".itemid"+(k-1)+" < "+" c"+lint+".itemid"+(k)+" and ";
					}
					
					supportCondition="";
					supportCondition = supportCondition + " C"+lint+".count1 >= "+count+" and ";
					confidenceCondition="";
					confidenceCondition = confidenceCondition + " C"+lint+".count1 / "+" C"+mint+".count1"+" >= " + (Double.parseDouble(task4Confidence)/100) ;
					
					select = select + from + condition1+condition2+condition3+supportCondition+confidenceCondition;
					
					query="";
					query = query + "Insert into Outputc"+lint+" "+select;
					
					insert.executeQuery(query);
					
				}			
			}
			
			//Print Association Rules
			
			//Join the OutputC2 to OutputCn tables with items to get item names
			for(int lint=2;lint<= Double.parseDouble(task4Size);lint++)
			{
				String select="";
				String from="";
				String where="";
				select = "select outputc"+lint+".leftItems,";
					
				String appendItem="";
				for(int k=1;k<=lint;k++)
				{
					appendItem = appendItem + " i"+k+".itemname,";
				}
				select = select + appendItem + " outputc"+lint+".support, outputc"+lint+".confidence ";
				from = " from outputc"+lint+",";	
				
				appendItem="";
				for(int k=1;k<=lint;k++)
				{
					appendItem = appendItem + " items i"+k+",";
				}
				appendItem = appendItem.substring(0,appendItem.length()-1);
				from = from + appendItem + " where ";
				
				where = "";
				for(int k=1;k<=lint;k++)
				{
					where = where + " i"+k+".itemid = outputc"+lint+".itemid"+k+" and ";
				}
				where = where.substring(0,where.length()-4);
				query="";
				query = query + select+from+where;
				ResultSet outputTest = stmt.executeQuery(query);
				
				while(outputTest.next())
				{
					//find the leftsize and segregate items in left and right side to get association rule.
					int leftSize = Integer.parseInt(outputTest.getString(1));
					String print="";
					String testPrint="";
					String testPrint1="";
					for(int i=1;i<=leftSize;i++)
					{
						testPrint = testPrint + outputTest.getString(i+1)+",";
					}
					testPrint = testPrint.substring(0, testPrint.length()-1);
					for(int j=leftSize+1;j<=lint;j++)
					{
						testPrint1 = testPrint1 + outputTest.getString(j+1)+",";
					}
					testPrint1 = testPrint1.substring(0, testPrint1.length()-1);
					double support1 = Double.parseDouble( outputTest.getString(lint+2))*100/distinctCount;
					double confidence1 = Double.parseDouble(outputTest.getString(lint+3))*100;
					print="{{"+testPrint+"} --> {"+testPrint1+"}},s="+support1+"%, c="+confidence1+"%";
									
					System.out.println(print);
					pwTask4.println(print+newline);
				}
				
			}
			
			//Calculation of Execution Time
			long stop = System.currentTimeMillis();
			System.out.println("Execution time of Program: "+(stop-start) + " Miliseconds");
			
			////THE END//////////////////////////		
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}		
		finally
		{
			conn.close(); // ** IMPORTANT : Close connections when done **
		}		
		
	}
}
