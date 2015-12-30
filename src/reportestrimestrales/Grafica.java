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
            System.out.println("mandarCorreo(c('<hugoallangm@gmail.com>'), 'Reporte Finalizado Hospitalarias', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "hospitalarias.pdf','" + ruta+ "presentacionHospitalarias.pdf'))");
            System.out.println(r.eval("mandarCorreo(c('<hugoallangm@gmail.com>','<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Hospitalarias', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "hospitalarias.pdf','" + ruta+ "presentacionHospitalarias.pdf'))"));
            //System.out.println(r.eval("mandarCorreo(c('<hugoallangm@gmail.com>', '<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Vitales', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "vitales.pdf','" + ruta+ "presentacionVitales.pdf'))"));
            
        }
        else if(lista.equalsIgnoreCase("faltas")){        
            r.eval("setListJudiciales(faltas)");
            System.out.println("La ruta es: " + ruta);
            r.eval("setPathJudiciales('"+ruta+"')");
            r.eval("graficasJudiciales(modalidad= 'trimestral')");
            r.eval("setPathJudiciales(file.path('"+ ruta +"','GraficasPresentacion/'))");
            r.eval("graficasJudiciales(modalidad='presentacion')");
            System.out.println("compilar('" + ruta + "', mostrar = F)");
            r.eval("compilar('" + ruta  + "/faltasJudiciales.tex', mostrar = F)");
            r.eval("compilar('" + ruta + "/faltasJudiciales.tex', mostrar = F)");
            r.eval("compilar('" + ruta  + "/presentacionFaltas.tex', mostrar = F)");
            //r.eval("mandarCorreo(c('<hugoallangm@gmail.com>', '<ccabrera@ine.gob.gt>', '<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Vitales', 'Su reporte lo encontrará adjunto en este correo. ','" + ruta  + "/vitales.pdf"+ "')");
            System.out.println("mandarCorreo(c('<hugoallangm@gmail.com>'), 'Reporte Finalizado Faltas Judiciales', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "faltas.pdf','" + ruta+ "presentacionFaltas.pdf'))");
            System.out.println(r.eval("mandarCorreo(c('<hugoallangm@gmail.com>','<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Faltas Judiciales', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "faltasJudiciales.pdf','" + ruta+ "presentacionFaltas.pdf'))"));
        }
        else if(lista.equalsIgnoreCase("transporte")){
            System.out.println("Entre a transportes");
            System.out.println("graficasTransporte("+lista+ ",file.path('"+ ruta +"','GraficasPresentacion/'), modalidad = 'presentacion')");
            System.out.println(r.eval("names(transporte)"));
            r.eval("graficasTransportes("+lista+ ",'"+ ruta +"', modalidad = 'trimestral')");
            r.eval("graficasTransportes("+lista+ ",file.path('"+ ruta +"','GraficasPresentacion/'), modalidad = 'presentacion')");
            System.out.println("compilar('" + ruta  + "/tranporte.tex', mostrar = F)");
            r.eval("compilar('" + ruta  + "/transporte.tex', mostrar = F)");
            r.eval("compilar('" + ruta + "/transpsorte.tex', mostrar = F)");
            r.eval("compilar('" + ruta  + "/presentacionTransporte.tex', mostrar = F)");
            //r.eval("mandarCorreo(c('<hugoallangm@gmail.com>', '<ccabrera@ine.gob.gt>', '<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Vitales', 'Su reporte lo encontrará adjunto en este correo. ','" + ruta  + "/vitales.pdf"+ "')");
            System.out.println("mandarCorreo(c('<hugoallangm@gmail.com>','<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Transportes y Servicios', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "transporte.pdf','" + ruta+ "presentacionTransporte.pdf'))");
            System.out.println(r.eval("mandarCorreo(c('<hugoallangm@gmail.com>','<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Transportes y Servicios', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "transporte.pdf','" + ruta+ "presentacionTransporte.pdf'))"));
        }
        else if(lista.equalsIgnoreCase("comercio")){
            r.eval("graficasComercio("+lista+ ",'"+ ruta +"', modalidad = 'trimestral')");
            System.out.println("graficasComercio("+lista+ ",file.path('"+ ruta +"','GraficasPresentacion/'), modalidad = 'presentacion')");
            r.eval("graficasComercio("+lista+ ",file.path('"+ ruta +"','GraficasPresentacion/'), modalidad = 'presentacion')");
            System.out.println("compilar('" + ruta + "', mostrar = F)");
            r.eval("compilar('" + ruta  + "/comercio.tex', mostrar = F)");
            r.eval("compilar('" + ruta + "/comercio.tex', mostrar = F)");
            r.eval("compilar('" + ruta  + "/presentacionComercio.tex', mostrar = F)");
            //r.eval("mandarCorreo(c('<hugoallangm@gmail.com>', '<ccabrera@ine.gob.gt>', '<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Vitales', 'Su reporte lo encontrará adjunto en este correo. ','" + ruta  + "/vitales.pdf"+ "')");
            System.out.println("mandarCorreo(c('<hugoallangm@gmail.com>'), 'Reporte Finalizado Comercio Exterior', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "comercio.pdf','" + ruta+ "presentacionComercio.pdf'))");
            System.out.println(r.eval("mandarCorreo(c('<hugoallangm@gmail.com>','<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Comercio Exterior', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "comercio.pdf','" + ruta+ "presentacionComercio.pdf'))"));
        }
        else if(lista.equalsIgnoreCase("agropecuarias")){
            r.eval("graficasAgropecuarias("+lista+ ",'"+ ruta +"', modalidad = 'trimestral')");
            System.out.println("graficasAgropecuarias("+lista+ ",file.path('"+ ruta +"','GraficasPresentacion/'), modalidad = 'presentacion')");
            r.eval("graficasAgropecuarias("+lista+ ",file.path('"+ ruta +"','GraficasPresentacion/'), modalidad = 'presentacion')");
            System.out.println("compilar('" + ruta + "', mostrar = F)");
            r.eval("compilar('" + ruta  + "/agropecuarias.tex', mostrar = F)");
            r.eval("compilar('" + ruta + "/agropecuarias.tex', mostrar = F)");
            r.eval("compilar('" + ruta  + "/presentacionAgropecuarias.tex', mostrar = F)");
            //r.eval("mandarCorreo(c('<hugoallangm@gmail.com>', '<ccabrera@ine.gob.gt>', '<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Vitales', 'Su reporte lo encontrará adjunto en este correo. ','" + ruta  + "/vitales.pdf"+ "')");
            System.out.println("mandarCorreo(c('<hugoallangm@gmail.com>'), 'Reporte Finalizado Agropecuarias', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "agropecuarias.pdf','" + ruta+ "presentacionAgropecuarias.pdf'))");
            System.out.println(r.eval("mandarCorreo(c('<hugoallangm@gmail.com>','<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Agropecuarias', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "agropecuarias.pdf','" + ruta+ "presentacionAgropecuarias.pdf'))"));
        }
        else if(lista.equalsIgnoreCase("vif")){
            r.eval("graficasVIF("+lista+ ",'"+ ruta +"', modalidad = 'trimestral')");
            System.out.println("graficasVIF("+lista+ ",file.path('"+ ruta +"','GraficasPresentacion/'), modalidad = 'presentacion')");
            r.eval("graficasVIF("+lista+ ",file.path('"+ ruta +"','GraficasPresentacion/'), modalidad = 'presentacion')");
            System.out.println("compilar('" + ruta + "', mostrar = F)");
            r.eval("compilar('" + ruta  + "/vif.tex', mostrar = F)");
            r.eval("compilar('" + ruta + "/vif.tex', mostrar = F)");
            r.eval("compilar('" + ruta  + "/presentacionVif.tex', mostrar = F)");
            //r.eval("mandarCorreo(c('<hugoallangm@gmail.com>', '<ccabrera@ine.gob.gt>', '<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Vitales', 'Su reporte lo encontrará adjunto en este correo. ','" + ruta  + "/vitales.pdf"+ "')");
            System.out.println("mandarCorreo(c('<hugoallangm@gmail.com>'), 'Reporte Finalizado VIF', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "vif.pdf','" + ruta+ "presentacionVif.pdf'))");
            System.out.println(r.eval("mandarCorreo(c('<hugoallangm@gmail.com>','<rdnarcisoc@gmail.com>'), 'Reporte Finalizado VIF', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "vif.pdf','" + ruta+ "presentacionVif.pdf'))"));
        }
        else if(lista.equalsIgnoreCase("transito")){
            r.eval("graficasTransito("+lista+ ",'"+ ruta +"', modalidad = 'trimestral')");
            System.out.println("graficasTransito("+lista+ ",file.path('"+ ruta +"','GraficasPresentacion/'), modalidad = 'presentacion')");
            r.eval("graficasTransito("+lista+ ",file.path('"+ ruta +"','GraficasPresentacion/'), modalidad = 'presentacion')");
            System.out.println("compilar('" + ruta + "', mostrar = F)");
            r.eval("compilar('" + ruta  + "/transito.tex', mostrar = F)");
            r.eval("compilar('" + ruta + "/transito.tex', mostrar = F)");
            r.eval("compilar('" + ruta  + "/presentacionTransito.tex', mostrar = F)");
            //r.eval("mandarCorreo(c('<hugoallangm@gmail.com>', '<ccabrera@ine.gob.gt>', '<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Vitales', 'Su reporte lo encontrará adjunto en este correo. ','" + ruta  + "/vitales.pdf"+ "')");
            System.out.println("mandarCorreo(c('<hugoallangm@gmail.com>'), 'Reporte Finalizado Eventos de Tránsito', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "transito.pdf','" + ruta+ "presentacionTransito.pdf'))");
            System.out.println(r.eval("mandarCorreo(c('<hugoallangm@gmail.com>','<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Eventos de Tránsito', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "transito.pdf','" + ruta+ "presentacionTransito.pdf'))"));
        }
        else if(lista.equalsIgnoreCase("delictivos")){
            r.eval("graficasDelictivos("+lista+ ",'"+ ruta +"', modalidad = 'trimestral')");
            System.out.println("graficasDelictivos("+lista+ ",file.path('"+ ruta +"','GraficasPresentacion/'), modalidad = 'presentacion')");
            r.eval("graficasDelictivos("+lista+ ",file.path('"+ ruta +"','GraficasPresentacion/'), modalidad = 'presentacion')");
            System.out.println("compilar('" + ruta + "', mostrar = F)");
            r.eval("compilar('" + ruta  + "/delictivos.tex', mostrar = F)");
            r.eval("compilar('" + ruta + "/delictivos.tex', mostrar = F)");
            r.eval("compilar('" + ruta  + "/presentacionDelictivos.tex', mostrar = F)");
            //r.eval("mandarCorreo(c('<hugoallangm@gmail.com>', '<ccabrera@ine.gob.gt>', '<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Vitales', 'Su reporte lo encontrará adjunto en este correo. ','" + ruta  + "/vitales.pdf"+ "')");
            System.out.println("mandarCorreo(c('<hugoallangm@gmail.com>'), 'Reporte Finalizado Hechos Delictivos', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "delictivos.pdf','" + ruta+ "presentacionDelictivos.pdf'))");
            System.out.println(r.eval("mandarCorreo(c('<hugoallangm@gmail.com>','<rdnarcisoc@gmail.com>'), 'Reporte Finalizado Hechos Delictivos', 'Su reporte lo encontrará adjunto en este correo. ',c('" + ruta  + "delictivos.pdf','" + ruta+ "presentacionDelictivos.pdf'))"));
        }
        
    }
}
