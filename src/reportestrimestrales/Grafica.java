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
    private String modalidad;
    
    public Grafica(String lista, String ruta, Rengine r, String modalidad){
        super();
        this.lista = lista;
        this.r = r;
        this.ruta = ruta;
        this.modalidad = modalidad;
    }
    
    public void run(){
        System.out.println("HILO: " + ruta);
        if(lista.equalsIgnoreCase("vitales")){
            r.eval("graficasVitales("+lista+ ",'"+ ruta +"', modalidad = '"+ modalidad +"')");    
        }else if( lista.equalsIgnoreCase("ipc") ){
            System.out.println("IPC");
            System.out.println("sink(file = '" + r.eval("getPath()").asString() + "log.txt'");
            r.eval("sink(file = '/var/www/html/IPC/sync.txt')" );
            r.eval("library(tikzDevice)");
            System.out.println(r.eval("getListIpc()"));
            System.out.println(r.eval("lsf.str(asNamespace('funcionesINE'))[14]"));
            System.out.println(r.eval("tempdir()"));
            r.eval("capitulo2()");
            r.eval("capitulo3()");
            r.eval("capitulo4()");
            r.eval("capitulo5()");
            r.eval("capitulo6()");
            r.eval("capitulo7()");
            r.eval("capitulo8()");
            r.eval("capitulo9()");
            r.eval("capitulo10()");
            r.eval("sink()");
            

            
        }
        
    }
}
