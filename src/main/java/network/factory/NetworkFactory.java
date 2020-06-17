package network.factory;

import network.Network;
import network.impl.TCPNetwork;
import network.impl.UDPNetwork;
import utils.Constants;

public class NetworkFactory {

    public static Network getNetwork(String type) {
        if (Constants.TCP.equals(type)) {
            return new TCPNetwork();
        } else if (Constants.UDP.equals(type)) {
            return new UDPNetwork();
        }
        throw new RuntimeException("Wrong type of network");
    }
}
