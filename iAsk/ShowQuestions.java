package iAsk;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

public class ShowQuestions {
    static String QID;

    public static void show(Statement sql, JTable table){
        DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
        //添加列
        tableModel.addColumn("问题编号");
        tableModel.addColumn("提问者");
        tableModel.addColumn("问题标题");
        tableModel.addColumn("提问日期");
        tableModel.addColumn("回答数");

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
        try{
            ResultSet rs = sql.executeQuery("SELECT *from Question");
            while(rs.next()){
                Object[] objects = {rs.getString("QID")
                              ,rs.getString("UserName")
                              ,rs.getString("Title")
                              ,rs.getString("QDate")
                              ,rs.getString("AnswerNum")};
                tableModel.addRow(objects);
            }
            rs.close();
        }catch (SQLException e) {
            System.out.println(e);
            System.out.println("系统出错，请重新操作");
        }
    }

    public static void showQuestions(String username, String password, Statement sql){
        JFrame jf = new JFrame("显示问题");
        jf.setIconImage(Toolkit.getDefaultToolkit().getImage(iAsk.class.getResource("/images/Show.png")));
        jf.setBounds(750,300,600,800);
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

        JLabel l1 = new JLabel("显示问题");
        JButton b1 = new JButton("返回主菜单"), b2 = new JButton("我也想问");
        JTable table = new JTable(){
            //设置表格不可编辑
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        JTableHeader tableHeader = table.getTableHeader();
        ListSelectionModel selectionMode = table.getSelectionModel();

        show(sql,table);

        l1.setBounds(225,14,162,46);
        l1.setFont(new Font("黑体",Font.PLAIN,38));
        l1.setHorizontalAlignment(JTextField.CENTER);

        b1.setBounds(83,655,127,52);
        b2.setBounds(395,655,127,52);

        tableHeader.setBounds(33,66,541,30);
        tableHeader.setBackground(new Color(88,183,248));
        table.setBounds(33,96,541,554);
        table.setBackground(new Color(219,248,255));

        b1.setBorder(new RoundBorder());
        //按钮事件
        b1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jf.dispose();
                new MenuPage().menuPage(username, password, sql);
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
                new AskQuestion().askQuestion(username,password,sql);
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

        selectionMode.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                QID = (String) table.getValueAt(table.getSelectedRow(),0);
                jf.dispose();
                new QuestionDetail().detail(username,password,QID,sql);
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
        c.add(b1);
        c.add(b2);
        c.add(table);
        c.add(tableHeader);

        jf.setVisible(true);
    }
}
