import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.xplore.pdf.PDFPaging;


public class ExampleProcessForBigPDF {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filename = "C:/task/task16-pdfpage/pdfmanager/test.pdf";
		PDFPaging p = new PDFPaging(filename);
		try {
			p.pagingPDF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int pageNumber = p.getPage();
		System.out.println(pageNumber);
		
	}

}
