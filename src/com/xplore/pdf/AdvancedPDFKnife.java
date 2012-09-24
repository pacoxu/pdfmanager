package com.xplore.pdf;

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
		
	}

	private static void cutPDFintoPages(String pdfpath) {
		//whether the filepath exists
		//whether it is a file or a directory
		//whether its format is PDF.
		
		PdfReader reader = null;
		try {
			//before it format the file path // \ /
			reader = new PdfReader(pdfpath);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
        int page = reader.getNumberOfPages();
        
        Document document = null;  
		PdfCopy copy = null;  
		try   
		{  
			String directory = pdfpath.substring(0, pdfpath.lastIndexOf("\\")+1); 
			String savePre = pdfpath.substring(pdfpath.lastIndexOf("\\")+1, pdfpath.length()-4);  
			ArrayList<String> savepaths = new ArrayList<String>();  
			for( int i = 1; i <= page;  i ++ )  
			{  
				savePre = directory + savePre + "-page-" + i + ".pdf";  
				savepaths.add(savePre);                      
			}     
			for( int i = 0; i < page; i++)  
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