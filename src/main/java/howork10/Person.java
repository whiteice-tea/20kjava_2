package howork10;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class Person {
    private String name;
    private Vehicles vehicles;

    public Person(String name, Vehicles vehicles) {
        this.name = name;
        this.vehicles = vehicles;
    }

    public void passriver() {
        if(!(this.vehicles instanceof Boat)){
            this.vehicles = VehiclesFactory.getBoat();

        }
        this.vehicles.work();

    }

    public void passenger() {
        if(!(this.vehicles instanceof Horse)){
            this.vehicles = VehiclesFactory.getHorse();

        }
        this.vehicles.work();
    }

    public String getName() {
        return name;
    }

    public Vehicles getVehicles() {
        return vehicles;
    }


}
