/**
 * 
 */
package com.DIC.SearchApp;
import java.io.IOException;
import java.nio.file.Paths;

import javax.websocket.server.PathParam;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.flexible.core.builders.QueryBuilder;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jvaranasi
 *
 */
@RestController

public class SearchIndexedSalesforceRecords {

	/**
	 * @param args
	 */
	private static final String INDEX_DIR = "D:\\Jyoshna\\Lucene2";
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path="/service/indexsearch/account")
	@GetMapping
	public static String SearchedIndexedRecords(@RequestParam("key") String keyword) throws Exception  {
		// TODO Auto-generated method stub
		
		 //Create lucene searcher. It search over a single IndexReader.
        IndexSearcher searcher = createSearcher();
         System.out.println("input string is ----->"+keyword);
        //Search indexed contents using search term
        TopDocs foundDocs = searchInContent(keyword, searcher);
         
        //Total found documents
        System.out.println("Total Results :: " + foundDocs.totalHits);
         
        //Let's print out the path of files which have searched term
        JSONArray resultsList=new JSONArray();
        String resultString="";
        for (ScoreDoc sd : foundDocs.scoreDocs) 
        {
            Document d = searcher.doc(sd.doc);
            JSONObject resultAccount=new JSONObject();
            for(IndexableField field:d.getFields()) {
            	//System.out.println(+"---------Path : "+ d.getField("Id").stringValue() + ", Score : " + sd.score);
            	resultAccount.put(field.name(), d.getField(field.name()).stringValue());
            }
            resultsList.add(resultAccount);
            resultString +=resultAccount.toString();
            
        }
        if(resultString.isEmpty()) {
        	resultString="No Results";
        }
        System.out.println("resultString====="+resultString+"\n");
		return resultString;

	}
	
	private static TopDocs searchInContent(String textToFind, IndexSearcher searcher) throws Exception
    {
		String[] fields= {"Id","Type","Name","Path","Industry","Phone","Fax"};
        //Create search query
		//QueryBuilder qb=new 
		QueryParser qp=new MultiFieldQueryParser(fields,new StandardAnalyzer());
		//QueryParser qp=new QueryParser("Name",new StandardAnalyzer());
		//qp.createBooleanQuery("Name", textToFind, Occur.SHOULD);
        //QueryParser qp = new QueryParser();
        Query query = qp.parse(textToFind);
         
        //search the index
        TopDocs hits = searcher.search(query, 200);
        return hits;
    }
 
    private static IndexSearcher createSearcher() throws IOException 
    {
        Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
         
        //It is an interface for accessing a point-in-time view of a lucene index
        IndexReader reader = DirectoryReader.open(dir);
        Term term=new Term("Name", "Freeman");
        System.out.println("lucene latest version"+Version.LATEST);
         System.out.println("3rd Docs count=====>"+reader.document(33));
    
        //Index searcher
        IndexSearcher searcher = new IndexSearcher(reader);
       
        
        return searcher;
    }

}
