/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lowerbound;

/**
 *
 * @author luca
 */
public class LowerBoundSolution {
    
    Vertex[] solution;
    int[] cammino; //contiene i vertici dall'arrivo al primo raggiunto dalla partenza
    double[] costo_archi; //costo ad ogni cardinalità k (da n a 1), preso dagli archi direttamente
    double[] costo_stati; //costo ad ogni cardinalità k (da n a 1), preso dagli stati nPath
    boolean isOptimal;
    double lowerBoundValue_archi;
    double lowerBoundValue_stati;
    Stati_nPath[] stati_lb; //stati generati nel calcolo del lower bound
    double[] lambda; //contiene le penalità lagrangiane ottime calcolate nel lower bound
    
}
