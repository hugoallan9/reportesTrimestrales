/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportestrimestrales;

import org.rosuda.JRI.Rengine;

/**
 *
 * @author INE
 */
public class SesionR {
    Rengine re;
    
    public SesionR(){ 
        re=new Rengine (new String [] {"--vanilla"}, false, null);
        System.out.println("Rengine created, waiting for R"); 
    }
    
    public Rengine get() {
        return(re);
    }
}
