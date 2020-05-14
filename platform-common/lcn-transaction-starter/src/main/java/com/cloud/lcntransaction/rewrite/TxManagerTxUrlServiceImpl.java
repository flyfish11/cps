package com.cloud.lcntransaction.rewrite;

import com.codingapi.tx.config.service.TxManagerTxUrlService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;

/**
 * @Classname TxManagerHttpRequestServiceImpl
 * @Description <h1>使用服务发现重写 Txmanager 获取规则</h1>
 * @Author yulj
 * @Date: 2020/04/09 10:51 上午
 */
@Slf4j
@Service
@AllArgsConstructor
public class TxManagerTxUrlServiceImpl implements TxManagerTxUrlService {

    /**
     * 分布式事务协调服务名称
     */
    public static final String TX_MANAGER = "tx-manager";

    private final LoadBalancerClient loadBalancerClient;

    @Override
    public String getTxUrl() {
        ServiceInstance serviceInstance = loadBalancerClient.choose(TX_MANAGER);
        String host = serviceInstance.getHost();
        Integer port = serviceInstance.getPort();
        String url = String.format("http://%s:%s/tx/manager/", host, port);
        log.info("tm.manager.url -> {}", url);
        return url;
    }

}
