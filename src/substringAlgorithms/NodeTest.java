package substringAlgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Julian Fenner
 * Root can have zero, one or more children.
 *
 */

/* NODE SPLIT LOGIC
 *checking if the substring is a prefix of the child's substring e.g child's substring = 11 and substring = 1.
If yes then SPLIT	  
In other word the substring to add ends part of the way through the child's substring value.
 in this case the node must be split
 two cases one for lead and one for non leaf

CASE FOR LEAF NODE
checking if the substring is a prefix of the child's substring e.g child's substring = 11 and substring = 1 	
 This case turns a LEAF node where the substring to add is a prefix of this node's substring field value.  
 * i.e
 * 		   \
 * 			\
 * 			11 (2)  <- adding 1 to leaf
 * 
 * 			\
 *           \
 *            1
 * 			 / \
 * 			/   \
 * 		null(3)  1(2)
 * 		
 * 
 * 
 * This requires 
 * 1) var for matching prefix 
 * 2) var for rest of string i.e suffix
 * 3) adding a child leaf node which has the suffix as its value along with the index of the current node
 * 4) current node 'converted' into non-leaf node by deleting its index (not applicable now) 
 * 5) and setting its substring field to prefix
 * 6) finally we need to give the other index of the prefix 
 * 
 * 
 **/


public class NodeTest implements Node{
	//TO DO CHANGE TO NODE INTERFACE
	private List<Node> children;
	private String subStringField;
	private Integer subStringIndex;
	
	public NodeTest(String subStringToAdd, int subStringIndex){
		//TO DO, implement singleton pattern.  Only one root allowed
		this.subStringField = subStringToAdd;
		this.subStringIndex = subStringIndex;
		children = new ArrayList<Node>();
	}
	

	public void addSubString(String subStringToAdd, int subStringIndex){
		
		//BASE CASE 1 THERE ARE NO CHILDREN
//		NEED TO CREATE ROOT NODE WITH NO STRING FIELD AND CHILD WITH $(0)
		if(this.children.isEmpty()){
//			System.out.println("--->>String Updated");
//			System.out.println("sunStringToAdd " + subStringToAdd);
//			System.out.println("sunStringIndex " + subStringIndex);
			this.updateSubString(subStringToAdd, subStringIndex);
			//System.out.println("children.isEmpty");
		} else {
			for(Node child: children) {
//				System.out.println("Child = " + child.getSubString());
//				System.out.println("Substring = " + subStringToAdd);
//				System.out.println("");
				
				
				
				if (child.getSubString().equals("$")){ //<-- should be child?
//					System.out.println("child substring is equal to $");
//					System.out.println(subStringToAdd);
//					System.out.println();
				//	BASE CASE 2 
					//i.e. this is a leaf node without any value then you are at correct place and just need to update the field
					//children will be sorted so that "$" for any given list of children will always be at the end.
					//TO DO - UPDATE INDEX TOO FOR SAFETY?
					child.updateSubString(subStringToAdd, subStringIndex);
					return;	
					
				} else if(child.isAPrefixOf(subStringToAdd)){
					System.out.println("child is a prefix of substring");
					System.out.println("Child = " + child.getSubString());
					System.out.println("Substring = " +subStringToAdd);
					System.out.println();
					//recursive case. Travel further down the tree removing the part of the substring already matched
//BUG HERE TRY ADDING "ab" IS THIS RECURSIVE CASE CORRECT
					child.addSubString(removePrefix(subStringToAdd), subStringIndex);
				} else if(child.hasAPrefixOf(subStringToAdd)) {
//					System.out.println("child has a prefix of substring to add");
//					System.out.println("Child = " + child.getSubString());
//					System.out.println("Substring = " +subStringToAdd);
//					System.out.println();
					//TWO CASES HERE
					// 1 LEAF NODE
					// 2 NON LEAF NODE
					//
			
					//1
					String prefix =  subStringField.substring(0, subStringToAdd.length());
					//2
					String suffix = subStringField.substring(subStringToAdd.length(), subStringField.length());
					//3 LEAF NODE
					child.addChild(new NodeTest(suffix, this.subStringIndex));
					//4
					//TO DO - DEAL WITH NON LEAF NODES DIFFERENTLY NOT WITH NULL
					this.subStringIndex = null;
					//5
					subStringField = prefix;
					//6 string is null to represent the equivalent of $ terminating symbol
					//TO DO - check first if these is already a terminating symbol $ here
					children.add(new NodeTest("$", subStringIndex)); 
					//	
						
					return;		
			} else if (subStringIndex == children.size()){
//				System.out.println("at last child");
//				System.out.println("subStringToAdd");
				// We are checking the last element in the current substring and no other cases have been matched.
				// 
				children.add(new NodeTest(subStringToAdd, subStringIndex));
				return;
			}
		} // end of for loop
	    }
	}
	
	
	//TO DO - Find better method name
	@Override
	public String removePrefix(String subStringArg){
		System.out.println("x");
//TO DO FIX BUG HERE		
		System.out.println("LENGTH " + this.subStringField.length());
		//return subStringArg.substring(this.subStringField.length(), subStringArg.length() );
		return "";
	}
	

	@Override
	/*checks if this object's subString field is a prefix of param string.
	 * checks length as well to make sure they're not exact match
	*/
	public boolean isAPrefixOf(String string) {
		if(string.startsWith(this.subStringField) && string.length() > this.subStringField.length()){
			return true;
	    } else {
	    	return false;
	    }
	}
	
	@Override
	public boolean hasAPrefixOf(String string) {
		if(this.subStringField.startsWith(string) && this.subStringField.length() >  string.length()){
			return true;
	    } else {
	    	return false;
	    }
	}
	
	@Override
	public String getSubString() {
		return this.subStringField;
	}


	@Override
	public void updateSubString(String subString, int subStringIndex) {
		
		if(this.subStringField == "$") {
			System.out.println("$");
			this.subStringField = subString;
		} else {
		this.subStringField += subString;
		this.subStringIndex = subStringIndex;
		}
		Collections.sort(this.children);
		
	}


	@Override
	public List<Node> getChildren() {
		return this.children;
	}


	@Override
	/**
	 * Whenever child is added the collection is sorted to ensure any $ string are at the end
	 */
	public void addChild(Node node) {
		this.children.add(node);
		if(children.size() > 1) Collections.sort(this.children);
	}

	public void printTree (){
		
		if(! children.isEmpty())
			for(Node child: children){
				child.printTree();
		}
		System.out.println("Node field = " + this.subStringField);
		System.out.println("Node index = " + this.subStringIndex);
		System.out.println();
		
	}
	
}
