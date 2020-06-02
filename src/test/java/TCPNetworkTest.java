
import com.google.common.primitives.UnsignedLong;
import entity.Message;
import entity.Packet;
import network.Network;
import network.impl.TCPNetwork;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TCPNetworkTest {

    private Network network;

    @Before
    public void setUp() throws Exception {
        network = new TCPNetwork();
        network.connect();
    }

    @Test
    public void sendTest() {
        Message testMessage = new Message(1, 1, "time");
        Packet packet = new Packet((byte) 1, UnsignedLong.ONE, testMessage);

        try {
            network.send(packet);
            final Packet receivedPacket = network.receive();

            final String message = receivedPacket.getBMsq().getMessage();

            Assert.assertEquals("now()", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        network.close();
    }
}

