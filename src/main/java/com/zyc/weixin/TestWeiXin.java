package com.zyc.weixin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.io.FileUtils;

import com.eastrobot.ask.sdk.AskRequest;
import com.eastrobot.ask.sdk.AskResponse;
import com.eastrobot.ask.sdk.AskService;
import com.eastrobot.ask.sdk.CloudNotInitializedException;
import com.eastrobot.ask.sdk.CloudServiceFactory;
import com.eastrobot.ask.sdk.RecogRequest;
import com.eastrobot.ask.sdk.RecogResponse;
import com.eastrobot.ask.sdk.RecogService;
import com.eastrobot.ask.sdk.SynthRequest;
import com.eastrobot.ask.sdk.SynthResponse;
import com.eastrobot.ask.sdk.SynthService;
import com.eastrobot.ask.utils.Constant;

public class TestWeiXin {

	public static void main(String[] args) throws MalformedURLException,
			IOException {
		String appKey = "Vwy5GJKzfWax";
		String appSecret = "K6VsWFwl2g49ZYe8GCEe";
		String question = "asdf";
		String exampleFile = "asdf";

		//智能问答
		AskRequest askRequest = new AskRequest(appKey, appSecret, question,
				Constant.SENIOR_TYPE, null, Constant.WEIXIN_PLATFORM);
		AskService askService = CloudServiceFactory.getInstance()
				.createAskService();
		askService.init(null);
		AskResponse askResponse = null;
		try {
			askResponse = askService.ask(askRequest);
		} catch (CloudNotInitializedException e) {
			e.printStackTrace();
		}

		//语音合成
		SynthRequest synthRequest = new SynthRequest(appKey, appSecret, null,
				question);
		SynthService synthService = CloudServiceFactory.getInstance()
				.createSynthService();
		synthService.init(null);
		SynthResponse synthResponse = null;
		try {
			synthResponse = synthService.synth(synthRequest);
		} catch (CloudNotInitializedException e) {
			e.printStackTrace();
		}

		//语音识别
		File file = new File(exampleFile);
		byte[] data = FileUtils.readFileToByteArray(file);
		RecogRequest recogRequest = new RecogRequest(appKey, appSecret, null,
				data);
		RecogService recogService = CloudServiceFactory.getInstance()
				.createRecogService();
		recogService.init(null);
		RecogResponse recogResponse = null;
		try {
			recogResponse = recogService.recog(recogRequest);
		} catch (CloudNotInitializedException e) {
			e.printStackTrace();
		}
	}

}