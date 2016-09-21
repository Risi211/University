/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass03;

/**
 *
 * @author luca
 */
public class BBox {
    
    private P2d p1;
    private P2d p4;
    
    public BBox(P2d p1, P2d p4)
    {
        this.p1 = p1;
        this.p4 = p4;
    }
    
    public P2d getP1()
    {
        return p1;
    }
    
    public P2d getP4()
    {
        return p4;
    }
    
    public static BBox mergeBBox(BBox b1, BBox b2)
    {
        P2d pp1;
        P2d pp4;
        //guardo quale punto mettere come BBox in alto
        if(b1.getP1().getX() <= b2.getP1().getX()) //la prima x del primo bbox è <= della prima x del secondo bbox
        {
            if(b1.getP1().getY() <= b2.getP1().getY()) //la prima y del primo bbox è <= della prima y del secondo bbox
            {
                pp1 = new P2d(b1.getP1().getX(), b1.getP1().getY());
            }
            else //la prima y del primo bbox è > della prima y del secondo bbox
            {
                pp1 = new P2d(b1.getP1().getX(), b2.getP1().getY());
            }
        }
        else //la prima x del primo bbox è > della prima x del secondo bbox
        {
            if(b1.getP1().getY() <= b2.getP1().getY()) //la prima y del primo bbox è <= della prima y del secondo bbox
            {
               pp1 = new P2d(b2.getP1().getX(), b1.getP1().getY());
            }
            else //la prima y del primo bbox è > della prima y del secondo bbox
            {
                pp1 = new P2d(b2.getP1().getX(), b2.getP1().getY());
            }            
        }

        //guardo quale punto mettere come BBox in basso
        if(b1.getP4().getX() >= b2.getP4().getX()) //la seconda x del primo bbox è >= della seconda x del secondo bbox
        {
            if(b1.getP4().getY() >= b2.getP4().getY()) //la seconda y del primo bbox è >= della seconda y del secondo bbox
            {
                pp4 = new P2d(b1.getP4().getX(), b1.getP4().getY());
            }
            else //la prima y del primo bbox è < della prima y del secondo bbox
            {
                pp4 = new P2d(b1.getP4().getX(), b2.getP4().getY());
            }
        }
        else //la seconda x del primo bbox è < della seconda x del secondo bbox
        {
            if(b1.getP4().getY() >= b2.getP4().getY()) //la seconda y del primo bbox è >= della seconda y del secondo bbox
            {
                pp4 = new P2d(b2.getP4().getX(), b1.getP4().getY());
            }
            else //la prima y del primo bbox è < della prima y del secondo bbox
            {
                pp4 = new P2d(b2.getP4().getX(), b2.getP4().getY());
            }
        }
        
        return new BBox(pp1, pp4);
    }
    
}
