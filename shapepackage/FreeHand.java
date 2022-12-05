package shapepackage;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
public class FreeHand extends Shape{
    ArrayList<Pixel> pixels;
 
    public FreeHand(Color c){
        pixels = new ArrayList<Pixel>();
        setColor(c);
    }
    public FreeHand(){
        pixels = new ArrayList<Pixel>();
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        for (Pixel p : pixels){
            g.fillRect(p.x, p.y, p.width, p.height);
        }
    }
    public void add(Pixel p) {
        pixels.add(p);
    }
    public FreeHand drawfirstPixel(int x, int y, Color c, Pixel p){
        setColor(c);
        pixels = new ArrayList<Pixel>();
        this.add(p);
        return this;
    }
    // to draw just the current free line
    public FreeHand drawPixel(int x, int y, Pixel p){
        this.add(p);
        return this;
    }
}
