package io.bigseaken.github.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


public enum JsonConfig {
	
	INSTANCE;
	public String getHost() throws Exception{
		Properties properties = new Properties();
		File file = new File("");
		File file2 = new File(file.getCanonicalPath()+"\\config.properties");
		InputStream in = new FileInputStream(file2);
		properties.load(in);
		String value = properties.getProperty("host");
		in.close();
		return value;
	}
	public int getProt() throws Exception{
		Properties properties = new Properties();
		File file = new File("");
		File file2 = new File(file.getCanonicalPath()+"\\config.properties");
		InputStream in = new FileInputStream(file2);
		properties.load(in);
		String value = properties.getProperty("prot");
		in.close();
		return Integer.parseInt(value);
	}
	
	public static void main(String[] args)throws Exception {
		JsonConfig.INSTANCE.getProt();
	}
	
}
