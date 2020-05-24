package classes.impl;

import classes.Network;
import classes.Processor;
import entity.Message;
import entity.Packet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class TCPNetwork implements Network {

    Socket socket;

    ServerSocket serverSocket;

    OutputStream socketOutputStream;
    InputStream serverInputStream;

    @Override
    public void listen() throws IOException {
        serverSocket = new ServerSocket(2305);

        socket = serverSocket.accept();

        socketOutputStream = socket.getOutputStream();
        serverInputStream = socket.getInputStream();
    }

    @Override
    public Packet receive() {

        Integer state = 0;
        Integer wLen = 0;
        Boolean packetIncomplete = true;

        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
            ByteArrayOutputStream packetBytes = new ByteArrayOutputStream();

            byte oneByte[] = new byte[1];
            while (packetIncomplete && (serverInputStream.read(oneByte)) != -1) {
                if (Packet.bMagic.equals(oneByte[0])) {
                    state = 0;
                    byteBuffer = ByteBuffer.allocate(Packet.packetPartFirstLengthWithoutwLen - Packet.bMagic.BYTES);
                    packetBytes.reset();
                } else {
                    byteBuffer.put(oneByte);
                    switch (state) {
                        case 0:
                            if (!byteBuffer.hasRemaining()) {
                                byteBuffer = ByteBuffer.allocate(Integer.BYTES);
                                state = 1;
                            }
                            break;

                        case 1:
                            if (!byteBuffer.hasRemaining()) {
                                wLen = byteBuffer.getInt(0);
                                byteBuffer = ByteBuffer.allocate(Short.BYTES + Message.BYTES_WITHOUT_MESSAGE + wLen + Short.BYTES);
                                state = 2;
                            }
                            break;

                        case 2:
                            if (!byteBuffer.hasRemaining()) {
                                packetIncomplete = false;
                            }
                            break;
                    }
                }
                packetBytes.write(oneByte);
            }

            byte[] fullPacket = packetBytes.toByteArray();
            Packet packet = new Packet(fullPacket);
            System.out.println("Received");
            System.out.println(packet.toString());
            System.err.println(packet.getBMsq().getMessage());
            Processor.process(this, packet);
            return packet;
        } catch (Exception e) {
            System.err.println("Error:" + socket);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void connect() throws IOException {
        socket = new Socket("localhost", 2305);
        socketOutputStream = socket.getOutputStream();
        serverInputStream = socket.getInputStream();
    }

    @Override
    public void send(Packet packet) throws Exception {
        System.out.println(Thread.currentThread().getName());
        byte[] packetBytes = packet.toPacket();
        socketOutputStream.write(packetBytes);
        socketOutputStream.flush();

    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}

