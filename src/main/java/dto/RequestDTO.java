package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.Product;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestDTO {
    private String method; // get, delete, post
    private Map<String, Integer> querystring; // { id : 1 }
    private Product body; // id는 사용안해야지!
}
