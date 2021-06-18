package iAsk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.sql.*;

public class AnswerQuestion {
    static int AID = 0;
    public static void answerQuestion(String username, String password
                                    , Statement sql, String QID){
        //框架
        JFrame jf = new JFrame("回答问题");
        jf.setIconImage(Toolkit.getDefaultToolkit().getImage(iAsk.class.getResource("/images/Answer(write).png")));
        jf.setBounds(750,300,400,600);
        jf.setBackground(Color.white);
        Container c = jf.getContentPane();//获取窗体
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        c.setLayout(null);//绝对布局
        c.setBackground(new Color(112,225,254));
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
        JLabel l1 = new JLabel("回答问题");
        JLabel l3 = new JLabel("具体描述：");

        JTextArea jta = new JTextArea("请描述一下回答");

        JButton b1 = new JButton("提交");
        JButton b2 = new JButton("返回上一页");
        //设置滚动文本框
        JScrollPane jsp = new JScrollPane(jta);
        //监听按钮事件
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //选取对应问题编号的最新一条回答，提取其回答编号
                    ResultSet rs1 = sql.executeQuery("SELECT top 1 * FROM Answer ORDER BY AID ;")
                                , rs2 = sql.executeQuery("SELECT * FROM Question WHERE QID = '"+QID+"';");
                    int answerNum = Integer.parseInt(rs2.getString("AnswerNum"));
                    if(rs1.next()) {
                        //如果是修改答案
                        if(rs1.getString("UserName").equals(username)){
                            sql.executeUpdate("UPDATE Answer SET Answer='"
                                    + jta.getText() + "' WHERE UserName='" + username + "'");
                        }else{
                            //如果是新的回答（之前有回答记录）
                            AID = Integer.parseInt(rs1.getString("AID")) + 1;
                            answerNum+=1;
                            //更新对应问题的回答
                            sql.executeUpdate("INSERT INTO Answer(AID,UserName,Answer,CommentNum,QID) VALUES('" + AID + "','"
                                    + username + "','" + jta.getText() + "','0','" + QID + "');");
                        }
                    }else{
                        //该问题之前无回答记录
                        AID = 1;
                        //更新对应问题的回答
                        sql.executeUpdate("INSERT INTO Answer(AID,UserName,Answer,CommentNum,QID) VALUES('" + AID + "','"
                                + username + "','" + jta.getText() + "','0','" + QID + "');");
                    }
                    //更新回答数
                    sql.executeUpdate("UPDATE Question SET AnswerNum='"
                            + answerNum + "' WHERE QID='" + QID + "'");

                    l1.setText("提交回答成功");

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
                new ShowQuestions().showQuestions(username, password, sql);
            }
        });
        //加载图片
        URL url1 = jf.getClass().getResource("/images/Answer.png");
        Icon icon1 = new ImageIcon(url1);
        JLabel l4 = new JLabel();
        l4.setIcon(icon1);
        //控件布局
        l1.setBounds(30,30,320,40);
        l1.setFont(new Font("黑体",Font.PLAIN,36));
        l1.setHorizontalAlignment(JTextField.CENTER);
        l3.setBounds(20,80,150,30);
        l4.setBounds(60,30,320,40);

        jsp.setBounds(20,120,320,360);

        b1.setBounds(65,500,100,30);
        b2.setBounds(205,500,100,30);

        c.add(l1);
        c.add(l3);
        c.add(jsp);
        c.add(l4);
        c.add(b1);
        c.add(b2);

        jf.setVisible(true);

    }
}
