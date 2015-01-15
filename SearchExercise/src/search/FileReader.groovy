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
        file.eachLine { line -> readLine(line,lineNumber++) }
    }
    
    def readLine(def line,def lineNumber){
        def words=line.split(' ')
        def postings=[]
        def start=0;
        for(word in words){
            def posting=new Posting()
            posting.setStart(start)
            posting.setEnd(start+word.length())
            posting.setTerm(word)
            start=start+word.length()+2
            postings.add(posting)
        }
        return postings
    }
    
    def processFileInplace(file, Closure processText) {
        def text = file.text
        file.write(processText(text))
    }
}

