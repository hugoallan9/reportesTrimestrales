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
public class TransporteYServicios extends Documento{
    private List capitulos;
    private List introCapitulos;
    private List contenidos;
    private String formatoSerie;
    Collator comparador = Collator.getInstance();
    private SesionR rr;
    private String rutaCSV;    
    public SesionR getRr() {
        return rr;
    }
    
    
    
    
    public TransporteYServicios(String titulo, String trimestre, String pYear, String rutaCSV) {
        super(titulo, trimestre, pYear);
        capitulos = new ArrayList();
        introCapitulos = new ArrayList();
        contenidos = new ArrayList();
        this.rutaCSV = rutaCSV;
        rr = new SesionR();
        comparador.setStrength(Collator.PRIMARY);
        formatoSerie = "Serie histórica " +  (int)(Double.parseDouble(getAnioPublicacion()) -2) + "-" + getAnioPublicacion();
                
                
        cargarCSV(rutaCSV);
        setCapitulos();
        setIntroCapitulos();
        setContenidos();
    }
    
    protected void setCapitulos(){
        capitulos.add("Tráfico aéreo");
        capitulos.add("Tráfico postal");
        capitulos.add("Movimiento marítimo");
        capitulos.add("Parque vehicular");
        
    }
    
    protected void setIntroCapitulos(){
        introCapitulos.add("Es el servicio de trasladar de un lugar a otro a los "
                + "pasajeros o cargamento, mediante la utilización de aeronaves.");
        introCapitulos.add("Es el servicio de mensajería de correspondencia, "
                + "paquetes y otros a través de avión.");
        introCapitulos.add("Es el servicio de llevar carga por mar de un punto "
                + "geográfico a otro a través de buques, siendo el modo más "
                + "utilizado para el comercio internacional y el que soporta "
                + "mayor movimiento de mercancías, tanto en contenedores como "
                + "graneles secos o líquidos.");
        introCapitulos.add("Es la cantidad de vehículos registrados en la "
                + "ciudad de Guatemala a través de la Superintendencia de "
                + "Administración Tributaria.");
    }
    protected void setContenidos(){
         contenidos.add(cargarCapitulo1());
         contenidos.add(cargarCapitulo2());
         contenidos.add(cargarCapitulo3());
         contenidos.add(cargarCapitulo4());
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
                        tmp.get(4).toString(),tmp.get(5).toString(),"",(Boolean)tmp.get(6));
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
                REXP listadoCSV = rr.get().eval("transporte <- cargaMasiva('" +  ruta +"')");
                REXP nombres = rr.get().eval("names(transporte)");
                System.out.println(listadoCSV);
                System.out.println(nombres);
            }
    }
    
    protected ArrayList cargarCapitulo1(){
        ArrayList cap1 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("1_01");
        seccion1.add("Exportación aérea");
        seccion1.add("Volumen de carga exportada a través del "
                + "aereopuerto internacional La Aurora en miles de Kilogramos.");
        seccion1.add(formatoSerie);
        seccion1.add("1_01.tex");
        seccion1.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion1.add(true);
        seccion1.add("1_02");
        seccion1.add("Importación aérea");
        seccion1.add("Volumen de carga importada a través del "
                + "aereopuerto internacional La Aurora en miles de Kilogramos");
        seccion1.add(formatoSerie);
        seccion1.add("1_02.tex");
        seccion1.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion1.add(true);
        cap1.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("1_03");
        seccion2.add("Alimentos exportados vía aérea");
        seccion2.add("Carga de alimentos exportados a través del "
                + "aereopuerto internacional La Aurora en Kilogramos");
        seccion2.add(formatoSerie);
        seccion2.add("1_03.tex");
        seccion2.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion2.add(true);
        seccion2.add("1_04");
        seccion2.add("Artículos exportados vía aérea");
        seccion2.add("Carga de artículos exportados a través del "
                + "aereopuerto internacional La Aurora en Kilogramos");
        seccion2.add(formatoSerie);
        seccion2.add("1_04.tex");
        seccion2.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion2.add(true);
        cap1.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("1_05");
        seccion3.add("Plantas exportadoras vía aérea");
        seccion3.add("Carga de plantas exportadas a través del "
                + "aereopuerto internacional La Aurora en miles de Kilogramos");
        seccion3.add(formatoSerie);
        seccion3.add("1_05.tex");
        seccion3.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion3.add(true);
        seccion3.add("1_06");
        seccion3.add("Animales exportados vía aérea");
        seccion3.add("Carga de animales exportados a través del "
                + "aeropuerto internacional La Aurora en Kilogramos");
        seccion3.add(formatoSerie);
        seccion3.add("1_06.tex");
        seccion3.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion3.add(true);
        cap1.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("1_07");
        seccion4.add("Aeropuerto La Aurora: vuelos nacionales");
        seccion4.add("Despegues de vuelos nacionales en aeropuerto internacional"
                + "La Aurora");
        seccion4.add(formatoSerie);
        seccion4.add("1_07.tex");
        seccion4.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion4.add(true);
        seccion4.add("1_08");
        seccion4.add("Aeropuerto La Aurora: vuelos internacionales");
        seccion4.add("Despegues de vuelos internacionales en aeropuerto "
                + "internacional La Aurora");
        seccion4.add(formatoSerie);
        seccion4.add("1_08.tex");
        seccion4.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion4.add(true);
        cap1.add(seccion4);
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("1_09");
        seccion5.add("Aeropuerto La Aurora: pasajeros que arriban");
        seccion5.add("Pasajeros que arriban en el aeropuerto internacional La Aurora");
        seccion5.add(formatoSerie);
        seccion5.add("1_09.tex");
        seccion5.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion5.add(true);
        seccion5.add("1_10");
        seccion5.add("Aeropuerto La Aurora: pasajeros que salen");
        seccion5.add("Pasajeros que salen del aeropuerto internacional La Aurora");
        seccion5.add(formatoSerie);
        seccion5.add("1_10.tex");
        seccion5.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion5.add(true);
        cap1.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("1_11");
        seccion6.add("Aeropuerto Mundo Maya: pasajeros que arriban");
        seccion6.add("Pasajeros que arriban en el aeropuerto internacional Mundo Maya");
        seccion6.add(formatoSerie);
        seccion6.add("1_11.tex");
        seccion6.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion6.add(true);
        seccion6.add("1_12");
        seccion6.add("Aeropuerto Mundo Maya: pasajeros que salen");
        seccion6.add("Pasajeros que salen del aeropuerto internacional Mundo Maya");
        seccion6.add(formatoSerie);
        seccion6.add("1_12.tex");
        seccion6.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion6.add(true);
        cap1.add(seccion6);
        
        return cap1;        
        
    }
    protected ArrayList cargarCapitulo2(){
        ArrayList cap2 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("2_01");
        seccion1.add("Envío de correspondencia vía aérea");
        seccion1.add("Envío de correspondencia vía aérea");
        seccion1.add(formatoSerie);
        seccion1.add("2_01.tex");
        seccion1.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion1.add(true);
        seccion1.add("2_02");
        seccion1.add("Peso de la correspondencia vía aérea");
        seccion1.add("Peso de la correspondencia enviada por vía aérea en Kilogramos");
        seccion1.add(formatoSerie);
        seccion1.add("2_02.tex");
        seccion1.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion1.add(true);
        cap2.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("2_03");
        seccion2.add("Envío de correspondencia por continente");
        seccion2.add("Envío de correspondencia vía aérea");
        seccion2.add("Distribución porcentual según continente");
        seccion2.add("2_03.tex");
        seccion2.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion2.add(true);
        seccion2.add("2_04");
        seccion2.add("Principales países de destino de correspondencia");
        seccion2.add("Principales países de destino de la correspondencia enviada desde Guatemala");
        seccion2.add("Número de envíos");
        seccion2.add("2_04.tex");
        seccion2.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion2.add(true);
        cap2.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("2_05");
        seccion3.add("Correspondencia a Centroamérica y Panamá");
        seccion3.add("Envío de correspondencia vía aérea a América Central y Panamá");
        seccion3.add(formatoSerie);
        seccion3.add("2_05.tex");
        seccion3.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion3.add(true);
        seccion3.add("2_06");
        seccion3.add("Peso de la correspondencia enviada a Centroamérica y Panamá");
        seccion3.add("Peso de la correspondencia enviada por vía aérea a "
                + "América Central y Panamá en Kilogramos");
        seccion3.add(formatoSerie);
        seccion3.add("2_06.tex");
        seccion3.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion3.add(true);
        cap2.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("2_07");
        seccion4.add("Correspondencia a Norteamérica");
        seccion4.add("Envío de correspondencia vía aérea a América del Norte");
        seccion4.add(formatoSerie);
        seccion4.add("2_07.tex");
        seccion4.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion4.add(true);
        seccion4.add("2_08");
        seccion4.add("Peso de la correspondencia enviada a Norteamérica");
        seccion4.add("Peso de la correspondencia enviada por vía aérea a Norteamérica en Kilogramos");
        seccion4.add(formatoSerie);
        seccion4.add("2_08.tex");
        seccion4.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion4.add(true);
        cap2.add(seccion4);
        
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("2_09");
        seccion5.add("Correspondencia a Sudamérica");
        seccion5.add("Envío de correspondencia vía aérea a América del Sur");
        seccion5.add(formatoSerie);
        seccion5.add("2_09.tex");
        seccion5.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion5.add(true);
        seccion5.add("2_10");
        seccion5.add("Peso de la correspondencia enviada a Sudamérica");
        seccion5.add("Peso de la correspondencia enviada por vía aérea a "
                + "América del Sur en Kilogramos");
        seccion5.add(formatoSerie);
        seccion5.add("2_10.tex");
        seccion5.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion5.add(true);
        cap2.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("2_11");
        seccion6.add("Correspondencia a Europa");
        seccion6.add("Envío de correspondencia vía aérea a Europa");
        seccion6.add(formatoSerie);
        seccion6.add("2_11.tex");
        seccion6.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion6.add(true);
        seccion6.add("2_12");
        seccion6.add("Peso de la correspondencia enviada a Europa");
        seccion6.add("Peso de la correspondencia enviada por vía aérea a Europa en Kilogramos");
        seccion6.add(formatoSerie);
        seccion6.add("2_12.tex");
        seccion6.add("INE, con datos del BANGUAT");
        seccion6.add(true);
        cap2.add(seccion6);
        
        
        ArrayList seccion7 = new ArrayList();
        seccion7.add("2_13");
        seccion7.add("Correspondencia a Asia, África y Oceanía");
        seccion7.add("Envío de correspondencia vía aérea a Asia, África y Oceanía");
        seccion7.add(formatoSerie);
        seccion7.add("2_13.tex");
        seccion7.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion7.add(true);
        seccion7.add("2_14");
        seccion7.add("Peso de la correspondencia enviada a Asia, África y Oceanía");
        seccion7.add("Peso de la correspondencia enviada por vía aérea a Asia, África "
                + "y Oceanía en Kilogramos");
        seccion7.add(formatoSerie);
        seccion7.add("2_14.tex");
        seccion7.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion7.add(true);
        cap2.add(seccion7);
        
        ArrayList seccion8 = new ArrayList();
        seccion8.add("2_15");
        seccion8.add("Envío de encomiendas vía aérea");
        seccion8.add("Cantidad de encomiendas enviadas por vía aérea");
        seccion8.add(formatoSerie);
        seccion8.add("2_15.tex");
        seccion8.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion8.add(true);
        seccion8.add("2_16");
        seccion8.add("Peso de las encomiendas vía aérea");
        seccion8.add("Peso total de las encomiendas enviadas por vía aérea en Kilogramos");
        seccion8.add(formatoSerie);
        seccion8.add("2_16.tex");
        seccion8.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion8.add(true);
        cap2.add(seccion8);
        
        
        
        
        ArrayList seccion9 = new ArrayList();
        seccion9.add("2_17");
        seccion9.add("Encomiendas a Centroamérica y Panamá");
        seccion9.add("Cantidad de encomiendas enviadas por vía aérea a "
                + "América Central y Panamá");
        seccion9.add(formatoSerie);
        seccion9.add("2_17.tex");
        seccion9.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion9.add(true);
        seccion9.add("2_18");
        seccion9.add("Peso de las encomiendas a Centroamérica y Panamá");
        seccion9.add("Peso total de las encomiendas enviadas por vía aérea "
                + "a América Central y Panamá en Kilogramos");
        seccion9.add(formatoSerie);
        seccion9.add("2_18.tex");
        seccion9.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion9.add(true);
        cap2.add(seccion9);
        
        
        
        
        ArrayList seccion10 = new ArrayList();
        seccion10.add("2_19");
        seccion10.add("Encomiendas a Norteamérica");
        seccion10.add("Cantidad de encomiendas enviadas por vía aérea a "
                + "América del Norte");
        seccion10.add(formatoSerie);
        seccion10.add("2_19.tex");
        seccion10.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion10.add(true);
        seccion10.add("2_20");
        seccion10.add("Peso de las encomiendas a Norteamérica");
        seccion10.add("Peso total de las encomiendas enviadas por vía aérea "
                + "a América del Norte en Kilogramos");
        seccion10.add(formatoSerie);
        seccion10.add("2_20.tex");
        seccion10.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion10.add(true);
        cap2.add(seccion10);
        
        ArrayList seccion11 = new ArrayList();
        seccion11.add("2_21");
        seccion11.add("Encomiendas a Sudamérica");
        seccion11.add("Cantidad de encomiendas enviadas por vía aérea a "
                + "América del Sur");
        seccion11.add(formatoSerie);
        seccion11.add("2_21.tex");
        seccion11.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion11.add(true);
        seccion11.add("2_22");
        seccion11.add("Peso de las encomiendas a Sudamérica");
        seccion11.add("Peso total de las encomiendas enviadas por vía aérea "
                + "a América del Sur en Kilogramos");
        seccion11.add(formatoSerie);
        seccion11.add("2_22.tex");
        seccion11.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion11.add(true);
        cap2.add(seccion11);
        
        
        
        ArrayList seccion12 = new ArrayList();
        seccion12.add("2_23");
        seccion12.add("Encomiendas a Europa");
        seccion12.add("Cantidad de encomiendas enviadas por vía aérea a "
                + "Europa");
        seccion12.add(formatoSerie);
        seccion12.add("2_23.tex");
        seccion12.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion12.add(true);
        seccion12.add("2_24");
        seccion12.add("Peso de las encomiendas a Europa");
        seccion12.add("Peso total de las encomiendas enviadas por vía aérea "
                + "a Europa en Kilogramos");
        seccion12.add(formatoSerie);
        seccion12.add("2_24.tex");
        seccion12.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion12.add(true);
        cap2.add(seccion12);
        
        
        
        ArrayList seccion13 = new ArrayList();
        seccion13.add("2_25");
        seccion13.add("Encomiendas a Asia, África y Oceanía");
        seccion13.add("Cantidad de encomiendas enviadas por vía aérea a "
                + "Asia, África y Oceanía");
        seccion13.add(formatoSerie);
        seccion13.add("2_25.tex");
        seccion13.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion13.add(true);
        seccion13.add("2_26");
        seccion13.add("Peso de las encomiendas a Asia, África y Oceanía");
        seccion13.add("Peso total de las encomiendas enviadas por vía aérea "
                + "a Asia, África y Oceanía en Kilogramos");
        seccion13.add(formatoSerie);
        seccion13.add("2_26.tex");
        seccion13.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion13.add(true);
        cap2.add(seccion13);
        
        ArrayList seccion14 = new ArrayList();
        seccion14.add("2_27");
        seccion14.add("Principales destinos de las encomiendas");
        seccion14.add("Principales países de destino de las encomiendas");
        seccion14.add(getFormatoTrimestre());
        seccion14.add("2_27.tex");
        seccion14.add("INE, con datos de Dirección General de Correos y Telégrafos");
        
        
        
        return cap2;        
    }
    
    
    
    protected ArrayList cargarCapitulo3(){
        ArrayList cap3 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("3_01");
        seccion1.add("Buques");
        seccion1.add("Buques que arribaron a Guatemala");
        seccion1.add(formatoSerie);
        seccion1.add("3_01.tex");
        seccion1.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion1.add(true);
        seccion1.add("3_02");
        seccion1.add("Carga total exportada");
        seccion1.add("Exportaciones en el sistema portuario nacional en miles de toneladas métricas");
        seccion1.add(formatoSerie);
        seccion1.add("3_02.tex");
        seccion1.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion1.add(true);
        cap3.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("3_03");
        seccion2.add("Carga total importada");
        seccion2.add("Importaciones en el sistema portuario nacional en miles de toneladas métricas");
        seccion2.add(formatoSerie);
        seccion2.add("3_03.tex");
        seccion2.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion2.add(true);
        seccion2.add("3_04");
        seccion2.add("Tipo de carga: general");
        seccion2.add("Exportaciones e importaciones en el sistema portuario nacional, "
                + "de carga tipo general en miles de toneladas métricas");
        seccion2.add(formatoSerie);
        seccion2.add("3_04.tex");
        seccion2.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion2.add(true);
        cap3.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("3_05");
        seccion3.add("Tipo de carga: contenerizada");
        seccion3.add("Exportaciones e importaciones en el sistema portuario nacional, "
                + "carga en contendores en miles de toneladas métricas");
        seccion3.add(formatoSerie);
        seccion3.add("3_05.tex");
        seccion3.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion3.add(true);
        seccion3.add("3_06");
        seccion3.add("Tipo de carga: líquida a granel");
        seccion3.add("Exportacones e importaciones en el sistema portuario nacional, "
                + "carga líquida a granel en miles de toneladas métricas");
        seccion3.add(formatoSerie);
        seccion3.add("3_06.tex");
        seccion3.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion3.add(true);
        cap3.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("3_07");
        seccion4.add("Tipo de carga: sólida a granel");
        seccion4.add("Exportaciones e importaciones en el sistema portuario nacional, "
                + "carga sólida a granel en miles de toneladas métricas");
        seccion4.add(formatoSerie);
        seccion4.add("3_07.tex");
        seccion4.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion4.add(true);
        seccion4.add("3_08");
        seccion4.add("Relación de las exportaciones respecto al total de carga según el tipo");
        seccion4.add("Porcentaje de exportaciones en relación al total de carga manejada "
                + "en el sistema portuario nacional, según el tipo de carga");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("3_08.tex");
        seccion4.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion4.add(true);
        cap3.add(seccion4);
        
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("3_09");
        seccion5.add("Puerto Santo Tomás de Castilla: buques");
        seccion5.add("Buques que arribanron al puerto Santo Tomás de Castilla");
        seccion5.add(formatoSerie);
        seccion5.add("3_09.tex");
        seccion5.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion5.add(true);
        seccion5.add("3_10");
        seccion5.add("Puerto Santo Tomás de Castilla: carga exportada");
        seccion5.add("Exportaciones en el puerto Santo Tomás de Castilla en miles "
                + "de toneladas métricas");
        seccion5.add(formatoSerie);
        seccion5.add("3_10.tex");
        seccion5.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion5.add(true);
        cap3.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("3_11");
        seccion6.add("Puerto Santo Tomás de Castilla: carga importada");
        seccion6.add("Importaciones a través del puerto Santo Tomás de Castilla en miles de "
                + "toneladas métricas");
        seccion6.add(formatoSerie);
        seccion6.add("3_11.tex");
        seccion6.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion6.add(true);
        seccion6.add("3_12");
        seccion6.add("Puerto Barrios: Buques");
        seccion6.add("Buques que arribaron a Puerto Barrios");
        seccion6.add(formatoSerie);
        seccion6.add("3_12.tex");
        seccion6.add("INE, con datos del BANGUAT");
        seccion6.add(true);
        cap3.add(seccion6);
        
        
        ArrayList seccion7 = new ArrayList();
        seccion7.add("3_13");
        seccion7.add("Puerto Barrios: carga exportada");
        seccion7.add("Exportaciones en Puerto Barrios en miles de toneladas métricas");
        seccion7.add(formatoSerie);
        seccion7.add("3_13.tex");
        seccion7.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion7.add(true);
        seccion7.add("3_14");
        seccion7.add("Puerto Barrios: carga importada");
        seccion7.add("Importaciones a través de Puerto Barrios en miles de toneladas métricas");
        seccion7.add(formatoSerie);
        seccion7.add("3_14.tex");
        seccion7.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion7.add(true);
        cap3.add(seccion7);
        
        ArrayList seccion8 = new ArrayList();
        seccion8.add("3_15");
        seccion8.add("Puerto San José: buques");
        seccion8.add("Buques que arribaron al puerto San José");
        seccion8.add(formatoSerie);
        seccion8.add("3_15.tex");
        seccion8.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion8.add(true);
        seccion8.add("3_16");
        seccion8.add("Puerto San José: carga exportada");
        seccion8.add("Exportaciones en el puerto San José en miles de toneladas métricas");
        seccion8.add(formatoSerie);
        seccion8.add("3_16.tex");
        seccion8.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion8.add(true);
        cap3.add(seccion8);
        
        
        
        
        ArrayList seccion9 = new ArrayList();
        seccion9.add("3_17");
        seccion9.add("Puerto San José: carga importada");
        seccion9.add("Importaciones a través de puerto San José en miles de toneladas métricas");
        seccion9.add(formatoSerie);
        seccion9.add("3_17.tex");
        seccion9.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion9.add(true);
        seccion9.add("3_18");
        seccion9.add("Puerto Quetzal: buques");
        seccion9.add("Buques que arribaron al Puerto Quetzal");
        seccion9.add(formatoSerie);
        seccion9.add("3_18.tex");
        seccion9.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion9.add(true);
        cap3.add(seccion9);
        
        
        
        
        ArrayList seccion10 = new ArrayList();
        seccion10.add("3_19");
        seccion10.add("Puerto Quetzal: carga exportada");
        seccion10.add("Exportacoines en el Puerto Quetzal en miles de toneladas métricas");
        seccion10.add(formatoSerie);
        seccion10.add("3_19.tex");
        seccion10.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion10.add(true);
        seccion10.add("3_20");
        seccion10.add("Puerto Quetzal: carga importada");
        seccion10.add("Importaciones a través de Puerto Quetzal en miles de tonelas métricas");
        seccion10.add(formatoSerie);
        seccion10.add("3_20.tex");
        seccion10.add("INE, con datos proporcionados por la Comisión Portuaria Nacional");
        seccion10.add(true);
        cap3.add(seccion10);
        
        
        return cap3;        
    }
    
    
    protected ArrayList cargarCapitulo4(){
        ArrayList cap4 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("4_01");
        seccion1.add("Parque vehicular");
        seccion1.add("Parque vehicular");
        seccion1.add(formatoSerie);
        seccion1.add("4_01.tex");
        seccion1.add("INE, con datos proporcionados por la Superintendencia de Administración Tributaria");
        seccion1.add(true);
        seccion1.add("4_02");
        seccion1.add("Parque vehicular por modelo");
        seccion1.add("Distribución porcentual del parque vehicular por modelo");
        seccion1.add(getFormatoTrimestre());
        seccion1.add("4_02.tex");
        seccion1.add("INE, con datos proporcionados por la Superintendencia de Administración Tributaria");
        seccion1.add(true);
        cap4.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("4_03");
        seccion2.add("Parque vehicular por departamento");
        seccion2.add("Distribución porcuentual del parque vehicular por departamento");
        seccion2.add(getFormatoTrimestre());
        seccion2.add("4_03.tex");
        seccion2.add("INE, con datos proporcionados por la Superintendencia de Administración Tributaria");
        seccion2.add(true);
        seccion2.add("4_04");
        seccion2.add("Parque vehicular por tipo");
        seccion2.add("Distribución porcentual del parque vehicular según tipo de vehículo");
        seccion2.add(getFormatoTrimestre());
        seccion2.add("4_04.tex");
        seccion2.add("INE, con datos proporcionados por la Superintendencia de Administración Tributaria");
        seccion2.add(true);
        cap4.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("4_05");
        seccion3.add("Parque vehicular por tipo de combustible");
        seccion3.add("Porcentaje de parque vehicular por tipo de combustible");
        seccion3.add(getFormatoTrimestre());
        seccion3.add("4_05.tex");
        seccion3.add("INE, con datos proporcionados por la Superintendencia de Administración Tributaria");
        seccion3.add(true);
        seccion3.add("4_06");
        seccion3.add("Importación de vehículos nuevos");
        seccion3.add("Importación de vehículos nuevos");
        seccion3.add(formatoSerie);
        seccion3.add("4_06.tex");
        seccion3.add("INE, con datos proporcionados por la Superintendencia de Administración Tributaria");
        seccion3.add(true);
        cap4.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("4_07");
        seccion4.add("Importacion de vehículos usados");
        seccion4.add("Importación de vehículos usados");
        seccion4.add(formatoSerie);
        seccion4.add("4_07.tex");
        seccion4.add("INE, con datos proporcionados por la Superintendencia de Administración Tributaria");
        seccion4.add(true);
        cap4.add(seccion4);
        return cap4;        
    }        
    
    protected void generarGraficas(String modalidad){
        System.out.println("GENERANDO LAS GRAFICAS");
        Grafica vitales = new Grafica("transporte", getRuta(), rr.get(), modalidad);
        vitales.start();
    }
    
    protected void hacerPortada(){
       File source = new File("/home/ineservidor/Transportes/Caratula");
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
            "\\node[inner sep =0, rotate = 90]at(0.908,0.15){Guatemala, "+ getMesServidor()  +" de " +  getYearServidor()+"};\n "
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
                "	Marlon Humberto Pirir Garcia\\\\[0.8cm]\n" +
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
                "\\indent El Instituto Nacional de Estadística -INE-, a través de la Unidad de Transportes "
                + "y Servicios, elaboró la edición del {\\Bold " + corregirTrimestre(getTrimestre()).toLowerCase() + " trimestre del "
                + getAnioPublicacion() +"} sobre {\\Bold Estadísticas de Transportes y Servicios}, que contiene "
                + "los capítulos de transporte aéreo, tráfico postal, movimiento marítimo "
                + "y paque vehícular. \n" +
                "\n" +
                "El documento contiene la información que proporciona la Dirección de Aeronáutica "
                + "Civil, Dirección General de Correos y Telégrafos, Comisión Portuaria Nacional y "
                + "Superintendecia de Administración Tributaria. {\\Bold Los datos presentados son "
                + "preliminares} y serán ajustados con el ingreso de los registros tardíos.\n" +
                "\n" +
                "El INE agradece el apoyo brindado por las fuentes de información, esperando "
                + "que esta nueva edición sea de utilidad tanto a investigadores y público en "
                + "general como para la elaboración de programas, políticas, planes en materia "
                + "de servicios y transportes para el país.\n" +
                "\n" +
                "\\thispagestyle{empty}\n" +
                "\n" +
                "\n" +
                "\\cleardoublepage");
    }
    
      @Override
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

