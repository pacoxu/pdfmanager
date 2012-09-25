package com.xplore.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

public class AdvancedPDFKnife {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
        long start = System.currentTimeMillis();
		cutPDFintoPages(args[0]);
		System.out.println("It spent "+ (System.currentTimeMillis()-start) + " ms.");
		System.out.println("It spent "+ (System.currentTimeMillis()-start)/1000.0 + " s.");
		System.out.println("It spent "+ (System.currentTimeMillis()-start)/60000.0 + " min.");
		
	}

	private static void cutPDFintoPages(String pdfpath) {

        long start = System.currentTimeMillis();
		
		//whether the filepath exists
		//whether it is a file or a directory
		//whether its format is PDF.

		//File size
		File tem = new File(pdfpath);
		long size = tem.length();
		System.out.println("this pdf's size is "+ size +" bytes.");
		System.out.println("this pdf's size is "+ size/1000.0 +" KB.");
		System.out.println("this pdf's size is "+ size/1000000.0 +" MB.");

		
		PdfReader reader = null;
		try {
			//before it format the file path // \ /
			reader = new PdfReader(pdfpath);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
        int page = reader.getNumberOfPages();
		System.out.println("this pdf have "+page+" pages.");
        
        Document document = null;  
		PdfCopy copy = null;  
		try   
		{  
			String directory = pdfpath.substring(0, pdfpath.lastIndexOf("\\")+1); 
			String savePre = pdfpath.substring(pdfpath.lastIndexOf("\\")+1, pdfpath.length()-4);  
			ArrayList<String> savepaths = new ArrayList<String>();  
			for( int i = 1; i <= page;  i ++ )  
			{  
				String savePath = directory + savePre + "-page-" + i + ".pdf";  
				savepaths.add(savePath);                      
			}     
			for( int i = 1; i < page; i++)  
			{
				document = new Document(reader.getPageSize(1));  
				copy = new PdfCopy(document, new FileOutputStream(savepaths.get(i)));             
				document.open();  
				document.newPage();   
				PdfImportedPage pagei = copy.getImportedPage(reader, i);  
				copy.addPage(pagei);  
				document.close();  
			}  
		}
		catch (IOException e)
		{  
			e.printStackTrace();  
		}
		catch(DocumentException e) 
		{  
			e.printStackTrace();  
		}  
	}

	
	
}
