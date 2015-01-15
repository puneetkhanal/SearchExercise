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
import groovy.io.FileType

import org.apache.log4j.*
import groovy.util.logging.*

@Log4j
class FileReader {
    
    def indexService
	
    def readFromDirectory(def directoryPath){
        def list = []
        def dir = new File(directoryPath)
        dir.eachFileRecurse (FileType.FILES) { file ->
            list << file
        }
        list.each {
            readFile(it.path)
        }
    }
    
    def readFile(def path){
        def file = new File(path)
        def lineNumber=0
        def fileName=new String(file.getName())
        if(!fileName.startsWith("_bak")){
            file.eachLine { line -> readLine(path,line.trim(),lineNumber++) }
        
        }
    }
    
    def readLine(def path,def line,def lineNumber){
        // remove extra whitespace
        def words=line.replaceAll("\\s+", " ").split(' ')
        def postings=[]
        def start=0;
        
        for(word in words){
            def posting=new Posting()
            posting.setStart(start)
            posting.setEnd(start+word.length())
            posting.setTerm(word)
            posting.setDocument(path)
            posting.setLineNumber(lineNumber)
            start=start+word.length()+2
            if(indexService!=null){
                indexService.index(posting)
            }
            postings.add(posting)
        }
        // mark first and second word for deteting text over line break
        postings.getAt(0)["firstWordInLine"]=true
        postings.getAt(words.size()-1)["lastWordInLine"]=true
        
        return postings
    }
    
    def processFileInplace(file, Closure processText) {
        def text = file.text
        file.write(processText(text))
    }
    
    def copy(File src,File dest){
        def input = src.newDataInputStream()
	def output = dest.newDataOutputStream()
 
	output << input 
 
	input.close()
	output.close()
    }
    
    def processResults(def results,def searchString,def replaceString,def outputFile){
        def changedFiles=new HashSet()
        for(currentResult in results){
            log.info 'Search String \''+searchString+'\' found at lineNumber: '+currentResult['lineNumber']+' columnNumber: '+currentResult['start']+' in document:' +currentResult['document']
            changedFiles.add(currentResult['document'])
        }
        if(replaceString!=null){
            replace(changedFiles,searchString,replaceString)
        }
        
        if(outputFile!=null){
            outputChangedFiles(changedFiles,outputFile);
        }
    }
    
    def outputChangedFiles(def changedFiles,def outputFile){
        File file = new File(outputFile)
        changedFiles.each {
            file << it+"\n"
        }
    }
    
    
    def replace(def changedFiles,def searchString,def replaceString){
        for(eachFile in changedFiles){
            File file=new File(eachFile)
            copy(file,new File(file.getParent()+"/_bak_"+new Long(System.currentTimeMillis())+"_"+file.getName()))
            processFileInplace(file) { text ->
                text.replaceAll(QueryProcessor.preProcessForReplace(searchString), replaceString)
            }
        }
    }
    
    
    def processResults(def results,def searchString,def replaceString){
        processResults(results,searchString,replaceString,null)
    }
}

