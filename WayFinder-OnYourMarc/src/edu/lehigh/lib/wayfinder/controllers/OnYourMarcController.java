package edu.lehigh.lib.wayfinder.controllers;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.marc4j.MarcReader;
import org.marc4j.MarcStreamWriter;
import org.marc4j.MarcWriter;
import org.marc4j.MarcXmlReader;
import org.marc4j.MarcXmlWriter;
import org.marc4j.marc.DataField;
import org.marc4j.marc.MarcFactory;
import org.marc4j.marc.Record;
import edu.lehigh.lib.wayfinder.constants.WayFinderConstants;

@WebServlet("/upload")
@MultipartConfig
public class OnYourMarcController extends HttpServlet {

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("WEB-INF/onyourmarc.jsp");
		request.setAttribute("messagestyle","visibility: hidden !important;");
		rd.forward(request, response);	
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
		System.err.println("In OnYourMarcController's doPost method");
		if ( description == null  || description.trim().length() > 0 ) {
			RequestDispatcher rd = null;
			rd = request.getRequestDispatcher("WEB-INF/onyourmarc.jsp");
			request.setAttribute("messagestyle","visibility: hidden !important;");
			rd.forward(request, response);
		} else {
			Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
			InputStream fileContent = filePart.getInputStream();

			Workbook workbook = new XSSFWorkbook(fileContent);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();
			//SKIPPING THE FIRST ROW
			iterator.next();
			MarcFactory factory = MarcFactory.newInstance();


			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
			String shortName = WayFinderConstants.onyourmarcFilesPrefix + dateFormat.format(new Date()) +".mrc";
			String fileName = WayFinderConstants.onyourmarcFilesPath + "/" + shortName;
			File file = new File(fileName); 

			FileOutputStream fileOutputStream = new FileOutputStream(file);
			//MarcWriter writer = null;
			//writer = new MarcXmlWriter(fileOutputStream, true);
			MarcWriter writer = new MarcStreamWriter(fileOutputStream);



			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				/*Iterator<Cell> cellIterator = nextRow.cellIterator();



            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                //DataFormatter df = new DataFormatter();
                //String value = df.formatCellValue(cell);
                cell.setCellType(CellType.STRING);
                String value  = cell.getStringCellValue();
                System.out.println(value);


            }*/

				String isbn = null;
				String oclc = null;
				String vendor = null;
				String price = null;
				String fundCode = null;
				String object = null;
				String subObject = null;

				//TODO CAN SHORTEN THIS CODE LATER
				isbn = getValueFromCell(nextRow.getCell(0), CellType.STRING);
				oclc = getValueFromCell(nextRow.getCell(1),CellType.STRING);
				vendor = getValueFromCell(nextRow.getCell(2),CellType.STRING);
				price = getValueFromCell(nextRow.getCell(3),CellType.NUMERIC);
				fundCode = getValueFromCell(nextRow.getCell(4), CellType.STRING);
				object = getValueFromCell(nextRow.getCell(5), CellType.STRING);
				subObject = getValueFromCell(nextRow.getCell(6), CellType.STRING);

				if ( (isbn == null || isbn.trim().length() == 0) &&
						(oclc == null || oclc.trim().length() == 0) ) {
					System.out.println("No ISBN or OCLC number, skipping row: " + nextRow.toString());
				} else {

					System.out.println("will look up using ISBN: " + isbn + ", OCLC number: " + oclc);

					Client client = ClientBuilder.newClient();

					WebTarget webTarget;
					//IF SPREADSHEET CONTAINS OCLC NO. USE IT, OTHERWISE USE ISBN
					if (oclc != null) {
						webTarget = client.target("http://www.worldcat.org/webservices/catalog/content/" + oclc.replaceAll("\\u00A0", "") + "?wskey=QZSNU7WdFZRCu2YW9d0n9DkmHy6zgXT12fa3PICduTwpvMMmu9ndoUP3fn0kqOVWnAIvLlSYHFCocWoP");
						System.out.println("http://www.worldcat.org/webservices/catalog/content/" + oclc.trim() + "?wskey=QZSNU7WdFZRCu2YW9d0n9DkmHy6zgXT12fa3PICduTwpvMMmu9ndoUP3fn0kqOVWnAIvLlSYHFCocWoP");
					} else {
						webTarget = client.target("http://www.worldcat.org/webservices/catalog/content/isbn/" + isbn.trim() + "?wskey=QZSNU7WdFZRCu2YW9d0n9DkmHy6zgXT12fa3PICduTwpvMMmu9ndoUP3fn0kqOVWnAIvLlSYHFCocWoP");
					}

					Response r = webTarget.request(MediaType.APPLICATION_XML).get();
					System.out.println("THE RESPONSE FROM THE SERVICE WAS " + r.getStatus());		
					String body = r.readEntity(String.class);
					System.out.println(body);

					MarcReader reader = null;
					InputStream marcin =  new ByteArrayInputStream( body.getBytes("UTF-8") );
					try {
						reader = new MarcXmlReader(marcin);
					}
					catch(Exception e) {
						System.out.println(e.toString());
						//continue;
					}
					Record record = null;
					record = reader.next();
					//ADD 980 FIELD
					DataField df = factory.newDataField("980", ' ', ' ');
					//JUST TESTING WITH QUANTITY
					//TODO TALK TO DAN ABOUT p and q fields
					df.addSubfield(factory.newSubfield('b',fundCode));
					df.addSubfield(factory.newSubfield('m',price));
					df.addSubfield(factory.newSubfield('o',object));
					if (subObject != null) df.addSubfield(factory.newSubfield('n',subObject));
					df.addSubfield(factory.newSubfield('p', "1"));
					df.addSubfield(factory.newSubfield('q', "1"));
					//removed vendor per dan (for now)
					//if (vendor != null) df.addSubfield(factory.newSubfield('v', vendor));
					record.addVariableField(df);
					System.out.println(record.toString());

					//WRITE RECORD TO A FILE  
					writer.write(record);
				}

			}

			//EMAIL NEW FILE
			writer.close();

			//BETTER WAY TO RETURN THE FILE?
			FileInputStream in = new FileInputStream(fileName);
			response.setHeader("Content-Disposition", "attachment; filename='" + shortName + "'");
			OutputStream outputStream = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int length;
			while ((length = in.read(buffer)) > 0){
				outputStream.write(buffer, 0, length);
			}
			outputStream.flush();
			outputStream.close();
		}
	}

	private String getValueFromCell(Cell cell,CellType cellType) {

		if (cell == null) return null;


		cell.setCellType(cellType);

		//JUST HARDCODING STRING & DOUBLE RIGHT NOW
		//TODO

		if (cellType == CellType.STRING) {
			return cell.getStringCellValue().trim();
		}
		if (cellType == CellType.NUMERIC) {
			return new Double(cell.getNumericCellValue()).toString();
		}


		return null;
	}

}
