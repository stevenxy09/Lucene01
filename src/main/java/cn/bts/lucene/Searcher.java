package cn.bts.lucene;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
* @author stevenxy E-mail:random_xy@163.com
* @Date 2018年6月8日
* @Description 查询
*/
public class Searcher {
	
	public static void search(String indexDir,String q)throws Exception {
		Directory directory=FSDirectory.open(Paths.get(indexDir));
		IndexReader reader=DirectoryReader.open(directory);
		IndexSearcher is=new IndexSearcher(reader);
		Analyzer analyzer=new StandardAnalyzer();
		QueryParser parser=new QueryParser("contents", analyzer);
		Query query=parser.parse(q);
		long start=System.currentTimeMillis();
		TopDocs hits=is.search(query, 10);
		long end=System.currentTimeMillis();
		System.out.println("匹配:"+q+",总共花费:"+(end-start)+"毫秒"+hits.totalHits+"个记录");
		for(ScoreDoc scoreDoc:hits.scoreDocs) {
			Document doc=is.doc(scoreDoc.doc);
			System.out.println(doc.get("fullPath"));
		}
		reader.close();
	}
	
	public static void main(String[] args) {
		String indexDir="D://lucene";
		String q="Zygmunt Saloni";
		try {
			search(indexDir, q);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
