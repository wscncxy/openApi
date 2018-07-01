package com.sai.openapi;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import com.orbitz.consul.model.kv.Value;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ConsulUtil {

    private Consul consul;
    private AgentClient agentClient;
    private KeyValueClient keyValueClient;

    private ConsulUtil() {
    }

    private static class InnerSingleton {
        private static final ConsulUtil INSTANCE = new ConsulUtil();
    }

    public static final ConsulUtil getInstance() {
        return InnerSingleton.INSTANCE;
    }

    public void init(Properties properties) {
        if (properties != null) {
            String host = properties.getProperty("consul.host");
            Integer port = Integer.parseInt(properties.getProperty("consul.port"));
            consul = Consul.builder().withHostAndPort(HostAndPort.fromParts(host, port)).build();
            agentClient = consul.agentClient();
            keyValueClient = consul.keyValueClient();
        } else {
            throw new NullPointerException("ConsulUtil's properties is null");
        }
    }

    public String getValue(String key) {
        return keyValueClient.getValueAsString(key).get();
    }

    public List<String> getSubKeys(String key){
        return keyValueClient.getKeys(key);
    }

}
