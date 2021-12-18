package data_structures;
import java.util.Iterator;
import java.util.ConcurrentModificationException;


public class DictionaryTester {
    public static void main(String [] args) {
        final int SIZE = 1000000;
        long start, stop;
        int [] array = new int[SIZE];
        DictionaryADT<Integer,Integer> dictionary = 
           new Hashtable<Integer,Integer>(SIZE);
           //new BinarySearchTree<Integer,Integer>();
          //new BalancedTreeDictionary<Integer,Integer>();                    
        for(int i=0; i < SIZE; i++) 
            array[i] = (i+1);
        for(int i=0; i < SIZE; i++) {
            int index = (int)(SIZE*Math.random());
            int tmp = array[i];            
            array[i] = array[index];
            array[index] = tmp;
            }                        
            
        System.out.println("Adding elements to dictionary");
        start = System.currentTimeMillis();            
        for(int i=0; i < SIZE; i++)
            if(!dictionary.put(array[i] , array[i])) {
                System.out.println("ERROR, insertion failed!");
                System.exit(0);
                }
        stop = System.currentTimeMillis();
        System.out.println("Time for insertion of " + SIZE + " elements: " +
            (stop-start));                
        if(dictionary.size() != SIZE)
            System.out.println("ERROR in size(), should be " + SIZE +
                " but the method retured " + dictionary.size());
                
        System.out.println("Now doing lookups");  
        start = System.currentTimeMillis();              
        for(int i=0; i < SIZE; i++) {
            Integer tmp = dictionary.get(array[i]);
            if(tmp == null) {
                System.out.println("ERROR, getValue failed!");
                System.exit(0);
                }
            }
        stop = System.currentTimeMillis();
        System.out.println("Time for getValue with " + SIZE + " elements: " +
            (stop-start));            
            
        for(int i=0; i < 100; i++) {
            Integer tmp = dictionary.getKey(array[i]);
            if(tmp == null) {
                System.out.println("ERROR, getKey failed!");
                System.exit(0);
                }
            } 
        
        System.out.println("Now Doing deletion");  
        start = System.currentTimeMillis();          
        for(int i=0; i < SIZE; i++)
            if(!dictionary.delete(array[i])) {
                System.out.println("ERROR, deletion failed!");
                System.exit(0);
                } 
        stop = System.currentTimeMillis();
        System.out.println("Time for deletion with " + SIZE + " elements: " +
            (stop-start));        
                
        if(dictionary.size() != 0)
            System.out.println("ERROR in size(), should be 0 " +
                " but the method retured " + dictionary.size());  
                
        for(int i=0; i < SIZE; i++) {
            Integer tmp = dictionary.get(array[i]);
            if(tmp != null) {
                System.out.println("ERROR, getValue failed, found a deleted value at index " + i+"!");
                System.exit(0);
                }
            }
            
        dictionary.clear();
        
        for(int i=1; i <= 10; i++)
            dictionary.put(i,i);                                                 
                     
        Iterator<Integer> keys = dictionary.keys();
        Iterator<Integer> values = dictionary.values();
        
        System.out.println("The iterators should print 1 .. 10");
        while(keys.hasNext()) {
            System.out.print(keys.next());
            System.out.print("   " + values.next());
            System.out.println();
        }        
        
        try {
            keys = dictionary.keys();
            values = dictionary.values();
            dictionary.put(100,100);   // add element to taint iterators         
            while(keys.hasNext()) {
                Integer tmp = keys.next();
                Integer tmp2 = values.next();
                System.out.println("ERROR, iterator is not fail-fast");
                }       
            }
        catch(ConcurrentModificationException e) {
            System.out.println("OK, iterators are fail-fast");
            }
        catch(Exception e) {
            System.out.println("Iterators are fail-fast, but threw the " +
                "wrong exception " + e);
            }
        dictionary.clear();    
                
        keys = dictionary.keys();
        System.out.println("Now calling iterator on EMPTY structure: ");
        System.out.println("NO output should follow this line ");        
        while(keys.hasNext()) 
            System.out.print(keys.next()+" ");                
    }
}
	
	/*
	public static void main(String [] args) {
        new DictionaryTester();
        }
        
    public DictionaryTester() {
        int STRUCTURE_SIZE = 100000;
        int STEP_SIZE = 100000;
        int ITERATIONS = 10;
        int M_NUMBER = 100000;
        int maxSize = STRUCTURE_SIZE+M_NUMBER+STEP_SIZE*ITERATIONS;
        long start, stop;
        DictionaryADT dictionary;
//======================================================================

        Integer [] array = new Integer[maxSize];
        for(int i=0; i < maxSize; i++)
            array[i] = new Integer(i+1);
            
        for(int i=0; i < maxSize; i++) {
            int idx = ( (int) (maxSize * Math.random()));
            Integer tmp = array[idx];
            array[idx] = array[i];
            array[i] = tmp;
            }            
        
//======================================================================

        System.out.println(
        "Timing for iterations of size: " + M_NUMBER);           
        for(int i=0; i < ITERATIONS; i++) { 
        
//======================================================================     

            dictionary = new Hashtable(STRUCTURE_SIZE+M_NUMBER);            
           // dictionary = new BinarySearchTree();
          //  dictionary = new BalancedTreeDictionary();
// Change the line above to test different implementations.
        System.out.println("Testing the " + dictionary.getClass() + " implementation");
//======================================================================  
        System.gc(); //run the garbage collector 
        
            for(int j=0; j < STRUCTURE_SIZE; j++) {
         
                if(!dictionary.put(array[j],array[j])) {
                    System.out.println(
                    "ERROR, could not add item at index " + j);
                    System.exit(1);
                    }
                }
            //time insertion                    
            start = System.currentTimeMillis();
            for(int j=STRUCTURE_SIZE; j < (M_NUMBER+STRUCTURE_SIZE); j++) {
                if(!dictionary.put(array[j],array[j])) {
                    System.out.println(
                    "ERROR, could not add item at index " + j);
                    System.exit(1);
                    }
                }
            stop = System.currentTimeMillis();
            System.out.println(
            "Time for insertion in structure with " + 
            STRUCTURE_SIZE + " items: " + (stop-start));  
            
            //remove the added items
            start = System.currentTimeMillis();            
            for(int j=STRUCTURE_SIZE; j < (M_NUMBER+STRUCTURE_SIZE); j++) {
                if(!dictionary.delete(array[j])) {
                    System.out.println(
                    "ERROR, could not remove item at index " + j);
                    System.exit(1);
                    }                
                }
            stop = System.currentTimeMillis();
            System.out.println(
            "Time for deletion in structure with " + 
            STRUCTURE_SIZE + " items: " + (stop-start));   
                           
            //time search
            start = System.currentTimeMillis();
            for(int j=0; j < M_NUMBER; j++) {
                Integer tmp = (Integer) dictionary.get(array[i]);
                if(tmp == null) {
                    System.out.println(
                    "ERROR, item not found during search!!");
                    System.exit(1);
                    }
                }
            stop = System.currentTimeMillis();
            System.out.println(
            "Time for search in structure with " + 
            STRUCTURE_SIZE + " items: " + (stop-start));
            //now remove all items, just an extra test for your program
            for(int j=0; j < STRUCTURE_SIZE; j++) {
         
                if(!dictionary.delete(array[j])) {
                    System.out.println(
                    "ERROR, could not add remove at index " + j);
                    System.exit(1);
                    }
                }                 
            //system should now be empty
            dictionary.clear();             
            STRUCTURE_SIZE += STEP_SIZE;
            System.out.println();
        }
                
                       
    }
    */
//}    





















