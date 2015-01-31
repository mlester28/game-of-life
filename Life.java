//mlester: this is the "game of life"

import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Life
{

    //fields
    private boolean[][] matrix;
    private JButton[][] board;
    private JPanel panel;

    //default constructor
    public Life(){
	panel = new JPanel();
        panel.setLayout(new GridLayout(40,40));
	matrix = new boolean[40][40];
	Random r = new Random();
	for (int i=0; i<40; i++){
	    for (int j=0; j<40; j++){
		if (r.nextInt(10)>7){
		    matrix[i][j]=true;
		}
		else
		    matrix[i][j]=false;	
	    }
	}

	board = new JButton[40][40];
	for (int i=0; i<40; i++){
	    for (int j=0; j<40; j++){
		board[i][j] = new JButton();
		panel.add(board[i][j]);
	    }
	}
	render();
	
    }

    //method to help the board wrap around
    public static int mod(int x, int d){
	if ((x%d) < 0)
	    return 40+(x%d);
	else
	    return x%d;
    }

    //method to count neighbors
    public int neighborCounter(int i, int j){
	int counter = 0;
	if (matrix[mod(i-1,40)][mod(j-1,40)])
	    counter++;
	if (matrix[mod(i-1,40)][j])
	    counter++;
	if (matrix[mod(i-1,40)][mod(j+1,40)])
	    counter++;
	if (matrix[i][mod(j-1,40)])
	    counter++;
	if (matrix[i][mod(j+1,40)])
	    counter++;
	if (matrix[mod(i+1,40)][mod(j-1,40)])
	    counter++;
	if (matrix[mod(i+1,40)][j])
	    counter++;
	if (matrix[mod(i+1,40)][mod(j+1,40)])
	    counter++;
	return counter;
    }

    //method creates matrix of next generation
    public boolean[][] next() {
	boolean[][] newMatrix = new boolean[40][40];
	for (int i=0; i<40; i++){
	    for (int j=0; j<40; j++){
		int n = neighborCounter(i, j);
		if (n<2)
		    newMatrix[i][j] = false;
		else if (n==2)
		    newMatrix[i][j] = matrix[i][j];
		else if (n==3)
		    newMatrix[i][j] = true;
		else if (n>3)
		    newMatrix[i][j] = false;
	    }
	}
	matrix = newMatrix;
	return matrix;
    }

    /*method that maps the true/false values of matrix to blue/white state 
      of buttons in board*/
    public void render() { 
	for (int i=0; i<40; i++){
	    for (int j=0; j<40; j++){
		if (matrix[i][j]){
		    board[i][j].setOpaque(true);
		    board[i][j].setBorderPainted(false);
		    board[i][j].setBackground(Color.BLUE);
		}
		else{
		    board[i][j].setOpaque(true);
		    board[i][j].setBorderPainted(false);
		    board[i][j].setBackground(Color.WHITE);
		}
	    }
	}

    }



    public static void main(String [] args) {
	
	//creates life model object from default constructor
	final Life lm = new Life();
	
	//when clicked the truth value of the cell will switch
	class ClickListener implements ActionListener{
	    int x,y;

	    public ClickListener(int xval, int yval){
		x=xval;
		y=yval;
	    }

	    public void actionPerformed(ActionEvent event){
		lm.matrix[x][y] = !lm.matrix[x][y];
	    }
	}

	for (int i=0; i<40; i++){
	    for (int j=0; j<40; j++){
		lm.board[i][j].addActionListener(new ClickListener(i,j));
	    }
	}

	//creates each next board with the new pattern
	class LifeListener implements ActionListener{
	    public void actionPerformed(ActionEvent event){
		lm.next();
		lm.render();
	    }
	}

	//runs every 100 milliseconds
	LifeListener lListener = new LifeListener();
	Timer t = new Timer(100, lListener);
	t.start();


	//creates a JFrame
	JFrame frame = new JFrame();
	frame.setSize(600,600);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 	frame.setTitle("The Game of Life");

	frame.add(lm.panel);
	frame.setVisible(true);
    }

}
