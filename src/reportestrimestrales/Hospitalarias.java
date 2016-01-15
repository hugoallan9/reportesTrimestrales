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
public class Hospitalarias extends Documento{
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
    
    
    
    
    public Hospitalarias(String titulo, String trimestre, String pYear, String rutaCSV) {
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
        capitulos.add("Morbilidad ambulatoria");
        capitulos.add("Morbilidad hospitalaria");
        
    }
    
    protected void setIntroCapitulos(){
        introCapitulos.add("Morbilidad ambulatoria (de consulta externa y urgencias), fuera de las áreas de hospitalización.");
        introCapitulos.add("Morbilidad hospitalaria (en pacientes internados), es decir que ocuparon camas censales.");
        
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
                
                String columna1="";
                String columna2="";
                escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA "+hoja+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
                System.out.println("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA "+hoja+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
                ArrayList tmp = (ArrayList)tmpSeccion.get(j);
                
                columna1 = columna(tmp.get(0).toString(),tmp.get(1).toString(),"",
                        "",tmp.get(2).toString(),tmp.get(3).toString(),
                        tmp.get(4).toString(),tmp.get(5).toString(),"",(Boolean)tmp.get(6)  );
                
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
                REXP listadoCSV = rr.get().eval("hospitalarias <- cargaMasiva('" +  ruta +"', codificacion = 'utf8')");
                REXP nombres = rr.get().eval("names(hospitalarias)");
                System.out.println(listadoCSV);
                System.out.println(nombres);
            }
    }
    
    protected ArrayList cargarCapitulo1(){
        ArrayList cap1 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("1_01");
        seccion1.add("Consulta externa");
        seccion1.add("Atenciones de consulta externa en hospitales privados");
        seccion1.add(formatoSerie + "en miles de unidades");
        seccion1.add("1_01.tex");
        seccion1.add("INE, Unidad de Estadísticas de Salud");
        seccion1.add(true);
        seccion1.add("1_02");
        seccion1.add("Consulta externa por departamento");
        seccion1.add("Atenciones de consulta externa e hospitales privados, "
                + "por departamento de residencia del paciente");
        seccion1.add(formatoTrimestre + "en miles de unidades");
        seccion1.add("1_02.tex");
        seccion1.add("INE, Unidad de Estadísticas de Salud");
        seccion1.add(true);
        cap1.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("1_03");
        seccion2.add("Consulta externa por grupos de edad");
        seccion2.add("Distribución porcentual de atenciones en consulta "
                + "externa en hospitales privados, según rango de edad del paciente");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("1_03.tex");
        seccion2.add("INE, Unidad de Estadísticas de Salud");
        seccion2.add(true);
        seccion2.add("1_04");
        seccion2.add("Consulta externa en pacientes menores de cinco años");
        seccion2.add("Porcentaje de atenciones por consulta externa en "
                + "hospitales privados a pacientes menores de cinco años");
        seccion2.add(formatoSerie + "en porcentaje");
        seccion2.add("1_04.tex");
        seccion2.add("INE, Unidad de Estadísticas de Salud");
        seccion2.add(true);
        cap1.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("1_05");
        seccion3.add("Consulta externa por sexo del paciente");
        seccion3.add("Distribución porcentual de atenciones por sexo del paciente");
        seccion3.add(formatoTrimestre + "en porcentaje");
        seccion3.add("1_05.tex");
        seccion3.add("INE, Unidad de Estadísticas de Salud");
        seccion3.add(true);
        seccion3.add("1_06");
        seccion3.add("Consulta externa por grupo étnico del paciente");
        seccion3.add("Distribución porcentual de atenciones externas en hospitales "
                + "privados, por grupo étnico del paciente");
        seccion3.add(formatoTrimestre + "en porcentaje");
        seccion3.add("1_06.tex");
        seccion3.add("INE, Unidad de Estadísticas de Salud");
        seccion3.add(true);
        cap1.add(seccion3);
        
        return cap1;        
    }
    protected ArrayList cargarCapitulo2(){
        ArrayList cap2 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("2_01");
        seccion1.add("Consulta Interna");
        seccion1.add("Atenciones de consulta interna en hospitales privados");
        seccion1.add(formatoSerie + "en unidades");
        seccion1.add("2_01.tex");
        seccion1.add("INE, Unidad de Estadísticas de Salud");
        seccion1.add(true);
        seccion1.add("2_02");
        seccion1.add("Consulta interna por departamento");
        seccion1.add("Atenciones de consulta interna en hospitales privados, "
                + "por departamento de residencia del paciente");
        seccion1.add(formatoTrimestre + "en unidades");
        seccion1.add("2_02.tex");
        seccion1.add("INE, Unidad de Estadísticas de Salud");
        seccion1.add(true);
        cap2.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("2_03");
        seccion2.add("Consulta interna por grupos de edad");
        seccion2.add("Distribución porcentual de atenciones de consulta interna en "
                + "hospitales privados, según rangos de edad del paciente");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("2_03.tex");
        seccion2.add("INE, Unidad de Estadísticas de Salud");
        seccion2.add(true);
        seccion2.add("2_04");
        seccion2.add("Consulta interna en pacientes menores de cinco años");
        seccion2.add("Porcentaje de atenciones por consulta interna en "
                + "hospitales privados, en pacientes menores de cinco años");
        seccion2.add(formatoSerie + "en porcentaje");
        seccion2.add("2_04.tex");
        seccion2.add("INE, Unidad de Estadísticas de Salud");
        seccion2.add(true);
        cap2.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("2_05");
        seccion3.add("Consulta interna por sexo del paciente");
        seccion3.add("Distribución porcentual de atenciones por consulta interna "
                + "en hospitales privados, por sexo del paciente");
        seccion3.add(formatoTrimestre + "en porcentaje");
        seccion3.add("2_05.tex");
        seccion3.add("INE, Unidad de Estadísticas de Salud");
        seccion3.add(true);
        seccion3.add("2_06");
        seccion3.add("Consulta interna por grupo étnico del paciente");
        seccion3.add("Distribución orcentual de atenciones por consulta interna "
                + "en hospitales privados, por grupo étnico del paciente");
        seccion3.add(formatoTrimestre + "en porcentaje");
        seccion3.add("2_06.tex");
        seccion3.add("INE, Unidad de Estadísticas de Salud");
        seccion3.add(true);
        cap2.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("2_07");
        seccion4.add("Consulta interna por tratamiento");
        seccion4.add("Distribución porcentual de atenciones por consulta interna "
                + "en hospitales nacionales, segun tratamiento");
        seccion4.add(formatoTrimestre + "en porcentaje");
        seccion4.add("2_07.tex");
        seccion4.add("INE, Unidad de Estadísticas de Salud");
        seccion4.add(true);
        seccion4.add("2_08");
        seccion4.add("Consulta interna por promedio de días de estancia del paciente");
        seccion4.add("Promedio de días de estancia en hospitales privados");
        seccion4.add(formatoSerie + "en unidades");
        seccion4.add("2_08.tex");
        seccion4.add("INE, Unidad de Estadísticas de Salud");
        seccion4.add(true);
        cap2.add(seccion4);
        
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("2_09");
        seccion5.add("Consulta interna por condición de egreso del paciente");
        seccion5.add("Distribución porcentual de las consultas internas "
                + "realizadas en hospitales privados, "
                + "por condición de egreso del paciente");
        seccion5.add(formatoTrimestre + "en porcentaje");
        seccion5.add("2_09.tex");
        seccion5.add("INE, Unidad de Estadísticas de Salud");
        seccion5.add(true);
        cap2.add(seccion5);
        
        
        return cap2;        
    }
    
    
    private void t3_01_02(){
        String columna1 = tablaApendice("A_01",
                "Consulta externa por trimestre de ocurrencia, según departamento de residencia del paciente",
                "1",
                "plantillaTabla1.pdf",
                "INE, Unidad de Estadísticas de Salud",
                "");
        
        String columna2 = tablaApendice("A_02",
                "Análisis de variación de consulta externa",
                "2",
                "plantillaTabla2.pdf",
                "INE, Unidad de Estadísticas de Salud",
                "");
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    
    private void t3_03_04(){
        String columna1 = tablaApendice("A_03",
                "Consulta interna por trimestre de ocurrencia, según departamento de residencia del paciente",
                "1",
                "plantillaTabla1.pdf",
                "INE, Unidad de Estadísticas de Salud",
                "");
        
        String columna2 = tablaApendice("A_04",
                "Análisis de variación de consulta interna",
                "2",
                "plantillaTabla2.pdf",
                "INE, Unidad de Estadísticas de Salud",
                "");
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    protected void cargarCapitulo3(){
       t3_01_02();
       t3_03_04();           
    }
    protected void apendices(String rutaTEX){
    escribirLinea("\n \\appendixa \n" +
    "\n" +
    "\n" +
    "\n" );
    cargarCapitulo3();
    Tabla ap = new Tabla(rutaTEX,trimestres(),rr);
    ap.setRuta("/var/www/html/Hospitalarias/Entradas/CSV");
    ap.generarHospitalarias();
    
    
    }
    
    
    
    protected void generarGraficas(String modalidad){
        System.out.println("GENERANDO LAS GRAFICAS");
        Grafica hospitalarias = new Grafica("hospitalarias", getRuta(), rr.get(), modalidad);
        hospitalarias.start();
    }
    
    protected void hacerPortada(){
        File source = new File("/home/ineservidor/Hospitalarias/Caratula");
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
                "	Edgar Guillermo Solares\\\\[0.8cm]\n" +
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
                + "de salud y siendo el ente rector de la política estadística nacional "
                + "en Guatemala, en cumplimiento a su Ley Orgánica, Decreto Ley 3-85, se "
                + "complace en presentar el siguiente informe, que contiene las {\\Bold Estadísticas Hospitalarias del sector privado}"
                + ", con información correspondiente al {\\Bold " + corregirTrimestre(getTrimestre()).toLowerCase() + " trimestre del "
                + getAnioPublicacion() +"}, información esencial para la planificación de la salud.\n" +
                "\n" +
                "La información presentada a continuación fue recolectada a través "
                + "de los distintos hospitales, sanatorios, casas de salud, hospitales privados "
                + "de día de la República de Guatemal en el " + getAnioPublicacion() + ". \\\\ \\\\"
                + "{\\Bold Las cifras del  "  +  corregirTrimestre(getTrimestre()).toLowerCase() +  " trimestre del "  + getAnioPublicacion()
                + " son preliminares} y serán ajustadas con el ingreso de los registros tardíos.\\\\ \\\\"+
                
                "Por lo tanto, el INE se complace en presentar este informe, con "
                + "el propósito de brindar una herramienta más de análisis a la "
                + "población guatemalteca. A la vez agradece el aporte y colaboración "
                + "a todos los centros hospitalarios privados del país, a quienes se insta  a "
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

