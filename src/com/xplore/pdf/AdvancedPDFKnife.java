package com.xplore.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

public class AdvancedPDFKnife {

	
	/**
	 * @param args[0] is the PDF file path
	 * @param args[1] is the password for the PDF
	 */
	public static void main(String[] args) {
		if(args.length == 0 ){
			System.out.println("Need a pdf filepath as argument");
			return;
		}
		
		
		//File size
		getFileSize(args[0]);
		
		long start = System.currentTimeMillis();
		//cut PDF
		cutPDFintoPages(args);
		long time  = System.currentTimeMillis() - start;
		if(time < 1000)
			System.out.println("It spent "+ time + " ms.");
		else if( time < 60000)
			System.out.println("It spent "+ time/1000.0 + " s.");
		else 
			System.out.println("It spent "+ time/60000.0 + " min.");
	}
		
	public static long getFileSize(String filepath){
		File tem = new File(filepath);
		long size = tem.length();
		if(size < 1000)
			System.out.println("This pdf's size is "+ size +" B.");
		else if( size < 1000000)
			System.out.println("This pdf's size is "+ size/1000.0 +" KB.");
		else if( size < 1000000000)
			System.out.println("This pdf's size is "+ size/1000000.0 +" MB.");
		else
			System.out.println("This pdf's size is "+ size/1000000000.0 +" GB.");
		return size;		
	}

	private static void cutPDFintoPages(String[] args) {

		//whether the filepath exists
		//whether it is a file or a directory
		//whether its format is PDF.
		String pdfpath = args[0];
		PdfReader reader = null;
		try 
		{
			if(args.length > 1)
			{
				byte[] password = args[1].getBytes();
				//args[1] is the password
				reader = new PdfReader(pdfpath, password );
			}
			else
			{
				//before it format the file path // \ /
				reader = new PdfReader(pdfpath);
			}
				
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
			for( int i = 1; i <= page;  i ++ )  
			{  
				String savePath = directory + savePre + "-page-" + i + ".pdf";  
				document = new Document(reader.getPageSize(1));  
				copy = new PdfCopy(document, new FileOutputStream(savePath));             
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
