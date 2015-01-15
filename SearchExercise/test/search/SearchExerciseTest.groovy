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

/**
 *
 * @author puneetkhanal
 */

import org.apache.log4j.*
import groovy.util.logging.*
import org.mapdb.*;

@Log4j
class SearchExerciseTest {

    @Test
    public void searchExerciseTest(){
        def fileReader=new FileReader()
        def indexService=new Indexer()
        def searchString='doug cuttings'
        
        // inject map db for persistable map
        indexService.setMap(DBMaker.newTempTreeMap())
        searchString=QueryProcessor.parseQuery(searchString);
        
        def currentTime=System.currentTimeMillis()
        
        fileReader.setIndexService(indexService)
        fileReader.readFromDirectory('data');
        
        log.info 'total indexing time: '+(System.currentTimeMillis()-currentTime)+' ms'
        
        currentTime=System.currentTimeMillis()
        def results=indexService.search(searchString)
       
        log.info 'total search time: '+(System.currentTimeMillis()-currentTime)+' ms'
        
        fileReader.processResults(results,searchString,'doug','data/changedFiles.txt')
    }
}
