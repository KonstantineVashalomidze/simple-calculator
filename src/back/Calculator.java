package back;

public class Calculator {


    public static double Add(double a, double b){
        return a+b;
    }
    public static double Sub(double a, double b){
        return a-b;
    }
    public static double Mul(double a, double b){
        return a*b;
    }
    public static double Div(double a, double b){
        if(b == 0){
            System.out.println("division by zero");
        }

        return a/b;
    }

}
