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
        Calendar cal = Calendar.getInstance();
        System.out.println(new SimpleDateFormat("MMMM").format(cal.getTime()));
        Vitales docu;
        docu= new Vitales("Mi primer documento", "Primero", "2012","C:/Users/INE/Documents/Graficador-Nuevo2/dataVT1");
        docu.setRuta("C:\\Users\\INE\\Documents\\Salidas");
        docu.setTex("primerDocumento");
        docu.preambulo();
        docu.iniciarDocumento();
        docu.hacerTitulo();
        docu.juntaDirectiva();
        docu.capitulo1();
        docu.terminarDocumento();
        docu.getRr().get().end();
    }
    
}
