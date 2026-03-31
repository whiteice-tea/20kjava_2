package collection_exc;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class homework1 {
    public static void main(String[] args) {
        ArrayList<News> Fox_news = new ArrayList<News>();
        Fox_news.add(new News("张雪峰前妻好友发声! 曝现任付某很有手段，刚在一起就要了一套房","News1"));
        Fox_news.add(new News("陈怡蓉晒温馨日常！7 岁女儿长睫戴镜超可爱，与爸妈亲密互动超暖心","News2"));
        for(int i=Fox_news.size()-1;i>=0;i--){
            String title = Fox_news.get(i).getTitle();
            if(title.length()>15){
                title = title.substring(0,15)+"...";
            }
            Fox_news.get(i).setTitle(title);
            System.out.println(Fox_news.get(i));
        }
        System.out.println(Fox_news);

    }
}

class News{
    String title;
    String content;

    public News(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return title + "\n";
    }

}
