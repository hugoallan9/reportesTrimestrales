/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author INE
 */
package reportestrimestrales;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Calendar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils; 

public class Documento {


    private String titulo;
    private String mes;
    private String year;
    private String trimestre;
    private List junta;
    private List gerencias;
    private String ruta;

    public String getRuta() {
        return ruta;
    }
    private File tex;
    private String anioPublicacion;
    private DecimalFormat df;
    private DecimalFormat df2;
    private String formatoTrimestre;

    public String getFormatoTrimestre() {
        return formatoTrimestre;
    }

    public String getFormatoSubtituloG() {
        return formatoSubtituloG;
    }
    private String formatoSubtituloG;

    

    
    public  Documento(String titulo, String trimestre, String pYear){
        this.titulo = titulo;
        this.trimestre = trimestre;
        Calendar cal = Calendar.getInstance();
        mes = new SimpleDateFormat("MMMM").format(cal.getTime());
        this.year = new SimpleDateFormat("yyyy").format(cal.getTime());
        junta = new  ArrayList();
        gerencias = new ArrayList();
        tex = null;
        this.anioPublicacion = pYear;
        df = new DecimalFormat("#,###.#");
        df2 = new DecimalFormat("#,###.##");
        formatoTrimestre =  corregirTrimestre(getTrimestre()).toLowerCase() + " trimestre del " + getAnioPublicacion();
        formatoSubtituloG =  corregirTrimestre(getTrimestre()) + " trimestre, año "+ getAnioPublicacion();
    }

    public DecimalFormat getDf() {
        return df;
    }
    
    public DecimalFormat getDf2(){
        return df2;
    }

    public String getAnioPublicacion() {
        return anioPublicacion;
    }
    
    

    public String getTitulo() {
        return titulo;
    }

    public String getMes() {
        return mes;
    }

    public String getYear() {
        return year;
    }

    public String getTrimestre() {
        return trimestre;
    }
    
    
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    
    public void setTex(String tex){
        this.tex = new File(ruta + "\\" + tex + ".tex");
    }
    
    
    protected void iniciarDocumento(){
        try {
            tex.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            FileWriter escritora = new FileWriter(tex,true);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write("\\renewcommand{\\titulodoc}{" + titulo + " " + trimestreToSimbolo(trimestre)+ "-"+anioPublicacion +"}");
            buffer.write("\\begin{document}\n");
            buffer.write("\\includepdf{caratula.pdf}");
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void terminarDocumento(){
        try {
            FileWriter escritora = new FileWriter(tex,true);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write("\n \\end{document}\n");
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    protected void juntaDirectiva(){
        try {
            Document doc = Jsoup.connect("http://www.ine.gob.gt/index.php/institucion/organizacion").get();
            Elements tables =  doc.select("tbody");
            Element juntaDirectiva = tables.get(0);
            Elements miembros = juntaDirectiva.select("tr");
            String[] temp;
            for( int i = 0 ; i < miembros.size(); i++ ){
                String[] partes = miembros.get(i).text().split("Suplente");
                if(i%2 != 0){
                    junta.add(partes[0]);
                    junta.add("Suplente" + partes[1]);
                }else{
                    junta.add(partes[0]);
                }
                
            }
            
            Element  tablaGerente = tables.get(1);
            Elements gerente = tablaGerente.select("tr");
            gerencias.add(gerente.get(0).text().split("Gerente")[1]);
            
            Element tablaGerencias = tables.get(2);
            Elements subgerencias = tablaGerencias.select("td");
            gerencias.add(subgerencias.get(0).text().split("[Ss]ubgerencia [Aa]dministrativa [Ff]inanciera")[1]);
            gerencias.add(subgerencias.get(1).text().split("[Ss]ubgerencia [Tt][ée]cnica")[1]);
            System.out.println(gerencias.get(1));
            System.out.println(gerencias.get(2));
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            FileWriter escritora = new FileWriter(tex,true);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write("\\hoja{\n" +
            "	$\\ $\n" +
            "	\\vspace{0.3cm}\n" +
            "	\n" +
            "	\\begin{center}\n" +
            "		{\\Bold \\LARGE AUTORIDADES}\\\\[0.7cm]\n" +
            "		\n" +
            "		\n" +
            "		{\\Bold \\large \\color{color1!89!black} JUNTA  DIRECTIVA} \\\\[0.5cm]\n" +
            "		\n" +
            "		\\begin{center}\n" +
            "			\\begin{tabular}{x{7.0cm}x{7.0cm}}\n");
            buffer.write("\t \t \t { \\Bold Ministerio de Economía}   & 		{\\Bold Ministerio de Finanzas}\\\\ \n");
            buffer.write( "\t \t \t " +  junta.get(junta.indexOf("Ministerio de Economía") + 1 )  +" & "+ junta.get(junta.indexOf("Ministerio de Finanzas Públicas") + 1 )  +"  \\\\ \n");
            buffer.write( "\t \t \t " +  junta.get(junta.indexOf("Ministerio de Economía") + 2 )  +" & "+ junta.get(junta.indexOf("Ministerio de Finanzas Públicas") + 2 )  +"  \\\\ \n");
            buffer.write("\t \t \t & \\\\\n" +
"				{\\Bold Ministerio de Agricultura,} & {\\Bold Ministerio de Energía y Minas}\\\\ \n" +
"				{\\Bold Ganadería y Alimentación} & "+  junta.get(junta.indexOf("Ministerio de Energía y Minas")+1) +"\\\\ \n");
            buffer.write( "\t \t \t " +  junta.get(junta.indexOf("Ministerio de Agricultura, Ganadería y Alimentación") + 1 )  +" & "+ junta.get(junta.indexOf("Ministerio de Energía y Minas") + 2 )  +"  \\\\ \n");
            buffer.write( "\t \t \t " +  junta.get(junta.indexOf("Ministerio de Agricultura, Ganadería y Alimentación") + 2 )  +" &   \\\\ \n");
            buffer.write("\t \t \t & {\\Bold Banco de Guatemala} \\\\ \n");
            buffer.write("\t \t \t {\\Bold Secretaría de Planificación y} &"+ junta.get(junta.indexOf("Banco de Guatemala") + 1 )   + "\\\\\n" +
"				{\t \t \t \\Bold Programación de la Presidencia} & "  + junta.get(junta.indexOf("Banco de Guatemala") + 2 )  + " \\\\\n \t \t \t "     +
                                 junta.get(junta.indexOf("Secretaría de Planificación y Programación de la Presidencia") + 1 ) +" & \\\\ \n");
            buffer.write("\t \t \t " + junta.get(junta.indexOf("Secretaría de Planificación y Programación de la Presidencia") + 2 ) +  " & {\\Bold Universidad de San Carlos de Guatemala} \\\\ \n");
            buffer.write("&" + junta.get(junta.indexOf("Universidad de San Carlos de Guatemala") + 1 )   +  "  \\\\\n" +
"				{\\Bold Comité Coordinador de } &" +  junta.get(junta.indexOf("Universidad de San Carlos de Guatemala") + 2 )+   " \\\\  \n" +
"				{\\Bold Asociaciones  Agrícolas, Comerciales, } & \\\\\n" +
"				{\\Bold Industriales y Financieras} & {\\Bold Universidades Privadas} \\\\\n" +
                                junta.get(junta.indexOf("Comité Coordinador de Asociaciones Agrícolas, Comerciales, Industriales y Financieras") +1 ) + " & " + junta.get(junta.indexOf("Universidades Privadas") + 1) +   "\\\\\n" +
				junta.get(junta.indexOf("Comité Coordinador de Asociaciones Agrícolas, Comerciales, Industriales y Financieras") +2 ) + " & " + junta.get(junta.indexOf("Universidades Privadas") + 2) +   "\\\\\n" +
"			\\end{tabular} 	\n" +
"		\\end{center} \n	");
            buffer.write("	\n" +
"		{\\Bold \\large \\color{color1!89!black} GERENCIA}\\\\[0.2cm]\n" +
                "Gerente: " + gerencias.get(0) +   "		\\\\\n" +
                "Subgerente Técnico: " + gerencias.get(2) + 		"\\\\\n" +
                "Subgerente Administrativo Financiero: " + gerencias.get(1) +  "\\\\ \n");
            buffer.write("\t \t \t \\end{center}\n");
            buffer.write("\t \t } \n");
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
    protected void preambulo(){
       String preambulo = "http://www.ine.gob.gt/ftparchivos/preambulo.tex";
       File file = new File(ruta,"preambulo.tex");
       URL url = null;
        try {
            url = new URL(preambulo);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        try {
            FileWriter escritora = new FileWriter(tex,false);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write("\\input{preambulo}\n");
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    protected void hacerTitulo(){
        try{
           FileWriter escritora = new FileWriter(tex,true);
           BufferedWriter buffer = new BufferedWriter(escritora);
           buffer.write("$\\ $\n" +
           "\\vspace{4.5cm}\n" +
           "\n" +
           "\\noindent\\begin{tabular}{p{0.1cm}p{6.8cm}}\n" +
           "	& " + year +  ".$\\,$ Guatemala, América Central \\\\\n" +
           "	&\\Bold Instituto Nacional de Estadística\\\\[-0.4cm]\n" +
           "	&\\color{blue!50!black}\\url{www.ine.gob.gt}\\\\[0.9cm]\n" +
           "\\end{tabular}\\\\\n" +
           "\\noindent\\begin{tabular}{p{0.1cm}p{6.8cm}}\n" +
           "	& Está permitida la reproducción parcial o total de los contenidos de este documento con la mención de la fuente. \\\\[0.5cm]\n" +
           "	\n" +
           "	& Este documento fue elaborado empleando  {\\Sans R}, Inkscape y {\\Logos \\XeLaTeX}.\\\\\n" +
           "\\end{tabular} \n" +
           "\n" +
           "\n" +
           "\\clearpage");
           
           buffer.write("$\\ $\n" +
          "\\vspace{3.5cm}\n" +
          "\n" +
          "\\begin{center}\n" +
          "	\\Bold \\LARGE REPÚBLICA DE GUATEMALA\\\\\n" +
          "{\\MakeUppercase{\\titulodoc}}\n"+
                   "\\end{center}\n" +
          "\\cleardoublepage");
           buffer.close();
        } catch(IOException ex){
            System.out.println(ex);
        }
    }
    
    protected String trimestreToSimbolo(String trimestre){
        String simbolo = null;
        System.out.println(this.trimestre);
        if(trimestre.equalsIgnoreCase("primero")){
            simbolo = "T1";
        }else if(trimestre.equalsIgnoreCase("segundo")){
            simbolo = "T2";
        }else if(trimestre.equalsIgnoreCase("tercero")){
            simbolo = "T3";
        }else if(trimestre.equalsIgnoreCase("cuarto")){
            simbolo = "T4";
        }else{
            simbolo = "ERROR" ;
        }
         return simbolo;   
        
    }
    
    protected String  simboloToTrimestre(String simbolo){
        String trimestre = null;
        if(simbolo.equalsIgnoreCase("T1")){
            trimestre = "primer";
        }else if(simbolo.equalsIgnoreCase("T2")){
            trimestre = "segundo";
        }else if(simbolo.equalsIgnoreCase("T3")){
            trimestre  = "tercer";
        }else if(simbolo.equalsIgnoreCase("T4")){
            trimestre = "cuarto";
        }else{
            System.err.println(simbolo + "NO ESCRIBISTE UN SIMBOLO CORRECTO");
        }
        return trimestre;
    }
    
    protected void escribirCapitulo(String nombreIndice, String nombre1, String nombre2, String contenido){
        FileWriter escritora;
        try {
            escritora = new FileWriter(tex,true);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(" \n \\INEchapter{" + nombreIndice + " }{" + nombre1 + "}{"
                   + nombre2 + "}{" + contenido + "}%\n");
            buffer.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    protected String hojaTrimestral(String columna1, String columna2){
        return("\\hojados{" + columna1 +"}{" + columna2 + "}");
    }
    
    protected String columna(String nombreSeccion, String parrafo1, String parrafo2, String tituloGrafica,
            String tipoGrafica, String grafica, String fuente, String pie){
        return("\n \\columna{"
                + nombreSeccion + "}{"
                + parrafo1 + "}{"
                + parrafo2 + "}{"
                + tituloGrafica + "}{"
                + tipoGrafica + "}{"
                + grafica + "}{"
                + fuente + "}{"
                + pie+"}\n");
    }
    
    protected  void escribirLinea(String linea){
        FileWriter escritora;
        try {
            escritora = new FileWriter(tex,true);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(linea);
            buffer.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    protected String corregirTrimestre(String trimestre){
        String trim = null;
        if(trimestre.equalsIgnoreCase("primero")){
            trim = "Primer";
        }else if(trimestre.equalsIgnoreCase("segundo")){
            trim = "Segundo";
        }else if(trimestre.equalsIgnoreCase("tercero")){
            trim = "Tercer";
        }else{
            trim = "Cuarto";
        }
        return trim;
    }

    
        protected void compilar(SesionR rr, String ruta, String mostrar){
            rr.get().eval("compilar('" + ruta + "',"+ mostrar+")" );
            rr.get().end();
        }
}
