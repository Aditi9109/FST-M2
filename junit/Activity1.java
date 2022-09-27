

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Activity1 {

    static ArrayList<String> list;

    // Initialize test fixtures before each test method
    @BeforeEach
    void setUp() throws Exception {
        list = new ArrayList<String>();
        list.add("alpha");
        list.add("beta");
    }

    @Test
    public void insertTest() {

        //Write an assertion to check the size of the ArrayList. The expected value is 2.
        assertEquals(2, list.size());
        // Add a new value to the list
        list.add("gama");
        // assertion to check the size of the new list. The expected values of the list should be 3.
        assertEquals(3, list.size(), "Wrong size");

        // assertions for checking each of the values in the ArrayList.
        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("beta", list.get(1), "Wrong element");
        assertEquals("gama", list.get(2), "Wrong element");
    }

    @Test
    public void replaceTest() {
        //assertion to check the size of the ArrayList. The expected value is 2.
        assertEquals(2, list.size());
        // Add a new value to the list
        list.add("gama");
        // assertion to check the size of the new list. The expected values of the list should be 3.
        assertEquals(3, list.size(), "Wrong size");
        // Replace new element
        list.set(1, "charlie");
        // Assert individual elements
        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("charlie", list.get(1), "Wrong element");
        assertEquals("gama", list.get(2), "Wrong element");
    }
}
