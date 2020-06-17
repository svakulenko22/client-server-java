package dto;

import com.sun.net.httpserver.HttpExchange;
import lombok.Data;

@Data
public class SaveProductDTO {
    String template = "";
    Object data;
    Integer statusCode;
    HttpExchange httpExchange;
}
