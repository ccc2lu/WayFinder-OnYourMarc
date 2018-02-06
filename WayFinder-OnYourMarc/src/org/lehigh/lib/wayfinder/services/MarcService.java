package org.lehigh.lib.wayfinder.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.CellType;
import org.marc4j.MarcReader;
import org.marc4j.MarcStreamWriter;
import org.marc4j.MarcWriter;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.DataField;
import org.marc4j.marc.MarcFactory;
import org.marc4j.marc.Record;
import edu.lehigh.lib.wayfinder.constants.WayFinderConstants;

@Path ("/marcservice")
public class MarcService {
	
	
	@Context
	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;
	   

	@POST
	@Produces("application/json")
	@Path("/singleMarcRecord")
	public Response getSingleMarcRecord(@PathParam("accountId") String accountId,MultivaluedMap<String,String> formParams) {		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss"); 
		String fn = WayFinderConstants.onyourmarcFilesPrefix + dateFormat.format(new Date()) +".mrc";
		//TODO FIX THIS -- GET DYNAMIC PATH SO USER CAN DOWNLOAD FILE
		File file = new File(WayFinderConstants.onyourmarcFilesPath + "/" + fn); 
		
		
		try {
			
            String isbn = formParams.getFirst("isbn");
            String oclc = formParams.getFirst("oclc");
            //String vendor = formParams.getFirst("isbn");
            String price = formParams.getFirst("price");
            String fundCode = formParams.getFirst("fundCode");
            String object = formParams.getFirst("object");
            String subObject = formParams.getFirst("subObject");
            
            
            Client client = ClientBuilder.newClient();
            
            WebTarget webTarget;
            //IF SPREADSHEET CONTAINS OCLC NO. USE IT, OTHERWISE USE ISBN
            if (oclc != null && !oclc.replaceAll("\\u00A0", "").trim().contentEquals("")) {
            	webTarget = client.target("http://www.worldcat.org/webservices/catalog/content/" + oclc.replaceAll("\\u00A0", "") + "?wskey=QZSNU7WdFZRCu2YW9d0n9DkmHy6zgXT12fa3PICduTwpvMMmu9ndoUP3fn0kqOVWnAIvLlSYHFCocWoP");
            	System.out.println("http://www.worldcat.org/webservices/catalog/content/" + oclc.trim() + "?wskey=QZSNU7WdFZRCu2YW9d0n9DkmHy6zgXT12fa3PICduTwpvMMmu9ndoUP3fn0kqOVWnAIvLlSYHFCocWoP");
            }
            else {
            	webTarget = client.target("http://www.worldcat.org/webservices/catalog/content/isbn/" + isbn.trim() + "?wskey=QZSNU7WdFZRCu2YW9d0n9DkmHy6zgXT12fa3PICduTwpvMMmu9ndoUP3fn0kqOVWnAIvLlSYHFCocWoP");
            }
            
            Response r = webTarget.request(MediaType.APPLICATION_XML).get();
            System.out.println("THE RESPONSE FROM THE SERVICE WAS " + r.getStatus());		
            String body = r.readEntity(String.class);
            System.out.println(body);
            


    		MarcFactory factory = MarcFactory.newInstance();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            MarcWriter writer = new MarcStreamWriter(fileOutputStream);
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
		    //REMOVE VENDOR FOR NOW PER DAN 12/7/2017
		    
		    //if (vendor != null) df.addSubfield(factory.newSubfield('v', vendor));
		    record.addVariableField(df);
		    System.out.println(record.toString());

			//WRITE RECORD TO A FILE  
			writer.write(record);
			writer.close();
			
			
		}
		catch(Exception e) {
			
		}
		//return Response.ok().build();
		//return Response.ok(WayFinderConstants.wayfinderFilesURL + "/" + fn).build();
		return Response.ok("https://" + WayFinderConstants.wayfinderhost + "/" + WayFinderConstants.wayfinderFilesURL + "/" + fn).build();
	}
	

}
