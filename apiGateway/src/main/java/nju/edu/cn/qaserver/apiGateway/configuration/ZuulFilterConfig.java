package nju.edu.cn.qaserver.apiGateway.configuration;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

/**
 * Created by demons on 2017/7/10.
 */
@Component
public class ZuulFilterConfig extends ZuulFilter {

    static private Long uid = null;

    static public void setUid(Long uid) {
        ZuulFilterConfig.uid = uid;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader("uid", uid.toString());
        return null;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 999;
    }
}
