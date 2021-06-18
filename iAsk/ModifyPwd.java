package iAsk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.net.URL;


public class ModifyPwd {
    private static String new_password;
    private static String newPwd2;
    public static boolean modifyPwd(String originalPwd, JTextField jtf1
                                    , JTextField jtf2, JTextField jtf3
                                    , JLabel l5, JLabel l6){
        String myPwd = jtf1.getText();

        if (myPwd.equals(originalPwd)) {
            l5.setText("原密码匹配");
            new_password = jtf2.getText();
            newPwd2 = jtf3.getText();

        } else {
            l5.setText("原密码不匹配");
        }
        //判空
        if (new_password == null) {
            l6.setText("新密码不准为空");
            return false;
        }

        if(new_password.equals(newPwd2)){
            l5.setText("");
            l6.setText("新密码确认成功");
            return true;
        }else{
            l6.setText("新密码不一致");
            return false;
        }

    }

    public static void modifyPwdPage(String username, String password,Statement sql){
        JFrame jf = new JFrame("修改密码");
        jf.setIconImage(Toolkit.getDefaultToolkit().getImage(iAsk.class.getResource("/images/Modify.png")));
        jf.setBounds(750,300,400,600);
        Container c = jf.getContentPane();//获取窗体
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        c.setLayout(null);//绝对布局
        c.setBackground(new Color(203,190,209));
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
        JTextField jtf1 = new JTextField("请输入原密码")
                , jtf2 = new JTextField("请输入新密码")
                , jtf3 = new JTextField("请在此输入新密码");
        JLabel l1 = new JLabel("原密码 ")
                , l2 = new JLabel("新密码")
                , l3 = new JLabel("确认新密码")
                , l4 = new JLabel("修改密码界面")
                , l5 = new JLabel()
                , l6 = new JLabel();
        JButton b1 = new JButton("确认"), b2 = new JButton("返回主菜单");
        //加载图片
        URL url1 = jf.getClass().getResource("/images/Modify.png");
        Icon icon1 = new ImageIcon(url1);
        JLabel l7 = new JLabel();
        l7.setIcon(icon1);
        //按钮格式
        b1.setBounds(95,400,100,30);
        b2.setBounds(205,400,100,30);
        //监听按钮事件
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean confirm = modifyPwd(password,jtf1,jtf2,jtf3,l5,l6);
                if(confirm){
                    try {
                        sql.executeUpdate("UPDATE Users SET Password='"
                                + new_password + "' WHERE UserName='" + username + "'");
                        l4.setText("密码修改成功");
                    }catch (SQLException E) {
                        System.out.println(E);
                        System.out.println("系统出错，请重新操作");
                    }
                }
                else{
                    l4.setText("请检查输入内容");
                }
                }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new MenuPage().menuPage(username,new_password,sql);
            }
        });
        //文本栏格式
        jtf1.setBounds(145,200,150,30);
        jtf2.setBounds(145,270,150,30);
        jtf3.setBounds(145,340,150,30);
        //标签格式
        l1.setBounds(100,200,150,30);
        l2.setBounds(100,270,150,30);
        l3.setBounds(75,340,150,30);
        l4.setBounds(30,90,320,40);
        l4.setFont(new Font("黑体",Font.PLAIN,36));
        l4.setHorizontalAlignment(JTextField.CENTER);
        l5.setBounds(145,230,150,30);
        l6.setBounds(145,310,150,30);

        c.add(jtf1);
        c.add(jtf2);
        c.add(jtf3);
        c.add(l1);
        c.add(l2);
        c.add(l3);
        c.add(l4);
        c.add(l5);
        c.add(l6);
        c.add(l7);
        c.add(b1);
        c.add(b2);

        jf.setVisible(true);
    }
}
