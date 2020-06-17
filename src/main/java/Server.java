import network.Network;
import network.factory.NetworkFactory;
import utils.NetworkProperties;

import java.io.IOException;


public class Server {
    public static void main(String[] args) {
        try {
            String networkType = NetworkProperties.getProperty("type");

            final Network network = NetworkFactory.getNetwork(networkType);

            System.out.println("Server running via " + network + " connection");

            network.listen();

            for (int i = 0; i < 12; i++) {
                network.receive();
                try {
                    Thread.sleep(3000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            network.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
