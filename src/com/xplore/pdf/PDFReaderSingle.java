package com.xplore.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class PDFReaderSingle {

	private static PdfReader pdf;
	private static PdfStamper stamper;
	public static PdfReader getInstance(String filepath) throws IOException {
		if(pdf == null){
			pdf = new PdfReader(filepath);			
		}
		return pdf;
	}
	public static PdfStamper getStamper(String filepath) throws IOException, DocumentException {
		if(stamper == null){
			PdfReader template = PDFReaderSingle.getInstance(filepath);
			ByteArrayOutputStream opsTemp = new ByteArrayOutputStream();
			stamper = new PdfStamper(template, opsTemp);			
		}
		return stamper;
	}
	
	
}
