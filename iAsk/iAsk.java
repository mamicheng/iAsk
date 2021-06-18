package iAsk;

import javafx.scene.image.Image;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.sql.*;
import java.util.Timer;
import java.util.*;


public class iAsk extends JFrame{
    static String username;
    private static String password;
    static boolean isLogin = false;


    public static void logInOperation(JLabel l4, JFrame jf, Statement sql){
        // 加载桥连接器的驱动程序
        try{
            ResultSet rs = sql.executeQuery("SELECT *from Users WHERE UserName='" + username + "' AND Password = '" + password + "'");

            if (rs.next()) {
                System.out.println("登陆成功");
                l4.setText("用户"+username+"登录成功");
                isLogin = true;
                //延时1s
                Timer timer = new Timer();// 实例化Timer类
                timer.schedule(new TimerTask() {
                    public void run() {
                        l4.setText("请等待跳转");
                        jf.dispose();
                        new MenuPage().menuPage(username,password,sql);
                    }
                }, 2000);// 这里百毫秒
            } else {
                l4.setText("用户"+username+"不存在或密码错误");
            }

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("系统出错，请重新操作");
        }

    }

    public static void logInPage(Statement sql){
        JFrame jf = new JFrame();
        jf.setTitle("iAsk登录界面");
        jf.setIconImage(Toolkit.getDefaultToolkit().getImage(iAsk.class.getResource(
                "/images/Title.png")));
        jf.setBounds(750,300,400,400);
        Container c = jf.getContentPane();//获取窗体
        jf.setResizable(false);

        jf.addMouseListener(new MouseListener() {//好耶
            @Override
            public void mouseClicked(MouseEvent e) {

            }
            @Override
            public void mousePressed(MouseEvent e) {
                c.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                        new ImageIcon("C:\\Users\\www19\\IdeaProjects\\temp\\src\\images\\ok.png").getImage()
                        , new Point(10,10),"cursor"));
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

        c.setBackground(new Color(255,255,255));
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//默认关闭模式
        // 实例化控件
        c.setLayout(null);//绝对布局
        JLabel l1 = new JLabel("用户名"), l2 = new JLabel("密码")
                , l3 = new JLabel("欢迎来到iAsk"), l4 = new JLabel("请先登录");
        JButton b1 = new JButton("登录");
        JPasswordField jp = new JPasswordField();
        JTextField jtf = new JTextField("请输入用户名");
        //跳转注册页面
        JLabel l8 = new JLabel("<html><u>还没有账户？点击加入iAsk</u></html>");
        l8.setForeground(Color.BLUE);
        //对标签进行鼠标监听
        l8.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jf.dispose();
                RegisterPage.registerPage(sql);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                l8.setForeground(Color.MAGENTA);//标签变紫
                l8.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//鼠标变换
            }

            @Override
            public void mouseExited(MouseEvent e) {
                l8.setForeground(Color.BLUE);
            }
        });
        //增加图片
        URL url1 = jf.getClass().getResource("/images/User.png")
            , url2 = jf.getClass().getResource("/images/Key.png")
            , url3 = jf.getClass().getResource("/images/LogInBG.gif");
        Icon icon1 = new ImageIcon(url1), icon2 = new ImageIcon(url2)
                    , icon3 = new ImageIcon(url3);
        JLabel l5 = new JLabel(), l6 = new JLabel()
                , l7 = new JLabel();
        l5.setIcon(icon1);
        l6.setIcon(icon2);
        l7.setIcon(icon3);
        //文本框格式
        jtf.setBorder(new RoundBorder());//边框颜色，圆角
        //密码框格式
        jp.setEchoChar('\u2605');
        jp.setBorder(new RoundBorder());
        //标签框格式
        l3.setFont(new Font("黑体",Font.PLAIN,36));
        l4.setFont(new Font("黑体",Font.PLAIN,25));
        l4.setHorizontalAlignment(JTextField.CENTER);
        //按钮格式
        b1.setBorder(new RoundBorder());
        //按钮事件
        b1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                b1.setBorder(BorderFactory.createLoweredSoftBevelBorder());
                b1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                b1.setBorder(BorderFactory.createRaisedSoftBevelBorder());
                b1.setBorder(new RoundBorder());
            }
        });

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = jtf.getText();
                char str[] = jp.getPassword();
                password = new String(str);
                logInOperation(l4,jf,sql);
            }
        });
        jf.getRootPane().setDefaultButton(b1);//监听回车实现b1的功能

        //布局
        l1.setBounds(95,180,80,30);
        l2.setBounds(109,220,80,30);
        l3.setBounds(100,50,220,30);
        l4.setBounds(0,130,400,30);

        l5.setBounds(80,180,80,30);
        l6.setBounds(94,220,80,30);
        l7.setBounds(0,0,400,130);
        l8.setBounds(132,250,180,30);

        b1.setBounds(135,290,150,30);

        jtf.setBounds(145,180,150,30);
        jp.setBounds(145,220,150,30);

        //添加控件
        c.add(b1);

        c.add(l1);
        c.add(l2);
        c.add(l3);
        c.add(l4);

        c.add(jtf);
        c.add(jp);

        c.add(l5);
        c.add(l6);
        c.add(l7);
        c.add(l8);

        jf.setVisible(true);//可视化
    }
    public static void main(String args[]){
        try {
            Class.forName("com.hxtt.sql.access.AccessDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("加载桥连接器驱动失败！");
        }

        // 连接到数据库
        try {
            Connection con = DriverManager.getConnection("jdbc:Access://./User1.mdb", "", "");
            // 向数据库发送SQL语句
            Statement sql = con.createStatement();
            logInPage(sql);
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("系统出错，请重新操作");
        }

    }

}
