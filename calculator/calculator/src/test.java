import java.math.BigDecimal;

public class test {

    public static void main(String[] args) {
        try {
            throw new Exception();
        }catch (Exception e) {
            System.out.println(1);
        }
        System.out.println(2);
    }
}