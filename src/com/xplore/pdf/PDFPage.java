package com.xplore.pdf;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

public class PDFPage extends Thread{
	
	public static void main(String[] args) throws IOException, DocumentException, InterruptedException {
		long start = System.currentTimeMillis();
		int pageNumber = PDFPage.getPage(args[0]);
		System.out.println(args[0]+" has "+pageNumber +" pages");
		for(int i = 1 ; i <= 10 ; i ++){
			System.out.println(i);
			PDFPage p = new PDFPage(args[0], i);
			p.start();
		}
		
		System.out.println(args[0]+" has been cut into "+pageNumber +" pages");
		System.out.println("It spent "+ (System.currentTimeMillis()-start) + " ms to do so.");	}
	
	//small pdf
	
	private String filepath;
//	String fileName;
	private static  int page;
	private int i;
	
	public String getFilepath() {
		return filepath;
	}

	public static int getPage(String filepath) throws IOException {
        PdfReader template = PDFReaderSingle.getInstance(filepath);
        page = template.getNumberOfPages();
        return page;
	}

	public PDFPage( String filepath , int i) throws IOException{
		this.filepath = filepath;
		this.i = i;

	}
	
		public void pagingPDF() throws IOException, DocumentException{
			System.out.println(i+" starts to cut");
	        PdfReader template = new PdfReader(filepath);
	        ByteArrayOutputStream opsTemp = new ByteArrayOutputStream();
	        PdfStamper stamp = new PdfStamper(template, opsTemp);
	        
	        String filePrex = filepath.substring(0, filepath.length() - 4 );
            Document document = new Document(PageSize.LETTER, 0, 0, 0,0 );
            document.open();
            OutputStream ops = new FileOutputStream(filePrex + "-page-"+i+".pdf");
            System.out.println(filePrex + "-page-"+i+".pdf");
            PdfWriter writer = PdfWriter.getInstance(document, ops);
            AcroFields form = stamp.getAcroFields();
            form.setField("XXX", "XXX");
            stamp.setFormFlattening(true);
            stamp.close();
            PdfReader data = new PdfReader(opsTemp.toByteArray());
            PdfImportedPage page = writer.getImportedPage(data,i);
            Image image = Image.getInstance(page);
            document.open();
            document.add(image);
            document.close();
            opsTemp.close();
            ops.close();  
		}

		@Override
		public void run() {
			try {
				pagingPDF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
