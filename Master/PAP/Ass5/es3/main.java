/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luca
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int num_threads = 10;
        Player[] p = new Player[num_threads]; //10 giocatori
        Sync s = new Sync(num_threads);
        Flag f = new Flag();
        Arbiter a = new Arbiter(f);
        
        for(int i = 0; i < num_threads; i++)
        {
            p[i] = new Player(f, s, i + 1);
            p[i].start();
        }

        a.start();
        
        
        
    }
    
}
