package network;

import entity.Packet;

import java.io.IOException;

public interface Network {

    void listen() throws IOException;

    Packet receive() throws IOException;

    void connect() throws IOException;

    default void reconnect() throws IOException { }

    default boolean isConnected() {return false;}

    void send(Packet packet) throws Exception;

    default void disconnect() { }

    void close() throws IOException;
}

