import classes.AESHelper;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class AESHelperTest {

    @Test
    public void encode() {
        try {
            String sourceText = "test123";
            System.out.println("sourceText: " + sourceText);

            String cypheredText = AESHelper.encode(sourceText);
            System.out.println("cypheredText: " + cypheredText);

            String decypheredText = AESHelper.decode(cypheredText);
            System.out.println("decypheredText: " + decypheredText);

            assertEquals(sourceText, decypheredText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}