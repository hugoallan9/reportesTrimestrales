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
    
    
    Mapa(String rutaCSV){
        this.rutaCSV = new File(rutaCSV);
        descarga();
    }
    
    protected static void descarga(){
        File file = null; 
       URL url = null;
        try {
            File f = new File(rutaSalida, "mapasRegionales");
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
    }
    
}
