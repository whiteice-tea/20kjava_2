package howork11;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class Car {
    private double temperature;

    public Car(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }

    public void m1(){
        class Air{
            public void flow(){
                if(temperature >40){
                    System.out.println("hot");
                } else if(temperature <0){
                    System.out.println("cold");
                }else{
                    System.out.println("stop");
                }
            }
        }
        Air air = new Air();
        air.flow();

    }

}
