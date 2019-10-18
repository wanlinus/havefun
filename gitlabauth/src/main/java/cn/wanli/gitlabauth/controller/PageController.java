package cn.wanli.gitlabauth.controller;

import cn.wanli.gitlabauth.entity.PaginationRequest;
import com.alibaba.fastjson.JSON;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * @author wanli
 * @date 2019-07-22 17:42
 */
@RestController
@RequestMapping("/page")
public class PageController {
    @GetMapping
    public String page(@Valid PaginationRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("+"));
        }
        return JSON.toJSONString(request);
    }
}
