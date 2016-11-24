package io.bigseaken.github.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Pool {
	private static JedisPool pool;
	
	private final static String HOST = "";
	private final static int PROT = 6379;
	
	private static void createJedisPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(100);
		config.setMaxWait(1000);
		config.setMaxIdle(10);
		try {
			pool = new JedisPool(config, JsonConfig.INSTANCE.getHost(), JsonConfig.INSTANCE.getProt());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static synchronized void poolInit() {
		if (pool == null)
			createJedisPool();
	}

	public static Jedis getJedis() {
		if (pool == null)
			poolInit();
		return pool.getResource();
	}

	public static void returnJs(Jedis js) {
		pool.returnResource(js);
	}
	
}
