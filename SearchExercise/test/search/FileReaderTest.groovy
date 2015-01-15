
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
import search.FileReader
import search.Indexer
import static org.junit.Assert.*


/**
 *
 * @author puneetkhanal
 */
class FileReaderTest {

   
    @Test
    public void lineReaderTest(){
        def fileReader=new FileReader()
        def postings=fileReader.readLine('',"This is fun",1)    
        def indexer=new Indexer();
        for(posting in postings)
        {
            indexer.index(posting)
        }
        def results=indexer.search("This is fun")
        
        assertEquals('This',results[0]['term'])
        assertEquals('is',results[0].next['term'])
        assertEquals('fun',results[0].next.next['term'])
    }
        
        
    @Test
    public void fileReaderTest(){
        def fileReader=new FileReader()
        fileReader.readFile('data/test1')
    }
        
        
    @Test
    public void directoryReaderTest(){
        def fileReader=new FileReader()
        def list=fileReader.readFromDirectory('data')
        println list
        println list.contains("data\test1")
        assertTrue(list.size()>0)
    }
        

    
    
}
