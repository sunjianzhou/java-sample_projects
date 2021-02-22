package com.jdicity.gateway.nacos;

import com.alibaba.csp.sentinel.concurrent.NamedThreadFactory;
import com.alibaba.csp.sentinel.datasource.AbstractDataSource;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import org.springframework.context.annotation.Profile;

import java.util.Properties;
import java.util.concurrent.*;

import static ch.qos.logback.core.spi.ComponentTracker.DEFAULT_TIMEOUT;


/**
 * 动态监听nacos限流熔断.
 *
 * @param <T> 需要实时监控的数据
 * @author zhengweibing3
 * @date 2020/12/18/0018 17:11
 */
@Profile("!integrationTest")
public class NacosDataSource<T> extends AbstractDataSource<String, T> {

    /**
     * 配置监听
     */
    private final Listener configListener;

    /**
     * 配置
     */
    private final Properties properties;

    /**
     * dataId
     */
    private final String dataId;

    /**
     * 群组id
     */
    private final String groupId;

    /**
     * 配置服务
     */
    private ConfigService configService = null;

    /**
     * 线程池
     */
    private final ExecutorService pool = new ThreadPoolExecutor(1, 1,
            0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1),
            new NamedThreadFactory("sentinel-nacos-ds-update"),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    /**
     * 构建方法
     * @param serverAddress
     * @param groupId
     * @param dataId
     * @param parser
     */
    public NacosDataSource(final String serverAddress, final String groupId, final String dataId,
            Converter<String, T> parser) {
        this(NacosDataSource.buildProperties(serverAddress), groupId, dataId, parser);
    }

    /**
     * 构建方法
     * @param properties
     * @param groupId
     * @param dataId
     * @param parser
     */
    public NacosDataSource(final Properties properties, final String groupId, final String dataId,
            Converter<String, T> parser) {
        super(parser);
        if (StringUtil.isBlank(groupId) || StringUtil.isBlank(dataId)) {
            throw new IllegalArgumentException(String.format("Bad argument: groupId=[%s], dataId=[%s]",
                    groupId, dataId));
        }
        AssertUtil.notNull(properties, "Nacos properties must not be null, you could put some keys from PropertyKeyConst");
        this.groupId = groupId;
        this.dataId = dataId;
        this.properties = properties;
        this.configListener = new Listener() {
            @Override
            public Executor getExecutor() {
                return pool;
            }

            @Override
            public void receiveConfigInfo(final String configInfo) {
                RecordLog.info("[NacosDataSource] New property value received for (properties: {}) (dataId: {}, groupId: {}): {}",
                        properties, dataId, groupId, configInfo);
                T newValue = NacosDataSource.this.parser.convert(configInfo);
                // Update the new value to the property.
                getProperty().updateValue(newValue);
            }
        };
        initNacosListener();
        loadInitialConfig();
    }

    /**
     * 初始化加载
     */
    private void loadInitialConfig() {
        try {
            T newValue = loadConfig();
            if (newValue == null) {
                RecordLog.warn("[NacosDataSource] WARN: initial config is null, you may have to check your data source");
            }
            getProperty().updateValue(newValue);
        } catch (Exception ex) {
            RecordLog.warn("[NacosDataSource] Error when loading initial config", ex);
        }
    }

    /**
     * 初始化nacos监听器
     */
    private void initNacosListener() {
        try {
            this.configService = NacosFactory.createConfigService(this.properties);
            // Add config listener.
            configService.addListener(dataId, groupId, configListener);
        } catch (Exception e) {
            RecordLog.warn("[NacosDataSource] Error occurred when initializing Nacos data source", e);
            e.printStackTrace();
        }
    }

    @Override
    public String readSource() throws Exception {
        if (configService == null) {
            throw new IllegalStateException("Nacos config service has not been initialized or error occurred");
        }
        return configService.getConfig(dataId, groupId, DEFAULT_TIMEOUT);
    }

    @Override
    public void close() {
        if (configService != null) {
            configService.removeListener(dataId, groupId, configListener);
        }
        pool.shutdownNow();
    }

    private static Properties buildProperties(String serverAddress) {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, serverAddress);
        return properties;
    }
}
