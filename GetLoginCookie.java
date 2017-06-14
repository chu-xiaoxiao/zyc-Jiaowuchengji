package com.zyc.processor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zyc.util.Shibie;

public class GetLoginCookie {
	private static Header[] headers;
	private static CookieStore cookieStore = null;
	private static CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
	public static Header[] getHeaders() {
		return headers;
	}

	public static void setHeaders(Header[] headers) {
		GetLoginCookie.headers = headers;
	}
	
	public static CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public static void setHttpClient(CloseableHttpClient httpClient) {
		GetLoginCookie.httpClient = httpClient;
	}
	
	public static CookieStore getCookieStore() {
		return cookieStore;
	}

	public static void setCookieStore(CookieStore cookieStore) {
		GetLoginCookie.cookieStore = cookieStore;
	}

	public static void main(String[] args) throws IOException {
		// shibie();
		logIn("141201406", "162114");
		//findChengji();
	}
	
	public static void login(String name,String pwd) throws IOException {
		// shibie();
		logIn(name, pwd);
		//findChengji();
	}

	public static String getImgHttp() {
		try {
			Scanner reader = new Scanner(System.in);
			HttpGet httpGet = new HttpGet("http://jwc.sut.edu.cn/ACTIONVALIDATERANDOMPICTURE.APPPROCESS");
			HttpResponse response = httpClient.execute(httpGet);
			FileOutputStream fileOutputStream = new FileOutputStream(new File("yanzhengma.jpg"));
			response.getEntity().writeTo(fileOutputStream);
			fileOutputStream.close();
			System.out.println("获取成功");
			return reader.next();
			//return Shibie.getText(new File("yanzhengma.jpg"));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}

	public static void logIn(String name, String pwd) throws IOException {
		HttpGet httpGet = new HttpGet("http://jwc.sut.edu.cn/index.jsp");
		httpGet.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
		System.out.println("--------------------------------------");
		CloseableHttpResponse response1 = httpClient.execute(httpGet);
		for (Header headers : response1.getAllHeaders()) {
			System.out.println(headers);
		}
		HttpPost httpPost = new HttpPost("http://jwc.sut.edu.cn/ACTIONLOGON.APPPROCESS?mode=4");
		httpPost.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
		List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("WebUserNO", name));
		formparams.add(new BasicNameValuePair("Password", pwd));
		formparams.add(new BasicNameValuePair("Agnomen", getImgHttp()));
		formparams.add(new BasicNameValuePair("submit.x", "0"));
		formparams.add(new BasicNameValuePair("submit.y", "0"));
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
		httpPost.setEntity(urlEncodedFormEntity);
		System.out.println("executing req uest " + httpPost.getURI());
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			System.out.println("--------------------------------------");
			for (Header headers : response.getAllHeaders()) {
				System.out.println(headers);
			}
			System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
			System.out.println("--------------------------------------");
		}
		setcookies(response1);
	}
	public static void findChengji() throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost("http://jwc.sut.edu.cn/ACTIONQUERYSTUDENTSCORE.APPPROCESS?mode=2");
		httpPost.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
		List<BasicNameValuePair> parames = new ArrayList<BasicNameValuePair>();
		parames.add(new BasicNameValuePair("Cache-Control", "-1"));
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(parames, "UTF-8");
		httpPost.setEntity(urlEncodedFormEntity);
		httpPost.setEntity(urlEncodedFormEntity);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity encodedFormEntity = response.getEntity();
		// System.out.println(EntityUtils.toString(encodedFormEntity,"UTF-8"));
		Document document = Jsoup.parse(EntityUtils.toString(encodedFormEntity, "UTF-8"));
		System.out.println(document);
		Elements elements = document.getElementsByTag("tr");
		for (Element element : elements) {
			System.out.print(element.getElementsByIndexEquals(2).text() + "\t");
			System.out.println(element.getElementsByIndexEquals(10).text());
		}
	}
	public static void setcookies(HttpResponse response){
		System.out.println("=====setcookies");
		cookieStore = new BasicCookieStore();
		String setCookie = response.getFirstHeader("Set-Cookie").getValue();
		String JSESSIONID = setCookie.substring("JSESSIONID=".length(),setCookie.indexOf(";"));
		System.out.println("JSESSIONID:" + JSESSIONID);
	    BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",JSESSIONID);
		cookieStore.addCookie(cookie);
	}
}
