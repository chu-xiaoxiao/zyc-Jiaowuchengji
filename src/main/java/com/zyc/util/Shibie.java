package com.zyc.util;

import java.io.File;
import java.io.IOException;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Shibie {
	public static String getText(File imageFile){
		System.out.println("========================");
		System.out.println("识别验证码");
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        String result = null;
        try {
        	result = instance.doOCR(imageFile);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("========================");
        return result.substring(0,4);
	}
}
