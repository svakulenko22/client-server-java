package network.impl;

import classes.Processor;
import entity.Message;
import entity.Packet;
import network.Network;
import utils.NetworkProperties;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class TCPNetwork implements Network {
    Socket socket;
    ServerSocket serverSocket;

    @Override
    public void listen() throws IOException {
        String portProperty = NetworkProperties.getProperty("port");
        if (portProperty == null) {
            portProperty = "2305";
        }

        serverSocket = new ServerSocket(Integer.parseInt(portProperty));

        socket = serverSocket.accept();
    }

    @Override
    public Packet receive() throws IOException {
        InputStream serverInputStream = socket.getInputStream();

        try {
            byte[] maxPacketBuffer = new byte[Packet.packetMaxSize];

            serverInputStream.read(maxPacketBuffer);

            ByteBuffer byteBuffer = ByteBuffer.wrap(maxPacketBuffer);
            Integer wLen = byteBuffer.getInt(Packet.packetPartFirstLengthWithoutwLen);

            byte[] fullPacket = byteBuffer.slice(0, Packet.packetPartFirstLength + Message.BYTES_WITHOUT_MESSAGE + wLen).array();

            System.out.println("Received");
            System.out.println(Arrays.toString(fullPacket) + "\n");

            Packet packet = new Packet(fullPacket);
            System.err.println(packet.getBMsq().getMessage());

            if (serverSocket != null)
                Processor.process(this, packet);
            else
                return packet;
        } catch (Exception e) {
            System.err.println("Error:" + socket);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void connect() throws IOException {
        String hostProperty = NetworkProperties.getProperty("host");
        if (hostProperty == null) {
            hostProperty = "localhost";
        }

        String portProperty = NetworkProperties.getProperty("port");
        if (portProperty == null) {
            portProperty = "2305";
        }

        socket = new Socket(hostProperty,Integer.parseInt(portProperty));
    }

    @Override
    public void send(Packet packet) throws Exception {
        OutputStream socketOutputStream = socket.getOutputStream();

        byte[] packetBytes = packet.toPacket();

        socketOutputStream.write(packetBytes);
        socketOutputStream.flush();

        System.out.println("Send");
        System.out.println(Arrays.toString(packetBytes) + "\n");
    }

    @Override
    public void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        } else {
            socket.close();
        }
    }
}








