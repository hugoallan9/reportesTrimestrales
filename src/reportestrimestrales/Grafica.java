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
public class Grafica extends Thread {
    private String lista;
    private Rengine r;
    private String ruta;
    
    public Grafica(String lista, String ruta, Rengine r){
        super();
        this.lista = lista;
        this.r = r;
        this.ruta = ruta;
    }
    
    public void run(){
        System.out.println("HILO: " + ruta);
        r.eval("graficasVitales("+lista+ ",'"+ ruta +"/')");
//        for(int i = 0 ; i <1000000; i++){
//            System.out.println("Hola mundo en un hilo");
//        }
    }
}
