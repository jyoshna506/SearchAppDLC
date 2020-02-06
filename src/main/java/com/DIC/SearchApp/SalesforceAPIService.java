package com.DIC.SearchApp;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class SalesforceAPIService {

	public AuthenticationResponse login(){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
		 
		params.add("username", "jyoshna.varanasi@appshark.com");
		params.add("password", "Kittujyo@43914qSwMCNcEMd4TJlRIrYwmh5" );
		params.add("client_secret", "2110204A24812AF5B398301E80297491E90080A42A81D10A5D63F4BD301B36FF" );
		params.add("client_id", "3MVG9G9pzCUSkzZvyfihM9Sh00fFH_VrjDlTPGsTHNPYmjETHVCcFwzwjFyp2_LzW1.6M.yvRKczYx_ZiOjav");
		params.add("grant_type","password");
		 
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		 
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity("https://login.salesforce.com/services/oauth2/token", request, AuthenticationResponse.class);
		return response.getBody();
		}
	
	public AccountResponse getAccountData(String accessToken, String instanceUrl) throws Exception{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + accessToken);
		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
		 
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<AccountResponse> salesforceTestData = restTemplate.exchange(instanceUrl+ "/services/data/v47.0/query?q=select  id, Name, Type, Industry, Phone, Fax from Account", HttpMethod.GET, request, AccountResponse.class);
		//System.out.println(salesforceTestData.getBody());
		indexAccountsData(salesforceTestData.getBody());
		return salesforceTestData.getBody();
		}
	public void getAttachementsForAccount(String accessToken, String instanceUrl) throws Exception{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + accessToken);
		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
		 
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<AccountResponse> salesforceTestData = restTemplate.exchange(instanceUrl+ "/services/data/v47.0/query?q=select  id, Name, Type, Industry, Phone, Fax from Account", HttpMethod.GET, request, AccountResponse.class);
		
	}
	
	
	public String indexAccountsData(AccountResponse accResopnse) throws Exception {
		
		//IndexSalesforceRecords indexSFRecords=new IndexSalesforceRecords();
		List<Account> accountsList=accResopnse.getRecords();
//		for(Account acc:accountsList) {
//			System.out.println(acc.getId()+"\n");
//		}
		String indexPath = "file:///D:/Jyoshna/Lucene2/";
		URI uri=new URI(indexPath);
		Directory dir = FSDirectory.open( Paths.get(uri) );
		 Analyzer analyzer = new StandardAnalyzer();
         
	        //IndexWriter Configuration
	        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
	        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
	         
	        //IndexWriter writes new index files to the directory
	        IndexWriter writer = new IndexWriter(dir, iwc);
	        
	        IndexSalesforceRecords.indexDocuments(writer,accountsList);
		
		
		return null;
	}
	
	
	
}
