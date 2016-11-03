package com.angkorteam.mbaas.server.bean;

/**
 * Created by socheatkhauv on 10/26/16.
 */
public class GroovyClassLoader extends groovy.lang.GroovyClassLoader {

    public static final String PAGE = "page_";
    public static final String LAYOUT = "layout_";

    public void removeClassCache(String key) {
        if (this.classCache != null) {
            this.classCache.remove(key);
        }
    }

    public void removeSourceCache(String key) {
        if (this.sourceCache != null) {
            this.sourceCache.remove(key);
        }
    }

}