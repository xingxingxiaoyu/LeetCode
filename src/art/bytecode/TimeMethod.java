package art.bytecode; 
 
public class TimeMethod { 
 
    public static void main(String[] args) { 
        long startTime = System.currentTimeMillis(); 
 
        long endTime = System.currentTimeMillis(); 
        System.out.println(endTime-startTime); 
    } 
} 
