/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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

    public ReplaceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void replaceTest(){
        def file=new File('data/test1')
        processFileInplace(file) { text ->
            text.replaceAll('lucene', 'lucenes')
        }
    }
    
    def processFileInplace(file, Closure processText) {
        def text = file.text
        
        file.write(processText(text))
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
