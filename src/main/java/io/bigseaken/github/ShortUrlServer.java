package io.bigseaken.github;

import io.bigseaken.github.service.HHXH;
import io.bigseaken.github.service.RedisServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class ShortUrlServer extends SpringBootServletInitializer {

	private RedisServer redisServer;
	
	/**
	 * order by regex url to redirect t 
	 * @param url
	 * @param respone
	 * @throws Exception
	 */
	@RequestMapping(value = "/{url:\\w*}", method = RequestMethod.GET)
	 void home(@PathVariable String url, HttpServletResponse respone) throws Exception {
		String redirectUrl = redisServer.get(url) == null
				|| "-1".equals(redisServer.get(url)) ? "http://www.baidu.com"
				: redisServer.get(url);
			respone.sendRedirect(redirectUrl);
	}
	@RequestMapping(value = "/",method = RequestMethod.GET)
	String index(){
		return "<h1>this's only for shortServer ......</h1><br/><hr/>"+
					 "<div>this server support for ba.iweiju.com </div>"+
					 "<div>Are you hava any problem ?take up you phone at 020-2222222</div>";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	 String getShortUrl(String url) throws Exception, IOException {
		String shortUrl = redisServer.get(url);
		if (null == shortUrl) {
			String[] shortUrls = HHXH.shortUrl(url);
			for (String str : shortUrls) {
				shortUrl = redisServer.get(str);
				if (!"".equals(redisServer.get(str))) {
					shortUrl = str;
					break;
				}
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put(url, shortUrl);
			map.put(shortUrl, url);
			String result = redisServer.setValue(map);
			System.out.println(result);
		}
		return "{'shortUrl:" + shortUrl + "','longUrl:" + url + "'}";
	}

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}
	public static void main(String[] args) {
		SpringApplication.run(ShortUrlServer.class, args);
	}
}