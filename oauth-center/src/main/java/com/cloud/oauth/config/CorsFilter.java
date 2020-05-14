package com.cloud.oauth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Title: cloud-service
 * @Description :
 * @Authod: smokong
 * @Date: 2019/4/30 17:24
 * @Version: 1.0
 */
@Component
@Slf4j
public class CorsFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;

		//HttpServletRequest reqs = (HttpServletRequest) req;
		response.setHeader("Access-Control-Allow-Origin","*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PATCH");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
		//response.setHeader("Access-Control-Request-Headers", "*");
		// response.setHeader("Content-Type", "application/json");
		//((HttpServletResponse)res).setHeader("Content-Type", "application/json");
		//response.setHeader("Access-Control-Allow-Origin", "*");
        /*String url="http://localhost:9900/oauth/check_token?token="+reqs.getHeader("Token");
        String resp = HttpClientUtil.sendHttp(HttpClientUtil.HttpRequestMethedEnum.HttpGet,url,null);


        if(resp.equals("success")){
            chain.doFilter(req,res);
        }else{
            throw new IOException("无权访问!");
        }
*/
		chain.doFilter(req,res);
	}
}
