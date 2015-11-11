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
                REXP listadoCSV = rr.get().eval("vitales <- cargaMasiva('" +  ruta +"')");
                REXP nombres = rr.get().eval("names(vitales)");
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
        seccion1.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_01.tex}  \\end{tikzpicture}");
        seccion1.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion1.add(true);
        seccion1.add("1_02");
        seccion1.add("Importación aérea");
        seccion1.add("Volumen de carga importada a través del "
                + "aereopuerto internacional La Aurora en miles de Kilogramos");
        seccion1.add(formatoSerie);
        seccion1.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_02.tex}  \\end{tikzpicture}");
        seccion1.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion1.add(true);
        cap1.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("1_03");
        seccion2.add("Alimentos exportados vía aérea");
        seccion2.add("Carga de alimentos exportados a través del "
                + "aereopuerto internacional La Aurora en Kilogramos");
        seccion2.add(formatoSerie);
        seccion2.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_03.tex}  \\end{tikzpicture}");
        seccion2.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion2.add(true);
        seccion2.add("1_04");
        seccion2.add("Artículos exportados vía aérea");
        seccion2.add("Carga de artículos exportados a través del "
                + "aereopuerto internacional La Aurora en Kilogramos");
        seccion2.add(formatoSerie);
        seccion2.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_04.tex}  \\end{tikzpicture}");
        seccion2.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion2.add(true);
        cap1.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("1_05");
        seccion3.add("Plantas exportadoras vía aérea");
        seccion3.add("Carga de plantas exportadas a través del "
                + "aereopuerto internacional La Aurora en miles de Kilogramos");
        seccion3.add(formatoSerie);
        seccion3.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_05.tex}  \\end{tikzpicture}");
        seccion3.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion3.add(true);
        seccion3.add("1_06");
        seccion3.add("Animales exportados vía aérea");
        seccion3.add("Carga de animales exportados a través del "
                + "aeropuerto internacional La Aurora en Kilogramos");
        seccion3.add(formatoSerie);
        seccion3.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_06.tex}  \\end{tikzpicture}");
        seccion3.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion3.add(true);
        cap1.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("1_07");
        seccion4.add("Aeropuerto La Aurora: vuelos nacionales");
        seccion4.add("Despegues de vuelos nacionales en aeropuerto internacional"
                + "La Aurora");
        seccion4.add(formatoSerie);
        seccion4.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_07.tex}  \\end{tikzpicture}");
        seccion4.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion4.add(true);
        seccion4.add("1_08");
        seccion4.add("Aeropuerto La Aurora: vuelos internacionales");
        seccion4.add("Despegues de vuelos internacionales en aeropuerto "
                + "internacional La Aurora");
        seccion4.add(formatoSerie);
        seccion4.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_08.tex}  \\end{tikzpicture}");
        seccion4.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion4.add(true);
        cap1.add(seccion4);
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("1_09");
        seccion5.add("Aeropuerto La Aurora: pasajeros que arriban");
        seccion5.add("Pasajeros que arriban en el aeropuerto internacional La Aurora");
        seccion5.add(formatoSerie);
        seccion5.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_09.tex}  \\end{tikzpicture}");
        seccion5.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion5.add(true);
        seccion5.add("1_10");
        seccion5.add("Aeropuerto La Aurora: pasajeros que salen");
        seccion5.add("Pasajeros que salen del aeropuerto internacional La Aurora");
        seccion5.add(formatoSerie);
        seccion5.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_10.tex}  \\end{tikzpicture}");
        seccion5.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion5.add(true);
        cap1.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("1_11");
        seccion6.add("Aeropuerto Mundo Maya: pasajeros que arriban");
        seccion6.add("Pasajeros que arriban en el aeropuerto internacional Mundo Maya");
        seccion6.add(formatoSerie);
        seccion6.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_11.tex}  \\end{tikzpicture}");
        seccion6.add("INE, con datos de Dirección General de Aeronáutica Civil");
        seccion6.add(true);
        seccion6.add("1_12");
        seccion6.add("Aeropuerto Mundo Maya: pasajeros que salen");
        seccion6.add("Pasajeros que salen del aeropuerto internacional Mundo Maya");
        seccion6.add(formatoSerie);
        seccion6.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{1_12.tex}  \\end{tikzpicture}");
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
        seccion1.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_01.tex}  \\end{tikzpicture}");
        seccion1.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion1.add(true);
        seccion1.add("2_02");
        seccion1.add("Peso de la correspondencia vía aérea");
        seccion1.add("Peso de la correspondencia enviada por vía aérea en Kilogramos");
        seccion1.add(formatoSerie);
        seccion1.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_02.tex}  \\end{tikzpicture}");
        seccion1.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion1.add(true);
        cap2.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("2_03");
        seccion2.add("Envío de correspondencia por continente");
        seccion2.add("Envío de correspondencia vía aérea");
        seccion2.add("Distribución porcentual según continente");
        seccion2.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_03.tex}  \\end{tikzpicture}");
        seccion2.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion2.add(true);
        seccion2.add("2_04");
        seccion2.add("Principales países de destino de correspondencia");
        seccion2.add("Principales países de destino de la correspondencia enviada desde Guatemala");
        seccion2.add("Número de envíos");
        seccion2.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_04.tex}  \\end{tikzpicture}");
        seccion2.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion2.add(true);
        cap2.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("2_05");
        seccion3.add("Correspondencia a Centroamérica y Panamá");
        seccion3.add("Envío de correspondencia vía aérea a América Central y Panamá");
        seccion3.add(formatoSerie);
        seccion3.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_05.tex}  \\end{tikzpicture}");
        seccion3.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion3.add(true);
        seccion3.add("2_06");
        seccion3.add("Peso de la correspondencia enviada a Centroamérica y Panamá");
        seccion3.add("Peso de la correspondencia enviada por vía aérea a "
                + "América Central y Panamá en Kilogramos");
        seccion3.add(formatoSerie);
        seccion3.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_06.tex}  \\end{tikzpicture}");
        seccion3.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion3.add(true);
        cap2.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("2_07");
        seccion4.add("Correspondencia a Norteamérica");
        seccion4.add("Envío de correspondencia vía aérea a América del Norte");
        seccion4.add(formatoSerie);
        seccion4.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_07.tex}  \\end{tikzpicture}");
        seccion4.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion4.add(true);
        seccion4.add("2_08");
        seccion4.add("Peso de la correspondencia enviada a Norteamérica");
        seccion4.add("Peso de la correspondencia enviada por vía aérea a Norteamérica en Kilogramos");
        seccion4.add(formatoSerie);
        seccion4.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_08.tex}  \\end{tikzpicture}");
        seccion4.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion4.add(true);
        cap2.add(seccion4);
        
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("2_09");
        seccion5.add("Correspondencia a Sudamérica");
        seccion5.add("Envío de correspondencia vía aérea a América del Sur");
        seccion5.add(formatoSerie);
        seccion5.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_09.tex}  \\end{tikzpicture}");
        seccion5.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion5.add(true);
        seccion5.add("2_10");
        seccion5.add("Peso de la correspondencia enviada a Sudamérica");
        seccion5.add("Peso de la correspondencia enviada por vía aérea a "
                + "América del Sur en Kilogramos");
        seccion5.add(formatoSerie);
        seccion5.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_10.tex}  \\end{tikzpicture}");
        seccion5.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion5.add(true);
        cap2.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("2_11");
        seccion6.add("Correspondencia a Europa");
        seccion6.add("Envío de correspondencia vía aérea a Europa");
        seccion6.add(formatoSerie);
        seccion6.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_11.tex}  \\end{tikzpicture}");
        seccion6.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion6.add(true);
        seccion6.add("2_12");
        seccion6.add("Peso de la correspondencia enviada a Europa");
        seccion6.add("Peso de la correspondencia enviada por vía aérea a Europa en Kilogramos");
        seccion6.add(formatoSerie);
        seccion6.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_12.tex}  \\end{tikzpicture}");
        seccion6.add("INE, con datos del BANGUAT");
        seccion6.add(true);
        cap2.add(seccion6);
        
        
        ArrayList seccion7 = new ArrayList();
        seccion7.add("2_13");
        seccion7.add("Correspondencia a Asia, África y Oceanía");
        seccion7.add("Envío de correspondencia vía aérea a Asia, África y Oceanía");
        seccion7.add(formatoSerie);
        seccion7.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_13.tex}  \\end{tikzpicture}");
        seccion7.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion7.add(true);
        seccion7.add("2_14");
        seccion7.add("Peso de la correspondencia enviada a Asia, África y Oceanía");
        seccion7.add("Peso de la correspondencia enviada por vía aérea a Asia, África "
                + "y Oceanía en Kilogramos");
        seccion7.add(formatoSerie);
        seccion7.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_14.tex}  \\end{tikzpicture}");
        seccion7.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion7.add(true);
        cap2.add(seccion7);
        
        ArrayList seccion8 = new ArrayList();
        seccion8.add("2_15");
        seccion8.add("Envío de encomiendas vía aérea");
        seccion8.add("Cantidad de encomiendas enviadas por vía aérea");
        seccion8.add(formatoSerie);
        seccion8.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_15.tex}  \\end{tikzpicture}");
        seccion8.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion8.add(true);
        seccion8.add("2_16");
        seccion8.add("Peso de las encomiendas vía aérea");
        seccion8.add("Peso total de las encomiendas enviadas por vía aérea en Kilogramos");
        seccion8.add(formatoSerie);
        seccion8.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_16.tex}  \\end{tikzpicture}");
        seccion8.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion8.add(true);
        cap2.add(seccion8);
        
        
        
        
        ArrayList seccion9 = new ArrayList();
        seccion9.add("2_17");
        seccion9.add("Encomiendas a Centroamérica y Panamá");
        seccion9.add("Cantidad de encomiendas enviadas por vía aérea a "
                + "América Central y Panamá");
        seccion9.add(formatoSerie);
        seccion9.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_17.tex}  \\end{tikzpicture}");
        seccion9.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion9.add(true);
        seccion9.add("2_18");
        seccion9.add("Peso de las encomiendas a Centroamérica y Panamá");
        seccion9.add("Peso total de las encomiendas enviadas por vía aérea "
                + "a América Central y Panamá en Kilogramos");
        seccion9.add(formatoSerie);
        seccion9.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_18.tex}  \\end{tikzpicture}");
        seccion9.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion9.add(true);
        cap2.add(seccion9);
        
        
        
        
        ArrayList seccion10 = new ArrayList();
        seccion10.add("2_19");
        seccion10.add("Encomiendas a Norteamérica");
        seccion10.add("Cantidad de encomiendas enviadas por vía aérea a "
                + "América del Norte");
        seccion10.add(formatoSerie);
        seccion10.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_19.tex}  \\end{tikzpicture}");
        seccion10.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion10.add(true);
        seccion10.add("2_20");
        seccion10.add("Peso de las encomiendas a Norteamérica");
        seccion10.add("Peso total de las encomiendas enviadas por vía aérea "
                + "a América del Norte en Kilogramos");
        seccion10.add(formatoSerie);
        seccion10.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_20.tex}  \\end{tikzpicture}");
        seccion10.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion10.add(true);
        cap2.add(seccion10);
        
        ArrayList seccion11 = new ArrayList();
        seccion11.add("2_21");
        seccion11.add("Encomiendas a Sudamérica");
        seccion11.add("Cantidad de encomiendas enviadas por vía aérea a "
                + "América del Sur");
        seccion11.add(formatoSerie);
        seccion11.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_21.tex}  \\end{tikzpicture}");
        seccion11.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion11.add(true);
        seccion11.add("2_22");
        seccion11.add("Peso de las encomiendas a Sudamérica");
        seccion11.add("Peso total de las encomiendas enviadas por vía aérea "
                + "a América del Sur en Kilogramos");
        seccion11.add(formatoSerie);
        seccion11.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_22.tex}  \\end{tikzpicture}");
        seccion11.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion11.add(true);
        cap2.add(seccion11);
        
        
        
        ArrayList seccion12 = new ArrayList();
        seccion12.add("2_23");
        seccion12.add("Encomiendas a Europa");
        seccion12.add("Cantidad de encomiendas enviadas por vía aérea a "
                + "Europa");
        seccion12.add(formatoSerie);
        seccion12.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_23.tex}  \\end{tikzpicture}");
        seccion12.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion12.add(true);
        seccion12.add("2_24");
        seccion12.add("Peso de las encomiendas a Europa");
        seccion12.add("Peso total de las encomiendas enviadas por vía aérea "
                + "a Europa en Kilogramos");
        seccion12.add(formatoSerie);
        seccion12.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_24.tex}  \\end{tikzpicture}");
        seccion12.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion12.add(true);
        cap2.add(seccion12);
        
        
        
        ArrayList seccion13 = new ArrayList();
        seccion13.add("2_25");
        seccion13.add("Encomiendas a Asia, África y Oceanía");
        seccion13.add("Cantidad de encomiendas enviadas por vía aérea a "
                + "Asia, África y Oceanía");
        seccion13.add(formatoSerie);
        seccion13.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_25.tex}  \\end{tikzpicture}");
        seccion13.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion13.add(true);
        seccion13.add("2_26");
        seccion13.add("Peso de las encomiendas a Asia, África y Oceanía");
        seccion13.add("Peso total de las encomiendas enviadas por vía aérea "
                + "a Asia, África y Oceanía en Kilogramos");
        seccion13.add(formatoSerie);
        seccion13.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_26.tex}  \\end{tikzpicture}");
        seccion13.add("INE, con datos de Dirección General de Correos y Telégrafos");
        seccion13.add(true);
        cap2.add(seccion13);
        
        ArrayList seccion14 = new ArrayList();
        seccion14.add("2_27");
        seccion14.add("Principales destinos de las encomiendas");
        seccion14.add("Principales países de destino de las encomiendas");
        seccion14.add(getFormatoTrimestre());
        seccion14.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{2_27.tex}  \\end{tikzpicture}");
        seccion14.add("INE, con datos de Dirección General de Correos y Telégrafos");
        
        
        
        return cap2;        
    }
    
    
    
    protected ArrayList cargarCapitulo3(){
        ArrayList cap3 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("3_01");
        seccion1.add("Exportaciones decreto 29-89");
        seccion1.add("Comercio decreto 29-89: exportaciones trimestrales en millones de US$");
        seccion1.add(formatoSerie);
        seccion1.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_01.tex}  \\end{tikzpicture}");
        seccion1.add("INE, con datos del BANGUAT");
        seccion1.add(true);
        seccion1.add("3_02");
        seccion1.add("Variación interanual de las exportaciones");
        seccion1.add("Porcentaje de variación interanual de las exportaciones por comercio decreto 29-89");
        seccion1.add(formatoSerie);
        seccion1.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_02.tex}  \\end{tikzpicture}");
        seccion1.add("INE, con datos del BANGUAT");
        seccion1.add(true);
        cap3.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("3_03");
        seccion2.add("Importaciones decreto 29-89");
        seccion2.add("Importaciones trimestrales comercio decreto 29-89 en millones de US$");
        seccion2.add(formatoSerie);
        seccion2.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_03.tex}  \\end{tikzpicture}");
        seccion2.add("INE, con datos del BANGUAT");
        seccion2.add(true);
        seccion2.add("3_04");
        seccion2.add("Variación Interanual de las importaciones");
        seccion2.add("Porcentaje de variación interanual de las importaciones por comercio decreto 29-89");
        seccion2.add(formatoSerie);
        seccion2.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_04.tex}  \\end{tikzpicture}");
        seccion2.add("INE, con datos del BANGUAT");
        seccion2.add(true);
        cap3.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("3_05");
        seccion3.add("Balanza comercial del decreto 29-89");
        seccion3.add("Balanza comercial trimestral del decreto 29-89 en millones de US$");
        seccion3.add(formatoSerie);
        seccion3.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_05.tex}  \\end{tikzpicture}");
        seccion3.add("INE, con datos del BANGUAT");
        seccion3.add(true);
        seccion3.add("3_06");
        seccion3.add("Balanza comercial relativa del decreto 29-89");
        seccion3.add("Balanza comercial relativa del decreto 29-89 adimensional");
        seccion3.add(formatoSerie);
        seccion3.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_06.tex}  \\end{tikzpicture}");
        seccion3.add("INE, con datos del BANGUAT");
        seccion3.add(true);
        cap3.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("3_07");
        seccion4.add("Balanza comercial por continente");
        seccion4.add("Balanca comercial comercio decreto 29-89 por continente en millones de US$");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_07.tex}  \\end{tikzpicture}");
        seccion4.add("INE, con datos del BANGUAT");
        seccion4.add(true);
        seccion4.add("3_08");
        seccion4.add("Balanza comercial con Centroamérica");
        seccion4.add("Balanza comercial del decreto 29-89 con el mercado común centroamericano en millones de US$");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_08.tex}  \\end{tikzpicture}");
        seccion4.add("INE, con datos del BANGUAT");
        seccion4.add(true);
        cap3.add(seccion4);
        
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("3_09");
        seccion5.add("Principales productos de exportación");
        seccion5.add("Principales productos de exportación por decreto 29-89 en millones de US$");
        seccion5.add(getFormatoTrimestre());
        seccion5.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_09.tex}  \\end{tikzpicture}");
        seccion5.add("INE, con datos del BANGUAT");
        seccion5.add(true);
        seccion5.add("3_10");
        seccion5.add("Exportaciones según los principales países de destino");
        seccion5.add("Principales países de exportaciones guatemaltecas por decreto 29-89 en millones de US$");
        seccion5.add(getFormatoTrimestre());
        seccion5.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_10.tex}  \\end{tikzpicture}");
        seccion5.add("INE, con datos del BANGUAT");
        seccion5.add(true);
        cap3.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("3_11");
        seccion6.add("Principales exportaciones según secciones SAC");
        seccion6.add("Exportaciones por decreto 29-89 según las principales secciones del SAC en millones de US$");
        seccion6.add(getFormatoTrimestre());
        seccion6.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_11.tex}  \\end{tikzpicture}");
        seccion6.add("INE, con datos del BANGUAT");
        seccion6.add(true);
        seccion6.add("3_12");
        seccion6.add("Principales productos de importación");
        seccion6.add("Principales productos de importación por decreto 29-89 en millones de US$");
        seccion6.add(getFormatoTrimestre());
        seccion6.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_12.tex}  \\end{tikzpicture}");
        seccion6.add("INE, con datos del BANGUAT");
        seccion6.add(true);
        cap3.add(seccion6);
        
        
        ArrayList seccion7 = new ArrayList();
        seccion7.add("3_13");
        seccion7.add("Importaciones según los principales países de origen");
        seccion7.add("Importación por decreto 29-89 a los principales países en millones de US$");
        seccion7.add(getFormatoTrimestre());
        seccion7.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_13.tex}  \\end{tikzpicture}");
        seccion7.add("INE, con datos del BANGUAT");
        seccion7.add(true);
        seccion7.add("3_14");
        seccion7.add("Principales importaciones según secciones del SAC");
        seccion7.add("Importaciones por decreto 29-89 según las principales secciones del SAC en millones de US$");
        seccion7.add(getFormatoTrimestre());
        seccion7.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{3_14.tex}  \\end{tikzpicture}");
        seccion7.add("INE, con datos del BANGUAT");
        seccion7.add(true);
        cap3.add(seccion7);
        
        
        return cap3;        
    }
    
    
    protected ArrayList cargarCapitulo4(){
        ArrayList cap4 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("4_01");
        seccion1.add("Exportaciones zonas francas");
        seccion1.add("Comercio zonas francas: exportaciones trimestrales en millones de US$");
        seccion1.add(formatoSerie);
        seccion1.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_01.tex}  \\end{tikzpicture}");
        seccion1.add("INE, con datos del BANGUAT");
        seccion1.add(true);
        seccion1.add("4_02");
        seccion1.add("Variación interanual de las exportaciones");
        seccion1.add("Porcentaje de variación interanual de las exportaciones por comercio zonas francas");
        seccion1.add(formatoSerie);
        seccion1.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_02.tex}  \\end{tikzpicture}");
        seccion1.add("INE, con datos del BANGUAT");
        seccion1.add(true);
        cap4.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("4_03");
        seccion2.add("Importaciones zonas francas");
        seccion2.add("Importaciones trimestrales comercio zonas francas en millones de US$");
        seccion2.add(formatoSerie);
        seccion2.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_03.tex}  \\end{tikzpicture}");
        seccion2.add("INE, con datos del BANGUAT");
        seccion2.add(true);
        seccion2.add("4_04");
        seccion2.add("Variación Interanual de las importaciones");
        seccion2.add("Porcentaje de variación interanual de las importaciones por comercio zonas francas");
        seccion2.add(formatoSerie);
        seccion2.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_04.tex}  \\end{tikzpicture}");
        seccion2.add("INE, con datos del BANGUAT");
        seccion2.add(true);
        cap4.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("4_05");
        seccion3.add("Balanza comercial de zonas francas");
        seccion3.add("Balanza comercial trimestral de zonas francas en millones de US$");
        seccion3.add(formatoSerie);
        seccion3.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_05.tex}  \\end{tikzpicture}");
        seccion3.add("INE, con datos del BANGUAT");
        seccion3.add(true);
        seccion3.add("4_06");
        seccion3.add("Balanza comercial relativa de zonas francas");
        seccion3.add("Balanza comercial relativa de zonas francas adimensional");
        seccion3.add(formatoSerie);
        seccion3.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_06.tex}  \\end{tikzpicture}");
        seccion3.add("INE, con datos del BANGUAT");
        seccion3.add(true);
        cap4.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("4_07");
        seccion4.add("Balanza comercial por continente");
        seccion4.add("Balanca comercial zonas francas por continente en millones de US$");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_07.tex}  \\end{tikzpicture}");
        seccion4.add("INE, con datos del BANGUAT");
        seccion4.add(true);
        seccion4.add("4_08");
        seccion4.add("Balanza comercial con Centroamérica");
        seccion4.add("Balanza comercial de zonas francas con el mercado común centroamericano en millones de US$");
        seccion4.add(getFormatoTrimestre());
        seccion4.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_08.tex}  \\end{tikzpicture}");
        seccion4.add("INE, con datos del BANGUAT");
        seccion4.add(true);
        cap4.add(seccion4);
        
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("4_09");
        seccion5.add("Principales productos de exportación");
        seccion5.add("Principales productos de exportación por zonas francas en millones de US$");
        seccion5.add(getFormatoTrimestre());
        seccion5.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_09.tex}  \\end{tikzpicture}");
        seccion5.add("INE, con datos del BANGUAT");
        seccion5.add(true);
        seccion5.add("4_10");
        seccion5.add("Exportaciones según los principales países de destino");
        seccion5.add("Principales países de exportaciones guatemaltecas por zonas francas en millones de US$");
        seccion5.add(getFormatoTrimestre());
        seccion5.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_10.tex}  \\end{tikzpicture}");
        seccion5.add("INE, con datos del BANGUAT");
        seccion5.add(true);
        cap4.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("4_11");
        seccion6.add("Principales exportaciones según secciones SAC");
        seccion6.add("Exportaciones por zonas francas según las principales secciones del SAC en millones de US$");
        seccion6.add(getFormatoTrimestre());
        seccion6.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_11.tex}  \\end{tikzpicture}");
        seccion6.add("INE, con datos del BANGUAT");
        seccion6.add(true);
        seccion6.add("4_12");
        seccion6.add("Principales productos de importación");
        seccion6.add("Principales productos de importación por zonas francas en millones de US$");
        seccion6.add(getFormatoTrimestre());
        seccion6.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_12.tex}  \\end{tikzpicture}");
        seccion6.add("INE, con datos del BANGUAT");
        seccion6.add(true);
        cap4.add(seccion6);
        
        
        ArrayList seccion7 = new ArrayList();
        seccion7.add("4_13");
        seccion7.add("Importaciones según los principales países de origen");
        seccion7.add("Importación por zonas francas a los principales países en millones de US$");
        seccion7.add(getFormatoTrimestre());
        seccion7.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_13.tex}  \\end{tikzpicture}");
        seccion7.add("INE, con datos del BANGUAT");
        seccion7.add(true);
        seccion7.add("4_14");
        seccion7.add("Principales importaciones según secciones del SAC");
        seccion7.add("Importaciones por zonas francas según las principales secciones del SAC en millones de US$");
        seccion7.add(getFormatoTrimestre());
        seccion7.add("\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{4_14.tex}  \\end{tikzpicture}");
        seccion7.add("INE, con datos del BANGUAT");
        seccion7.add(true);
        cap4.add(seccion7);
        
        
        return cap4;        
    }
        protected void apendices(String rutaTEX){
        escribirLinea("\n \\appendixa \n" +
        "\n" +
        "\n" +
        "\n" );
        apendice1();
        Tabla ap = new Tabla(rutaTEX,trimestres(),rr);
        ap.setRuta("/var/www/html/Comercio/Entradas/CSV");
        ap.generarComercioExterior();
        
        
        
    }
    private void apendice1(){
        String columna1 = tablaApendice("A_01",
                "Comercio total: análisis de variación de las exportaciones",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos del BANGUAT",
                "");
        
        String columna2 = tablaApendice("A_02",
                "Comercio total: análisis de variación de las importaciones",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos del BANGUAT",
                "");
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    protected void generarGraficas(String modalidad){
        System.out.println("GENERANDO LAS GRAFICAS");
        Grafica vitales = new Grafica("vitales", getRuta(), rr.get(), modalidad);
        vitales.start();
    }
    
    protected void hacerPortada(){
       String portada = "http://www.ine.gob.gt/ftparchivos/portadaVitales.pdf";
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
        
        String contraPortada = "http://www.ine.gob.gt/ftparchivos/contraportadaVitales.pdf";
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
            "\\node[inner sep =0, rotate = 90]at(0.908,0.15){Guatemala, "+ getMes()  +" de " +  getYear()+"};\n "
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
    
    private String asistencia(String valor){
        String retorno = "";
        System.out.println(valor);
        if(comparador.compare(valor,"medica") == 0){
            retorno = "recibieron asistencia médica";
        }else if(comparador.compare(valor, "comadrona") == 0){
            retorno = "fueron asistidos por una comadrona";
        }else if(comparador.compare(valor,"ninguna") == 0){
            retorno = "no recibieron ningún tipo de asistencia";
        }else if(comparador.compare(valor,"paramedica") == 0){
            retorno = "recibieron asistencia paramédica";
        }else if(comparador.compare(valor, "empirica") == 0){
            retorno = "recibieron asistencia empírica";
        }
        return retorno;
    }

    private String sitio(String valor){
        String retorno = "";
        System.out.println(valor);
        if(comparador.compare(valor, "Domicilio") == 0){
            retorno = "el domicilio del fallecido";
        }else if(comparador.compare(valor, "Hospital público") == 0){
            retorno = "un hospital público";
        }else if(comparador.compare(valor, "Seguro Social") == 0){
            retorno = "el IGSS";
        }else if(comparador.compare(valor, "Hospital privado") == 0){
            retorno = "un hospital privado";
        }else if(comparador.compare(valor,"Centro de salud") == 0){
            retorno = "un centro de salud";
        }else if(comparador.compare(valor, "Lugar de trabajo") == 0){
            retorno = " el lugar de trabajo del fallecido ";
        }else if(comparador.compare(valor, "Vía pública") == 0){
            retorno = " la vía pública";
        }
        return retorno;
    }
    
   

        



}

