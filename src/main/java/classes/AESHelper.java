package classes;

import utils.Constants;

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

    private static final SecretKeySpec SECRET_KEY_SPEC = new SecretKeySpec(Constants.KEY.getBytes(), "AES");
    private static final IvParameterSpec IV_PARAMETER_SPEC = new IvParameterSpec(Constants.IV.getBytes());

    public static String encode(final String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY_SPEC, IV_PARAMETER_SPEC);

        return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes()));
    }

    public static String decode(final String text) throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY_SPEC, IV_PARAMETER_SPEC);

        final byte[] decodeText = Base64.getDecoder().decode(text.getBytes());
        return new String(cipher.doFinal(decodeText));
    }
}

