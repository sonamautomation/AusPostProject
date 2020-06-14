package messageObjects;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import com.tibco.tibjms.TibjmsConnectionFactory;

import DTO.TibcoMessageDetails;
import base.Base;


/**
 * @author ShettyD
 *
 */
public class TibcoMessaging extends Base{

	        List<String> sampleOrderList = new ArrayList<String>();
			public static Queue outputQueue; 

			/**
			 * @return
			 * @throws Exception
			 */
			public List<TibcoMessageDetails> getListOfOrdersFromTibco() throws Exception {
				return getMessages(getEnumeration(createQueueBrowser(connectTIBCO())));
			}




			/**
			 * @return
			 * @throws JMSException
			 */
			public Session connectTIBCO() throws JMSException {
				TibjmsConnectionFactory factory = new
						TibjmsConnectionFactory(tibcoURL); Connection connection
						= factory.createConnection(tibcoUserId,
								tibcoPassword);
						Session session = connection.createSession(false,
								javax.jms.Session.AUTO_ACKNOWLEDGE);
						return session;
			}

			/**
			 * @param session
			 * @return
			 * @throws JMSException
			 */
			public QueueBrowser createQueueBrowser(Session session) throws JMSException {
				outputQueue = session.createQueue(tibcoQueue);
				QueueBrowser browser = session.createBrowser(outputQueue);
				return browser;
			}

			/**
			 * @param browser
			 * @return
			 * @throws JMSException
			 */
			public Enumeration getEnumeration(QueueBrowser browser) throws JMSException {
				Enumeration enumeration = browser.getEnumeration();
				return enumeration;
			}

			/**
			 * @param enumeration
			 * @return
			 * @throws Exception
			 */
			public List<TibcoMessageDetails> getMessages(Enumeration enumeration) throws Exception {
				String businessRefId= null;
				String id=null;
				String total = null;

				List<TibcoMessageDetails> tibcoMessageDetailsList = new ArrayList<TibcoMessageDetails>();

				try {	
					while (enumeration.hasMoreElements()) {

						if(enumeration!=null) {
							Message m = (Message) enumeration.nextElement();
							if(!m.equals(null)) {		
								TextMessage message = (TextMessage) m;
								if(message.getText().contains("ONLINESHOP")) {
									DocumentBuilder builder =DocumentBuilderFactory.newInstance().newDocumentBuilder();
									InputSource src = new InputSource();
									src.setCharacterStream(new StringReader(message.getText()));

									Document doc = builder.parse(src);
									businessRefId = doc.getElementsByTagName("BusinessReferenceID").item(0).getTextContent();
									id= doc.getElementsByTagName("ID").item(0).getTextContent();				
									String productID = doc.getElementsByTagName("POSItemID").item(0).getTextContent();
									total = doc.getElementsByTagName("Total").item(0).getTextContent();
									TibcoMessageDetails tibcoMessageDetails = new TibcoMessageDetails();
									tibcoMessageDetails.setBusinessReferenceId(businessRefId);
									tibcoMessageDetails.setId(id);
									tibcoMessageDetails.setAmount(total);
									tibcoMessageDetails.setProductId(productID);
									tibcoMessageDetailsList.add(tibcoMessageDetails);

								}
							}}
					}
				} catch (Exception e) { System.out.print("ERROR!!!"); throw new Exception(e);
				}

				return tibcoMessageDetailsList;
			}




			public void validateTibcoMessagesFromOrderFiles() throws Exception {
			
				//Get list of all Orders from TIBCO 
				List<TibcoMessageDetails> tibcoMsgs =  getListOfOrdersFromTibco();

				for(String order :sampleOrderList ) {

					List<TibcoMessageDetails> matchedTibcoMessageDetails =  tibcoMsgs.stream().filter(s->Objects.equals(s.getBusinessReferenceId(),order)).collect(Collectors.toList());
					if(matchedTibcoMessageDetails.size()==0) {
						System.out.println("MESSAGE HAS NOT REACHED TO TIBCO FOR THE ORDER - "+order);
					}else {
						for(TibcoMessageDetails tibcoMsg:matchedTibcoMessageDetails) {
							if(tibcoMsg.getProductId().equals("")) {
								System.out.println("MESSAGE HAS REACHED TO TIBCO FOR :"+"  </br><b>ORDER NUMBER - </b> " +tibcoMsg.getBusinessReferenceId() + "     </br><b>ID - </b> " + tibcoMsg.getId()+ " </br><b>AMOUNT - </b> "+tibcoMsg.getAmount());
							}else {
								System.out.println("MESSAGE HAS REACHED TO TIBCO FOR :"+"  </br><b>ORDER NUMBER - </b> " +tibcoMsg.getBusinessReferenceId() + "     </br><b>ID - </b> " + tibcoMsg.getId()+" </br><b>ITEM ID - </b> "+tibcoMsg.getProductId()+ " </br><b>AMOUNT - </b> "+tibcoMsg.getAmount());
							}
						}
					}

				}
			}

}
