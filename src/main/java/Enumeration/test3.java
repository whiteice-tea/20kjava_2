package Enumeration;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class test3 {
    public static void main(String[] args) {

//        System.out.println(Season2.AUTUMN);
        Season2 spring =Season2.SPRING;
        Season2 autumn = Season2.AUTUMN;
        System.out.println(autumn.name());
        //ordinal 输出的是该枚举对象的次序/编号   从零开始
        System.out.println(spring.ordinal());
        System.out.println(autumn.ordinal());
        //values方法，返回Season2[]

        Season2[] values = Season2.values();
        for(Season2 s:values){
            System.out.println(s.name());
        }


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