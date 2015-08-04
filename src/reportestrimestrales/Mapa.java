/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportestrimestrales;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
    
    
    Mapa(String rutaCSV, String rutaSalida){
        this.rutaCSV = new File(rutaCSV);
        this.rutaSalida = new File(rutaSalida);
        descarga();
    }
    
    protected static void descarga(){
        File file = null; 
       URL url = null;
       System.out.println(rutaSalida);
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
            file = new  File(f,"MapaRegiones.dbf");
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
            file = new  File(f,"MapaRegiones.prj");
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
            file = new  File(f,"MapaRegiones.qpj");
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
            file = new  File(f,"MapaRegiones.shp");
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
            file = new  File(f,"MapaRegiones.shx");
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
    
    
    
    
}
