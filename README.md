###模块说明
├── config-center                   --配置中心   
├── doc                             --相关文档  
├── gateway-zuul                    --网关模块  
├── oauth-center                    --授权中心  
├── platform-apps                   --微服务应用归集  
│   └── platform-app-manager          --服务管理中心微服务  
├── platform-common                 --平台基础服务归集  
│   ├── api-model                   --公共实体模块  
│   ├── commons                     --系统公共模块  
│   ├── lcn-transaction-starter     --分布式事务支持模块   
│   └── log-starter                 --日志中心starter  
├── platform-visual                 --平台图形化功能模块  
│   ├── file-center                 --文件中心  
│   ├── log-center                  --日志中心  
│   ├── monitor-center              --监控中心   
│   ├── tx-manager                  --分布式事务管理模块  
│   ├── platform-user                 --用户中心   
│   └── zipkin-server               --调用链拓扑  
└── register-center                 --注册中心  

### 版本说明
> SpringBoot 2.0.1  
> SpringCloud Finchley.RC1

- 2020.04.10  更新版本  
    SpringBoot 2.2.6  
    SpringCloud Hoxton.SR3    
>

### hosts文件配置
>> windows: C:\Windows\System32\drivers\etc  
>> Linux: /etc/hosts
```shell script
127.0.0.1       api.gateway.com #外网网关ip
127.0.0.1       gateway-zuul#内网网关ip
127.0.0.1       zipkin-server #监控中心ip
127.0.0.1       register-center#注册中心ip
127.0.0.1       local.monitor.com #监控中心ip
127.0.0.1       local.mysql.com #数据库ip
127.0.0.1       local.redis.com #redis ip
127.0.0.1       local.rabbitmq.com #rabbitmq ip
```