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
        try{
            if(args.size()<2){
                log.error 'insufficient arguments'
                System.exit(0)
            }
            def fileReader=new FileReader()
            def indexService=new Indexer()
            def basePath=args[0]
            def searchString=args[1]
            def replaceString
            if(args.size()>=3){
                replaceString=args[2]
            }
            def outputfile
            if(args.size()==4){
                outputfile=args[3]
            }
        
            // inject map db for persistence map
            indexService.setMap(DBMaker.newTempTreeMap())
            searchString=QueryProcessor.parseQuery(searchString);
        
            def currentTime=System.currentTimeMillis()
        
            fileReader.setIndexService(indexService)
            fileReader.readFromDirectory(basePath);
        
            log.info 'Total indexing time: '+(System.currentTimeMillis()-currentTime)+' ms'
        
            currentTime=System.currentTimeMillis()
            def results=indexService.search(searchString)
       
            log.info 'Total search time: '+(System.currentTimeMillis()-currentTime)+' ms'
        
            if(replaceString==null){
                fileReader.processResults(results,searchString)
            }else if(outputfile==null){
                fileReader.processResults(results,searchString,replaceString)
            }else{
                log.info outputfile
                fileReader.processResults(results,searchString,replaceString,outputfile)
            }
        }catch(FileNotFoundException e){
            log.error e.getMessage()+" Not found"
        }
    }
    
    static main(def args){
        def searchExercise=new SearchExercise()
        searchExercise.execute(args)
    }
}

