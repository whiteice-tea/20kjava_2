package Cellph;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
interface Icalculate {
    public double work(double n1, double n2);
    public double Subtraction(double n1, double n2);

}

class Cellphone{

    public void work(Icalculate calc,double n1,double n2) {
        double result =calc.work(n1,n2);
        System.out.println("结果是="+result);

    }

    public void Subtraction(Icalculate calc,double n1,double n2) {
        double result =calc.Subtraction(n1,n2);
        System.out.println("<UNK>="+result);
    }
}

public class homework {
    public static void main(String[] args) {
        Cellphone cellphone = new Cellphone();

        Icalculate calc = new Icalculate() {
            @Override
            public double work(double n1,double n2){
                return n1+n2;
            }

            @Override
            public double Subtraction(double n1,double n2){
                return n1-n2;
            }
        };

        cellphone.work(calc,1,1);
        cellphone.Subtraction(calc,1,1);


    }
}

