package shapepackage;
import java.awt.Graphics;
import java.awt.Color;
public class Line extends Shape{
    public Line(int x, int y, int x2, int y2, Color c, boolean s){
        setX(x);
        setY(y);
        setX2(x2);
        setY2(y2);
        setColor(c);
        setSolid(s);
    }
    public Line (){}
    public void draw(Graphics g){
        g.drawLine(getX(), getY(), getX2(), getY2());
    } 
}
