import com.google.common.primitives.UnsignedLong;
import entity.Message;
import entity.Packet;
import network.Network;
import network.factory.NetworkFactory;
import service.MessageService;
import service.impl.MessageServiceImpl;
import utils.NetworkProperties;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.MAX_PRIORITY;

public class Client {

    private static final List<Packet> failedPackets = new LinkedList<>();

    public static void main(String[] args) {

        MessageService messageService = new MessageServiceImpl();

        Message testMessage = messageService.generate();
        Packet firstPacket = new Packet((byte) 1, UnsignedLong.ONE, testMessage);

        Message secondTestMessage = new Message(1, 1, "notTime");
        Packet secondPacket = new Packet((byte) 1, UnsignedLong.ONE, secondTestMessage);

        List<Packet> packets = new LinkedList<>();
        packets.add(firstPacket);
        packets.add(secondPacket);

        try {
            String networkType = NetworkProperties.getProperty("type");

            final Network network = NetworkFactory.getNetwork(networkType);

            System.out.println("Client running via " + network + " connection");

            network.connect();

            final ExecutorService threadPool = Executors.newFixedThreadPool(10);

            Runnable runnable = () -> {
                try {
                    while (true) {
                        sending(network, packets);
                        if (failedPackets.isEmpty()) {
                            return;
                        }
                        sending(network, failedPackets);
                        if (failedPackets.isEmpty()) {
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };

            threadPool.execute(runnable);

            threadPool.shutdown();
            threadPool.awaitTermination(MAX_PRIORITY, TimeUnit.HOURS);

            network.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sending(Network network, List<Packet> packets) throws Exception {
        for (int i = 0; i < 12; i++) {
            if (i == 5) {
                network.disconnect();
                System.out.println("Network is connected: " + network.isConnected());
            }
            if (i == 9) {
                network.disconnect();
                System.out.println("Network is connected: " + network.isConnected());
            }
            checkNetwork(network);

            int index;
            if (i % 2 == 0) {
                index = 0;
            } else {
                index = 1;
            }

            final Packet packet = packets.get(index);

            network.send(packet);
            Packet answer = network.receive();
            final boolean response = checkResponse(answer, packet);
            if (response) {
                failedPackets.removeIf(s -> s.getBPktId().equals(answer.getBPktId()));
            } else {
                failedPackets.add(packet);
            }
        }
    }

    private static boolean checkResponse(Packet answer, Packet packet) {
        if (answer.getBPktId().equals(packet.getBPktId())) {
            System.out.println("CORRECT");
            return true;
        } else {
            System.out.println("WRONG PACKET RESPONSE");
            return false;
        }
    }

    private static void checkNetwork(Network network) throws IOException {
        while (true) {
            if (network.isConnected()) {
                return;
            } else {
                System.out.println("Client is trying to reconnect");
                network.reconnect();
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
