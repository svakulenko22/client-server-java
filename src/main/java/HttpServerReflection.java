import com.sun.net.httpserver.HttpServer;
import controllers.MultiactionController;
import views.HtmlView;
import views.View;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServerReflection {

    final static int HTTP_SERVER_PORT = 8888;

    final static View VIEW = new HtmlView();

    public static void main(String[] args) {
        try {
            MultiactionController.setView(VIEW);

            HttpServer server = HttpServer.create();

            server.bind(new InetSocketAddress(HTTP_SERVER_PORT), 0);

            MultiactionController multiactionController = new MultiactionController();
            server.createContext("/", multiactionController);

            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
