package org.bgbm.biovel.drf.client.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.bgbm.biovel.drf.client.data.ChecklistInfo;
import org.bgbm.biovel.drf.client.data.SciName;
import org.bgbm.biovel.drf.client.data.SciName.Scrutiny;
import org.bgbm.biovel.drf.client.data.SciName.Source;

import name.pehl.totoe.xml.client.Attribute;
import name.pehl.totoe.xml.client.CDATA;
import name.pehl.totoe.xml.client.Document;
import name.pehl.totoe.xml.client.Element;
import name.pehl.totoe.xml.client.Node;
import name.pehl.totoe.xml.client.NodeType;
import name.pehl.totoe.xml.client.Text;
import name.pehl.totoe.xml.client.XmlParser;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;



public class DRFUtils {

	public static final String QUERY_XPATH = "/default:tnrMsg/default:query";
	public static final String INPUT_NAME_XPATH = "./default:tnrRequest/default:taxonName/default:name/default:nameComplete/text()";
	public static final String SYN_RESPONSE_XPATH = "./default:tnrResponse";
	public static final String CHKLIST_ATTR_XPATH = "./@checklist";
	public static final String CHKLIST_URL_ATTR_XPATH = "./@checklist_url";
	public static final String CHKLIST_VERSION_ATTR_XPATH = "./@checklist_version";
	public static final String CHKLIST_CITATION_ATTR_XPATH = "./@checklist_citation";
	public static final String ACC_NAME_XPATH = "./default:acceptedName[1]";
	public static final String CLASSIFICATION_XPATH = "./default:classification";
	public static final String SYN_NAME_XPATH = "./default:synonym";
	public static final String NAME_XPATH = "./default:taxonName/default:name/default:nameComplete/text()";
	public static final String SRC_NAME_XPATH = "./default:source/default:name/text()";
	public static final String SRC_URL_XPATH = "./default:source/default:url/default:text()";
	public static final String SRC_DATASETID_XPATH = "./default:source/default:datasetID/text()";
	public static final String SRC_DATASETNAME_XPATH = "./default:source/default:datasetName/text()";
	public static final String SCRUTINY_ACCTO_XPATH = "./default:scrutiny/default:accordingTo/text()";
	public static final String SCRUTINY_MODIFIED_XPATH = "./default:scrutiny/default:modified/text()";
	public static final String INFO_URL_XPATH = "./default:info/default:url/text()";
	public static HashMap<String, ChecklistInfo> checklistInfoMap = new HashMap<String, ChecklistInfo>();

	public static String lastEntryId = null;
	public static HashSet buildNames(List synreqres_list) {

		Iterator<String> itrSynreqres = synreqres_list.iterator();
		HashSet<SciName> nameSet = new HashSet<SciName>();
		String xml;
		
		while (itrSynreqres.hasNext()) {
			xml = itrSynreqres.next();						
			Document document = new XmlParser().parse(xml,"xmlns:default=\"http://bgbm.org/biovel/drf/tnr/msg\"");		
			
			List<Node> queries = document.selectNodes(QUERY_XPATH);
			Iterator itrQueries = queries.iterator();
			while(itrQueries.hasNext()) {
				Node query = (Node)itrQueries.next();
				SciName sn = new SciName();
				Text iName = (Text)query.selectNode(INPUT_NAME_XPATH);
				sn.name = iName.getText().trim();
				
				List<Node> responses = query.selectNodes(SYN_RESPONSE_XPATH);
				Iterator itrResponses = responses.iterator();
				while(itrResponses.hasNext()) {
					Node responseNode = (Node)itrResponses.next();

					Attribute chklistattr = (Attribute)responseNode.selectNode(CHKLIST_ATTR_XPATH);
					Attribute chklisturlattr = (Attribute)responseNode.selectNode(CHKLIST_URL_ATTR_XPATH);
					Attribute chklistverattr = (Attribute)responseNode.selectNode(CHKLIST_VERSION_ATTR_XPATH);				
					Attribute chklistciteattr = (Attribute)responseNode.selectNode(CHKLIST_CITATION_ATTR_XPATH);
					

					checklistInfoMap.put(chklistattr.getText(), 
							new ChecklistInfo(chklistattr.getText(),
									chklisturlattr.getText(), 
									(chklistverattr == null)?"":chklistverattr.getText(), 
											(chklistciteattr == null)?"":chklistciteattr.getText()));

					
					Node accName = (Node)responseNode.selectNode(ACC_NAME_XPATH);	
					SciName acceptedName = new SciName();

					if (accName != null) {					
						Text aName = (Text)accName.selectNode(NAME_XPATH);					
						sn.setAccName(chklistattr.getText(), acceptedName);
						acceptedName.name = aName.getText().trim();
						
						Element classification = (Element)accName.selectNode(CLASSIFICATION_XPATH);
						if(classification != null) {						
							List<Element> rankValues = classification.getChildren(NodeType.ELEMENT);
							Iterator itrRankValues = rankValues.iterator();
							while(itrRankValues.hasNext()) {
								Element rankNode = (Element)itrRankValues.next();							
								acceptedName.addClassification(rankNode.getName(), rankNode.getText());
							}
						}										
						Text srcName = (Text)accName.selectNode(SRC_NAME_XPATH);
						String srcNameStr = (srcName == null)?"":srcName.getText();

						//Text srcUrl = (Text)accName.selectNode(SRC_URL_XPATH);
						Text srcUrl = (Text)accName.selectNode(INFO_URL_XPATH);
						String srcUrlStr = (srcUrl == null)?"":srcUrl.getText();                        

						Text srcDid = (Text)accName.selectNode(SRC_DATASETID_XPATH);
						String srcDidStr = (srcDid == null)?"":srcDid.getText();  

						Text srcDname = (Text)accName.selectNode(SRC_DATASETNAME_XPATH);
						String srcDnameStr = (srcDname == null)?"":srcDname.getText();  

						SciName.Source source = acceptedName.new Source(srcNameStr, srcUrlStr, srcDidStr, srcDnameStr);
						acceptedName.setSource(source);

						Text scrutinyAccTo = (Text)accName.selectNode(SCRUTINY_ACCTO_XPATH);
						String scrutinyAccToStr = (scrutinyAccTo == null)?"":scrutinyAccTo.getText();  

						Text scrutinyModified = (Text)accName.selectNode(SCRUTINY_MODIFIED_XPATH);
						String scrutinyModifiedStr = (scrutinyModified == null)?"":scrutinyModified.getText();  

						SciName.Scrutiny scrutiny = acceptedName.new Scrutiny(scrutinyAccToStr, scrutinyModifiedStr);
						acceptedName.setScrutiny(scrutiny);

					}

					List<Node> synonyms = responseNode.selectNodes(SYN_NAME_XPATH);
					if (synonyms.isEmpty()) {
						sn.setNullSynList(chklistattr.getText());
					} else {
						//should not happen, but can
						if (accName == null) {
							acceptedName.name = "";
							sn.setAccName(chklistattr.getText(), acceptedName);
						}
						Iterator itrSynonyms = synonyms.iterator();
						while(itrSynonyms.hasNext()) {
							Node syn = (Node)itrSynonyms.next();
							Text sName = (Text)syn.selectNode(NAME_XPATH);
							SciName synonymName = new SciName();                        		                        		                             
							synonymName.name = sName.getText().trim();     
							
							sn.addSynName(chklistattr.getText(), synonymName);

							Text srcName = (Text)syn.selectNode(SRC_NAME_XPATH);
							String srcNameStr = (srcName == null)?"":srcName.getText();

							//srcUrl = (Text)syn.selectNode(SRC_URL_XPATH);
							Text srcUrl = (Text)syn.selectNode(INFO_URL_XPATH);
							String srcUrlStr = (srcUrl == null)?"":srcUrl.getText();                        

							Text srcDid = (Text)syn.selectNode(SRC_DATASETID_XPATH);
							String srcDidStr = (srcDid == null)?"":srcDid.getText();  

							Text srcDname = (Text)syn.selectNode(SRC_DATASETNAME_XPATH);
							String srcDnameStr = (srcDname == null)?"":srcDname.getText();  


							SciName.Source source = synonymName.new Source(srcNameStr, srcUrlStr, srcDidStr, srcDnameStr);
							synonymName.setSource(source);

							Text scrutinyAccTo = (Text)syn.selectNode(SCRUTINY_ACCTO_XPATH);
							String scrutinyAccToStr = (scrutinyAccTo == null)?"":scrutinyAccTo.getText();  

							Text scrutinyModified = (Text)syn.selectNode(SCRUTINY_MODIFIED_XPATH);
							String scrutinyModifiedStr = (scrutinyModified == null)?"":scrutinyModified.getText();  

							SciName.Scrutiny scrutiny = synonymName.new Scrutiny(scrutinyAccToStr, scrutinyModifiedStr);
							synonymName.setScrutiny(scrutiny);
						}
					}					
				}			

				nameSet.add(sn);
			}
		}
		return nameSet;
	}
	
	public static String getNewInteractionPageUrl(String feedXml) {
		
	    final String PRESENTATION_REL = "presentation";
	    final String ENTRY_LINK_XPATH = "/feed/entry/link";
	    final String HREF_ATTR_XPATH = "./@href";
	    final String REL_ATTR_XPATH = "./@rel";
	    final String ENTRY_ID_XPATH = "../id";
		String newUrl = null;
		
		String newEntryId = null;
		
		Document document = new XmlParser().parse(feedXml);
		List<Node> entryLinkNodes = (List<Node>)document.selectNodes(ENTRY_LINK_XPATH);

		Iterator itrEntryLinks = entryLinkNodes.iterator();
		while(itrEntryLinks.hasNext()) {
			Node elNode = (Node)itrEntryLinks.next();
			Attribute relAttrNode = (Attribute)elNode.selectNode(REL_ATTR_XPATH);                 
			String relattr = relAttrNode.getText();
            String hrefattr = null;
                 if (relattr.equals(PRESENTATION_REL)) {
                	 Attribute hrefAttrNode = (Attribute)elNode.selectNode(HREF_ATTR_XPATH);
                     hrefattr = hrefAttrNode.getText();

                     Text entryIdNode = (Text)elNode.selectNode(ENTRY_ID_XPATH);                   
                     String entryId = entryIdNode.getText();

                     if (newEntryId == null) {
                         newEntryId = entryId;
                     }

                     if (lastEntryId == null) {
                         lastEntryId = newEntryId;
                     }
                     
                     //System.out.println("Last : " + lastEntryId + ", " + "New : " + newEntryId);
                     if (entryId.equals(lastEntryId)) {
                         break;
                     }

                     newUrl = hrefattr;

                     
                 }
             }
             if (newEntryId != null) {
                 lastEntryId = newEntryId;
             }

		return newUrl;
	}


}
