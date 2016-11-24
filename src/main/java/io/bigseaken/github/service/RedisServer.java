package io.bigseaken.github.service;

import io.bigseaken.github.util.Pool;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class RedisServer {
	
	private static  Jedis js = Pool.getJedis();
	private final static String SUCSSS = "success";
	private final static String EMTRY = "map  is emtry";
	private final static int TIMEOUT = 2*30*24*3600;
	
	
	/**
	 * from redis get value by key
	 * @param key
	 * @return
	 */
	public static  String get(String key){
		String value = ("".equals(key)&&key==null)?"-1":js.get(key);
		
		Pool.returnJs(js);
		return value;
	}
	
	/**
	 * map is null server cann't do anything
	 * @param map
	 * @return
	 */
	public static String setValue(Map<String,String> map){
		if(map==null){return EMTRY;}
		for(Map.Entry<String, String> m :map.entrySet()){
			js.set(m.getKey(),m.getValue());
			js.expire(m.getKey(), TIMEOUT);
		}
		Pool.returnJs(js);
		return SUCSSS;
	}
	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("zz", "xxx");
		RedisServer.setValue(map);
	}
	
}
