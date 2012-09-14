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
		File f = new File(filepath);
		this.filepath = filepath;
	}
	
	public void pagingPDF() throws IOException, DocumentException{
        PdfReader template1 = new PdfReader(filepath);
        page = template1.getNumberOfPages();
        for (int i = 1; i <= page; i++) {
            PdfReader template = new PdfReader(filepath);
            Document document = new Document(PageSize.LETTER, 0, 0, 0,0 );
            document.open();
            OutputStream ops = new FileOutputStream(filepath+"-page-"+i+".pdf");
            PdfWriter writer = PdfWriter.getInstance(document, ops);
            ByteArrayOutputStream opsTemp = new ByteArrayOutputStream();
            PdfStamper stamp = new PdfStamper(template, opsTemp);
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
