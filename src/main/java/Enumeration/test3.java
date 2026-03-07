package Enumeration;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class test3 {
    public static void main(String[] args) {
//        System.out.println(Season2.AUTUMN);
    }
}

enum Season2{


//    public static final Season2 SPRING = new Season2("春天","Spring");
//    public static final Season2 SUMMER = new Season2("1","Summer");
//    public static final Season2 AUTUMN = new Season2("2","Autumn");
//    public static final Season2 WINTER = new Season2("3","Winter");
    SPRING("spring","1"),SUMMER("summer","2"),
    AUTUMN("autumn","3"),WINTER("winter","4");

    private String name;
    private String desc;

    private Season2(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }


    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "Season2{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}