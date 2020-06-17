package service.impl;

import entity.Message;
import service.MessageService;

import java.util.Random;

public class MessageServiceImpl implements MessageService {

    @Override
    public Message generate() {
        final Random random = new Random();
        String message = System.currentTimeMillis() % 2 == 0 ? "time" : "notTime";
        return new Message(1, 1 + random.nextInt(11), message);
    }
}
