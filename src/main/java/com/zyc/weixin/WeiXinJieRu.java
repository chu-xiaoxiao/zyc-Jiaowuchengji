package com.zyc.weixin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONPath;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
public class WeiXinJieRu implements PageProcessor{
	private Site site = Site.me().addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
	
	public static void main(String[] args) {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxdc7b235132a646f3&secret=be730572a1d70b706c1a6dd5f9c650ef";
		Spider.create(new WeiXinJieRu()).addUrl(url).thread(5).run();
	}
	public void process(Page page) {
		Html html = page.getHtml();
		Document document = Jsoup.parse(html.toString());
		String access_token= (String) JSONPath.read(document.getElementsByTag("body").text(), "$.access_token");
		
	}
	public Site getSite() {
		return this.site;
	}
}
