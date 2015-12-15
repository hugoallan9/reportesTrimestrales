/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportestrimestrales;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
            "\\setmainfont{FuturaStd-CondensedLight.otf}\n" +
            "\\begin{document}\n" +
            "\n" +
            "\\begin{tikzpicture} \n "+
            "\\node[anchor=south west,inner sep=0] (image) at (0,0) {\\includegraphics{portada}};\n" +
            "\\begin{scope}[x={(image.south east)},y={(image.north west)}] "
            + "\\node[inner sep =0, scale = 3.8, align = left, color = white] at (0.49,0.53) {\n" +
             "República de Guatemala: \n" +
            "		\\\\\n" +
            "Índice de  Precios  al  Consumidor \n "+
                    "		\\\\\n" +
            getMes()+ " " +getYear()+
            "		\\\\\n" + " };" +
            "\\node[inner sep =0, scale =2, color = white]at(0.54,0.05){Guatemala, "+ getMesServidor()+" de " +  getYearServidor()+"};\n "+
            "\\end{scope}\n" +
            "\\end{tikzpicture}\n" +
            "\n" +
            "\\end{document}");
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            File p1 = new File(getRuta(),"transicion.tex");
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
            "\\setmainfont{FuturaStd-CondensedLight.otf}\n" +
            "\\begin{document}\n" +
            "\n" +
            "\\begin{tikzpicture} \n "+
            "\\node[anchor=south west,inner sep=0] (image) at (0,0) {\\includegraphics{portadaTranIPC.pdf}};\n" +
            "\\begin{scope}[x={(image.south east)},y={(image.north west)}] "
            + "\\node[inner sep =0, scale = 3.8, align = left, color = white] at (0.49,0.53) {\n" +
             "República de Guatemala: \n" +
            "		\\\\\n" +
            "Índice de  Precios  al  Consumidor \n "+
                    "		\\\\\n" +
            getMes()+ " " +getYear()+
            "		\\\\\n" + " };" +
            "\\node[inner sep =0, scale =2, color = white]at(0.54,0.05){Guatemala, "+ getMesServidor()  +" de " +  getYearServidor()+"};\n "+
            "\\end{scope}\n" +
            "\\end{tikzpicture}\n" +
            "\n" +
            "\\end{document}");
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        compilar(rr, getRuta() + "/caratula.tex","F");
        compilar(rr, getRuta() + "/transicion.tex","F");
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
        section1_01();
        section1_02();
        section1_03();
        section1_04();
        section1_05();
        section1_06();
        section1_07();
    }
    
    private void section1_01() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "1_01","Precio internacional de los alimentos", 
                "descripcion201",
                "Índice de precios de los alimentos de la FAO",
                "Indicador internacional, serie histórica, adimensional",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_01.tex}  \\end{tikzpicture}",
                "FAO"));
    }
    
    
    private void section1_02() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "1_02","Precio del pretróleo", 
                "descripcion201",
                "Precio promedio mensual del barril del petróleo",
                "Indicador internacional, serie histórica, en dólares por barril",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_02.tex}  \\end{tikzpicture}",
                "Bloomberg"));
    }
    
    private void section1_03() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "1_03","Cambio del quetzal", 
                "descripcion201",
                "Tipo de cambio nominal promedio",
                "República de Guatemala, serie histórica, en quetzales por dólar estadounidense",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_03.tex}  \\end{tikzpicture}",
                "Banco de Guatemala"));
    }
    
    
    private void section1_04() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "1_04","Tasa de interés", 
                "descripcion201",
                "Tasas de interés activa bancaria",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_04.tex}  \\end{tikzpicture}",
                "Banco de Guatemala"));
    }
    
    
    private void section1_05() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "1_05","Índice de precios al consumidor de EE.UU.", 
                "descripcion201",
                "Variación interanual del IPC de Estados Unidos de América",
                "Estados Unidos de América, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_06.tex}  \\end{tikzpicture}",
                "U.S. Bureau of Labor Statistics "));
    }
    
    
    private void section1_06() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "1_06","Índice de precios al consumidor de México", 
                "descripcion201",
                "Variación interanual del IPC de México",
                "Estados Unidos Mexicanos, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_05.tex}  \\end{tikzpicture}",
                "INEGI"));
    }
    
    private void section1_07(){
               File f = new File(rutaCSV,"1_07.csv");
    BufferedReader br = null;
    String line = "";
        boolean encabezado = true;
             String tabla = "\\begin{tabular}{lcrr}\n" +
" 		\n" +
"\\hline \\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}\\\\[-0.4cm]\n" +
"		\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\n" +
" 		\\multicolumn{2}{c}{\\cellcolor{color1!10!white} \\Bold{\\small Inflación interanual a}} \\\\\n" +
" 		\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}\\\\[-0.4cm]" +
" 		\n" +
" 		\\cmidrule{3-4}\n" +
" 		\n"
                     +
"\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}\\\\[-0.45cm]" +
"		\\cellcolor{color1!10!white} \\multirow{-2}[0]{*}{} &\n" +
" 		\\cellcolor{color1!10!white} \\multirow{-2}[0]{*}"
                     + "{\\begin{tabular}{c}\\Bold \\small Meta de \\\\ \\Bold \\small Inflación\\end{tabular}}"
                     + " & \\cellcolor{color1!10!white} \\Bold \\small " + getFormatoMesAnterior(getMes())
                     + " & \\cellcolor{color1!10!white} \\Bold \\small " + getMes().substring(0,3) + "-"
                     +getAnioPublicacion().substring(getAnioPublicacion().length()-2,getAnioPublicacion().length())
                     +" \\\\[0.02cm] \n" +
" 		\n" +
" 		\\hline\n" +
" 		\\rowcolor{white}\n";
             
             
             
            
             try {
                br = new BufferedReader(new FileReader(f.getAbsolutePath()));
                while ((line = br.readLine()) != null) {
                    if(encabezado)
                        encabezado=false;
                    else{
                        String[] valores = line.split(";");                        
                        tabla += valores[0]+" & "+ valores[1].replace("%", "\\%")+ " & " + valores[2]+" & "+valores[3]+ "\\\\" ;                        
                    }
            
            
            
                }
            
             }
            catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                tabla += "\\bottomrule\n" +
" 		" +
" 	\\end{tabular} ";
        
        
        
        
        
         escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
                 
        
        escribirLinea(sectionTabla("1_07", 
                "Inflación en América Central",
                "",
                "Tasa de variación del IPC de los países Centroamericanos",
                "Centro América, meses seleccionados, en porcentaje" ,
                tabla,
                "Banco de Guatemala"));
    
    
    }
    
    private String getFormatoMesAnterior(String mesActual){
        String anio = getAnioPublicacion();
        String mes="";
        mesActual= mesActual.toLowerCase();
        switch (mesActual){
            case "enero":
                mes = "Dic-";
                Integer an= Integer.parseInt(anio);
                an = an-1;
                anio = an.toString();                
                break;
            case "febrero":
                mes = "Ene-";
                break;
            case "marzo":
                mes = "Feb-";
                break;
            case "abril":
                mes = "Mar-";
                break;
            case "mayo":
                mes = "Abr-";
                break;
            case "junio":
                mes = "May-";
                break;
            case "julio":
                mes = "Jun-";
                break;
            case "agosto":
                mes = "Jul-";
                break;
            case "septiembre":
                mes = "Ago-";
                break;
            case "octubre":
                mes = "Sep-";
                break;
            case "noviembre":
                mes = "Oct-";
                break;
            case "diciembre":
                mes = "Nov-";
                break;                       
            default:               
                break;            
        }
        mes += anio.substring(anio.length()-2,anio.length());     
        return mes;
    }
    protected void capitulo2(){
        Mapa mapa = new Mapa(rutaCSV, getRuta());
        Mapa.descarga();
        mapa.mapasIPC();
        escribirCapituloAnual(capitulos.get(1).toString(), "");
        section2_01();
        section2_02();
        section2_03();
        section2_04();
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
        section2_22();
        section2_23();
        section2_24();
        section2_25();
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
        section2_43();
        section2_44();
        section2_45();
        section2_46();
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
        String tabla = "\n \\begin{tabular}{clcrrr}\n" +
"\n" +
"\\hline \\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}\\\\[-0.4cm]\n" +
"\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\n" +
"\\multicolumn{3}{c}{\\cellcolor{color1!10!white} \\Bold{Variación porcentual}} \\\\\n" +
"\n" +
"\\cmidrule{4-6}\n" +
"\n" +
"\\cellcolor{color1!10!white} \\multirow{-2}[0]{*}{\\Bold Año} &\n" +
"\\cellcolor{color1!10!white} \\multirow{-2}[0]{*}{\\Bold Mes} &\n" +
"\\cellcolor{color1!10!white} \\multirow{-2}[0]{*}{\\Bold Índice} & \\cellcolor{color1!10!white}  \\Bold Mensual & \\cellcolor{color1!10!white} \\Bold Acumulada& \\cellcolor{color1!10!white} \\Bold Interanual\\\\[0.04cm] \n" +
"\n" +
"\\hline\n" +
"\\rowcolor{white}\n" +
"\\input{CSV/tablas/2_01.csv}	\n" +
"\\bottomrule\n" +
"\n" +
"	\\end{tabular} \n" +
"}%";
        
        escribirLinea(cajotaTabla("2_01", 
                "IPC y sus variaciones",
                "",
                "Comportamiento del IPC a nivel de república y sus variaciones",
                " República de Guatemala,  " + getMes() + " " + getYear()  + ",  adimensional y en porcentaje " ,
                tabla,
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_02() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 12%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_02","Evolución del IPC", 
                "descripcion201",
                "IPC, base diciembre del 2010",
                "República de Guatemala, Serie histórica años " + getSerie()      + "adimensional",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_02.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_03() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 12%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_03","IPC por división de gasto", 
                "descripcion203",
                "IPC por división de gasto, base diciembre del 2010",
                "República de Guatemala, " + getMes() + " " + getYear()+  "adimensional",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_03.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_04(){
        escribirLinea(cajotaMapa("2_04", 
                "Índice de precios al consumidor por regiones", 
                "",
                "IPC",
                " República de Guatemala, " + getMes() + " " + getYear() + "adimensional",
                "\\includegraphics[width=52\\cuadri]{2_04.pdf}",
                "Instituto Nacional de Estadística"));
        
    }
    
    private void section2_05(){
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 14%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        String tabla = "\\begin{tabular}{l@{1}rrrrrrrrrrrrrr@{}}\n" +
        "          \\rowcolor{color1!10!white}\n" +
        "          \\hline\n" +
        "          &&&&&&&&&&&&&\\\\[-0.4cm]\n" +
        "          &  & &  \\phantom{abc} &  \\multicolumn{10}{c}{\\Bold División del Gasto}\\\\\n" +
        "         \\cmidrule{3-14}\n" +
        "         &&&&&&&&&&&&&\\\\[-0.35cm]\n" +
        "         \\multirow{-7}[0]{*}{\\Bold Región}&\\multirow{-7}[0]{*}{\\begin{tabular}{c}\\Bold Variación\\\\ \\Bold Mensual\\end{tabular}} & \\begin{sideways}\\small \\Bold Alimentos\\end{sideways} &  \\begin{sideways} \\small \\Bold Bebidas alcohólicas\\end{sideways} &  \\begin{sideways}\\small \\Bold Vestuario\\end{sideways} &  \\begin{sideways}\\small \\Bold Vivienda\\end{sideways} &  \\begin{sideways}\\small \\Bold Muebles\\end{sideways} &  \\begin{sideways}\\small \\Bold Salud\\end{sideways} &  \\begin{sideways}\\small \\Bold Transporte\\end{sideways} &  \\begin{sideways}\\small \\Bold Comunicaciones\\end{sideways} &  \\begin{sideways}\\small \\Bold Recreación\\end{sideways} &  \\begin{sideways}\\small \\Bold Educación\\end{sideways} &  \\begin{sideways}\\small \\Bold Restaurantes\\end{sideways} &  \\begin{sideways}\\small \\Bold Bienes diversos \\end{sideways}\\\\\n" +
        "         &&&&&&&&&&&&&\\\\[-0.4cm]\n" +
        "\n" +
        "         \\hline\n" +
        "         \\rowcolor{white}\n" +
        "         &&&&&&&&&&&&&\\\\[-0.35cm]\n" +
        "\n" +
        "\\input{CSV/tablas/2_05.csv}   \n" +
        "\\bottomrule\n" +
        "    \\end{tabular}";
        
        escribirLinea(cajotaTabla("2_05", 
                "Cambio mensual del IPC, por región y tipo de gasto",
                "",
                "Variación mensual del IPC por división de gasto y región",
                " República de Guatemala " + getMes() + " " + getYear() + ", en porcentaje y adimensional" ,
                tabla,
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_06() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 15%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_06","Evolución del cambio mensual del IPC", 
                "descripcion206",
                "Variación intermensual del IPC",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_06.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_07() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 15%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_07","Cambio mensual del IPC por gasto", 
                "descripcion207",
                "Variación intermensual del IPC por división de gastos",
                "República de Guatemala, "+ getMes() + " " + getYear()+ ", en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_07.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_08() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 16%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_08","Bienes con mayor incremento mensual", 
                "descripcion208",
                "Principales variaciones intermensuales positivas del IPC,  " ,
                "República de Guatemala," + getMes() + " " + getYear() +", en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_08.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_09() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 16%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_09","Bienes con mayor disminución mensual", 
                "descripcion209",
                "Principales variaciones intermensuales negativas del IPC, ",
                "República de Guatemala," + getMes() + " " + getYear() +", en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_09.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_10() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 17%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_10","Cambio mensual del IPC de alimentos", 
                "descripcion210",
                "Variación intermensual del IPC de alimentos y bebidas no alcohólicas",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_10.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_11() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 17%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_11","Cambio mensual del IPC de \\ bebidas alcohólicas", 
                "descripcion211",
                "Variación intermensual del IPC de \\ bebidas alcohólicas y tabaco",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_11.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
   
    private void section2_12() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 18%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_12","Cambio mensual del IPC de vestuario", 
                "descripcion212",
                "Variación intermensual  del IPC de \\ prendas de vestir y calzado",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_12.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_13() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 18%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_13","Cambio mensual del IPC de  viviendas", 
                "descripcion213",
                "Variación intermensual  del IPC de vivienda, \\ agua, electricidad y gas",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_13.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }

    private void section2_14() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 19%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_14","Cambio mensual del IPC de muebles", 
                "descripcion214",
                "Variación intermensual  del IPC de muebles y artículos para el hogar",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_14.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_15() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 19%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_15","Cambio mensual del IPC de salud", 
                "descripcion215",
                "Variación intermensual  del IPC de salud",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_15.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
       
    private void section2_16() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 20%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_16","Cambio mensual del IPC de transporte", 
                "descripcion216",
                "Variación intermensual  del IPC de transporte",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_16.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_17() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 20%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_17","Cambio mensual del IPC de comunicaciones", 
                "descripcion217",
                "Variación intermensual  del IPC de \\ comunicaciones",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_17.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_18() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 21%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_18","Cambio mensual del IPC de recreación", 
                "descripcion218",
                "Variación intermensual  del IPC de \\ recreación y cultura",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_18.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_19() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 21%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_19","Cambio mensual del IPC de educación", 
                "descripcion219",
                "Variación intermensual  del IPC de \\ educación",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_19.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
       
     private void section2_20() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 22%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_20","Cambio mensual del IPC de restaurantes", 
                "descripcion220",
                "Variación intermensual  del IPC de \\ restaurantes y hoteles",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_20.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_21() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 22%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_21","Cambio mensual del IPC de bienes diversos", 
                "descripcion221",
                "Variación intermensual  del IPC de bienes y \\ servicios diversos",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_21.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
      
     private void section2_23() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 24%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_23","Impacto del tipo de gasto en la variación mensual ", 
                "descripcion223",
                "Incidencia de las divisiones de gasto en la variación intermensual del IPC",
                "República de Guatemala, "+ getMes() + " " + getYear() + ", adimensional",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_23.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
     private void section2_22(){
        escribirLinea(cajotaMapa("2_22", 
                "Cambio mensual del IPC por región", 
                "",
                "Variaciones intermensuales del IPC por regiones",
                " República de Guatemala, " +getMes() + " " + getYear() + ", en porcentaje  ",
                "\\includegraphics[width=52\\cuadri]{2_22.pdf}",
                "Instituto Nacional de Estadística"));
        
    }
     
    private void section2_24() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 24%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_24","Bienes con mayor impacto en el cambio mensual", 
                "descripcion224",
                "Gastos básicos con mayor incidencia positiva  y negativa absoluta en la variación intermensual del IPC",
                "República de Guatemala, " +getMes() + " " + getYear() + ", en porcentaje  ",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_24.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_25(){
        escribirLinea(cajotaMapa("2_25", 
                "Impacto de las regiones en el cambio mensual", 
                "",
                "Incidencias de las regiones en la variación intermensual del IPC",
                 "República de Guatemala " + getMes() + " " + getYear() + ", adimensional",
                "\\includegraphics[width=52\\cuadri]{2_25.pdf}",
                "Instituto Nacional de Estadística"));
        
    }
    
    private void section2_26(){
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 26%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        String tabla = "\n \\begin{tabular}{l@{1}rrrrrrrrrrrrrr@{}}\n" +
        "          \\rowcolor{color1!10!white}\n" +
        "          \\hline\n" +
        "          &&&&&&&&&&&&&\\\\[-0.4cm]\n" +
        "          &  & &  \\phantom{abc} &  \\multicolumn{10}{c}{\\Bold División del Gasto}\\\\\n" +
        "         \\cmidrule{3-14}\n" +
        "         &&&&&&&&&&&&&\\\\[-0.35cm]\n" +
        "         \\multirow{-7}[0]{*}{\\Bold Región}&\\multirow{-7}[0]{*}{\\begin{tabular}{c}\\Bold Variación\\\\ \\Bold Mensual\\end{tabular}} & \\begin{sideways}\\small \\Bold Alimentos\\end{sideways} &  \\begin{sideways} \\small \\Bold Bebidas alcohólicas\\end{sideways} &  \\begin{sideways}\\small \\Bold Vestuario\\end{sideways} &  \\begin{sideways}\\small \\Bold Vivienda\\end{sideways} &  \\begin{sideways}\\small \\Bold Muebles\\end{sideways} &  \\begin{sideways}\\small \\Bold Salud\\end{sideways} &  \\begin{sideways}\\small \\Bold Transporte\\end{sideways} &  \\begin{sideways}\\small \\Bold Comunicaciones\\end{sideways} &  \\begin{sideways}\\small \\Bold Recreación\\end{sideways} &  \\begin{sideways}\\small \\Bold Educación\\end{sideways} &  \\begin{sideways}\\small \\Bold Restaurantes\\end{sideways} &  \\begin{sideways}\\small \\Bold Bienes diversos \\end{sideways}\\\\\n" +
        "         &&&&&&&&&&&&&\\\\[-0.4cm]\n" +
        "\n" +
        "         \\hline\n" +
        "         \\rowcolor{white}\n" +
        "         &&&&&&&&&&&&&\\\\[-0.35cm]\n" +
        "\n" +
        "\\input{CSV/tablas/2_26.csv}   \n" +
        "\\bottomrule\n" +
        "    \\end{tabular}";
        
        escribirLinea(cajotaTabla("2_26", 
                "Cambio anual del IPC, por región y tipo de gasto",
                "",
                "Variación interanual del IPC por división de gasto y región",
                "República de Guatemala, " + getMes() + " " + getYear() + ", en porcentaje y adimensional" ,
                tabla,
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_27() {
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 27%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_27","Evolución del cambio anual del IPC  ", 
                "descripcion227",
                "Variación interanual del IPC",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_27.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_28() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 27%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_28","Cambio anual del IPC por tipo de gasto ", 
                "descripcion228",
                "Variación interanual del IPC  por división de gasto",
                "República de Guatemala, " + getMes() + " " + getYear() + ", en porcentaje y adimensional" ,
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_28.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_29() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 28%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_29","Bienes con mayor aumento anual  ", 
                "descripcion229",
                "Principales variaciones interanuales positivas del IPC, " ,
                "República de Guatemala, " +  getMes() + " " + getYear() + ", en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_29.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_30() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 28%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_30","Bienes con mayor disminución anual", 
                "descripcion230",
                "Principales variaciones interanuales negativas del IPC, \\ " + getMes() + " " + getYear(),
                "República de Guatemala, " +  getMes() + " " + getYear() + ", en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_30.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }    
    
    private void section2_31() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 29%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_31","Cambio anual de IPC de alimentos  ", 
                "descripcion231",
                "Variación interanual  del IPC de alimentos \\ y bebidas no alcohólicas",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_31.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_32() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 29%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_32","Cambio anual del IPC de bebidas alcohólicas ", 
                "descripcion232",
                "Variación interanual del IPC de \\ bebidas alcohólicas y tabaco",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_32.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
      
    private void section2_33() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 30%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_33","Cambio anual de IPC de vestuario  ", 
                "descripcion233",
                "Variación interanual del IPC de \\ prendas de vestir y calzado",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_33.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_34() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 30%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_34","Cambio anual del IPC de vivienda ", 
                "descripcion234",
                "Variación interanual del IPC de vivienda",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_34.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_35() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 31%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_35","Cambio anual de IPC de muebles  ", 
                "descripcion235",
                "Variación interanual del  IPC de muebles \\ y artículos para el hogar",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_35.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_36() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 31%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_36","Cambio anual del IPC de salud ", 
                "descripcion236",
                "Variación interanual del IPC de salud",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_36.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
        
    private void section2_37() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 32%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_37","Cambio anual de IPC de transporte  ", 
                "descripcion237",
                "Variación interanual nacional del IPC de transporte",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_37.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_38() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 32%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_38","Cambio anual del IPC de comunicaciones ", 
                "descripcion238",
                "Variación interanual del IPC de \\ comunicaciones",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_38.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    } 
    
    private void section2_39() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 33%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_39","Cambio anual de IPC de \\ recreación  ", 
                "descripcion237",
                "Variación interanual  del IPC de recreación y cultura",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_39.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_40() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 33%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_40","Cambio anual del IPC de \\ educación ", 
                "descripcion238",
                "Variación interanual del IPC de educación",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_40.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
       
    private void section2_41() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 34%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_41","Cambio anual de IPC de restaurantes  ", 
                "descripcion237",
                "Variación interanual nacional del IPC de \\ los servicios en restaurantes y hoteles",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_41.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_42() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 34%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_42","Cambio anual del IPC de bienes diversos ", 
                "descripcion238",
                "Variación interanual del IPC de bienes y \\ servicios diversos",
                "República de Guatemala, serie histórica, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_42.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_43(){
        escribirLinea(cajotaMapa("2_43", 
                "Cambio anual del IPC por región", 
                "",
                "Variaciones interanuales del IPC de las regiones",
                "República de Guatemala, " + getMes() + " " + getYear() + ", en porcentaje",
                "\\includegraphics[width=52\\cuadri]{2_43.pdf}",
                "Instituto Nacional de Estadística"));
        
    }
    
    private void section2_44() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 36%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_44","Impacto del tipo de gasto en la variación anual", 
                "descripcion203",
                "Incidencia de las divisiones de gasto en la variación interanual del IPC",
                "República de Guatemala, "+getMes() + " " + getYear()+ ", adimensional",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_44.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void section2_45() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 36%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_45","Bienes con mayor impacto en el cambio anual IPC ", 
                "descripcion203",
                "Gastos básicos con mayor incidencia positiva y negativa absoluta en la variación interanual del IPC",
                "República de Guatemala, " + getMes() + " " + getYear()+ ", adimensional",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_45.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    
    private void section2_46(){
        escribirLinea(cajotaMapa("2_46", 
                "Impacto de las regiones en el cambio anual", 
                "",
                "Incidencias de las regiones en la variación interanual del IPC",
                "República de Guatemala, " + getMes() + " " + getYear()+ ", adimensional",
                "\\includegraphics[width=52\\cuadri]{2_46.pdf}",
                "Instituto Nacional de Estadística"));
        
    }
    
    
    private void section2_47() {
           escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 38%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        escribirLinea(seccion( "2_47","Valor del dinero", 
                "descripcion238",
                "Poder adquisitivo del quetzal",
                "República de Guatemala, serie histórica mensual, en porcentaje",
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_47.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    } 
    
    private void sectionR_01(int region){
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 1 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        String tabla = "      \\begin{tabular}{clcrrr}\n" +
"\n" +
"\\hline \\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}\\\\[-0.4cm]\n" +
"\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\\cellcolor{color1!10!white}&\n" +
"\\multicolumn{3}{c}{\\cellcolor{color1!10!white} \\Bold{Variación porcentual}} \\\\\n" +
"\n" +
"\\cmidrule{4-6}\n" +
"\n" +
"\\cellcolor{color1!10!white} \\multirow{-2}[0]{*}{\\Bold Año} &\n" +
"\\cellcolor{color1!10!white} \\multirow{-2}[0]{*}{\\Bold Mes} &\n" +
"\\cellcolor{color1!10!white} \\multirow{-2}[0]{*}{\\Bold Índice} & \\cellcolor{color1!10!white}  \\Bold Mensual & \\cellcolor{color1!10!white} \\Bold Acumulada& \\cellcolor{color1!10!white} \\Bold Interanual\\\\[0.04cm]\n" +
"\n" +
"\\hline\n" +
"\\rowcolor{white}\n" +
"\\input{CSV/tablas/"+ (region+2) +"_01.csv}   \n" +
"\\bottomrule\n" +
"\n" +
"    \\end{tabular}";
        
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
                "IPC " + getRomano(region) + " por división de gasto, base diciembre del 2010",
                getMes() + " " + getYear(),
                "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{"+(region + 2)+"_03.tex}  \\end{tikzpicture}",
                "Instituto Nacional de Estadística"));
    }
    
    private void sectionR_04(int region){
        escribirLinea("\n \n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%HOJA 3 de region " + region + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% \n\n");
        String tabla = "\\begin{tabular}{lrrrrrrrrrrrrrr@{}}\n" +
        "          \\rowcolor{color1!10!white}\n" +
        "          \\hline\n" +
        "          &&&&&&&&&&&&&\\\\[-0.4cm]\n" +
        "          &  & &  \\phantom{abc} &  \\multicolumn{10}{c}{\\Bold División del Gasto}\\\\\n" +
        "         \\cmidrule{3-14}\n" +
        "         &&&&&&&&&&&&&\\\\[-0.35cm]\n" +
        "         \\multirow{-7}[0]{*}{\\Bold Período}&\\multirow{-7}[0]{*}{\\begin{tabular}{c}\\Bold Variación\\\\ \\Bold Mensual\\end{tabular}} & \\begin{sideways}\\small \\Bold Alimentos\\end{sideways} &  \\begin{sideways} \\small \\Bold Bebidas alcohólicas\\end{sideways} &  \\begin{sideways}\\small \\Bold Vestuario\\end{sideways} &  \\begin{sideways}\\small \\Bold Vivienda\\end{sideways} &  \\begin{sideways}\\small \\Bold Muebles\\end{sideways} &  \\begin{sideways}\\small \\Bold Salud\\end{sideways} &  \\begin{sideways}\\small \\Bold Transporte\\end{sideways} &  \\begin{sideways}\\small \\Bold Comunicaciones\\end{sideways} &  \\begin{sideways}\\small \\Bold Recreación\\end{sideways} &  \\begin{sideways}\\small \\Bold Educación\\end{sideways} &  \\begin{sideways}\\small \\Bold Restaurantes\\end{sideways} &  \\begin{sideways}\\small \\Bold Bienes diversos \\end{sideways}\\\\\n" +
        "         &&&&&&&&&&&&&\\\\[-0.4cm]\n" +
        "\n" +
        "         \\hline\n" +
        "         \\rowcolor{white}\n" +
        "         &&&&&&&&&&&&&\\\\[-0.35cm]\n" +
        "\n" +
        "\\input{CSV/tablas/" + (region+2) + "_04.csv}   \n" +
        "\\bottomrule\n" +
        "    \\end{tabular}";
        
        escribirLinea(cajotaTabla((region + 2) + "_04", 
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
        escribirLinea(seccion((region + 2)+"_08","Bienes con mayor disminución mensual", 
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
        String tabla = "\\begin{tabular}{lrrrrrrrrrrrrrr@{}}\n" +
"          \\rowcolor{color1!10!white}\n" +
"          \\hline\n" +
"          &&&&&&&&&&&&&\\\\[-0.4cm]\n" +
"          &  & &  \\phantom{abc} &  \\multicolumn{10}{c}{\\Bold División del Gasto}\\\\\n" +
"         \\cmidrule{3-14}\n" +
"         &&&&&&&&&&&&&\\\\[-0.35cm]\n" +
"         \\multirow{-7}[0]{*}{\\Bold Período}&\\multirow{-7}[0]{*}{\\begin{tabular}{c}\\Bold Variación\\\\ \\Bold Mensual\\end{tabular}} & \\begin{sideways}\\small \\Bold Alimentos\\end{sideways} &  \\begin{sideways} \\small \\Bold Bebidas alcohólicas\\end{sideways} &  \\begin{sideways}\\small \\Bold Vestuario\\end{sideways} &  \\begin{sideways}\\small \\Bold Vivienda\\end{sideways} &  \\begin{sideways}\\small \\Bold Muebles\\end{sideways} &  \\begin{sideways}\\small \\Bold Salud\\end{sideways} &  \\begin{sideways}\\small \\Bold Transporte\\end{sideways} &  \\begin{sideways}\\small \\Bold Comunicaciones\\end{sideways} &  \\begin{sideways}\\small \\Bold Recreación\\end{sideways} &  \\begin{sideways}\\small \\Bold Educación\\end{sideways} &  \\begin{sideways}\\small \\Bold Restaurantes\\end{sideways} &  \\begin{sideways}\\small \\Bold Bienes diversos \\end{sideways}\\\\\n" +
"         &&&&&&&&&&&&&\\\\[-0.4cm]\n" +
"\n" +
"         \\hline\n" +
"         \\rowcolor{white}\n" +
"         &&&&&&&&&&&&&\\\\[-0.35cm]\n" +
"\n" +
"\\input{CSV/tablas/" + (region+2) +"_23.csv}   \n" +
"\\bottomrule\n" +
"    \\end{tabular}";
        
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
                System.out.println("ipc <- cargaMasiva('" +  ruta +")");
                System.out.println("***********SIN CODIFICACION********************");
                REXP listadoCSV = rr.get().eval("ipc <- cargaMasiva('" +  ruta +"')");
                REXP nombres = rr.get().eval("names(ipc)");
                rr.get().eval("setListIpc(ipc)");
                System.out.println(listadoCSV);
                System.out.println(nombres);
                
            }
    }
    
     @Override
    protected void preambuloPresentacion(){
        File source = new File("/home/ineservidor/IPC/Utilidades");
        File dest = new File(getRuta());
        try {
            FileUtils.copyDirectory(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    protected void portadaAzul(){
         try{
           FileWriter escritora = new FileWriter(getTex(),true);
           BufferedWriter buffer = new BufferedWriter(escritora);
           buffer.write("\n \n \\includepdf{transicion.pdf} \n \n");
           buffer.close();
        } catch(IOException ex){
            System.out.println(ex);
        }
    }

    private String getSerie() {
        System.out.println(getYear());
        int anio = Integer.parseInt(getYear()) -2;
        System.out.println(String.valueOf(anio)  + "-" + getYear());
        return( String.valueOf(anio)  + "-" + getYear() );
        
    }

    
}
