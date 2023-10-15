import java.math.BigDecimal;

public class test {

    public static void main(String[] args) {
        BigDecimal de = BigDecimal.valueOf(3.14);
        System.out.println(de.divide(new BigDecimal(2)));
        System.out.println(de);
        System.out.println(3.14/2);
    }
}