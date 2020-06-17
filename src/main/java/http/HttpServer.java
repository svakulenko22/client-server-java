package http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HttpServer {

public final static int PORT = 8888;
private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

private AbstractConnectionFactory connectionFactory();

public HttpServer(AbstractConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;

public HttpServer() {
    connectionFactory = new ProductionConnectionFactory();
}

public static void main(String[] args) {
    HttpServer server = new HttpServer();
    new Thread(server).start();
}

public void handleRequest(Socket connection) {
    try {
        InputStream input = connection.getInputStream();
        OutputStream output = connection.getOutputStream();
    } {
        HttpParser hp = new HttpParser(input);
        ResponseSender responseSender = new ResponseSender(output);
        HttpProcessor httpProcessor = new HttpProcessor(connectionFactory, hp, responseSender);
        httpProcessor.process();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

@Override
public void run() {
    try {
        ServerSocket socket = new ServerSocket(PORT);
//  }   {
        System.out.println("Server is waiting for request at port:" + PORT);
        while(true) {
            Socket connection = socket.accept();
            Runnable task = () -> handleRequest(connection);
            exec.execute(task);
        }
    } catch (Exception e) {
        e.printStackTrace();
        System.exit(1);
    }
}
}