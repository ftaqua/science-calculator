package com.auqa.version;

// 导入自己的计算类
import POJO.calculate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


// 计算器界面
public class calculator extends JFrame implements ActionListener {

    // 获得显示屏大小
    public static final int SCREAM_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public static final int SCREAM_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    // 根据显示屏高度设定计算器界面大小
    private static final int EXAMPLE = (int) (SCREAM_HEIGHT / 4.32);


    // 字体大小
    Font cu = new Font("粗体", Font.BOLD, (int) (EXAMPLE * 0.2));


    /**********************************************超级初始化块*******************************************************/


    protected void __init__() {
        // 设置窗口名称
        this.setTitle("计算器");
        // 4比3固定窗口
        this.setSize(EXAMPLE * 3, EXAMPLE * 4);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        // 设置窗口可见
        this.setVisible(true);
        this.setBackground(Color.black);
        // 设置关闭按钮(释放进程)
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // 设置方向布局
        this.setLayout(new BorderLayout());
    }


    /**********************************************北国风光*******************************************************/


    // 北面组件
    private JPanel northBox = new JPanel(new FlowLayout());
    private JTextField input = new JTextField();
    private JButton clear = new JButton();


    // 设置北面组件
    private void setNorth() {
        // 设置数字栏
        this.input.setPreferredSize(new Dimension((int) (EXAMPLE * 2.2), (int) (EXAMPLE * 0.4)));
        this.input.setFont(this.cu);
        this.input.setForeground(Color.BLACK);      // 额好像没用,但限制用户输入更重要
        this.input.setEnabled(false);
        this.input.setHorizontalAlignment(SwingConstants.RIGHT);

        // 设置清空
        this.clear.setText("C");
        this.clear.setPreferredSize(new Dimension((int) (EXAMPLE * 0.4), (int) (EXAMPLE * 0.4)));
        this.clear.setFont(this.cu);
        this.clear.setForeground(Color.RED);

        // 安装北仪表
        this.northBox.add(this.input);
        this.northBox.add(this.clear);

        // 安装北仪表到主体
        this.add(this.northBox, BorderLayout.NORTH);
    }


    /**********************************************中央处理器*******************************************************/


    // 中部组件
    private JPanel CPU = new JPanel();
    private JButton[] cal = new JButton[20];
    // 后16个按钮顺序，懒得写集合类了
    String str = "789/456x123-0.=+";

    // 设置中部组件
    private void setCenter() {
        // 划分20格
        this.CPU.setLayout(new GridLayout(5, 4));
        // 设置开方按钮
        this.cal[0] = new JButton();
        this.cal[0].setText("^-2");
        this.cal[0].setPreferredSize(new Dimension((int) (EXAMPLE * 0.2), (int) (EXAMPLE * 0.15)));
        this.cal[0].setFont(this.cu);
        this.cal[0].setForeground(Color.BLUE);
        // 设置括号按钮
        this.cal[1] = new JButton();
        this.cal[1].setText("^2");
        this.cal[1].setPreferredSize(new Dimension((int) (EXAMPLE * 0.2), (int) (EXAMPLE * 0.15)));
        this.cal[1].setFont(this.cu);
        this.cal[1].setForeground(Color.BLUE);
        this.cal[2] = new JButton();
        this.cal[2].setText("(");
        this.cal[2].setPreferredSize(new Dimension((int) (EXAMPLE * 0.2), (int) (EXAMPLE * 0.15)));
        this.cal[2].setFont(this.cu);
        this.cal[2].setForeground(Color.BLUE);

        // 设置清除按钮
        this.cal[3] = new JButton();
        this.cal[3].setText(")");
        this.cal[3].setPreferredSize(new Dimension((int) (EXAMPLE * 0.2), (int) (EXAMPLE * 0.15)));
        this.cal[3].setFont(this.cu);
        this.cal[3].setForeground(Color.BLUE);

        // 设置后16个按钮
        for (int i = 4; i < 20; i++) {
            String temp = this.str.substring(i - 4, i - 3);
            this.cal[i] = new JButton();
            this.cal[i].setText(temp);
            this.cal[i].setPreferredSize(new Dimension((int) (EXAMPLE * 0.2), (int) (EXAMPLE * 0.15)));
            this.cal[i].setFont(this.cu);
            if ("+-x/=".contains(temp)) {
                this.cal[i].setForeground(Color.GRAY);
            }
        }
        // 添加按钮
        for (int i = 0; i < 20; i++) {
            this.CPU.add(this.cal[i]);
        }
        this.add(this.CPU,BorderLayout.CENTER);
    }


    /**********************************************南柯一梦*******************************************************/

    public static final String version = "V1.2.0";
    // 南面组件
    private JLabel message = new JLabel(version, SwingConstants.CENTER);

    // 设置南面组件
    private void setSouth() {
        this.message.setPreferredSize(new Dimension((int) (EXAMPLE * 0.1), (int) (EXAMPLE * 0.1)));
        this.message.setForeground(Color.BLACK);
        this.add(this.message, BorderLayout.SOUTH);
    }


    /*********************************************监听*********************************************************/

    // 给按钮添加监听
    private void setListener() {
        for (JButton j : cal) {
            j.addActionListener(this);
        }
        this.clear.addActionListener(this);
    }

    // 监听事件设置
    @Override
    public void actionPerformed(ActionEvent e) {
        String listen = e.getActionCommand();
        if ("0.1^23456789+-x/()^-2".contains(listen)) {
            this.input.setText(this.input.getText() + listen);
        }
        this.bigWork(listen);
    }


    /*****************************************状态**************************************************/

    // 小数点信号
    private Boolean pointSignal = false;
    // 括号信号
    private int barcketNum = 0;

    private String num = "0123456789";
    private String sign = "+-x/(";

    // 输入的最后一位为数字时的状态，详细见详细设计表格
    public void inNum() {
        // 只能输入pow函数，右括号，数字和符号按钮，不能输入左括号，若小数点信号为真，则可以输入小数点
        for (int i=0;i<20;i++) {
            if("(".equals(this.cal[i].getText())) {
                this.cal[i].setEnabled(false);
            }
            else {
                this.cal[i].setEnabled(true);
            }
        }
        // 根据信号设置
        this.cal[17].setEnabled(this.pointSignal);
    }

    // 输入的最后一位为符号或左括号时
    public void inSign() {
        // 只能输入非小数点数字及左括号，小数点信号开启
        for (int i=0;i<20;i++) {
            if("(".equals(this.cal[i].getText()) || this.num.contains(this.cal[i].getText())) {
                this.cal[i].setEnabled(true);
            }
            else {
                this.cal[i].setEnabled(false);
            }
        }
        this.pointSignal = true;
    }

    // 输入最后一位为右括号或pow运算时
    public void inPow() {
        // 只能输入符号和右括号和pow函数
        for (int i=0;i<20;i++) {
            if("(".equals(this.cal[i].getText()) || this.num.contains(this.cal[i].getText()) || ".".equals(this.cal[i].getText())) {
                this.cal[i].setEnabled(false);
            }
            else {
                this.cal[i].setEnabled(true);
            }
        }
    }

    // 输入最后一位为小数点时
    public void inPoint() {
        // 只能输入非小数点数字，小数点信号关闭
        for (int i=0;i<20;i++) {
            if(this.num.contains(this.cal[i].getText())) {
                this.cal[i].setEnabled(true);
            }
            else {
                this.cal[i].setEnabled(false);
            }
        }
        this.pointSignal = false;
    }

    public void inEqual() {
        for (int i=0;i<20;i++) {
            this.cal[i].setEnabled(false);
        }
    }


    /*****************************************核酸隔离点*********************************************/


    // 真正的超级初始化块
    public calculator() throws HeadlessException {
        // 界面设置
        this.__init__();
        this.setNorth();
        this.setCenter();
        this.setSouth();
        // 交互设置
        this.setListener();
    }


    calculate calculate = new calculate();
    private String temStr = "";
    // 控制器
    public void bigWork(String listen) {
        
        // 记录括号信号
        if ("(".equals(listen)) {
            this.barcketNum++;
        }
        if (")".equals(listen)) {
            this.barcketNum--;
        }
        
        // 基础状体转换
        if (this.num.contains(listen)) {
            this.temStr = this.temStr +listen;
            this.inNum();
        } else if (this.sign.contains(listen)) {
            if(!"".equals(temStr)) {
                this.calculate.numPush(this.temStr);
                this.temStr = "";
            }
            this.calculate.calIOC(listen);
            this.inSign();
        } else if (")".equals(listen) || listen.contains("^")) {
            if(!"".equals(temStr)) {
                this.calculate.numPush(this.temStr);
                this.temStr = "";
            }
            if (listen.contains("^")) {
                calculate.powIOC(listen);
            } else {
                this.calculate.barcketIOC();
            }
            this.inPow();
        } else if (".".equals(listen)) {
            this.temStr = this.temStr +listen;
            this.inPoint();
        }  else if ("=".equals(listen)) {
            if(!"".equals(temStr)) {
                this.calculate.numPush(this.temStr);
                this.temStr = "";
            }
            this.input.setText(this.calculate.equaIOC().toString());
            this.inEqual();
        }else if ("C".equals(listen)) {
            this.calculate.refresh();
            this.input.setText("");
            this.temStr = "";
            this.barcketNum = 0;
            this.inSign();
        } else {
            JOptionPane.showMessageDialog(this, "error : unvaild input");
        }
        
        // 限制用户输入
        if (this.barcketNum < 0) {
            JOptionPane.showMessageDialog(this,"error : wrong number of barcket");
        }
        if(this.barcketNum == 0) {
            this.cal[3].setEnabled(false);
        }

        if (this.barcketNum > 0) {
            this.cal[18].setEnabled(false);
        }
    }

    // 软件测试
    public static void main(String[] args) {
        calculator cal = new calculator();
        JOptionPane.showMessageDialog(cal, "由于框架原因,本计算器打开时可能按钮显示不全,请最小化后打开");
        System.out.println(SCREAM_WIDTH + " * " + SCREAM_HEIGHT + " due " + EXAMPLE);
        cal.inSign();
        cal.calculate.refresh();
    }


}
