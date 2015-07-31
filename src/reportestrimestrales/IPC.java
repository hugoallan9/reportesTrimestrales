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
        section2_01();
        section2_02();
        section2_03();
        section2_05();
        section2_06();
        section2_07();
        section2_08();
        section2_09();
        section2_10();
        section2_11();
        section2_12();
        section2_13();
        section2_15();
        section2_16();
        section2_17();
        section2_18();
        section2_19();
        section2_20();
        section2_21();
        section2_23();
        section2_24();
        section2_27();
        section2_28();
        section2_29();
        section2_30();
        section2_31();
        section2_32();
        section2_33();
        section2_34();
        section2_35();
        section2_36();
        section2_37();
        section2_38();
        section2_39();
        section2_40();
        section2_41();
        section2_42();
        section2_44();
        section2_45();
        section2_47();

        
    }

    
    private void section2_01(){
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 11%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        String tabla = "	\\begin{tabular}{rlrrrcc}\n" +
"		&  \\multicolumn{2}{c}{Número Índice} \\phantom{abc} &   \\multicolumn{4}{c}{Variación porcentual}\\\\\n" +
"		\\cmidrule{2-3} \\cmidrule{5-7} \n" +
                "Año &  Mes &  Índice &   Mensual &  Acumulada&  Interanual\\\\ \\hline \n"+
                "\\input{CSV/tablas/2_01.csv}"+
                "	\\bottomrule\n" +
"	\\end{tabular}\n";
        
        escribirLinea(cajotaTabla("2_01", 
                "IPC y sus variaciones",
                "",
                "Comportamiento del IPC región I y sus variaciones",
                getMes() + " " + getYear() ,
                tabla,
                "Instituto Nacional de Estadística"));
    }
    
    
    private void section2_02() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 12%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_02","Evolución del IPC", 
                "descripcion201",
                "IPC nacional, base diciembre del 2010",
                "Serie histórica mensual",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_02.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_03() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 12%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_03","IPC por división de gasto", 
                "descripcion203",
                "IPC por divisiòn de gasto, base diciembre del 2010",
                getMes() + " " + getYear(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_03.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_05(){
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 14%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        String tabla = " 	\\begin{tabular}{@{}ccrrrrrrrrrrrrr@{}}\\toprule\n" +
" 		\\multirow{2}{2cm}{Región}&  \\multirow{2}{1cm}{Variación mensual} &  \\phantom{abc} &  \\multicolumn{10}{c}{División del Gasto}\\\\\n" +
" 		\\cmidrule{3-14}\n" +
" 		& & \\begin{sideways}Alimentos\\end{sideways} &  \\begin{sideways}Bebidas alcohólicas\\end{sideways} &  \\begin{sideways}Vestuario\\end{sideways} &  \\begin{sideways}Vivienda\\end{sideways} &  \\begin{sideways}Muebles\\end{sideways} &  \\begin{sideways}Salud\\end{sideways} &  \\begin{sideways}Transporte\\end{sideways} &  \\begin{sideways}Comunicaciones\\end{sideways} &  \\begin{sideways}Recreación\\end{sideways} &  \\begin{sideways}Educación\\end{sideways} &  \\begin{sideways}Restaurantes\\end{sideways} &  \\begin{sideways} Bienes diversos \\end{sideways}\\\\\n" +
" 		\\cmidrule{1-14} \n"+
                "\\input{CSV/tablas/2_05.csv}"+
                "	\\bottomrule\n" +
"	\\end{tabular}\n";
        
        escribirLinea(cajotaTabla("2_05", 
                "Cambio mensual del IPC, por región y tipo de gasto",
                "",
                "Variación mensual del IPC por división de gasto y región",
                getMes() + " " + getYear() ,
                tabla,
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_06() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 15%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_06","Evolución del cambio mensual del IPC", 
                "descripcion206",
                "Variación intermensual del IPC nacional ",
                "Serie histórica mensual",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_06.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_07() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 15%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_07","Cambio mensual del IPC por gasto", 
                "descripcion207",
                "Variación intermensual del IPC nacional por división de gastos",
                getMes() + " " + getYear(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_07.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_08() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 16%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_08","Bienes con mayor incremento mensual", 
                "descripcion208",
                "Principales variaciones intermensuales positivas del IPC,  " + getMes() + " " + getYear(),
                "Por gasto básico, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_08.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_09() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 16%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_08","Bienes con mayor disminuciòn mensual", 
                "descripcion209",
                "Principales variaciones intermensuales negativas del IPC, " + getMes() + " " + getYear(),
                "Por gasto básico, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_09.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_10() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 17%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_10","Cambio mensual del IPC de alimentos", 
                "descripcion210",
                "Variación intermensual nacional del IPC de alimentos y bebidas no alcohólicas",
                "Serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_10.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_11() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 17%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_11","Cambio mensual del IPC de bebidas alcohólicas", 
                "descripcion211",
                "Variación intermensual nacional del IPC de bebidas alcohólicas y tabaco",
                "Serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_11.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    
    private void section2_12() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 18%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_12","Cambio mensual del IPC de vestuario", 
                "descripcion212",
                "Variación intermensual nacional del IPC de prendas de vestir y calzado",
                "Serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_12.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_13() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 18%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_13","Cambio mensual del IPC de  viviendas", 
                "descripcion213",
                "Variación intermensual nacional del IPC de vivienda, agua, electricidad y gas",
                "Serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_13.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }

    
    private void section2_14() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 19%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_14","Cambio mensual del IPC de muebles", 
                "descripcion214",
                "Variación intermensual nacional del IPC de muebles y artículos para el hogar",
                "Serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_14.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_15() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 19%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_15","Cambio mensual del IPC de salud", 
                "descripcion215",
                "Variación intermensual nacional del IPC de salud",
                "Serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_15.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    
    private void section2_16() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 20%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_16","Cambio mensual del IPC de transporte", 
                "descripcion216",
                "Variación intermensual nacional del IPC de transporte",
                "Serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_16.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_17() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 20%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_17","Cambio mensual del IPC de comunicaciones", 
                "descripcion217",
                "Variación intermensual nacional del IPC de comunicaciones",
                "Serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_17.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    
    
    private void section2_18() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 21%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_18","Cambio mensual del IPC de recreación", 
                "descripcion218",
                "Variación intermensual nacional del IPC de recreación y cultura",
                "Serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_18.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_19() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 21%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_19","Cambio mensual del IPC de educación", 
                "descripcion219",
                "Variación intermensual nacional del IPC de educación",
                "Serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_19.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    
     private void section2_20() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 22%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_20","Cambio mensual del IPC de restaurantes", 
                "descripcion220",
                "Variación intermensual nacional del IPC de restaurantes y hoteles",
                "Serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_20.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_21() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 22%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_21","Cambio mensual del IPC de bienes diversos", 
                "descripcion221",
                "Variación intermensual nacional del IPC de bienes y servicios diversos",
                "Serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_21.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    
     private void section2_23() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 24%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_23","Impacto del tipo de gasto en la variación mensual ", 
                "descripcion223",
                "Incidencia de las divisiones de gasto en la variación intermensual del IPC nacional",
                getMes() + " " + getYear(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_23.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_24() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 24%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_24","Bienes con mayor impacto en el cambio mensual", 
                "descripcion224",
                "Gastos básicos con mayor incidencia positiva  y negativa absoluta en la variación intermensual del IPC",
                getMes() + " " + getYear(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_24.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    

    
    
    private void section2_27() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 27%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_27","Variación interanual del IPC nacional  ", 
                "descripcion227",
                "Variación interanual del IPC nacional",
                "Serie histórica mensual",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_27.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_28() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 27%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_28","Cambio anual del IPC por tipo de gasto ", 
                "descripcion228",
                "Variación interanual del IPC nacional por división de gasto",
                getMes() + " " + getYear(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_28.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    
    private void section2_29() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 28%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_29","Bienes con mayor aumento anual  ", 
                "descripcion229",
                "Principales variaciones interanuales positivas del IPC, " + getMes() + " " + getYear(),
                "Por gasto básico, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_29.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_30() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 28%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_30","Bienes con mayor disminución anual", 
                "descripcion230",
                "Principales variaciones interanuales negativas del IPC, " + getMes() + " " + getYear(),
                "Por gasto básico, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_30.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    
    private void section2_31() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 29%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_31","Cambio anual de IPC de alimentos  ", 
                "descripcion231",
                "Variación interanual nacional del IPC de alimentos y bebidas no alcohólicas",
                "Serie histórica mensual, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_31.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_32() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 29%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_32","Cambio anual del IPC de bebidas alcohólicas ", 
                "descripcion232",
                "Variación interanual del IPC de bebidas alcohólicas y tabaco",
                "Serie histórica mensual, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_32.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    
      private void section2_33() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 30%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_33","Cambio anual de IPC de vestuario  ", 
                "descripcion233",
                "Variación interanual nacional del IPC de prendas de vestir y calzado",
                "Serie histórica mensual, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_33.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_34() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 30%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_34","Cambio anual del IPC de vivienda ", 
                "descripcion234",
                "Variación interanual del IPC de vivienda",
                "Serie histórica mensual, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_34.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
       private void section2_35() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 31%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_35","Cambio anual de IPC de muebles  ", 
                "descripcion235",
                "Variación interanual nacional del IPC de muebles y artículos para el hogar",
                "Serie histórica mensual, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_35.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_36() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 31%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_36","Cambio anual del IPC de salud ", 
                "descripcion236",
                "Variación interanual del IPC de salud",
                "Serie histórica mensual, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_36.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    
    private void section2_37() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 32%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_37","Cambio anual de IPC de transporte  ", 
                "descripcion237",
                "Variación interanual nacional del IPC de transporte",
                "Serie histórica mensual, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_37.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_38() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 32%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_38","Cambio anual del IPC de comunicaciones ", 
                "descripcion238",
                "Variación interanual del IPC de comunicaciones",
                "Serie histórica mensual, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_38.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    
    private void section2_39() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 33%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_39","Cambio anual de IPC de recreación  ", 
                "descripcion237",
                "Variación interanual nacional del IPC de recreación y cultura",
                "Serie histórica mensual, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_39.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_40() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 33%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_40","Cambio anual del IPC de educación ", 
                "descripcion238",
                "Variación interanual del IPC de educación",
                "Serie histórica mensual, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_40.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    
    private void section2_41() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 34%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_41","Cambio anual de IPC de restaurantes  ", 
                "descripcion237",
                "Variación interanual nacional del IPC de los servicios en restaurantes y hoteles",
                "Serie histórica mensual, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_41.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_42() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 34%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_42","Cambio anual del IPC de bienes diversos ", 
                "descripcion238",
                "Variación interanual del IPC de bienes y servicios diversos",
                "Serie histórica mensual, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_42.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
     private void section2_44() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 36%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_44","Impacto del tipo de gasto en la variación anual", 
                "descripcion203",
                "Incidencia de las divisiones de gasto en la variación interanual del IPC nacional",
                getMes() + " " + getYear(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_44.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_47() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 38%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_47","Valor del dinero", 
                "descripcion238",
                "Poder adquisitivo del quetzal",
                "Serie histórica mensual, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_47.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    } 
      private void section2_45() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 36%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_45","Bienes con mayor impacto en el cambio del anual IPC ", 
                "descripcion203",
                "Gastos básicos con mayor incidencia positiva y negativa absoluta en la variación interanual del IPC",
                getMes() + " " + getYear(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_45.tex}  \\end{tikzpicture}",
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
