
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

   

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    //    @Test
    //    public void lineReaderTest(){
    //        def fileReader=new FileReader()
    //        def postings=fileReader.readLine("This is fun",1)
    //        def indexer=new Indexer();
    //        for(posting in postings){
    //            println posting['term'] + ":"+posting['start']+":"+posting['end']
    //            indexer.index(posting)
    //        }
    //        def results=indexer.search("This is fun")
    //    }
    //    
    //    
    //    @Test
    //    public void fileReaderTest(){
    //        def fileReader=new FileReader()
    //        fileReader.readFile('data/test1')
    //    }
    //    
    //    
    //     @Test
    //    public void directoryReaderTest(){
    //        def fileReader=new FileReader()
    //        fileReader.readFromDirectory('data')
    //    }
    //    
    
    @Test
    public void indexServiceTest(){
        def fileReader=new FileReader()
        def indexService=new Indexer()
        fileReader.setIndexService(indexService)
        fileReader.readFromDirectory('data')
        def searchString='doug cuttings'
        def results=indexService.search(searchString)
        
        fileReader.processResults(results,searchString,'doug cutting','data/changedFiles.txt')
    }
    
    
}
