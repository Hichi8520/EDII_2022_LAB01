package main.java.com.talenthub.components.rsa;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSA {
	
	private int p, q, n, z, d = 0, e, i;
	private BigInteger N, C;
	private List<Double> encryptedList;
	private List<BigInteger> decryptedList;

	public RSA() {
		
	}
	
	private void init() {
		p = 0;
		q = 0;
		n = 0;
		z = 0;
		d = 0;
		e = 0;
		i = 0;
		N = null;
		C = null;
		encryptedList = new ArrayList<Double>();
		decryptedList = new ArrayList<BigInteger>();
	}
	
	public String encrypt(String textToEncrypt) {
		init();
		
		List<Integer> intList = new ArrayList<Integer>();
		for (char c : textToEncrypt.toCharArray()) {
			intList.add((int) ((byte) c));
		}
		
		getKeys();
		encryptText(intList);
		
		return formatEncryptedText();
	}
	
	public String decrypt(String textToDecrypt) {
		init();
		
		List<BigInteger> bigintList = new ArrayList<BigInteger>();
		for (char c : textToDecrypt.toCharArray()) {
			byte bt = (byte) c;
			bigintList.add(BigInteger.valueOf((byte) c));
		}
		
		getKeys();
		decryptText(bigintList);
		
		return formatDecryptedText();
	}
	
	private void getKeys() {
		
	    //double c;

	    // 1st prime number p
	    p = 11;

	    // 2nd prime number q
	    q = 13;
	    n = p * q;
	    z = (p - 1) * (q - 1);
	    System.out.println("the value of z = " + z);

	    for (e = 2; e < z; e++) {

	        // e is for public key exponent
	        if (mcd(e, z) == 1) {
	            break;
	        }
	    }
	    System.out.println("the value of e = " + e);
	    for (i = 0; i <= 9; i++) {
	        int x = 1 + (i * z);

	        // d is for private key exponent
	        if (x % e == 0) {
	            d = x / e;
	            break;
	        }
	    }
	    System.out.println("the value of d = " + d);
	    //c = (Math.pow(msg, e)) % n;
	    //System.out.println("Encrypted message is : " + c);

	    // converting int value of n to BigInteger
	    N = BigInteger.valueOf(n);

	    // converting float value of c to BigInteger
	    //C = BigDecimal.valueOf(c).toBigInteger();
	    //msgback = (C.pow(d)).mod(N);
	    //System.out.println("Decrypted message is : "
	    //                   + msgback);
	}
	
	private void encryptText(List<Integer> intList) {
		for (Integer item : intList) {
			encryptedList.add((Math.pow(item, e)) % n);
		}
	}
	
	private void decryptText(List<BigInteger> bigintList) {
		for (BigInteger item : bigintList) {
			decryptedList.add((item.pow(d)).mod(N));
		}
	}
	
	private String formatEncryptedText() {
		String result = "";
		
		for (Double d : encryptedList) {
			byte bt = (byte)(double)d;
			result += (char) d.byteValue();
		}
		return result;
	}
	
	private String formatDecryptedText() {
		String result = "";
		
		for (BigInteger d : decryptedList) {
			result += (char) d.byteValue();
		}
		return result;
	}
	
	public void encrypt() {
		
		int p, q, n, z, d = 0, e, i;
		 
	    // The number to be encrypted and decrypted
	    int msg = 100;
	    double c;
	    BigInteger msgback;

	    // 1st prime number p
	    p = 17;

	    // 2nd prime number q
	    q = 19;
	    n = p * q;
	    z = (p - 1) * (q - 1);
	    System.out.println("the value of z = " + z);

	    for (e = 2; e < z; e++) {

	        // e is for public key exponent
	        if (mcd(e, z) == 1) {
	            break;
	        }
	    }
	    System.out.println("the value of e = " + e);
	    for (i = 0; i <= 9; i++) {
	        int x = 1 + (i * z);

	        // d is for private key exponent
	        if (x % e == 0) {
	            d = x / e;
	            break;
	        }
	    }
	    System.out.println("the value of d = " + d);
	    c = (Math.pow(msg, e)) % n;
	    System.out.println("Encrypted message is : " + c);

	    // converting int value of n to BigInteger
	    BigInteger N = BigInteger.valueOf(n);

	    // converting float value of c to BigInteger
	    BigInteger C = BigDecimal.valueOf(c).toBigInteger();
	    msgback = (C.pow(d)).mod(N);
	    System.out.println("Decrypted message is : "
	                       + msgback);
	}
	
	private int mcd(int e, int z)
    {
        if (e == 0)
            return z;
        else
            return mcd(z % e, e);
    }
}
