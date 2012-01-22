/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author msk
 */
public class NewEmptyJUnitTest {

    public NewEmptyJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testAdd() {
      int num1 = 3;
      int num2 = 2;
      int total = 5;
      int sum = 0;
      sum = Math.add(num1, num2);
      assertEquals(sum, total);
    }
    
    @Test
    public void testMulitply() {
      
      int num1 = 3; 
      int num2 = 7; 
      int total = 21;
      int sum = 0;
      sum = Math.multiply(num1, num2);
      assertEquals("Problem with multiply", sum, total);
      
      
      num1 = 5;
      num2 = 4;
      total = 20;
      sum = Math.multiply(num1, num2);
      assertEquals("Problem with multiply", sum, total);
      
    }

}