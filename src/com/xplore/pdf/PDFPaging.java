package com.xplore.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

public class PDFPaging {
	
	public static void main(String[] args) throws IOException, DocumentException {
		cutPDF(args);
	}

	public	static void cutPDF(String[] args) throws IOException, DocumentException{
		long start = System.currentTimeMillis();
		//1.1 cut pdf to pages
//		String filename = "C:/task/task16-pdfpage/pdfmanager/resource/20080407_Alfresco.pdf";
		if(args == null || args.length == 0){
			System.out.println("XploreFeeder need a filepath as an input!");
			System.out.println("*.pdf");
		}
		if(!args[0].toLowerCase().endsWith(".pdf")){
			System.out.println("The file need to be PDF document.");
		}
		if(!new File(args[0]).exists()){
			System.out.println("Cannot find file "+args[0]+"in file system");
		}
		PDFPaging p = new PDFPaging(args[0]);
		p.pagingPDF();
		int pageNumber = p.getPage();
		System.out.println(args[0]+" has been cut into "+pageNumber +" pages");
		System.out.println("It spent "+ (System.currentTimeMillis()-start) + " ms to do so.");

	}
	
	private String filepath;
//	String fileName;
	private int page;

	public String getFilepath() {
		return filepath;
	}

	public int getPage() {
		return page;
	}

	public PDFPaging( String filepath){
		this.filepath = filepath;
	}

	
	//small pdf
	public void pagingPDF() throws IOException, DocumentException{
        PdfReader template = new PdfReader(filepath);
        ByteArrayOutputStream opsTemp = new ByteArrayOutputStream();
        PdfStamper stamp = new PdfStamper(template, opsTemp);

        String filePrex = filepath.substring(0, filepath.length() - 4 );
        page = template.getNumberOfPages();
        for (int i = 1; i <= page; i++) {
            Document document = new Document(PageSize.LETTER, 0, 0, 0,0 );
            document.open();
            OutputStream ops = new FileOutputStream(filePrex + "-page-"+i+".pdf");
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
	}
}
