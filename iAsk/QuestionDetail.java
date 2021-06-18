package iAsk;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

public class QuestionDetail {
    static String AID;
    static String user_Answer;
    static String QID;
    static String answerNum;
    public  static void description(Statement sql, JTextField jtf1, JTextField jtf2){
        try{
            System.out.println(QID);
            ResultSet rs = sql.executeQuery("SELECT *from Question WHERE QID='"+QID+"';");
            while(rs.next()) {
                jtf1.setText(rs.getString("Title"));
                jtf2.setText(rs.getString("Details") + "\n@" + rs.getString("UserName"));
            }
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("系统出错，请重新操作2");
        }
    }
    public static void show(Statement sql, JTable table){
        try{
            ResultSet rs = sql.executeQuery("SELECT *from Answer");
            DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            //添加列
            tableModel.addColumn("回答编号");
            tableModel.addColumn("回答者");
            tableModel.addColumn("具体描述");
            tableModel.addColumn("评论数");
            tableModel.addColumn("点赞数");

            //设置行列长宽
            TableColumn column1 = table.getColumnModel().getColumn(0);
            column1.setPreferredWidth(71);
            TableColumn column2 = table.getColumnModel().getColumn(1);
            column2.setPreferredWidth(93);
            TableColumn column3 = table.getColumnModel().getColumn(2);
            column3.setPreferredWidth(234);
            TableColumn column4 = table.getColumnModel().getColumn(3);
            column4.setPreferredWidth(98);
            TableColumn column5 = table.getColumnModel().getColumn(4);
            column5.setPreferredWidth(42);
            table.setRowHeight(40);
            //表格添加数据
            if(answerNum.equals("0")){
                //回答数为零时
                Object[] objects = {"无","无","无","无","无"};
                tableModel.addRow(objects);
            }
            else{//回答数不为零时
                while(rs.next()){
                    Object[] objects = {
                             rs.getString("AID")
                            ,rs.getString("UserName")
                            ,rs.getString("Answer")
                            ,rs.getString("CommentNum")
                            ,rs.getString("Likes")
                    };
                    tableModel.addRow(objects);
                }
            }
            rs.close();
            }
        catch (SQLException e) {
                System.out.println(e);
                System.out.println("系统出错，请重新操作2");
            }
    }
    public static void detail(String username,String password
                            , String Q_ID, Statement sql){
        QID = Q_ID;
        JFrame jf = new JFrame("显示问题");
        jf.setIconImage(Toolkit.getDefaultToolkit().getImage(iAsk.class.getResource("/images/Answer.png")));
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

        JLabel l1 = new JLabel("问题描述"), l2 = new JLabel("具体描述");
        JTextField jtf1 = new JTextField(), jtf2 = new JTextField();
        JButton b1 = new JButton("我来回答"), b2 = new JButton("返回上一页");
        JTable table = new JTable(){
            //设置表格不可编辑
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        JTableHeader tableHeader = table.getTableHeader();
        ListSelectionModel selectionMode = table.getSelectionModel();


        l1.setBounds(58,32,63,32);
        l2.setBounds(58,63,63,32);
        jtf1.setBounds(129,35,414,25);
        jtf1.setEditable(false);
        jtf2.setBounds(129,63,414,202);
        jtf2.setEditable(false);
        b1.setBounds(82,707,127,52);
        b2.setBounds(395,707,127,52);
        table.setBounds(58,313,486,390);
        tableHeader.setBounds(58,285,486,30);
        table.setBackground(new Color(219,248,255));
        tableHeader.setBackground(new Color(88,183,248));

        b1.setBorder(new RoundBorder());
        //按钮事件
        b1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jf.dispose();
                new AnswerQuestion().answerQuestion(username,password,sql,QID);
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
                new ShowQuestions().showQuestions(username,password, sql);
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
        //获取回答数
        try{
            ResultSet rs2 = sql.executeQuery("SELECT AnswerNum FROM Question  WHERE QID = '"+QID+"';");
            if(rs2.next()){
                answerNum = rs2.getString("AnswerNum");
            }
            rs2.close();
        }catch (SQLException e) {
            System.out.println(e);
            System.out.println("系统出错，请重新操作1");
        }

        description(sql,jtf1,jtf2);
        show(sql, table);

        selectionMode.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                AID = (String) table.getValueAt(table.getSelectedRow(),0);
                user_Answer = (String) table.getValueAt(table.getSelectedRow(),1);
                jf.dispose();
                new AnswerDetails().answerDetail(username,password,sql,user_Answer,AID);
            }
        });//监听选行
        table.addMouseListener(new MouseListener() {
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
        });//监听表内鼠标


        c.add(l1);
        c.add(l2);
        c.add(jtf1);
        c.add(jtf2);
        c.add(b1);
        c.add(b2);
        c.add(table);
        c.add(tableHeader);

        jf.setVisible(true);
    }
}
