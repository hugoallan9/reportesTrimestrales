/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportestrimestrales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author hugo
 */
public class Mapa {
    File rutaCSV;
    static File rutaSalida;
    static Boolean bajada;
    
    
   public Mapa(String rutaCSV, String rutaSalida){
        this.rutaCSV = new File(rutaCSV);
        this.rutaSalida = new File(rutaSalida);
    }
    
    public void hacerRegional(String csv){
        try {
            File  f = new File(rutaSalida,"mapaRegional.py");
            File f1 = new File(rutaSalida, "mapasRegionales");
            f1 = new File(f1, "datos.tex");
            File CSV =  new File(rutaCSV,csv);
            /*
            Los parámetros son 1. Ruta para el csv
            2. Ruta de los shapes para los mapas
            3. Ruta del tex de los datos para etiquetas
            4. La ruta de salida para el pdf
            */
            System.out.println("python2.7 " + f.getAbsolutePath() + " " + CSV.getAbsolutePath() + " " + 
                    rutaSalida + " " + f1 + " " + rutaSalida);
            Process p = Runtime.getRuntime().exec("python2.7 '" + f.getAbsolutePath() + "' " + CSV.getAbsolutePath() + " " + 
                    rutaSalida + " " + f1 + " " + rutaSalida + " > /home/hugo/log.txt");
            BufferedReader in = new BufferedReader(
                                new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void descarga(){
        File file = null; 
       URL url = null;
       System.out.println(rutaSalida);
        
       try {
            if( rutaSalida.exists() == false){
                rutaSalida.mkdir();
            }
            file = new  File(rutaSalida,"mapaRegional.py");
            url = new URL("http://www.ine.gob.gt/ftparchivos/Scripts/mapaRegional.py");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
       
       try {
            File f = new File(rutaSalida, "mapasRegionales");
            if( f.exists() == false){
                f.mkdir();
            }
            file = new  File(f,"mapaAnotado.tex");
            url = new URL("http://www.ine.gob.gt/ftparchivos/mapasRegionales/mapaAnotado.tex");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            File f = new  File(rutaSalida,"MapaRegiones");
            if( f.exists() == false){
                f.mkdir();
            }
            file = new  File(f,"mapaRegiones.dbf");
            url = new URL("http://www.ine.gob.gt/ftparchivos/pruebasMapas/MapaRegiones/mapaRegiones.dbf");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        try {
            File f = new  File(rutaSalida,"MapaRegiones");
            if( f.exists() == false){
                f.mkdir();
            }
            file = new  File(f,"mapaRegiones.prj");
            url = new URL("http://www.ine.gob.gt/ftparchivos/pruebasMapas/MapaRegiones/mapaRegiones.prj");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        try {
            File f = new  File(rutaSalida,"MapaRegiones");
            if( f.exists() == false){
                f.mkdir();
            }
            file = new  File(f,"mapaRegiones.qpj");
            url = new URL("http://www.ine.gob.gt/ftparchivos/pruebasMapas/MapaRegiones/mapaRegiones.qpj");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            File f = new  File(rutaSalida,"MapaRegiones");
            if( f.exists() == false){
                f.mkdir();
            }
            file = new  File(f,"mapaRegiones.shp");
            url = new URL("http://www.ine.gob.gt/ftparchivos/pruebasMapas/MapaRegiones/mapaRegiones.shp");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            File f = new  File(rutaSalida,"MapaRegiones");
            if( f.exists() == false){
                f.mkdir();
            }
            file = new  File(f,"mapaRegiones.shx");
            System.out.println(f.getAbsolutePath());
            url = new URL("http://www.ine.gob.gt/ftparchivos/pruebasMapas/MapaRegiones/mapaRegiones.shx");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    
    public void mapasIPC(){
        List mapas = new ArrayList();
        mapas.add("2_04.csv");
        mapas.add("2_22.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerRegional((String) iterator.next());
        }
        
    }
    
    
}
