import java.math.BigDecimal;

public class test {

    public static void main(String[] args) {
        BigDecimal de = BigDecimal.valueOf(1);
        System.out.println(de.divide(new BigDecimal("1"), 5, BigDecimal.ROUND_HALF_UP));
        System.out.println(de);
    }
}