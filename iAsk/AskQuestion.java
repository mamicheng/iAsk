package iAsk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.URL;
import java.util.Date;
import java.text.SimpleDateFormat;

public class AskQuestion {
    public static void askQuestion(String username, String password, Statement sql){
        //框架
        JFrame jf = new JFrame("问个问题");
        jf.setIconImage(Toolkit.getDefaultToolkit().getImage(iAsk.class.getResource("/images/Ask.png")));
        jf.setBounds(750,300,400,600);
        jf.setBackground(Color.white);
        Container c = jf.getContentPane();//获取窗体
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        c.setLayout(null);//绝对布局
        c.setBackground(new Color(215,191,185));
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
        //初始化控件
        JLabel l1 = new JLabel("问个问题");
        JLabel l2 = new JLabel("标题：");
        JLabel l3 = new JLabel("具体描述：");

        JTextField jtf1 = new JTextField("请输入标题");
        JTextArea jta = new JTextArea("请描述一下问题");

        JButton b1 = new JButton("提交");
        JButton b2 = new JButton("返回主菜单");
        //设置滚动文本框
        JScrollPane jsp = new JScrollPane(jta);
        //监听按钮事件
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String currentDate = df.format(new Date());
                try {
                    sql.executeUpdate("INSERT INTO Question(QID,UserName,Title,Details,QDate) VALUES('1','"
                            + username + "','" + jtf1.getText() + "','" +jta.getText()+"','"+ currentDate + "')");
                    l1.setText("提交问题成功");
                }catch (SQLException E) {
                        System.out.println(E);
                        System.out.println("系统出错，请重新操作");
                    }
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new MenuPage().menuPage(username, password, sql);
            }
        });
        //加载图片
        URL url1 = jf.getClass().getResource("/images/Ask.png");
        Icon icon1 = new ImageIcon(url1);
        JLabel l4 = new JLabel();
        l4.setIcon(icon1);
        //控件布局
        l1.setBounds(30,30,320,40);
        l1.setFont(new Font("黑体",Font.PLAIN,36));
        l1.setHorizontalAlignment(JTextField.CENTER);
        l2.setBounds(20,80,150,30);
        l3.setBounds(20,160,150,30);
        l4.setBounds(80,30,320,40);

        jtf1.setBounds(20,120,320,30);
        jsp.setBounds(20,200,320,280);

        b1.setBounds(65,500,100,30);
        b2.setBounds(205,500,100,30);

        c.add(l1);
        c.add(l2);
        c.add(l3);
        c.add(jtf1);
        c.add(jsp);
        c.add(l4);
        c.add(b1);
        c.add(b2);

        jf.setVisible(true);
    }
}
