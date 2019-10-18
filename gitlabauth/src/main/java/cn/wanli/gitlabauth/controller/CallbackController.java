package cn.wanli.gitlabauth.controller;

import cn.wanli.gitlabauth.entity.GitlabToken;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/gitlab")
public class CallbackController {

    public static final String CLIENT_ID = "37c111426318a880b3eba318c77b455835364485e8e51292fc017ace9a31fb291";
    public static final String CLIENT_SECRET = "3a056cb479634eb198d78f58c8cebe7fadffca1b868f919d4c8132c256e511fdd";
    //Gitlab发送302给UI, 让ui访问Client的回调地址
    public static final String REDIRECT_URL = "http://127.0.0.1/gitlab/callback";

    public static final String GITLAB_REDIRECT_URL = "https://gitlab.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code&state=YOUR_UNIQUE_STATE_HASH";
    public static final String GITLAB_TOKEN = "https://gitlab.com/oauth/token";
    public static final Logger LOGGER = LoggerFactory.getLogger(CallbackController.class);

    @GetMapping("login")
    public void login(HttpServletResponse response) throws IOException {
        LOGGER.info("302 重定向");
        response.sendRedirect(String.format(GITLAB_REDIRECT_URL, CLIENT_ID, REDIRECT_URL));
    }


    @GetMapping("callback")
    public String callback(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Callback 客户端IP: {} \nCode: {}", request.getRemoteHost(), code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Map<String, String> map = new HashMap<>(8);
        map.put("client_id", CLIENT_ID);
        map.put("client_secret", CLIENT_SECRET);
        map.put("code", code);
        map.put("grant_type", "authorization_code");
        map.put("redirect_uri", REDIRECT_URL);
        map.put("scope", "api");
        HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(map), headers);

        GitlabToken token = new RestTemplate().postForObject(GITLAB_TOKEN, entity, GitlabToken.class);
        LOGGER.info("accesstoken is {}", token);

        //获取用户信息
        String userJson = new RestTemplate().getForObject("https://gitlab.com/api/v4/user?access_token={token}",
                String.class, token.getAccessToken());

        LOGGER.info("UserJson: {}", userJson);


        return userJson;
    }

}
