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
    private String formatoTrimestre;
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
        formatoSerie = "República de Guatemala, serie histórica trimestral, ";
        formatoTrimestre = "República de Guatemala, "+corregirTrimestre(getTrimestre()).toLowerCase() + " trimestre "
                + getAnioPublicacion() +", ";
                
                
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
        introCapitulos.add("Las estadísticas de jóvenes que participan en hechos delictivos menores, " +
                "ayudan a detectar las características de los mismos, así como su distribución " +
                "geográfica en el país.");
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
                REXP listadoCSV = rr.get().eval("faltas <- cargaMasiva('" +  ruta +"', codificacion = 'utf8')");
                REXP nombres = rr.get().eval("names(faltas)");
                System.out.println(listadoCSV);
                System.out.println(nombres);
            }
    }
    
    protected ArrayList cargarCapitulo1(){
        ArrayList cap1 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("1_01");
        seccion1.add("Faltas judiciales");
        seccion1.add("Personas que cometieron faltas judiciales");
        seccion1.add(formatoSerie+"en unidades");
        seccion1.add("1_01.tex");
        seccion1.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion1.add(true);
        seccion1.add("1_02");
        seccion1.add("Faltas judiciales por departamento");
        seccion1.add("Distribución porcentual de las personas que "
                + "cometieron faltas judiciales, según departamento");
        seccion1.add(formatoTrimestre +"en porcentaje");
        seccion1.add("1_02.tex");
        seccion1.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion1.add(true);
        cap1.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("1_03");
        seccion2.add("Faltas judiciales por área geográfica");
        seccion2.add("Distribución porcentual de las personas que "
                + "cometieron faltas judiciales, según área geográfica");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("1_03.tex");
        seccion2.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion2.add(true);
        seccion2.add("1_04");
        seccion2.add("Faltas judiciales por edad del infractor");
        seccion2.add("Distribución porcentual de las personas que "
                + "cometieron faltas judiciales, según grupo de edad "
                + "del infractor");
        seccion2.add(formatoTrimestre+"en porcentaje");
        seccion2.add("1_04.tex");
        seccion2.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion2.add(true);
        cap1.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("1_05");
        seccion3.add("Faltas judiciales según sexo");
        seccion3.add("Distribución porcentual de las personas que "
                + "cometieron faltas judiciales, según sexo");
        seccion3.add(formatoTrimestre + "en porcentaje");
        seccion3.add("1_05.tex");
        seccion3.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion3.add(true);
        seccion3.add("1_06");
        seccion3.add("Faltas judiciales por grupo étnico");
        seccion3.add("Distribución porcentual de las personas que "
                + "cometieron faltas judiciales, según grupo étnico");
        seccion3.add(formatoTrimestre + "en porcentaje");
        seccion3.add("1_06.tex");
        seccion3.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion3.add(true);
        cap1.add(seccion3);
        
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("1_07");
        seccion4.add("Faltas judiciales por alfabetismo");
        seccion4.add("Distribución porcentual de las personas que "
                + "cometieron faltas judiciales, según condición de "
                + "alfabetismo");
        seccion4.add(formatoTrimestre + "en porcentaje");
        seccion4.add("1_07.tex");
        seccion4.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion4.add(true);
        seccion4.add("1_08");
        seccion4.add("Faltas judiciales por nivel de educación");
        seccion4.add("Distribución porcentual de las personas que "
                + "cometieron faltas judiciales, según nivel de "
                + "educación");
        seccion4.add(formatoTrimestre + "en porcentaje");
        seccion4.add("1_08.tex");
        seccion4.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion4.add(true);
        cap1.add(seccion4);
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("1_09");
        seccion5.add("Faltas judiciales por condición de ebriedad");
        seccion5.add("Distribución porcentual de las personas que "
                + "cometieron faltas judiciales, según condición de ebriedad");
        seccion5.add(formatoTrimestre + "en porcentaje");
        seccion5.add("1_09.tex");
        seccion5.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion5.add(true);
        seccion5.add("1_10");
        seccion5.add("Faltas judiciales por tipo de falta");
        seccion5.add("Distribución porcentual de las personas que "
                + "cometieron faltas judiciales, según tipo de falta "
                + "cometida");
        seccion5.add(formatoTrimestre + "en porcentaje");
        seccion5.add("1_10.tex");
        seccion5.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion5.add(true);
        cap1.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("1_11");
        seccion6.add("Faltas judiciales por sexo del infractor según tipo de"
        + " falta cometida");
        seccion6.add("Distribución porcentual de las personas que "
                + "cometieron faltas judiciales, según sexo y "
                + "tipo de falta cometida");
        seccion6.add(formatoTrimestre + "en porcentaje");
        seccion6.add("1_11.tex");
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
        seccion1.add("Personas menores de edad que cometieron faltas judiciales");
        seccion1.add(formatoSerie + "en unidades");
        seccion1.add("2_01.tex");
        seccion1.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion1.add(true);
        seccion1.add("2_02");
        seccion1.add("Infractores menores de edad por sexo");
        seccion1.add("Distribución porcentual de las personas menores de edad"
                + " que cometieron faltas judiciales, según sexo");
        seccion1.add(formatoTrimestre + "en porcentaje");
        seccion1.add("2_02.tex");
        seccion1.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion1.add(true);
        cap2.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("2_03");
        seccion2.add("Infractores menores de edad por edad");
        seccion2.add("Distribución porcentual de las personas menores de edad"
                + " que cometieron faltas judiciales, según edad simple");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("2_03.tex");
        seccion2.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion2.add(true);
        seccion2.add("2_04");
        seccion2.add("Infractores menores de edad por grupo étnico");
        seccion2.add("Distribución porcentual de las personas menores de edad"
                + " que cometieron faltas judiciales, según grupo étnico");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("2_04.tex");
        seccion2.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion2.add(true);
        cap2.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("2_05");
        seccion3.add("Infractores menores de edad por nivel de educación");
        seccion3.add("Distribución porcentual de las personas menores de edad"
                + " que cometieron faltas judiciales, según nivel de educación");
        seccion3.add(formatoTrimestre + "en porcentaje");
        seccion3.add("2_05.tex");
        seccion3.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion3.add(true);
        seccion3.add("2_06");
        seccion3.add("Infractores menores de edad por tipo de falta cometida");
        seccion3.add("Distribución porcentual de las personas menores de edad"
                + " que cometieron faltas judiciales, según nivel de educación");
        seccion3.add(formatoTrimestre + "en porcentaje");
        seccion3.add("2_06.tex");
        seccion3.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion3.add(true);
        cap2.add(seccion3);
        
        return cap2;        
    }
    
    
    
    protected ArrayList cargarCapitulo3(){
        ArrayList cap3 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("3_01");
        seccion1.add("Faltas contra las personas");
        seccion1.add("Personas que cometieron faltas judiciales del tipo "
                + "contra las personas");
        seccion1.add(formatoSerie + "en unidades");
        seccion1.add("3_01.tex");
        seccion1.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion1.add(true);
        seccion1.add("3_02");
        seccion1.add("Faltas contra la propiedad");
        seccion1.add("Personas que cometieron faltas judiciales del tipo "
                + "contra la propiedad");
        seccion1.add(formatoSerie + "en unidades");
        seccion1.add("3_02.tex");
        seccion1.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion1.add(true);
        cap3.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("3_03");
        seccion2.add("Faltas contra las buenas costumbres");
        seccion2.add("Personas que cometieron faltas judiciales del tipo "
                + "contra las buenas costumbres");
        seccion2.add(formatoSerie + "en unidades");
        seccion2.add("3_03.tex");
        seccion2.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion2.add(true);
        seccion2.add("3_04");
        seccion2.add("Faltas contra el orden público");
        seccion2.add("Personas que cometieron faltas judiciales del tipo "
                + "contra el orden público");
        seccion2.add(formatoSerie + "en unidades");
        seccion2.add("3_04.tex");
        seccion2.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion2.add(true);
        cap3.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("3_05");
        seccion3.add("Otras faltas");
        seccion3.add("Personas que cometieron faltas judiciales de "
                + "otro tipo no clasificado anteriormente");
        seccion3.add(formatoSerie + "en unidades");
        seccion3.add("3_05.tex");
        seccion3.add("INE, con datos de los juzgados de paz del Organismo Judicial");
        seccion3.add(true);
        cap3.add(seccion3);
        
        
        return cap3;        
    }
    private void apendice1(){
        String columna1 = tablaApendice("A_01",
                "Faltas judiciales: análisis de variación",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos de los juzgados de paz del Organismo Judicial",
                "");
        
        String columna2 = tablaApendice("A_02",
                "Faltas contra las personas: análisis de variación",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos de los juzgados de paz del Organismo Judicial",
                "");
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    
    
    private void apendice2(){
        String columna1 = tablaApendice("A_03",
                "Faltas contra la propiedad: análisis de variación",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos de los juzgados de paz del Organismo Judicial",
                "");
        
        String columna2 = tablaApendice("A_04",
                "Faltas contra el orden público: análisis de variación",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos de los juzgados de paz del Organismo Judicial",
                "");
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    private void apendice3(){
        String columna1 = tablaApendice("A_05",
                "Faltas contra las buenas costumbres: análisis de variación",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos de los juzgados de paz del Organismo Judicial",
                "");
        
        String columna2 = "";
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    protected void apendices(String rutaTEX){
        escribirLinea("\n \\appendixa \n" +
        "\n" +
        "\n" +
        "\n" );
        apendice1();
        apendice2();
        apendice3();
        Tabla ap = new Tabla(rutaTEX,trimestres(),rr);
        ap.setRuta("/var/www/html/FaltasJudiciales/Entradas/CSV");
        ap.generarFaltasJudiciales();
        
        
        
    }
    
    protected void generarGraficas(String modalidad){
        System.out.println("GENERANDO LAS GRAFICAS");
        Grafica vitales = new Grafica("faltas", getRuta(), rr.get(), modalidad);
        vitales.start();
    }
    
    protected void hacerPortada(){
       File source = new File("/home/ineservidor/FaltasJudiciales/Caratula");
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
                "	Karin Lenisse Barrios Carrascosa\\\\\n" +                
                "	Mirta Rubidia Marín Hernández\\\\[0.8cm]\n" +
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
                "\\indent El Instituto Nacional de Estadística -INE- en cumplimiento a su Ley Orgánica, "
                + "Decreto Ley 3-85, "
                + "presenta datos sobre las {\\Bold Faltas Judiciales del "
                + corregirTrimestre(getTrimestre()).toLowerCase() + " trimestre del "
                + getAnioPublicacion() +"}, las cuales son los delitos menores, contemplados en el "
                + "código penal en el artículo 480, correspondiente al Libro Tercero de las Faltas, "
                + "Título Único.\n" +
                "\n" +
                "Los datos son recolectados a través de la boleta 42 B, que registras las características "
                + "y datos generales de las personas que cometieron las faltas, proporcionados por los "
                + "Juzgados de Paz y Juzgados de Paz Móviles de todo el país, la cual es {\\Bold información "
                + " preliminar} y será ajustada con el ingreso de registros tardíos.\n" +
                "\n" +
                "Se pone a disposición el presente informe de Faltas Judiciales del "
                + corregirTrimestre(getTrimestre()).toLowerCase() + " trimestre del "
                + getAnioPublicacion() 
                + " con el fin de apoyar la elaboración de programas, planes, en materia de "
                + "seguridad nacional. A su vez se agradece el aporte y colaboración de los "
                + "Juzgados y se les insta a continuar el apoyo a este proceso.\n" +
                "\n" +
                "\\thispagestyle{empty}\n" +
                "\n" +
                "\n" +
                "\\cleardoublepage");
    }
    

    
   
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

