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
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.text.Collator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
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
    private String formatoSerie;
    Collator comparador = Collator.getInstance();
        

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
        comparador.setStrength(Collator.PRIMARY);
        formatoSerie = "Serie histórica " + (Double.parseDouble(getAnioPublicacion()) -2) + "-" + getAnioPublicacion();
                
                
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
        // ALGUNAS VARIABLES UTILES
        //INTRODUCCION
        escribirCapitulo(capitulos.get(0).toString(), capitulos.get(0).toString()
                ," ", introCapitulos.get(0).toString());
        
        //PRIMERA HOJA
        section1_01();
        
        //SEGUNDA HOJA
        section1_02();
        
        //TERCERA HOJA
        section1_03();
        
        //CUARTA HOJA
        
        section1_04();
        
        //QUINTA HOJA
        
        section1_05();
        
        

    }
    
    protected void section1_01(){
          escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA 1%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
          String columna1 = columna("Nacimientos","El número de nacimientos es una de  las variable en el "
                + "estudio de los fenómenos demográficos que, utilizado "
                + "conjuntamente con la estadística de defunciones y matrimonios"
                + ", ayuda a determinar la estructura y dinámica de población, "
                + "con la incorporación de individuos a la población.", "Durante el " + 
                simboloToTrimestre(rr.get().eval("vitales$'1_01'$x[1]").asString().substring(0,2)) +
                " trimestre del " + getAnioPublicacion()+  " se registraron "
                +  getDf().format(rr.get().eval("vitales$'1_01'$y[9]").asDouble())  + " nacimientos, es decir,  "
                + rr.get().eval("iconv(calcularRespuestaPor(vitales$'1_01'), 'utf8')").asString()
                +" el mismo período "
                + "del año anterior y "+ getDf().format(rr.get().eval("cambioInterAnual(vitales$'1_01', paso = 1)").asDouble())
                + "\\% menos de lo registrado en el trimestre anterior. ",
                "Nacimientos por trimestre", 
                "Serie histórica " + rr.get().eval("vitales$'1_01'$x[1]").asString().substring(3) + "-" +rr.get().eval("vitales$'1_01'$x[9]").asString().substring(3)
                , "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_01.tex}  \\end{tikzpicture}","INE, con datos del RENAP", "");
        
        rr.get().eval("temp <- vitales$'1_02'[ordenarNiveles(vitales$'1_02',T),]");
        String columna2 = columna("Nacimientos por departamento", "La cantidad "
                + "de nacimientos por departamento permite identificar la dinámica "
                + "poblacional a nivel interno en el país.", "La gráfica indica que la mayoría"
                        + " de madres residen en el  "
                        + "departamento de  " + rr.get().eval("temp$x[1]").asString() + 
                        " con un " + getDf().format(rr.get().eval("temp$y[1]/sum(temp$y) *100").asDouble()) + 
                        "\\%," + " seguido de " + rr.get().eval("temp$x[2]").asString() +
                        " con el "+ getDf().format(rr.get().eval("temp$y[2]/sum(temp$y) *100").asDouble())+
                        "\\% y " + rr.get().eval("temp$x[3]").asString() +
                        " con " + getDf().format(rr.get().eval("temp$y[3]/sum(temp$y) * 100").asDouble())+
                        "\\%." + " Los departamentos con menor proporción son "
                        + rr.get().eval("temp$x[length(temp$x)]").asString() +  " con " +
                        getDf().format(rr.get().eval("temp$y[length(temp$x)]/sum(temp$y) *100").asDouble()) + "\\% y "+
                        rr.get().eval("temp$x[length(temp$x)-1]").asString() + " con " + 
                        getDf().format(rr.get().eval("temp$y[length(temp$x)-1]/sum(temp$y) *100").asDouble()) + "\\%."
                + " ", "Número de nacimientos por departamento de residencia de la madre"
                , getFormatoSubtituloG()
                ,"\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_02.tex}  "
                        + "\\end{tikzpicture}" , "INE, con datos del RENAP", "");
               
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    private void section1_02(){
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA 2%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        rr.get().eval("temp <- vitales$'1_03'[ordenarNiveles(vitales$'1_03',T),]");
        String columna1 = columna("Nacimientos por edad de la madre", 
                "La desagregación por grupos de edad de la madre, permite "
                + "identificar el tipo de fecundidad prevaleciente; "
                + "la fecundidad es temprana si la mayoría de nacimientos "
                + "ocurre entre los 20 y 24 años, tardía si es en el grupo "
                + "de 25 a 29, y dilatada si no hay diferencia "
                + "significativa entre los dos grupos.", "La gráfica "
                + "de distribución de nacimientos por grupo de edad de la madre,"
                + " muestra que el " + 
                getDf().format(rr.get().eval("temp$y[1]").asDouble())+"\\% de los"
                + " casos, la madre estaba comprendida entre las edades de "
                + rr.get().eval("temp$x[1]").asString() +" años, "
                + "seguido del grupo de población de madres de " + rr.get().eval("temp$x[2]").asString() +" años "
                + " con un " + getDf().format(rr.get().eval("temp$y[2]").asDouble()) + "\\%."
                + " En el "+ getFormatoTrimestre() + " se registraron " + 
                ((rr.get().eval("vitales$'1_03'$y[1]").asDouble() > rr.get().eval("vitales$'1_03'$y[4]").asDouble()) ? " más " : " menos " ) +  " nacimientos en mujeres de 15 a 19 años "
                + "que en mujeres de 30 a 34 años.", "Distribución de nacimientos "
                        + "por grupo de edad de la madre", corregirTrimestre(getTrimestre()) + " trimestre, año " + getAnioPublicacion()
                , "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_03.tex} \\end{tikzpicture}", "INE, "
                        + "con datos del RENAP", "");
        
        String columna2 = columna("Nacimientos en madres adolescentes", 
                "La desagregación de los nacimientos de las madres adolescentes\\footnote{Comprendidas entre los 10 y 19 años}"
                        + " por edad " +
                "simple muestra que, durante  el " + getFormatoTrimestre()  + ", " + getDf().format(rr.get().eval("sum(vitales$'1_04'$y[1:5])").asDouble()) +  
                " fueron por madres en la etapa de adolescencia temprana\\footnote{Adolescencia temprana: entre los"
                        + " 10 y los 14 años.}" +
                " y " + getDf().format(rr.get().eval("sum(vitales$'1_04'$y[6:10])").asDouble()) + " fueron por madres en la etapa" +
                " de adolescencia tardía\\footnote{Adolescencia tardía: entre los 15 y 19 años}.","Los nacimientos de madres adolecentes se consideran"
                + " de alto riesgo y conlleva mayores complicaciones "
                + "que los que se presentan en madres cuyas edades oscilan entre "
                + "los 20 y 40 años.","Número de nacimientos en madres menores de "
                + "20 años por edad simple",  corregirTrimestre(getTrimestre()) +
                " trimestre, año " + getAnioPublicacion()  , 
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_04.tex} \\end{tikzpicture}", 
                "INE, con datos del RENAP", "");
        
        
        escribirLinea(hojaTrimestral(columna1,columna2));
    }
    
    private void section1_03(){
         escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA 3%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        rr.get().eval("temp <- vitales$'1_05'[ordenarNiveles(vitales$'1_05',T),]");
        String columna1 = columna("Nacimientos por pueblo de pertenecia de la madre", 
                "En la distribución de nacimientos registrados en el " + getFormatoTrimestre()
                +", el " + getDf().format(rr.get().eval("temp$y[1]").asDouble())+ "\\% " +
                " de los nacimientos fueron por madres pertenecientes al pueblo " + 
                rr.get().eval("temp$x[1]").asString() + " y el  "+ getDf().format(rr.get().eval("temp$y[2]").asDouble()) +
                "\\% por madres del pueblo " + rr.get().eval("temp$x[2]").asString()+
                " . Se desconoce el pueblo de pertenencia de la madre en " +
                getDf().format(rr.get().eval("temp$y[length(temp$y)]").asDouble())+ "\\% de los casos.",
                "", "Distribución porcentual de nacimientos"
                + " por pueblo de pertenencia de la madre ", corregirTrimestre(getTrimestre()) + " trimestre, año " + getAnioPublicacion(), 
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_05.tex} \\end{tikzpicture}",
                "INE, con datos del RENAP", "");
        
        rr.get().eval("temp <- vitales$'1_06'[ordenarNiveles(vitales$'1_06',T),]");
        String columna2 = columna("Nacimientos según estado civil de la madre", 
                "El estado conyugal de las madres es una desagregación utilizada "
                + "para realizar análisis indirectos del comportamiento reproductivo"
                + " de una población.  En el caso de Guatemala se cuenta con la variable"
                + " estado civil, que permite desagregaciones de casadas, solteras y "
                + "uniones formales, no así las madres que están separadas, "
                + "divorciadas o en unión libre.", "La distribución de nacimientos"
                + " por estado civil de la madre, muestra que el " + 
                getDf().format(rr.get().eval("temp$y[1]").asDouble()) + "\\% corresponde a "
                +"madres " + rr.get().eval("temp$x[1]").asString().toLowerCase()+"s, "
                + getDf().format(rr.get().eval("temp$y[1]").asDouble()) + "\\% a madres " + 
                rr.get().eval("temp$x[2]").asString().toLowerCase()+"s, y " + 
                getDf().format(rr.get().eval("temp$y[3]").asDouble()) + "\\% a madres " + 
                rr.get().eval("temp$x[3]").asString().toLowerCase()+"s. En el " + 
                getDf2().format(rr.get().eval("temp$y[4]").asDouble()) + "\\% de los casos "
                + "se ignora el estado civil de la madre.", "Distribución porcentual "
                + "de nacimientos por estado civil de la madre",
                corregirTrimestre(getTrimestre()) + " trimestre, año " + 
                getAnioPublicacion(), "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_06.tex} \\end{tikzpicture}",
                "INE, con datos del RENAP", "");
        
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    private void section1_04(){
        compilar(rr, getRuta() + "/1_07.tex","F");
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA 4%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        String genero1 = "";
        String genero2 = "";
        Double masculinidad = 0.0;
        if(rr.get().eval("vitales$'1_07'$x[1]").asString().equalsIgnoreCase("hombre")){
            genero1 = "hombres";
            genero2= "mujeres";
            masculinidad = rr.get().eval("vitales$'1_07'$y[1]").asDouble() / rr.get().eval("vitales$'1_07'$y[2]").asDouble() * 100; 
        }else if(rr.get().eval("vitales$'1_07'$x[1]").asString().equalsIgnoreCase("mujer")){
            genero1 = "mujeres";
            genero2 = "hombres";
            masculinidad = rr.get().eval("vitales$'1_07'$y[2]").asDouble() / rr.get().eval("vitales$'1_07'$y[1]").asDouble() * 100; 
        }else{
            genero1 = rr.get().eval("vitales$'1_07'$x[1]").asString();
            genero2 = rr.get().eval("vitales$'1_07'$x[2]").asString();
            masculinidad = rr.get().eval("vitales$'1_07'$y[1]").asDouble() / rr.get().eval("vitales$'1_07'$y[2]").asDouble() * 100; 
        }
        String columna1 = columna("Nacimientos según sexo del recién nacido",
            "El análisis de los nacimientos según sexo permite determinar "
            + "el índice de masculinidad\\footnote{El índice de "
            + "masculinidad es el número  de hombres por cada cien "
            + "mujeres en la población. Se calcula en cuatro etapas:  "
            + "al nacer, población menor de 15 años, población entre 15 y "
            + "65, y población mayor a 65 años.} al nacer, este permite\n" +
            "identificar cambios en la distribución por sexos "
            + "de la población y facilita la lectura de género de" +
            " eventos relacionados con la salud y otros de naturaleza social y económica.", "En el " + 
            getFormatoTrimestre() +", el  "
            + getDf().format(rr.get().eval("vitales$'1_07'$y[1]").asDouble()) + "\\% de los nacidos fueron "+
            genero1 + " mientras que el " + 
            getDf().format(rr.get().eval("vitales$'1_07'$y[2]").asDouble()) + "\\% fueron " + 
            genero2 +", además el índice de masculinidad registrado fue de " + getDf().format(masculinidad) + "\\%.", "Distribución "
            + "porcentual de nacimientos por sexo del recién nacido",corregirTrimestre(getTrimestre()) 
            + " trimestre, año " + getAnioPublicacion(), "\\XeTeXpdffile \"1_07.pdf\" " 
            , "INE, con datos del RENAP", "");
        
        rr.get().eval("temp <- vitales$'1_08'[vitales$'1_08'$x != 'Peso adecuado',]");
        rr.get().eval("temp <- temp[temp$x != 'Ignorado',]");
        String columna2 = columna("Nacimientos según peso del recién nacido", 
                "El peso\\footnote{"
                + "El peso se divide en cuatro categorías: "
                + "\\begin{itemize} \n "
                + "\\item Adecuado: Se refiere a pesos  de 5.5 libras o más. \n "
                + "\\item Bajo: Comprende los pesos mayores a 3.3 libras pero menores a 5.5 libras. \n"
                + "\\item  Muy bajo: Peso mayor a 2.2 libras y menor a 3.3. \n"
                + "\\item Extremadamente bajo:  Comprende los pesos por debajo de las 2.2 libras. \n"
                + "\\end{itemize} }"
                + "variable usada para evaluar las probabilidades de supervivencia "
                + " del recién nacido en sus primeros días de vida, así como para "
                + "evaluar las condiciones de las madres en una población.", 
                "En el " + getFormatoTrimestre() + ", el " + 
                getDf().format(rr.get().eval("vitales$'1_08'[vitales$'1_08' == 'Peso adecuado',]$y").asDouble())
                + "\\% del total de nacimientos registraron un peso adecuado al nacer, mientras que en el  "+
                getDf().format(rr.get().eval("sum(temp$y)").asDouble()) + "\\%"
                +" de los casos los recién nacidos pesaron  menos de 5.5 libras.", 
                "Distribución porcentual de nacimientos por peso del recién nacido",
                getFormatoSubtituloG(), "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_08.tex}  \\end{tikzpicture}",
                "INE, con datos del RENAP", "");
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    private void section1_05(){
         escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA 5%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        String cambio = ( (rr.get().eval("vitales$'1_09'$y[9]").asDouble() > rr.get().eval("vitales$'1_09'$y[1]").asDouble()) ?
                        "aumentado":"disminuido" );
        System.out.println(cambio);
        String columna1 = columna("Nacimientos con bajo peso al nacer",
                "Un bajo peso al nacer predispone al recién nacido a complicaciones "
                + "de salud en los primeros días de vida.", 
                "Se puede observar que en los últimos dos años, el porcentaje de nacimientos "+
                "registrados con bajo peso al nacer, ha variado entre " + 
                getDf().format(rr.get().eval("vitales$'1_09'$y[1]").asDouble()) + "\\%  y "+
                getDf().format(rr.get().eval("vitales$'1_09'$y[9]").asDouble()) + "\\%. A partir del "
                + simboloToTrimestre(rr.get().eval("substr(vitales$'1_09'$x[1],1,2)").asString()) 
                + " trimestre de "+
                Math.round(( Double.parseDouble(getAnioPublicacion())-2)) +  " el porcentaje ha " + cambio+
                 "en " +  getDf().format(rr.get().eval("vitales$'1_09'$y[9] - vitales$'1_09'$y[1]").asDouble()) + 
                " puntos.",
                "Porcentaje de nacimientos con bajo peso al nacer", 
                formatoSerie ,"\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_09.tex}  \\end{tikzpicture}" 
                ,"INE, con datos del RENAP", "");
        
        
        rr.get().eval("temp <- vitales$'1_10'[ordenarNiveles(vitales$'1_10'),]");
        String columna2 = columna("Nacimientos según asistencia recibida durante el parto",
                "Una adecuada atención al momento del nacimiento garantiza la salud"
                + " materna e infantil de una población.", "Del total de nacimientos registrados durante " +
                getFormatoTrimestre() + " el " + getDf().format(rr.get().eval("temp$y[1]").asDouble()) +
                "\\% de los casos " + asistencia(rr.get().eval("iconv(temp$x[1],'utf8')").asString()) + ", mientras que "
                + "el " +getDf().format(rr.get().eval("temp$y[length(temp$y)]").asDouble())+ "\\%" +
                " " + asistencia(rr.get().eval("iconv(temp$x[length(temp$x)], 'utf8')").asString())+".",
                "Distribución porcentual de nacimientos según la asistencia recibida durante el parto",
                getFormatoSubtituloG(), "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_10.tex}  \\end{tikzpicture}",
                "INE, con datos del RENAP", "");
        
        
        
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    protected void generarGraficas(){
        Grafica vitales = new Grafica("vitales", getRuta(), rr.get());
        vitales.start();
    }
    
    protected void hacerPortada(){
        String portada = "https://dl.dropboxusercontent.com/u/24564039/portada.pdf";
       File file = new File(getRuta(),"portada.pdf");
       URL url = null;
        try {
            url = new URL(portada);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
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
                "El Instituto Nacional de Estadística -INE-, consciente de la demanda de información "
                + "demográfica y siendo el ente rector de la política estadística nacional "
                + "en Guatemala, en cumplimiento a su Ley Orgánica, Decreto Ley 3-85, se "
                + "complace en presentar el siguiente informe, que contiene las {\\Bold Estadísticas Vitales}"
                + ", con información correspondiente al {\\Bold " + corregirTrimestre(getTrimestre()).toLowerCase() + " trimestre del "
                + getAnioPublicacion() +"}, información esencial para la planificación del desarrollo humano.\n" +
                "\n" +
                "La información presentada a continuación fue recolectada a través "
                + "del Registro Nacional de las Personas  -RENAP- y consiste en "
                + "los hechos ocurridos sobre nacimientos, defunciones, defunciones fetales, "
                + "matrimonios y divorcios registrados "
                + "en el "  +  corregirTrimestre(getTrimestre()).toLowerCase() +  " trimestre del "  + getAnioPublicacion() +" .  "
                + "Sin embargo, los datos para el período {\\Bold son preliminares}, "
                + "sujetos a la adición de registros ingresados tardíamente.\n" +
                "\n" +
                "Por lo tanto, el INE se complace en presentar este informe, con "
                + "el propósito de brindar una herramienta más de análisis a la "
                + "población guatemalteca, y a la vez agradece el aporte y colaboración "
                + "del Registro Nacional de las Personas, a quien se insta  a "
                + "continuar con el apoyo a este proceso.\n" +
                "\n" +
                "\\thispagestyle{empty}\n" +
                "\n" +
                "\n" +
                "\\cleardoublepage");
    }
    
    private String asistencia(String valor){
        String retorno = "";
        System.out.println(valor);
        if(comparador.compare(valor,"medica") == 0){
            retorno = "recibieron asistencia médica";
        }else if(comparador.compare(valor, "comadrona") == 0){
            retorno = "fueron asistidos por una comadrona";
        }else if(comparador.compare(valor,"ninguna") == 0){
            retorno = "no recibieron ningún tipo de asistencia";
        }else if(comparador.compare(valor,"paramedica") == 0){
            retorno = "recibieron asistencia paramédica";
        }else if(comparador.compare(valor, "empirica") == 0){
            retorno = "recibieron asistencia empírica";
        }
        return retorno;
    }

//    protected void compilar(String ruta){
//        rr.get().eval("compilar('" + ruta + "')" );
//    }
}
