package howork10;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class VehiclesFactory {
    public static Horse getHorse(){
        return new Horse();
    }

    public static Boat getBoat(){
        return new Boat();
    }
}
