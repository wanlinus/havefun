package cn.wanli.gitlabauth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;

/**
 * @author wanli
 * @date 2019-07-25 17:36
 */
public class FengjiawenQueTest {
    @Test
    public void toJson() {
        String json = "{'code':200, 'msg':'操作成功', 'data':[{'id':'ididid'}]}";
        ResponseEntity<PointTabDTO> p = JSON.parseObject(json, new TypeReference<ResponseEntity<PointTabDTO>>() {
        });
        System.out.println(p.getCode());
        System.out.println(p.getData().get(0).getId());
    }
}
