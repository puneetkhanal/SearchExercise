/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import static org.junit.Assert.*

/**
 *
 * @author puneetkhanal
 */
class ReplaceTest {
    
    @Test
    public void replaceTest(){
        def file=new File('data/test1')
        processFileInplace(file) { text ->
            text.replaceAll('doug\\s+cutting', 'lucenes')
        }
    }
    
    def processFileInplace(file, Closure processText) {
        def text = file.text
        
        file.write(processText(text))
    }
}
