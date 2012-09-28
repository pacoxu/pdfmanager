package com.xplore.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BadPdfFormatException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

public class AdvancedPDFKnife {

	
	/**
	 * @param args[0] is the PDF file path
	 * @param args[1] is the password for the PDF
	 * @throws DocumentException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws DocumentException, InterruptedException {
		if(args.length == 0 ){
			System.out.println("Need a pdf filepath as argument");
			return;
		}
		
		
		//File size
		getFileSize(args[0]);
		
		long start = System.currentTimeMillis();
		AdvancedPDFKnife apk = new AdvancedPDFKnife();

		//if you want to run the multi-thread process
//		apk.setEnableMultiThread(true);
		
		//cut PDF
		apk.cutPDFintoPages(args);
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
	
	private PdfReader reader = null;

	private void cutPDFintoPages(String[] args) throws DocumentException, InterruptedException {

		//whether the filepath exists
		//whether it is a file or a directory
		//whether its format is PDF.
		String pdfpath = args[0];
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
        
		
		try   
		{  
			String directory = pdfpath.substring(0, pdfpath.lastIndexOf("\\")+1); 
			String savePre = pdfpath.substring(pdfpath.lastIndexOf("\\")+1, pdfpath.length()-4);  
			generatePDF(directory , savePre, page);
		}
		catch (IOException e)
		{  
			e.printStackTrace();  
		}

	}

	public void setEnableMultiThread(boolean enableMultiThread)
	{
		this.enableMultiThread = enableMultiThread;
	}

	private boolean enableMultiThread = false;
	public final static int BATCHSIZE = 100;
	private void generatePDF(String directory, String savePre, int page) throws IOException, DocumentException, InterruptedException 
	{
		
		ArrayList<CreatePDFThread> list = new ArrayList<CreatePDFThread>();

		if(enableMultiThread)
		{
			//1 - page
			if(page <= BATCHSIZE){
				CreatePDFThread cp = new CreatePDFThread(directory , savePre , 1, page);
				cp.run();
				list.add(cp);
			}else{
				while(page > BATCHSIZE){
					CreatePDFThread cp = new CreatePDFThread(directory , savePre , page -BATCHSIZE, page);
					cp.run();
					list.add(cp);
					page = page - 100;
				}
				CreatePDFThread cp = new CreatePDFThread(directory , savePre , 1, page);
				cp.run();
				list.add(cp);			
			}
			
			for(CreatePDFThread tem :	list){
				tem.join();
			}

		}
		else 
		{
			cutStartToEnd( directory, savePre, 1, page);
		}
	}
	class CreatePDFThread extends Thread{

		String directory;
		String savePre;
		int start;
		int end;
		
		CreatePDFThread(String directory, String savePre, int start, int end){
			this.directory = directory;
			this.savePre = savePre;
			this.start = start;
			this.end = end;
		}
		@Override
		public void run() {			
			cutStartToEnd( directory, savePre, start, end);
		}
		
	}

	public void cutStartToEnd(String directory, String savePre, int start, int end){
		Document document = null;  
		PdfCopy copy = null; 
		for( int i = start; i <= end;  i ++ )  
		{  

			String savePath = directory + savePre + "-page-" + i + ".pdf";  
			document = new Document(reader.getPageSize(1));  
			try {
				copy = new PdfCopy(document, new FileOutputStream(savePath));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}             
			document.open();  
			document.newPage();   
			PdfImportedPage pagei = copy.getImportedPage(reader, i);  
			try {
				copy.addPage(pagei);
			} catch (BadPdfFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}  
			document.close();  
		}  
	}
	
}
