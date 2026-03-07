package Enumeration;

public class test {
    public static void main(String[] args) {
        Season spring = new Season("春天","Spring");
        Season winter = new Season("1","Winter");
        Season summer = new Season("2","Summer");
        Season autumn = new Season("3","Autumn");
    }
}

class Season{
    private String name;
    private String desc;

    public Season(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}