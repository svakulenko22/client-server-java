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
        try {
            UnsignedLong moreThanLongPktId = UnsignedLong.valueOf(Long.MAX_VALUE);
            moreThanLongPktId = moreThanLongPktId.plus(UnsignedLong.valueOf("2305"));

            String packetTestMessage = "packettestmsg";
            System.out.println("Packet testing message: " + packetTestMessage);

            Message testMessage = new Message(4, 3, packetTestMessage);
            Packet testPacket = new Packet((byte) 1, moreThanLongPktId, testMessage);

            byte[] cypheredtestPacket = testPacket.toPacket();
            Packet decypheredtestPacket = new Packet(cypheredtestPacket);

            //Testing CType sameness
            assertEquals(decypheredtestPacket.getBMsq().getCType(), testPacket.getBMsq().getCType());

            //Testing message sameness
            assertEquals(decypheredtestPacket.getBMsq().getMessage(), packetTestMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bSrcTest() {
        try {
            UnsignedLong moreThanLongPktId = UnsignedLong.valueOf(Long.MAX_VALUE);
            moreThanLongPktId = moreThanLongPktId.plus(UnsignedLong.valueOf("2305"));

            String bSrcTestMessage = "bSrctestmsg";
            System.out.println("bSrc testing message: " + bSrcTestMessage);

            Message testMessage = new Message(4, 3, bSrcTestMessage);
            Packet testPacket = new Packet((byte) 1, moreThanLongPktId, testMessage);

            byte[] cypheredtestPacket = testPacket.toPacket();
            Packet decypheredtestPacket = new Packet(cypheredtestPacket);

            assertEquals(decypheredtestPacket.getBSrc(), testPacket.getBSrc());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void wLenTest() {
        try {
            UnsignedLong moreThanLongPktId = UnsignedLong.valueOf(Long.MAX_VALUE);
            moreThanLongPktId = moreThanLongPktId.plus(UnsignedLong.valueOf("2305"));

            String wLenTestMessage = "wlentestmsg";
            System.out.println("wLen testing message: " + wLenTestMessage);

            Message testMessage = new Message(4, 3, wLenTestMessage);
            Packet testPacket = new Packet((byte) 1, moreThanLongPktId, testMessage);

            byte[] cypheredtestPacket = testPacket.toPacket();
            Packet decypheredtestPacket = new Packet(cypheredtestPacket);

            assertEquals(decypheredtestPacket.getWLen(), testPacket.getWLen());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}