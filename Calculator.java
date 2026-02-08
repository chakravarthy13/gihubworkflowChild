public class Calculator {
    
    // Method that performs subtraction with two parameters
    public static int subtract(int a, int b) {
        return a - b;
    }
    
    // Method that passes values to the subtraction method
    public static void main(String[] args) {
        int num1 = 30;
        int num2 = 8;
        
        // Call subtract method and pass values
        int result = subtract(num1, num2);
        
        System.out.println("Result: " + result); // Output: Result: 12
    }
}
