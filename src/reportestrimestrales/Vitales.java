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
        formatoSerie = "Serie histórica " + formatearNumero((Double.parseDouble(getAnioPublicacion()) -2)) + "-" + getAnioPublicacion();
                
                
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
        introCapitulos.add("La estadística continua de defunciones permite "
                + "identificar y diferenciar la frecuencia y las características"
                + " de la mortalidad según el área donde habita, sexo, edad, grupo "
                + "étnico  y la causa básica de defunción, datos necesarios para "
                + "la elaboración de programas de salud pública para el control de "
                + "enfermedades infecciosas, epidemiológicas, enfermedades crónicas "
                + "no transmisibles, salud alimentaria y nutricional, inmunizaciones, "
                + "salud mental, salud reproductiva, prevención de accidentes y otros.");
        introCapitulos.add("Estas estadísticas permiten conocer el entorno en que "
                + "se dan este tipo de muertes (situación social y económica de "
                + "los padres, tipo de asistencia, estado de salud de la madre y "
                + "del feto, etc.), así como la frecuencia con la que ocurren.");
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
          String columna1 = columna("1_01","Nacimientos","El número de nacimientos es una de  las variable en el "
                + "estudio de los fenómenos demográficos que, utilizado "
                + "conjuntamente con la estadística de defunciones y matrimonios"
                + ", ayuda a determinar la estructura y dinámica de población, "
                + "con la incorporación de individuos a la población.", "Durante el " + 
                getFormatoTrimestre() +
                " trimestre del " + getAnioPublicacion()+  " se registraron "
                +  getDf().format(rr.get().eval("vitales$'1_01'$y[9]").asDouble())  + " nacimientos, es decir,  "
                + rr.get().eval("iconv(calcularRespuestaPor(vitales$'1_01'), 'utf8')").asString()
                +" el mismo período "
                + "del año anterior y "+ rr.get().eval("iconv(calcularRespuestaPor(vitales$'1_01', primeraPos = 8), 'utf8')").asString()
                + " el trimestre anterior. ",
                "Nacimientos por trimestre", 
                formatoSerie,
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_01.tex}  \\end{tikzpicture}","INE, con datos del RENAP", "");
        
        rr.get().eval("temp <- vitales$'1_02'[ordenarNiveles(vitales$'1_02',T),]");
        String columna2 = columna("1_02","Nacimientos por departamento", "La cantidad "
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
        String columna1 = columna("1_03","Nacimientos por edad de la madre", 
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
        
        String columna2 = columna("1_04","Nacimientos en madres adolescentes", 
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
        String columna1 = columna("1_05","Nacimientos por pueblo de pertenecia de la madre", 
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
        String columna2 = columna("1_06","Nacimientos según estado civil de la madre", 
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
        String columna1 = columna("1_07","Nacimientos según sexo del recién nacido",
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
        String columna2 = columna("1_08","Nacimientos según peso del recién nacido", 
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
        String columna1 = columna("1_09","Nacimientos con bajo peso al nacer",
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
        String columna2 = columna("1_10","Nacimientos según asistencia recibida durante el parto",
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
        System.out.println("GENERANDO LAS GRAFICAS");
        Grafica vitales = new Grafica("vitales", getRuta(), rr.get());
        vitales.start();
    }
    
    protected void hacerPortada(){
       String portada = "http://www.ine.gob.gt/ftparchivos/portadaVitales.pdf";
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

    private String sitio(String valor){
        String retorno = "";
        System.out.println("valor");
        if(comparador.compare(valor, "Domicilio") == 0){
            retorno = "el domicilio del fallecido";
        }else if(comparador.compare(valor, "Hospital público") == 0){
            retorno = "un hospital público";
        }else if(comparador.compare(valor, "Seguro Social") == 0){
            retorno = "el IGSS";
        }else if(comparador.compare(valor, "Hospital privado") == 0){
            retorno = "un hospital privado";
        }else if(comparador.compare(valor,"Centro de salud") == 0){
            retorno = "un centro de salud";
        }else if(comparador.compare(valor, "Lugar de trabajo") == 0){
            retorno = " el lugar de trabajo del fallecido ";
        }else if(comparador.compare(valor, "Vía pública") == 0){
            retorno = " la vía pública";
        }
        return retorno;
    }
    
   

    protected void capitulo2(){
        //Escribiendo la introducción del capítulo 2
        escribirCapitulo(capitulos.get(1).toString(), capitulos.get(1).toString()
                ,"", introCapitulos.get(1).toString());
        
        
        section2_01();
        section2_02();
        section2_03();
        section2_04();
    }
    
    private void section2_01() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA 6%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        String columna1 =  columna("2_01","Defunciones","Las defunciones "
                + "constituyen la variable en el estudio de los fenómenos "
                + "demográficos que, utilizado conjuntamente con la estadística "
                + "de nacimientos y matrimonios, ayuda a determinar su estructura "
                + "y dinámica, con la salida de individuos de la población.", 
                "La gráfica de la serie histórica muestra que durante " + getFormatoTrimestre() +
                " se registraron " + getDf().format(rr.get().eval("vitales$'2_01'$y[9]").asDouble())+ 
                " defunciones, esto es " + rr.get().eval("iconv(calcularRespuestaPor(vitales$'2_01'), 'utf8')").asString()
                +" el mismo período " + " del año anterior y "+ getDf().format(rr.get().eval("cambioInterAnual(vitales$'2_01', paso = 1)").asDouble())
                + "\\% menos de lo registrado en el trimestre anterior. "
                , "Defunciones por trimestre",formatoSerie,
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_01.tex} "
                + "\\end{tikzpicture}","INE, con datos del RENAP", "");
        
        rr.get().eval("temp <- vitales$'2_02'[ordenarNiveles(vitales$'2_02',T),]");
        String columna2 = columna("2_02","Defunciones por departamento", 
                "La desagregación de las defunciones según el departamento de residencia, " +
                "permite identificar la dinámica poblacional para la focalización de programas específicos\n" +
                "para cubrir las necesidades del área.", 
                "Para el " + getFormatoTrimestre() + 
                " , el departamento que registró mayor proporcición de de defunciones  "
                + "fue " + rr.get().eval("temp$x[1]").asString() + " con un " + 
                getDf().format(rr.get().eval("temp$y[1]/vitales$'2_01'$y[9] *100").asDouble()) + "\\% "
                + "seguido de " + rr.get().eval("temp$x[2]").asString() + " con el " +
                getDf().format(rr.get().eval("temp$y[2]/vitales$'2_01'$y[9] *100").asDouble()) + "\\% y "
                + rr.get().eval("temp$x[3]").asString() + " con " + 
                getDf().format(rr.get().eval("temp$y[3]/vitales$'2_01'$y[9] *100").asDouble()) + "\\%."
                + " Los departamentos con menor proporción son "
                + rr.get().eval("temp$x[length(temp$x)]").asString() +  " con " +
                getDf().format(rr.get().eval("temp$y[length(temp$x)]/vitales$'2_01'$y[9] *100").asDouble()) + "\\% y "+
                rr.get().eval("temp$x[length(temp$x)-1]").asString() + " con " + 
                getDf().format(rr.get().eval("temp$y[length(temp$x)-1]/vitales$'2_01'$y[9] *100").asDouble()) + "\\%." , 
                "Número de defunciones por departamento de residencia de la persona fallecida", 
                getFormatoSubtituloG(), "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_02.tex}  \\end{tikzpicture}","INE, con datos del RENAP", "");
    
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    private void section2_02(){
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA 7%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        rr.get().eval("temp <- vitales$'2_03'[ordenarNiveles(vitales$'2_03',T),]");
        String columna1 =  columna("2_03","Defunciones por día de la semana","La "
                + "frecuencia y el día de la semana en el que se dan las "
                + "defunciones permite  identificar patrones temporales, "
                + "especialmente cuando estas son ocasionadas por causas "
                + "externas, y así diseñar planes de emergencia y concienciación "
                + "de prevención.", 
                "Para el " + getFormatoTrimestre() 
                + " el día con mayor cantidad de defunciones fue " 
                + rr.get().eval("temp$x[1]").asString().toLowerCase() +
                " totalizando un  " + getDf().format(rr.get().eval("temp$y[1]").asDouble())+ 
                "\\% de los casos, seguido de " +
                rr.get().eval("temp$x[2]").asString().toLowerCase() + 
                ", día en el que se registraron un " + getDf().format(rr.get().eval("temp$y[2]").asDouble())
                +"\\%, de las defunciones. El "+ rr.get().eval("temp$x[length(temp$x)]").asString().toLowerCase() + 
                " fue el día que registro menos descesos, contabilizando el " + 
                getDf().format(rr.get().eval("temp$y[length(temp$y)]").asDouble())
                + "\\% del total de defunciones. "
                , "Distribución porcentual de defunciones por día de la semana de ocurrencia", getFormatoSubtituloG(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_03.tex} "
                + "\\end{tikzpicture}","INE, con datos del RENAP", "");
        
        String columna2 = columna("2_04","Mortalidad infantil", 
                "Durante " + getFormatoTrimestre() + " se registraron " +
                getDf().format(rr.get().eval("(vitales$'2_04'[2])[1,]+(vitales$'2_04'[3])[1,]").asDouble())+
                " defunciones de niños en la etapa infantil\\footnote{Niños mayores de un año}, de los  cuales el " + 
                getDf().format(rr.get().eval("vitales$'2_04'$Hombres[1] / ((vitales$'2_04'[2])[1,]+(vitales$'2_04'[3])[1,]) * 100").asDouble())+
                "\\% fueron hombres. ","En cuanto a la edad al momento de la muerte, el "
                + getDf().format(rr.get().eval(" (vitales$'2_04'$Hombres[2] + vitales$'2_04'$Mujeres[2] ) / sum(vitales$'2_04'$Hombres + vitales$'2_04'$Mujeres) * 100 ").asDouble()) + 
                "\\% falleció en la etapa neonatal\\footnote{Niños menores de 28 días}, mientras que el " + 
                getDf().format(rr.get().eval("( vitales$'2_04'$Hombres[3] + vitales$'2_04'$Mujeres[3] )/ sum(vitales$'2_04'$Hombres + vitales$'2_04'$Mujeres) * 100").asDouble()) + 
                "\\% en la etapa post-neonatal\\footnote{Niños menores de un año pero mayores de 27 días}. ", 
                "Defunciones neonatales y post neonatales por sexo", 
                getFormatoSubtituloG(), "\\XeTeXpdffile \"2_04.pdf\" ","INE, con datos del RENAP", ""); 
        
        escribirLinea(hojaTrimestral(columna1, columna2));
    }

    private void section2_03() {
         escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA 8%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        String columna1 =  columna("2_05",
                "Defunciones en menores de cinco años",
                "La mortalidad en la niñez es un indicador clave para determinar "
                + "las condiciones básicas de salud de una población.", 
                "Como se muestra en la gráfica, el porcentaje de defunciones en menores de cinco años para el " + 
                getFormatoTrimestre() +
                " fue del " + getDf().format(rr.get().eval("vitales $'2_05'$y[9]").asDouble() )+  "\\%, esto es, "+
                rr.get().eval("iconv(calcularRespuestaNeta(vitales$'2_05'), 'utf8')").asString()+
                "el mismo período del año anterior y "+ 
                rr.get().eval("iconv(calcularRespuestaNeta(vitales$'2_05',primeraPos=8), 'utf8')").asString()
                + "  el trimestre anterior. ",
                 "Porcentaje de defunciones en menores de cinco años", 
                 formatoSerie,
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_05.tex} "
                + "\\end{tikzpicture}","INE, con datos del RENAP", "");
        
         rr.get().eval("temp <- excluirNiveles(vitales$'2_06')");
        String columna2 = columna("2_06","Defunciones según asistencia recibida",
                "Del total de defunciones registradas durante " +
                getFormatoTrimestre() + " el " + getDf().format(rr.get().eval("temp$y[1]").asDouble()) +
                "\\% de los casos " + asistencia(rr.get().eval("iconv(temp$x[1],'utf8')").asString()) + ", mientras que "
                + "el " +getDf().format(rr.get().eval("temp$y[length(temp$y)]").asDouble())+ "\\%" +
                " " + asistencia(rr.get().eval("iconv(temp$x[length(temp$x)], 'utf8')").asString())+".",
                "Además el " + getDf().format(rr.get().eval("temp$y[2]").asDouble()) 
                +"\\% "+ asistencia(rr.get().eval("iconv(temp$x[2],'utf8')").asString())+ 
                " y  " + getDf().format(rr.get().eval("temp$y[3]").asDouble()) + "\\% "+
                asistencia(rr.get().eval("iconv(temp$x[3],'utf8')").asString()),
                "Distribución porcentual de nacimientos según la asistencia recibida durante el parto",
                getFormatoSubtituloG(), "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_06.tex}  \\end{tikzpicture}",
                "INE, con datos del RENAP", ""); 
        
        escribirLinea(hojaTrimestral(columna1, columna2));
    }

    private void section2_04() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA 8%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        
        rr.get().eval("temp <- excluirNiveles(vitales$'2_07')");
        String columna1 = columna("2_07",
                "Defunciones por lugar de ocurrencia",
                "El lugar de ocurrencia de una defunción está estrechamente "
                + "relacionado con la atención recibida y las condiciones de la misma.", 
                "Según la gráfica de defunciones por lugar de ocurrencia, el  "+
                getDf().format(rr.get().eval("temp$y[1]").asDouble()) + "\\% de los casos ocurrieron en "
                + sitio(rr.get().eval("temp$x[1]").asString()) + ". Por otro lado la menor proporción "
                + " de defunciones sucedieron en " + sitio(rr.get().eval("temp$x[length(temp$x)]").asString())+
                ", representando el " + formatearNumero(rr.get().eval("temp$y[length(temp$y)]").asDouble()) + 
                "\\% de los casos. ",
                "Distribución porcentual de defunciones por lugar de ocurrencia",
                getFormatoSubtituloG(),
                "\\begin{tikzpicture}[x=1pt,y=1pt] \\input{2_07.tex} \\end{tikzpicture}",
                "INE, con datos del RENAP",
                ""
                );
        escribirLinea(hojaTrimestral(columna1, ""));
    }
    
    protected void capitulo3(){
         //Escribiendo la introducción del capítulo 3
        escribirCapitulo(capitulos.get(2).toString(), capitulos.get(2).toString()
                ,"", introCapitulos.get(2).toString());
        
        section3_01();
        
        section3_02();
    }

protected ArrayList<String> filtrarDatos(String dataFrame){
    ArrayList<String> retorno = new ArrayList<String>();
    rr.get().eval("");
    
    return retorno;
}

    private void section3_01() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA 10%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        String columna1 =  columna("3_01",
                "Defunciones fetales",
                "La defunción fetal es un resultado adverso de los embarazos, "
                + "que pueden tener causas relacionadas específicamente con la "
                + "madre o bien ser de carácter propiamente fetal.", 
                "La gráfica de la serie histórica muestra que durante " + getFormatoTrimestre() +
                " se registraron " + getDf().format(rr.get().eval("vitales$'3_01'$y[9]").asDouble())+ 
                " defunciones fetales, esto es " + rr.get().eval("iconv(calcularRespuestaPor(vitales$'3_01'), 'utf8')").asString()
                +" el mismo período " + " del año anterior y "+ getDf().format(rr.get().eval("cambioInterAnual(vitales$'3_01', paso = 1)").asDouble())
                + "\\% menos de lo registrado en el trimestre anterior. ",
                "Defunciones fetales por trimestre",formatoSerie,
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_01.tex} "
                + "\\end{tikzpicture}","INE, con datos del RENAP", "");
        
        rr.get().eval("temp <- excluirNiveles( vitales$'3_02')");
        String columna2 = columna("3_02",
                "Defunciones fetales por departamento", 
                "Las estadísticas de defunciones fetales  por departamento, "
                + "permite identificar las áreas donde se da la mayor frecuencia"
                + " de estos casos y determinar las características sociales, "
                + "económicas y ambientales que inciden en ellos.",
                "Para el " + getFormatoTrimestre() + 
                " , el departamento que registró mayor proporcición de de defunciones fetales  "
                + "fue " + rr.get().eval("temp$x[1]").asString() + " con un " + 
                getDf().format(rr.get().eval("temp$y[1]/vitales$'3_01'$y[9] *100").asDouble()) + "\\% "
                + "seguido de " + rr.get().eval("temp$x[2]").asString() + " con el " +
                getDf().format(rr.get().eval("temp$y[2]/vitales$'3_01'$y[9] *100").asDouble()) + "\\% y "
                + rr.get().eval("temp$x[3]").asString() + " con " + 
                getDf().format(rr.get().eval("temp$y[3]/vitales$'3_01'$y[9] *100").asDouble()) + "\\%."
                + " Los departamentos con menor proporción son "
                + rr.get().eval("temp$x[length(temp$x)]").asString() +  " con " +
                getDf().format(rr.get().eval("temp$y[length(temp$x)]/vitales$'3_01'$y[9] *100").asDouble()) + "\\% y "+
                rr.get().eval("temp$x[length(temp$x)-1]").asString() + " con " + 
                getDf().format(rr.get().eval("temp$y[length(temp$x)-1]/vitales$'3_01'$y[9] *100").asDouble()) + "\\%." , 
                "Número de defunciones fetales por departamento de residencia de la madre", 
                getFormatoSubtituloG(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_02.tex}  \\end{tikzpicture}",
                "INE, con datos del RENAP", "");
    
        escribirLinea(hojaTrimestral(columna1, columna2));
    }

    private void section3_02() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA 11%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        rr.get().eval("temp <- excluirNiveles(vitales$'3_03')");
        String columna1 = columna("3_03",
                "Defunciones fetales por semana de gestación", 
                "Es la muerte de un producto de la concepción, antes de su "
                + "expulsión o extracción completa del cuerpo de su madre, "
                + "independientemente de la duración del embarazo.",
                "De las defunciones fetales registradas en " +
                getFormatoTrimestre() +  " el " + 
                getDf().format(rr.get().eval("temp$y[1]").asDouble())
                + "\\% sucedieron cuando el tiempo de embarazo estaba comprendido entre  "
                + rr.get().eval("temp$x[1]").asString() +" semanas y el  "
                + getDf().format(rr.get().eval("temp$y[2]").asDouble()) + "\\% entre " +
               rr.get().eval("temp$x[2]").asString() + " semanas. ",
                "Distribución porcentual de defunciones fetales según semana de gestación",
                getFormatoSubtituloG(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_03.tex} \\end{tikzpicture}",
                "INE, con datos del RENAP",
                "");
        
        String columna2 = columna("3_04","Nacimientos en madres adolescentes", 
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



}

