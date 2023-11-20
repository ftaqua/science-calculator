package POJO;


import java.util.*;
import java.math.*;


public class calculate {

    // 符号栈
    private Stack<String> signStack = new Stack();

    // 数字栈
    private Stack<BigDecimal> numStack = new Stack();


    /*******************************************将军的恩情还不完*************************************************/


    // 额switch可以判断引用类型变量的值相等吗{0:'+', 1:'-', 2:'x', 3:'/'}
    private static final String signCode = "+-x/()";

    // 计算器主体模块
    public BigDecimal work(BigDecimal caled, BigDecimal cal, String sign) {
        BigDecimal ans = null;
        switch (signCode.indexOf(sign)) {
            case 0:
                ans = caled.add(cal);
                break;
            case 1:
                ans = caled.subtract(cal);
                break;
            case 2:
                ans = caled.multiply(cal);
                break;
            case 3:
                try {
                    ans = caled.divide(cal);
                }catch (ArithmeticException AE) {
                    if(AE.getLocalizedMessage().equals("Non-terminating decimal expansion; no exact representable decimal result."))
                        ans = caled.divide(cal, 5, BigDecimal.ROUND_HALF_UP);
                    else
                        ans = BigDecimal.valueOf(32202);
                    System.out.println("Exception : "+AE.getLocalizedMessage());
                }
                break;
            case 4:
            case 5:
                this.numStack.push(caled);
                ans = cal;
                break;
            default:
                ans = null;
        }
        return ans;
    }

    // 设计开方（牛顿莱布尼兹）
    public static BigDecimal niuton(BigDecimal caled) {
        BigDecimal ans;
        if (caled.doubleValue() < 0) {
            System.out.println("Exception : Negative caled");
            return BigDecimal.valueOf(32202);
        }
        double x = 1;
        double y = x - (x * x - caled.doubleValue()) / (2 * x);
        while (x - y > 0.00000001 || x - y < -0.00000001) {
            x = y;
            y = x - (x * x - caled.doubleValue()) / (2 * x);
        }
        ans = BigDecimal.valueOf(y);
        return ans;
    }

    // 设计平方
    public static BigDecimal square(BigDecimal caled) {
        return caled.pow(2);
    }

    // 设计清屏
    public void refresh() {
        this.numStack.clear();
        this.signStack.clear();
        this.signStack.push("=");
        // 解决计算当(x+y)后输入符号时，需要出栈两个数进行括号运算(即将数按顺序压回去)时数字栈只有一个栈的问题。
        this.numStack.push(new BigDecimal(0));
    }


    /**********************************************入集中营**************************************************/

    // 索引，见详细设计
    private String index = "+-x/()=";

    // 数据，见详细设计^^_  ,>为0,<为1,=为2,null为3
    private int[][] compareToSign = {{0, 0, 1, 1, 1, 0, 0}, {0, 0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 0}, {1, 1, 1, 1, 1, 2, 3}, {0, 0, 0, 0, 3, 0, 0}, {1, 1, 1, 1, 1, 3, 2}};


    // 数字入栈
    public void numPush(String decimal) {
        this.numStack.push(new BigDecimal(decimal));
    }

    public void numPush(BigDecimal decimal) {
        this.numStack.push(decimal);
    }


    // 控制流，详细见详细设计p1
    public void calIOC(String topSign) {
        BigDecimal caled, cal;
        String temp;
        temp = this.signStack.peek();
        switch (this.compareToSign[index.indexOf(temp)][index.indexOf(topSign)]) {
            case 0:
                cal = this.numStack.pop();
                caled = this.numStack.pop();
                temp = this.signStack.pop();
                this.numStack.push(this.work(caled, cal, temp));
                this.signStack.push(topSign);
                break;
            case 1:
                this.signStack.push(topSign);
                break;
            case 2:
                this.signStack.pop();
                break;
            default:
                System.out.println("Exception : wrong I/O");
                break;
        }
    }


    // 等号入栈
    public BigDecimal equaIOC() {
        BigDecimal ans, caled, cal;
        String topSign;
        while (!"=".equals(this.signStack.peek())) {
            topSign = this.signStack.pop();
            cal = this.numStack.pop();
            caled = this.numStack.pop();
            this.numStack.push(this.work(caled, cal, topSign));
        }
        ans = this.numStack.pop();
        return ans;
    }

    // pow的IO流控制
    public void powIOC(String topSign) {
        BigDecimal temp;
        temp = this.numStack.pop();
        if (topSign.equals("^2")) {
            this.numStack.push(calculate.square(temp));
        } else {
            this.numStack.push(calculate.niuton(temp));
        }
    }

    public static void main(String[] args) {
        calculate c = new calculate();
        c.numPush("2");
        c.powIOC("^2");
        System.out.println(c.numStack.peek());
    }

    // 通过循环执行运算功能直到左括号为栈顶符号来规避括号内有运算符
    public void barcketIOC() {
        BigDecimal caled, cal;
        String topSign;
        while (!"(".equals(this.signStack.peek())) {
            topSign = this.signStack.pop();
            cal = this.numStack.pop();
            caled = this.numStack.pop();
            this.numStack.push(this.work(caled, cal, topSign));
        }
        this.signStack.pop();
    }


}
