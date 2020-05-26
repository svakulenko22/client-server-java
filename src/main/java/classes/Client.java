package classes;

import classes.impl.TCPNetwork;
import classes.service.MessageService;
import classes.service.impl.MessageServiceImpl;
import com.google.common.primitives.UnsignedLong;
import entity.Message;
import entity.Packet;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.MAX_PRIORITY;


public class Client {

    public static void main(String[] args) {

        MessageService messageService = new MessageServiceImpl();

        Message testMessage = messageService.generate();
        Packet packet = new Packet((byte) 1, UnsignedLong.ONE, testMessage);

        Message secondTestMessage = messageService.generate();
        Packet secondPacket = new Packet((byte) 1, UnsignedLong.ONE, secondTestMessage);

        final ExecutorService threadPool = Executors.newFixedThreadPool(10);

        Network network = new TCPNetwork();

        System.out.println(Thread.currentThread().getName());

        try {
            network.connect();

            Runnable runnable = () -> {
                try {
                    network.send(packet);
                    network.receive();
                    network.send(secondPacket);
                    network.receive();

                } catch (Exception e) {

                    e.printStackTrace();
                }
            };

            threadPool.execute(runnable);

            threadPool.shutdown();
            //threadPool.awaitTermination(15, TimeUnit.SECONDS);
            threadPool.awaitTermination(MAX_PRIORITY, TimeUnit.HOURS);

            network.close();

            System.out.println(Thread.currentThread().getName());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}