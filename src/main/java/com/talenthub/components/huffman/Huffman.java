package main.java.com.talenthub.components.huffman;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman {
	
	private 
	
	private void printCode(HuffmanNode root, String s) {
		if (root.getLeft() == null && root.getRight() == null && Character.isLetter(root.getC())) { // leaf node
			
			System.out.println(root.getC() + "   |  " + s);

		    return;
		}
		printCode(root.getLeft(), s + "0");
	    printCode(root.getRight(), s + "1");
	}
	
	public void encode() {
		int n = 8;
	    char[] charArray = { 'd', 'a', 'b', 'c', 'e', 'h', 'f', 'g' };
	    int[] charfreq = { 9, 4, 4, 7, 1, 1, 1, 5 };
	    
	    PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(n, new HuffmanComparator());
	    
	    for (int i = 0; i < n; i++) {
	        HuffmanNode huffNode = new HuffmanNode();

	        huffNode.setC(charArray[i]);
	        huffNode.setItem(charfreq[i]);

	        huffNode.setLeft(null);
	        huffNode.setRight(null);

	        q.add(huffNode);
	    }
	    
	    HuffmanNode root = null;
	    
	    while (q.size() > 1) {

	        HuffmanNode x = q.peek();
	        q.poll();

	        HuffmanNode y = q.peek();
	        q.poll();

	        HuffmanNode f = new HuffmanNode();

	        f.setItem(x.getItem() + y.getItem());
	        f.setC('-');
	        f.setLeft(x);
	        f.setRight(y);
	        root = f;

	        q.add(f);
	      }
	      System.out.println(" Char | Huffman code ");
	      System.out.println("--------------------");
	      printCode(root, "");
	}
	
	private void getFrecuencies() {
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		String s = "aasjjikkk";
		for (int i = 0; i < s.length(); i++) {
		    char c = s.charAt(i);
		    Integer val = map.get(c);
		    if (val != null) {
		        map.put(c, new Integer(val + 1));
		    }
		    else {
		       map.put(c, 1);
		   }
		}
	}
}
