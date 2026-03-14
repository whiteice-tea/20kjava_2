package wrapper_exc;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class ARRAYex {
    public static void main(String[] args) {
        Book[] books = new Book[4];
        books[0] = new Book("hlm",100);
        books[1] = new Book("jpm",200);
        books[2] = new Book("qnwz",600);
        books[3] = new Book("java",400);

//        Arrays.sort(books, new Comparator() {
//            @Override
//            public int compare(Object o1,Object o2){
//                  Book b1 = (Book)o1;
//                  Book b2 = (Book)o2;
//                  double priceval= b2.getPrice()-b1.getPrice();
//                  if(priceval>0){
//                      return -1;
//                  }else if(priceval<0){
//                      return 1;
//                  }else{
//                      return 0;
//                  }
//            }
//        });

        Arrays.sort(books, new Comparator() {
           @Override
           public int compare(Object o1,Object o2){
               Book b1 = (Book)o1;
               Book b2 = (Book)o2;
               double priceval= b2.getPrice()-b1.getPrice();
               if(priceval<0){
                   return -1;
               }else if(priceval>0){
                   return 1;
               }else {
                   return 0;
               }
           }

        });

        Arrays.sort(books, new Comparator() {
           @Override
           public int compare(Object o1,Object o2){
               Book b1 = (Book)o1;
               Book b2 = (Book)o2;
               double strlength = b2.getName().length() - b1.getName().length();
               if(strlength>0){
                   return -1;
               }else if(strlength<0){
                   return 1;
               }else{
                   return 0;
               }
           }
        });
        System.out.println(Arrays.toString(books));

        sort_price(books,new Comparator() {
            @Override
            public int compare(Object o1,Object o2){
                Book b1 = (Book)o1;
                Book b2 = (Book)o2;
                double priceval= b2.getPrice()-b1.getPrice();
                if(priceval<0){
                    return -1;
                }else if(priceval>0){
                    return 1;
                }else {
                    return 0;
                }
            }
        });
        System.out.println(Arrays.toString(books));
    }
    public static void sort_price(Book[] a, Comparator c){
            for(int i=0;i<a.length;i++){
                for(int j=i+1;j<a.length;j++){
                    if(c.compare(a[i],a[j])<0){
                        Book temp=a[i];
                        a[i]=a[j];
                        a[j]=temp;
                    }
                }
            }
    }

}

class Book{
    private String name;
    private double price;

    public Book(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
