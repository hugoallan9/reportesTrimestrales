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
 * @author INE, con datos de la Policía Nacional Civil
 */
public class HechosDelictivos extends Documento{
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
    
    
    
    
    public HechosDelictivos(String titulo, String trimestre, String pYear, String rutaCSV) {
        super(titulo, trimestre, pYear);
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
        capitulos.add("Víctimas de hechos delictivos");
        capitulos.add("Detenidos");
        capitulos.add("Sindicados");
        capitulos.add("Evaluaciones médico legal");
        capitulos.add("Necropsias");
        capitulos.add("Sentenciados");        
    }
    
    protected void setIntroCapitulos(){
        introCapitulos.add("Sujeto pasivo del delito y de la persecución indebida que "
                + "sufre violencia injusta en su persona o ataque a sus derechos.");
        introCapitulos.add("El(la) capturado(a) por cometer un delito encontrándose "
                + "en calidad infraganti, o por anteriores investigaciones realizadas, "
                + "le hayan encontrado pruebas en contra que lo(a) transforman en "
                + "acusado(a) o sindicado(a).");
        introCapitulos.add("El(la) acusado(a) de infringir las leyes penales.");
        introCapitulos.add("Criterio médico legal registrado en un documento oficial "
                + "que expresa el análisis científico, de las consecuencias del hecho "
                + "lesivo sobre el individuo, el que es de interés para la persona u "
                + "organismos, los tipos de informes médico legal pueden ser "
                + "certificados por lesiones, toxicológico, psicofisiológico, "
                + "ginecológico, andrológico, proctológico, de edad clínica, de "
                + "defunción; dictamen o protocolo, constancia, comprobante, receta "
                + "médica, responsiva médica, informe, acta médica, certificado de "
                + "defunción y muerte fetal, e historia clínica.");
        introCapitulos.add("Su etimología proviene de las voces griegas \"necros\" "
                + "que significa cadáver y \"opsis\" que significa cortar, es decir, "
                + "cortar un cadáver. También llamada autopsia, es el estudio que "
                + "realiza un médico especialista en patología, al exhumar un cuerpo "
                + "humano que se encuentra enterrado, se analiza el exterior del cadáver, "
                + "se practica una incisión para observar los órganos internos, se "
                + "anota su posición del cuerpo y se extraen las partes para su "
                + "análisis visual con ayuda del microscopio.");
        introCapitulos.add("Persona en la que recae una resolución judicial de una "
                + "causa y fallo en la cuestión principal de un proceso. La sentencia "
                + "puede ser absolutoria o condenatoria. Es sentencia absolutoria al "
                + "no probrase los hechos en que una parte apoya su pretensión o por "
                + "no contar con fundamentos jurídicos, la que desestima la petición "
                + "del actor o no hace lugar a la acusación formulada. Sentencia "
                + "condenatoria es la que acepta en todo o en parte las pretensiones "
                + "del actor, manifestadas en la demanda, o las del acusador expuestas "
                + "en la querella.");
    }
    protected void setContenidos(){
         contenidos.add(cargarCapitulo1());
         contenidos.add(cargarCapitulo2());
         contenidos.add(cargarCapitulo3());
         contenidos.add(cargarCapitulo4());
         contenidos.add(cargarCapitulo5());
         contenidos.add(cargarCapitulo6());
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
                REXP listadoCSV = rr.get().eval("delictivos <- cargaMasiva('" +  ruta +"', codificacion = 'utf8')");
                REXP nombres = rr.get().eval("names(delictivos)");
                System.out.println(listadoCSV);
                System.out.println(nombres);
            }
    }
    
    protected ArrayList cargarCapitulo1(){
        ArrayList cap1 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("1_01");
        seccion1.add("Víctimas");
        seccion1.add("Víctimas de hechos delictivos");
        seccion1.add(formatoSerie + "en unidades");
        seccion1.add("1_01.tex");
        seccion1.add("INE, con datos de la Policía Nacional Civil");
        seccion1.add(true);
        seccion1.add("1_02");
        seccion1.add("Víctimas según causa");
        seccion1.add("Distribución de las víctimas de hechos delictivos por "
                + "tipo de causa");
        seccion1.add(formatoTrimestre + "en porcentaje");
        seccion1.add("1_02.tex");
        seccion1.add("INE, con datos de la Policía Nacional Civil");
        seccion1.add(true);
        cap1.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("1_03");
        seccion2.add("Víctimas según departamento");
        seccion2.add("Distribución de las víctimas de hechos delictivos "
                + "por departamento");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("1_03.tex");
        seccion2.add("INE, con datos de la Policía Nacional Civil");
        seccion2.add(true);
        seccion2.add("1_04");
        seccion2.add("Víctimas por área geográfica");
        seccion2.add("Distribución de las víctimas de hechos delictivos "
                + "por área geográfica");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("1_04.tex");
        seccion2.add("INE, con datos de la Policía Nacional Civil");
        seccion2.add(true);
        cap1.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("1_05");
        seccion3.add("Víctimas según grupos de edad y sexo");
        seccion3.add("Víctimas según grupos de edad y sexo");
        seccion3.add(formatoTrimestre + "en unidades");
        seccion3.add("1_05.tex");
        seccion3.add("INE, con datos de la Policía Nacional Civil");
        seccion3.add(true);
           seccion3.add("1_06");
        seccion3.add("Víctimas de homicidio por tipo");
        seccion3.add("Víctimas de homicidio");
        seccion3.add(formatoTrimestre + "en unidades");
        seccion3.add("1_06.tex");
        seccion3.add("INE, con datos de la Policía Nacional Civil");
        seccion3.add(true);
        cap1.add(seccion3);
        ArrayList seccion4 = new ArrayList();
        seccion4.add("1_07");
        seccion4.add("Víctimas de delitos contra el patrimonio");
        seccion4.add("Víctimas de hechos delictivos contra el patrimonio, según "
                + "el tipo");
        seccion4.add(formatoTrimestre + "en unidades");
        seccion4.add("1_07.tex");
        seccion4.add("INE, con datos de la Policía Nacional Civil");
        seccion4.add(true);
        seccion4.add("1_08");
        seccion4.add("Víctimas de extorsiones por departamento");
        seccion4.add("Distribución de las víctimas de extorsiones por departamento");
        seccion4.add(formatoTrimestre + "en porcentaje");
        seccion4.add("1_08.tex");
        seccion4.add("INE, con datos de la Policía Nacional Civil");
        seccion4.add(true);
        cap1.add(seccion4);
        ArrayList seccion5 = new ArrayList();
        seccion5.add("1_09");
        seccion5.add("Víctimas de extorsiones por sexo");
        seccion5.add("Distribución de las víctimas de extorsiones por sexo");
        seccion5.add(formatoTrimestre + "en porcentaje");
        seccion5.add("1_09.tex");
        seccion5.add("INE, con datos de la Policía Nacional Civil");
        seccion5.add(true);
        seccion5.add("1_10");
        seccion5.add("Víctimas de extorsiones por grupos de edad y sexo");
        seccion5.add("Víctimas de extorsiones por grupos de edad y sexo");
        seccion5.add(formatoTrimestre + "en unidades");
        seccion5.add("1_10.tex");
        seccion5.add("INE, con datos de la Policía Nacional Civil");
        seccion5.add(true);
        cap1.add(seccion5);
        return cap1;        
        
    }
    protected ArrayList cargarCapitulo2(){
        ArrayList cap2 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("2_01");
        seccion1.add("Detenidos");
        seccion1.add("Detenidos(as) por cometer hechos delictivos");
        seccion1.add(formatoSerie + "en unidades");
        seccion1.add("2_01.tex");
        seccion1.add("INE, con datos de la Policía Nacional Civil");
        seccion1.add(true);
        seccion1.add("2_02");
        seccion1.add("Detenidos según tipos de causa");
        seccion1.add("Detenidos(as) por cometer hechos delictivos según los principales "
                + "tipos de causa");
        seccion1.add(formatoTrimestre + "en unidades");
        seccion1.add("2_02.tex");
        seccion1.add("INE, con datos de la Policía Nacional Civil");
        seccion1.add(true);
        cap2.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("2_03");
        seccion2.add("Detenidos según departamento");
        seccion2.add("Detenidos(as) por cometer hechos delictivos según"
                + "departamento");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("2_03.tex");
        seccion2.add("INE, con datos de la Policía Nacional Civil");
        seccion2.add(true);
        seccion2.add("2_04");
        seccion2.add("Detenidos según área geográfica");
        seccion2.add("Detenidos(as) por cometer hechos delictivos según "
                + "área geográfica");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("2_04.tex");
        seccion2.add("INE, con datos de la Policía Nacional Civil");
        seccion2.add(true);
        cap2.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("2_05");
        seccion3.add("Detenidos según edad y sexo");
        seccion3.add("Detenidos(as) por cometer hechos delictivos según edad y sexo");
        seccion3.add(formatoTrimestre + "en unidades");
        seccion3.add("2_05.tex");
        seccion3.add("INE, con datos de la Policía Nacional Civil");
        seccion3.add(true);
        seccion3.add("2_06");
        seccion3.add("Detenidos por cometer homicidio según tipo");
        seccion3.add("Detenidos(as) por cometer homicidios según el tipo de arma utilizada");
        seccion3.add(formatoTrimestre + "en unidades");
        seccion3.add("2_06.tex");
        seccion3.add("INE, con datos de la Policía Nacional Civil");
        seccion3.add(true);
        cap2.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("2_07");
        seccion4.add("Detenidos por cometer delitos contra el patrimonio");
        seccion4.add("Detenidos(as) por cometer delitos contra el patrimonio, según las principales "
                + "causas");
        seccion4.add(formatoTrimestre + "en unidades");
        seccion4.add("2_07.tex");
        seccion4.add("INE, con datos de la Policía Nacional Civil");
        seccion4.add(true);
        seccion4.add("2_08");
        seccion4.add("Detenidos por extorsiones según departamento");
        seccion4.add("Detenidos(as) por cometer extorsiones "
                + "según departamento");
        seccion4.add(formatoTrimestre + "en porcentaje");
        seccion4.add("2_08.tex");       
        seccion4.add("INE, con datos de la Policía Nacional Civil");
        seccion4.add(true);
        cap2.add(seccion4);
        ArrayList seccion5 = new ArrayList();
        seccion5.add("2_09");
        seccion5.add("Detenidos por extorsiones según sexo");
        seccion5.add("Detenidos(as) por cometer extorsiones "
                + "según sexo");
        seccion5.add(formatoTrimestre + "en porcentaje");
        seccion5.add("2_09.tex");
        seccion5.add("INE, con datos de la Policía Nacional Civil");
        seccion5.add(true);
        seccion5.add("2_10");
        seccion5.add("Detenidos por extorsiones según grupos de edad y sexo");
        seccion5.add("Detenidos(as) por cometer extorsiones según edad y sexo");
        seccion5.add(formatoTrimestre + "en unidades");
        seccion5.add("2_10.tex");       
        seccion5.add("INE, con datos de la Policía Nacional Civil");
        seccion5.add(true);
        cap2.add(seccion5);
        return cap2;        
    }
    
    
    
    protected ArrayList cargarCapitulo3(){
        ArrayList cap3 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("3_01");
        seccion1.add("Sindicados");
        seccion1.add("Sindicados por cometer hechos delictivos");
        seccion1.add(formatoSerie + "en unidades");
        seccion1.add("3_01.tex");
        seccion1.add("INE, con datos del Ministerio Público");
        seccion1.add(true);
        seccion1.add("3_02");
        seccion1.add("Sindicados por tipo de delito");
        seccion1.add("Distribución de las sindicados por cometer hechos delictivos, "
                + "según el tipo");
        seccion1.add(formatoTrimestre + "en porcentaje");
        seccion1.add("3_02.tex");
        seccion1.add("INE, con datos del Ministerio Público");
        seccion1.add(true);
        cap3.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("3_03");
        seccion2.add("Sindicados según departamento");
        seccion2.add("Distribución de las sindicados por cometer hechos delictivos, "
                + "por departamento");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("3_03.tex");
        seccion2.add("INE, con datos del Ministerio Público");
        seccion2.add(true);
        seccion2.add("3_04");
        seccion2.add("Sindicados según mes");
        seccion2.add("Distribución de las sindicados por cometer hechos delictivos, "
                + "según mes");
        seccion2.add(formatoTrimestre + "en unidades");
        seccion2.add("3_04.tex");
        seccion2.add("INE, con datos del Ministerio Público");
        seccion2.add(true);
        //cap3.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("3_05");
        seccion3.add("Sindicados según sexo");
        seccion3.add("Distribución de las sindicados por cometer hechos delictivos, "
                + "según sexo");
        seccion3.add(formatoTrimestre + "en porcentaje");
        seccion3.add("3_05.tex");
        seccion3.add("INE, con datos del Ministerio Público");
        seccion3.add(true);
        seccion3.add("3_06");
        seccion3.add("Sinidcados según sexo y grupo de edad");
        seccion3.add("Distribucion de los sindicados por cometer hechos delictivos,"
                + " según sexo y grupo de edad ");
        seccion3.add(formatoTrimestre + "en unidades");
        seccion3.add("3_06.tex");
        seccion3.add("INE, con datos del Ministerio Público");
        seccion3.add(true);
        cap3.add(seccion3);
        
        return cap3;        
    }
    
    
    protected ArrayList cargarCapitulo4(){
        ArrayList cap4 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("4_01");
        seccion1.add("Evaluaciones médico legal");
        seccion1.add("Evaluciones médico legal");
        seccion1.add(formatoSerie + "en unidades");
        seccion1.add("4_01.tex");
        seccion1.add("INE, con datos del Instituto Nacional de Ciencias Forenses");
        seccion1.add(true);
        seccion1.add("4_02");
        seccion1.add("Evaluaciones médico legal por tipo de dictamen");
        seccion1.add("Evaluaciones médico legal, según tipo de dictamen");
        seccion1.add(formatoTrimestre + "en porcentaje");
        seccion1.add("4_02.tex");
        seccion1.add("INE, con datos del Instituto Nacional de Ciencias Forenses");
        seccion1.add(true);
        cap4.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("4_03");
        seccion2.add("Evaluaciones médico legal por departamento");
        seccion2.add("Evaluaciones médico legal, según departamento");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("4_03.tex");
        seccion2.add("INE, con datos del Instituto Nacional de Ciencias Forenses");
        seccion2.add(true);
        seccion2.add("4_04");
        seccion2.add("Evaluaciones médico legal por sexo");
        seccion2.add("Evaluaciones médico legal, según sexo");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("4_04.tex");
        seccion2.add("INE, con datos del Instituto Nacional de Ciencias Forenses");
        seccion2.add(true);
        cap4.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("4_05");
        seccion3.add("Evaluaciones médico legal por grupos de edad");
        seccion3.add("Evaluaciones médico legal, según grupo de edad");
        seccion3.add(formatoTrimestre + "en unidades");
        seccion3.add("4_05.tex");
        seccion3.add("INE, con datos del Instituto Nacional de Ciencias Forenses");
        seccion3.add(true);
        
        cap4.add(seccion3);
        
        return cap4;        
    }
    protected ArrayList cargarCapitulo5(){
        ArrayList cap5 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("5_01");
        seccion1.add("Necropsias");
        seccion1.add("Número de necropsias");
        seccion1.add(formatoSerie + "en unidades");
        seccion1.add("5_01.tex");
        seccion1.add("INE, con datos del Instituto Nacional de Ciencias Forenses");
        seccion1.add(true);
        seccion1.add("5_02");
        seccion1.add("Necropsias por tipo de dictamen");
        seccion1.add("Necropsias, según tipo de dictamen");
        seccion1.add(formatoTrimestre + "en porcentaje");
        seccion1.add("5_02.tex");
        seccion1.add("INE, con datos del Instituto Nacional de Ciencias Forenses");
        seccion1.add(true);
        cap5.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("5_03");
        seccion2.add("Necropsias por departamento");
        seccion2.add("Necropsias, según departamento");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("5_03.tex");
        seccion2.add("INE, con datos del Instituto Nacional de Ciencias Forenses");
        seccion2.add(true);
        seccion2.add("5_04");
        seccion2.add("Necropsias por sexo");
        seccion2.add("Necropsias, según sexo");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("5_04.tex");
        seccion2.add("INE, con datos del Instituto Nacional de Ciencias Forenses");
        seccion2.add(true);
        cap5.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("5_05");
        seccion3.add("Necropsias por grupos de edad");
        seccion3.add("Necropsias, según grupo de edad");
        seccion3.add(formatoTrimestre + "en unidades");
        seccion3.add("5_05.tex");
        seccion3.add("INE, con datos del Instituto Nacional de Ciencias Forenses");
        seccion3.add(true);
        cap5.add(seccion3);
        
        return cap5;        
    }
    
    protected ArrayList cargarCapitulo6(){
        ArrayList cap6 = new ArrayList();
        ArrayList seccion1 = new ArrayList();
        seccion1.add("6_01");
        seccion1.add("Sentencias");
        seccion1.add("Sentenciados(as) por el Organismo Judicial, por cometer hechos delictivos");
        seccion1.add(formatoSerie + "en datos absolutos");
        seccion1.add("6_01.tex");
        seccion1.add("INE, con datos del Organismo Judicial");
        seccion1.add(true);
        seccion1.add("6_02");
        seccion1.add("Sentencias por tipo");
        seccion1.add("Sentenciados(as) por el Organismo Judicial, por cometer hechos delictivos, "
                + "según el tipo de sentencia");
        seccion1.add(formatoTrimestre + "en unidades");
        seccion1.add("6_02.tex");
        seccion1.add("INE, con datos del Organismo Judicial");
        seccion1.add(true);
        cap6.add(seccion1);
        
        
        ArrayList seccion2 = new ArrayList();
        seccion2.add("6_03");
        seccion2.add("Sentencias condenatorias por departamento");
        seccion2.add("Distribución porcentual de las personas procesadas por el Organismo Judicial "
                + "por cometer hechos delictivos, con sentencia condenatoria, por departamento");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("6_03.tex");
        seccion2.add("INE, con datos del Organismo Judicial");
        seccion2.add(true);
        seccion2.add("6_04");
        seccion2.add("Setnencias condenatorias por sexo");
        seccion2.add("Distribución porcentual de las personas procesadas por el Organismo Judicial "
                + "por cometer hechos delictivos, con sentencia condenatoria, según sexo");
        seccion2.add(formatoTrimestre + "en porcentaje");
        seccion2.add("6_04.tex");
        seccion2.add("INE, con datos del Organismo Judicial");
        seccion2.add(true);
        cap6.add(seccion2);
        
        
        ArrayList seccion3 = new ArrayList();
        seccion3.add("6_05");
        seccion3.add("Sentencias condenatorias por edad y sexo");
        seccion3.add("Personas procesadas por el Organismo Judicial por cometer hechos delictivos, "
                + "con sentencia condenatoria, según edad y sexo");
        seccion3.add(formatoTrimestre + "en unidades");
        seccion3.add("6_05.tex");
        seccion3.add("INE, con datos del Organismo Judicial");
        seccion3.add(true);
        seccion3.add("6_06");
        seccion3.add("Sentencias condenatorias por tipo de ley");
        seccion3.add("Distribución porcentual de las personas procesadas por el Organismo Judicial "
                + "por cometer hechos delictivos, con sentencia condenatoria, según ley aplicada");
        seccion3.add(formatoTrimestre + "en porcentaje");
        seccion3.add("6_06.tex");
        seccion3.add("INE, con datos del Organismo Judicial");
        seccion3.add(true);
        cap6.add(seccion3);
        
        
        
        ArrayList seccion4 = new ArrayList();
        seccion4.add("6_07");
        seccion4.add("Sentencias condenatorias por tipo de delito en el Ramo Penal");
        seccion4.add("Distribución porcentual de las personas procesadas por el Organismo Judicial "
                + "por cometer hechos delictivos del ramo penal, con sentencia condenatoria, "
                + "según delitos");
        seccion4.add(formatoTrimestre + "en porcentaje");
        seccion4.add("6_07.tex");
        seccion4.add("INE, con datos del Organismo Judicial");
        seccion4.add(true);
        seccion4.add("6_08");
        seccion4.add("Sentencias aboslutorias por departamento");
        seccion4.add("Distribución de las personas procesadas por el Organismo Judicial "
                + "por cometer hechos delictivos, con sentencia absolutoria, por departamento");
        seccion4.add(formatoTrimestre + "en porcentaje");
        seccion4.add("6_08.tex");       
        seccion4.add("INE, con datos del Organismo Judicial");
        seccion4.add(true);
        cap6.add(seccion4);
        
        
        ArrayList seccion5 = new ArrayList();
        seccion5.add("6_09");
        seccion5.add("Sentencias absolutorias por sexo");
        seccion5.add("Distribución porcentual de las personas procesadas por el Organismo Judicial "
                + "por cometer hechos delictivos, con sentencia absolutoria, según sexo");
        seccion5.add(formatoTrimestre + "en porcentaje");
        seccion5.add("6_09.tex");
        seccion5.add("INE, con datos del Organismo Judicial");
        seccion5.add(true);
        seccion5.add("6_10");
        seccion5.add("Sentencias absolutorias por edad y sexo");
        seccion5.add("Personas procesadas por el Organismo Judicial por cometer hechos delictivos, "
                + "con sentencia absolutoria, según edad y sexo");
        seccion5.add(formatoTrimestre + "en porcentaje");
        seccion5.add("6_10.tex");       
        seccion5.add("INE, con datos del Organismo Judicial");
        seccion5.add(true);
        cap6.add(seccion5);
        
        ArrayList seccion6 = new ArrayList();
        seccion6.add("6_11");
        seccion6.add("Sentencias absolutorias por tipo de ley");
        seccion6.add("Distribución porcentual de las personas procesadas por el Organismo Judicial "
                + "por cometer hechos delictivos, con sentencia absolutoria, "
                + "según ley aplicada");
        seccion6.add(formatoTrimestre + "en porcentaje");
        seccion6.add("6_11.tex");
        seccion6.add("INE, con datos del Organismo Judicial");
        seccion6.add(true);
        seccion6.add("6_12");
        seccion6.add("Sentencias aboslutorias por tipo de delito en el Ramo Penal");
        seccion6.add("Distribución orcentual de las personas procesadas por el Organismo Judicial "
                + "por cometer hechos delictivos contemplados en el ramo penal,"
                + " con sentencia absolutoria, según delitos");
        seccion6.add(formatoTrimestre + "en porcentaje");
        seccion6.add("6_12.tex");       
        seccion6.add("INE, con datos del Organismo Judicial");
        seccion6.add(true);
        cap6.add(seccion6);
        
        
        
        return cap6;        
    }
    
    
    
        protected void apendices(String rutaTEX){
        escribirLinea("\n \\appendixa \n" +
        "\n" +
        "\n" +
        "\n" );
        apendice1();
        apendice2();
        apendice3();
        Tabla ap = new Tabla(rutaTEX,trimestres(),rr);
        ap.setRuta("/var/www/html/VIF/Entradas/CSV");
        ap.generarHechosDelictivos();
        
        
        
    }
    private void apendice1(){
        String columna1 = tablaApendice("A_01",
                "Análisis de variación de los detenidos",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos de la Policía Nacional Civil",
                "");
        
        String columna2 = tablaApendice("A_02",
                "Análisis de variación de las víctimas",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos de la Policía Nacional Civil",
                "");
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    private void apendice2(){
        String columna1 = tablaApendice("A_03",
                "Análisis de variación de los sindicados",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos del Ministerio Público",
                "");
        
        String columna2 = tablaApendice("A_04",
                "Análisis de variación de las evaluaciones médico legales",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos del Instituto Nacional de Ciencias Forenses",
                "");
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    private void apendice3(){
        String columna1 = tablaApendice("A_05",
                "Análisis de variación de las necropsias",
                "2",
                "plantillaTabla2.pdf",
                "INE, con datos del Instituo Nacional de Ciencias Forenses",
                "");
        
        String columna2 = "";
        escribirLinea(hojaTrimestral(columna1, columna2));
    }
    
    protected void generarGraficas(String modalidad){
        System.out.println("GENERANDO LAS GRAFICAS");
        Grafica vif = new Grafica("delictivos", getRuta(), rr.get(), modalidad);
        vif.start();
    }
    
    protected void hacerPortada(){
       File source = new File("/home/ineservidor/Delictivos/Caratula");
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
            + "\\node[inner sep =0, scale = 3.5, align = left] at (0.5,0.595) {\n" +
             "República de Guatemala \n" +
            "		\\\\\n" +
            getTitulo() + "\n" +
            "		\\\\\n" +
            corregirTrimestre( getTrimestre() ) +  " trimestre "  + getAnioPublicacion() + " };" +
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
                "	Karin Barrios\\\\\n" +
                "	Carla Villatoro\\\\\n" +
                "	Ricardo Loaiza\\\\[0.8cm]\n" +
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
                "\\indent El Instituto Nacional de Estadística -INE-, a través de la Unidad de "
                + "Estadísticas Socioculturales y de Seguridad, elabora la edición del "
                + " {\\Bold " + corregirTrimestre(getTrimestre()).toLowerCase() + " trimestre del "
                + getAnioPublicacion() +"} sobre {\\Bold Estadísticas de Hechos "
                + "Delictivos} que comprende el área de sguridad y justicia del país.\n" +
                "\n" +
                "El documento contiene la información de los hechos delictivos desde el momento "
                + "en que se comete (reconocimiento) por medio de la Policía Nacional Civil. "
                + "Luego la invetigación realizada por el Ministerio Público y el Instituto "
                + "Nacional de Ciencias Forenses. {\\Bold La información presentada es preliminar}"
                + " y será ajustada con el ingreso de los registros tardíos.\n"+
                "\n" +
                "El INE agradeceel apoyo brindado por las fuentes de información, esperando "
                + "que esta nueva edición sea de utilidad tanto a investigadores y público "
                + "en general como para la elaboración de programas, políticas, planes en "
                + "materia de seguridad para el país.\n"+
                "\n" +
                "\\thispagestyle{empty}\n" +
                "\n" +
                "\n" +
                "\\cleardoublepage");
    }
    
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

