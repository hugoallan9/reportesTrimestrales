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
public class VIF extends Documento{
    private List capitulos;
    private List introCapitulos;
    private List contenidos;
    private String formatoSerie;
    Collator comparador = Collator.getInstance();
    private SesionR rr;
    private String rutaCSV;    
    private String formatoTrim;
    public SesionR getRr() {
        return rr;
    }
    
    
    
    
    public VIF(String titulo, String trimestre, String pYear, String rutaCSV) {
        super(titulo, trimestre, pYear);
        capitulos = new ArrayList();
        introCapitulos = new ArrayList();
        contenidos = new ArrayList();
        this.rutaCSV = rutaCSV;
        rr = new SesionR();
        comparador.setStrength(Collator.PRIMARY);
        formatoSerie = "Serie histórica " +  (int)(Double.parseDouble(getAnioPublicacion()) -2) + "-" + getAnioPublicacion();
        formatoTrim = corregirTrimestre(trimestre) + " trimestre del "+ getAnioPublicacion();
                
        cargarCSV(rutaCSV);
        setCapitulos();
        setIntroCapitulos();
        setContenidos();
    }
    
    protected void setCapitulos(){
        capitulos.add("Hechos registrados, ");
        capitulos.add("Características de las,mujeres víctimas");
        capitulos.add("Características de los,hombres víctimas");
        capitulos.add("Características de los,hombres agresores");
        capitulos.add("Características de las,mujeres agresoras");
        
    }
    
    protected void setIntroCapitulos(){
        introCapitulos.add("Corresponde a los hechos registrados a través de "
                + "la boleta única de registro estadístico de violencia intrafamiliar, "
                + "recopilada de las seis instituciones nombradas en la Ley (Decreto "
                + "97-96).");
        introCapitulos.add("En este apartado se encuentran las mujeres que fueron "
                + "víctimas de violencia intrafamiliar y que en general se puede "
                + "decir que aproximadamente en 9 de cada 10 denuncias de violencia "
                + "intrafamiliar, la víctima es mujer.");
        introCapitulos.add("En este apartado se encuentran la información de los "
                + "hombres que denunciaron haber sido víctimas de algún pariente y "
                + "por algún tipo de violencia. Como dato general se puede decir "
                + "que históricamente, de cada 10 denuncias por violencia intrafamiliar, "
                + "en 1 de ellas la víctima es hombre.");
        introCapitulos.add("Respecto a los agresores, la información estadística "
                + "muestra que de cada 10 agresores 9 son hombres. Por lo que es "
                + "importante caracterizar este tipo de agresores.");
        introCapitulos.add("En relación a las mujeres agresoras, en este espacio se "
                + "observa la información sobre características como edad, nivel "
                + "de escolaridad, empleo, etc. que identifican claramente a este "
                + "grupo.");
    }
    protected void setContenidos(){
         contenidos.add(cargarCapitulo1());
         contenidos.add(cargarCapitulo2());
         contenidos.add(cargarCapitulo3());
         contenidos.add(cargarCapitulo4());
         contenidos.add(cargarCapitulo5());
         System.out.println("cargados los contenidos");
    }
    
    protected void rellenar(){
        int hoja =1;
        int finContenido = contenidos.size();
        for (int i=0; i<finContenido;i++){
            ArrayList tmpSeccion = (ArrayList)contenidos.get(i);
            String[] tmpTitle =capitulos.get(i).toString().split(",");
            System.out.println(tmpTitle[0]);
            System.out.println(tmpTitle[1]);
            escribirCapitulo(tmpTitle[0] +" " + tmpTitle[1], tmpTitle[0]
                ,tmpTitle[1], introCapitulos.get(i).toString());
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
                REXP listadoCSV = rr.get().eval("vif <- cargaMasiva('" +  ruta +"')");
                REXP nombres = rr.get().eval("names(vif)");
                System.out.println(listadoCSV);
                System.out.println(nombres);
            }
    }
    
    
    protected ArrayList cargarCapitulo1(){
        ArrayList cap1 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("1_01");
        seccion1.add("Hechos de violencia intrafamiliar");
        seccion1.add("Casos de violencia intrafamiliar registrados");
        seccion1.add(formatoSerie);
        seccion1.add("1_01.tex");
        seccion1.add("INE");
        seccion1.add(true);
        seccion1.add("1_02");
        seccion1.add("Denuncias por departamento de ocurrencia");
        seccion1.add("Número de denuncias por departamento de ocurrencia");
        seccion1.add(getFormatoSubtituloG());
        seccion1.add("1_02.tex");
        seccion1.add("INE");
        seccion1.add(true);
        cap1.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("1_03");
        seccion2.add("Tipo de violencia");
        seccion2.add("Distribución de denuncias por tipo de violencia");
        seccion2.add(getFormatoSubtituloG());
        seccion2.add("1_03.tex");
        seccion2.add("INE");
        seccion2.add(true);
        seccion2.add("1_04");
        seccion2.add("Institución que recibió la denuncia");
        seccion2.add("Distribución porcentual de denuncias de violencia intrafamiliar "
                + "según la institución que recibió la denuncia");
        seccion2.add(getFormatoSubtituloG());
        seccion2.add("1_04.tex");
        seccion2.add("INE");
        seccion2.add(true);
        cap1.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("1_05");
        seccion3.add("Denuncias por ley aplicada");
        seccion3.add("Distribución porcentual de denuncias de violencia intrafamiliar "
                + "reportadas por el Organismo Judicial, por ley aplicada");
        seccion3.add(getFormatoSubtituloG());
        seccion3.add("1_05.tex");
        seccion3.add("INE");
        seccion3.add(true);
        cap1.add(seccion3);
        return cap1;        
        
    }
    protected ArrayList cargarCapitulo2(){
        ArrayList cap2 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("2_01");
        seccion1.add("Mujeres víctimas");
        seccion1.add("Porcentaje de denuncias de mujeres víctimas de violencia intrafamiliar");
        seccion1.add(formatoSerie);
        seccion1.add("2_01.tex");
        seccion1.add("INE");
        seccion1.add(true);
        seccion1.add("2_02");
        seccion1.add("Mujeres víctimas según pueblo de pertenencia");
        seccion1.add("Porcentaje de mujeres víctimas de violencia intrafamiliar por pueblo de "
                + "pertenencia");
        seccion1.add(getFormatoSubtituloG());
        seccion1.add("2_02.tex");
        seccion1.add("INE");
        seccion1.add(true);
        cap2.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("2_03");
        seccion2.add("Mujeres víctimas por grupos de edad");
        seccion2.add("Porcentaje de mujeres víctimas de violencia intrafamiliar, por grupos de edad");
        seccion2.add(getFormatoSubtituloG());
        seccion2.add("2_03.tex");
        seccion2.add("INE");
        seccion2.add(true);
        seccion2.add("2_04");
        seccion2.add("Mujeres víctimas por condición de empleo");
        seccion2.add("Distribución de mujeres víctimas de violencia intrafamiliar de 7 años y más, "
                + "según condición de empleo");
        seccion2.add(getFormatoSubtituloG());
        seccion2.add("2_04.tex");
        seccion2.add("INE");
        seccion2.add(true);
        cap2.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("2_05");
        seccion3.add("Mujeres víctimas por condición de alfabetismo");
        seccion3.add("Distribución porcentual de mujeres vícitmas de violencia intrafamiliar, "
                + "según condición de alfabetismo");
        seccion3.add(getFormatoSubtituloG());
        seccion3.add("2_05.tex");
        seccion3.add("INE");
        seccion3.add(true);
        seccion3.add("2_06");
        seccion3.add("Mujeres víctimas por tipo de agresión sufrida");
        seccion3.add("Distribución de mujeres víctimas de violencia intrafamiliar, por"
                + "tipo de agresión sufrida");
        seccion3.add(getFormatoSubtituloG());
        seccion3.add("2_06.tex");
        seccion3.add("INE");
        seccion3.add(true);
        cap2.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("2_07");
        seccion4.add("Mujeres víctimas según su relación con el agresor(a)");
        seccion4.add("Distribución porcentual de mujeres víctimas de violencia intrafamiliar, "
                + "por relación con el agresor(a)");
        seccion4.add(getFormatoSubtituloG());
        seccion4.add("2_07.tex");
        seccion4.add("INE");
        seccion4.add(true);
        cap2.add(seccion4);
               
        return cap2;        
    }
    
    
    
    protected ArrayList cargarCapitulo3(){
        ArrayList cap3 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("3_01");
        seccion1.add("Hombres víctimas por grupos de edad");
        seccion1.add("Distribucion de hombres victimas de violencia intrafamiliar segun grupos "
                + "de edad");
        seccion1.add(getFormatoSubtituloG());
        seccion1.add("3_01.tex");
        seccion1.add("INE");
        seccion1.add(true);
        seccion1.add("3_02");
        seccion1.add("Hombres víctimas de 60 años o más, por relación con el agresor(a)");
        seccion1.add("Distribución de hombres víctimas de 60 años y más, por relación con "
                + "el agresor(a)");
        seccion1.add(getFormatoSubtituloG());
        seccion1.add("3_02.tex");
        seccion1.add("INE");
        seccion1.add(true);
        cap3.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("3_03");
        seccion2.add("Hombres víctimas y su relación con el agresor(a)");
        seccion2.add("Porcentaje de hombres víctimas de violencia intrafamiliar según su "
                + "relación con el agresor(a)");
        seccion2.add(getFormatoSubtituloG());
        seccion2.add("3_03.tex");
        seccion2.add("INE");
        seccion2.add(true);
        seccion2.add("3_04");
        seccion2.add("Hombres víctimas por condición de alfabetismo");
        seccion2.add("Distribución de hombres víctimas de violencia intrafamiliar, por"
                + "condición de alfabetismo");
        seccion2.add(getFormatoSubtituloG());
        seccion2.add("3_04.tex");
        seccion2.add("INE");
        seccion2.add(true);
        cap3.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("3_05");
        seccion3.add("Hombres víctimas por condición de empleo");
        seccion3.add("Distribución de hombres víctimas de violencia intrafamiliar por condición "
                + "de empleo");
        seccion3.add(getFormatoSubtituloG());
        seccion3.add("3_05.tex");
        seccion3.add("INE");
        seccion3.add(true);
        seccion3.add("3_06");
        seccion3.add("Hombres víctimas por tipo de agresión sufrida");
        seccion3.add("Porcentaje de hombres víctimas de violencia intrafamiliar, por tipo de "
                + "agresión sufrida");
        seccion3.add(getFormatoSubtituloG());
        seccion3.add("3_06.tex");
        seccion3.add("INE");
        seccion3.add(true);
        cap3.add(seccion3);
        
        return cap3;        
    }
    
    
    protected ArrayList cargarCapitulo4(){
        ArrayList cap4 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("4_01");
        seccion1.add("Hombres Agresores");
        seccion1.add("Porcentaje de denuncias de violencia intrafamiliar donde el agresor es "
                + "hombre, según trimestre de registro");
        seccion1.add(formatoSerie);
        seccion1.add("4_01.tex");
        seccion1.add("INE");
        seccion1.add(true);
        seccion1.add("4_02");
        seccion1.add("Hombres agresores según grupos de edad");
        seccion1.add("Distribución porcentual de hombres agresores de violencia intrafamiliar "
                + "por grupos de edad");
        seccion1.add(getFormatoSubtituloG());
        seccion1.add("4_02.tex");
        seccion1.add("INE");
        seccion1.add(true);
        cap4.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("4_03");
        seccion2.add("Hombres agresores según el nivel educativo");
        seccion2.add("Porcentaje de hombres agresores de violencia intrafamiliar, según "
                + "el nivel educativo");
        seccion2.add(getFormatoSubtituloG());
        seccion2.add("4_03.tex");
        seccion2.add("INE");
        seccion2.add(true);
        seccion2.add("4_04");
        seccion2.add("Hombres agresores por condición de empleo");
        seccion2.add("Distribución porcentual de hombres agresores de violencia intrafamilar "
                + "por condición de empleo");
        seccion2.add(getFormatoSubtituloG());
        seccion2.add("4_04.tex");
        seccion2.add("INE");
        seccion2.add(true);
        cap4.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("4_05");
        seccion3.add("Hombres agresores por pueblo de pertenencia");
        seccion3.add("Distribucion porcentual de hombres agresores de violencia intrafamiliar "
                + "por pueblo de pertenencia");
        seccion3.add(getFormatoSubtituloG());
        seccion3.add("4_05.tex");
        seccion3.add("INE");
        seccion3.add(true);
        seccion3.add("4_06");
        seccion3.add("Hombres agresores por tipo de agresión");
        seccion3.add("Distribución porcentual de hombres agresores de violencia intrafamiliar, "
                + "por tipo de agresión ejercida");
        seccion3.add(getFormatoSubtituloG());
        seccion3.add("4_06.tex");
        seccion3.add("INE");
        seccion3.add(true);
        cap4.add(seccion3);
        
        return cap4;        
    }
    protected ArrayList cargarCapitulo5(){
        ArrayList cap5 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("5_01");
        seccion1.add("Mujeres agresoras por grupos de edad");
        seccion1.add("Distribución porcentual de mujeres agresoras de violencia "
                + "intrafamiliar por grupos de edad");
        seccion1.add(getFormatoSubtituloG());
        seccion1.add("5_01.tex");
        seccion1.add("INE");
        seccion1.add(true);
        seccion1.add("5_02");
        seccion1.add("Mujeres agresoras según nivel de escolaridad");
        seccion1.add("Distribución porcentual de mujeres agresoras de violencia intrafamiliar "
                + "por nivel de escolaridad");
        seccion1.add(getFormatoSubtituloG());
        seccion1.add("5_02.tex");
        seccion1.add("INE");
        seccion1.add(true);
        cap5.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("5_03");
        seccion2.add("Mujeres agresoras por condición de empleo");
        seccion2.add("Porcentaje de mujeres agresoras de violencia intrafamiliar, por "
                + "condición de empleo");
        seccion2.add(getFormatoSubtituloG());
        seccion2.add("5_03.tex");
        seccion2.add("INE");
        seccion2.add(true);
        seccion2.add("5_04");
        seccion2.add("Mujeres agresoras por pueblo de pertenencia");
        seccion2.add("Distribución porcentual de mujeres agresoras de violencia intrafamiliar "
                + "por pueblo de pertenencia");
        seccion2.add(getFormatoSubtituloG());
        seccion2.add("5_04.tex");
        seccion2.add("INE");
        seccion2.add(true);
        cap5.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("5_05");
        seccion3.add("Mujeres agresoras por tipo de agresión");
        seccion3.add("Distribucion porcentual de mujeres agresoras de violencia intrafamiliar "
                + "por tipo de agresión ejercida");
        seccion3.add(getFormatoSubtituloG());
        seccion3.add("5_05.tex");
        seccion3.add("INE");
        seccion3.add(true);
        cap5.add(seccion3);
        
        return cap5;        
    }
        protected void apendices(String rutaTEX){
        escribirLinea("\n \\appendixa \n" +
        "\n" +
        "\n" +
        "\n" );
        apendice1();
        apendice2();
        Tabla ap = new Tabla(rutaTEX,trimestres(),rr);
        ap.setRuta("/var/www/html/Violencia/Entradas/CSV");
        ap.generarVIF();
        
        
        
    }
    private void apendice1(){
        String columna1 = tablaApendice("A_01",
                "Análisis de variación de la cantidad de denuncias",
                "2",
                "plantillaTabla2.pdf",
                "INE",
                "");
        
        String columna2 = tablaApendice("A_02",
                "Análisis de variación de mujeres víctimas",
                "2",
                "plantillaTabla2.pdf",
                "INE",
                "");
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    private void apendice2(){
        String columna1 = tablaApendice("A_03",
                "Análisis de variación de hombres víctimas",
                "2",
                "plantillaTabla2.pdf",
                "INE",
                "");
        
        String columna2 = tablaApendice("A_04",
                "Análisis de variación de hombres agresores",
                "2",
                "plantillaTabla2.pdf",
                "INE",
                "");
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    protected void generarGraficas(String modalidad){
        System.out.println("GENERANDO LAS GRAFICAS");
        Grafica vif = new Grafica("vif", getRuta(), rr.get(), modalidad);
        vif.start();
    }
    
    protected void hacerPortada(){
        File source = new File("/home/ineservidor/Violencia/Caratula");
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
            + "\\node[inner sep =0, scale = 3.5, align = left] at (0.54,0.595) {\n" +
             "República de Guatemala \n" +
            "		\\\\\n" +
            getTitulo() + "\n" +
            "		\\\\\n" +
            corregirTrimestre( getTrimestre() ) +  " trimestre "  + getAnioPublicacion() + " };" +
            "\\node[inner sep =0, rotate = 90]at(0.908,0.15){Guatemala, "+ getMesServidor()+" de " +  getYearServidor()+"};\n "
            + "\\node[inner sep = 0, rotate = 90]at(0.18, 0.39) {\\textcolor{gray}{Cifras Preliminares}}; \n" +
            " \\node (inelogo) at (0.3,0.85){\\includegraphics[scale=0.38]{logoINE.pdf}}; \n"+
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
                "	Anabella De La Cruz Rodríguez\\\\\n" +
                "	José Alberto Flores Gallo\\\\[0.8cm]\n" +
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
                "\\indent El Instituto Nacional de Estadística -INE-, en cumplimiento de sus funciones "
                + "como órgano central de información de divulgar datos estadísticos de los delitos "
                + "contemplados en la Ley para prevenir, sancionar y erradicar la violencia "
                + "intrafamiliar, emite el presente informe de {\\Bold Estadísticas de Violencia "
                + "Intrafamiliar} que corresponde al período del "
                + " {\\Bold " + corregirTrimestre(getTrimestre()).toLowerCase() + " trimestre del "
                + getAnioPublicacion() +"}.\n" +
                "\n" +
                "El documento incluye datos preliminares del perído , "
                + "sobre los hechos de violencia intrafamiliar registrados, "
                + "las características de las mujeres y los hombres víctimas, "
                + "las características de las mujeres y los hombres agresores, "
                + "así como cuadros estadísticos donde se analiza la variación "
                + "entre períodos de estos hechos.\n"+
                "\n" +
                "Las fuentes para el presente son: Ministeripo Público (MP), "
                + "Procuraduría General de la Nación (PGN), Policía Nacional Civil "
                + "(PNC), Organismo Judicial a través de los juzgados de Paz y "
                + "Familia (OJ), Bufetes Populares (BP) y la Procuraduría de los "
                + "Derechos Humanos (PDH). \n"+
                "\n" +
                "Con esta publicación el instituto persigue contribuir significativamente "
                + "a las crecientes demandas de información de las y los usuarios de "
                + "estadísticas de violencia intrafamiliar.\n"+
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

