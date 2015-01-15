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
        for(eachPosting in firstPostingsList){
            previousPosting=eachPosting
            def termIndex=1;
            for(;termIndex<terms.size();termIndex++){
                def currentPostingsList=map[terms[termIndex]]
                if(currentPostingsList==null){
                    break;
                }
                def postingIndex=0;
                for(;postingIndex<currentPostingsList.size();postingIndex++){
                    def currentPosting=currentPostingsList.getAt(postingIndex)
                    //find neigbhor word
                    if(isSameDocument(previousPosting,currentPosting)&&isNeighbor(previousPosting,currentPosting)){
                        previousPosting.setNext(currentPosting)
                        previousPosting=currentPosting
                        break
                    }
                }
                //no match found so terminate
                if(postingIndex==currentPostingsList.size()){
                    break;
                }
            }
            // every term has been found so a complete string has been found
            if(termIndex==terms.size()){
                results.add(eachPosting);
            }
        }
        return results
    }
    
    def isSameDocument(def previousPosting,def currentPosting){
        return previousPosting['document'].equals(currentPosting['document'])
    }
    
    def isNeighbor(def previousPosting,def currentPosting){
        if(previousPosting['end']+2==currentPosting['start']){
            return true;
        }else if(previousPosting['lastWordInLine']&&currentPosting['firstWordInLine']&&previousPosting['lineNumber']+1==currentPosting['lineNumber']){
            return true;
        }else{
            return false;
        }
    }
    
}

