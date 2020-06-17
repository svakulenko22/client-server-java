package controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dto.SaveProductDTO;
import utils.HttpUtil;
import views.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class MultiactionController implements HttpHandler {
    private static View view;

    public static void setView(View newView) {
        view = newView;
    }

    public void handler(HttpExchange httpExchange, Map result) {
        URI requestUri = httpExchange.getRequestURI();

        String query = requestUri.getRawQuery();
        result.put("query", query);

        Map<String, Object> getRequestParameters = HttpUtil.parseQuery(query);
        result.put("getRequestParameters", getRequestParameters);

        String requestMethod = httpExchange.getRequestMethod();
        String requestMethodLowerCased = requestMethod.toLowerCase();

        if (requestMethodLowerCased.equals("post")) {
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody(), "UTF-8");

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String postRequest = bufferedReader.readLine();

                Map<String, Object> postRequestParameters = HttpUtil.parseQuery(postRequest);
                result.put("postRequestParameters", postRequestParameters);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        SaveProductDTO response = new SaveProductDTO();

        response.setTemplate("list");
        response.setStatusCode(200);
        response.setData(result);
        response.setHttpExchange(httpExchange);

        view.view(response);
    }

    @Override
    public void handle(HttpExchange httpExchange) {
        try {
            String methodToCallName = "handler";

            Map<String, Object> result = new HashMap<>();

            URI requestUri = httpExchange.getRequestURI();
            result.put("requestUri", requestUri);

            String requestUriPath = requestUri.getPath();
            result.put("requestUriPath", requestUriPath);

            String[] requestUriPathParts = requestUriPath.split("/");
            result.put("requestUriPathParts", requestUriPathParts);

            String method = httpExchange.getRequestMethod();
            result.put("method", method);

            Method exampleHelloControllerHandlerMethod = MultiactionController.class.getDeclaredMethod(methodToCallName, HttpExchange.class, Map.class);
            exampleHelloControllerHandlerMethod.invoke(this, httpExchange, result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
