package cn.wanli.gitlabauth;

import java.util.List;

/**
 * @author wanli
 * @date 2019-07-25 17:33
 */
public class ResponseEntity<T> {
    private Integer code;
    private String msg;
    private List<T> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
