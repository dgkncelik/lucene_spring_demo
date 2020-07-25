#Lucene-Spring Demo

This repository is represents simple text search application with Lucene + Spring

# Api
After booting Spring application

* localhost:8080/lucene/add?text=[THE TEXT YOU WOULD LIKE TO SEARCH LATER]
  `Endpoint allows you to index text to Lucene memory index for further search operations`

* localhost:8080/lucene/search?query_string=[KEYWORDS YOU WOULD LIKE TO SEARCH ON ALREADY INDEXED DATA]
   `Endpoint allows you to search text on already indexed data (performs search with query)`


Example:

    localhost:8080/lucene/add?text=hello
    "OK:
    
    localhost:8080/lucene/add?text=world
    "OK"
       
    localhost:8080/lucene/search?query_string=hello
    "hello"
