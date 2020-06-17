package http;

import java.io.OutputStream;
import java.io.PrintWriter;

public class ResponseSender {
    private final static String APP_JSON = "application/json";

    private final PrintWriter pw;

    public ResponseSender(OutputStream out) {
        pw = new PrintWriter(out, true);
    }

    public void sendResponse(int code, String contentType, String body) {
        pw.print("HTTP/1.1 " + code + "\r\n");
        pw.print("Content-Type: " + contentType + "\r\n");
        pw.print("Access-Control-Allow-Origin: *\r\n");
        pw.print("Connection: close\r\n");
        pw.print("\r\n");
        pw.println(body);
    }

    public void sendJsonResponse(int code, String body) {
        this.sendResponse(code, APP_JSON, body);
    }

    public void sendOptionsResponse() {
        pw.print("HTTP/1.1 204 No Content\r\n");
        pw.print("Access-Control-Allow-Methods: GET, PUT, POST, DELETE, OPTIONS\r\n");
        pw.print("Access-Control-Allow-Headers: content-type, x-auth\r\n");
        pw.print("Connection: Keep-Alive\r\n");
    }
}