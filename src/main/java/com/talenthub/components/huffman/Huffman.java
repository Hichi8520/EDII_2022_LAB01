package main.java.com.talenthub.components.huffman;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {
	
	private List<Character> charList;
	private List<Integer> charFreq;
	private Map<Character, String> charPrefixMap; // Encoding map
	private Map<String, Character> prefixCharMap; // Decoding map
	private String delimiter = "¿";
	
	private String encodedText;
	
	private String decodedText;
	private String textToDecode;
	private int extraZeros;
	
	public Huffman() {
		init();
	}
	
	private void init() {
		this.charList = new ArrayList<Character>();
		this.charFreq = new ArrayList<Integer>();
		this.charPrefixMap = new HashMap<Character, String>();
		this.prefixCharMap = new HashMap<String, Character>();
		this.encodedText = "";
		this.decodedText = "";
		this.textToDecode = "";
		this.extraZeros = 0;
	}
	
	public String encode(String textToEncode) {
		init();
		getFrecuenciesFromText(textToEncode); // Set charList & charFreq
	    
	    nodesProcess(true);
	      
	    textToBytes(textToEncode);
	    appendTable();
	    
	    return encodedText;
	}
	
	public String decode(String encodedText) {
		init();
		getFrecuenciesFromTable(encodedText); // Set charList & charFreq
	    
	    nodesProcess(false);
	      
	    bytesToText();
	    
	    return decodedText;
	}
	
	@SuppressWarnings("removal")
	private void getFrecuenciesFromText(String s) {
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
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
		
		for (Map.Entry<Character, Integer> entry : map.entrySet()) {
			charList.add(entry.getKey());
			charFreq.add(entry.getValue());
	    }
	}
	
	private void getFrecuenciesFromTable(String s) {
		String[] items = s.split(delimiter);
		this.extraZeros = Integer.parseInt(items[items.length - 2]);
		this.textToDecode = items[items.length - 1];
		
		for(int i=0; i < items.length - 2; i++) {
			charList.add(items[i].charAt(0));
			i++;
			charFreq.add(Integer.parseInt(items[i]));
		}
	}
	
	private void nodesProcess(boolean isEncoding) {
		int n = charList.size();
		
		PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(n, new HuffmanComparator());
	    
	    for (int i = 0; i < n; i++) {
	        HuffmanNode huffNode = new HuffmanNode();

	        huffNode.setC(charList.get(i));
	        huffNode.setItem(charFreq.get(i));

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
	    //System.out.println(" Char | Huffman code ");
	    //System.out.println("--------------------");
	    getPrefixCode(root, "", isEncoding);
	}
	
	private void getPrefixCode(HuffmanNode root, String s, boolean isEncoding) {
		if (root.getLeft() == null && root.getRight() == null) { // leaf node
			
			//System.out.println(root.getC() + "   |  " + s);
			if(isEncoding)
				charPrefixMap.put(root.getC(), s);
			else
				prefixCharMap.put(s, root.getC());

		    return;
		}
		getPrefixCode(root.getLeft(), s + "0", isEncoding);
		getPrefixCode(root.getRight(), s + "1", isEncoding);
	}
	
	private void textToBytes(String textToEncode) {
		// Form the prefix string of text
		String bitString = "";
		
		for(int i=0; i < textToEncode.length(); i++) {
			bitString += charPrefixMap.get(textToEncode.charAt(i));
		}
		//System.out.println("Cadena como bits: " + bitString);
		
		
		// Read 7 by 7 bits to obtain the bytes and form each character
		byte b;
		int extraZeros = 0;
		List<Byte> byteList = new ArrayList<Byte>();
		
		while(!bitString.isEmpty()) {
			if(bitString.length() > 7) {
				b = Byte.parseByte(bitString.substring(0, 7), 2);
				bitString = bitString.substring(7);
				
			} else if (bitString.length() == 7) {
				b = Byte.parseByte(bitString, 2);
				bitString = "";
				
			} else {
				extraZeros = 7 - bitString.length();
				//String formatted = String.format("%07s", bitString);
				b = Byte.parseByte(bitString, 2);
				bitString = "";
			}
			byteList.add(b);
		}
		
		byte[] byteArr = new byte[byteList.size()];
		for(int i = 0; i < byteList.size(); i++) byteArr[i] = byteList.get(i);
		
		try {
			encodedText = String.valueOf(extraZeros)
					.concat(delimiter)
					.concat(new String(byteArr, "UTF-8"));
			//System.out.println("EncodedText: " + encodedText);
			//System.out.println("EncodedTextLength: " + encodedText.length());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private void bytesToText() {
		String bitString = "";
		for(int i=0; i < textToDecode.length() - 1; i++) {
			byte b = (byte) textToDecode.charAt(i);
			String s1 = String.format("%7s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
			bitString = bitString + s1;
		}
		// Last character
		byte b = (byte) textToDecode.charAt(textToDecode.length() - 1);
		String s1 = String.format("%7s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
		if(extraZeros > 0)
			bitString = bitString + s1.substring(extraZeros);
		else
			bitString = bitString + s1;
		
		//System.out.println("Cadena como bits: " + bitString);
		
		
		String readBits = "";
		for(int j=0; j < bitString.length(); j++) {
			readBits += bitString.charAt(j);
			Character foundChar = prefixCharMap.get(readBits);
			if(foundChar != null) {
				decodedText += prefixCharMap.get(readBits);
				readBits = "";
			}
			
		}
		//System.out.println("DecodedText: " + decodedText);
	}
	
	private void appendTable() {
		String table = "";
		for(int i=0; i < charList.size(); i++) {
			table = new StringBuilder(table)
					.append(charList.get(i))
					.append(delimiter)
					.append(charFreq.get(i))
					.append(delimiter).toString();
		}
		encodedText = table.concat(encodedText);
	}
}
