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
	
    def term
    def document
    def start
    def end
    def next
    def lineNumber;
    def lastWordInLine
    def firstWordInLine
    
}

