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
        Mapa mapa = new Mapa(rutaCSV, getRuta());
        Mapa.descarga();
        mapa.mapasIPC();
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
        section2_14();
        section2_15();
        section2_16();
        section2_17();
        section2_18();
        section2_19();
        section2_20();
        section2_21();
        section2_23();
        section2_24();
        
        section2_26();
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
    
    protected void capitulosRegionales(){
        for(int i=1; i<=8; i++){
            escribirCapituloAnual(capitulos.get(i+1).toString(), "");
            sectionR_01(i);
            sectionR_02(i);
            sectionR_03(i);
            sectionR_04(i);
            sectionR_05(i);
            sectionR_06(i);
            sectionR_07(i);
            sectionR_08(i);
            for(int j=1; j<=12; j++){
                sectionR_9_20(i, j);
            }
            sectionR_21(i);
            sectionR_22(i);
            sectionR_23(i);
            sectionR_24(i);
            sectionR_25(i);
            sectionR_26(i);
            sectionR_27(i);
            for(int j=1; j<=12; j++){
                sectionR_28_39(i, j);
            }
            sectionR_40(i);
            sectionR_41(i);
            sectionR_42(i);
        } 
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
                "Comportamiento del IPC a nivel de república y sus variaciones",
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
    
    private void section2_04(){
        escribirLinea(cajotaMapa("2_04", 
                "Índice de precios al consumidor por regiones", 
                "",
                "IPC por regiones",
                getMes() + " " + getYear(),
                "\\includegraphics[width=52\\cuadri]{2_04.pdf}",
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
    
    private void section2_26(){
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 26%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        String tabla = " 	\\begin{tabular}{@{}ccrrrrrrrrrrrrr@{}}\\toprule\n" +
" 		\\multirow{2}{2cm}{Región}&  \\multirow{2}{1cm}{Variación mensual} &  \\phantom{abc} &  \\multicolumn{10}{c}{División del Gasto}\\\\\n" +
" 		\\cmidrule{3-14}\n" +
" 		& & \\begin{sideways}Alimentos\\end{sideways} &  \\begin{sideways}Bebidas alcohólicas\\end{sideways} &  \\begin{sideways}Vestuario\\end{sideways} &  \\begin{sideways}Vivienda\\end{sideways} &  \\begin{sideways}Muebles\\end{sideways} &  \\begin{sideways}Salud\\end{sideways} &  \\begin{sideways}Transporte\\end{sideways} &  \\begin{sideways}Comunicaciones\\end{sideways} &  \\begin{sideways}Recreación\\end{sideways} &  \\begin{sideways}Educación\\end{sideways} &  \\begin{sideways}Restaurantes\\end{sideways} &  \\begin{sideways} Bienes diversos \\end{sideways}\\\\\n" +
" 		\\cmidrule{1-14} \n"+
                "\\input{CSV/tablas/2_26.csv}"+
                "	\\bottomrule\n" +
"	\\end{tabular}\n";
        
        escribirLinea(cajotaTabla("2_26", 
                "Cambio anual del IPC, por región y tipo de gasto",
                "",
                "Variación interanual del IPC por división de gasto y región",
                getMes() + " " + getYear() ,
                tabla,
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_27() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 27%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_27","Evolución del cambio anual del IPC  ", 
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
    
    private void section2_45() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 36%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_45","Bienes con mayor impacto en el cambio anual IPC ", 
                "descripcion203",
                "Gastos básicos con mayor incidencia positiva y negativa absoluta en la variación interanual del IPC",
                getMes() + " " + getYear(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_45.tex}  \\end{tikzpicture}",
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
    
    private void sectionR_01(int region){
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 1 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        String tabla = "	\\begin{tabular}{rlrrrcc}\n" +
"		&  \\multicolumn{2}{c}{Número Índice} \\phantom{abc} &   \\multicolumn{4}{c}{Variación porcentual}\\\\\n" +
"		\\cmidrule{2-3} \\cmidrule{5-7} \n" +
                "Año &  Mes &  Índice &   Mensual &  Acumulada&  Interanual\\\\ \\hline \n"+
                "\\input{CSV/tablas/" + (region+2) + "_01.csv}"+
                "	\\bottomrule\n" +
"	\\end{tabular}\n";
        
        escribirLinea(cajotaTabla((region + 2) + "_01", 
                "IPC y sus variaciones",
                "",
                "Comportamiento del IPC a nivel de república y sus variaciones",
                getMes() + " " + getYear() ,
                tabla,
                "Instituto Nacional de Estadística"));
    }
    
    private void sectionR_02(int region) {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 2 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion((region + 2) + "_02","Evolución del IPC de la región " + getRomano(region), 
                "descripcion201",
                "IPC región " + getRomano(region) + ", base diciembre del 2010",
                "Serie histórica mensual",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+(region + 2)+"_02.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private String getRomano(int numero){
        if(numero == 1) return "I";
        else if(numero == 2) return "II";
        else if(numero == 3) return "III";
        else if(numero == 4) return "IV";
        else if(numero == 5) return "V";
        else if(numero == 6) return "VI";
        else if(numero == 7) return "VII";
        else if(numero == 8) return "VIII";
        else return "";
    }
    
    private void sectionR_03(int region) {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 2 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion((region + 2) + "_03","IPC región " + getRomano(region) + " por división de gasto", 
                "descripcion203",
                "IPC " + getRomano(region) + " por divisiòn de gasto, base diciembre del 2010",
                getMes() + " " + getYear(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+(region + 2)+"_03.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void sectionR_04(int region){
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 3 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        String tabla = " 	\\begin{tabular}{@{}ccrrrrrrrrrrrrr@{}}\\toprule\n" +
" 		\\multirow{2}{2cm}{Región}&  \\multirow{2}{1cm}{Variación mensual} &  \\phantom{abc} &  \\multicolumn{10}{c}{División del Gasto}\\\\\n" +
" 		\\cmidrule{3-14}\n" +
" 		& & \\begin{sideways}Alimentos\\end{sideways} &  \\begin{sideways}Bebidas alcohólicas\\end{sideways} &  \\begin{sideways}Vestuario\\end{sideways} &  \\begin{sideways}Vivienda\\end{sideways} &  \\begin{sideways}Muebles\\end{sideways} &  \\begin{sideways}Salud\\end{sideways} &  \\begin{sideways}Transporte\\end{sideways} &  \\begin{sideways}Comunicaciones\\end{sideways} &  \\begin{sideways}Recreación\\end{sideways} &  \\begin{sideways}Educación\\end{sideways} &  \\begin{sideways}Restaurantes\\end{sideways} &  \\begin{sideways} Bienes diversos \\end{sideways}\\\\\n" +
" 		\\cmidrule{1-14} \n"+
                "\\input{CSV/tablas/"+(region + 2)+"_04.csv}"+
                "	\\bottomrule\n" +
"	\\end{tabular}\n";
        
        escribirLinea(cajotaTabla((region + 2) + "_05", 
                "Cambio mensual del IPC, por región y tipo de gasto, serie mensual",
                "",
                "Variación mensual del IPC de la región " + getRomano(region) + "por división de gasto",
                getMes() + " " + getYear() ,
                tabla,
                "Instituto Nacional de Estadística"));
    }
    
    private void sectionR_05(int region) {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 4 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion((region + 2) + "_05","Evolución del cambio mensual del IPC región " + getRomano(region), 
                "descripcion206",
                "Variación intermensual del IPC región " + getRomano(region),
                "Serie histórica mensual",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+(region + 2)+"_05.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void sectionR_06(int region) {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 4 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion((region + 2) + "_06","Cambio mensual del IPC por gasto", 
                "descripcion207",
                "Variación intermensual del IPC región " + getRomano(region) + " por división de gasto",
                getMes() + " " + getYear(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+(region + 2)+"_06.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void sectionR_07(int region) {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 5 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion((region + 2)+"_07","Bienes con mayor incremento mensual", 
                "descripcion208",
                "Principales variaciones intermensuales positivas del IPC,  " + getMes() + " " + getYear(),
                "Por gasto básico, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+(region + 2)+"_07.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void sectionR_08(int region) {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 5 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion((region + 2)+"_08","Bienes con mayor disminuciòn mensual", 
                "descripcion209",
                "Principales variaciones intermensuales negativas del IPC, " + getMes() + " " + getYear(),
                "Por gasto básico, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+(region + 2)+"_08.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private String getDivisionGasto(int codigo){
        if(codigo==1) return "alimentos y bebidas no alcohólicas";
        else if(codigo==2) return "bebidas alcohólicas y tabaco";
        else if(codigo==3) return "prendas de vestir y calzado";
        else if(codigo==4) return "vivienda, agua, electricidad y gas";
        else if(codigo==5) return "muebles y artículos para el hogar";
        else if(codigo==6) return "salud";
        else if(codigo==7) return "transporte";
        else if(codigo==8) return "comunicaciones";
        else if(codigo==9) return "recreación y cultura";
        else if(codigo==10) return "educación";
        else if(codigo==11) return "restaurantes y hoteles";
        else if(codigo==12) return "bienes y servicios diversos";
        else return "";
    }
    
    private String getDivisionGastoAbreviacion(int codigo){
        if(codigo==1) return "alimentos";
        else if(codigo==2) return "bebidas alcohólicas";
        else if(codigo==3) return "vestuario";
        else if(codigo==4) return "vivienda";
        else if(codigo==5) return "muebles";
        else if(codigo==6) return "salud";
        else if(codigo==7) return "transporte";
        else if(codigo==8) return "comunicaciones";
        else if(codigo==9) return "recreación";
        else if(codigo==10) return "educación";
        else if(codigo==11) return "restaurantes";
        else if(codigo==12) return "bienes diversos";
        else return "";
    }
    
    private void sectionR_9_20(int region, int division) {
        int hoja = 5 + division/2;
        String archivo;
        if(division == 1) archivo = (region + 2) + "_0" + (division+8);
        else  archivo = (region + 2) + "_" + (division+8);
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA " + (hoja) + " de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion(archivo,"Cambio mensual del IPC de " + getDivisionGastoAbreviacion(division) + " de la región " + getRomano(region), 
                "descripcion210",
                "Variación intermensual del IPC de " + getDivisionGasto(division) + " de la región " + getRomano(region),
                "Serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+archivo + ".tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }

    private void sectionR_21(int region) {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 12 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion((region + 2) + "_21","Impacto del tipo de gasto en la variación mensual ", 
                "descripcion223",
                "Incidencia de las divisiones de gasto en la variación intermensual del IPC de la región " + getRomano(region),
                getMes() + " " + getYear(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+(region + 2) + "_21.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void sectionR_22(int region) {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 12 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion((region + 2) + "_22","Bienes con mayor impacto en el cambio mensual", 
                "descripcion224",
                "Gastos básicos con mayor incidencia positiva y negativa absoluta en la variación intermensual del IPC de la región " + getRomano(region),
                getMes() + " " + getYear(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+(region + 2) + "_22.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void sectionR_23(int region){
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 13 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        String tabla = " 	\\begin{tabular}{@{}ccrrrrrrrrrrrrr@{}}\\toprule\n" +
" 		\\multirow{2}{2cm}{Región}&  \\multirow{2}{1cm}{Variación mensual} &  \\phantom{abc} &  \\multicolumn{10}{c}{División del Gasto}\\\\\n" +
" 		\\cmidrule{3-14}\n" +
" 		& & \\begin{sideways}Alimentos\\end{sideways} &  \\begin{sideways}Bebidas alcohólicas\\end{sideways} &  \\begin{sideways}Vestuario\\end{sideways} &  \\begin{sideways}Vivienda\\end{sideways} &  \\begin{sideways}Muebles\\end{sideways} &  \\begin{sideways}Salud\\end{sideways} &  \\begin{sideways}Transporte\\end{sideways} &  \\begin{sideways}Comunicaciones\\end{sideways} &  \\begin{sideways}Recreación\\end{sideways} &  \\begin{sideways}Educación\\end{sideways} &  \\begin{sideways}Restaurantes\\end{sideways} &  \\begin{sideways} Bienes diversos \\end{sideways}\\\\\n" +
" 		\\cmidrule{1-14} \n"+
                "\\input{CSV/tablas/"+(region + 2)+"_23.csv}"+
                "	\\bottomrule\n" +
"	\\end{tabular}\n";
        
        escribirLinea(cajotaTabla((region + 2) + "_23", 
                "Cambio anual del IPC, por región y tipo de gasto, serie mensual",
                "",
                "Variación interanual del IPC por división de gasto",
                getMes() + " " + getYear() ,
                tabla,
                "Instituto Nacional de Estadística"));
    }
    
    private void sectionR_24(int region) {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 14 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion((region + 2)+"_24","Evolución del cambio anual del IPC región " + getRomano(region), 
                "descripcion227",
                "Variación interanual del IPC región " + getRomano(region),
                "Serie histórica mensual",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+(region + 2)+"_24.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void sectionR_25(int region) {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 14 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion((region + 2)+"_25","Cambio anual del IPC por tipo de gasto ", 
                "descripcion228",
                "Variación interanual del IPC región " + getRomano(region) + " por división de gasto",
                getMes() + " " + getYear(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+(region + 2)+"_25.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void sectionR_26(int region) {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 15 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion((region + 2)+"_26","Bienes con mayor aumento anual  ", 
                "descripcion229",
                "Principales variaciones interanuales positivas del IPC de la región " + getRomano(region) + ", " + getMes() + " " + getYear(),
                "Por gasto básico, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+(region + 2)+"_26.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void sectionR_27(int region) {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 15 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion((region + 2)+"_27","Bienes con mayor disminución anual", 
                "descripcion230",
                "Principales variaciones interanuales negativas del IPC, " + getMes() + " " + getYear(),
                "Por gasto básico, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+(region + 2)+"_27.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }    
    
    private void sectionR_28_39(int region, int division) {
        int hoja = 15 + division/2;
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA " + (hoja) + " de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion((region + 2) + "_" + (division+27),"Cambio anual de IPC de " + getDivisionGastoAbreviacion(division) + " de la región " + getRomano(region), 
                "descripcion231",
                "Variación interanual del IPC de " + getDivisionGasto(division) + " de la región " + getRomano(region),
                "Serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+(region + 2) + "_" + (division+27) + ".tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void sectionR_40(int region) {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 22 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion((region + 2) + "_40","Impacto del tipo de gasto en el cambio anual", 
                "descripcion203",
                "Incidencia de las divisiones de gasto en la variación interanual del IPC región " + getRomano(region),
                getMes() + " " + getYear(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+(region + 2) +"_40.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void sectionR_41(int region) {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 22 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion((region + 2) + "_41","Bienes con mayor impacto en el cambio anual del anual del IPC ", 
                "descripcion203",
                "Gastos básicos con mayor incidencia positiva y negativa absoluta en la variación interanual del IPC de la región " + getRomano(region),
                getMes() + " " + getYear(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+(region + 2) +"_41.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void sectionR_42(int region) {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 23 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion((region + 2) + "_42","Valor del dinero", 
                "descripcion238",
                "Poder adquisitivo del quetzal en la región " + getRomano(region),
                "Serie histórica mensual",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+(region + 2) +"_42.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    } 
    
    protected void generarGraficas(String modalidad){
        System.out.println("GENERANDO LAS GRAFICAS");
        System.out.println("La ruta que se obtiene es : " + getRuta());
         rr.get().eval("setPath('" + getRuta() + "')");
         System.out.println("La ruta para las gráficas es " + rr.get().eval("getPath()"));
        Grafica vitales = new Grafica("ipc", getTex().getAbsolutePath(), rr.get(), modalidad);
        vitales.start();
    } 

    
    
    
    private void cargarCSV(String ruta) {
        if (!rr.get().waitForR())
            {
                System.err.println("No se pudo establecer  conexión con R ");
            }else {
                System.out.println("La ruta para cargar los CSV " + ruta);
                System.out.println(rr.get().eval("library(funcionesINE)"));
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
