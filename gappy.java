package data_structures;
import java.util.Scanner;

public class gappy {
	

	public static void main(String[] args) {
	 
	    
	    double[] examGrades = new double[5];
	    
	    Scanner input = new Scanner(System.in);

	    System.out.println("Enter 5 Exam Grades: ");

	    for(int i = 0; i < examGrades.length; i++){
	        
	       double examScore = input.nextDouble();
	    	if (validate(examScore)){
	            examGrades[i] = examScore; 
	        }
	        else {
	            examGrades[i] = 0;
	        }
	    }
	   
	    for(int i = 0; i < examGrades.length; i++) {
	    	System.out.println(" Value at index " + i + " = " + examGrades[i]);
	    }
	   
	 
	    System.out.println("The Exam Average " + average(examGrades));
	}



	public static double average(double[] examGrades){

	    double sum = 0;

	    for(int index = 0; index < examGrades.length; index++){
	        sum += examGrades[index];
	    }
	    
	    return (sum / examGrades.length);

	}

	public static boolean validate(double examScore){
	    if(examScore < 0 || examScore > 100){
	        return false;
	    }
	    else{
	        return true;
	        
	    }
	}

	//@Override
	//public static boolean validate(double examScore) {
	//    return examScore > 0 && examScore < 100;
	//}

}
