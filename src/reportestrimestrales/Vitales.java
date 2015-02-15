/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportestrimestrales;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RList;
import org.rosuda.JRI.RVector;

/**
 *
 * @author INE
 */
public class Vitales extends Documento{
    private List capitulos;
    private List introCapitulos;

    public SesionR getRr() {
        return rr;
    }
    private SesionR rr;
    private String rutaCSV;
    
    
    public Vitales(String titulo, String trimestre, String pYear, String rutaCSV) {
        super(titulo, trimestre, pYear);
        capitulos = new ArrayList();
        introCapitulos = new ArrayList();
        this.rutaCSV = rutaCSV;
        rr = new SesionR();
        cargarCSV(rutaCSV);
        setCapitulos();
        setIntroCapitulos();
    }
    
    protected void setCapitulos(){
        capitulos.add("Nacimientos");
        capitulos.add("Defunciones");
        capitulos.add("Defunciones Fetales");
        capitulos.add("Matrimonios");
        capitulos.add("Divorcios");
        capitulos.add("CUADROS ESTADÍSTICOS");
        capitulos.add("TABLAS DE VARIACIONES");
    }
    
    protected void setIntroCapitulos(){
        introCapitulos.add("Es la expulsión completa del cuerpo de la madre, "
                + "independiente de la duración del embarazo, de un producto de "
                + "la concepción que, después de dicha separación, respire o dé "
                + "cualquier otra señal de vida, como latidos del corazón, "
                + "pulsaciones del cordón umbilical o movimientos efectivos de "
                + "los músculos de contracción voluntaria.");
        introCapitulos.add("");
    }
    
    protected void cargarCSV(String ruta){
        if (!rr.get().waitForR())
            {
                System.err.println("No se pudo establecer  conexión con R ");
            }else {
                rr.get().eval("library(funcionesINE)");
                REXP listadoCSV = rr.get().eval("vitales <- cargaMasiva('" +  ruta +"')");
                REXP nombres = rr.get().eval("names(vitales)");
                System.out.println(listadoCSV);
                System.out.println(nombres);
            }
    }
    
    protected void capitulo1(){
        //CARGA DE SESION DE R
        if (!rr.get().waitForR())
            {
                System.err.println("No se pudo establecer  conexión con R ");
            }

        //INTRODUCCION
        escribirCapitulo(capitulos.get(0).toString(), capitulos.get(0).toString()
                ," ", introCapitulos.get(0).toString());
        
        //PRIMERA HOJA
        String columna1 = columna("Nacimientos","Los nacimientos constituyen la variable en el "
                + "estudio de los fenómenos demográficos que, utilizado "
                + "conjuntamente con la estadística de defunciones y matrimonios"
                + ", ayuda a determinar la estructura y dinámica de población, "
                + "con la incorporación de individuos a la población.", "Como se"
                        + " muestra en gráfica de la serie histórica de nacimientos,"
                        + " durante el " + 
                        simboloToTrimestre(rr.get().eval("vitales$'1_01'$x[1]").asString().substring(0,2)) +
                        " trimestre del " + getAnioPublicacion()+  " se registraron "
                        +  getDf().format(rr.get().eval("vitales$'1_01'$y[1]").asDouble())  +", "
                        + Math.round(rr.get().eval("cambioInterAnual(vitales$'1_01')").asDouble())
                        +"\\% menos que lo registrado en el mismo período "
                        + "del año anterior y "+ Math.round(rr.get().eval("cambioInterAnual(vitales$'1_01', paso = 1)").asDouble())
                        + "\\% menos que lo registrado en el último "
                        + "trimestre del " + String.valueOf((int)Double.parseDouble(getAnioPublicacion())-1)+"." ,
                "Nacimientos por trimestre", 
                "Serie histórica " + rr.get().eval("vitales$'1_01'$x[1]").asString().substring(3) + "-" +rr.get().eval("vitales$'1_01'$x[9]").asString().substring(3)
                , "","INE, con datos del RENAP", "");
               
        escribirLinea(hojaTrimestral(columna1, ""));
    }
    
    
}
