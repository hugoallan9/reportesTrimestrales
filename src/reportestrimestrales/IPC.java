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
public class IPC extends Documento{
    private List capitulos;
    Collator comparador = Collator.getInstance();
    private SesionR rr;
    private String rutaCSV;
    
    public IPC(String titulo, String trimestre, String pYear, String rutaCSV){
        super(titulo, trimestre, pYear);
        capitulos = new ArrayList();
        this.rutaCSV = rutaCSV;
        rr = new SesionR();
        comparador.setStrength(Collator.PRIMARY);
        cargarCSV(rutaCSV);
        setCapitulos();
    }
    
    
    protected void hacerPortada(){
       String portada = "http://www.ine.gob.gt/ftparchivos/portadaIPC.pdf";
       System.out.println(getRuta());
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
        
        String contraPortada = "http://www.ine.gob.gt/ftparchivos/contraportadaIPC.pdf";
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
            buffer.write("\\documentclass[11pt,twoside]{book}\\usepackage[T1]{fontenc}\n" +
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
            + "\\node[inner sep =0, scale = 3.5, align = left] at (0.54,0.57) {\n" +
             "República de Guatemala \n" +
            "		\\\\\n" +
            "Indice de  precios al consumidor - IPC\n" +
            "		\\\\\n" +
            getMes() + getAnioPublicacion() + " };" +
            "\\node[inner sep =0, scale =2.5]at(0.54,0.05){Guatemala, "+ getMes()  +" de " +  getYear()+"};\n "+
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
    
    protected void setCapitulos(){
        capitulos.add("Variables que condicionan los precios internos");
        capitulos.add("Resultados nacionales");
        capitulos.add("Resultados de la región I");
        capitulos.add("Resultados de la región II");
        capitulos.add("Resultados de la región III");
        capitulos.add("Resultados de la región IV");
        capitulos.add("Resultados de la región V");
        capitulos.add("Resultados de la región VI");
        capitulos.add("Resultados de la región VII");
        capitulos.add("Resultados de la región VIII");
        
    }
    
    
    protected void capitulo1(){
        escribirCapituloAnual(capitulos.get(0).toString(),"");
     
    }
    
    
    protected void capitulo2(){
        escribirCapituloAnual(capitulos.get(1).toString(), "");
        
        section2_02();
        
    }

    private void section2_02() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INICIO HOJA 1%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_02","Evolución del IPC", 
                "descripcion2_01",
                "IPC nacional, base diciembre del 2010",
                "Serie histórica mensual",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_01.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }

    protected void generarGraficas(String modalidad){
        System.out.println("GENERANDO LAS GRAFICAS");
         rr.get().eval("setPath('" + getRuta() + "')");
         System.out.println("La ruta para las gráficas es " + rr.get().eval("getPath()"));
        Grafica vitales = new Grafica("ipc", getRuta(), rr.get(), modalidad);
        vitales.start();
    } 

    private void cargarCSV(String ruta) {
        if (!rr.get().waitForR())
            {
                System.err.println("No se pudo establecer  conexión con R ");
            }else {
                System.out.println("La ruta para cargar los CSV " + ruta);
                rr.get().eval("library(funcionesINE)");
                System.out.println(rr.get().eval(".libPaths()"));
                System.out.println(rr.get().eval("R.version"));
                System.out.println("ipc <- cargaMasiva('" +  ruta +"')");
                REXP listadoCSV = rr.get().eval("ipc <- cargaMasiva('" +  ruta +"')");
                REXP nombres = rr.get().eval("names(ipc)");
                rr.get().eval("setListIpc(ipc)");
                System.out.println(listadoCSV);
                System.out.println(nombres);
                
            }
    }
    
}
