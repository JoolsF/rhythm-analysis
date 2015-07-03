package suffixTree;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;



/**
 * @author Julian Fenner
 * 
 * My implementation / adaptation of Ukkonens' suffix tree construction algorithm 
 * https://www.cs.helsinki.fi/u/ukkonen
 * http://www.geeksforgeeks.org/ukkonens-suffix-tree-construction-part-1/
 */

public class SuffixTree {
	
	private Node root;
	
	public SuffixTree(String str){
		root = new NodeRoot();
		addString(str);
	}
	
	public void addString(String str){		
		for(int i = 0; i < str.length(); i++){			
			for(int index = 0; index <= i; index++){
System.out.println("NODE TO ADD: " + str.substring(index, i+1) + "(" +index+")");
				root.addString(str.substring(index, i+1), index);
				
			}	
			//$ added at the end of each substring iteration
			root.addString("$", i+1);
System.out.println("$ " + (i+1));
System.out.println("%% END OF SUBSTRING %%");
System.out.println();
System.out.println();
		}	
	}
	
	public Node getTree(){
		return this.root;
	}
	
	public static void main(String[] args){
		// CHECK MORE DEPTH 2 TREES THEN TRY DEPTH 3. 
		
		// WORKING UP TO AB11AA
		// WORKING 112233
		//
		// WORKING AB11001
		// WORKING AB110011 <- depth 3 check debug output at 11(6).
		// 	case where 10011(2) and 0011(3) and can't move 1 up as you 
		// then have 2 nodes starting with 0 therefore need to split edge
		// NOT WORKING AB110011
		SuffixTree test = new SuffixTree("AB110011"); //trace construction debug output carefully
		test.getTree().printTree();
		
		
		Map<String, List<Integer>> nodeMap = test.getTree().nodesToMap(new TreeMap<String, List<Integer>>());
		for(Entry<String, List<Integer>> value: nodeMap.entrySet()){	
    		System.out.println(value.getKey() + ": " + value.getValue());
    	}
		
		
	}
	
}
