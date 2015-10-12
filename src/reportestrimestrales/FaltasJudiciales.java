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
public class FaltasJudiciales extends Documento{
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
    
    
    
    
    public FaltasJudiciales(String titulo, String trimestre, String pYear, String rutaCSV) {
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
        capitulos.add("Faltas judiciales");
        capitulos.add("Infractores menores de edad");
        capitulos.add("Tipos de faltas");
        
    }
    
    protected void setIntroCapitulos(){
        introCapitulos.add("Las faltas judiciales son acciones u omisiones voluntarias castigadas "
                + "por la ley con pena leve. En la legislación guatemalteca están contenidas "
                + "en el Libro Tercero del Código Penal.");
        introCapitulos.add("Comprende a todas las edades de las personas antes de cumplir 18 " 
                + "años que han cometido acciones u omisiones voluntarias castigadas por " 
                + "la Ley con pena leve.");
        introCapitulos.add("En derecho Penal, son acciones u omisiones voluntarias castigadas " 
                + "por la ley con pena leve.  Contenidas en el libro tercero de las faltas, " 
                + "título único del Código Penal, el artículo 480 en el inciso 6º. Indica: “Se " 
                + "sancionará como falta solamente los hechos que,conforme a este Código " 
                + "(Código Penal), no constituya delito”.");
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
                        tmp.get(4).toString(),tmp.get(5).toString(),"");
                if(j<(finSeccion-1)){
                columna2 = columna(tmp.get(6).toString(),tmp.get(7).toString(),"",
                        "",tmp.get(8).toString(),tmp.get(9).toString(),
                        tmp.get(10).toString(),tmp.get(11).toString(),"");
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
                REXP listadoCSV = rr.get().eval("vitales <- cargaMasiva('" +  ruta +"')");
                REXP nombres = rr.get().eval("names(vitales)");
                System.out.println(listadoCSV);
                System.out.println(nombres);
            }
    }
    
    protected ArrayList cargarCapitulo1(){
        ArrayList cap1 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("1_01");
        seccion1.add("Faltas judiciales");
        seccion1.add("Número de faltas judiciales");
        seccion1.add(formatoSerie);
        seccion1.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_01.tex}  \\end{tikzpicture}");
        seccion1.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        
        seccion1.add("1_02");
        seccion1.add("Faltas judiciales por departamento");
        seccion1.add("Faltas judiciales ocurridas en el año 2014");
        seccion1.add("Distribución porcentual por departamento");
        seccion1.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_02.tex}  \\end{tikzpicture}");
        seccion1.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        cap1.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("1_03");
        seccion2.add("Faltas judiciales por área geográfica");
        seccion2.add("Faltas judiciales ocurridas en el año 2014");
        seccion2.add("Distribución porcentual por área geográfica");
        seccion2.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_03.tex}  \\end{tikzpicture}");
        seccion2.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        
        seccion2.add("1_04");
        seccion2.add("Faltas judiciales por edad del infractor");
        seccion2.add("Faltas judiciales ocurridas en el año 2014");
        seccion2.add("Distribución porcentual por edad del infractor");
        seccion2.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_04.tex}  \\end{tikzpicture}");
        seccion2.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        cap1.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("1_05");
        seccion3.add("Faltas judiciales según sexo");
        seccion3.add("Faltas judiciales ocurridas en el año 2014");
        seccion3.add("Distribución porcentual por sexo");
        seccion3.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_05.tex}  \\end{tikzpicture}");
        seccion3.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        
        seccion3.add("1_06");
        seccion3.add("Faltas judiciales por grupo étnico");
        seccion3.add("Faltas judiciales ocurridas en el año 2014");
        seccion3.add("Distribución porcentual por grupo étnico");
        seccion3.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_06.tex}  \\end{tikzpicture}");
        seccion3.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        cap1.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("1_07");
        seccion4.add("Faltas judiciales por analfabetismo");
        seccion4.add("Faltas judiciales ocurridas en el año 2014");
        seccion4.add("Distribución porcentual por condición de analfabetismo");
        seccion4.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_07.tex}  \\end{tikzpicture}");
        seccion4.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        
        seccion4.add("1_08");
        seccion4.add("Faltas judiciales por nivel de educación");
        seccion4.add("Faltas judiciales ocurridas en el año 2014");
        seccion4.add("Distribución porcentual por nivel de educación");
        seccion4.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_08.tex}  \\end{tikzpicture}");
        seccion4.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        cap1.add(seccion4);
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("1_09");
        seccion5.add("Faltas judiciales por condición de ebriedad");
        seccion5.add("Faltas judiciales ocurridas en el año 2014");
        seccion5.add("Distribución porcentual por condición de ebriedad");
        seccion5.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_09.tex}  \\end{tikzpicture}");
        seccion5.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        
        seccion5.add("1_10");
        seccion5.add("Faltas judiciales por tipo de falta");
        seccion5.add("Faltas judiciales ocurridas en el año 2014");
        seccion5.add("Distribución porcentual por tipo de falta");
        seccion5.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_10.tex}  \\end{tikzpicture}");
        seccion5.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        cap1.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("1_11");
        seccion6.add("Faltas judiciales por sexo del infractor según tipo de"
        + " falta cometida");
        seccion6.add("Faltas judiciales ocurridas en el año 2014");
        seccion6.add("Distribución porcentual por tipo de falta cometida");
        seccion6.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_11.tex}  \\end{tikzpicture}");
        seccion6.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        cap1.add(seccion6);
        return cap1;        
    }
    protected ArrayList cargarCapitulo2(){
        ArrayList cap2 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("2_01");
        seccion1.add("Infractores menores de 18 años");
        seccion1.add("Número de faltas judiciales");
        seccion1.add(formatoSerie);
        seccion1.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_01.tex}  \\end{tikzpicture}");
        seccion1.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        
        seccion1.add("2_02");
        seccion1.add("Infractores menores de edad por departamento");
        seccion1.add("Faltas judiciales ocurridas en el año 2014");
        seccion1.add("Distribución porcentual por departamento");
        seccion1.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_02.tex}  \\end{tikzpicture}");
        seccion1.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        cap2.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("2_03");
        seccion2.add("Infractores menores de edad por sexo");
        seccion2.add("Faltas judiciales ocurridas en el año 2014");
        seccion2.add("Distribución porcentual por sexo");
        seccion2.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_03.tex}  \\end{tikzpicture}");
        seccion2.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        
        seccion2.add("2_04");
        seccion2.add("Infractores menores de edad por edad");
        seccion2.add("Faltas judiciales ocurridas en el año 2014");
        seccion2.add("Distribución porcentual por edades simples");
        seccion2.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_04.tex}  \\end{tikzpicture}");
        seccion2.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        cap2.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("2_05");
        seccion3.add("Infractores menores de edad por grupo étnico");
        seccion3.add("Faltas judiciales ocurridas en el año 2014");
        seccion3.add("Distribución porcentual por grupo étnico");
        seccion3.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_05.tex}  \\end{tikzpicture}");
        seccion3.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        
        seccion3.add("2_06");
        seccion3.add("Infractores menores de edad por nivel de educación");
        seccion3.add("Faltas judiciales ocurridas en el año 2014");
        seccion3.add("Distribución porcentual por nivel de educación");
        seccion3.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_06.tex}  \\end{tikzpicture}");
        seccion3.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        cap2.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("2_07");
        seccion4.add("Infractores menores de edad por tipo de falta cometida");
        seccion4.add("Faltas judiciales ocurridas en el año 2014");
        seccion4.add("Distribución porcentual por tipo de falta cometida");
        seccion4.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_07.tex}  \\end{tikzpicture}");
        seccion4.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        cap2.add(seccion4);
        
        
        return cap2;        
    }
    
    
    
    protected ArrayList cargarCapitulo3(){
        ArrayList cap3 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("3_01");
        seccion1.add("Faltas contra las personas");
        seccion1.add("Número de faltas judiciales contra las personas");
        seccion1.add(formatoSerie);
        seccion1.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_01.tex}  \\end{tikzpicture}");
        seccion1.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        
        seccion1.add("3_02");
        seccion1.add("Faltas contra la propiedad");
        seccion1.add("Número de faltas judiciales contra la propiedad");
        seccion1.add(formatoSerie);
        seccion1.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_02.tex}  \\end{tikzpicture}");
        seccion1.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        cap3.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("3_03");
        seccion2.add("Faltas contra las buenas costumbres");
        seccion2.add("Número de faltas judiciales contra las buenas costumbres");
        seccion2.add(formatoSerie);
        seccion2.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_03.tex}  \\end{tikzpicture}");
        seccion2.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        
        seccion2.add("3_04");
        seccion2.add("Faltas contra el orden público");
        seccion2.add("Número de faltas judiciales contra el orden público");
        seccion2.add(formatoSerie);
        seccion2.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_04.tex}  \\end{tikzpicture}");
        seccion2.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        cap3.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("3_05");
        seccion3.add("Otras faltas");
        seccion3.add("Número de faltas judiciales (Clasificadas como \"Otras\")");
        seccion3.add("Distribución porcentual por grupo étnico");
        seccion3.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_05.tex}  \\end{tikzpicture}");
        seccion3.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        
        cap3.add(seccion3);
        
        
        return cap3;        
    }
    
    
    
    protected void capitulo1(){
        //CARGA DE SESION DE R
        
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
                getFormatoTrimestre() + " se registraron "
                +  getDf().format(rr.get().eval("vitales$'1_01'$y[9]").asDouble())  + " nacimientos, lo que representa  "
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
                        + "departamento de  " + rr.get().eval("iconv(temp$x[1],'utf8')").asString() + 
                        " con un " + formatearNumero(rr.get().eval("temp$y[1]/vitales$'1_01'$y[9] *100").asDouble()) + 
                        "\\%," + " seguido de " + rr.get().eval("iconv(temp$x[2],'utf8')").asString() +
                        " con el "+ formatearNumero(rr.get().eval("temp$y[2]/vitales$'1_01'$y[9] *100").asDouble())+
                        "\\% y " + rr.get().eval("iconv(temp$x[3],'utf8')").asString() +
                        " con " + formatearNumero(rr.get().eval("temp$y[3]/vitales$'1_01'$y[9] * 100").asDouble())+
                        "\\%." + " Los departamentos con menor proporción son "
                        + rr.get().eval("iconv(temp$x[length(temp$x)],'utf8')").asString() +  " con " +
                        formatearNumero(rr.get().eval("temp$y[length(temp$x)]/vitales$'1_01'$y[9] *100").asDouble()) + "\\% y "+
                        rr.get().eval("temp$x[length(temp$x)-1]").asString() + " con " + 
                        formatearNumero(rr.get().eval("temp$y[length(temp$x)-1]/vitales$'1_01'$y[9] *100").asDouble()) + "\\%."
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
                ((rr.get().eval("vitales$'1_03'$y[2]").asDouble() > rr.get().eval("vitales$'1_03'$y[5]").asDouble()) ? " más " : " menos " ) +  " nacimientos en mujeres de 15 a 19 años "
                + "que en mujeres de 30 a 34 años.", "Distribución de nacimientos "
                        + "por grupo de edad de la madre", corregirTrimestre(getTrimestre()) + " trimestre, año " + getAnioPublicacion()
                , "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_03.tex} \\end{tikzpicture}", "INE, "
                        + "con datos del RENAP", "");
        
        String columna2 = columna("1_04","Nacimientos en madres menores de veinte años", 
                "La desagregación de los nacimientos de las madres adolescentes\\footnote{Comprendidas entre los 10 y 19 años}"
                        + " por edad " +
                "simple muestra que, durante  el " + getFormatoTrimestre()  + ", " + getDf().format(rr.get().eval("sum(vitales$'1_04'$y[0:4])").asDouble()) +  
                " fueron  madres en la etapa de adolescencia temprana\\footnote{Adolescencia temprana: entre los"
                        + " 10 y los 14 años.}" +
                " y " + getDf().format(rr.get().eval("sum(vitales$'1_04'$y[5:9])").asDouble()) + " fueron  madres en la etapa" +
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
                " de las madres pertenecen al pueblo " + 
                rr.get().eval("temp$x[1]").asString() + " y el  "+ getDf().format(rr.get().eval("temp$y[2]").asDouble()) +
                "\\% del pueblo " + rr.get().eval("temp$x[2]").asString()+
                " . Se desconoce el pueblo de pertenencia de la madre en " +
                getDf().format(rr.get().eval("temp$y[length(temp$y)]").asDouble())+ "\\% de los casos.",
                "",
                "Distribución porcentual de nacimientos"
                + " por pueblo de pertenencia de la madre ",
                getFormatoSubtituloG(), 
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
                + getDf().format(rr.get().eval("temp$y[2]").asDouble()) + "\\% a madres " + 
                rr.get().eval("temp$x[2]").asString().toLowerCase()+"s, y " + 
                getDf().format(rr.get().eval("temp$y[3]").asDouble()) + "\\% a madres " + 
                rr.get().eval("temp$x[3]").asString().toLowerCase()+"s.",
                "Distribución porcentual "
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
            genero2 +", además el índice de masculinidad registrado fue de " + getDf().format(masculinidad) + ".", "Distribución "
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
                + "es una variable usada para evaluar las probabilidades de supervivencia "
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
                Math.round(( Double.parseDouble(getAnioPublicacion())-2)) +  " el porcentaje ha  " + cambio+
                 " en " +  getDf().format(rr.get().eval("vitales$'1_09'$y[9] - vitales$'1_09'$y[1]").asDouble()) + 
                ( (rr.get().eval("vitales$'1_09'$y[9] - vitales$'1_09'$y[1]").asDouble() == 1 ) ?" punto.":" puntos."),
                "Porcentaje de nacimientos con bajo peso al nacer", 
                formatoSerie ,"\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_09.tex}  \\end{tikzpicture}" 
                ,"INE, con datos del RENAP", "");
        
        
        rr.get().eval("temp <- vitales$'1_10'[ordenarNiveles(vitales$'1_10'),]");
        String columna2 = columna("1_10","Nacimientos según asistencia recibida durante el parto",
                "Una adecuada atención al momento del nacimiento garantiza la salud"
                + " materna e infantil de una población.", "Del total de nacimientos registrados durante " +
                getFormatoTrimestre() + ", el " + getDf().format(rr.get().eval("temp$y[1]").asDouble()) +
                "\\% de los casos " + asistencia(rr.get().eval("iconv(temp$x[1],'utf8')").asString()) + ", mientras que "
                + "el " +getDf().format(rr.get().eval("temp$y[length(temp$y)]").asDouble())+ "\\%" +
                " " + asistencia(rr.get().eval("iconv(temp$x[length(temp$x)], 'utf8')").asString())+".",
                "Distribución porcentual de nacimientos según la asistencia recibida durante el parto",
                getFormatoSubtituloG(), "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_10.tex}  \\end{tikzpicture}",
                "INE, con datos del RENAP", "");
        
        
        
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    protected void generarGraficas(String modalidad){
        System.out.println("GENERANDO LAS GRAFICAS");
        Grafica vitales = new Grafica("vitales", getRuta(), rr.get(), modalidad);
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
        
        String contraPortada = "http://www.ine.gob.gt/ftparchivos/contraportadaVitales.pdf";
        File file1 = new File(getRuta(),"contraPortada.pdf");
        try {
            url = new URL(contraPortada);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FileUtils.copyURLToFile(url, file1);
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
                "\\indent El Instituto Nacional de Estadística -INE-, consciente de la demanda de información "
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
                + "del Registro Nacional de las Personas, al cual se insta  a "
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
        System.out.println(valor);
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
                "La gráfica de la serie histórica muestra que durante el " + getFormatoTrimestre() +
                " se registraron " + getDf().format(rr.get().eval("vitales$'2_01'$y[9]").asDouble())+ 
                " defunciones, esto es " + rr.get().eval("iconv(calcularRespuestaPor(vitales$'2_01',cota = 0.1), 'utf8')").asString()
                +" el mismo período " + " del año anterior y " + rr.get().eval("iconv(calcularRespuestaPor(vitales$'2_01', primeraPos = 8), 'utf8')").asString()
                + " el trimestre anterior. "
                , "Defunciones por trimestre",formatoSerie,
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_01.tex} "
                + "\\end{tikzpicture}","INE, con datos del RENAP", "");
         
        rr.get().eval("temp <- vitales$'2_02'[ordenarNiveles(vitales$'2_02',T),]");
        String columna2 = columna("2_02","Defunciones por departamento", 
                "La desagregación de las defunciones según el departamento de residencia, " +
                "permite identificar la dinámica poblacional para la focalización de programas específicos\n" +
                "para cubrir las necesidades del área.", 
                "Para el " + getFormatoTrimestre() + 
                ", el departamento que registró mayor proporcición de  defunciones  "
                + "fue " + rr.get().eval("temp$x[1]").asString() + " con un " + 
                getDf().format(rr.get().eval("temp$y[1]/vitales$'2_01'$y[9] *100").asDouble()) + "\\% "
                + "seguido de " + rr.get().eval("temp$x[2]").asString() + " con  " +
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
                + rr.get().eval("iconv(temp$x[1],'utf8')").asString().toLowerCase() +
                " totalizando un  " + getDf().format(rr.get().eval("temp$y[1]").asDouble())+ 
                "\\% de los casos, seguido de " +
                rr.get().eval("iconv(temp$x[2],'utf8')").asString().toLowerCase() + 
                ", día en el que se registraron un " + getDf().format(rr.get().eval("temp$y[2]").asDouble())
                +"\\%, de las defunciones. El "+ rr.get().eval("iconv(temp$x[length(temp$x)],'utf8')").asString().toLowerCase() + 
                " fue el día que registró menos decesos, contabilizando el " + 
                getDf().format(rr.get().eval("temp$y[length(temp$y)]").asDouble())
                + "\\% del total de defunciones. "
                , "Distribución porcentual de defunciones por día de la semana de ocurrencia", getFormatoSubtituloG(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_03.tex} "
                + "\\end{tikzpicture}","INE, con datos del RENAP", "");
        
        String columna2 = columna("2_04","Mortalidad infantil", 
                "Durante el " + getFormatoTrimestre() + " se registraron " +
                getDf().format(rr.get().eval("total<- (vitales$'2_04'[2])[1,]+(vitales$'2_04'[3])[1,]").asDouble())+
                " defunciones de niños en la etapa infantil\\footnote{Niños menores de un año.}, de los  cuales el " + 
                getDf().format(rr.get().eval("vitales$'2_04'$Hombres[1] / ((vitales$'2_04'[2])[1,]+(vitales$'2_04'[3])[1,]) * 100").asDouble())+
                "\\% fueron hombres. ","En cuanto a la edad al momento de la muerte, el "
                + getDf().format(rr.get().eval(" (vitales$'2_04'$Hombres[3] + vitales$'2_04'$Mujeres[3] ) / total * 100 ").asDouble()) + 
                "\\% falleció en la etapa neonatal\\footnote{Niños menores de 28 días.}, mientras que el " + 
                getDf().format(rr.get().eval("( vitales$'2_04'$Hombres[2] + vitales$'2_04'$Mujeres[2] )/ total * 100").asDouble()) + 
                "\\% en la etapa post-neonatal\\footnote{Niños menores de un año pero mayores de 27 días.}. ", 
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
                ", fue del " + getDf().format(rr.get().eval("vitales $'2_05'$y[9]").asDouble() )+  "\\%, esto es, "+
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
                asistencia(rr.get().eval("iconv(temp$x[3],'utf8')").asString()) + ".",
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
                + sitio(rr.get().eval("iconv(temp$x[1],'utf8')").asString()) + ". Por otro lado, la menor proporción "
                + " de defunciones sucedieron en " + sitio(rr.get().eval("iconv(temp$x[length(temp$x)],'utf8')").asString())+
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
        
        section3_03();
        
        section3_04();
    }
    
    protected void capitulo4(){
        //Escribiendo la introducción del capítulo 4
        escribirCapitulo(capitulos.get(3).toString(), capitulos.get(3).toString()
                ,"", introCapitulos.get(3).toString());
        
        section4_01();
        section4_02();
    }
    
    protected void capitulo5(){
        //Esribiendo la introducción del capítulo 5
        escribirCapitulo(capitulos.get(4).toString(), capitulos.get(4).toString()
                ,"", introCapitulos.get(4).toString());
        
        section5_01();
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
                ", se registraron " + getDf().format(rr.get().eval("vitales$'3_01'$y[9]").asDouble())+ 
                " defunciones fetales, esto es " + rr.get().eval("iconv(calcularRespuestaPor(vitales$'3_01'), 'utf8')").asString()
                +" el mismo período " + " del año anterior y "+  rr.get().eval("iconv(calcularRespuestaPor(vitales$'3_01', primeraPos = 8), 'utf8')").asString()
                + " el trimestre anterior. ",
                "Defunciones fetales por trimestre",formatoSerie,
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_01.tex} "
                + "\\end{tikzpicture}","INE, con datos del RENAP", "");
        
        rr.get().eval("temp <- excluirNiveles( vitales$'3_02')");
        String columna2 = columna("3_02",
                "Defunciones fetales por departamento", 
                "Las estadísticas de defunciones fetales  por departamento, "
                + "permiten identificar las áreas donde se da la mayor frecuencia"
                + " de estos casos y determinar las características sociales, "
                + "económicas y ambientales que inciden en ellos.",
                "Para el " + getFormatoTrimestre() + 
                ", el departamento que registró mayor proporcición de  defunciones fetales  "
                + "fue " + rr.get().eval("iconv(temp$x[1],'utf8')").asString() + " con un " + 
                getDf().format(rr.get().eval("temp$y[1]/vitales$'3_01'$y[9] *100").asDouble()) + "\\% "
                + "seguido de " + rr.get().eval("iconv(temp$x[2],'utf8')").asString() + " con " +
                getDf().format(rr.get().eval("temp$y[2]/vitales$'3_01'$y[9] *100").asDouble()) + "\\% y "
                + rr.get().eval("iconv(temp$x[3],'utf8')").asString() + " con " + 
                getDf().format(rr.get().eval("temp$y[3]/vitales$'3_01'$y[9] *100").asDouble()) + "\\%."
                + " Los departamentos con menor proporción son "
                + rr.get().eval("iconv(temp$x[length(temp$x)],'utf8')").asString() +  " con " +
                getDf().format(rr.get().eval("temp$y[length(temp$x)]/vitales$'3_01'$y[9] *100").asDouble()) + "\\% y "+
                rr.get().eval("iconv(temp$x[length(temp$x)-1],'utf8')").asString() + " con " + 
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
                "La defunción fetal es la muerte de un producto de la concepción, antes de la "
                + "expulsión o extracción completa del cuerpo de su madre, "
                + "independientemente de la duración del embarazo.",
                "De las defunciones fetales registradas en el " +
                getFormatoTrimestre() +  " el " + 
                getDf().format(rr.get().eval("temp$y[1]").asDouble())
                + "\\% sucedieron cuando el tiempo de embarazo era de   "
                + rr.get().eval("iconv(temp$x[1], 'utf8')").asString().toLowerCase() +" semanas y el  "
                + getDf().format(rr.get().eval("temp$y[2]").asDouble()) + "\\% de " +
               rr.get().eval("iconv(temp$x[2], 'utf8')").asString().toLowerCase() + " semanas. ",
                "Distribución porcentual de defunciones fetales según semana de gestación",
                getFormatoSubtituloG(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_03.tex} \\end{tikzpicture}",
                "INE, con datos del RENAP",
                "");
        
        rr.get().eval("temp <- excluirNiveles(vitales$'3_04')");
        String columna2 = columna("3_04",
                "Defunciones fetales por grupo de edad de la madre", 
                "La distribución de las defunciones fetales según la edad de la "
                + "madre, permite identificar los grupos mas vulnerables, "
                + "asociados con la tasa de fecundidad. En el " + getFormatoTrimestre()  + ", "
                + "el " + getDf().format(rr.get().eval("temp$y[1]").asDouble()) +  
                "\\%  de las defunciones fetales fueron por madres de " + 
                rr.get().eval("iconv(temp$x[1], 'utf8')").asString().toLowerCase()+
                " años, representando la mayoría de los casos, mientras que el  " 
                + getDf().format(rr.get().eval("temp$y[length(temp$y)]").asDouble()) + 
                "\\%, que representa la minoría,  fueron por madres de " +
                rr.get().eval("iconv(temp$x[length(temp$x)], 'utf8')").asString().toLowerCase() + 
                " años",
                "",
                "Distribución porcentual de defunciones fetales por grupo de edad de la madre",
                getFormatoSubtituloG()  , 
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_04.tex} \\end{tikzpicture}", 
                "INE, con datos del RENAP", "");
        
        
        escribirLinea(hojaTrimestral(columna1,columna2));
    }

    private void section3_03() {
       escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA 12%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
       
       rr.get().eval("temp <- vitales$'3_05'[ordenarNiveles(vitales$'3_05',T),]");
        String columna1 = columna("3_05",
                "Defunciones fetales por pueblo de pertenencia de la madre", 
                "La desagregación de los datos por grupos poblacionales permite "
                + "identificar y focalizar los esfuerzos en mejorar las condiciones "
                + "de salud de una población en particular.",
                "En la distribución de defunciones fetales registrados en el " + getFormatoTrimestre()
                +", el " + getDf().format(rr.get().eval("temp$y[1]").asDouble())+ "\\% " +
                " de los nacimientos fueron por madres pertenecientes al pueblo " + 
                rr.get().eval("iconv(temp$x[1],'utf8')").asString() + " y el  "+ getDf().format(rr.get().eval("temp$y[2]").asDouble()) +
                "\\% por madres del pueblo " + rr.get().eval("iconv(temp$x[2],'utf8')").asString()+
                " . Se desconoce el pueblo de pertenencia de la madre en " +
                getDf().format(rr.get().eval("temp$y[length(temp$y)]").asDouble())+ "\\% de los casos.", 
                "Distribución porcentual de defunciones fetales  por pueblo de pertenencia de la madre ",
                getFormatoSubtituloG(), 
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_05.tex} \\end{tikzpicture}",
                "INE, con datos del RENAP", "");
        
        rr.get().eval("temp <- excluirNiveles(vitales$'3_06')");
        String columna2 = columna("3_06",
                "Defunciones fetales según asistencia recibida",
                "La distribución de defunciones fetales por tipo de asistencia "
                + "recibida da un panorama de las condiciones en las que la madre "
                + "es atendida en cuanto instrumentos utilizados, medicamentos, "
                + " experiencia de quien atiende el caso y otros.",
                "Del total de defunciones fetales registradas durante " +
                getFormatoTrimestre() + " el " + getDf().format(rr.get().eval("temp$y[1]").asDouble()) +
                "\\% de los casos " + asistencia(rr.get().eval("iconv(temp$x[1],'utf8')").asString()) + ", mientras que "
                + "el " +getDf().format(rr.get().eval("temp$y[length(temp$y)]").asDouble())+ "\\%" +
                " " + asistencia(rr.get().eval("iconv(temp$x[length(temp$x)], 'utf8')").asString())+"."+
                " Además el " + getDf().format(rr.get().eval("temp$y[2]").asDouble()) 
                +"\\% "+ asistencia(rr.get().eval("iconv(temp$x[2],'utf8')").asString())+ 
                " y  " + getDf().format(rr.get().eval("temp$y[3]").asDouble()) + "\\% "+
                asistencia(rr.get().eval("iconv(temp$x[3],'utf8')").asString()),
                "Distribución porcentual de defunciones fetales según la asistencia recibida durante el parto",
                getFormatoSubtituloG(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_06.tex}  \\end{tikzpicture}",
                "INE, con datos del RENAP", ""); 
        
        escribirLinea(hojaTrimestral(columna1, columna2));
    }

    private void section3_04() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA 13%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        
        rr.get().eval("temp <- excluirNiveles(vitales$'3_07')");
        String columna1 = columna("3_07",
                "Defunciones fetales por sitio de ocurrencia",
                "El sitio de ocurrencia de una defunción fetal está relacionado "
                + "con el tipo de asistencia recibida, así como de las condiciones en que ocurrió.", 
                "Según la gráfica de defunciones fetales por lugar de ocurrencia, el  "+
                getDf().format(rr.get().eval("temp$y[1]").asDouble()) + "\\% de los casos ocurrieron en "
                + sitio(rr.get().eval("iconv(temp$x[1], 'utf8')").asString()) + ". Por otro lado la menor proporción "
                + " de defunciones sucedieron en " + sitio(rr.get().eval("iconv(temp$x[length(temp$x)], 'utf8')").asString())+
                ", representando el " + formatearNumero(rr.get().eval("temp$y[length(temp$y)]").asDouble()) + 
                "\\% de los casos. ",
                "Distribución porcentual de defunciones por lugar de ocurrencia",
                getFormatoSubtituloG(),
                "\\begin{tikzpicture}[x=1pt,y=1pt] \\input{3_07.tex} \\end{tikzpicture}",
                "INE, con datos del RENAP",
                ""
                );
        escribirLinea(hojaTrimestral(columna1, ""));
    }

    private void section4_01() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA 14%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        String columna1 =  columna("4_01",
                "Matrimonios",
                "El cambio de estado civil de las personas es un factor que "
                + "influye en la estructura de los hogares. En términos demográficos,"
                + " el hogar, como célula de la sociedad, aporta individuos a la "
                + "población, ya que uno de sus fines es la procreación.", 
                "La serie histórica de matrimonios muestra que durante " + getFormatoTrimestre() +
                " se registraron " + getDf().format(rr.get().eval("vitales$'4_01'$y[9]").asDouble())+ 
                " , esto es " + rr.get().eval("iconv(calcularRespuestaPor(vitales$'4_01'), 'utf8')").asString()
                +" el mismo período " + " del año anterior y "+ getDf().format(rr.get().eval("cambioInterAnual(vitales$'4_01', ultimaPos = 9, primeraPos = 8)").asDouble())
                + "\\% menos de lo registrado en el trimestre anterior. ",
                 "Matrimonios por trimestre",
                formatoSerie,
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_01.tex} "
                + "\\end{tikzpicture}",
                "INE, con datos del RENAP", "");
        
        rr.get().eval("temp <- vitales$'4_02'[ordenarNiveles(vitales$'4_02',T),]");
        String columna2 = columna("4_02",
                "Matrimonios por departamento",
                "En este apartado se muestra la distribución de los matrimonios "
                + "realizados durante el " + getFormatoTrimestre() +" en los departamentos. "
                + "Este dato es utilizado para realizar análisis de la estructura de las familias.", 
                "El departamento que realizó la mayor proporción de matrimonios  "
                + "fue " + rr.get().eval("iconv(temp$x[1],'utf8')").asString() + " con un " + 
                getDf().format(rr.get().eval("temp$y[1]/vitales$'4_01'$y[9] *100").asDouble()) + "\\% "
                + "seguido de " + rr.get().eval("iconv(temp$x[2],'utf8')").asString() + " con el " +
                getDf().format(rr.get().eval("temp$y[2]/vitales$'4_01'$y[9] *100").asDouble()) + "\\% y "
                + rr.get().eval("iconv(temp$x[3],'utf8')").asString() + " con " + 
                getDf().format(rr.get().eval("temp$y[3]/vitales$'4_01'$y[9] *100").asDouble()) + "\\%."
                + " Los departamentos con menor proporción de matrimonios son "
                + rr.get().eval("iconv(temp$x[length(temp$x)],'utf8')").asString() +  " con " +
                getDf().format(rr.get().eval("temp$y[length(temp$x)]/vitales$'4_01'$y[9] *100").asDouble()) + "\\% y "+
                rr.get().eval("iconv(temp$x[length(temp$x)-1],'utf8')").asString() + " con " + 
                getDf().format(rr.get().eval("temp$y[length(temp$x)-1]/vitales$'4_01'$y[9] *100").asDouble()) + "\\%." , 
                "Número de matrimonios por departamento de ocurrencia", 
                getFormatoSubtituloG(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_02.tex}  \\end{tikzpicture}",
                "INE, con datos del RENAP", "");
    
        escribirLinea(hojaTrimestral(columna1, columna2));
    }

    private void section4_02() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA 15%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        rr.get().eval("temp <- vitales$'4_03'[ordenarNiveles(vitales$'4_03',T),]");
        String columna1 =  columna("4_03",
                "Matrimonios por día de ocurrencia",
                "La gráfica de distribución de matrimonios realizados en  el " + getFormatoTrimestre() 
                + ", por día de ocurrencia, muestra que el día con mayor cantidad de matrimonios fue " 
                + rr.get().eval("iconv(temp$x[1], 'utf8')").asString().toLowerCase() +
                " totalizando un  " + getDf().format(rr.get().eval("temp$y[1]").asDouble())+ 
                "\\% de los casos, seguido de " +
                rr.get().eval("iconv(temp$x[2],'utf8')").asString().toLowerCase() + 
                ", día en el que se registraron un " + getDf().format(rr.get().eval("temp$y[2]").asDouble())
                +"\\%, de los matrimonios. El "+ rr.get().eval("iconv(temp$x[length(temp$x)],'utf8')").asString().toLowerCase() + 
                " fue el día que registro menos matrimonios, contabilizando el " + 
                formatearNumero(rr.get().eval("temp$y[length(temp$y)]").asDouble())
                + "\\%. ", 
                "",
                "Distribución porcentual de matrimonios por día de la semana de ocurrencia",
                getFormatoSubtituloG(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_03.tex} "
                + "\\end{tikzpicture}",
                "INE, con datos del RENAP", "");
        
        
        rr.get().eval("temp <- excluirNiveles(vitales$'4_04')");
        String columna2 = columna("4_04",
                "Matrimonios según régimen económico", 
                "La distribución de los matrimonios realizados según la clase "
                + "de unión, permite identificar el establecimiento económico y "
                + "patrimonial por el que las nuevas familias inician.",
                "Del total de matrimonios registrados en el " + getFormatoTrimestre() +
                " la mayoría se efectuaron bajo el régimen de  "
                +rr.get().eval("iconv(temp$x[1],'utf8')").asString().toLowerCase()+
                ", representando estos un " +
                formatearNumero(rr.get().eval("temp$y[1]").asDouble())+ "\\% del total de matrimonios. "
                + getMenorRegimen(rr.get().eval("iconv(temp$x[length(temp$x)],'utf8')").asString(),
                       rr.get().eval("temp$y[length(temp$y)]").asDouble()) , 
                "Distribución porcentual de matrimonios por clase de unión", 
                getFormatoSubtituloG(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_04.tex} "
                + "\\end{tikzpicture}",
                "INE, con datos del RENAP", ""); 
        
        escribirLinea(hojaTrimestral(columna1, columna2));
    }

    private String getMenorRegimen(String valor, Double porcentaje) {
        String regimen = "";
        if(comparador.compare(valor,"Comunidad de ganaciales") == 0){
            regimen = " El régimen menos utilizado por las parejas fue el de comunidad de"
                    + " gananciales.";
        }else if(comparador.compare(valor,"No especificado") == 0){
            regimen = " En un " + formatearNumero(porcentaje) + "\\% de los matrimonios "
                    + "no se especificó el régimen económico bajo el cual se realizó la unión.";
        }else if(comparador.compare(valor, "Comunidad absoluta") == 0){
            regimen = " El régimen menos utilizado por las parejas fue el de comunidad absoluta";
        }else if(comparador.compare(valor, "Separación absoluta") == 0){
            regimen = " El régimen menos utilizado por las parejas fue el de separación absoluta.";
        }
        return regimen;
    }

    private void section5_01() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA 15%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        String columna1 =  columna("5_01",
                "Divorcios",
                "La serie histórica de los divorcios permite identificar "
                + "tendencias de este tipo de acciones en el tiempo.", 
                "La serie histórica de divorcios muestra que durante " + getFormatoTrimestre() +
                " se registraron " + getDf().format(rr.get().eval("vitales$'5_01'$y[9]").asDouble())+ 
                " , esto es " + rr.get().eval("iconv(calcularRespuestaPor(vitales$'5_01'), 'utf8')").asString()
                +" el mismo período " + " del año anterior y "+ getDf().format(rr.get().eval("cambioInterAnual(vitales$'5_01', ultimaPos = 9, primeraPos = 8)").asDouble())
                + "\\% menos de lo registrado en el trimestre anterior. ",
                 "Divorcios por trimestre",
                formatoSerie,
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{5_01.tex} "
                + "\\end{tikzpicture}",
                "INE, con datos del RENAP",
                "");
        
        rr.get().eval("temp <- vitales$'5_02'[ordenarNiveles(vitales$'5_02',T),]");
        String columna2 = columna("5_02",
                "Divorcios por departamento de ocurrencia",
                "En este apartado se muestra la distribución de los divoricios "
                + "realizados durante el " + getFormatoTrimestre() +" en los departamentos. ", 
                "La gráfica muestra que el departamento donde ocurrió la mayor proporción de divorcios  "
                + "fue " + rr.get().eval("iconv(temp$x[1],'utf8')").asString() + " con un " + 
                getDf().format(rr.get().eval("temp$y[1]/vitales$'5_01'$y[9] *100").asDouble()) + "\\% "
                + "seguido de " + rr.get().eval("iconv(temp$x[2],'utf8')").asString() + " con el " +
                getDf().format(rr.get().eval("temp$y[2]/vitales$'5_01'$y[9] *100").asDouble()) + "\\% y "
                + rr.get().eval("iconv(temp$x[3],'utf8')").asString() + " con " + 
                getDf().format(rr.get().eval("temp$y[3]/vitales$'5_01'$y[9] *100").asDouble()) + "\\%."
                + " Los departamentos con menor proporción de divorcios realizados son "
                + rr.get().eval("iconv(temp$x[length(temp$x)],'utf8')").asString() +  " con " +
                getDf().format(rr.get().eval("temp$y[length(temp$x)]/vitales$'5_01'$y[9] *100").asDouble()) + "\\% y "+
                rr.get().eval("iconv(temp$x[length(temp$x)-1],'utf8')").asString() + " con " + 
                getDf().format(rr.get().eval("temp$y[length(temp$x)-1]/vitales$'5_01'$y[9] *100").asDouble()) + "\\%." , 
                "Número de divorcios por departamento de ocurrencia", 
                getFormatoSubtituloG(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{5_02.tex}  \\end{tikzpicture}",
                "INE, con datos del RENAP", "");
    
        escribirLinea(hojaTrimestral(columna1, columna2));
    
    }



}
