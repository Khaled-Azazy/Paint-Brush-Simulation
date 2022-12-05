package shapepackage;
import java.awt.Graphics;
import java.awt.Color;;

public class Rect extends Shape{
    public Rect (int x, int y, int x2, int y2, Color c, boolean s){
        setX(x);
        setY(y);
        setX2(x2);
        setY2(y2);
        setColor(c);
        setSolid(s);
    }
    public Rect (){}
    public void draw(Graphics g){
        int x1, y1, w, h;
        x1 = y1 = h = w = 0;
        if (getX()<=getX2() && getY2()<=getY()){
            w = getX2()-getX();
            h = getY()-getY2();
            x1 = getX();
            y1 = getY()-h;
        }
        else if (getX()<=getX2() && getY2()>=getY()){
            w = getX2()-getX();
            h = getY2()-getY();
            x1 = getX();
            y1 = getY();
        }
        else if (getX()>=getX2() && getY2()>=getY()){
            w = getX()-getX2();
            h = getY2()-getY();
            x1 = getX()-w;
            y1 = getY2()-h;
        }
        else{
            w = getX()-getX2();
            h = getY()-getY2();
            x1 = getX2();
            y1 = getY2();
        }
        if(getSolid())
            g.fillRect(x1, y1, w, h);
        else
            g.drawRect(x1, y1, w, h);
    }

    public void getRectDim(){
        int x1, y1, w, h;
        x1 = y1 = h = w = 0;
        if (getX()<=getX2() && getY2()<=getY()){
            w = getX2()-getX();
            h = getY()-getY2();
            x1 = getX();
            y1 = getY()-h;
        }
        else if (getX()<=getX2() && getY2()>=getY()){
            w = getX2()-getX();
            h = getY2()-getY();
            x1 = getX();
            y1 = getY();
        }
        else if (getX()>=getX2() && getY2()>=getY()){
            w = getX()-getX2();
            h = getY2()-getY();
            x1 = getX()-w;
            y1 = getY2()-h;
        }
        else{
            w = getX()-getX2();
            h = getY()-getY2();
            x1 = getX2();
            y1 = getY2();
        }
        setX(x1);
        setY(y1);
        setX2(w);
        setY2(h);
    }
}
