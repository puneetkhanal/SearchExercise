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
import java.io.Serializable;

class Posting implements Serializable{
	
    //individual word
    def term 
    //file
    def document
    //startPosition of a word
    def start 
    //endPosition of a word
    def end  
    // pointer to next word
    def next  
    def lineNumber
    // boolean flag to indicate last word in line
    def lastWordInLine 
    // boolean flag to indicate first word in a line
    def firstWordInLine 
    
}

