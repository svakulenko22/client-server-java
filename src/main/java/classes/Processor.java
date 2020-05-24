package classes;

import com.google.common.primitives.UnsignedLong;
import entity.Message;
import entity.Packet;

public class Processor {

    public static void process(Network network, Packet packet) {
        String message = packet.getBMsq().getMessage();

        Message answerMessage;
        if (message.equals("time")) {
            answerMessage = new Message(1, 1, "now()");
        } else {
            answerMessage = new Message(1, 1, "other");
        }
        Packet answerPacket = new Packet((byte) 1, UnsignedLong.ONE, answerMessage);

        try {
            network.send(answerPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

