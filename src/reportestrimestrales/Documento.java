/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author INE
 */
package reportestrimestrales;

import java.io.IOException;
import java.util.Calendar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Documento {


    private String titulo;
    private String mes;
    private String year;
    private String trimestre;

    
    public  Documento(String titulo, String trimestre){
        this.titulo = titulo;
        this.trimestre = trimestre;
        Calendar cal = Calendar.getInstance();
        mes = new SimpleDateFormat("MMMM").format(cal.getTime());
        year = new SimpleDateFormat("yyyy").format(cal.getTime());
    }
    
    

    public String getTitulo() {
        return titulo;
    }

    public String getMes() {
        return mes;
    }

    public String getYear() {
        return year;
    }

    public String getTrimestre() {
        return trimestre;
    }
    
    protected void juntaDirectiva(){
        try {
            Document doc = Jsoup.connect("http://www.ine.gob.gt/index.php/institucion/organizacion").get();
            Elements tables =  doc.select("tbody");
//            for( Element table : tables ){
//                System.out.println(table.getElementById("br"));
//                Elements trs = table.select("tr");
//                String[][] trtd = new String[trs.size()][];
//                String[][] trtdbr = new String[trs.size()][];
//                for (int i = 0; i < trs.size(); i++) {
//                     Elements tds = trs.get(i).select("td");
//                     trtd[i] = new String[tds.size()];
//                     //System.out.println(trs.get(i).text());
//                     for (int j = 0; j < 1; j++) {                        
//                        trtd[i][j] = tds.get(j).text(); 
//                        //System.out.println(tds.get(j).text());
//                    }
//                }
//            }
            Element juntaDirectiva = tables.get(0);
            Elements miembros = juntaDirectiva.select("tr");
            for( int i = 0 ; i < miembros.size(); i++ ){
                System.out.println(miembros.get(i).text());
            }
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    
    }   
}
