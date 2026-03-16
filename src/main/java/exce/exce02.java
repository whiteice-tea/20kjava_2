package exce;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class exce02 {
    public static void main(String[] args) {
        User user = new User("abc", "123456", "abc@qq.com");
        try{
            in_sign(user);
            System.out.println("complete");
        }catch(Exception e){
            System.out.println("false"+e.getMessage());
        }

    }
    public static void in_sign(User user){
        in_sign_mail(user.mail);
        in_sign_password(user.password);
        in_sign_name(user.name);
    }
    public static void in_sign_name(String name){
        if(!(name.length()>=2&&name.length()<=4)){
            throw new RuntimeException("用户名长度必须是2到4位");
        }
    }
    public static void in_sign_password(String password){
        if(!(password.length()==6)){
            throw new RuntimeException("密码长度必须是6位");
        }

        for(int i=0;i<password.length();i++){
            if(!Character.isDigit(password.charAt(i))){
                throw new RuntimeException("密码必须全是数字");
            }
        }
    }
    public static void in_sign_mail(String mail){
        int index1=mail.indexOf("@");
        int index2=mail.indexOf(".");

        if(index1==-1||index2==-1||index1>index2){
            throw new RuntimeException("邮箱格式不正确，必须包含@和.，并且@在.前面");
        }

    }

}
class User {
    String name;
    String password;
    String mail;

    public User(String name, String password, String mail) {
        this.name = name;
        this.password = password;
        this.mail = mail;
    }
}