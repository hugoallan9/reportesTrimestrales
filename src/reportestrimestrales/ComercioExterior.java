/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportestrimestrales;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.rosuda.JRI.REXP;

/**
 *
 * @author INE
 */
public class ComercioExterior extends Documento{
    private List capitulos;
    private List introCapitulos;
    private List contenidos;
    private String formatoSerie;
    Collator comparador = Collator.getInstance();
    private SesionR rr;
    private String rutaCSV;    
    public SesionR getRr() {
        return rr;
    }
    
    
    
    
    public ComercioExterior(String titulo, String trimestre, String pYear, String rutaCSV) {
        super(titulo, trimestre, pYear);
        System.out.println("Entrando a comercio");
        capitulos = new ArrayList();
        introCapitulos = new ArrayList();
        contenidos = new ArrayList();
        this.rutaCSV = rutaCSV;
        rr = new SesionR();
        comparador.setStrength(Collator.PRIMARY);
        formatoSerie = "Serie histórica " +  (int)(Double.parseDouble(getAnioPublicacion()) -2) + "-" + getAnioPublicacion();
        
                
        cargarCSV(rutaCSV);
        setCapitulos();
        setIntroCapitulos();
        setContenidos();
        System.out.println("Saliendo de comercio");
    }
    
    protected void setCapitulos(){
        capitulos.add("Comercio Total");
        capitulos.add("Comercio Territorio Aduanero");
        capitulos.add("Comercio Decreto 29-89");
        capitulos.add("Comercio Zonas Francas");
        capitulos.add("Cuadros Estadísticos");
        
    }
    
    protected void setIntroCapitulos(){
        introCapitulos.add("Es el total de las operaciones de comercio exterior. "
                + "Incluye el régimen general de territorio aduanero, el comercio "
                + "de zonas francas y los regímenes "
                + "bajo la aplicación de la ley de Fomento y Desarrollo de la Actividad "
                + "Exportadora y de Maquila (Decreto 29-89).");
        introCapitulos.add("Son las operaciones de comercio exterior que se realizan "
                + "dentro del territorio en el que la aduana ejerce su jurisdicción y "
                + "en el que son plenamente aplicables las disposiciones de la "
                + "legislación arancelaria y aduanera nacional.");
        introCapitulos.add("Regímenes bajo la aplicación de la ley de Fomento y "
                + "Desarrollo de la Actividad Exportadora y de Maquila.");
        introCapitulos.add("Son las operaciones que se realizan en el área de terreno "
                + "físicamente delimitada, planificada y diseñada, sujeta a un "
                + "Régimen Aduanero Especial, en la que personas individuales o "
                + "jurídicas se dediquen indistintamente a la producción o "
                + "comercialización de bienes para la exportación o "
                + "reexportación, así como a la prestación de servicios vinculados "
                + "con el comercio internacional. La zona franca estará custodiada "
                + "y controlada por la autoridad aduanera.");
    }
    protected void setContenidos(){
         contenidos.add(cargarCapitulo1());
         contenidos.add(cargarCapitulo2());
         contenidos.add(cargarCapitulo3());
         contenidos.add(cargarCapitulo4());
         System.out.println("cargados los contenidos");
    }
    
    protected void rellenar(){
        int hoja =1;
        int finContenido = contenidos.size();
        for (int i=0; i<finContenido;i++){
            ArrayList tmpSeccion = (ArrayList)contenidos.get(i);
            escribirCapitulo(capitulos.get(i).toString(), capitulos.get(i).toString()
                ," ", introCapitulos.get(i).toString());
            int finSeccion = tmpSeccion.size();
            for (int j=0; j<finSeccion;j++){
                
                String columna1="";
                String columna2="";
                escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA "+hoja+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
                System.out.println("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA "+hoja+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
                ArrayList tmp = (ArrayList)tmpSeccion.get(j);
                
                columna1 = columna(tmp.get(0).toString(),tmp.get(1).toString(),"",
                        "",tmp.get(2).toString(),tmp.get(3).toString(),
                        tmp.get(4).toString(),tmp.get(5).toString(),"",(Boolean)tmp.get(6));
                try{
                columna2 = columna(tmp.get(7).toString(),tmp.get(8).toString(),"",
                        "",tmp.get(9).toString(),tmp.get(10).toString(),
                        tmp.get(11).toString(),tmp.get(12).toString(),"",(Boolean)tmp.get(13));    
                }catch (Exception e){
                    System.out.println(e.toString());                    
                }
                escribirLinea(hojaTrimestral(columna1, columna2));
                hoja++;
                
            }
        }
        
    }
    protected void cargarCSV(String ruta){
        if (!rr.get().waitForR())
            {
                System.err.println("No se pudo establecer  conexión con R ");
            }else {
                rr.get().eval("library(funcionesINE)");
                REXP listadoCSV = rr.get().eval("comercio <- cargaMasiva('" +  ruta +"')");
                REXP nombres = rr.get().eval("names(comercio)");
                //System.out.println(listadoCSV);
                //System.out.println(nombres);
            }
    }
    
    protected ArrayList cargarCapitulo1(){
        ArrayList cap1 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("1_01");
        seccion1.add("Exportaciones totales");
        seccion1.add("Comercio Total: exportaciones trimestrales en millones de US\\$");
        seccion1.add(formatoSerie);
        seccion1.add("1_01.tex");
        seccion1.add("INE, con datos del BANGUAT");
        seccion1.add(true);
        seccion1.add("1_02");
        seccion1.add("Variación interanual de las exportaciones");
        seccion1.add("Porcentaje de variación interanual de las exportaciones");
        seccion1.add(formatoSerie);
        seccion1.add("1_02.tex");
        seccion1.add("INE, con datos del BANGUAT");
        seccion1.add(true);
        cap1.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("1_03");
        seccion2.add("Importaciones totales");
        seccion2.add("Comercio total: importaciones trimestrales en millones de US\\$");
        seccion2.add(formatoSerie);
        seccion2.add("1_03.tex");
        seccion2.add("INE, con datos del BANGUAT");
        seccion2.add(true);
        seccion2.add("1_04");
        seccion2.add("Variación Interanual de las importaciones");
        seccion2.add("Porcentaje de variación interanual de las importaciones");
        seccion2.add(formatoSerie);
        seccion2.add("1_04.tex");
        seccion2.add("INE, con datos del BANGUAT");
        seccion2.add(true);
        cap1.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("1_05");
        seccion3.add("Balanza comercial general");
        seccion3.add("Balanza comercial general trimestral en millones de US\\$");
        seccion3.add(formatoSerie);
        seccion3.add("1_05.tex");
        seccion3.add("INE, con datos del BANGUAT");
        seccion3.add(true);
        seccion3.add("1_06");
        seccion3.add("Balanza comercial relativa");
        seccion3.add("Balanza comercial relativa adimensional");
        seccion3.add(formatoSerie);
        seccion3.add("1_06.tex");
        seccion3.add("INE, con datos del BANGUAT");
        seccion3.add(true);
        cap1.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("1_07");
        seccion4.add("Balanza comercial por continente");
        seccion4.add("Balanza comercial de Guatemala por continente en millones de US\\$");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("1_07.tex");
        seccion4.add("INE, con datos del BANGUAT");
        seccion4.add(true);
        seccion4.add("1_08");
        seccion4.add("Balanza comercial con Centroamérica");
        seccion4.add("Balanza comercial de Guatemala con el mercado común centroamericano en millones de US\\$");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("1_08.tex");
        seccion4.add("INE, con datos del BANGUAT");
        seccion4.add(true);
        cap1.add(seccion4);
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("1_09");
        seccion5.add("Principales productos de exportación");
        seccion5.add("Principales productos de exportación en millones de US\\$");
        seccion5.add(getFormatoTrimestre());
        seccion5.add("1_09.tex");
        seccion5.add("INE, con datos del BANGUAT");
        seccion5.add(true);
        seccion5.add("1_10");
        seccion5.add("Exportaciones según los principales países de destino");
        seccion5.add("Principales destinos de exportaciones guatemaltecas en millones de US\\$");
        seccion5.add(getFormatoTrimestre());
        seccion5.add("1_10.tex");
        seccion5.add("INE, con datos del BANGUAT");
        seccion5.add(true);
        cap1.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("1_11");
        seccion6.add("Principales exportaciones según secciones SAC");
        seccion6.add("Exportaciones según las principales secciones del SAC en millones de US\\$");
        seccion6.add(getFormatoTrimestre());
        seccion6.add("1_11.tex");
        seccion6.add("INE, con datos del BANGUAT");
        seccion6.add(true);
        seccion6.add("1_12");
        seccion6.add("Principales productos de importación");
        seccion6.add("Principales productos de importación en millones de US\\$");
        seccion6.add(getFormatoTrimestre());
        seccion6.add("1_12.tex");
        seccion6.add("INE, con datos del BANGUAT");
        seccion6.add(true);
        cap1.add(seccion6);
        
        
        ArrayList seccion7 = new ArrayList();
        seccion7.add("1_13");
        seccion7.add("Importaciones según los principales países de origen");
        seccion7.add("Importación a los principales 10 países en millones de US\\$");
        seccion7.add(getFormatoTrimestre());
        seccion7.add("1_13.tex");
        seccion7.add("INE, con datos del BANGUAT");
        seccion7.add(true);
        seccion7.add("1_14");
        seccion7.add("Principales importaciones según secciones del SAC");
        seccion7.add("Importaciones según las principales secciones del SAC en millones de US\\$");
        seccion7.add(getFormatoTrimestre());
        seccion7.add("1_14.tex");
        seccion7.add("INE, con datos del BANGUAT");
        seccion7.add(true);
        cap1.add(seccion7);
        
        return cap1;        
        
    }
    protected ArrayList cargarCapitulo2(){
        ArrayList cap2 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("2_01");
        seccion1.add("Exportaciones territorio aduanero");
        seccion1.add("Comercio territorio aduanero: exportaciones trimestrales en millones de US\\$");
        seccion1.add(formatoSerie);
        seccion1.add("2_01.tex");
        seccion1.add("INE, con datos del BANGUAT");
        seccion1.add(true);
        seccion1.add("2_02");
        seccion1.add("Variación interanual de las exportaciones");
        seccion1.add("Porcentaje de variación interanual de las exportaciones por territorio aduanero");
        seccion1.add(formatoSerie);
        seccion1.add("2_02.tex");
        seccion1.add("INE, con datos del BANGUAT");
        seccion1.add(true);
        cap2.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("2_03");
        seccion2.add("Importaciones territorio aduanero");
        seccion2.add("Importaciones trimestrales comercio territorio aduanero en millones de US\\$");
        seccion2.add(formatoSerie);
        seccion2.add("2_03.tex");
        seccion2.add("INE, con datos del BANGUAT");
        seccion2.add(true);
        seccion2.add("2_04");
        seccion2.add("Variación Interanual de las importaciones");
        seccion2.add("Porcentaje de variación interanual de las importaciones por comercio territorio aduanero");
        seccion2.add(formatoSerie);
        seccion2.add("2_04.tex");
        seccion2.add("INE, con datos del BANGUAT");
        seccion2.add(true);
        cap2.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("2_05");
        seccion3.add("Balanza comercial del territorio aduanero");
        seccion3.add("Balanza comercial trimestral del territorio aduanero en millones de US\\$");
        seccion3.add(formatoSerie);
        seccion3.add("2_05.tex");
        seccion3.add("INE, con datos del BANGUAT");
        seccion3.add(true);
        seccion3.add("2_06");
        seccion3.add("Balanza comercial relativa del territorio aduanero");
        seccion3.add("Balanza comercial relativa del territorio aduanero adimensional");
        seccion3.add(formatoSerie);
        seccion3.add("2_06.tex");
        seccion3.add("INE, con datos del BANGUAT");
        seccion3.add(true);
        cap2.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("2_07");
        seccion4.add("Balanza comercial por continente");
        seccion4.add("Balanza comercial comercio territorio aduanero por continente en millones de US\\$");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("2_07.tex");
        seccion4.add("INE, con datos del BANGUAT");
        seccion4.add(true);
        seccion4.add("2_08");
        seccion4.add("Balanza comercial con Centroamérica");
        seccion4.add("Balanza comercial del territorio aduanero con el mercado común centroamericano en millones de US\\$");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("2_08.tex");
        seccion4.add("INE, con datos del BANGUAT");
        seccion4.add(true);
        cap2.add(seccion4);
        
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("2_09");
        seccion5.add("Principales productos de exportación");
        seccion5.add("Principales productos de exportación por territorio aduanero en millones de US\\$");
        seccion5.add(getFormatoTrimestre());
        seccion5.add("2_09.tex");
        seccion5.add("INE, con datos del BANGUAT");
        seccion5.add(true);
        seccion5.add("2_10");
        seccion5.add("Exportaciones según los principales países de destino");
        seccion5.add("Principales países de exportaciones guatemaltecas por territorio aduanero en millones de US\\$");
        seccion5.add(getFormatoTrimestre());
        seccion5.add("2_10.tex");
        seccion5.add("INE, con datos del BANGUAT");
        seccion5.add(true);
        cap2.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("2_11");
        seccion6.add("Principales exportaciones según secciones SAC");
        seccion6.add("Exportaciones por territorio aduanero según las principales secciones del SAC en millones de US\\$");
        seccion6.add(getFormatoTrimestre());
        seccion6.add("2_11.tex");
        seccion6.add("INE, con datos del BANGUAT");
        seccion6.add(true);
        seccion6.add("2_12");
        seccion6.add("Principales productos de importación");
        seccion6.add("Principales productos de importación por territorio aduanero en millones de US\\$");
        seccion6.add(getFormatoTrimestre());
        seccion6.add("2_12.tex");
        seccion6.add("INE, con datos del BANGUAT");
        seccion6.add(true);
        cap2.add(seccion6);
        
        
        ArrayList seccion7 = new ArrayList();
        seccion7.add("2_13");
        seccion7.add("Importaciones según los principales países de origen");
        seccion7.add("Importación por territorio aduanero a los principales países en millones de US\\$");
        seccion7.add(getFormatoTrimestre());
        seccion7.add("2_13.tex");
        seccion7.add("INE, con datos del BANGUAT");
        seccion7.add(true);
        seccion7.add("2_14");
        seccion7.add("Principales importaciones según secciones del SAC");
        seccion7.add("Importaciones por territorio aduanero según las principales secciones del SAC en millones de US\\$");
        seccion7.add(getFormatoTrimestre());
        seccion7.add("2_14.tex");
        seccion7.add("INE, con datos del BANGUAT");
        seccion7.add(true);
        cap2.add(seccion7);
        
        
        return cap2;        
    }
    
    
    
    protected ArrayList cargarCapitulo3(){
        ArrayList cap3 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("3_01");
        seccion1.add("Exportaciones decreto 29-89");
        seccion1.add("Comercio decreto 29-89: exportaciones trimestrales en millones de US\\$");
        seccion1.add(formatoSerie);
        seccion1.add("3_01.tex");
        seccion1.add("INE, con datos del BANGUAT");
        seccion1.add(true);
        seccion1.add("3_02");
        seccion1.add("Variación interanual de las exportaciones");
        seccion1.add("Porcentaje de variación interanual de las exportaciones por comercio decreto 29-89");
        seccion1.add(formatoSerie);
        seccion1.add("3_02.tex");
        seccion1.add("INE, con datos del BANGUAT");
        seccion1.add(true);
        cap3.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("3_03");
        seccion2.add("Importaciones decreto 29-89");
        seccion2.add("Importaciones trimestrales comercio decreto 29-89 en millones de US\\$");
        seccion2.add(formatoSerie);
        seccion2.add("3_03.tex");
        seccion2.add("INE, con datos del BANGUAT");
        seccion2.add(true);
        seccion2.add("3_04");
        seccion2.add("Variación Interanual de las importaciones");
        seccion2.add("Porcentaje de variación interanual de las importaciones por comercio decreto 29-89");
        seccion2.add(formatoSerie);
        seccion2.add("3_04.tex");
        seccion2.add("INE, con datos del BANGUAT");
        seccion2.add(true);
        cap3.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("3_05");
        seccion3.add("Balanza comercial del decreto 29-89");
        seccion3.add("Balanza comercial trimestral del decreto 29-89 en millones de US\\$");
        seccion3.add(formatoSerie);
        seccion3.add("3_05.tex");
        seccion3.add("INE, con datos del BANGUAT");
        seccion3.add(true);
        seccion3.add("3_06");
        seccion3.add("Balanza comercial relativa del decreto 29-89");
        seccion3.add("Balanza comercial relativa del decreto 29-89 adimensional");
        seccion3.add(formatoSerie);
        seccion3.add("3_06.tex");
        seccion3.add("INE, con datos del BANGUAT");
        seccion3.add(true);
        cap3.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("3_07");
        seccion4.add("Balanza comercial por continente");
        seccion4.add("Balanza comercial comercio decreto 29-89 por continente en millones de US\\$");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("3_07.tex");
        seccion4.add("INE, con datos del BANGUAT");
        seccion4.add(true);
        seccion4.add("3_08");
        seccion4.add("Balanza comercial con Centroamérica");
        seccion4.add("Balanza comercial del decreto 29-89 con el mercado común centroamericano en millones de US\\$");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("3_08.tex");
        seccion4.add("INE, con datos del BANGUAT");
        seccion4.add(true);
        cap3.add(seccion4);
        
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("3_09");
        seccion5.add("Principales productos de exportación");
        seccion5.add("Principales productos de exportación por decreto 29-89 en millones de US\\$");
        seccion5.add(getFormatoTrimestre());
        seccion5.add("3_09.tex");
        seccion5.add("INE, con datos del BANGUAT");
        seccion5.add(true);
        seccion5.add("3_10");
        seccion5.add("Exportaciones según los principales países de destino");
        seccion5.add("Principales países de exportaciones guatemaltecas por decreto 29-89 en millones de US\\$");
        seccion5.add(getFormatoTrimestre());
        seccion5.add("3_10.tex");
        seccion5.add("INE, con datos del BANGUAT");
        seccion5.add(true);
        cap3.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("3_11");
        seccion6.add("Principales exportaciones según secciones SAC");
        seccion6.add("Exportaciones por decreto 29-89 según las principales secciones del SAC en millones de US\\$");
        seccion6.add(getFormatoTrimestre());
        seccion6.add("3_11.tex");
        seccion6.add("INE, con datos del BANGUAT");
        seccion6.add(true);
        seccion6.add("3_12");
        seccion6.add("Principales productos de importación");
        seccion6.add("Principales productos de importación por decreto 29-89 en millones de US\\$");
        seccion6.add(getFormatoTrimestre());
        seccion6.add("3_12.tex");
        seccion6.add("INE, con datos del BANGUAT");
        seccion6.add(true);
        cap3.add(seccion6);
        
        
        ArrayList seccion7 = new ArrayList();
        seccion7.add("3_13");
        seccion7.add("Importaciones según los principales países de origen");
        seccion7.add("Importación por decreto 29-89 a los principales países en millones de US\\$");
        seccion7.add(getFormatoTrimestre());
        seccion7.add("3_13.tex");
        seccion7.add("INE, con datos del BANGUAT");
        seccion7.add(true);
        seccion7.add("3_14");
        seccion7.add("Principales importaciones según secciones del SAC");
        seccion7.add("Importaciones por decreto 29-89 según las principales secciones del SAC en millones de US\\$");
        seccion7.add(getFormatoTrimestre());
        seccion7.add("3_14.tex");
        seccion7.add("INE, con datos del BANGUAT");
        seccion7.add(true);
        cap3.add(seccion7);
        
        
        return cap3;        
    }
    
    
    protected ArrayList cargarCapitulo4(){
        ArrayList cap4 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("4_01");
        seccion1.add("Exportaciones zonas francas");
        seccion1.add("Comercio zonas francas: exportaciones trimestrales en millones de US\\$");
        seccion1.add(formatoSerie);
        seccion1.add("4_01.tex");
        seccion1.add("INE, con datos del BANGUAT");
        seccion1.add(true);
        seccion1.add("4_02");
        seccion1.add("Variación interanual de las exportaciones");
        seccion1.add("Porcentaje de variación interanual de las exportaciones por comercio zonas francas");
        seccion1.add(formatoSerie);
        seccion1.add("4_02.tex");
        seccion1.add("INE, con datos del BANGUAT");
        seccion1.add(true);
        cap4.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("4_03");
        seccion2.add("Importaciones zonas francas");
        seccion2.add("Importaciones trimestrales comercio zonas francas en millones de US\\$");
        seccion2.add(formatoSerie);
        seccion2.add("4_03.tex");
        seccion2.add("INE, con datos del BANGUAT");
        seccion2.add(true);
        seccion2.add("4_04");
        seccion2.add("Variación Interanual de las importaciones");
        seccion2.add("Porcentaje de variación interanual de las importaciones por comercio zonas francas");
        seccion2.add(formatoSerie);
        seccion2.add("4_04.tex");
        seccion2.add("INE, con datos del BANGUAT");
        seccion2.add(true);
        cap4.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("4_05");
        seccion3.add("Balanza comercial de zonas francas");
        seccion3.add("Balanza comercial trimestral de zonas francas en millones de US\\$");
        seccion3.add(formatoSerie);
        seccion3.add("4_05.tex");
        seccion3.add("INE, con datos del BANGUAT");
        seccion3.add(true);
        seccion3.add("4_06");
        seccion3.add("Balanza comercial relativa de zonas francas");
        seccion3.add("Balanza comercial relativa de zonas francas adimensional");
        seccion3.add(formatoSerie);
        seccion3.add("4_06.tex");
        seccion3.add("INE, con datos del BANGUAT");
        seccion3.add(true);
        cap4.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("4_07");
        seccion4.add("Balanza comercial por continente");
        seccion4.add("Balanza comercial zonas francas por continente en millones de US\\$");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("4_07.tex");
        seccion4.add("INE, con datos del BANGUAT");
        seccion4.add(true);
        seccion4.add("4_08");
        seccion4.add("Balanza comercial con Centroamérica");
        seccion4.add("Balanza comercial de zonas francas con el mercado común centroamericano en millones de US\\$");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("4_08.tex");
        seccion4.add("INE, con datos del BANGUAT");
        seccion4.add(true);
        cap4.add(seccion4);
        
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("4_09");
        seccion5.add("Principales productos de exportación");
        seccion5.add("Principales productos de exportación por zonas francas en millones de US\\$");
        seccion5.add(getFormatoTrimestre());
        seccion5.add("4_09.tex");
        seccion5.add("INE, con datos del BANGUAT");
        seccion5.add(true);
        seccion5.add("4_10");
        seccion5.add("Exportaciones según los principales países de destino");
        seccion5.add("Principales países de exportaciones guatemaltecas por zonas francas en millones de US\\$");
        seccion5.add(getFormatoTrimestre());
        seccion5.add("4_10.tex");
        seccion5.add("INE, con datos del BANGUAT");
        seccion5.add(true);
        cap4.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("4_11");
        seccion6.add("Principales exportaciones según secciones SAC");
        seccion6.add("Exportaciones por zonas francas según las principales secciones del SAC en millones de US\\$");
        seccion6.add(getFormatoTrimestre());
        seccion6.add("4_11.tex");
        seccion6.add("INE, con datos del BANGUAT");
        seccion6.add(true);
        seccion6.add("4_12");
        seccion6.add("Principales productos de importación");
        seccion6.add("Principales productos de importación por zonas francas en millones de US\\$");
        seccion6.add(getFormatoTrimestre());
        seccion6.add("4_12.tex");
        seccion6.add("INE, con datos del BANGUAT");
        seccion6.add(true);
        cap4.add(seccion6);
        
        
        ArrayList seccion7 = new ArrayList();
        seccion7.add("4_13");
        seccion7.add("Importaciones según los principales países de origen");
        seccion7.add("Importación por zonas francas a los principales países en millones de US\\$");
        seccion7.add(getFormatoTrimestre());
        seccion7.add("4_13.tex");
        seccion7.add("INE, con datos del BANGUAT");
        seccion7.add(true);
        seccion7.add("4_14");
        seccion7.add("Principales importaciones según secciones del SAC");
        seccion7.add("Importaciones por zonas francas según las principales secciones del SAC en millones de US\\$");
        seccion7.add(getFormatoTrimestre());
        seccion7.add("4_14.tex");
        seccion7.add("INE, con datos del BANGUAT");
        seccion7.add(true);
        cap4.add(seccion7);
        
        
        return cap4;        
    }
        protected void apendices(String rutaTEX){
        escribirLinea("\n \\appendixa \n" +
        "\n" +
        "\n" +
        "\n" );
        apendice1();
        Tabla ap = new Tabla(rutaTEX,trimestres(),rr);
        ap.setRuta("/var/www/html/Comercio/Entradas/CSV");
        ap.generarComercioExterior();
        
        
        
    }
    private void apendice1(){
        String columna1 = tablaApendice("A_01",
                "Comercio total: análisis de variación de las exportaciones",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos del BANGUAT",
                "");
        
        String columna2 = tablaApendice("A_02",
                "Comercio total: análisis de variación de las importaciones",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos del BANGUAT",
                "");
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    protected void generarGraficas(String modalidad){
        System.out.println("GENERANDO LAS GRAFICAS");
        Grafica comercio = new Grafica("comercio", getRuta(), rr.get(), modalidad);
        comercio.start();
    }
    
    protected void hacerPortada(){
       File source = new File("/home/ineservidor/Comercio/Caratula");
        File dest = new File(getRuta());
        try {
            FileUtils.copyDirectory(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
  
        try {
            File p1 = new File(getRuta(),"caratula.tex");
            FileWriter escritora = new FileWriter(p1.getAbsolutePath(),false);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write("\\documentclass[10pt,twoside]{book}\\usepackage[T1]{fontenc}\n" +
            "\\usepackage{tikz}\n" +
            "\n" +
            "\\usepackage[active,tightpage,xetex]{preview}\n" +
            "\n" +
            "\\usepackage{fontspec,xunicode}\n" +
            "\n" +
            "\\PreviewEnvironment{pgfpicture}\n" +
            "\n" +
            "\\setlength\\PreviewBorder{0pt}\n" +
            "\n" +
            "\\usetikzlibrary{calc}\n" +
            "\n" +
            "\\usetikzlibrary{positioning}\n" +
            "\n" +
            "\\usepackage{fontspec,xunicode}\n" +
            "\n" +
            "\\setmainfont{Open Sans Condensed Light}\n" +
            "\\begin{document}\n" +
            "\n" +
            "\\begin{tikzpicture} \n "+
            "\\node[anchor=south west,inner sep=0] (image) at (0,0) {\\includegraphics{portada}};\n" +
            "\\begin{scope}[x={(image.south east)},y={(image.north west)}] "
            + "\\node[inner sep =0, scale = 3.5, align = left] at (0.44,0.595) {\n" +
             "República de Guatemala \n" +
            "		\\\\\n" +
            getTitulo() + "\n" +
            "		\\\\\n" +
            corregirTrimestre( getTrimestre() ) +  " trimestre "  + getAnioPublicacion() + " };" +
            "\\node[inner sep =0, rotate = 90]at(0.908,0.15){Guatemala, "+ getMes()  +" de " +  getYear()+"};\n "
            + "\\node[inner sep = 0, rotate = 90]at(0.18, 0.39) {\\textcolor{gray}{Cifras Preliminares}}; \n" +
            "\\end{scope}\n" +
            "\\end{tikzpicture}\n" +
            "\n" +
            "\\end{document}");
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        compilar(rr, getRuta() + "/caratula.tex","F");
    }
    
    protected void equipoYPresentacion(){
        
        
        escribirLinea("\\clearpage\n" +
                "\n" +
                "$\\ $\n" +
                "\\vspace{0.5cm}\n" +
                "\n" +
                "\\begin{center}\n" +
                "	{\\Bold \\LARGE EQUIPO RESPONSABLE}\\\\[1.5cm]\n" +
                "	\n" +
                "	{\\Bold \\large \\color{color2} REVISIÓN GENERAL}\\\\[0.2cm]\n" +
                "	Rubén Narciso\\\\[0.8cm]\n" +
                "	\n" +
                "	\n" +
                "	{\\Bold \\large \\color{color2} EQUIPO TÉCNICO}\\\\[0.2cm]\n" +
                "	Flor de María Hernández Soto\\\\\n" +
                "	Cristian Miguel Cabrera Ayala\\\\\n" +
                "	Blanca Angelica Ramirez González\\\\\n" +
                "	Marlon Humberto Pirir Garcia\\\\[0.8cm]\n" +
                "	\n" +
                "	{\\Bold \\large \\color{color2} DIAGRAMACIÓN Y DISEÑO}\\\\[0.2cm]\n" +
                "	Hugo Allan García Monterrosa\\\\\n" +
                "	Fabiola Beatriz Ramírez Pinto\\\\\n" +
                "	José Carlos Bonilla Aldana\\\\[0.8cm]\n" +
                "	\n" +
                "	\n" +
                "	\n" +
                "\\end{center}\\setcounter{page}{0}\\cleardoublepage\n" +
                "\n" +
                "\n" +
                "\n" +
                "$\\ $\\\\[0.7cm]\n" +
                "\n" +
                "\\tableofcontents\n" +
                "\n" +
                "\\cleardoublepage\n" +
                "\\pagestyle{estandar}\n" +
                "\\setcounter{page}{1}\n" +
                "\\setlength{\\arrayrulewidth}{1.0pt}\n" +
                "\n" +
                "\n" +
                "\\cleardoublepage\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "$\\ $\\\\[0.5cm]\n" +
                "\\thispagestyle{empty}\n" +
                "\\noindent {\\Bold \\LARGE Presentación}\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "$\\ $\\\\\n" +
                "\\indent El Instituto Nacional de Estadística -INE-, consciente de la demanda de información "
                + "económica y siendo el ente rector de la política estadística nacional "
                + "en Guatemala, en cumplimiento a su Ley Orgánica, Decreto Ley 3-85, se "
                + "complace en presentar el siguiente informe, que contiene las {\\Bold Estadísticas de Comercio Exterior}"
                + ", con información correspondiente al {\\Bold " + corregirTrimestre(getTrimestre()).toLowerCase() + " trimestre del "
                + getAnioPublicacion() +"}, la cual es {\\Bold preliminar} y será ajustada con el ingreso de registros tardíos.\n" +
                "\n" +
                "Se detallan 3 tipos de comercio: Territorio Aduanero, Decreto 29-89 y Zonas Francas. "
                + "Se incorpora, además, el Comercio Total, que integra los tres tipos de comercio.\n"+
                "\n" +
                "Se detallan los datos en valores de US\\$ y peso en kilogramos. Estos datos son registrados "
                + "a través de las aduanas del país por medio de las vías aérea, marítima y terrestre. \n"+
                "\n" +
                "Las variables se clasifican de acuerdo al Sistema Arancelario Centroamericano -SAC-, "
                + "el cual se integra en secciones, capítulos, partidas, sub-partidas e incisos, "
                + "determinados por combinaciones de números (códigos).\n"+
                "\n" +
                
                "Se agradece al Banco de Guatemala (Banguat) por su valiosa colaboración al facilitar los datos.\n" +
                "\n" +
                "\\thispagestyle{empty}\n" +
                "\n" +
                "\n" +
                "\\cleardoublepage");
    }
    
         @Override
    protected void preambuloPresentacion(){
        File source = new File("/home/ineservidor/Vitales/Presentacion");
        File dest = new File(getRuta());
        try {
            FileUtils.copyDirectory(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

