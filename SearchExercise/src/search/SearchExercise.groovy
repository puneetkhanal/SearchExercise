/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package search

/**
 *
 * @author puneetkhanal
 */


import org.apache.log4j.*
import groovy.util.logging.*
import org.mapdb.*;

@Log4j
class SearchExercise {
	
    def execute(def args){
        def fileReader=new FileReader()
        def indexService=new Indexer()
        def searchString='doug'
        indexService.setMap(DBMaker.newTempTreeMap())
        searchString=QueryProcessor.parseQuery(searchString);
        fileReader.setIndexService(indexService)
        fileReader.readFromDirectory('data');
        def results=indexService.search(searchString)
        fileReader.processResults(results,searchString,'doug','data/changedFiles.txt')
    }
    static main(def args){
        def searchExercise=new SearchExercise()
        searchExercise.execute(args)
    }
}

