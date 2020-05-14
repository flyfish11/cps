package com.cloud.lcntransaction.rewrite;

import com.codingapi.tx.netty.service.TxManagerHttpRequestService;
import com.lorne.core.framework.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Classname TxManagerHttpRequestServiceImpl
 * @Description <h1>请求tx-manager接口实现</h1>
 * @Author yulj
 * @Date: 2020/04/09 10:45 上午
 */
@Slf4j
@Service
public class TxManagerHttpRequestServiceImpl implements TxManagerHttpRequestService {

    @Override
    public String httpGet(String url) {
        String res = HttpUtils.get(url);
        log.debug("请求接口 {}，响应报文：{}", url, res);
        return res;
    }

    @Override
    public String httpPost(String url, String params) {
        String res = HttpUtils.post(url, params);
        log.debug("请求接口 {}，响应报文：{}", url, res);
        return res;
    }

}
