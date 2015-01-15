
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import static org.junit.Assert.*
import search.Posting
import search.Indexer

/**
 *
 * @author puneetkhanal
 */
class IndexerTest {

    public IndexerTest() {
    }
    
    //    @Test
    //    public void indexTest(){
    //        def indexer=new Indexer();
    //        indexer.index(createIncident(1,"hi",1,2))
    //        indexer.index(createIncident(1,"hello",1,2))
    //    }
    
    
    @Test
    public void searchTest(){
        def indexer=new Indexer();
        indexer.index(createPosting(1,"hi",0,1))
        //        indexer.index(createIncident(2,"hi",1,2))
        indexer.index(createPosting(1,"hello",3,7))
        indexer.index(createPosting(1,"puneet",9,14))
        
        indexer.search("hi hello puneet")
    }
    
    
    def createPosting(def document,def term,int start,int end){
        Posting posting=new Posting();
        posting.setDocument(document)
        posting.setEnd(end)
        posting.setStart(start)
        posting.setTerm(term)
        return posting;
    }
   
}
