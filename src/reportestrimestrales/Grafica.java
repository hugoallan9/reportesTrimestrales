/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportestrimestrales;

import java.io.File;
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
            r.eval("graficasVitales("+lista+ ",'"+ ruta +"', modalidad = 'trimestral')");
            System.out.println("graficasVitales("+lista+ ",file.path('"+ ruta +"','GraficasPresentacion/'), modalidad = 'presentacion')");
            r.eval("graficasVitales("+lista+ ",file.path('"+ ruta +"','GraficasPresentacion/'), modalidad = 'presentacion')");
            System.out.println("compilar('" + ruta + "', mostrar = F)");
            r.eval("compilar('" + ruta  + "/vitales.tex', mostrar = F)");
            r.eval("compilar('" + ruta + "/vitales.tex', mostrar = F)");
            r.eval("compilar('" + ruta  + "/presentacionVitales.tex', mostrar = F)");
            //r.eval("mandarCorreo(c('<hugoallangm@gmail.com>', '<ccabrera@ine.gob.gt>', '<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Vitales', 'Su reporte lo encontrará adjunto en este correo. ','" + ruta  + "/vitales.pdf"+ "')");
            System.out.println("mandarCorreo(c('<hugoallangm@gmail.com>', '<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Vitales', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "vitales.pdf','" + ruta+ "presentacionVitales.pdf'))");
            System.out.println(r.eval("mandarCorreo(c('<hugoallangm@gmail.com>', '<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Vitales', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "vitales.pdf','" + ruta+ "presentacionVitales.pdf'))"));
        }else if( lista.equalsIgnoreCase("ipc") ){
            System.out.println("IPC");
            //r.eval("sink(file = '/var/www/html/IPC/sync.txt')" );
            System.out.println(r.eval("getListIpc()"));
            System.out.println("Iniciando Gráficas");
            r.eval("capitulo1()");
            r.eval("capitulo2()");
            r.eval("capitulo3()");
            r.eval("capitulo4()");
            r.eval("capitulo5()");
            r.eval("capitulo6()");
            r.eval("capitulo7()");
            r.eval("capitulo8()");
            r.eval("capitulo9()");
            r.eval("capitulo10()");
            System.out.println("compilar('" + ruta + "', mostrar = F)");
            r.eval("compilar('" + ruta + "', mostrar = F)");
            r.eval("compilar('" + ruta + "', mostrar = F)");
            System.out.println(r.eval("mandarCorreo(c('<hugoallangm@gmail.com>', '<rdnarcisoc@gmail.com>'), 'Reporte Finalizado IPC', 'Su reporte lo encontrará adjunto en este correo. ','" + ruta.substring(0, ruta.lastIndexOf(".")) + ".pdf"+ "')")); 
            //r.eval("");
            //r.eval("sink()");
            

            
        }
        else if(lista.equalsIgnoreCase("hospitalarias")){
            /*hospitalarias
                    graficas aqui
            */
            
             r.eval("graficasHospitalarias("+lista+ ",'"+ ruta +"', modalidad = 'trimestral')");
            System.out.println("graficasHospitalarias("+lista+ ",file.path('"+ ruta +"','GraficasPresentacion/'), modalidad = 'presentacion')");
            r.eval("graficasHospitalarias("+lista+ ",file.path('"+ ruta +"','GraficasPresentacion/'), modalidad = 'presentacion')");
            System.out.println("compilar('" + ruta + "', mostrar = F)");
            r.eval("compilar('" + ruta  + "/hospitalarias.tex', mostrar = F)");
            r.eval("compilar('" + ruta + "/hospitalarias.tex', mostrar = F)");
            r.eval("compilar('" + ruta  + "/presentacionHospitalarias.tex', mostrar = F)");
            
            System.out.println(r.eval("mandarCorreo(c('<hugoallangm@gmail.com>'), 'Reporte Finalizado Vitales', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "vitales.pdf','" + ruta+ "presentacionVitales.pdf'))"));
            //System.out.println(r.eval("mandarCorreo(c('<hugoallangm@gmail.com>', '<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Vitales', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "vitales.pdf','" + ruta+ "presentacionVitales.pdf'))"));
            
        }
        
        
        
    }
}
