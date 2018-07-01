package com.sai.openapi;

import com.alibaba.fastjson.JSONObject;
import com.orbitz.consul.Consul;
import com.sai.core.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.*;

public class ConsulPropertySourceLoader implements PropertySourceLoader {
    @Override
    public String[] getFileExtensions() {
        return new String[]{"consul"};
    }

    @Override
    public PropertySource<?> load(String name, Resource resource, String profile) throws IOException {
        Properties properties = new Properties();
        properties.load(resource.getInputStream());
        Map<String, Object> source = this.process(properties);
        if (!source.isEmpty()) {
            return new MapPropertySource(name, source);
        }
        return null;
    }

    private Map<String, Object> process(Properties properties) {
        ConsulUtil consulUtil = ConsulUtil.getInstance();
        consulUtil.init(properties);
        final String keyPre = "saiApp/config/" + properties.getProperty("spring.active.profiles");
        final Map<String, Object> result = new LinkedHashMap<String, Object>();
        List<String> subKeyList = consulUtil.getSubKeys(keyPre);
        if (subKeyList != null || subKeyList.size() > 0) {
            for (String subKey : subKeyList) {
                String value = consulUtil.getValue(subKey);
                if (StringUtils.isBlank(value)) {
                    continue;
                }
                String theKey = subKey.replaceFirst(keyPre, "").replaceAll("/", Constants.SYMBOL_POINT).replaceFirst(Constants.SYMBOL_POINT,"");
                JSONObject jsonObject = JSONUtil.toJSONObject(value);
                if (jsonObject == null) {
                    result.put(theKey, value);
                } else {
                    Iterator<Map.Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Object> entry = iterator.next();
                        Object entryValue = entry.getValue();
                        if (entryValue != null) {
                            String entryKey = entry.getKey();
                            result.put(theKey + Constants.SYMBOL_POINT + entryKey, entryValue);
                        }
                    }
                }
            }
        }
        System.out.println(JSONObject.toJSONString(result));
        return result;
    }
}
