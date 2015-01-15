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
            file.eachLine { line -> readLine(path,line,lineNumber++) }
        
        }
    }
    
    def readLine(def path,def line,def lineNumber){
        def words=line.split(' ')
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
                println 'indexing'
                indexService.index(posting)
            }
            postings.add(posting)
        }
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
    
    def processResults(def results,def searchString){
        for(result in results){
            def currentResult=result
            println 'Search String \''+searchString+'\' found in document: '+result['document']
            while(currentResult!=null){
                println currentResult['term']+":("+currentResult['lineNumber']+","+currentResult['start']+")"
                currentResult=currentResult.next
            }
            File file=new File(result['document'])
            processFileInplace(file) { text ->
                text.replaceAll('doug cutting', 'doug cutting')
            }
          
            copy(file,new File(file.getParent()+"/_bak_"+(int)System.currentTimeMillis()/1000+"_"+file.getName()))
        }
    }
}

