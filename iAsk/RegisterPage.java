package iAsk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterPage {

    public static void confirmPage(Statement sql, JFrame jf,
                                JTextField jtf1, JTextField jtf2,
                                JTextField jtf4, JTextField jtf6){
        JFrame jf2 = new JFrame("确认上传？");
        jf2.setIconImage(Toolkit.getDefaultToolkit().getImage(iAsk.class.getResource("/images/Register.png")));
        jf2.setBounds(750, 300, 300, 200);
        Container c = jf2.getContentPane();//获取窗体
        jf2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        c.setLayout(null);//绝对布局
        c.setBackground(new Color(255,255,128));

        JLabel l1 = new JLabel("确认上传信息？");
        JButton b1 = new JButton("是"), b2 = new JButton("否");

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf2.dispose();
                try {
                    sql.executeUpdate("INSERT INTO Users (UserName, Password, SName, Email) VALUES('"
                            + jtf1.getText() + "','" + jtf2.getText() + "','" + jtf4.getText() + "','" + jtf6.getText()
                            + "');");
                    jf.dispose();
                    new iAsk().logInPage(sql);
                } catch (SQLException E) {
                    System.out.println(E);
                    System.out.println("系统出错，请重新操作");
                }
            }
        });
        b1.setBorder(new RoundBorder());
        b1.setBorder(BorderFactory.createLoweredSoftBevelBorder());

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf2.dispose();
            }
        });
        b2.setBorder(new RoundBorder());
        b2.setBorder(BorderFactory.createLoweredSoftBevelBorder());

        l1.setBounds(110,20,100,30);
        b1.setBounds(80,80,60,30);
        b2.setBounds(170,80,60,30);

        c.add(l1);
        c.add(b1);
        c.add(b2);

        jf2.setVisible(true);
    }

    public static void registerPage(Statement sql) {
        JFrame jf = new JFrame("显示问题");
        jf.setIconImage(Toolkit.getDefaultToolkit().getImage(iAsk.class.getResource("/images/Register.png")));
        jf.setBounds(750, 300, 400, 600);
        Container c = jf.getContentPane();//获取窗体
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        c.setLayout(null);//绝对布局
        c.setBackground(new Color(33,46,63));
        jf.addMouseListener(new MouseListener() {//好耶
            @Override
            public void mouseClicked(MouseEvent e) {

            }
            @Override
            public void mousePressed(MouseEvent e) {
                c.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                        new ImageIcon("C:\\Users\\www19\\IdeaProjects\\temp\\src\\images\\ok.png").getImage()
                        , new Point(20,20),"cursor"));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                c.setCursor(Cursor.getDefaultCursor());
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        JLabel l1 = new JLabel("用户名"), l2 = new JLabel("密码")
                , l3 = new JLabel("确认密码"), l4 = new JLabel("姓名")
                , l5 = new JLabel("职业"), l6 = new JLabel("E-mail")
                , l7 = new JLabel("欢迎加入iAsk，请先完善信息");
        l1.setForeground(Color.WHITE);
        l2.setForeground(Color.WHITE);
        l3.setForeground(Color.WHITE);
        l4.setForeground(Color.WHITE);
        l5.setForeground(Color.WHITE);
        l6.setForeground(Color.WHITE);
        l7.setForeground(Color.WHITE);
        JTextField jtf1 = new JTextField("请设置您的用户名"), jtf2 = new JTextField("请设置密码"),
                   jtf3 = new JTextField("请确认密码"), jtf4 = new JTextField("请输入您的真实姓名"),
                   jtf6 = new JTextField("请输入您的邮箱地址");
        String[] jobList = new String[]{"学生","老师"};
        JComboBox<String> cb = new JComboBox<>(jobList);
        JButton b1 = new JButton("注册好啦"), b2 = new JButton("返回登录");
        l7.setFont(new Font("SimHei",Font.CENTER_BASELINE,27));
        l7.setHorizontalAlignment(JTextField.CENTER);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmPage(sql, jf, jtf1, jtf2, jtf4, jtf6);
                }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new iAsk().logInPage(sql);
            }
        });
        l1.setBounds(40,105,50,30);
        l2.setBounds(50,165,50,30);
        l3.setBounds(30,228,55,30);
        l4.setBounds(50,285,50,30);
        l5.setBounds(50,345,50,30);
        l6.setBounds(45,408,50,30);
        l7.setBounds(0,20,400,60);

        jtf1.setBounds(85,100,250,50);
        jtf2.setBounds(85,160,250,50);
        jtf3.setBounds(85,220,250,50);
        jtf4.setBounds(85,280,250,50);
        cb.setBounds(85,340,250,50);
        jtf6.setBounds(85,400,250,50);

        b1.setBounds(85,480,100,30);
        b2.setBounds(205,480,100,30);

        c.add(l1);
        c.add(l2);
        c.add(l3);
        c.add(l4);
        c.add(l5);
        c.add(l6);
        c.add(l7);

        c.add(jtf1);
        c.add(jtf2);
        c.add(jtf3);
        c.add(jtf4);
        c.add(cb);
        c.add(jtf6);

        c.add(b1);
        c.add(b2);

        jf.setVisible(true);
    }
}
