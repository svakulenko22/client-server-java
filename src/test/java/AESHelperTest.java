import classes.AESHelper;
import com.google.common.primitives.UnsignedLong;
import entity.Message;
import entity.Packet;
import org.junit.Test;


import static org.junit.Assert.assertEquals;


public class AESHelperTest {

    @Test
    public void deencodeTest() {
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

    @Test
    public void packetMessageTest() {
        try{
            UnsignedLong moreThanLongPktId = UnsignedLong.valueOf(Long.MAX_VALUE);
            moreThanLongPktId = moreThanLongPktId.plus(UnsignedLong.valueOf("2305"));

            String packetTestMessage = "packettestmsg";
            System.out.println("Packet test message: " + packetTestMessage);

            Message testMessage = new Message(4, 3, packetTestMessage);
            Packet testPacket = new Packet((byte) 1, moreThanLongPktId, testMessage);

            byte[] encodedtestPacket = testPacket.toPacket();
            Packet decodedtestPacket = new Packet(encodedtestPacket);

            assertEquals(decodedtestPacket.getBMsq().getMessage(), packetTestMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}