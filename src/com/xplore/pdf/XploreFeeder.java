package com.xplore.pdf;

import java.io.File;
import java.io.IOException;

import com.emc.documentum.core.fulltext.client.admin.api.FtAdminFactory;
import com.emc.documentum.core.fulltext.client.admin.api.IFtAdminService;
import com.emc.documentum.core.fulltext.common.admin.DSearchAdminException;
import com.lowagie.text.DocumentException;

public class XploreFeeder {
	/**
	 * @param args
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, DocumentException {
		
		//1.1 cut pdf to pages
//		String filename = "C:/task/task16-pdfpage/pdfmanager/resource/20080407_Alfresco.pdf";
		if(args == null){
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
		
		
		//1.2 feed in batch
		//ArrayList<String> fl = new ArrayList<String>();
		
		
//		String[] fl = new String[pageNumber];
//		for (int i = 1; i <= pageNumber; i++) {
//			fl[i-1] = args[0] + "-page-"+i+".pdf";
//		}
//		XploreFeeder t = new XploreFeeder();		
//		t.feedPages("10.37.10.223", 9300, "Pass123", fl);

	}
	
	public void feedPages(String host, int port, String password, String[] fl){
		IFtAdminService essi =null;
		try {
			essi = FtAdminFactory.getAdminService("http",host, port, password);
		} catch (DSearchAdminException e) {
			System.out.println("@@ catch exception");
		}
		String domain = "pdfDomain";
		String collection = "pdfCollection";
		String category = "dftxml";		
		String result = "";
		for(String filepath: fl){
			try {
				String xmlContent = generateDFTXML(filepath);
				System.out.println("@@ start upload");
				System.out.println(xmlContent);
				result= String.valueOf(
				essi.testIndexDocument(xmlContent ,domain,collection,category)
						);
				System.out.println("@@ Alreay upload");
//				result = getFingerprint(xml);
			} catch (DSearchAdminException e2) {
				System.out.println("@@ e");
			}
			
		}
	}

	private  String generateDFTXML(String filepath) {
		 String dftxml = xmlTemplate;
		 dftxml = dftxml.replaceAll("_DOC_ID_", generateDocIDForFile( filepath));
		 dftxml = dftxml.replaceAll("__DOC_CONTENT__", "file:///" + filepath.replace("\\", "/"));
		 File fileToWrite = new File(filepath);
	     dftxml = dftxml.replaceAll("_DOC_NAME_", fileToWrite.getName());
	     dftxml = dftxml.replaceAll("_USER_NAME_", "testuser");
//	     dftxml = dftxml.replaceAll("_CREATION_DATE_", creationdate);//_CREATION_DATE_ 
	     dftxml = dftxml.replaceAll("_CONTENT_TYPE_", "dm_document");
         return dftxml;
	}
		
	private  String generateDocIDForFile(String filepath){

	   	String specialChars = "=|'|\"|:|!|\\$|\\(|\\)|\\*|\\?|\\[|\\]|\\<|\\>|\\.|\\;|\\,|\\|";
	   	File file = new File(filepath);
	       if (file.exists() == false || file.isDirectory())
	       {
	           throw new IllegalArgumentException("The file path is not a valid file path:" + filepath);
	       }
	       //pattern: fileName+timestamp
	       return file.getName().replaceAll(specialChars, "") + System.currentTimeMillis();
	}
	
    private final  String xmlTemplate = 
    "<dmftdoc dmftkey=\"_DOC_ID_\" ess_tokens=\":_DOC_ID_:dmftdoc:1\">\n" +
    "  <dmftkey>_DOC_ID_</dmftkey>                         \n" +
    "  <dmftmetadata>\n" +
    "    <dm_sysobject>\n" +
    "      <r_object_id dmfttype=\"dmid\">_DOC_ID_</r_object_id>\n" +
    "      <object_name dmfttype=\"dmstring\">_DOC_NAME_</object_name>\n" +
    "      <r_object_type dmfttype=\"dmstring\">dm_document</r_object_type>\n" +
    "      <r_creation_date dmfttype=\"dmdate\">_CREATION_DATE_</r_creation_date>\n" +
    "      <r_modify_date dmfttype=\"dmdate\">_CREATION_DATE_</r_modify_date>\n" +
    "      <r_modifier dmfttype=\"dmstring\">_USER_NAME_</r_modifier>\n" +
    "      <r_access_date dmfttype=\"dmdate\"/>\n" +
    "      <a_is_hidden dmfttype=\"dmbool\">false</a_is_hidden>\n" +
    "      <i_is_deleted dmfttype=\"dmbool\">false</i_is_deleted>\n" +
    "      <a_retention_date dmfttype=\"dmdate\"/>\n" +
    "      <a_archive dmfttype=\"dmbool\">false</a_archive>\n" +
    "      <a_link_resolved dmfttype=\"dmbool\">false</a_link_resolved>\n" +
    "      <i_reference_cnt dmfttype=\"dmint\">1</i_reference_cnt>\n" +
    "      <i_has_folder dmfttype=\"dmbool\">true</i_has_folder>\n" +
    "      <i_folder_id dmfttype=\"dmid\">0c0c0b5580000105</i_folder_id>\n" +
    "      <r_link_cnt dmfttype=\"dmint\">0</r_link_cnt>\n" +
    "      <r_link_high_cnt dmfttype=\"dmint\">0</r_link_high_cnt>\n" +
    "      <r_assembled_from_id dmfttype=\"dmid\">0000000000000000</r_assembled_from_id>\n" +
    "      <r_frzn_assembly_cnt dmfttype=\"dmint\">0</r_frzn_assembly_cnt>\n" +
    "      <r_has_frzn_assembly dmfttype=\"dmbool\">false</r_has_frzn_assembly>\n" +
    "      <r_is_virtual_doc dmfttype=\"dmint\">0</r_is_virtual_doc>\n" +
    "      <i_contents_id dmfttype=\"dmid\">060c0b558000d942</i_contents_id>\n" +
    "      <a_content_type dmfttype=\"dmstring\">text</a_content_type>\n" +
    "      <r_page_cnt dmfttype=\"dmint\">1</r_page_cnt>\n" +
    "      <r_content_size dmfttype=\"dmint\">0</r_content_size>\n" +
    "      <a_full_text dmfttype=\"dmbool\">true</a_full_text>\n" +
    "      <a_storage_type dmfttype=\"dmstring\">filestore_01</a_storage_type>\n" +
    "      <i_cabinet_id dmfttype=\"dmid\">0c0c0b5580000105</i_cabinet_id>\n" +
    "      <owner_name dmfttype=\"dmstring\">_USER_NAME_</owner_name>\n" +
    "      <owner_permit dmfttype=\"dmint\">7</owner_permit>\n" +
    "      <group_name dmfttype=\"dmstring\">docu</group_name>\n" +
    "      <group_permit dmfttype=\"dmint\">5</group_permit>\n" +
    "      <world_permit dmfttype=\"dmint\">3</world_permit>\n" +
    "      <i_antecedent_id dmfttype=\"dmid\">0000000000000000</i_antecedent_id>\n" +
    "      <i_chronicle_id dmfttype=\"dmid\">_DOC_ID_</i_chronicle_id>\n" +
    "      <i_latest_flag dmfttype=\"dmbool\">true</i_latest_flag>\n" +
    "      <r_lock_date dmfttype=\"dmdate\"/>\n" +
    "      <r_version_label dmfttype=\"dmstring\">1.0</r_version_label>\n" +
    "      <r_version_label dmfttype=\"dmstring\">CURRENT</r_version_label>\n" +
    "      <i_branch_cnt dmfttype=\"dmint\">0</i_branch_cnt>\n" +
    "      <i_direct_dsc dmfttype=\"dmbool\">false</i_direct_dsc>\n" +
    "      <r_immutable_flag dmfttype=\"dmbool\">false</r_immutable_flag>\n" +
    "      <r_frozen_flag dmfttype=\"dmbool\">false</r_frozen_flag>\n" +
    "      <r_has_events dmfttype=\"dmbool\">false</r_has_events>\n" +
    "      <acl_domain dmfttype=\"dmstring\">_USER_NAME_</acl_domain>\n" +
    "      <acl_name dmfttype=\"dmstring\">dm_450c0b5580000101</acl_name>\n" +
    "      <i_is_reference dmfttype=\"dmbool\">false</i_is_reference>\n" +
    "      <r_creator_name dmfttype=\"dmstring\">_USER_NAME_</r_creator_name>\n" +
    "      <r_is_public dmfttype=\"dmbool\">true</r_is_public>\n" +
    "      <r_policy_id dmfttype=\"dmid\">0000000000000000</r_policy_id>\n" +
    "      <r_resume_state dmfttype=\"dmint\">0</r_resume_state>\n" +
    "      <r_current_state dmfttype=\"dmint\">0</r_current_state>\n" +
    "      <r_alias_set_id dmfttype=\"dmid\">0000000000000000</r_alias_set_id>\n" +
    "      <a_is_template dmfttype=\"dmbool\">false</a_is_template>\n" +
    "      <r_full_content_size dmfttype=\"dmdouble\">0</r_full_content_size>\n" +
    "      <a_is_signed dmfttype=\"dmbool\">false</a_is_signed>\n" +
    "      <a_last_review_date dmfttype=\"dmdate\"/>\n" +
    "      <i_retain_until dmfttype=\"dmdate\"/>\n" +
    "      <i_partition dmfttype=\"dmint\">0</i_partition>\n" +
    "      <i_is_replica dmfttype=\"dmbool\">false</i_is_replica>\n" +
    "      <i_vstamp dmfttype=\"dmint\">0</i_vstamp>\n" +
    "    </dm_sysobject>\n" +
    "  </dmftmetadata>\n" +
    "  <dmftvstamp>\n" +
    "    <i_vstamp dmfttype=\"dmint\">0</i_vstamp>\n" +
    "  </dmftvstamp>\n" +
    "  <dmftsecurity>\n" +
    "    <acl_name dmfttype=\"dmstring\">dm_450c0b5580000101</acl_name>\n" +
    "    <acl_domain dmfttype=\"dmstring\">_USER_NAME_</acl_domain>\n" +
    "    <ispublic dmfttype=\"dmbool\">true</ispublic>\n" +
    "  </dmftsecurity>\n" +
    "  <dmftinternal>\n" +
    "    <docbase_id dmfttype=\"dmstring\">789333</docbase_id>\n" +
    "    <server_config_name dmfttype=\"dmstring\">CCRepository</server_config_name>\n" +
    "    <contentid dmfttype=\"dmid\">060c0b558000d942</contentid>\n" +
    "    <r_object_id dmfttype=\"dmid\">_DOC_ID_</r_object_id>\n" +
    "    <r_object_type dmfttype=\"dmstring\">dm_document</r_object_type>\n" +
    "    <i_all_types dmfttype=\"dmid\">030c0b5580000129</i_all_types>\n" +
    "    <i_all_types dmfttype=\"dmid\">030c0b5580000105</i_all_types>\n" +
    "    <i_dftxml_schema_version dmfttype=\"dmstring\">5.3</i_dftxml_schema_version>\n" +
    "  </dmftinternal>\n" +
    "  <dmftversions>\n" +
    "    <r_version_label dmfttype=\"dmstring\">1.0</r_version_label>\n" +
    "    <r_version_label dmfttype=\"dmstring\">CURRENT</r_version_label>\n" +
    "    <iscurrent dmfttype=\"dmbool\">true</iscurrent>\n" +
    "  </dmftversions>\n" +
    "  <dmftfolders>\n" +
    "    <i_folder_id dmfttype=\"dmid\">0c0c0b5580000105</i_folder_id>\n" +
    "  </dmftfolders>\n" +
    "  <dmftcontents>\n" +
    "    <dmftcontent>\n" +
    "      <dmftcontentattrs>\n" +
    "        <r_object_id dmfttype=\"dmid\">060c0b558000d942</r_object_id>\n" +
    "        <page dmfttype=\"dmint\">0</page>\n" +
    "        <i_full_format dmfttype=\"dmstring\">text</i_full_format>\n" +
    "      </dmftcontentattrs>\n" +
    "      <dmftcontentref content-type=\"_CONTENT_TYPE_\">__DOC_CONTENT__</dmftcontentref>\n" +
    "    </dmftcontent>\n" +
    "  </dmftcontents>\n" +
    "</dmftdoc>";


}
