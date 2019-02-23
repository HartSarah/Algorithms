import java.math.BigInteger;
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This algorithm creates a random private key that can be used for creating
 * cryptocurrency wallets. The final private key is in base58 (bitcoin).
 * 
 * @author Sarah Hart
 */

public class PrivateKey {
	public static void main(String args[]) {
		String eightyString = "80" + HexaString(); // adding "80" to front of string
		System.out.println("eightyString: " + eightyString);

		String sha = "";
		String doubleHash = "";
		String endDigits = "";

		try {
			sha = sha256(eightyString); // first hash
			System.out.println("first sha256: " + sha);

			doubleHash = sha256(sha); // second hash
			System.out.println("second sha256 (doubleHash): " + doubleHash);

		} catch (NoSuchAlgorithmException e) {
		}

		for (int x = 0; x < 8; x++) // getting first 8 digits of double hash
		{
			String digits = String.valueOf(doubleHash.charAt(x));
			endDigits += digits;
		}

		System.out.println("End digits: " + endDigits);

		String baseConvert = eightyString + endDigits; // adding 8 digits to end of 80 string
		System.out.println(baseConvert);

		byte[] bytearraynumber = hexStringToByteArray(baseConvert);
		BigInteger bignumber = new BigInteger(1, bytearraynumber);
		System.out.println("converted to decimal: " + bignumber); // changed to decimal

		BigInteger fiftyEight = new BigInteger("58");
		BigInteger zero = new BigInteger("0");
		BigInteger remainder = new BigInteger("0");

		String reverseKey = " ";
		String finalKey = " ";

		while (bignumber.compareTo(zero) != 0) {
			String alphabet = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz"; // base 58

			remainder = bignumber.mod(fiftyEight); // keeping remainder
			bignumber = bignumber.divide(fiftyEight); // dividing bignumber

			int position = remainder.intValue(); // remainder to int

			String key = String.valueOf(alphabet.charAt(position)); // getting base 58 value

			reverseKey += key; // adding to string (is the reverse of private key)
		}

		for (int x = reverseKey.length() - 1; x > 0; x--) // reversing reverseKey to private key
		{
			String add = String.valueOf(reverseKey.charAt(x));
			finalKey += add;
		}

		System.out.println("Private Key: " + finalKey); // Private key
	}

	public static String HexaString() // creating a random hexadecimal string with 64 bits
	{
		String[] hexArray = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
		Random randHexa = new Random();

		String ecdsa = "";

		for (int x = 0; x < 64; x++) {
			int rnd = randHexa.nextInt(hexArray.length); // random num b/w 0 and 15
			String arrayValue = hexArray[rnd]; // using rand num to get value in array

			String addOn = arrayValue;
			ecdsa += addOn; // concat string
		}
		System.out.println("ecdsa: " + ecdsa); // printing to see if works
		return ecdsa;
	}

	static String sha256(String input) throws NoSuchAlgorithmException // hashing code
	{
		byte[] in = hexStringToByteArray(input);
		MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
		byte[] result = mDigest.digest(in);
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}

	public static byte[] hexStringToByteArray(String s) // changing the Hexadecimal string to a Byte array
	{
		int len = s.length();
		byte[] data = new byte[len / 2];

		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}

		return data;
	}
}