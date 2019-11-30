package io.github.syske.mybatisplus.util;

import java.util.Date;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @program: mybatisPlus
 * @description:
 * @author: syske
 * @create: 2019-11-30 12:17
 */
public class PropertiesFileUtil {
    private static HashMap<String, PropertiesFileUtil> configMap = new HashMap();
    private Date loadTime = null;
    private ResourceBundle resourceBundle = null;
    private static final Integer TIME_OUT = 60000;

    private PropertiesFileUtil(String name) {
        this.loadTime = new Date();

        try {
            this.resourceBundle = ResourceBundle.getBundle(name);
        } catch (Exception var3) {
            this.resourceBundle = null;
        }

    }

    public static synchronized PropertiesFileUtil getInstance() {
        return getInstance("config");
    }

    public static synchronized PropertiesFileUtil getInstance(String name) {
        PropertiesFileUtil conf = (PropertiesFileUtil)configMap.get(name);
        if (null == conf) {
            conf = new PropertiesFileUtil(name);
            configMap.put(name, conf);
        }

        if ((new Date()).getTime() - conf.getLoadTime().getTime() > (long)TIME_OUT) {
            conf = new PropertiesFileUtil(name);
            configMap.put(name, conf);
        }

        return conf;
    }

    public String get(String key) {
        try {
            String value = this.resourceBundle.getString(key);
            return value;
        } catch (MissingResourceException var3) {
            return "";
        } catch (NullPointerException var4) {
            return "";
        }
    }

    public Integer getInt(String key) {
        try {
            String value = this.resourceBundle.getString(key);
            return Integer.parseInt(value);
        } catch (MissingResourceException var3) {
            return null;
        } catch (NullPointerException var4) {
            return null;
        }
    }

    public boolean getBool(String key) {
        try {
            String value = this.resourceBundle.getString(key);
            return "true".equals(value);
        } catch (MissingResourceException var3) {
            return false;
        } catch (NullPointerException var4) {
            return false;
        }
    }

    public Date getLoadTime() {
        return this.loadTime;
    }

    public static String getPropertiesFileValue(String name, String key) {
        try {
            return getInstance(name).get(key);
        } catch (MissingResourceException var3) {
            return "";
        } catch (NullPointerException var4) {
            return "";
        }
    }
}
