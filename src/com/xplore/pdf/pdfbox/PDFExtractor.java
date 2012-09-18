package com.xplore.pdf.pdfbox;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFExtractor {

	public static void main(String[] args) throws IOException {
		System.out.println("abc".substring(0,3));
		PDFExtractor p = new PDFExtractor();
		System.out.println(p.pages());
	}
		
	public int  pages() throws IOException{
		PDDocument pdd = PDDocument.load("C:/task/task16-pdfpage/pdfmanager/resource/20080407_Alfresco.pdf");
		List allPages = pdd.getDocumentCatalog().getAllPages();
		PDPage page = (PDPage)allPages.get(0);
		return allPages.size();
	}
}
