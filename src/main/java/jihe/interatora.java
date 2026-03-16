package jihe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class interatora {
    public static void main(String[] args) {
        Collection books = new ArrayList();

        books.add(new Book("Java基础", "张三", 45.0));
        books.add(new Book("数据结构", "李四", 58.5));
        books.add(new Book("操作系统", "王五", 62.0));
        books.add(new Book("计算机网络", "赵六", 39.9));

        Iterator iterator = books.iterator();//先得到对应的迭代器
        while (iterator.hasNext()) {//判断是否还有数据
            Book book = (Book) iterator.next();
            System.out.println(book);
        }
        //ctrl+j快捷键  itit
//        while (iterator.hasNext()) {
//            Object next =  iterator.next();
//
//        }
        //如果再次遍历需要 重置迭代器
        iterator = books.iterator();

        //快捷键 I
        for(Object a:books){
            System.out.println(a);
        }



    }
}
class Book {
    private String name;
    private String author;
    private double price;

    public Book() {
    }

    public Book(String name, String author, double price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{name='" + name + "', author='" + author + "', price=" + price + "}";
    }
}
