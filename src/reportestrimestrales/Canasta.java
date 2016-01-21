/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportestrimestrales;
import au.com.bytecode.opencsv.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author hugo
 */
public class Canasta extends Documento{
    List<String[]>mes1;
    List<String[]>mes2;
    List<String[]>mes3;
    List<String[]>mes4;
    List<String[]>mes5;
    List<String[]>mes6;
    List<String[]>mes7;
    List<String[]>mes8;
    List<String[]>mes9;
    List<String[]>mes10;
    List<String[]>mes11;
    List<String[]>mes12;
    List<String>meses;
    
    public Canasta(String titulo, String mes, String pYear){
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
        meses = new ArrayList<String>();
        leerDatos(mes, pYear);
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

                        case 6:
                        mes7 = lectorCSV.readAll();
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
                            
                }
                for(String[] data :  mes1){
                    for(String dato: data){
                        System.out.println(dato);
                    }
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
    
    
    
    
}
