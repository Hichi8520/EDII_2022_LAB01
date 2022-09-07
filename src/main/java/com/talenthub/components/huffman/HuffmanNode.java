package main.java.com.talenthub.components.huffman;

public class HuffmanNode {
	private int item;
	private char c;
	private HuffmanNode left;
	private HuffmanNode right;
	
	public HuffmanNode() {
		super();
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public char getC() {
		return c;
	}

	public void setC(char c) {
		this.c = c;
	}

	public HuffmanNode getLeft() {
		return left;
	}

	public void setLeft(HuffmanNode left) {
		this.left = left;
	}

	public HuffmanNode getRight() {
		return right;
	}

	public void setRight(HuffmanNode right) {
		this.right = right;
	}
}
