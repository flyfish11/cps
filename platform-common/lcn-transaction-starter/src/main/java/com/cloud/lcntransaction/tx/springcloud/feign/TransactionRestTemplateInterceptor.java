package com.cloud.lcntransaction.tx.springcloud.feign;

import com.codingapi.tx.aop.bean.TxTransactionLocal;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * @author LCN on 2017/6/26.
 * @author LCN
 * @since 4.1.0
 */
@Slf4j
public class TransactionRestTemplateInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication instanceof OAuth2Authentication) {
                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                String access_token = details.getTokenValue();
                requestTemplate.header("Authorization", OAuth2AccessToken.BEARER_TYPE + " " + access_token);
            }
        }

        TxTransactionLocal txTransactionLocal = TxTransactionLocal.current();
        String groupId = txTransactionLocal == null ? null : txTransactionLocal.getGroupId();

        log.info("LCN-SpringCloud TxGroup info -> groupId:{}", groupId);

        if (txTransactionLocal != null) {
            requestTemplate.header("tx-group", groupId);
        }
    }

}
