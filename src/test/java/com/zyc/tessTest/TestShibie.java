package com.zyc.tessTest;

import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TestShibie {
	public static void main(String[] args) {
		File imageFile = new File("yanzhengma.jpg");
		ITesseract iTesseract = new Tesseract();
		String result;
		try {
			result = iTesseract.doOCR(imageFile);
			System.out.println(result);
		} catch (TesseractException e) {
			e.printStackTrace();
		}
	}
	public static String getText(File imageFile){
		ITesseract iTesseract = new Tesseract();
		String result = null;
		try {
			result = iTesseract.doOCR(imageFile);
			System.out.println(result);
		} catch (TesseractException e) {
			e.printStackTrace();
		}
		return result;
	}	
}
