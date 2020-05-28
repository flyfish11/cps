package com.cloud.oauth.adauthenication;

import com.cloud.common.constants.CommonConstants;
import com.cloud.common.utils.R;
import com.cloud.model.platformuser.bo.LdapUserBO;
import com.cloud.oauth.feign.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class AdCheckFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private UserClient userClient;

    private Set<String> urls = new HashSet<>();

//    @Value("${ldap.host}")
//    private String host;
//
//    @Value("${ldap.domain}")
//    private String domain;
//
//    @Value("${ldap.port}")
//    private String port;
//
//    @Value("${ldap.basedn}")
//    private String basedn;
//
//    private final Control[] connCtls = null;


    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urls.add("/adLogin");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 当请求为登陆时 并且是post请求时 才进行过滤 进行密码校验
        if (StringUtils.equals("/adLogin", request.getRequestURI()) && StringUtils.equals(request.getMethod(), "POST")) {
            try {
                //validate(new ServletWebRequest(request));
                String account = request.getParameter("account");
                String password = request.getParameter("password");
                LdapUserBO ldapUser = LdapUserBO.builder().account(account).password(password).build();
                R authenticate = userClient.authenticate(ldapUser);
                if (authenticate.getCode() != CommonConstants.SUCCESS) {
                    authenticationFailureHandler.onAuthenticationFailure(request, response, new AdAuthenticationException(authenticate.getMsg()));
                    return;
                }
            } catch (Exception e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, new AdAuthenticationException(e.getMessage()));
                return;
            }
        }
        filterChain.doFilter(request, response);

    }

   /* private void validate(ServletWebRequest request) throws NamingException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        //AD域IP，必须填写正确
        //String host = "10.130.14.9";
        //域名后缀，例.@noker.cn.com
        //String domain = "@hyhhgroup.com";
        //端口，一般默认389
        //String port = "389";
        //固定写法
        System.out.println("LDAP地址：'"+"ldap://" + host + ":" + port+"';domain:"+domain);
        String url = "ldap://" + host + ":" + port;
        //实例化一个Env
        Hashtable env = new Hashtable();
        DirContext ctx = null;
        //LDAP访问安全级别(none,simple,strong),一种模式，这么写就行
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        //用户名
        env.put(Context.SECURITY_PRINCIPAL, "uid="+account +","+ basedn);
        //密码
        env.put(Context.SECURITY_CREDENTIALS, password);
        // LDAP工厂类
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        //Url
        env.put(Context.PROVIDER_URL, url);

        List<String> list = new LinkedList<>();
        NamingEnumeration results = null;
        try {
            // 初始化上下文
            ctx = new InitialDirContext(env);
            log.info("身份验证成功!");
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            /*results = ctx.search("", "(objectclass=person)", controls);
            while (results.hasMore()) {
                SearchResult searchResult = (SearchResult) results.next();
                Attributes attributes = searchResult.getAttributes();
                Attribute attr = attributes.get("cn");
                String cn = attr.get().toString();
                list.add(cn);
            }
        } catch (AuthenticationException e) {
            log.error("身份验证失败!");
            throw  new AdAuthenticationException(e.getMessage());
        } catch (CommunicationException e) {
            log.error("LDAP域连接失败!");
            throw  new AdAuthenticationException(e.getMessage());
        } catch (NameNotFoundException e) {
            log.error("用户名不存在");
            throw  new AdAuthenticationException(e.getMessage());
        } catch (Exception e) {
            log.error("身份验证未知异常:{}",e.getMessage());
            throw  new AdAuthenticationException(e.getMessage());
        } finally {
            if (null != ctx) {

                ctx.close();
                ctx = null;

            }
        }


        //进行ad认证
    }*/


    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

}
