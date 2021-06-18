package iAsk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.sql.*;

public class AnswerDetails{
    static String commentNum="0";
    static String AID;
    static String QID;
    static int comID;
    static int likes;
    static boolean i = false;//点赞标志是否被按下过
    public static void show(Statement sql, JTextArea jta){
        try{
            ResultSet rs = sql.executeQuery("SELECT * FROM Comment WHERE AID = '"+AID+"';");
            while(rs.next()){
                jta.append("@"+rs.getString("UserName")+"\n"+rs.getString("Comment")+"\n");
                comID = Integer.parseInt(rs.getString("ComID"));
            }
        }catch (SQLException e) {
            System.out.println(e);
            System.out.println("系统出错，请重新操作");
        }
    }

    public static void upload(Statement sql,JTextField jtf1, JTextArea jta, String username){
        try{
            String Comment = jtf1.getText();
            //上传最新评论
            jta.append("@"+username+"\n"+Comment+"\n");
            //给该评论下ID
            comID += 1;
            //数据库更新评论
            sql.executeUpdate("INSERT INTO Comment(ComID,Comment,UserName,AID) " +
                    "VALUES ('"+comID+"','"+Comment+"','"+username+"','"+AID+"');");
            int Num = Integer.parseInt(commentNum)+1;
            commentNum = String.valueOf(Num);
            //评论数更新
            sql.executeUpdate("UPDATE Answer SET CommentNum='"
                    + commentNum + "' WHERE AID='" + AID + "'");
        }catch (SQLException e) {
            System.out.println(e);
            System.out.println("系统出错，请重新操作");
        }
    }

    public static void answerDetail(String username, String password
            , Statement sql, String user_Answer,String AI_D){
        AID = AI_D;
        JFrame jf = new JFrame("显示问题");
        jf.setIconImage(Toolkit.getDefaultToolkit().getImage(iAsk.class.getResource("/images/Comment.png")));
        jf.setBounds(750,300,600,850);
        jf.setBackground(Color.white);
        Container c = jf.getContentPane();//获取窗体
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        c.setLayout(null);//绝对布局
        c.setBackground(new Color(244,255,241));

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

        JLabel l1 = new JLabel("用户"), l2 = new JLabel("具体描述")
                    , l3 = new JLabel("我来说两句"), l4 = new JLabel()
                    , l5 = new JLabel();
        l4.setText("@"+user_Answer);
        JTextField jtf1 = new JTextField();
        JTextArea jtf2 = new JTextArea(), jta = new JTextArea();
        JButton b1 = new JButton("我来回答"), b2 = new JButton("返回上一页")
                , b3 = new JButton("提交");
        URL url1 = jf.getClass().getResource("/images/Like-empty.png"),
            url2 = jf.getClass().getResource("/images/Like-full.png");
        Icon icon1 = new ImageIcon(url1),icon2 = new ImageIcon(url2);
        l5.setIcon(icon1);

        try {
            ResultSet rs_1 = sql.executeQuery("SELECT * FROM Answer WHERE AID = '"+AID+"';");
            if(rs_1.next()){
                jtf2.setText(rs_1.getString("Answer"));
                likes = Integer.parseInt(rs_1.getString("Likes"));
                commentNum = rs_1.getString("CommentNum");
                if(commentNum.equals("0"))
                    jta.append("目前暂无评论，快来说几句");
                rs_1.close();
            }

            ResultSet rs_2 = sql.executeQuery("SELECT QID FROM Answer WHERE AID = '"+AID+"';");
            if(rs_2.next()){
                QID = rs_2.getString("QID");
            }
        }catch (SQLException e) {
            System.out.println(e);
            System.out.println("系统出错，请重新操作");
        }

        l5.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                l5.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(i) {
                    l5.setBorder(null);
                    l5.setIcon(icon1);
                    likes-=1;
                    i = false;
                }else {
                    l5.setBorder(null);
                    l5.setIcon(icon2);
                    likes+=1;
                    i = true;
                }
                try {
                    sql.executeUpdate("UPDATE Answer SET Likes='"
                            + likes + "' WHERE AID='" + AID + "'");
                }catch (SQLException E) {
                    System.out.println(E);
                    System.out.println("系统出错，请重新操作");
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });



        l1.setBounds(58,32,63,32);
        l2.setBounds(58,72,63,32);
        l3.setBounds(49,652,77,17);
        l4.setBounds(130,40,98,17);
        jtf1.setBounds(130,648,320,25);
        jtf2.setBounds(130,72,414,204);
        jtf2.setEditable(false);
        b1.setBounds(82,707,127,52);
        b2.setBounds(395,707,127,52);
        b3.setBounds(469,648,70,25);
        l5.setBounds(488,30,37,35);
        jta.setBounds(100,300,441,334);
        jta.setEditable(false);
        jta.setFont(new Font("SimSun",Font.LAYOUT_LEFT_TO_RIGHT,16));

        show(sql,jta);

        b1.setBorder(new RoundBorder());
        //按钮事件
        b1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jf.dispose();
                new AnswerQuestion().answerQuestion(username,password, sql, QID);
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

        b2.setBorder(new RoundBorder());
        //按钮事件
        b2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jf.dispose();
                new QuestionDetail().detail(username,password,QID,sql);
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                b2.setBorder(BorderFactory.createLoweredSoftBevelBorder());
                b2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                b2.setBorder(BorderFactory.createRaisedSoftBevelBorder());
                b2.setBorder(new RoundBorder());
            }
        });

        b3.setBorder(new RoundBorder());
        b3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                upload(sql,jtf1,jta,username);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                b3.setBorder(BorderFactory.createLoweredSoftBevelBorder());
                b3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b3.setBorder(BorderFactory.createRaisedSoftBevelBorder());
                b3.setBorder(new RoundBorder());
            }
        });

        c.add(l1);
        c.add(l2);
        c.add(l3);
        c.add(l4);
        c.add(jtf1);
        c.add(jtf2);
        c.add(b1);
        c.add(b2);
        c.add(b3);
        c.add(l5);
        c.add(jta);


        jf.setVisible(true);
    }
}
