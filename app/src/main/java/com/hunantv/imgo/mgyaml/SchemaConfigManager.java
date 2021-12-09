package com.hunantv.imgo.mgyaml;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.hunantv.imgo.yaml.Yaml;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * 解析route配置文件
 * hegui 2021/12/7
 */
public class SchemaConfigManager {

    public static ComponentConfigEntity readConfig(Context ctx, String fileName, boolean isAsset) {
        if (TextUtils.isEmpty(fileName) || ctx == null) {
            return null;
        }
        return parseYaml(ctx, fileName, isAsset, ComponentConfigEntity.class);
    }

    /**
     * 开源yaml解析框架https://github.com/bmoliveira/snake-yaml
     */
    private static <T> T parseYaml(Context context, String fileName, boolean isAsset, Class<T> clazz) {
        BufferedReader reader = null;
        Yaml yaml = new Yaml();
        try {
            if (isAsset) {
                AssetManager assetManager = context.getAssets();
                reader = new BufferedReader(new InputStreamReader(
                        assetManager.open(fileName)));
            } else {
                File file = new File(fileName);
                if (file != null && file.exists() && file.isFile()) {
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                }
            }
            /** 先把yml转成map */
            Map<String, Object> map = yaml.loadAs(reader, Map.class);
            if (map != null) {
                /** 再把map转换成json字符串 */
                String jsonString = JSON.toJSONString(map);
                if (!TextUtils.isEmpty(jsonString)) {
                    /** 再把json字符串转换成dataBean */
                    return JSON.parseObject(jsonString, clazz);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(reader);
        }
        return null;
    }

    /**
     * 获取数据源
     * @param context
     * @param fileName
     * @param isAsset 是否为asset文件
     * @return
     */
    private static <T> T parseJson(Context context, String fileName, boolean isAsset, Class<T> clazz) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = null;
        try {
            if (isAsset) {
                AssetManager assetManager = context.getAssets();
                reader = new BufferedReader(new InputStreamReader(
                        assetManager.open(fileName)));
            } else {
                File file = new File(fileName);
                if (file != null && file.exists() && file.isFile()) {
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                }
            }
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String dataString = stringBuilder.toString();
            if (!TextUtils.isEmpty(dataString)) {
                return JSON.parseObject(dataString, clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(reader);
        }
        return null;
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

}
