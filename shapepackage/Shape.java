package shapepackage;
import java.awt.Graphics;
import java.awt.Color;
public abstract class Shape {
    // here all the 4 common attributes of all shapes
    private int x;
    private int y;
    private int x2;
    private int y2;
    private Color color;
    private boolean solid;
    public void setColor(Color c){color = c;}
    public Color getColor() {return color;}
    public void setSolid(boolean v){solid = v;}
    public boolean getSolid() {return solid;}
    public void setX(int att){x = att;}
    public void setY(int att){y = att;}
    public void setX2(int att){x2 = att;}
    public void setY2(int att){y2 = att;}
    public int getX() {return x;}
    public int getY() {return y;}
    public int getX2() {return x2;}
    public int getY2() {return y2;}

    public abstract void draw(Graphics g);

}






