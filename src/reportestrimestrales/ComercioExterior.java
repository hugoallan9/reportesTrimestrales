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
        seccion1.add("Exportaciones totales");
        seccion1.add("Comercio Total: exportaciones trimestrales en millones de US$");
        seccion1.add(formatoSerie);
        seccion1.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_01.tex}  \\end{tikzpicture}");
        seccion1.add("INE, con datos del BANGUAT");
        seccion1.add(true);
        seccion1.add("1_02");
        seccion1.add("Variación interanual de las exportaciones");
        seccion1.add("Porcentaje de variación interanual de las exportaciones");
        seccion1.add(formatoSerie);
        seccion1.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_02.tex}  \\end{tikzpicture}");
        seccion1.add("INE, con datos del BANGUAT");
        seccion1.add(true);
        cap1.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("1_03");
        seccion2.add("Importaciones totales");
        seccion2.add("Comercio total: importaciones trimestrales en millones de US$");
        seccion2.add(formatoSerie);
        seccion2.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_03.tex}  \\end{tikzpicture}");
        seccion2.add("INE, con datos del BANGUAT");
        seccion2.add(true);
        seccion2.add("1_04");
        seccion2.add("Faltas judiciales por edad del infractor");
        seccion2.add("Faltas judiciales ocurridas en el año 2014");
        seccion2.add("Distribución porcentual por edad del infractor");
        seccion2.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_04.tex}  \\end{tikzpicture}");
        seccion2.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion2.add(true);
        cap1.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("1_05");
        seccion3.add("Faltas judiciales según sexo");
        seccion3.add("Faltas judiciales ocurridas en el año 2014");
        seccion3.add("Distribución porcentual por sexo");
        seccion3.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_05.tex}  \\end{tikzpicture}");
        seccion3.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion3.add(true);
        seccion3.add("1_06");
        seccion3.add("Faltas judiciales por grupo étnico");
        seccion3.add("Faltas judiciales ocurridas en el año 2014");
        seccion3.add("Distribución porcentual por grupo étnico");
        seccion3.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_06.tex}  \\end{tikzpicture}");
        seccion3.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion3.add(true);
        cap1.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("1_07");
        seccion4.add("Faltas judiciales por analfabetismo");
        seccion4.add("Faltas judiciales ocurridas en el año 2014");
        seccion4.add("Distribución porcentual por condición de analfabetismo");
        seccion4.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_07.tex}  \\end{tikzpicture}");
        seccion4.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion4.add(true);
        seccion4.add("1_08");
        seccion4.add("Faltas judiciales por nivel de educación");
        seccion4.add("Faltas judiciales ocurridas en el año 2014");
        seccion4.add("Distribución porcentual por nivel de educación");
        seccion4.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_08.tex}  \\end{tikzpicture}");
        seccion4.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion4.add(true);
        cap1.add(seccion4);
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("1_09");
        seccion5.add("Faltas judiciales por condición de ebriedad");
        seccion5.add("Faltas judiciales ocurridas en el año 2014");
        seccion5.add("Distribución porcentual por condición de ebriedad");
        seccion5.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_09.tex}  \\end{tikzpicture}");
        seccion5.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion5.add(true);
        seccion5.add("1_10");
        seccion5.add("Faltas judiciales por tipo de falta");
        seccion5.add("Faltas judiciales ocurridas en el año 2014");
        seccion5.add("Distribución porcentual por tipo de falta");
        seccion5.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_10.tex}  \\end{tikzpicture}");
        seccion5.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion5.add(true);
        cap1.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("1_11");
        seccion6.add("Faltas judiciales por sexo del infractor según tipo de"
        + " falta cometida");
        seccion6.add("Faltas judiciales ocurridas en el año 2014");
        seccion6.add("Distribución porcentual por tipo de falta cometida");
        seccion6.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_11.tex}  \\end{tikzpicture}");
        seccion6.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion6.add(true);
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
    
   

        



}

