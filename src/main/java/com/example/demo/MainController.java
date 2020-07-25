package com.example.demo;

import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/lucene")
public class MainController {
    private LuceneOperator luceneOperator;

    @Autowired
    public MainController(LuceneOperator luceneOperator){
        this.luceneOperator = luceneOperator;
    }

    @GetMapping("/add")
    public String add_text(@PathParam("text") String text){
        try {
            this.luceneOperator.addSingleFieldDocument("text", text);
            return "OK";
        }catch (Exception e){
            return "ERROR";
        }
    }

    @GetMapping("/search")
    public String search_text(@PathParam("query_string") String query_string){
        StringBuilder output = new StringBuilder();
        try {
            List<Document> results = this.luceneOperator.search("text", query_string, 100);
            for (Document result : results) {
                output.append("<br>").append(result.getField("text").stringValue());
            }
        }catch (Exception e){
            output = new StringBuilder("ERROR");
        }
        return output.toString();
    }

}
