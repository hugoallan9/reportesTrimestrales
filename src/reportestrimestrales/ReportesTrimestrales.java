/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportestrimestrales;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import consultor.*;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author INE
 */
public class ReportesTrimestrales {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        File f = new File("C:\\Users\\INE\\Documents\\IPC\\CSV");
//        if( !f.exists() ){
//            System.out.println("La carpeta no existe: " + f.getAbsolutePath());
//            f.mkdir();
//        }
//        
//        File f1 = new File("C:\\Users\\INE\\Documents\\IPC\\CSV\\tablas");
//        if( !f1.exists() ){
//            System.out.println("La carpeta no existe: " + f1.getAbsolutePath());
//            f1.mkdir();
//        }
//        try {
//            Conector c = new Conector("C:\\Users\\INE\\Downloads\\marzo.csv","C:\\Users\\INE\\Documents\\IPC\\CSV", "C:\\Users\\INE\\Documents\\IPC");
//        } catch (SQLException ex) {
//            Logger.getLogger(ReportesTrimestrales.class.getName()).log(Level.SEVERE, null, ex);
//        }
            // TODO code application logic here
//        MenuPrincipal menu = new MenuPrincipal();
//        menu.setVisible(true);
//        Calendar cal = Calendar.getInstance();
//        System.out.println(new SimpleDateFormat("MMMM").format(cal.getTime()));
//        Vitales docu;
//        docu= new Vitales("Estadísticas Vitales", "Tercero", "2014","C:/Users/INE/Downloads/CSV_Vitales/CSV_Vitales");
//        docu.setRuta("C:/Users/INE/Documents/Vitales3/Vitales/");
//        docu.setTex("vitalesTercero2015");
//        docu.hacerPortada();
//        docu.preambulo();
//        docu.iniciarDocumento();
//        docu.hacerTitulo();
//        docu.juntaDirectiva();
//        docu.equipoYPresentacion();
//        docu.capitulo1();
//        docu.capitulo2();
//        docu.capitulo3();
//        docu.capitulo4();
//        docu.capitulo5();
//        docu.terminarDocumento();
//        docu.getRr().get().end();
//        docu.generarGraficas("presentacion");
//        docu.compilar(docu.getRr(),"C:/Users/INE/Documents/Vitales3/vitalesTercero2015.tex","T");
        
        IPC docu;
        docu = new IPC("IPC", "Junio", "2015", "C:/Users/INE/Documents/IPC/CSV/");
        docu.setRuta("C:/Users/INE/Documents/IPC/");
        docu.setTex("IPC" + docu.getMes());
        docu.hacerPortada();
        docu.preambuloAnual();
        docu.iniciarDocumentoAnual();
        docu.hacerTituloAnual();
        docu.juntaDirectivaAnual();
        docu.equipoYPresentacion();
        docu.capitulo1();
        docu.capitulo2();
       // docu.generarGraficas("anual");
        docu.terminarDocumento();
        
        
//        IPC docu;
//        docu = new IPC(args[0],args[1], args[2], args[3]);
//        docu.setRuta(args[4]);
//        docu.setTex("IPC" + docu.getMes());
//        docu.hacerPortada();
//        docu.preambuloAnual();
//        docu.iniciarDocumentoAnual();
//        docu.hacerTituloAnual();
//        docu.juntaDirectivaAnual();
//        docu.equipoYPresentacion();
//        docu.capitulo1();
//        docu.capitulo2();
//        //docu.generarGraficas("anual");
//        docu.terminarDocumento();
//        
    }
    
}
