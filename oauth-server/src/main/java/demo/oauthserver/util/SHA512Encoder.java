package demo.oauthserver.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.password.PasswordEncoder;

public class SHA512Encoder implements PasswordEncoder {
	private static final char[] hexChar = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	@Override
	public String encode(CharSequence rawPassword) {
		try {
            return getSHA512Hash(rawPassword.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            return encodedPassword.equals(getSHA512Hash(rawPassword.toString()));
        } catch (Exception e) {
        }
        return false;
	}
	
	private String getSHA512Hash(String message) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return getHash(message, "SHA-512");
	}
	
	private String getHash(String message, String algorithm)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (message == null) {
			throw new IllegalArgumentException("message is null");
		}
		
		byte[] messageBytes = message.getBytes("UTF-8");
		MessageDigest md = MessageDigest.getInstance(algorithm);
		byte[] data = md.digest(messageBytes);

		return conventBytesToHexString(data);
	}
	
	/**
     * Convert bytes to hex string.
     * 
     * @param data is the bytes.
     * @return the hex string representation of bytes.
     */
	private String conventBytesToHexString(byte[] data) {
        return convertBytesToHexString(data, 0, data.length);
    }

    // 
    /**
     * Convert bytes to hex string value (using Big-Endian rule).
     * 
     * @param data is the bytes.
     * @param offset is the offset.
     * @param length is the length.
     * @return the hex string representation of bytes.
     */
	private String convertBytesToHexString(byte[] data, int offset,
            int length) {
        StringBuffer sBuf = new StringBuffer();
        // System.err.println("converBytesToHexString");
        for (int i = offset; i < length; i++) {
            /*
             * System.err.println("data[" + i + "] = " + data[i]);
             * System.err.println(hexChar[(data[i] >> 4) & 0xf]);
             * System.err.println(hexChar[data[i] & 0xf]);
             */
            sBuf.append(hexChar[(data[i] >> 4) & 0xf]);
            sBuf.append(hexChar[data[i] & 0xf]);
            /*
             * sBuf.append(hexChar[(data[i] >> 4) & 0xf]);
             * sBuf.append(hexChar[data[i]& 0xf]);
             */
            // sBuf.append(Integer.toHexString(data[i]));
        }
        return sBuf.toString();
    }

}
