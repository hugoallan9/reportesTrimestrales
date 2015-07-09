/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportestrimestrales;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author INE
 */
public class ReportesTrimestrales {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        MenuPrincipal menu = new MenuPrincipal();
//        menu.setVisible(true);
//        Calendar cal = Calendar.getInstance();
//        System.out.println(new SimpleDateFormat("MMMM").format(cal.getTime()));
        Vitales docu;
        docu= new Vitales("Estadísticas Vitales", "Tercero", "2014","C:/Users/INE/Downloads/CSV_Vitales/CSV_Vitales");
        docu.setRuta("C:/Users/INE/Documents/Vitales3/Vitales/");
        docu.setTex("vitalesTercero2015");
        docu.hacerPortada();
        docu.preambulo();
        docu.iniciarDocumento();
        docu.hacerTitulo();
        docu.juntaDirectiva();
        docu.equipoYPresentacion();
        docu.capitulo1();
        docu.capitulo2();
        docu.capitulo3();
        docu.capitulo4();
        docu.capitulo5();
        docu.terminarDocumento();
        docu.getRr().get().end();
        docu.generarGraficas("presentacion");
        docu.compilar(docu.getRr(),"C:/Users/INE/Documents/Vitales3/vitalesTercero2015.tex","T");
        
//        IPC docu;
//        docu = new IPC("IPC", "Junio", "2015", "C:/Users/INE/Desktop/IPC/CSV_IPC");
//        docu.setRuta("C:/Users/INE/Documents/IPC");
//        docu.setTex("IPC" + docu.getMes());
//        docu.hacerPortada();
//        docu.preambuloAnual();
//        docu.iniciarDocumentoAnual();
//        docu.hacerTituloAnual();
//        docu.juntaDirectivaAnual();
//        docu.equipoYPresentacion();
//        docu.capitulo1();
//        docu.capitulo2();
//        docu.terminarDocumento();
        
    }
    
}
