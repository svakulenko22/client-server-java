package classes;

import entity.Packet;

import java.io.IOException;

public interface Network {

    void listen() throws IOException;

    Packet receive() throws IOException;

    void connect() throws IOException;

    void send(Packet packet) throws Exception;

    void close() throws IOException;
}
