package com.zyc.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class MyProcessor implements PageProcessor{
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
	public Site getSite() {
		return this.site;
	}

	public void process(Page page) {
		page.putField("juzi", page.getHtml().$("a.xlistju").all());
		page.putField("juzixpth", page.getHtml().xpath("//a[@class=xlistju]/text()").all());
		page.putField("links", page.getHtml().links().all());
	}
	public static void main(String[] args) {
		Spider.create(new MyProcessor())
		.addUrl("http://www.juzimi.com/tags/%E5%94%AF%E7%BE%8E")
		.addPipeline(new JsonFilePipeline("e:\\webmagic\\"))
		.thread(5)
		.run();
	}
}
