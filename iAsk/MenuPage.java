package iAsk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.sql.*;


public class MenuPage {

    public static void menuPage(String username, String password, Statement sql){
        JFrame jf = new JFrame("菜单");
        jf.setIconImage(Toolkit.getDefaultToolkit().getImage(iAsk.class.getResource("/images/Menu.png")));
        jf.setBounds(750,300,400,600);
        Container c = jf.getContentPane();//获取窗体
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        c.setBackground(new Color(255,255,255));
        c.setLayout(null);//绝对布局
        c.setBackground(new Color(129,254,138));
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

        //实例控件
        JButton b1 = new JButton("修改密码"), b2 = new JButton("显示问题")
                , b3 = new JButton("问个问题"), b4 = new JButton("退出系统");
        JLabel l1 = new JLabel("欢迎用户 "+username+ " 来到iAsk系统")
                , l2 = new JLabel("请选择接下来的操作");
        //标签格式
        l1.setBounds(0,40,400,60);
        l2.setBounds(40,75,300,60);
        l1.setFont(new Font("黑体",Font.PLAIN,23));
        l2.setFont(new Font("黑体",Font.PLAIN,18));
        l1.setHorizontalAlignment(JTextField.CENTER);
        l2.setHorizontalAlignment(JTextField.CENTER);

        //添加图片
        URL url1 = jf.getClass().getResource("/images/Modify.png")
            , url2 = jf.getClass().getResource("/images/Show.png")
            , url3 = jf.getClass().getResource("/images/Ask.png")
            , url4 = jf.getClass().getResource("/images/Exit.png");
        Icon icon1 = new ImageIcon(url1), icon2 = new ImageIcon(url2)
                , icon3 = new ImageIcon(url3), icon4 = new ImageIcon(url4);
        JLabel l3 = new JLabel(), l4 = new JLabel()
                , l5 = new JLabel(), l6 = new JLabel();
        l3.setIcon(icon1);
        l4.setIcon(icon2);
        l5.setIcon(icon3);
        l6.setIcon(icon4);

        l3.setBounds(75,150,150,60);
        l4.setBounds(75,250,150,60);
        l5.setBounds(75,350,150,60);
        l6.setBounds(75,450,150,60);

        //按钮格式
        b1.setBounds(110,150,150,60);
        b2.setBounds(110,250,150,60);
        b3.setBounds(110,350,150,60);
        b4.setBounds(110,450,150,60);
        //监听按钮
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new ModifyPwd().modifyPwdPage(username, password, sql);
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new ShowQuestions().showQuestions(username, password, sql);
            }
        });
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new AskQuestion().askQuestion(username,password,sql);
            }
        });
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        c.add(l1);
        c.add(l2);

        c.add(l3);
        c.add(l4);
        c.add(l5);
        c.add(l6);

        c.add(b1);
        c.add(b2);
        c.add(b3);
        c.add(b4);

        jf.setVisible(true);
    }
}
