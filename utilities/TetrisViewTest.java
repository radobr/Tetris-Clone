package myTetris;

// code copied from Simon Lucas
// code copied by Udo Kruschwitz


import utilities.JEasyFrame;
import java.util.Random;


public class TetrisViewTest
{
    public static void main(String[] args)
    {
        // test the view component
        Random r = new Random();
        int w = 10;
        int h = 20;
        int[][] a = new int[w][h];
        for (int i = 0; i < w; i++)
        {
            for (int j = h/2; j < h; j++)
            {
                a[i][j] =
                r.nextInt(TetrisView.colors.length);
            }
        }
        TetrisView tv = new TetrisView(a);
        new JEasyFrame(tv, "Tetris View");
    }
}