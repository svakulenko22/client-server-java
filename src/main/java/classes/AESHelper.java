package classes;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESHelper {

    private static final String KEY = "HSGDHFNHGFDGFDGH";
    private static final String IV = "JHGDFGBDGFHYTFGD";

    public static String encode(final String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        final SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        final IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes());

        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encodedBytes = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encodedBytes);
    }

    public static String decode(final String text) throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        final SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        final IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes());

        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        final byte[] decodeText = Base64.getDecoder().decode(text.getBytes());
        return new String(cipher.doFinal(decodeText));
    }
}

