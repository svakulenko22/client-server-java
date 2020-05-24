import classes.Network;
import classes.impl.TCPNetwork;

import java.io.IOException;

public class Server {

    public static void main(String[] args) {
        try {
            Network network = new TCPNetwork();
            network.listen();

            network.receive();
            network.receive();
            network.receive();

            network.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}