/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportestrimestrales;
import au.com.bytecode.opencsv.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.rosuda.JRI.REXP;
/**
 *
 * @author hugo
 */
public class Canasta extends Documento{
    private List<String[]>mes1;
    private List<String[]>mes2;
    private List<String[]>mes3;
    private List<String[]>mes4;
    private List<String[]>mes5;
    private List<String[]>mes6;
    private List<String[]>mes7;
    private List<String[]>mes8;
    private List<String[]>mes9;
    private List<String[]>mes10;
    private List<String[]>mes11;
    private List<String[]>mes12;
    private List<String[]>mes13;
    private List<String>meses;
    private List<String>mesesCortos;
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
    
    
    public Canasta(String titulo, String mes, String pYear, String rutaCSV){
        super(titulo, mes, pYear);
        mes1 = new ArrayList<String[]>();
        mes2 = new ArrayList<String[]>();
        mes3 = new ArrayList<String[]>();
        mes4 = new ArrayList<String[]>();
        mes5 = new ArrayList<String[]>();
        mes6 = new ArrayList<String[]>();
        mes7 = new ArrayList<String[]>();
        mes8= new ArrayList<String[]>();
        mes9 = new ArrayList<String[]>();
        mes10 = new ArrayList<String[]>();
        mes11 = new ArrayList<String[]>();
        mes12 = new ArrayList<String[]>();
        mes13 = new ArrayList<String[]>();
        meses = new ArrayList<String>();
        mesesCortos = new ArrayList<String>();
        mesesCortos = calcularMesesCortos(convertirMesANumero(mes), pYear);
        leerDatos(mes, pYear);
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
        capitulos.add("Canasta básica");
        capitulos.add("Productos de la canasta básica alimentaria");
    }
    
        
    protected void cargarCSV(String ruta){
        if (!rr.get().waitForR())
            {
                System.err.println("No se pudo establecer  conexión con R ");
            }else {
                rr.get().eval("library(funcionesINE)");
                REXP listadoCSV = rr.get().eval("delictivos <- cargaMasiva('" +  ruta +"', codificacion = 'utf8')");
                REXP nombres = rr.get().eval("names(delictivos)");
                System.out.println(listadoCSV);
                System.out.println(nombres);
            }
    }
    
    
    protected void setIntroCapitulos(){
        introCapitulos.add("Acá descripción de la canasta básica alimentaria y vital");
        introCapitulos.add("Descripción de productos que conforman la canasta básica alimentaria");
        
    }
    
    protected void setContenidos(){
         contenidos.add(cargarCapitulo1());
         contenidos.add(cargarCapitulo2());
         System.out.println("cargados los contenidos");
    }

     protected ArrayList cargarCapitulo1(){
        ArrayList cap1 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("1_01");
        seccion1.add("Canasta básica alimentaria");
        seccion1.add("Costo de la canasta básica alimentaria");
        seccion1.add(formatoSerie + ", en quetzales");
        seccion1.add("1_01.tex");
        seccion1.add("INE, con datos de la Policía Nacional Civil");
        seccion1.add(true);
        seccion1.add("1_02");
        seccion1.add("Canasta básica vital");
        seccion1.add("Costo de la canasta básica vital");
        seccion1.add(formatoTrimestre + ", en quetzales");
        seccion1.add("1_02.tex");
        seccion1.add("INE, con datos de la Policía Nacional Civil");
        seccion1.add(true);
        cap1.add(seccion1);
        return cap1;        
        
    }
    protected ArrayList cargarCapitulo2(){
        ArrayList cap2 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("2_01");
        seccion1.add("Arroz");
        seccion1.add("Costo medio del arroz corriente de segunda");
        seccion1.add(formatoSerie + ", en quetzales por 460 gramos");
        seccion1.add("2_01.tex");
        seccion1.add("INE, con datos de la Policía Nacional Civil");
        seccion1.add(true);
        seccion1.add("2_02");
        seccion1.add("Hojuelas de maíz");
        seccion1.add("Costo medio de las hojuelas de maíz");
        seccion1.add(formatoTrimestre + ", en qetzales por gramo");
        seccion1.add("2_02.tex");
        seccion1.add("INE, con datos de la Policía Nacional Civil");
        seccion1.add(true);
        cap2.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("2_03");
        seccion2.add("Incaparina");
        seccion2.add("Costo medio de la incaparina");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("2_03.tex");
        seccion2.add("INE, con datos de la Policía Nacional Civil");
        seccion2.add(true);
        seccion2.add("2_04");
        seccion2.add("Avena mosh");
        seccion2.add("Costo medio de la avena mosh");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("2_04.tex");
        seccion2.add("INE, con datos de la Policía Nacional Civil");
        seccion2.add(true);
        cap2.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("2_05");
        seccion3.add("Pan francés");
        seccion3.add("Costo medio pan tipo francés");
        seccion3.add(formatoTrimestre + "en unidades");
        seccion3.add("2_05.tex");
        seccion3.add("INE, con datos de la Policía Nacional Civil");
        seccion3.add(true);
        seccion3.add("2_06");
        seccion3.add("Pan dulce");
        seccion3.add("Costo medio pan dulce de manteca");
        seccion3.add(formatoTrimestre + "en unidades");
        seccion3.add("2_06.tex");
        seccion3.add("INE, con datos de la Policía Nacional Civil");
        seccion3.add(true);
        cap2.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("2_07");
        seccion4.add("Fideos");
        seccion4.add("Costo medio de los fideos ");
        seccion4.add(formatoTrimestre + "en unidades");
        seccion4.add("2_07.tex");
        seccion4.add("INE, con datos de la Policía Nacional Civil");
        seccion4.add(true);
        seccion4.add("2_08");
        seccion4.add("Tortillas calientes");
        seccion4.add("Costo medio de las tortillas calientes");
        seccion4.add(formatoTrimestre + "en porcentaje");
        seccion4.add("2_08.tex");       
        seccion4.add("INE, con datos de la Policía Nacional Civil");
        seccion4.add(true);
        cap2.add(seccion4);
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("2_09");
        seccion5.add("Posta");
        seccion5.add("Costo medio de carne de res sin hueso o posta ");
        seccion5.add(formatoTrimestre + "en porcentaje");
        seccion5.add("2_09.tex");
        seccion5.add("INE, con datos de la Policía Nacional Civil");
        seccion5.add(true);
        seccion5.add("2_10");
        seccion5.add("Carne de res con hueso");
        seccion5.add("Costo medio de carne de res con hueso para cocido");
        seccion5.add(formatoTrimestre + "en unidades");
        seccion5.add("2_10.tex");       
        seccion5.add("INE, con datos de la Policía Nacional Civil");
        seccion5.add(true);
        cap2.add(seccion5);
              
                
        ArrayList seccion6 = new ArrayList();
        seccion6.add("2_11");
        seccion6.add("Pollo de Granja");
        seccion6.add("Costo medio de pollo fresco entero de granja sin menudos  ");
        seccion6.add(formatoTrimestre + "en porcentaje");
        seccion6.add("2_11.tex");
        seccion6.add("INE, con datos de la Policía Nacional Civil");
        seccion6.add(true);
        seccion6.add("2_12");
        seccion6.add("Salchicha");
        seccion6.add("Costo medio de variedades de salchichas");
        seccion6.add(formatoTrimestre + "en unidades");
        seccion6.add("2_12.tex");       
        seccion6.add("INE, con datos de la Policía Nacional Civil");
        seccion6.add(true);
        cap2.add(seccion6);
        
              
        ArrayList seccion7 = new ArrayList();
        seccion7.add("2_13");
        seccion7.add("Leche en polvo");
        seccion7.add("Costo medio de la leche entera en polvo");
        seccion7.add(formatoTrimestre + "en porcentaje");
        seccion7.add("2_13.tex");
        seccion7.add("INE, con datos de la Policía Nacional Civil");
        seccion7.add(true);
        seccion7.add("2_14");
        seccion7.add("Leche fluida");
        seccion7.add("Costo medio de leche entera fluida");
        seccion7.add(formatoTrimestre + "en unidades");
        seccion7.add("2_14.tex");       
        seccion7.add("INE, con datos de la Policía Nacional Civil");
        seccion7.add(true);
        cap2.add(seccion7);
        
        
        ArrayList seccion8 = new ArrayList();
        seccion8.add("2_15");
        seccion8.add("Queso fresco");
        seccion8.add("Costo medio del queso fresco de vaca");
        seccion8.add(formatoTrimestre + "en porcentaje");
        seccion8.add("2_15.tex");
        seccion8.add("INE, con datos de la Policía Nacional Civil");
        seccion8.add(true);
        seccion8.add("2_16");
        seccion8.add("Crema de vaca");
        seccion8.add("Costo medio de la crema fresca pasteurizada envasada");
        seccion8.add(formatoTrimestre + "en unidades");
        seccion8.add("2_16.tex");       
        seccion8.add("INE, con datos de la Policía Nacional Civil");
        seccion8.add(true);
        cap2.add(seccion8);
        
              ArrayList seccion9 = new ArrayList();
        seccion9.add("2_17");
        seccion9.add("Huevos de gallina");
        seccion9.add("Costo medio de los huevos de gallina");
        seccion9.add(formatoTrimestre + "en porcentaje");
        seccion9.add("2_17.tex");
        seccion9.add("INE, con datos de la Policía Nacional Civil");
        seccion9.add(true);
        seccion9.add("2_18");
        seccion9.add("Aceite vegetal");
        seccion9.add("Costo medio del aceite corriente de origen vegetal envasado");
        seccion9.add(formatoTrimestre + "en unidades");
        seccion9.add("2_18.tex");       
        seccion9.add("INE, con datos de la Policía Nacional Civil");
        seccion9.add(true);
        cap2.add(seccion9);
        
        
        
              ArrayList seccion10 = new ArrayList();
        seccion10.add("2_19");
        seccion10.add("Aguacate");
        seccion10.add("Costo medio del aguacate");
        seccion10.add(formatoTrimestre + "en porcentaje");
        seccion10.add("2_19.tex");
        seccion10.add("INE, con datos de la Policía Nacional Civil");
        seccion10.add(true);
        seccion10.add("2_20");
        seccion10.add("Banano");
        seccion10.add("Costo medio del banano");
        seccion10.add(formatoTrimestre + "en unidades");
        seccion10.add("2_20.tex");       
        seccion10.add("INE, con datos de la Policía Nacional Civil");
        seccion10.add(true);
        cap2.add(seccion10);
        
                      ArrayList seccion11 = new ArrayList();
        seccion11.add("2_21");
        seccion11.add("Manzana roja");
        seccion11.add("Costo medio de la manzana");
        seccion11.add(formatoTrimestre + "en porcentaje");
        seccion11.add("2_21.tex");
        seccion11.add("INE, con datos de la Policía Nacional Civil");
        seccion11.add(true);
        seccion11.add("2_22");
        seccion11.add("Naranjas");
        seccion11.add("Costo medio de la naranja");
        seccion11.add(formatoTrimestre + "en unidades");
        seccion11.add("2_22.tex");       
        seccion11.add("INE, con datos de la Policía Nacional Civil");
        seccion11.add(true);
        cap2.add(seccion11);
        
        
                      ArrayList seccion12 = new ArrayList();
        seccion12.add("2_23");
        seccion12.add("Plátano");
        seccion12.add("Costo medio del plátano");
        seccion12.add(formatoTrimestre + "en porcentaje");
        seccion12.add("2_23.tex");
        seccion12.add("INE, con datos de la Policía Nacional Civil");
        seccion12.add(true);
        seccion12.add("2_24");
        seccion12.add("Sandia");
        seccion12.add("Costo medio de la sandia");
        seccion12.add(formatoTrimestre + "en unidades");
        seccion12.add("2_24.tex");       
        seccion12.add("INE, con datos de la Policía Nacional Civil");
        seccion12.add(true);
        cap2.add(seccion12);
        
        
                            ArrayList seccion13 = new ArrayList();
        seccion13.add("2_25");
        seccion13.add("Tomate");
        seccion13.add("Costo medio del tomate");
        seccion13.add(formatoTrimestre + "en porcentaje");
        seccion13.add("2_25.tex");
        seccion13.add("INE, con datos de la Policía Nacional Civil");
        seccion13.add(true);
        seccion13.add("2_26");
        seccion13.add("Güisquil");
        seccion13.add("Costo medio del güisquil");
        seccion13.add(formatoTrimestre + "en unidades");
        seccion13.add("2_26.tex");       
        seccion13.add("INE, con datos de la Policía Nacional Civil");
        seccion13.add(true);
        cap2.add(seccion13);
        
                
                            ArrayList seccion14 = new ArrayList();
        seccion14.add("2_27");
        seccion14.add("Frijol negro");
        seccion14.add("Costo medio del frijol negro en grano");
        seccion14.add(formatoTrimestre + "en porcentaje");
        seccion14.add("2_27.tex");
        seccion14.add("INE, con datos de la Policía Nacional Civil");
        seccion14.add(true);
        seccion14.add("2_28");
        seccion14.add("Cebolla");
        seccion14.add("Costo medio de la cebolla blanca sin tallo");
        seccion14.add(formatoTrimestre + "en unidades");
        seccion14.add("2_28.tex");       
        seccion14.add("INE, con datos de la Policía Nacional Civil");
        seccion14.add(true);
        cap2.add(seccion14);
        
        ArrayList seccion15 = new ArrayList();
        seccion15.add("2_29");
        seccion15.add("Papa");
        seccion15.add("Costo medio de la papa");
        seccion15.add(formatoTrimestre + "en porcentaje");
        seccion15.add("2_29.tex");
        seccion15.add("INE, con datos de la Policía Nacional Civil");
        seccion15.add(true);
        seccion15.add("2_30");
        seccion15.add("Zanahoria");
        seccion15.add("Costo medio de la zanahoria");
        seccion15.add(formatoTrimestre + "en unidades");
        seccion15.add("2_30.tex");       
        seccion15.add("INE, con datos de la Policía Nacional Civil");
        seccion15.add(true);
        cap2.add(seccion15);
        
        
        
        ArrayList seccion16 = new ArrayList();
        seccion16.add("2_31");
        seccion16.add("Macuy");
        seccion16.add("Costo medio del macuy");
        seccion16.add(formatoTrimestre + "en porcentaje");
        seccion16.add("2_31.tex");
        seccion16.add("INE, con datos de la Policía Nacional Civil");
        seccion16.add(true);
        seccion16.add("2_32");
        seccion16.add("Azúcar");
        seccion16.add("Costo medio del azúcar blanca granulada");
        seccion16.add(formatoTrimestre + "en unidades");
        seccion16.add("2_32.tex");       
        seccion16.add("INE, con datos de la Policía Nacional Civil");
        seccion16.add(true);
        cap2.add(seccion16);
        
        
        
        ArrayList seccion17 = new ArrayList();
        seccion17.add("2_33");
        seccion17.add("Sal");
        seccion17.add("Costo medio de la sal entera");
        seccion17.add(formatoTrimestre + "en porcentaje");
        seccion17.add("2_33.tex");
        seccion17.add("INE, con datos de la Policía Nacional Civil");
        seccion17.add(true);
        seccion17.add("2_34");
        seccion17.add("Sopa en sobre");
        seccion17.add("Costo medio de la sopa concentrada en sobre");
        seccion17.add(formatoTrimestre + "en unidades");
        seccion17.add("2_34.tex");       
        seccion17.add("INE, con datos de la Policía Nacional Civil");
        seccion17.add(true);
        cap2.add(seccion17);
        
        
        ArrayList seccion18 = new ArrayList();
        seccion18.add("2_35");
        seccion18.add("Café");
        seccion18.add("Costo medio del café molido");
        seccion18.add(formatoTrimestre + "en porcentaje");
        seccion18.add("2_35.tex");
        seccion18.add("INE, con datos de la Policía Nacional Civil");
        seccion18.add(true);
        seccion18.add("2_36");
        seccion18.add("Aguas gaseosas");
        seccion18.add("Costo medio aguas gaseosas");
        seccion18.add(formatoTrimestre + "en unidades");
        seccion18.add("2_36.tex");       
        seccion18.add("INE, con datos de la Policía Nacional Civil");
        seccion18.add(true);
        cap2.add(seccion18);
        
        
        return cap2;        
    }
 
    public void generarCSVEntradaSimple(int indicador){
        
        String nombreArchivo = ""+indicador;
        if (indicador<10) nombreArchivo="0"+nombreArchivo;
        File f = new File("/home/hugo/CB/CSV/","2_"+nombreArchivo + ".csv");
        
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(f.getAbsolutePath());
            pw = new PrintWriter(fichero);
 
            System.out.println(mesesCortos);
            System.out.println(((String[])mes1.get(1))[0]);
            pw.println("x;y");
                pw.println(mesesCortos.get(12)+";" +((String[])mes1.get(indicador-1))[0]);
                pw.println(mesesCortos.get(11)+";" +((String[])mes2.get(indicador-1))[0]);
                pw.println(mesesCortos.get(10)+";" +((String[])mes3.get(indicador-1))[0]);
                pw.println(mesesCortos.get(9)+";" +((String[])mes4.get(indicador-1))[0]);
                pw.println(mesesCortos.get(8)+";" +((String[])mes5.get(indicador-1))[0]);
                pw.println(mesesCortos.get(7)+";" +((String[])mes6.get(indicador-1))[0]);
                pw.println(mesesCortos.get(6)+";" +((String[])mes7.get(indicador-1))[0]);
                pw.println(mesesCortos.get(5)+";" +((String[])mes8.get(indicador-1))[0]);
                pw.println(mesesCortos.get(4)+";" +((String[])mes9.get(indicador-1))[0]);
                pw.println(mesesCortos.get(3)+";" +((String[])mes10.get(indicador-1))[0]);
                pw.println(mesesCortos.get(2)+";" +((String[])mes11.get(indicador-1))[0]);
                pw.println(mesesCortos.get(1)+";" +((String[])mes12.get(indicador-1))[0]);
                pw.println(mesesCortos.get(0)+";" +((String[])mes13.get(indicador-1))[0]);
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
        
    }
    
    public void generarCSVEntradas(){
        for (int i = 1; i<=36; i++){
            generarCSVEntradaSimple(i);
        }

    }
    
    private void leerDatos(String mes, String year) {
        int mesNumerico;
        mesNumerico = convertirMesANumero(mes);
        CSVReader lectorCSV;
        meses = calcularMeses(mesNumerico, year);
        
        try {
            int contador = 0;
            for( String item : meses){
                System.out.println(item);
                lectorCSV = new CSVReader(new FileReader("/home/hugo/CB/"+ item + ".csv"));
                switch ( contador ){
                        case 0:
                            mes1 = lectorCSV.readAll();
                            contador++;
                            break;
                        
                        case 1:
                            mes2 = lectorCSV.readAll();
                            contador++;
                            break;
                        
                        case 2:
                            mes3 = lectorCSV.readAll();
                            contador++;
                            break;
                
                        case 3:
                        mes4 = lectorCSV.readAll();
                        contador++;
                        break;

                        case 4:
                        mes5 = lectorCSV.readAll();
                        contador++;
                        break;
                        
                        case 5:
                        mes6 = lectorCSV.readAll();
                        contador++;
                        break;
                         
                        case 6:
                        mes7 = lectorCSV.readAll();
                        contador++;
                        break;
                        
                        case 7:
                        mes8 = lectorCSV.readAll();
                        contador++;
                        break;

                        case 8:
                        mes9 = lectorCSV.readAll();
                        contador++;
                        break;

                        case 9:
                        mes10 = lectorCSV.readAll();
                        contador++;
                        break;

                        case 10:
                        mes11 = lectorCSV.readAll();
                        contador++;
                        break;

                        case 11:
                        mes12 = lectorCSV.readAll();
                        contador++;
                        break;
                            
                        case 12:
                        mes13 = lectorCSV.readAll();
                        contador++;
                        break;
                            
                }
                    
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Canasta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Canasta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        mesNumerico = mesNumerico -1;        
        
    }

    private int convertirMesANumero(String mes) {
        int retorno = -1;
        if( mes.equalsIgnoreCase("enero")){
            retorno = 0;
        }else if( mes.equalsIgnoreCase("febrero") ){
            retorno = 1;
        }else if( mes.equalsIgnoreCase("marzo") ){
            retorno = 2;
        }else if( mes.equalsIgnoreCase("abril") ){
            retorno = 3;
        }else if( mes.equalsIgnoreCase("mayo") ){
            retorno = 4;
        }else if( mes.equalsIgnoreCase("junio") ){
            retorno = 5;
        }else if( mes.equalsIgnoreCase("julio") ){
            retorno = 6;
        }else if( mes.equalsIgnoreCase("agosto") ){
            retorno = 7;
        }else if( mes.equalsIgnoreCase("septiembre") ){
            retorno = 8;
        }else if( mes.equalsIgnoreCase("octubre") ){
            retorno = 9;
        }else if( mes.equalsIgnoreCase("noviembre") ){
            retorno = 10;
        }else if( mes.equalsIgnoreCase("diciembre") ){
            retorno = 11;
        }
        
        
        return retorno;
    }

    private String convertirNumeroAMesCorto(int mesNumerico) {
        String retorno = "Mes incorrecto";
        
        if( mod(mesNumerico,12) ==0 ){
            retorno = "Ene";
        }else if( mod(mesNumerico,12) == 1 ){
            retorno = "Feb";
        }else if( mod(mesNumerico,12) == 2 ){
            retorno = "Mar";
        }else if( mod(mesNumerico,12) == 3 ){
            retorno = "Abr";
        }else if( mod(mesNumerico,12) == 4 ){
            retorno = "May";
        }else if( mod(mesNumerico,12) == 5 ){
            retorno = "Jun";
        }else if( mod(mesNumerico,12) == 6 ){
            retorno = "Jul";
        }else if( mod(mesNumerico,12) == 7 ){
            retorno = "Ago";
        }else if( mod(mesNumerico,12) == 8 ){
            retorno = "Sep";
        }else if( mod(mesNumerico,12) == 9 ){
            retorno = "Oct";
        }else if( mod(mesNumerico,12) == 10 ){
            retorno = "Nov";
        }else if( mod(mesNumerico,12) == 11 ){
            retorno = "Dic";
        }
        
        return retorno;
    }
    
    private String convertirNumeroAMes(int mesNumerico) {
        String retorno = "Mes incorrecto";
        
        if( mod(mesNumerico,12) ==0 ){
            retorno = "enero";
        }else if( mod(mesNumerico,12) == 1 ){
            retorno = "febrero";
        }else if( mod(mesNumerico,12) == 2 ){
            retorno = "marzo";
        }else if( mod(mesNumerico,12) == 3 ){
            retorno = "abril";
        }else if( mod(mesNumerico,12) == 4 ){
            retorno = "mayo";
        }else if( mod(mesNumerico,12) == 5 ){
            retorno = "junio";
        }else if( mod(mesNumerico,12) == 6 ){
            retorno = "julio";
        }else if( mod(mesNumerico,12) == 7 ){
            retorno = "agosto";
        }else if( mod(mesNumerico,12) == 8 ){
            retorno = "septiembre";
        }else if( mod(mesNumerico,12) == 9 ){
            retorno = "octubre";
        }else if( mod(mesNumerico,12) == 10 ){
            retorno = "noviembre";
        }else if( mod(mesNumerico,12) == 11 ){
            retorno = "diciembre";
        }
        
        return retorno;
    }


    public List<String> calcularMesesCortos(int mesNumerico,String year){
         List<String> retorno = new ArrayList<String>();
        int mesInicial  =  mesNumerico;
        do{
             if( mesNumerico == -1 ){
                year = String.valueOf( Integer.parseInt(year) - 1 );
            }
            retorno.add(convertirNumeroAMesCorto(mesNumerico) + "-" + year);
            mesNumerico--;
            System.out.println("Mes inicial  = " + mesInicial + " Mes numérico  = " + mesNumerico);
        }while( mod(mesNumerico, 12) != mesInicial );
        if( mesNumerico == -1 ){
                year = String.valueOf( Integer.parseInt(year) - 1 );
            }
        retorno.add(convertirNumeroAMesCorto(mesInicial) + "-" + year);
        return retorno;
    }
    
    
    public List<String> calcularMeses(int mesNumerico, String year) {
           List<String> retorno = new ArrayList<String>();
        int mesInicial  =  mesNumerico;
        do{
             if( mesNumerico == -1 ){
                year = String.valueOf( Integer.parseInt(year) - 1 );
            }
            retorno.add(convertirNumeroAMes(mesNumerico) + "-" + year);
            mesNumerico--;
            System.out.println("Mes inicial  = " + mesInicial + " Mes numérico  = " + mesNumerico);
        }while( mod(mesNumerico, 12) != mesInicial );
        if( mesNumerico == -1 ){
                year = String.valueOf( Integer.parseInt(year) - 1 );
            }
        retorno.add(convertirNumeroAMes(mesInicial) + "-" + year);
        return retorno;
    }
    
    


    private static int mod(int x, int y)
    {
    int result = x % y;
    if (result < 0)
    {
        result += y;
    }
    return result;
    }

    void hacerPortada() {
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
            getMes() +  " "  + getAnioPublicacion() + " };" +
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

    void equipoYPresentacion() {
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
                + getMes() + " trimestre del "
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
                + getMes() + " trimestre del "
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

    void rellenar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void apendices(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void generarGraficas(String modalidad) {
        System.out.println("GENERANDO LAS GRAFICAS");
        Grafica vitales = new Grafica("canasta", getRuta(), rr.get(), modalidad);
        vitales.start();
    }
    
    
    
    
}
