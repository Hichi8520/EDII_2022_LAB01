package main.java.com.talenthub.components.trans;

public class Trans {

	private int[] p;
	
	public Trans() {
		
	}
	
	public String cipher(String key, String textToEncrypt) {
		
		setPatternFromKey(key);
		return new String(cipherProcess(textToEncrypt.getBytes()));
	}
	
	public String decipher(String key, String textToDecrypt) {
		
		setPatternFromKey(key);
		return new String(decipherProcess(textToDecrypt.getBytes()));
	}
	
	private void setPatternFromKey(String key) {
		
		int[] p = {5, 2, 0, 4, 1, 3};
		
		setKey(p);
	}
	
	private void setKey(int[] pattern){		
		int[] a = new int[pattern.length];
		
		int i = 0;
		boolean b = true;
		
		while ((i < pattern.length) && b) {
			if ((pattern[i] < pattern.length) && (pattern[i] >= 0)) a[pattern[i]] = pattern[i];
			else b = false;
			i++;
		}
		
		i = 0;
		
		while ((i < pattern.length) && b) {
			b = (a[i] == i);
			i++;
		}
		
		if (!b) throw new IllegalArgumentException();
		
		p = pattern;
	}

	private byte[] cipherProcess(byte[] m) {
		int l = m.length;
		int n = p.length;
		
		byte[] c = new byte[l];
		
		int k = 0;
		
		for (int i = 0; i < n; i++)
			for (int j = p[i]; j < l; j += n) c[k++] = m[j];
		
		return c;
	}

	private byte[] decipherProcess(byte[] c) {
		int l = c.length;
		int n = p.length;
		
		byte[] m = new byte[l];
		
		int k = 0;
		
		for (int i = 0; i < n; i++)
			for (int j = p[i]; j < l; j += n) m[j] = c[k++];
		
		return m;
	}
}
