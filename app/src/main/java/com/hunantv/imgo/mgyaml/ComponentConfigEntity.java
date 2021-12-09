package com.hunantv.imgo.mgyaml;

import android.text.TextUtils;
import android.util.Log;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 组件的配置
 */
public class ComponentConfigEntity implements Serializable {

    /**路由配置*/
    public List<RouterConfig> routerConfig;
    /**组件相关配置*/
    public ComponentConfig componentConfig;

    public static final class RouterConfig implements Serializable {
        /** 暂时预留 */
        public String host;
        /** 兼容线上的schema */
        public String path;
        /** launchMode */
        public String routeType;
        /** 兼容老schema */
        public int jumpKind;
        public ComponentPath component;
        public Object params;
    }

    public static final class ComponentConfig implements Serializable {
        /**组件是否可用*/
        public boolean enable = true;
        /**组件名称*/
        public String componentName;
        /**组件ID*/
        public String componentId;
    }

    public static final class ComponentPath implements Serializable {
        public String android;
        public String iOS;
    }

    public String[] getPaths() {
        List<String> hostArray = new ArrayList<>();
        if (routerConfig != null && routerConfig.size() > 0) {
            for (RouterConfig config : routerConfig) {
                if (config != null && config.component != null) {
                    String componentPath = config.component.android;
                    if (!TextUtils.isEmpty(componentPath) && !TextUtils.isEmpty(config.path)) {
                        hostArray.add(config.path);
                    }
                }
            }
        }
        String[] mHosts = hostArray.toArray(new String[hostArray.size()]);
        if (mHosts != null && mHosts.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String host : mHosts) {
                stringBuilder.append("[" + host + "],");
            }
            Log.d("ComponentConfigEntity", "hosts() mHosts = " + stringBuilder.toString());
        } else {
            Log.d("ComponentConfigEntity", "hosts() mHosts is empty!!!");
        }
        return mHosts;
    }

    public String getComponentPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        if (routerConfig != null) {
            for (ComponentConfigEntity.RouterConfig config : routerConfig) {
                if (path.equals(config.path)) {
                    Log.d("ComponentConfigEntity", "getComponentPath() path = "+ path
                            + ", componentPath = " + config.component.android);
                    return config.component.android;
                }
            }
        }
        Log.d("ComponentConfigEntity", "getComponentPath() path = "+ path
                + ", componentPath = null");
        return null;
    }
}
