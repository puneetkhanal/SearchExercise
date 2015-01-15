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

@Log4j
class Indexer {

    def map
    
    def index(def arg){
        if(map==null){
            map=new HashMap()
        }
        if(map[arg['term']]==null){
            map[arg["term"]]=[arg]
        }else{
            map[arg['term']].add(arg)
        }
    }
    
    def search(def searchText){
        def terms=searchText.split(" ")
        def firstPostingsList=map[terms[0]]
        def results=[]
        def previousPosting
        for(posting in firstPostingsList){
            previousPosting=posting
            def index=1;
            for(;index<terms.size();index++){
                def eachPostingsList=map[terms[index]]
                def match=false
                for(eachPosting in eachPostingsList){
                    if(isSameDocument(previousPosting,eachPosting)&&isNeighbor(previousPosting,eachPosting)){
                        match=true;
                        previousPosting.setNext(eachPosting)
                        previousPosting=eachPosting
                        break
                    }
                }
                if(!match){
                    break;
                }
            }
            if(index==terms.size()){
                results.add(posting);
            }
        }
        return results
    }
    
    def isSameDocument(def previousPosting,def eachPosting){
        return previousPosting['document'].equals(eachPosting['document'])
    }
    
    def isNeighbor(def previousPosting,def eachPosting){
        if(previousPosting['end']+2==eachPosting['start']){
            return true;
        }else if(previousPosting['lastWordInLine']&&eachPosting['firstWordInLine']&&previousPosting['lineNumber']+1==eachPosting['lineNumber']){
            return true;
        }else{
            return false;
        }
    }
    
}

