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
public class Agropecuarias extends Documento{
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
    
    
    
    
    public Agropecuarias(String titulo, String trimestre, String pYear, String rutaCSV) {
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
        capitulos.add("Productos de origen vegetal");
        capitulos.add("Productos de origen animal");                
    }
    
    protected void setIntroCapitulos(){
        introCapitulos.add("Son productos de origen vegetal los frutos que ofrece la naturaleza, "
                + "los cuales son ricos en sustancias nutritivas y ayudan a mantener el bienestar "
                + "de todo el organismo de las personas. Este capítulo presenta las estadísticas "
                + "más relevantes que están disponibles, como: azúcar, trigo y banano. De éstos "
                + "se investigan las variables: producción, productos y subproductos.");
        introCapitulos.add("Los productos de origen animal provienen directamente de los animales, "
                + "son fuentes de proteínas, entre otros, importantes para el buen funcionamiento "
                + "del organismo humano. \\\\ Este capítulo presenta las estadísticas más relevantes "
                + "que están disponibles. Las variables que se investigan son: número de cabezas de "
                + "ganado mayor y menor (bovino, porcino, ovino y caprino), para consumo interno.");        
    }
    protected void setContenidos(){
         contenidos.add(cargarCapitulo1());
         contenidos.add(cargarCapitulo2());
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
                String parrafo2="";
                String columna1="";
                String columna2="";
                if(i==0 &&j==1) parrafo2 = "En el "+ getFormatoTrimestre() + ", de la molienda se "
                        + "obtuvieron dos productos principales: harina y sémola.";
                    
                escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA "+hoja+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
                System.out.println("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA "+hoja+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
                ArrayList tmp = (ArrayList)tmpSeccion.get(j);
                
                columna1 = columna(tmp.get(0).toString(),tmp.get(1).toString(),"",
                        "",tmp.get(2).toString(),tmp.get(3).toString(),
                        tmp.get(4).toString(),tmp.get(5).toString(),"",(Boolean)tmp.get(6));
                try{
                columna2 = columna(tmp.get(7).toString(),tmp.get(8).toString(),"",
                        parrafo2,tmp.get(9).toString(),tmp.get(10).toString(),
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
                REXP listadoCSV = rr.get().eval("agropecuarias <- cargaMasiva('" +  ruta +"')");
                REXP nombres = rr.get().eval("names(agropecuarias)");
                System.out.println(listadoCSV);
                System.out.println(nombres);
            }
    }
    
    protected ArrayList cargarCapitulo1(){
        ArrayList cap1 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("1_01");
        seccion1.add("Producción de azúcar");
        seccion1.add("Producción de azúcar en los ingenios de la república "
                + "en millones de quintales");
        seccion1.add(formatoSerie);
        seccion1.add("1_01.tex");
        seccion1.add("INE, Estadísticas Agropecuarias");
        seccion1.add(true);
        seccion1.add("1_02");
        seccion1.add("Producción de azúcar blanca");
        seccion1.add("Producción de azúcar blanca en los ingenios de la república "
                + "en millones de quintales");
        seccion1.add(formatoSerie);
        seccion1.add("1_02.tex");
        seccion1.add("INE, Estadísticas Agropecuarias");
        seccion1.add(true);
        cap1.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("1_03");
        seccion2.add("Producción de azúcar cruda");
        seccion2.add("Producción de azúcar cruda en los ingenios de la república "
                + "en millones de quintales");
        seccion2.add(formatoSerie);
        seccion2.add("1_03.tex");
        seccion2.add("INE, Estadísticas Agropecuarias");
        seccion2.add(true);
        seccion2.add("1_04");
        seccion2.add("Trigo");
        seccion2.add("Producción de productos y subproductos de trigo en miles de quintales");
        seccion2.add(formatoSerie);
        seccion2.add("1_04.tex");
        seccion2.add("INE, Estadísticas Agropecuarias");
        seccion2.add(true);
        cap1.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("1_05");
        seccion3.add("Harina de trigo");
        seccion3.add("Producto del trigo: harina en miles de quintales");
        seccion3.add(formatoSerie);
        seccion3.add("1_05.tex");
        seccion3.add("INE, Estadísticas Agropecuarias");
        seccion3.add(true);
        seccion3.add("1_06");
        seccion3.add("Sémola de trigo");
        seccion3.add("Subproducto de trigo: sémola en miles de quintales");
        seccion3.add(formatoSerie);
        seccion3.add("1_06.tex");
        seccion3.add("INE, Estadísticas Agropecuarias");
        seccion3.add(true);
        cap1.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("1_07");
        seccion4.add("Afrecho");
        seccion4.add("Subproducto de trigo: afrecho en miles de quintales");
        seccion4.add(formatoSerie);
        seccion4.add("1_07.tex");
        seccion4.add("INE, Estadísticas Agropecuarias");
        seccion4.add(true);
        seccion4.add("1_08");
        seccion4.add("Granillo de trigo");
        seccion4.add("Subproducto de trigo: granillo en miles de quintales");
        seccion4.add(formatoSerie);
        seccion4.add("1_08.tex");
        seccion4.add("INE, Estadísticas Agropecuarias");
        seccion4.add(true);
        cap1.add(seccion4);
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("1_09");
        seccion5.add("Germen de trigo");
        seccion5.add("Subproducto de trigo: germen en quintales");
        seccion5.add(formatoSerie);
        seccion5.add("1_09.tex");
        seccion5.add("INE, Estadísticas Agropecuarias");
        seccion5.add(true);
        seccion5.add("1_10");
        seccion5.add("Banano");
        seccion5.add("Producción de banano de las compañías exportadoras en miles de quintales");
        seccion5.add(formatoSerie);
        seccion5.add("1_10.tex");
        seccion5.add("INE, Estadísticas Agropecuarias");
        seccion5.add(true);
        cap1.add(seccion5);
        
        return cap1;        
    }
    protected ArrayList cargarCapitulo2(){
        ArrayList cap2 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("2_01");
        seccion1.add("Ganado bovino");
        seccion1.add("Destace de ganado bovino en número de cabezas");
        seccion1.add(formatoSerie);
        seccion1.add("2_01.tex");
        seccion1.add("INE, con información de las municipalidades y empresas procesadoras de carne");
        seccion1.add(true);
        seccion1.add("2_02");
        seccion1.add("Ganado porcino");
        seccion1.add("Destace de ganado porcino en número de cabezas");
        seccion1.add(formatoSerie);
        seccion1.add("2_02.tex");
        seccion1.add("INE, con información de las municipalidades y empresas procesadoras de carne");
        seccion1.add(true);
        cap2.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("2_03");
        seccion2.add("Ganado ovino");
        seccion2.add("Destace de ganado ovino en número de cabezas");
        seccion2.add(formatoSerie);
        seccion2.add("2_03.tex");
        seccion2.add("INE, con información de las municipalidades y empresas procesadoras de carne");
        seccion2.add(true);
        seccion2.add("2_04");
        seccion2.add("Ganado caprino");
        seccion2.add("Destace de ganado caprino para consumo interno en número de cabezas");
        seccion2.add(formatoSerie);
        seccion2.add("2_04.tex");
        seccion2.add("INE, con información de las municipalidades y empresas procesadoras de carne");
        seccion2.add(true);
        cap2.add(seccion2);
        
        return cap2;        
    }
    
    
    
    
    private void apendice1(){
        String columna1 = tablaApendice("A_01",
                "Análisis de variación de la producción de azúcar blanca",
                "2",
                "plantillaTabla2.pdf",
                "INE, Estadísticas Agropecuarias",
                "");
        
        String columna2 = tablaApendice("A_02",
                "Análisis de variación de la producción de azúcar cruda",
                "2",
                "plantillaTabla2.pdf",
                "INE, Estadísticas Agropecuarias",
                "");
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    
    
    private void apendice2(){
        String columna1 = tablaApendice("A_03",
                "Análisis de variación del procesamiento de trigo",
                "2",
                "plantillaTabla2.pdf",
                "INE, Estadísticas Agropecuarias",
                "");
        
        String columna2 = tablaApendice("A_04",
                "Análisis de variación de la producción de banano",
                "2",
                "plantillaTabla2.pdf",
                "INE, Estadísticas Agropecuarias",
                "");
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    private void apendice3(){
        String columna1 = tablaApendice("A_05",
                "Análisis de variación del destace de ganado bovino",
                "2",
                "plantillaTabla2.pdf",
                "INE, con información de las municipalidades y empresas procesadoras de carne",
                "");
        
        String columna2 = tablaApendice("A_06",
                "Análisis de variación del destace de ganado porcino",
                "2",
                "plantillaTabla2.pdf",
                "INE, con información de las municipalidades y empresas procesadoras de carne",
                "");;
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    private void apendice4(){
        String columna1 = tablaApendice("A_07",
                "Análisis de variación del destace de ganado ovino",
                "2",
                "plantillaTabla2.pdf",
                "INE, con información de las municipalidades y empresas procesadoras de carne",
                "");
        
        String columna2 = tablaApendice("A_08",
                "Análisis de variación del destace de ganado caprino",
                "2",
                "plantillaTabla2.pdf",
                "INE, con información de las municipalidades y empresas procesadoras de carne",
                "");;
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    protected void generarGraficas(String modalidad){
        System.out.println("GENERANDO LAS GRAFICAS");
        Grafica agropecuarias = new Grafica("agropecuarias", getRuta(), rr.get(), modalidad);
        agropecuarias.start();
    }
    
    protected void hacerPortada(){File source = new File("/home/ineservidor/Agropecuarias/Caratula");
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
        protected void apendices(String rutaTEX){
        System.out.println("Generando tablas de apendices");
        escribirLinea("\n \\appendixa \n" +
        "\n" +
        "\n" +
        "\n" );
        apendice1();
        apendice2();
        apendice3();
        apendice4();
        System.out.println(rutaTEX);
        System.out.println(trimestres());
        System.out.println(rr);
        Tabla ap = new Tabla(rutaTEX,trimestres(),rr);
        System.out.println("Seteando ruta de ap");
        ap.setRuta("/var/www/html/Agropecuarias/Entradas/CSV");
        ap.generarAgropecuarias();
        
        
        
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
                "	Pedro Leonel Calvillo Flores\\\\\n" +
                "	Haydee Azucena Barrientos Osorio\\\\\n" +
                "	Fabiola Beatriz Ramírez Pinto\\\\[0.8cm]\n" +
                "	\n" +
                "	{\\Bold \\large \\color{color2} DIAGRAMACIÓN Y DISEÑO}\\\\[0.2cm]\n" +
                "	Hugo Allan García Monterrosa\\\\\n" +
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
                "\\indent El Instituto Nacional de Estadística -INE-,"
                + "consciente de la demanda de información económica y siendo el ente rector de la "
                + "política estadística nacional en Guatemala,"
                + " en cumplimiento a su Ley Orgánica, "
                + "Decreto Ley 3-85, "
                + "presenta el siguiente informe, que contiene las {\\Bold Estadísticas Agropecuarias}, "
                + "con información correspondiente al {\\Bold "
                + corregirTrimestre(getTrimestre()).toLowerCase() + " trimestre del "
                + getAnioPublicacion() +"}, la cual es {\\Bold preliminar} y será ajustada con el "
                + "ingreso de registros tardíos.\n" +
                "\n" +
                "El presente informe contiene información de: \n"
                + "\n"
                    
                + "\\begin{itemize}\n" +
"		\\item Productos de origen vegetal\n" +
"		\\item Productos de origen animal\n" +
"	\\end{itemize} \n"
                + "Esta edición se suma a las diferentes publicaciones del Instituto Nacional de Estadística, "
                + "que busca ofrecer a entidades oficiales, particulares y al público en general, "
                + "información confiable y oportuna que contribuya a facilitar la correcta toma de decisiones "
                + "por parte de las instituciones tanto públicas como privadas. \n"
                + "\n"
                + "Se deja constancia del agradecimiento a las Instituciones Nacionales, Empresas Privadas y "
                + "Personas Particulares que una vez más contribuyen para el logro de la presente publicación. \n"
                + "\n"
                + "Los comentarios y cualquier sugerencia que resulten del estudio de las cifras que hoy "
                + "se presentea, serán recibidos con interés por ser ésta la forma de lograr el mejoramiento "
                + "de toda publicación.\n" +
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

