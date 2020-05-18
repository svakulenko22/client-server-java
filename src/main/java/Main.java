import com.google.common.primitives.UnsignedLong;
import entity.Message;
import entity.Packet;

public class Main {

    public static void main(String[] args) {

        start();
    }

    private static void start() {
        UnsignedLong moreThanLongPktId = UnsignedLong.valueOf(Long.MAX_VALUE);
        moreThanLongPktId = moreThanLongPktId.plus(UnsignedLong.valueOf("2305"));

        System.out.println("UnsignedLong moreThanLongPktId: " + moreThanLongPktId.toString());
        System.out.println("long moreThanLongPktId: " + moreThanLongPktId.longValue());

        Message testMessage = new Message(3, 4, "test");
        Packet packet = new Packet((byte) 1, moreThanLongPktId, testMessage);

        try {
            byte[] encodedPacket = packet.toPacket();
            System.out.println(packet);
            System.out.println("Out packet: ");

            // sent to http

            Packet decodedPacket = new Packet(encodedPacket);
            System.out.println("Int packet: ");
            System.out.println(decodedPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

