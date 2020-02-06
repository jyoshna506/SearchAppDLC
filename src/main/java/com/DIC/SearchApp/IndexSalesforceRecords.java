/**
 * 
 */
package com.DIC.SearchApp;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.json.simple.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * @author jvaranasi
 * This class is used to index the Salesforce records
 *
 */

@SpringBootApplication
@EnableAutoConfiguration
public class IndexSalesforceRecords {
	
	/*
	 * public static void IndexRecords() throws Exception { //Input folder String
	 * docsPath =
	 * "http://www.aeclogic.com/Processfiles/AEC%20ERP%20business%20processes%20for%20seting%20up%20a%20crusher%20unit.pdf";
	 * //URI docsPathURl=new URI(docsPath);
	 * 
	 * //Output folder //String indexPath = "D:\\Jyoshna\\Lucene"; URL url=new
	 * URL(docsPath); //Input Path Variable final Path docDir =
	 * Paths.get(url.toURI().getPath());
	 * 
	 * try {
	 * 
	 * //org.apache.lucene.store.Directory instance Directory dir =
	 * FSDirectory.open( Paths.get(url.toURI().getPath()) );
	 * 
	 * //analyzer with the default stop words Analyzer analyzer = new
	 * StandardAnalyzer();
	 * 
	 * //IndexWriter Configuration IndexWriterConfig iwc = new
	 * IndexWriterConfig(analyzer); iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
	 * 
	 * //IndexWriter writes new index files to the directory IndexWriter writer =
	 * new IndexWriter(dir, iwc);
	 * 
	 * //Its recursive method to iterate all files and directories indexDocs(writer,
	 * docDir);
	 * 
	 * writer.close(); } catch (IOException e) { e.printStackTrace(); } }
	 */	
	static void indexDocs(final IndexWriter writer, Path path) throws IOException 
    {
        //Directory?
        if (Files.isDirectory(path)) 
        {
            //Iterate directory
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() 
            {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException 
                {
                    try
                    {
                        //Index this file
                        indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
                    } 
                    catch (IOException ioe) 
                    {
                        ioe.printStackTrace();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } 
        else
        {
            //Index this file
            indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis());
        }
    }
	
	
    static private void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException 
    {
        try (InputStream stream = Files.newInputStream(file)) 
        {
            //Create lucene Document
            Document doc = new Document();
             
            doc.add(new StringField("path", file.toString(), Field.Store.YES));
            doc.add(new LongPoint("modified", lastModified));
            doc.add(new TextField("contents", new String(Files.readAllBytes(file))+' '+file.toString(), Store.YES));
             
            //Updates a document by first deleting the document(s) 
            //containing <code>term</code> and then adding the new
            //document.  The delete and then add are atomic as seen
            //by a reader on the same index
            writer.updateDocument(new Term("path", file.toString()), doc);
        }
    }
    
    
    static void indexDocuments(IndexWriter writer, List<Account> accList) throws IOException 
    {
        try 
        {
        	//System.out.println("accList=========>"+accList);
        	if(accList!=null && !accList.isEmpty()) { 
        		for(Account acc:accList) { 
        			Document doc = new Document();
        			FieldType ft=new FieldType();
        			ft.setStored(true);
        			if(acc.getName()!=null)
   	        		 doc.add(new TextField("Name", acc.getName(), Field.Store.YES));
        			if(acc.getType()!=null)
        			doc.add(new TextField("Type", acc.getAttributes().getType(), Field.Store.YES));
        			if(acc.getId()!=null)
	        		 doc.add(new TextField("Id", acc.getId(), Field.Store.YES)); 
        			if(acc.getFax()!=null)
	        		 doc.add(new TextField("Fax", acc.getFax(), Field.Store.YES));
        			if(acc.getPath()!=null)
	        		 doc.add(new TextField("Path", acc.getAttributes().getUrl(),Field.Store.YES));
        			if(acc.getIndustry()!=null)
	        		 doc.add(new TextField("Industry", acc.getIndustry(),Field.Store.YES));
        			if(acc.getPhone()!=null)
	        		 doc.add(new TextField("Phone", acc.getPhone(), Field.Store.YES)); 
        			if(acc.getId()!=null)
	        		 writer.updateDocument(new Term("Id", acc.getId()), doc);
        		}

        	}

            //Create lucene Document
            
             
            //doc.add(new StringField("path", file.toString(), Field.Store.YES));
            // doc.add(new LongPoint("modified", lastModified));
            //doc.add(new TextField("contents", new String(Files.readAllBytes(file))+' '+file.toString(), Store.YES));
             
            //Updates a document by first deleting the document(s) 
            //containing <code>term</code> and then adding the new
            //document.  The delete and then add are atomic as seen
            //by a reader on the same index
           
        }finally{
        	writer.commit();
        	writer.close();
        }
    }

}
