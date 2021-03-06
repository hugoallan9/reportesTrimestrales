/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportestrimestrales;

import descripciones_faltas_judiciales.Generador;
import descripciones_faltas_judiciales.Porcentaje;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carto
 */

public class Tabla {
    File texs;
    String ruta;
    String rutaTex;
    String archivo;
    List trimestres;
    SesionR r;
    DecimalFormat df;
    DecimalFormat dm;
    String encabezadoTabla;
    private String encabezadoCSV1;
   
    public Tabla(String rutaTEX, List listado,SesionR rr){
        System.out.println("Iniciando el constructor de tabla");
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        df =  (DecimalFormat) nf;
        df.applyPattern("#.##");
        dm = (DecimalFormat) nf;
        dm.applyPattern("#,###");
        texs = new File(rutaTEX);
        this.trimestres = listado;
        this.rutaTex = rutaTEX;
        this.r = rr;
        System.out.println("Finalizando el contructor de tabla");
        this.encabezadoTabla = "";
        this.encabezadoCSV1="Departamento";
    }
    
    public void setRuta(String ruta){
        this.ruta = ruta;
    }
    private void setEncabezadoTabla(String encabezado){
        this.encabezadoTabla = encabezado;
    }
    
    public void generar(){
        generarCSV1("A_01");
        generarCSV1("A_03");
        generarCSV1("A_05");
        generarCSV1("A_07");
        generarCSV1("A_09");
        setEncabezadoTabla("Número de nacimientos");
        generarCSV2("A_02");
        setEncabezadoTabla("Número de defunciones");
        generarCSV2("A_04");
        setEncabezadoTabla("\\parbox{3.5cm}{Número de defunciones fetales}");
        generarCSV2("A_06");
        setEncabezadoTabla("Número de matrimonios");
        generarCSV2("A_08");
        setEncabezadoTabla("Número de divorcios");
        generarCSV2("A_10");
    }
    
    public void generarTransportes(){
        this.encabezadoCSV1 = "\\parbox{1.6cm}{Tipo de producto}";
        generarCSV1("A_01",7);
        generarCSV1("A_02");
        generarCSV1("A_04");
        generarCSV1("A_05");
        generarCSV1("A_07");
        generarCSV1("A_16");
        generarCSV1("A_17");
        generarCSV1("A_18");
        generarCSV1("A_19");
        generarCSV1("A_20");
        generarCSV1("A_21");
        generarCSV1("A_22");
        generarCSV1("A_23");
        
        setEncabezadoTabla("Número de envíos");
        generarCSV2("A_03");
        setEncabezadoTabla("Número de encomiendas");
        generarCSV2("A_06");
        setEncabezadoTabla("Peso");
        generarCSV2("A_08");
        setEncabezadoTabla("Peso");
        generarCSV2("A_09");
        setEncabezadoTabla("Número de vuelos");
        generarCSV2("A_10");
        setEncabezadoTabla("Número de pasajeros");
        generarCSV2("A_11");
        setEncabezadoTabla("Número de pasajeros");
        generarCSV2("A_12");
        setEncabezadoTabla("Número de buques");
        generarCSV2("A_13");
        setEncabezadoTabla("Peso");
        generarCSV2("A_14");
        setEncabezadoTabla("Peso");
        generarCSV2("A_15");
        
        
        
        
        
        
        
        
        
        
    }
    
    public void generarHospitalarias(){
        generarCSV1("A_01");
        generarCSV1("A_03");
        setEncabezadoTabla("\\parbox{3.5cm}{Número de consultas externas}");
        generarCSV2("A_02");
        setEncabezadoTabla("\\parbox{3.5cm}{Número de consultas internas}");
        generarCSV2("A_04");
        
    }
    
    public void generarComercioExterior(){
        setEncabezadoTabla("Exportaciones en US\\$");
        generarCSV2("A_01");
        generarCSV2("A_03");
        generarCSV2("A_05");
        generarCSV2("A_07");
        
        setEncabezadoTabla("Importaciones en US\\$");
        generarCSV2("A_02");
        generarCSV2("A_04");
        generarCSV2("A_06");
        generarCSV2("A_08");
        
    }
    public void generarTransito(){
        setEncabezadoTabla("\\parbox{3.5cm}{Número de eventos de tránsito}");
        generarCSV2("A_01");
        setEncabezadoTabla("Número de lesionados");
        generarCSV2("A_02");
        setEncabezadoTabla("Número de víctimas fatales");
        generarCSV2("A_03");
        setEncabezadoTabla("\\parbox{3.5cm}{Número de vehículos involucrados}");
        generarCSV2("A_04");
        setEncabezadoTabla("\\parbox{3.5cm}{Número de eventos con atropello}");
        generarCSV2("A_05");
        setEncabezadoTabla("\\parbox{3.5cm}{Número de atropellados lesionados}");
        generarCSV2("A_06");
        setEncabezadoTabla("\\parbox{3.5cm}{Número de atropellados fatales}");
        generarCSV2("A_07");
        
    }
    public void generarFaltasJudiciales(){
        setEncabezadoTabla("Número de faltas judiciales");
        generarCSV2("A_01");
        setEncabezadoTabla("\\parbox{3.5cm}{Número de faltas contra las personas}");  
        generarCSV2("A_02");
        setEncabezadoTabla("\\parbox{3.5cm}{Número de faltas contra la propiedad}");
        generarCSV2("A_03");
        setEncabezadoTabla("\\parbox{3.5cm}{Número de faltas contra el orden público}");
        generarCSV2("A_04");
        setEncabezadoTabla("\\parbox{3.5cm}{Número de faltas contra las buenas costumbres}");
        generarCSV2("A_05");
        
    }
    public void generarHechosDelictivos(){
        setEncabezadoTabla("Número de detenidos");
        generarCSV2("A_01");
        setEncabezadoTabla("Número de víctimas");
        generarCSV2("A_02");
        setEncabezadoTabla("Número de sindicados");
        generarCSV2("A_03");
        setEncabezadoTabla("\\parbox{3.5cm}{Número de evaluaciones médico legales}");
        generarCSV2("A_04");
        setEncabezadoTabla("Número de necropsias");
        generarCSV2("A_05");
        
    }
    public void generarAgropecuarias(){
        setEncabezadoTabla("\\parbox{3.5cm}{Producción de azúcar blanca (en quintales)}");
        generarCSV2("A_01");
        setEncabezadoTabla("\\parbox{3.5cm}{Producción de azúcar cruda (en quintales)}");
        generarCSV2("A_02");
        setEncabezadoTabla("\\parbox{3.5cm}{Trigo procesado (en quintales)}");
        generarCSV2("A_03");
        setEncabezadoTabla("\\parbox{3.5cm}{Producción de banano (en quintales)}");
        generarCSV2("A_04");
        setEncabezadoTabla("\\parbox{3.5cm}{Destace ganado bovino (número de cabezas)}");    
        generarCSV2("A_05");
        setEncabezadoTabla("\\parbox{3.5cm}{Destace ganado porcino (número de cabezas)}");    
        generarCSV2("A_06");
        setEncabezadoTabla("\\parbox{3.5cm}{Destace ganado ovino (número de cabezas)}");    
        generarCSV2("A_07");
        setEncabezadoTabla("\\parbox{3.5cm}{Destace ganado caprino (número de cabezas)}");    
        generarCSV2("A_08");
    }
    public void generarVIF(){
        setEncabezadoTabla("Número de denuncias VIF");    
        generarCSV2("A_01");
        setEncabezadoTabla("\\parbox{3.5cm}{Número de mujeres víctimas de VIF}");    
        generarCSV2("A_02");
        setEncabezadoTabla("\\parbox{3.5cm}{Número de hombres víctimas de VIF}");    
        generarCSV2("A_03");
        setEncabezadoTabla("\\parbox{3.5cm}{Número de hombres agresores VIF}");    
        generarCSV2("A_04");
    }
    private void escribirTEX(String nombre, String texto,boolean compilar){
     File file = new File(nombre);
     file.getParentFile().setReadable(true, false);
    file.getParentFile().setExecutable(true, false);
    file.getParentFile().setWritable(true, false);
    file.getParentFile().mkdirs();
    String fileName = nombre + ".tex";
     Path textFile = Paths.get(fileName);
     List<String> lines = new ArrayList<>();
     lines.add(texto);
     
    try {
        Files.write(textFile, lines, StandardCharsets.UTF_8);
        if (compilar){
            System.out.println(textFile);
            System.out.println(r.get().eval("compilar('" + textFile+ "', mostrar = F)"));
            System.out.println(r.get().eval("pdfcrop(ruta = '" + textFile+"')"));
        }
            
    } catch (IOException ex) {
        Logger.getLogger(Generador.class.getName()).log(Level.SEVERE, null, ex);
    }
 }
     
    
    
    
     private void generarCSV1(String csv) {
         
        
         
        File f = new File(ruta, csv + ".csv");
    BufferedReader br = null;
    String line = "";
        boolean encabezado = false;
        List<String> listaPlantilla= new ArrayList();
        List<String> lista = new ArrayList();
        listaPlantilla.add("\\input{../../../pre2}");
        listaPlantilla.add("\\begin{document}");
        listaPlantilla.add("	\\addtocounter{section}{1}");
        listaPlantilla.add("\\begin{center}");
        listaPlantilla.add("\\begin{tabular}{lS[table-format=2.1]S[table-format=2.1]S[table-format=2.1]S[table-format=2.1]S[table-format=2.1]S[table-format=2.1]S[table-format=2.1]S[table-format=2.1]S[table-format=2.1]}");
        listaPlantilla.add("\\hline");
        listaPlantilla.add("\\\\[-0.2cm]");
        listaPlantilla.add("\\rowcolor{white!25!white} &&&\\\\[-0.36cm] \\rowcolor{white!25!white}");
        listaPlantilla.add("\\input{info.tex}");
        listaPlantilla.add("\\hline");
        listaPlantilla.add("&&&&&&&&&\\\\[-0.36cm]");
        listaPlantilla.add("\\end{tabular}\\addtocounter{Cuadro}{1}");
        listaPlantilla.add("\\end{center}");
        listaPlantilla.add("\\end{document}");
        
        
        
        try {
            br = new BufferedReader(new FileReader(f.getAbsolutePath()));
            lista.add("\\multirow{2}{*}{\\Bold{"+encabezadoCSV1+"}} &");//aqui va el texto en latex del archivo info.tex
            for(int i = 0;i<9;i++){
                if(i==8){
                    lista.add("\\multirow{2}{*}{\\rotatebox[origin = c]{90}{\\Bold{"+trimestres.get(8).toString()+"}}} \\\\");
                }else{
                lista.add("\\multirow{2}{*}{\\rotatebox[origin = c]{90}{\\Bold{"+trimestres.get(i).toString()+"}}} &");
                }
            }
            lista.add("& & & & & & & & &\\\\[0.25cm]");
            lista.add("\\hline \\\\[-0.2cm]");
            
        while ((line = br.readLine()) != null) {
            String[] valores = line.split(";");
            
            if(encabezado){
                System.out.println("salta la linea"+valores[0].toString()+"\n");
                        encabezado = false;
            }else{
                
                    System.out.println("dato:"+valores[0].toString()+"\n");
                    String val = valores[0];
                    System.out.println("La longitud de valores es " + valores.length );
                    lista.add("\\multicolumn{1}{l}{"+val+"} & ");
                    for(int j=1;j< valores.length;j++){
                        System.out.println("Estoy entrando al for con j igual " + j );
                        if(j ==(valores.length -1))
                            lista.add("\\multicolumn{1}{g{0.9cm}}{"+dm.format(Double.parseDouble(valores[j]))+"} \\\\[0.15cm]");
                        else
                            lista.add("\\multicolumn{1}{g{0.9cm}}{"+dm.format(Double.parseDouble(valores[j]))+"} &");

                    }
                    
                }
            }
    } catch (FileNotFoundException e) {
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
        escribirTEX(new File(rutaTex, new File(csv, "info").getPath()).getAbsolutePath(), listToString(lista),false);
        escribirTEX(new File(rutaTex, new File(csv, "plantillaTabla1").getPath()).getAbsolutePath(), listToString(listaPlantilla),true);
      
         
        
  }
      private void generarCSV1(String csv,int column) {         
        File f = new File(ruta, csv + ".csv");
    BufferedReader br = null;
    String line = "";
        boolean encabezado = false;
        List<String> listaPlantilla= new ArrayList();
        List<String> lista = new ArrayList();
        String tabularFormat ="\\begin{tabular}{l" ;
        String newLineWithAmp="";
        for(int ix = 0;ix<column;ix++){
            tabularFormat += "S[table-format=2.1]";
            newLineWithAmp+="&";
        }
        tabularFormat +="}";
        listaPlantilla.add("\\input{../../../pre2}");
        listaPlantilla.add("\\begin{document}");
        listaPlantilla.add("	\\addtocounter{section}{1}");
        listaPlantilla.add("\\begin{center}");
        
        
        listaPlantilla.add(tabularFormat);
        listaPlantilla.add("\\hline");
        listaPlantilla.add("\\\\[-0.2cm]");
        listaPlantilla.add("\\rowcolor{white!25!white} &&&\\\\[-0.36cm] \\rowcolor{white!25!white}");
        listaPlantilla.add("\\input{info.tex}");
        listaPlantilla.add("\\hline");
        listaPlantilla.add(newLineWithAmp + "\\\\[-0.36cm]");
        listaPlantilla.add("\\end{tabular}\\addtocounter{Cuadro}{1}");
        listaPlantilla.add("\\end{center}");
        listaPlantilla.add("\\end{document}");
        
        
        
        try {
            br = new BufferedReader(new FileReader(f.getAbsolutePath()));
            lista.add("\\multirow{2}{*}{\\Bold{"+encabezadoCSV1+"}} &");//aqui va el texto en latex del archivo info.tex
            
            for(int i = 0;i<column;i++){
                if(i==(column-1)){
                    lista.add("\\multirow{2}{*}{\\rotatebox[origin = c]{90}{\\Bold{"+trimestres.get(i).toString()+"}}} \\\\");
                }else{
                lista.add("\\multirow{2}{*}{\\rotatebox[origin = c]{90}{\\Bold{"+trimestres.get(i).toString()+"}}} &");
                }
            }
            lista.add(newLineWithAmp + "\\\\[0.25cm]");
            lista.add("\\hline \\\\[-0.2cm]");
            
        while ((line = br.readLine()) != null) {
            String[] valores = line.split(";");
            
            if(encabezado){
                System.out.println("salta la linea"+valores[0].toString()+"\n");
                        encabezado = false;
            }else{
                
                    System.out.println("dato:"+valores[0].toString()+"\n");
                    String val = valores[0];
                    System.out.println("La longitud de valores es " + valores.length );
                    lista.add("\\multicolumn{1}{l}{"+val+"} & ");
                    for(int j=1;j< valores.length  ;j++){
                        System.out.println("Estoy entrando al for con j igual " + j );
                        if((valores.length -1) ==j)
                            lista.add("\\multicolumn{1}{g{0.9cm}}{"+dm.format(Long.parseLong(valores[j]))+"} \\\\[0.15cm]");
                        else
                            lista.add("\\multicolumn{1}{g{0.9cm}}{"+dm.format(Long.parseLong(valores[j]))+"} &");

                    }
                    
                }
            }
    } catch (FileNotFoundException e) {
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
        escribirTEX(new File(rutaTex, new File(csv, "info").getPath()).getAbsolutePath(), listToString(lista),false);
        escribirTEX(new File(rutaTex, new File(csv, "plantillaTabla1").getPath()).getAbsolutePath(), listToString(listaPlantilla),true);
      
         
        
  }
     private String cambioStringEncabezadoFila(String val){
         switch(val.toLowerCase()){
             case "ca":
                 return "Centroamérica";
             case "na":
                 return "Norteamérica";
             case "sa":
                 return "Sudamérica";
             case "eu":
                 return "Europa";
             case "aa":
                 return "Asia, África y Oceanía";
             case "contenedor":
                 return "Contenedorizado";
             case "granelliq":
                 return "Granel Líquido";
             case "granelsol":
                 return "Granel Sólido";
             default:
                 return val;
         }         
     }
     
     public void generarCSV2(String csv) {
         
        File f = new File(ruta,csv + ".csv");
    BufferedReader br = null;
    String line = "";
        boolean encabezado = false;
        List<String> listaPlantilla= new ArrayList();
        List<String> lista = new ArrayList();

        listaPlantilla.add("\\input{../../../pre2}");
        listaPlantilla.add("\\begin{document}");
        listaPlantilla.add("\\addtocounter{section}{1}");
        listaPlantilla.add("\\begin{center}");
        listaPlantilla.add("\\begin{tabular}{lS[table-format=2.1]S[table-format=2.1]S[table-format=2.1]S[table-format=2.1]}");
        listaPlantilla.add("\\hline");
        listaPlantilla.add("\\\\[-0.2cm]");
        listaPlantilla.add("\\input{info.tex}");
        listaPlantilla.add("\\hline");
        listaPlantilla.add("\\end{tabular}\\addtocounter{Cuadro}{1}");
        listaPlantilla.add("\\end{center}");
        listaPlantilla.add("\\end{document}");
         
        try {
            br = new BufferedReader(new FileReader(f.getAbsolutePath()));
            lista.add("&\\multirow{2}{*}{\\Bold{Periodo}} ");//aqui va el texto en latex del archivo info.tex
            lista.add("&\\multirow{2}{*}{\\Bold{"+ encabezadoTabla +"}}");
            lista.add("&\\multirow{2}{2.5cm}{\\Bold{Variación entre periodos}} ");
            lista.add("&\\multirow{2}{3.9cm}{\\Bold{Variación respecto al mismo periodo año anterior}}\\\\");
            lista.add("&&&&\\\\[0.2cm]");
            lista.add("\\hline \\\\[-0.2cm]");
            lista.add("\\multirow{13}{*}{\\rotatebox[origin = c]{90}{\\Bold{Mensual}}} ");

            int x=1;
        while ((line = br.readLine()) != null) {
            
            if(encabezado)
                encabezado=false;
            else{
                    String[] valores = line.split(";");
                    if(x==14){
                        lista.add("\\hline");
                        lista.add("\\\\[-0.1cm]");
                        lista.add("\\multirow{9}{*}{\\rotatebox[origin = c]{90}{\\Bold{Trimestral}}} ");    
                    }
                    if(x==23){
                        lista.add("\\hline");
                        lista.add("\\\\[-0.2cm]");
                        lista.add("\\multirow{4}{*}{\\rotatebox[origin = c]{90}{\\Bold{Anual}}}");
                    }

                    lista.add("&\\multicolumn{1}{g{2cm}}{"+valores[0]+"} &");
                    try{
                        lista.add("\\multicolumn{1}{g{3cm}}{"+dm.format(Long.parseLong(valores[1]))+"} & ");
                    }catch(Exception e){
                        lista.add("\\multicolumn{1}{g{3cm}}{"+ valores[1]+"} & ");
                    }
                    
                    try{
                        lista.add("\\multicolumn{1}{g{2cm}}{"+df.format(Double.parseDouble(valores[2]))+"} &");
                    }catch(Exception e){
                        lista.add("\\multicolumn{1}{g{2cm}}{"+ valores[2]+"} &");
                    }
                    
                    String linean= "";
                    try{                        
                        linean = "\\multicolumn{1}{g{2.5cm}}{"+df.format(Double.parseDouble(valores[3]))+"}";
                    }catch (Exception e){

                        e.printStackTrace();
                    }
                    linean = linean+"\\\\[0.1cm]";
                    lista.add(linean);
                    x++;
                }
            }
    } catch (FileNotFoundException e) {
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
        escribirTEX(new File(rutaTex, new File(csv, "info").getPath()).getAbsolutePath(), listToString(lista),false);
        escribirTEX(new File(rutaTex, new File(csv, "plantillaTabla2").getPath()).getAbsolutePath(), listToString(listaPlantilla),true);        
        
        
        
         
         
        
  }
     
  private String listToString(List listado){
      String val ="";
      int size = listado.size();
      for(int i = 0;i<size;i++){
          val = val+listado.get(i).toString()+"\n";
      }
      return val;
  }
    
}
