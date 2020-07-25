// references: https://www.baeldung.com/lucene
/*
 * Lucene Basics:
 *   - Lucene is an opensource full-text search engine
 *   - It uses `inverted index` as data-type to store and search operations.
 *     This operation named as `indexing`
 *   - `Indexes` where lucene store the data
 *   - `Indexes` made of `Documents` and `Documents` made of `Fields`
 *   - `Fields` are key-value pairs
 *   - `Search Results` are set of best matching documents of the search made to the index
 *   -  Example for a `Document`:
 *       ```
 *          title: Goodness of Tea
 * 	       body: Discussing goodness of drinking herbal tea...
 *       ```
 *   - `Searching`: after creating index with `IndexWriter`, index is searchable _
 *      with `IndexSearcher` and `Query`
 *   - `Query` is a string that lucene provides to determine the documents which will match and return
 *     (lucene has its own query syntax)
 *      Example for `Query`:
 *       ```
 *           fieldName:text
 *       ```
 *   - Different kind of queries exits:
 *      Range Query: ```timestamp:[1509909322,1572981321]```
 *      WildCard Query: ``` dri?nk OR drin*```
 *
 *   - `Analyzers` are basically parsers which convert text into smaller pieces to make search more effective
 *     There are multiple analyzers and some of them are built in;
 *
 *  */
package com.example.demo;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LuceneOperator {
    private Directory inMemoryIndex;
    private Analyzer defaultAnalyzer;

    public LuceneOperator() throws IOException {
        // creating index on init
        this.inMemoryIndex = new RAMDirectory();
        this.defaultAnalyzer = new SimpleAnalyzer();
    }

    public IndexWriter getWriter() throws IOException{
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(this.defaultAnalyzer);
        return new IndexWriter(this.inMemoryIndex, indexWriterConfig);
    }

    public void addSingleFieldDocument(String key, String value) throws IOException{
        Document document = new Document();
        document.add(new TextField(key, value, Field.Store.YES));
        IndexWriter iw = this.getWriter();
        iw.addDocument(document);
        iw.close();
    }

    public List<Document> search(String field_name, String query_string, Integer doc_count) throws ParseException, IOException {
        Query query = new QueryParser(field_name, this.defaultAnalyzer).parse(query_string);
        IndexReader indexReader = DirectoryReader.open(this.inMemoryIndex);
        IndexSearcher searcher = new IndexSearcher(indexReader);
        TopDocs topDocs = searcher.search(query, doc_count);
        List<Document> results = new ArrayList<>();
        for(ScoreDoc scoreDoc: topDocs.scoreDocs){
            results.add(searcher.doc(scoreDoc.doc));
        }

        return results;
    }
}
