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
    }
    
    public void setRuta(String ruta){
        this.ruta = ruta;
    }
    
    public void generar(){
        generarCSV1("A_01");
        generarCSV1("A_03");
        generarCSV1("A_05");
        generarCSV1("A_07");
        generarCSV1("A_09");
        
        generarCSV2("A_02");
        generarCSV2("A_04");
        generarCSV2("A_06");
        generarCSV2("A_08");
        generarCSV2("A_10");
    }
    
    public void generarHospitalarias(){
        generarCSV1("A_01");
        generarCSV1("A_03");
        
        generarCSV2("A_02");
        generarCSV2("A_04");
        
    }
    
    public void generarComercioExterior(){
        generarCSV2("A_01");
        generarCSV2("A_02");
    }
    public void generarTransito(){
        generarCSV2("A_01");
        generarCSV2("A_02");
        generarCSV2("A_03");
        generarCSV2("A_04");
        generarCSV2("A_05");
        generarCSV2("A_06");
        
        
        
    }
    public void generarFaltasJudiciales(){
        generarCSV2("A_01");
        generarCSV2("A_02");
        generarCSV2("A_03");
        generarCSV2("A_04");
        generarCSV2("A_05");
        
    }
    public void generarHechosDelictivos(){
        generarCSV2("A_01");
        generarCSV2("A_02");
        generarCSV2("A_03");
        generarCSV2("A_04");
        generarCSV2("A_05");
        
    }
    public void generarAgropecuarias(){
        generarCSV2("A_01");
        generarCSV2("A_02");
        generarCSV2("A_03");
        generarCSV2("A_04");
        generarCSV2("A_05");
        generarCSV2("A_06");
        generarCSV2("A_07");
        generarCSV2("A_08");
    }
    public void generarVIF(){
        generarCSV2("A_01");
        
        generarCSV2("A_02");
        generarCSV2("A_03");
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
        boolean encabezado = true;
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
            lista.add("\\multirow{2}{*}{\\Bold{Departamento}} &");//aqui va el texto en latex del archivo info.tex
            for(int i = 0;i<9;i++){
                if(i==8){
                    lista.add("\\multirow{2}{*}{\\rotatebox[origin = c]{90}{"+trimestres.get(8).toString()+"}} \\\\");
                }else{
                lista.add("\\multirow{2}{*}{\\rotatebox[origin = c]{90}{"+trimestres.get(i).toString()+"}} &");
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
                    lista.add("\\multicolumn{1}{l}{"+valores[0]+"} & ");
                    for(int j=1;j<10;j++){

                        if(j==9)
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
            lista.add("&\\multirow{2}{*}{\\Bold{Numero de nacimientos}}");
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
