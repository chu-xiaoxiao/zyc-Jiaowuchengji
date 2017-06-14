package com.zyc.processor;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zyc.util.SendMail;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class LoginProcessor implements PageProcessor{
	private Site site = Site.me().addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
	private static boolean flag = false;
	private static Integer Startcounter = 0;
	public Site getSite() {
		return this.site;
	}

	public void process(Page arg0) {
		Integer counter = 0;
		Html html = arg0.getHtml();
		Document document = Jsoup.parse(html.toString());
		StringBuffer stringBuffer = new StringBuffer();
		StringBuffer stringBuffer1 = new StringBuffer();
		try {
			Elements elements = document.getElementsByClass("tableborder");
			for(Element element :elements){
				Elements elements2 = element.getElementsByTag("tr");
				for(Element element3 : elements2){
					Elements elements3 = element3.getElementsByTag("td");
					if(!"".equals(elements3.get(11).text())&&!"总成绩".equals(elements3.get(11).text())){
						stringBuffer1.append(elements3.get(2).text()+elements3.get(11).text()+"\n\r");
						stringBuffer.append(elements3.get(2).text()+"\n\r");
						counter++;
					}
				}
			}
			System.out.println(counter+"==="+Startcounter);
			if(flag ==false){
				Startcounter=counter;
				SendMail sendMail = new SendMail();
				sendMail.sendmain("对教务处成绩的更新检测开启", "开启成功当前成绩为"+stringBuffer,"605953003@qq.com");
				sendMail = new SendMail();
				sendMail.sendmain("对教务处成绩的更新检测开启", "开启成功当前成绩为"+stringBuffer1,"739781049@qq.com");
				System.err.println("检测");
				flag = true;
			}
			if(counter>Startcounter){
				Startcounter=counter;
				SendMail sendMail = new SendMail();
				sendMail.sendmain("对成绩的检测更新!!!!!～～～～～～", "当前成绩为"+stringBuffer,"605953003@qq.com");
				sendMail = new SendMail();
				sendMail.sendmain("对成绩的检测更新!!!!!～～～～～～", "当前成绩为"+stringBuffer1,"739781049@qq.com");
				System.out.println("新");
			}
			System.out.println("更新检测"+new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void login() throws IOException {
		boolean flag = false;
		System.out.println("输入学号和密码");
		Scanner scanner = new Scanner(System.in);
		//GetLoginCookie.login(scanner.next().trim(),scanner.nextLine().trim());
		GetLoginCookie.login("141201406", "162114");
		LoginProcessor loginProcessor = new LoginProcessor();
		//设置登陆后的cookie
		loginProcessor.getSite().addCookie(GetLoginCookie.getCookieStore().getCookies().get(0).getName(), GetLoginCookie.getCookieStore().getCookies().get(0).getValue());
		while(true){
			Spider.create(loginProcessor).addUrl("http://jwc.sut.edu.cn/ACTIONQUERYSTUDENTSCORE.APPPROCESS?mode=2&YearTermNO=14").thread(1).run();
			if(flag==false){
				LoginProcessor.Startcounter=0;
				flag = true;
			}
			try {
				Thread.sleep(1000*60*10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
