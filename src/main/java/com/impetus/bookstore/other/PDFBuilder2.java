package com.impetus.bookstore.other;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.impetus.bookstore.model.BookDetails;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

// TODO: Auto-generated Javadoc
/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 * 
 * @author www.codejava.net
 * 
 */
public class PDFBuilder2 extends AbstractITextPdfView {

    /** The Constant logger. */
    private static final Logger LOG = Logger.getLogger(PDFBuilder.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.impetus.bookstore.other.AbstractITextPdfView#buildPdfDocument(java
     * .util.Map, com.itextpdf.text.Document, com.itextpdf.text.pdf.PdfWriter,
     * javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            // get data model which is passed by the Spring container
            List<BookDetails> listBooks = (List<BookDetails>) model
                    .get("listBooks");
            List delivery = (List) model.get("delivery");
            List returnb = (List) model.get("return");
            List cancel = (List) model.get("cancel");

            doc.add(new Paragraph("Books and their request counts"));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100.0f);
            table.setWidths(new float[] { 1.0f, 5.0f, 1.0f, 1.0f, 1.0f });
            table.setSpacingBefore(10);

            // define font for table header row
            Font font = FontFactory.getFont(FontFactory.HELVETICA);
            font.setColor(BaseColor.WHITE);

            // define table header cell
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(BaseColor.BLUE);
            cell.setPadding(5);

            // write table header
            cell.setPhrase(new Phrase("Book ID", font));
            table.addCell(cell);

            cell.setPhrase(new Phrase("Title", font));
            table.addCell(cell);

            cell.setPhrase(new Phrase("Delivery Count", font));
            table.addCell(cell);

            cell.setPhrase(new Phrase("Return Count", font));
            table.addCell(cell);

            cell.setPhrase(new Phrase("Cancel Count", font));
            table.addCell(cell);

            // write table row data

            BigInteger count;
            int i = 0;
            for (BookDetails aBook : listBooks) {

                table.addCell(String.valueOf(aBook.getBookid()));
                table.addCell(aBook.getName());

                count = (BigInteger) delivery.get(i);
                table.addCell(String.valueOf(count));

                count = (BigInteger) returnb.get(i);
                table.addCell(String.valueOf(count));

                count = (BigInteger) cancel.get(i);
                table.addCell(String.valueOf(count));
                i = i + 1;
            }
            LOG.debug("Returning pdf report");
            doc.add(table);
        } catch (Exception e) {
            LOG.error("Problem with generating pdf " + e.getCause());
        }
    }

}