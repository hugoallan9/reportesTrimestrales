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
public class Transito extends Documento{
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
    
    
    
    
    public Transito(String titulo, String trimestre, String pYear, String rutaCSV) {
        super(titulo, trimestre, pYear);
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
    }
    
    protected void setCapitulos(){
        capitulos.add("Eventos de tránsito");
        capitulos.add("Víctimas lesionadas \\\\  y fallecidas");
        capitulos.add("Atropellados");
                
    }
    
    protected void setIntroCapitulos(){
        introCapitulos.add("Los vehículos se entiende que es cualquier medio de transporte que "
                + "circule en la vía pública para el transporte de personas, cargas o estén "
                + "destinados a actividades especiales. \n \n Un evento de tránsito es "
                + "un suceos donde uno o varios vehículos ocasionan perjuico a una persona, "
                + "bien público o privado, por acción negligente de conductores, pasajeros "
                + "o peatones (como factores humanos), factores mecánicos o climatológicos.");
        introCapitulos.add("Es el recuento de las personas que sufrieron un daño o perjuicio, "
                + "provocado en un evento de tránsito, ya sea por imprudencia propia o "
                + "factores externos del tipo ambiental o humano. \n Los daños que se "
                + "registran en las personas son del tipo físico.");
        introCapitulos.add("Un atropello es la acción en la que uno o varios peatones son "
                + "arrollados por un vehículo en movimiento y a las víctimas de este hecho se "
                + "les denomina atropellados.");        
    }
    protected void setContenidos(){
         contenidos.add(cargarCapitulo1());
         contenidos.add(cargarCapitulo2());
         contenidos.add(cargarCapitulo3());
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
                REXP listadoCSV = rr.get().eval("transito <- cargaMasiva('" +  ruta +"', codificacion = 'utf8')");
                REXP nombres = rr.get().eval("names(transito)");
                System.out.println(listadoCSV);
                System.out.println(nombres);
            }
    }
    
    protected ArrayList cargarCapitulo1(){
        ArrayList cap1 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("1_01");
        seccion1.add("Eventos registrados");
        seccion1.add("Número de eventos de tránsito");
        seccion1.add(formatoSerie);
        seccion1.add("1_01.tex");
        seccion1.add("INE, con datos de Polícia Nacional Civil");
        seccion1.add(true);
        seccion1.add("1_02");
        seccion1.add("Eventos por departamento");
        seccion1.add("Distribución porcentual de eventos de tránsito según departamento");
        seccion1.add(getFormatoTrimestre());
        seccion1.add("1_02.tex");
        seccion1.add("INE, con datos de Polícia Nacional Civil");
        seccion1.add(true);
        cap1.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("1_03");
        seccion2.add("Eventos por día de la semana");
        seccion2.add("Distribución porcentual de eventos de tránsito según día de la semana");
        seccion2.add(getFormatoTrimestre());
        seccion2.add("1_03.tex");
        seccion2.add("INE, con datos de Polícia Nacional Civil");
        seccion2.add(true);
        seccion2.add("1_04");
        seccion2.add("Tipo de evento");
        seccion2.add("Distribución porcentual de eventos de tránsito según tipo de evento");
        seccion2.add(getFormatoTrimestre());
        seccion2.add("1_04.tex");
        seccion2.add("INE, con datos de Polícia Nacional Civil");
        seccion2.add(true);
        cap1.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("1_05");
        seccion3.add("Vehículos involucrados");
        seccion3.add("Número de vehículos involucrados en eventos de tránsito");
        seccion3.add(formatoSerie);
        seccion3.add("1_05.tex");
        seccion3.add("INE, con datos de Polícia Nacional Civil");
        seccion3.add(true);
        seccion3.add("1_06");
        seccion3.add("Tipo de vehículo");
        seccion3.add("Distribución porcentual de vehículos involucrados en eventos de "
                + "tránsito según tipo de vehículo");
        seccion3.add(getFormatoTrimestre());
        seccion3.add("1_06.tex");
        seccion3.add("INE, con datos de Polícia Nacional Civil");
        seccion3.add(true);
        cap1.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("1_07");
        seccion4.add("Conductores según sexo");
        seccion4.add("Distribución porcentual de conductores involucrados en eventos de "
                + "tránsito según sexo");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("1_07.tex");
        seccion4.add("INE, con datos de Polícia Nacional Civil");
        seccion4.add(true);
        seccion4.add("1_08");
        seccion4.add("Edad de los conductores");
        seccion4.add("Distribución porcentual de conductores involucrados en eventos de "
                + "tránsito según grupo de edad");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("1_08.tex");
        seccion4.add("INE, con datos de Polícia Nacional Civil");
        seccion4.add(true);
        cap1.add(seccion4);
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("1_09");
        seccion5.add("Condición de ebriedad de los conductores");
        seccion5.add("Distribución porcentual de conductores involucrados en eventos de "
                + "tránsito según estado de ebriedad");
        seccion5.add(getFormatoTrimestre());
        seccion5.add("1_09.tex");
        seccion5.add("INE, con datos de Polícia Nacional Civil");
        seccion5.add(true);
        seccion5.add("1_10");
        seccion5.add("Conductores en estado de ebriedad");
        seccion5.add("Conductores en estado de briedad involucrados en eventos de tránsito");
        seccion5.add(formatoSerie);
        seccion5.add("1_10.tex");
        seccion5.add("INE, con datos de Polícia Nacional Civil");
        seccion5.add(true);
        cap1.add(seccion5);
        
        return cap1;        
        
    }
    protected ArrayList cargarCapitulo2(){
        ArrayList cap2 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("2_01");
        seccion1.add("Víctimas");
        seccion1.add("Víctimas lesionadas y fallecidas por eventos de tránsito");
        seccion1.add(formatoSerie);
        seccion1.add("2_01.tex");
        seccion1.add("INE, con datos de Policía Nacional Civil");
        seccion1.add(true);
        seccion1.add("2_02");
        seccion1.add("Víctimas lesionadas y fallecidas");
        seccion1.add("Victimas fallecidas y lesionadas por eventos de tránsito según su tipo");
        seccion1.add(getFormatoTrimestre());
        seccion1.add("2_02.tex");
        seccion1.add("INE, con datos de Policía Nacional Civil");
        seccion1.add(true);
        cap2.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("2_03");
        seccion2.add("Víctimas fallecidas");
        seccion2.add("Número de víctimas fallecidas por eventos de tránsito");
        seccion2.add(formatoSerie);
        seccion2.add("2_03.tex");
        seccion2.add("INE, con datos de Policía Nacional Civil");
        seccion2.add(true);
        seccion2.add("2_04");
        seccion2.add("Víctimas fallecidas por departamento");
        seccion2.add("Distribución porcentual de víctimas fallecidas según departamento");
        seccion2.add(getFormatoTrimestre());
        seccion2.add("2_04.tex");
        seccion2.add("INE, con datos de Policía Nacional Civil");
        seccion2.add(true);
        cap2.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("2_05");
        seccion3.add("Víctimas fallecidas según sexo");
        seccion3.add("Distribución porcentual de víctimas fallecidas en eventos de tránsito "
                + "según sexo");
        seccion3.add(getFormatoTrimestre());
        seccion3.add("2_05.tex");
        seccion3.add("INE, con datos de Policía Nacional Civil");
        seccion3.add(true);
        seccion3.add("2_06");
        seccion3.add("Víctimas fallecidas según grupo de edad");
        seccion3.add("Distribución porcentual de víctimas fallecidas en eventos de tránsito "
                + "según grupo de edad");
        seccion3.add(getFormatoTrimestre());
        seccion3.add("2_06.tex");
        seccion3.add("INE, con datos de Policía Nacional Civil");
        seccion3.add(true);
        cap2.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("2_07");
        seccion4.add("Víctimas lesionadas");
        seccion4.add("Número de víctimas lesionadas por eventos de tránsito");
        seccion4.add(formatoSerie);
        seccion4.add("2_07.tex");
        seccion4.add("INE, con datos de Policía Nacional Civil");
        seccion4.add(true);
        seccion4.add("2_08");
        seccion4.add("Lesionados por departamento");
        seccion4.add("Distribución porcentual de víctimas lesionadas por eventos de tránsito "
                + "según departamento");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("2_08.tex");
        seccion4.add("INE, con datos de Policía Nacional Civil");
        seccion4.add(true);
        cap2.add(seccion4);
        
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("2_09");
        seccion5.add("Lesionados según sexo");
        seccion5.add("Distribución porcentual de víctimas lesionadas por eventos de tránsito "
                + "según sexo");
        seccion5.add(getFormatoTrimestre());
        seccion5.add("2_09.tex");
        seccion5.add("INE, con datos de Policía Nacional Civil");
        seccion5.add(true);
        seccion5.add("2_10");
        seccion5.add("Lesionados según grupo de edad");
        seccion5.add("Distribución porcentual de víctimas lesionadas por eventos de tránsito "
                + "según grupo de edad");
        seccion5.add(getFormatoTrimestre());
        seccion5.add("2_10.tex");
        seccion5.add("INE, con datos de Policía Nacional Civil");
        seccion5.add(true);
        cap2.add(seccion5);
        
        return cap2;        
    }
    
    
    
    protected ArrayList cargarCapitulo3(){
        ArrayList cap3 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("3_01");
        seccion1.add("Eventos de tránsito por atropello");
        seccion1.add("Atropellos en eventos de tŕansito");
        seccion1.add(formatoSerie);
        seccion1.add("3_01.tex");
        seccion1.add("INE, con datos de Policía Nacional Civil");
        seccion1.add(true);
        seccion1.add("3_02");
        seccion1.add("Eventos por atropello según departamento");
        seccion1.add("Distribución porcentual de eventos por atropello según departamento");
        seccion1.add(getFormatoTrimestre());
        seccion1.add("3_02.tex");
        seccion1.add("INE, con datos de Policía Nacional Civil");
        seccion1.add(true);
        cap3.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("3_03");
        seccion2.add("Eventos por atropello según día de la semana");
        seccion2.add("Distribución porcentual de eventos por atropello según día de la semana");
        seccion2.add(getFormatoTrimestre());
        seccion2.add("3_03.tex");
        seccion2.add("INE, con datos de Policía Nacional Civil");
        seccion2.add(true);
        seccion2.add("3_04");
        seccion2.add("Tipo de vehículo");
        seccion2.add("Distribución porcentual de vehículos involuctrados en atropello "
                + "según tipo de vehículo");
        seccion2.add(getFormatoTrimestre());
        seccion2.add("3_04.tex");
        seccion2.add("INE, con datos de Policía Nacional Civil");
        seccion2.add(true);
        cap3.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("3_05");
        seccion3.add("Condición de ebriedad del conductor");
        seccion3.add("Distribución porcentual de conductores  involucrados en eventos por "
                + "atropello según condición de ebriedad");
        seccion3.add(getFormatoTrimestre());
        seccion3.add("3_05.tex");
        seccion3.add("INE, con datos de Policía Nacional Civil");
        seccion3.add(true);
        seccion3.add("3_06");
        seccion3.add("Víctimas fallecidas por atropello");
        seccion3.add("Víctimas fallecidas por atropello");
        seccion3.add(formatoSerie);
        seccion3.add("3_06.tex");
        seccion3.add("INE, con datos de Policía Nacional Civil");
        seccion3.add(true);
        cap3.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("3_07");
        seccion4.add("Fallecidos por atropello según departamento");
        seccion4.add("Distribución porcentual de víctimas fallecidas por atropello según departamento");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("3_07.tex");
        seccion4.add("INE, con datos de Policía Nacional Civil");
        seccion4.add(true);
        seccion4.add("3_08");
        seccion4.add("Fallecidos por atropello según sexo");
        seccion4.add("Distribución porcentual de víctimas fallecidas por atropello según sexo");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("3_08.tex");
        seccion4.add("INE, con datos de Policía Nacional Civil");
        seccion4.add(true);
        cap3.add(seccion4);
        
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("3_09");
        seccion5.add("Fallecidos por atropello según grupo de edad");
        seccion5.add("Distribución porcentual de víctimas fallecidas por atropello según "
                + "grupo de edad");
        seccion5.add(getFormatoTrimestre());
        seccion5.add("3_09.tex");
        seccion5.add("INE, con datos de Policía Nacional Civil");
        seccion5.add(true);
        seccion5.add("3_10");
        seccion5.add("Fallecidos por atropello según grupo de edad y sexo");
        seccion5.add("Víctimas fallecidas por atropello según grupo de edad y sexo");
        seccion5.add(getFormatoTrimestre());
        seccion5.add("3_10.tex");
        seccion5.add("INE, con datos de Policía Nacional Civil");
        seccion5.add(true);
        cap3.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("3_11");
        seccion6.add("Lesionados por atropello");
        seccion6.add("Víctimas atropelladas lesionadas");
        seccion6.add(formatoSerie);
        seccion6.add("3_11.tex");
        seccion6.add("INE, con datos de Policía Nacional Civil");
        seccion6.add(true);
        seccion6.add("3_12");
        seccion6.add("Lesionados por atropello según departamento");
        seccion6.add("Distribución porcentual de víctimas lesionadas por atropello según "
                + "departamento");
        seccion6.add(getFormatoTrimestre());
        seccion6.add("3_12.tex");
        seccion6.add("INE, con datos del BANGUAT");
        seccion6.add(true);
        cap3.add(seccion6);
        
        
        ArrayList seccion7 = new ArrayList();
        seccion7.add("3_13");
        seccion7.add("Lesionados por atropello según sexo");
        seccion7.add("Distribución porcentual de víctimas lesionadas por atropello según sexo");
        seccion7.add(getFormatoTrimestre());
        seccion7.add("3_13.tex");
        seccion7.add("INE, con datos de Policía Nacional Civil");
        seccion7.add(true);
        seccion7.add("3_14");
        seccion7.add("Lesionados por atropello según grupo de edad");
        seccion7.add("Distribución porcentual de víctimas lesionadas por atropello según grupo "
                + "de edad");
        seccion7.add(getFormatoTrimestre());
        seccion7.add("3_14.tex");
        seccion7.add("INE, con datos de Policía Nacional Civil");
        seccion7.add(true);
        cap3.add(seccion7);
        
        ArrayList seccion8 = new ArrayList();
        seccion8.add("3_15");
        seccion8.add("Lesionados por atropello según grupo de edad y sexo");
        seccion8.add("Víctimas lesionadas por atropello según grupo de edad y sexo");
        seccion8.add(getFormatoTrimestre());
        seccion8.add("3_15.tex");
        seccion8.add("INE, con datos de Policía Nacional Civil");
        seccion8.add(true);
        cap3.add(seccion8);
        return cap3;        
    }
    
    
    protected void generarGraficas(String modalidad){
        System.out.println("GENERANDO LAS GRAFICAS");
        Grafica vitales = new Grafica("transito", getRuta(), rr.get(), modalidad);
        vitales.start();
    }
    
    
    
    protected void apendices(String rutaTEX){
        escribirLinea("\n \\appendixa \n" +
        "\n" +
        "\n" +
        "\n" );
        apendice1();
        apendice2();
        apendice3();
        apendice4();
        Tabla ap = new Tabla(rutaTEX,trimestres(),rr);
        ap.setRuta("/var/www/html/Transito/Entradas/CSV");
        ap.generarTransito();
        
        
        
    }
    private void apendice1(){
        String columna1 = tablaApendice("A_01",
                "Análisis de variación del número de eventos de tránsito",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos de Policía Nacional Civil",
                "");
        
        String columna2 = tablaApendice("A_02",
                "Análisis de variación del número de lesionados en eventos "
                        + "de tránsito",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos de Policía Nacional Civil",
                "");
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    private void apendice2(){
        String columna1 = tablaApendice("A_03",
                "Análisis de variación del número de víctimas que "
                        + "fallecieron debido a eventos de tránsito",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos de Policía Nacional Civil",
                "");
        
        String columna2 = tablaApendice("A_04",
                "Análisis de variación del número de vehículos involucrados "
                        + "en eventos de tránsito",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos de Policía Nacional Civil",
                "");
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    private void apendice3(){
        String columna1 = tablaApendice("A_05",
                "Análisis de variación del número eventos de tránsito "
                        + "con atropello",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos de Policía Nacional Civil",
                "");
        
        String columna2 = tablaApendice("A_06",
                "Análisis de variación del número de víctimas lesionados "
                        + "por atropello",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos de Policía Nacional Civil",
                "");
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    private void apendice4(){
        String columna1 = tablaApendice("A_07",
                "Análisis de variación del número de víctimas fallecidas "
                        + "por atropello",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos de Policía Nacional Civil",
                "");
        
        String columna2 = "";
        escribirLinea(hojaTrimestral(columna1, columna2));
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
            + "\\node[inner sep =0, scale = 3.5, align = left] at (0.55,0.595) {\n" +
             "República de Guatemala \n" +
            "		\\\\\n" +
            getTitulo() + "\n" +
            "		\\\\\n" +
            corregirTrimestre( getTrimestre() ) +  " trimestre "  + getAnioPublicacion() + " };" +
            "\\node[inner sep =0, rotate = 90]at(0.908,0.15){Guatemala, "+ getMesServidor()+" de " +  getYearServidor()+"};\n "
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
                "\\indent El Instituto Nacional de Estadística -INE-, consciente de la demanda "
                + "de información relacionada con los eventos de tránsito y cumpliendo su Ley "
                + "Orgánica, Decreto Ley 3-85, así como lo establecido en la Ley de Tránsito "
                + "y su Reglamento, Decreto número 132-96, registra datos estadísticos sobre "
                + "eventos de tránsito. \n"
                + "\n"
                + "La información presentada a continuación se generpo a través de las tres bases "
                + "de datos que la Policía Nacional Civil entrega al INE, las cuales son: "
                + "hechos de tránsito, personas lesionadas y personas fallecidas. \n"
                + "\n"
                + "Finalmente, el INE presenta el informe de las Estadísticas de "
                + "{\\Bold Eventos de Tránsito} del "
                + " {\\Bold " + corregirTrimestre(getTrimestre()).toLowerCase() + " trimestre del "
                + getAnioPublicacion() +"} con el propósito de dar a conocer la caracterización "
                + "de este fenómeno y de esta manera apoyar la elaboración de programas, políticas, "
                + "planes para la seguridad vial. Dicha {\\Bold información es preliminar} y será ajustado "
                + "con el ingreso de registros tardíos.\n" 
                + "\n" 
                + "Se agradece el aporte y colaboración de la Policía Nacional Civil -PNC- y "
                + "cordialmente se insta a continuar con el apoyo a este proceso.\n" +
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

