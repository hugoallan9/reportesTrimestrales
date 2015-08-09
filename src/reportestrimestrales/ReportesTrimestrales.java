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

    private static String rutaIPC, rutaDestinoCSV, rutaArchivoSubido;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        System.out.println(args[0] + ", " + args[1]);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ReportesTrimestrales.class.getName()).log(Level.SEVERE, null, ex);
//        }
        rutaArchivoSubido = "/var/www/archivos/ipc_csv.csv";
        rutaIPC = "/home/IPC";
        File f = new File(rutaIPC, "CSV");
        if( !f.exists() ){
            System.out.println("La carpeta no existe: " + f.getAbsolutePath());
            f.setReadable(true, false);
    f.setExecutable(true, false);
    f.setWritable(true, false);
    f.mkdir();
        }
        
        File f1 = new File(f, "tablas");
        if( !f1.exists() ){
            System.out.println("La carpeta no existe: " + f1.getAbsolutePath());
            f1.mkdir();
            f.setReadable(true, false);
    f.setExecutable(true, false);
    f.setWritable(true, false);
        }
        rutaDestinoCSV = f.getAbsolutePath();
        
        Consultor.reescribirCSV(rutaArchivoSubido);
        try {
            Conector c = new Conector(rutaArchivoSubido, rutaDestinoCSV, rutaIPC, args[0], args[1]);
        } catch (SQLException ex) {
            Logger.getLogger(ReportesTrimestrales.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        docu = new IPC("IPC", getMesCadena(Integer.parseInt(args[1])), args[0], rutaDestinoCSV);
        docu.setRuta(rutaIPC);
        docu.setTex("IPC" + docu.getMes());
        docu.hacerPortada();
        docu.preambuloAnual();
        docu.iniciarDocumentoAnual();
        docu.hacerTituloAnual();
        //docu.juntaDirectivaAnual();
        docu.equipoYPresentacion();
        docu.capitulo1();
        docu.capitulo2();
        docu.capitulosRegionales();
        if (args[2].equalsIgnoreCase("true")){
            docu.generarGraficas("anual");
        }
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
        System.out.println("Generación de reporte finalizada.");
    }
    
     private static String getMesCadena(int mes){
         if(mes == 1)return "Enero";
         else if(mes == 2)return "Febrero";
         else if(mes == 3)return "Marzo";
         else if(mes == 4)return "Abril";
         else if(mes == 5)return "Mayo";
         else if(mes == 6)return "Junio";
         else if(mes == 7)return "Julio";
         else if(mes == 8)return "Agosto";
         else if(mes == 9)return "Septiembre";
         else if(mes == 10)return "Octubre";
         else if(mes == 11)return "Noviembre";
         else if(mes == 12)return "Diciembre";
         return "";    
     }
    
}
