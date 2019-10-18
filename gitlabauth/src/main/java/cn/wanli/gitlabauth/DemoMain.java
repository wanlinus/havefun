package cn.wanli.gitlabauth;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanli
 * @date 2019-07-10 16:45
 */
public class DemoMain {
    public static void main(String[] args) {
        String url = "http://127.0.0.1/gitlab/json";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Map<String, String> map = new HashMap<>(4);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, headers);

//        ResponseEntity<String> str = new RestTemplate().exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<String>() {
//        });
//        System.out.println(str.getBody());

    }
}
