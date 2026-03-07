//package Enumeration;
//
///**
// * @author whiteicetea
// * @version 1.0.0
// */
//public class test2 {
//    public static void main(String[] args) {
//         System.out.println(Season2.AUTUMN);
//    }
//}
//class Season2{
//    private String name;
//    private String desc;
//    //将构造器私有化，目的防止直接new
//    //去掉set方法，防止属性被修改
//    //在该类内部，直接创建固定的对象
//    //定义了四个值
//    public static final Season2 SPRING = new Season2("春天","Spring");
//    public static final Season2 SUMMER = new Season2("1","Summer");
//    public static final Season2 AUTUMN = new Season2("2","Autumn");
//    public static final Season2 WINTER = new Season2("3","Winter");
//    private Season2(String name, String desc) {
//        this.name = name;
//        this.desc = desc;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//
//    public String getDesc() {
//        return desc;
//    }
//
//    @Override
//    public String toString() {
//        return "Season2{" +
//                "name='" + name + '\'' +
//                ", desc='" + desc + '\'' +
//                '}';
//    }
//}