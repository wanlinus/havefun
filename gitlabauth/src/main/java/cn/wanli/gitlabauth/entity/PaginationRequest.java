package cn.wanli.gitlabauth.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * @author wanli
 * @date 2019-07-22 17:43
 */
public class PaginationRequest {

    @Min(value = 1, message = "请求页不能小于1")
    private Integer requestPage = 1;
    @Min(value = 1, message = "pageSize的取值范围为1~100")
    @Max(value = 100, message = "pageSize的取值范围为1~100")
    private Integer pageSize = 10;
    @Size(max = 20, message = "keyword最大支持20个字符")
    private String keyword;

    public Integer getRequestPage() {
        return requestPage;
    }

    public void setRequestPage(Integer requestPage) {
        this.requestPage = requestPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
