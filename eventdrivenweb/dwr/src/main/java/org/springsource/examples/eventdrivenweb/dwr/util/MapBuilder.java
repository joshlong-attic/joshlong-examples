package org.springsource.examples.eventdrivenweb.dwr.util;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Surely, somebody else has written something like this?
 *
 * @since 1.0
 * @author Josh Long
 * @param <K>
 * @param <V>
 */
public class MapBuilder<K, V> {
    private Logger logger = Logger.getLogger(MapBuilder.class);
    private Map<K, V> map;

    public MapBuilder() {
        this.map = new ConcurrentHashMap<K, V>();
    }

    public MapBuilder(Class<? extends Map<K, V>> c) {
        try {
            this.map = c.newInstance();
        } catch (Throwable e) {
            logger.debug(e);
        }
    }

	public MapBuilder<K,V> putAll(Map<K,V> mv){
		this.map.putAll( mv);
		return this;
	}

    public MapBuilder<K, V> put(K k, V v) {
        this.map.put(k, v);
        return this;
    }

    public Map<K, V> toMap() {
        return this.map;
    }

	public static <K,V> MapBuilder<K,V> newMap(){
		return new MapBuilder<K,V>();
	}
}
