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
class Indexer {

    def map=["":""]
    
    def index(def arg){
        if(map[arg['term']]==null){
            map[arg["term"]]=[arg]
        }else{
            map[arg['term']].add(arg)
        }
    }
    
    def search(def searchText){
        def terms=searchText.split(" ")
        def firstPostingsList=map[terms[0]]
        def currentPosting
        def results=[]
        for(posting in firstPostingsList){
            currentPosting=posting
            def i=1;
            for(;i<terms.size();i++){
                def eachPostingsList=map[terms[i]]
                def match=false
                for(eachPosting in eachPostingsList){
//                    println currentPosting['document']+":"+eachPosting['document']
                    if(currentPosting['document']==eachPosting['document']&&currentPosting['end']+2==eachPosting['start']){
                        match=true;
                        currentPosting.setNext(eachPosting)
                        currentPosting=eachPosting
                        break
                    }
                }
                if(!match){
                    break;
                }
               
            }
            if(i==terms.size()){
                results.add(posting);
            }
        }
        
        return results
    }
    
}

