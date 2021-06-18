//边框样式
package iAsk;

import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
//使用内置Border接口
public class RoundBorder implements Border{
    private Color color;
    public RoundBorder(Color color){
        this.color = color;
    }
    public RoundBorder(){
        this.color =new Color(88,183,248);
    }
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }
    public boolean isBorderOpaque() {
        return false;
    }
    // 实现Border(父类)方法
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width,
                            int height) {
        g.setColor(color);
        g.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 15, 15);
    }
}
