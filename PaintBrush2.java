import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;
import java.awt.Color;
import shapepackage.*;



public class PaintBrush2 extends Applet{
    private static final int LINE = 1;
    private static final int OVAL = 2;
    private static final int RECT = 3;
    private static final int FREEHAND = 4;
    private static final int ERASER = 5;
    private String [] names; // the names of all the buttons on the app
    private ArrayList<Button> windowButtons; // list of all the buttons on the app
    private Checkbox ch; // to get the filled shapes
    private int currentShapeNum; // get the current shape number to know the shape to be drawn
    private Shape currentShape; // what is the current shape being drawn default is line
    private Color currentColor; // the current color for the applet app default is black
    private int x, y, x2, y2; // all of the coordinates of the mouse actions
    private boolean filled; // to know if the drawn shape is solid or not default is not filled
    private ArrayList<Shape> shapes; // keep track of all the shapes drawn in the applet app
    private boolean cleared; // to know if window is cleared or not
    private FreeHand f; // tmp object to saving the curerrnt freeline before drawing it
    private Stack<Shape> redoStack; // stack to save all the undo operation to redo in LIFO manner 
    Pixel p; // the pixel to be drawn for the freehand or the eraser
    public void init(){
        initializeData();
        initializeNames();
        initializeButtons();
        initializeMouseActions();
    }
    // intialize default values for all attributes 
    public void initializeData() {
        filled = false;
        x = y = x2 = y2 = 0;
        currentColor = Color.BLACK;
        currentShape = new Line(0, 0, 0, 0, currentColor, filled); // deafult shape is line 
        currentShapeNum = 1;
        shapes = new ArrayList<Shape>();
        redoStack = new Stack<Shape>();
        //f = new FreeHand();
        cleared = false;
        ch = new Checkbox("Filled");
    }
    // intilize button names
    public void initializeNames(){
        names = new String[]{"Line", "Oval", "Rectangle", "Free line", "Red", "Green", "Blue", "Eraser", "Clear", "Undo", "redo"};
        windowButtons = new ArrayList<Button>();
        for (int i = 0; i < names.length; ++i){
            windowButtons.add(new Button(names[i]));
            add(windowButtons.get(i)); // adding the components to the applet
        }
        add(ch); // adding the checkbox
    }
    // linke the mouse listener to the applet app
    public void initializeMouseActions(){
        this.addMouseListener(new MouseListener());
        this.addMouseMotionListener(new MouseListener());
    }
    // linke the Buttons listener to the applet app
    public void initializeButtons(){
        windowButtons.get(0).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                currentShapeNum = LINE;}});
        windowButtons.get(1).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                currentShapeNum = OVAL;}});
        windowButtons.get(2).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                currentShapeNum = RECT;}});
        windowButtons.get(3).addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        currentShapeNum = FREEHAND;}});
        windowButtons.get(4).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                currentColor = Color.RED;}});
        windowButtons.get(5).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                currentColor = Color.GREEN;}});
        windowButtons.get(6).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                currentColor = Color.BLUE;}});
        windowButtons.get(7).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                currentShapeNum = ERASER;
            }});
        windowButtons.get(8).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //print how many shape in the applet
                System.out.println(shapes.size());
                shapes.clear();
                cleared = true;
                repaint();
            }});
        windowButtons.get(9).addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    undo();}});
        windowButtons.get(10).addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    redo();}});
    }
    
    // draw the current shape with the current color
    public void paint(Graphics g){
        // draw all t he shapes first
        for (Shape s : shapes){
            g.setColor(s.getColor());
            s.draw(g);
        }
        g.setColor(currentColor); // set the current color for the current shape
        if (!cleared) {  // if the screen not clread draw the current shape other make it non cleared
            currentShape.draw(g);
        }
        else cleared=false;
        
    }
    // mouse Listener for pressed, dragged and released
    class MouseListener extends MouseAdapter{
        public void mousePressed(MouseEvent e){
            x = e.getX();
            y = e.getY();
            filled = checkBoxState(); // get the sate of the ckeckbox
            currentShape = getCurrentShape(currentShapeNum); // get the current shape
            // if the shape is freehand start drawing it
            if (currentShape instanceof FreeHand && currentShapeNum==FREEHAND){
                f = new FreeHand();  // new freehand object
                p = new Pixel(x,y); // create new pixel
                currentShape = f.drawfirstPixel(x,y,currentColor, p); // start draw the first pixel
                repaint();
            }
            // if the shape is eraser start drawing it
            else if (currentShape instanceof FreeHand && currentShapeNum==ERASER){
                p = new Pixel(x,y,20,20);
                f = new FreeHand();
                currentShape = f.drawfirstPixel(x,y,Color.WHITE,p);
                repaint();
            }
        }
        public void mouseDragged(MouseEvent e){
            x2 = e.getX();
            y2 = e.getY();
            // if the shape is freehand continue drawing it
            if (currentShape instanceof FreeHand && currentShapeNum==FREEHAND){
                p = new Pixel(x2,y2);
                currentShape = f.drawPixel(x2,y2, p); // continue drawing the first pixel
                repaint();
            }
            // if the shape is eraser continue drawing it
            else if (currentShape instanceof FreeHand && currentShapeNum==ERASER){
                p = new Pixel(x2,y2,20,20);
                currentShape = f.drawPixel(x2,y2, p);
                repaint();
            }
            //  if other shape draw the current shape from the start
            else {
                currentShape = getCurrentShape(currentShapeNum);
                repaint();
            }
        }
        public void mouseReleased(MouseEvent e){
            // if the shape was dragged save it and reset the cooridantes to zeros
            if (x2 != 0 && y2 != 0){
                shapes.add(currentShape);
                reset();
            }
        }
        
    }
    // get the current shape based on the current shape value
    private Shape getCurrentShape(int n){
        Shape s;
        switch (n) {
            case 1:
                s =  new Line(x, y, x2, y2, currentColor, filled);
                break;
            case 2:
                s = new Oval(x, y, x2, y2, currentColor, filled);
                break;
            case 3:
                s = new Rect(x, y, x2, y2, currentColor, filled);
                break;
            case 4:
                s = new FreeHand(currentColor);
                break;
            case 5:
                s = new FreeHand(Color.WHITE);
                break;
            default: // default shape is line 
                s = new Line();
        }
        return s;
    }
    // get the state of the check box
    private boolean checkBoxState() {
        boolean state = false;
        if (ch.getState())
            state = ch.getState();
        return state;
    }
    // reseting all the coordiantes values 
    public void reset() {
        x = y = x2 = y2 = 0;
    }
    // udno the last drawing operation
    private void undo() {
        if (shapes.size() > 0) { // if there is shapes in the array
            redoStack.add(shapes.get(shapes.size()-1));
            shapes.remove(shapes.size()-1); // remove last one
            reset(); // rest the values
            currentShape = getCurrentShape(currentShapeNum); // get the cerrent shape with zeros values
            repaint(); // draw again
        }
    }
    // redo the last drawing operation
    private void redo() {
        if (!redoStack.empty()){
            shapes.add(redoStack.pop());
            repaint();
        }
    }
}



