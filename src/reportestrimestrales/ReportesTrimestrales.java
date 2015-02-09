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
        Documento docu;
        docu= new Documento("Doc de prueba", "trimestre");
        System.out.println(docu.getTitulo());
        docu.juntaDirectiva();
    }
    
}
