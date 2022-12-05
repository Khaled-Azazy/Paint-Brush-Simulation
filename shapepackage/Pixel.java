package shapepackage;

public class Pixel {
    int x;
    int y;
    int width;
    int height;

    public Pixel(int a, int b, int w, int h){
        x = a;
        y = b;
        width = w;
        height = h;
    }
    public Pixel(int a, int b){
        x = a;
        y = b;
        width = 3;
        height = 3;
    }
    public Pixel(){
        width = 3;
        height = 3;
    }

}
