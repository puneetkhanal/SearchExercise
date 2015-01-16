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
class QueryProcessor {
	
    // replaces unwanted whitespaces
    static def parseQuery(def searchString){
        return searchString.replaceAll("\\s+"," ").toLowerCase()
    }
    
    // applies regex character to match multiple whitespace
    static def preProcessForReplace(def searchString){
        def terms=searchString.split(' ')
        // (?i) for case insensitve replacing
        def strBuilder=new StringBuilder('(?i)')
        def i=0;
        for(;i<terms.size();i++){
            if(i<terms.size()-1){
                strBuilder.append(terms.getAt(i))
            }else{
                strBuilder.append(terms.getAt(i));
            }
        }
        return strBuilder.toString().toLowerCase()
    }
}

