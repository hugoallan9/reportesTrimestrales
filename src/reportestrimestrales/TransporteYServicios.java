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
public class TransporteYServicios extends Documento{
    private List capitulos;
    private List introCapitulos;
    private List contenidos;
    private String formatoSerie;
    private String formatoTrimestre;
    Collator comparador = Collator.getInstance();
    private SesionR rr;
    private String rutaCSV;    
    public SesionR getRr() {
        return rr;
    }
    
    
    
    
    public TransporteYServicios(String titulo, String trimestre, String pYear, String rutaCSV) {
        super(titulo, trimestre, pYear);
        capitulos = new ArrayList();
        introCapitulos = new ArrayList();
        contenidos = new ArrayList();
        this.rutaCSV = rutaCSV;
        rr = new SesionR();
        comparador.setStrength(Collator.PRIMARY);
        formatoSerie = "República de Guatemala, serie histórica trimestral, ";
        formatoTrimestre ="República de Guatemala, "+corregirTrimestre(getTrimestre()).toLowerCase() + " trimestre "
                + getAnioPublicacion() +", ";
                
                
        cargarCSV(rutaCSV);
        setCapitulos();
        setIntroCapitulos();
        setContenidos();
    }
    
    protected void setCapitulos(){
        capitulos.add("Tráfico aéreo");
        capitulos.add("Tráfico postal");
        capitulos.add("Movimiento marítimo");
        //capitulos.add("Parque vehicular");
        
    }
    
    protected void setIntroCapitulos(){
        introCapitulos.add("Es el servicio de trasladar de un lugar a otro a los "
                + "pasajeros o cargamento, mediante la utilización de aeronaves.");
        introCapitulos.add("Es el servicio de mensajería de correspondencia, "
                + "paquetes y otros a través de avión.");
        introCapitulos.add("Es el servicio de llevar carga por mar de un punto "
                + "geográfico a otro a través de buques, siendo el modo más "
                + "utilizado para el comercio internacional y el que soporta "
                + "mayor movimiento de mercancías, tanto en contenedores como "
                + "graneles secos o líquidos.");
        /*introCapitulos.add("Es la cantidad de vehículos registrados en la "
                + "ciudad de Guatemala a través de la Superintendencia de "
                + "Administración Tributaria.");*/
    }
    protected void setContenidos(){
         contenidos.add(cargarCapitulo1());
         contenidos.add(cargarCapitulo2());
         contenidos.add(cargarCapitulo3());
         //contenidos.add(cargarCapitulo4());
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
                REXP listadoCSV = rr.get().eval("transporte <- cargaMasiva('" +  ruta +"')");
                REXP nombres = rr.get().eval("names(transporte)");
                System.out.println(listadoCSV);
                System.out.println(nombres);
            }
    }
    
    protected ArrayList cargarCapitulo1(){
        ArrayList cap1 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("1_01");
        seccion1.add("Exportación aérea");
        seccion1.add("Volumen de carga exportada a través del "
                + "aeropuerto internacional La Aurora");
        seccion1.add(formatoSerie + "en miles de kilogramos");
        seccion1.add("1_01.tex");
        seccion1.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion1.add(true);
        seccion1.add("1_02");
        seccion1.add("Importación aérea");
        seccion1.add("Volumen de carga importada a través del "
                + "aeropuerto internacional La Aurora");
        seccion1.add(formatoSerie + "en miles de kilogramos");
        seccion1.add("1_02.tex");
        seccion1.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion1.add(true);
        cap1.add(seccion1);
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("1_03");
        seccion4.add("Aeropuerto La Aurora: vuelos nacionales");
        seccion4.add("Despegues de vuelos nacionales en aeropuerto internacional "
                + "La Aurora");
        seccion4.add(formatoSerie + "en unidades");
        seccion4.add("1_03.tex");
        seccion4.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion4.add(true);
        seccion4.add("1_04");
        seccion4.add("Aeropuerto La Aurora: vuelos internacionales");
        seccion4.add("Despegues de vuelos internacionales en aeropuerto "
                + "internacional La Aurora");
        seccion4.add(formatoSerie + "en unidades");
        seccion4.add("1_04.tex");
        seccion4.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion4.add(true);
        cap1.add(seccion4);
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("1_05");
        seccion5.add("Aeropuerto La Aurora: pasajeros que arriban");
        seccion5.add("Pasajeros que arriban en el aeropuerto internacional La Aurora");
        seccion5.add(formatoSerie + "en miles de unidades");
        seccion5.add("1_05.tex");
        seccion5.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion5.add(true);
        seccion5.add("1_06");
        seccion5.add("Aeropuerto La Aurora: pasajeros que salen");
        seccion5.add("Pasajeros que salen del aeropuerto internacional La Aurora");
        seccion5.add(formatoSerie + "en miles de unidades");
        seccion5.add("1_06.tex");
        seccion5.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion5.add(true);
        cap1.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("1_07");
        seccion6.add("Aeropuerto Mundo Maya: pasajeros que arriban");
        seccion6.add("Pasajeros que arriban en el aeropuerto internacional Mundo Maya");
        seccion6.add(formatoSerie + "en unidades");
        seccion6.add("1_07.tex");
        seccion6.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion6.add(true);
        seccion6.add("1_08");
        seccion6.add("Aeropuerto Mundo Maya: pasajeros que salen");
        seccion6.add("Pasajeros que salen del aeropuerto internacional Mundo Maya");
        seccion6.add(formatoSerie + "en unidades");
        seccion6.add("1_08.tex");
        seccion6.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion6.add(true);
        cap1.add(seccion6);
        
        return cap1;        
        
    }
    protected ArrayList cargarCapitulo2(){
        ArrayList cap2 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("2_01");
        seccion1.add("Envío de correspondencia vía aérea");
        seccion1.add("Envío de correspondencia por vía aérea");
        seccion1.add(formatoSerie + "en unidades");
        seccion1.add("2_01.tex");
        seccion1.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion1.add(true);
        seccion1.add("2_02");
        seccion1.add("Peso de la correspondencia vía aérea");
        seccion1.add("Peso de la correspondencia enviada por vía aérea");
        seccion1.add(formatoSerie + "en kilogramos");
        seccion1.add("2_02.tex");
        seccion1.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion1.add(true);
        cap2.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("2_03");
        seccion2.add("Principales países de destino de correspondencia");
        seccion2.add("Principales países de destino de la correspondencia enviada desde Guatemala");
        seccion2.add(formatoTrimestre + "en unidades");
        seccion2.add("2_03.tex");
        seccion2.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion2.add(true);
        seccion2.add("2_04");
        seccion2.add("Envío de encomiendas vía aérea");
        seccion2.add("Cantidad de encomiendas enviadas por vía aérea");
        seccion2.add(formatoSerie + "en unidades");
        seccion2.add("2_04.tex");
        seccion2.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion2.add(true);
        
        
        cap2.add(seccion2);
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("2_05");
        seccion3.add("Peso de las encomiendas vía aérea");
        seccion3.add("Peso total de las encomiendas enviadas por vía aérea");
        seccion3.add(formatoSerie + "en kilogramos");
        seccion3.add("2_05.tex");
        seccion3.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion3.add(true);
        seccion3.add("2_06");
        seccion3.add("Principales destinos de las encomiendas");
        seccion3.add("Envío de encomiendas según los principales países de destino");
        seccion3.add(formatoTrimestre + "en unidades");
        seccion3.add("2_06.tex");
        seccion3.add("INE, con datos de Dirección General de Correos y Telégrafos");
        
        
        cap2.add(seccion3);
        
        return cap2;        
    }
    
    
    
    protected ArrayList cargarCapitulo3(){
        ArrayList cap3 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("3_01");
        seccion1.add("Buques");
        seccion1.add("Buques que arribaron a Guatemala");
        seccion1.add(formatoSerie + "en unidades");
        seccion1.add("3_01.tex");
        seccion1.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion1.add(true);
        seccion1.add("3_02");
        seccion1.add("Carga total exportada");
        seccion1.add("Exportaciones en el sistema portuario nacional");
        seccion1.add(formatoSerie + "en miles de toneladas métricas");
        seccion1.add("3_02.tex");
        seccion1.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion1.add(true);
        cap3.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("3_03");
        seccion2.add("Carga total importada");
        seccion2.add("Importaciones en el sistema portuario nacional");
        seccion2.add(formatoSerie + "en miles de toneladas métricas");
        seccion2.add("3_03.tex");
        seccion2.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion2.add(true);
        seccion2.add("3_04");
        seccion2.add("Importaciones y exportaciones según puerto");
        seccion2.add("Distribuciones porcentuales de la carga exxportada e "
                + "importada que pasa a través del sistema portuario "
                + "nacional, según puerto");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("3_04.tex");
        seccion2.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion2.add(true);
        
        
        cap3.add(seccion2);
        
        
       
        ArrayList seccion5 = new ArrayList();
        seccion5.add("3_05");
        seccion5.add("Puerto Santo Tomás de Castilla: buques");
        seccion5.add("Buques que arribanron al puerto Santo Tomás de Castilla");
        seccion5.add(formatoSerie + "en unidades");
        seccion5.add("3_05.tex");
        seccion5.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion5.add(true);
        seccion5.add("3_06");
        seccion5.add("Puerto Santo Tomás de Castilla: carga exportada");
        seccion5.add("Exportaciones en el puerto Santo Tomás de Castilla");
        seccion5.add(formatoSerie + "en miles de toneladas métricas");
        seccion5.add("3_06.tex");
        seccion5.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion5.add(true);
        cap3.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("3_07");
        seccion6.add("Puerto Santo Tomás de Castilla: carga importada");
        seccion6.add("Importaciones a través del puerto Santo Tomás de Castilla");
        seccion6.add(formatoSerie + "en miles de toneladas métricas");
        seccion6.add("3_07.tex");
        seccion6.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion6.add(true);
        seccion6.add("3_08");
        seccion6.add("Puerto Barrios: Buques");
        seccion6.add("Buques que arribaron a Puerto Barrios");
        seccion6.add(formatoSerie + "en unidades");
        seccion6.add("3_08.tex");
        seccion6.add("INE, con datos del BANGUAT");
        seccion6.add(true);
        cap3.add(seccion6);
        
        
        ArrayList seccion7 = new ArrayList();
        seccion7.add("3_09");
        seccion7.add("Puerto Barrios: carga exportada");
        seccion7.add("Exportaciones en Puerto Barrios");
        seccion7.add(formatoSerie + "en miles de toneladas métricas");
        seccion7.add("3_09.tex");
        seccion7.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion7.add(true);
        seccion7.add("3_10");
        seccion7.add("Puerto Barrios: carga importada");
        seccion7.add("Importaciones a través de Puerto Barrios");
        seccion7.add(formatoSerie + "en miles de toneladas métricas");
        seccion7.add("3_10.tex");
        seccion7.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion7.add(true);
        cap3.add(seccion7);
        
        ArrayList seccion8 = new ArrayList();
        seccion8.add("3_11");
        seccion8.add("Puerto San José: buques");
        seccion8.add("Buques que arribaron al puerto San José");
        seccion8.add(formatoSerie + "en unidades");
        seccion8.add("3_11.tex");
        seccion8.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion8.add(true);
        seccion8.add("3_12");
        seccion8.add("Puerto San José: carga exportada");
        seccion8.add("Exportaciones en el puerto San José");
        seccion8.add(formatoSerie + "en miles de toneladas métricas");
        seccion8.add("3_12.tex");
        seccion8.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion8.add(true);
        cap3.add(seccion8);
        
        
        
        
        ArrayList seccion9 = new ArrayList();
        seccion9.add("3_13");
        seccion9.add("Puerto San José: carga importada");
        seccion9.add("Importaciones a través de puerto San José");
        seccion9.add(formatoSerie + "en miles de toneladas métricas");
        seccion9.add("3_13.tex");
        seccion9.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion9.add(true);
        seccion9.add("3_14");
        seccion9.add("Puerto Quetzal: buques");
        seccion9.add("Buques que arribaron al Puerto Quetzal");
        seccion9.add(formatoSerie + "en unidades");
        seccion9.add("3_14.tex");
        seccion9.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion9.add(true);
        cap3.add(seccion9);
        
        
        
        
        ArrayList seccion10 = new ArrayList();
        seccion10.add("3_15");
        seccion10.add("Puerto Quetzal: carga exportada");
        seccion10.add("Exportacoines en el Puerto Quetzal");
        seccion10.add(formatoSerie+ "en miles de toneladas métricas");
        seccion10.add("3_15.tex");
        seccion10.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion10.add(true);
        seccion10.add("3_16");
        seccion10.add("Puerto Quetzal: carga importada");
        seccion10.add("Importaciones a través de Puerto Quetzal");
        seccion10.add(formatoSerie + "en miles de toneladas métricas");
        seccion10.add("3_16.tex");
        seccion10.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion10.add(true);
        cap3.add(seccion10);
        
        
        return cap3;        
    }
    
    
    protected ArrayList cargarCapitulo4(){
        ArrayList cap4 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("4_01");
        seccion1.add("Parque vehicular");
        seccion1.add("Parque vehicular");
        seccion1.add(formatoSerie);
        seccion1.add("4_01.tex");
        seccion1.add("INE, con datos proporcionados por la Superintendencia de Administración Tributaria");
        seccion1.add(true);
        seccion1.add("4_02");
        seccion1.add("Parque vehicular por modelo");
        seccion1.add("Distribución porcentual del parque vehicular por modelo");
        seccion1.add(getFormatoTrimestre());
        seccion1.add("4_02.tex");
        seccion1.add("INE, con datos proporcionados por la Superintendencia de Administración Tributaria");
        seccion1.add(true);
        cap4.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("4_03");
        seccion2.add("Parque vehicular por departamento");
        seccion2.add("Distribución porcuentual del parque vehicular por departamento");
        seccion2.add(getFormatoTrimestre());
        seccion2.add("4_03.tex");
        seccion2.add("INE, con datos proporcionados por la Superintendencia de Administración Tributaria");
        seccion2.add(true);
        seccion2.add("4_04");
        seccion2.add("Parque vehicular por tipo");
        seccion2.add("Distribución porcentual del parque vehicular según tipo de vehículo");
        seccion2.add(getFormatoTrimestre());
        seccion2.add("4_04.tex");
        seccion2.add("INE, con datos proporcionados por la Superintendencia de Administración Tributaria");
        seccion2.add(true);
        cap4.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("4_05");
        seccion3.add("Parque vehicular por tipo de combustible");
        seccion3.add("Porcentaje de parque vehicular por tipo de combustible");
        seccion3.add(getFormatoTrimestre());
        seccion3.add("4_05.tex");
        seccion3.add("INE, con datos proporcionados por la Superintendencia de Administración Tributaria");
        seccion3.add(true);
        seccion3.add("4_06");
        seccion3.add("Importación de vehículos nuevos");
        seccion3.add("Importación de vehículos nuevos");
        seccion3.add(formatoSerie);
        seccion3.add("4_06.tex");
        seccion3.add("INE, con datos proporcionados por la Superintendencia de Administración Tributaria");
        seccion3.add(true);
        cap4.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("4_07");
        seccion4.add("Importacion de vehículos usados");
        seccion4.add("Importación de vehículos usados");
        seccion4.add(formatoSerie);
        seccion4.add("4_07.tex");
        seccion4.add("INE, con datos proporcionados por la Superintendencia de Administración Tributaria");
        seccion4.add(true);
        cap4.add(seccion4);
        return cap4;        
    }        
    
    protected void generarGraficas(String modalidad){
        System.out.println("GENERANDO LAS GRAFICAS");
        Grafica vitales = new Grafica("transporte", getRuta(), rr.get(), modalidad);
        vitales.start();
    }
    
    protected void hacerPortada(){
       File source = new File("/home/ineservidor/Transportes/Caratula");
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
            "\\node[inner sep =0, rotate = 90]at(0.908,0.15){Guatemala, "+ getMesServidor()  +" de " +  getYearServidor()+"};\n "
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
                "\\indent El Instituto Nacional de Estadística -INE-, a través de la Unidad de Transportes "
                + "y Servicios, elaboró la edición del {\\Bold " + corregirTrimestre(getTrimestre()).toLowerCase() + " trimestre del "
                + getAnioPublicacion() +"} sobre {\\Bold Estadísticas de Transportes y Servicios}, que contiene "
                + "los capítulos de transporte aéreo, tráfico postal, movimiento marítimo "
                + "y paque vehícular. \n" +
                "\n" +
                "El documento contiene la información que proporciona la Dirección de Aeronáutica "
                + "Civil, Dirección General de Correos y Telégrafos, Comisión Portuaria Nacional y "
                + "Superintendecia de Administración Tributaria. {\\Bold Los datos presentados son "
                + "preliminares} y serán ajustados con el ingreso de los registros tardíos.\n" +
                "\n" +
                "El INE agradece el apoyo brindado por las fuentes de información, esperando "
                + "que esta nueva edición sea de utilidad tanto a investigadores y público en "
                + "general como para la elaboración de programas, políticas, planes en materia "
                + "de servicios y transportes para el país.\n" +
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

