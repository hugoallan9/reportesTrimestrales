/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportestrimestrales;
import com.rabbitmq.client.*;
import com.rabbitmq.client.ConnectionFactory;
import consultor.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
/**
 *
 * @author INE
 */ 
public class ReportesTrimestrales {
    
    
    
    private static String rutaIPC, rutaDestinoCSV, rutaArchivoSubido;
    /**
     * @param args the command line arguments
     */
    
    private static void doWork(String task) {  
      
        
        System.out.println("XXXXXXXXXXX");
 
          System.out.println("O/");
             String[] args = task.split(",");
        for(int i = 0;i < args.length;i++){
            System.out.println(args[i]);
        }
        System.out.println("el mensajito.");
        if ( args[0].equalsIgnoreCase("ipc") ){
            rutaArchivoSubido = "/var/www/archivos/CSV/IPC POR GASTO BASICO.csv";
            rutaIPC = "/home/ineservidor/IPC";
            
            /*
            21/10/2015 Codigo para el archivo xlsx
            */
            SesionR r = new SesionR();
            r.get().eval("library(funcionesINE)");
            r.get().eval("library(xlsx)");
            System.out.println(r.get().eval("ipc <- leerLibro('/var/www/archivos/ipc_csv.xlsx')"));
            System.out.println(r.get().eval("ipc <- convertirFechasIPC(ipc)"));
            //cambié vitales por ipc en la siguiente instruccion. creé la carpeta CSV
            r.get().eval("escribirCSV(ipc, '/var/www/archivos/CSV')");
            //r.get().end();
                    
            //termina lo del xlsx
            
            File ipcMes = new File(rutaIPC, getMesCadena(Integer.parseInt(args[2])) + args[1]);
            if ( !ipcMes.exists() ){
                ipcMes.setReadable(true, false);
                ipcMes.setExecutable(true, false);
                ipcMes.setWritable(true, false);
                ipcMes.mkdir();
            }
            File f = new File(ipcMes, "CSV");
            if( !f.exists() ){
                System.out.println("La carpeta no existe: " + f.getAbsolutePath());
                f.setReadable(true, false);
                f.setExecutable(true, false);
                f.setWritable(true, false);
                f.mkdir();
            }

            File f1 = new File(f, "tablas");
            if( !f1.exists() ){
                System.out.println("La carpeta no existe: " + f1.getAbsolutePath());
                f1.mkdir();
                f.setReadable(true, false);
                f.setExecutable(true, false);
                f.setWritable(true, false);
            }
            rutaDestinoCSV = f.getAbsolutePath();
            //Consultor.reescribirCSV(rutaArchivoSubido);
            try {
                System.out.println("Entro al try de la base");
                Conector c = new Conector(rutaArchivoSubido, rutaDestinoCSV, ipcMes.getAbsolutePath(), args[1], args[2]);
                c.getVariacionAnual();
                
            
            File source = new File("/var/www/archivos/CSV");
            try {
                FileUtils.copyDirectory(source, f);
            } catch (IOException e) {
                e.printStackTrace();
            }    
            IPC docu;
            docu = new IPC("IPC", getMesCadena(Integer.parseInt(args[2])), args[1], rutaDestinoCSV);
            docu.setMes(getMesCadena(Integer.parseInt(args[2])));
            docu.setYear(args[1]);
            docu.setRuta(ipcMes.getAbsolutePath());
            docu.setTex("IPC" + docu.getMes());
            docu.preambuloPresentacion();
            docu.hacerPortada();
            docu.preambuloAnual();
            docu.iniciarDocumentoAnual();
            docu.hacerTituloAnual();
            docu.portadaAzul();
            docu.juntaDirectivaAnual();
            docu.equipoYPresentacion(c.getVariacionAnual(), c.getVariacionMensual(),c.getVariacionAcumulada());
            docu.capitulo1();
            docu.capitulo2();
            docu.capitulosRegionales();
            docu.generarGraficas("anual");
            docu.terminarDocumentoApendice();
            } catch (SQLException ex) {
                Logger.getLogger(ReportesTrimestrales.class.getName()).log(Level.SEVERE, null, ex);
            }

   
        }//ipc
        else if ( args[0].equalsIgnoreCase("vitales") ){
            String rutaVitales = "/home/ineservidor/Vitales";
            SesionR r = new SesionR();
            r.get().eval("library(funcionesINE)");
            r.get().eval("library(xlsx)");
            System.out.println(r.get().eval("vitales <- leerLibro('/var/www/html/Vitales/Entradas/vitales.xlsx')"));
            System.out.println(r.get().eval("vitales <- convertirFechas(vitales)"));
            r.get().eval("escribirCSV(vitales, '/var/www/html/Vitales/Entradas/CSV')");
            File vitalesTrimestre = new File(rutaVitales, getTrimestreCadena(Integer.parseInt(args[2])) + args[1]);
            if ( !vitalesTrimestre.exists() ){
                vitalesTrimestre.setReadable(true, false);
                vitalesTrimestre.setExecutable(true, false);
                vitalesTrimestre.setWritable(true, false);
                vitalesTrimestre.mkdir();
            }
            //System.out.println("Arg 3: " + args[3]);
            Vitales docu;
            docu= new Vitales("Estadísticas Vitales", getTrimestreCadena(Integer.parseInt(args[2])), args[1],"/var/www/html/Vitales/Entradas/CSV");
            docu.setRuta(vitalesTrimestre.getAbsolutePath()+"/");
            docu.setTex("vitales");
            docu.hacerPortada();
            docu.preambulo();
            docu.preambuloPresentacion();
            docu.iniciarDocumento();
            docu.hacerTitulo();
            docu.juntaDirectiva();
            docu.equipoYPresentacion();
            docu.capitulo1();
            /*
            docu.capitulo2();
            docu.capitulo3();
            docu.capitulo4();
            docu.capitulo5();
*/
            docu.apendices(vitalesTrimestre.getAbsolutePath()+"/");
            docu.terminarDocumentoApendice();
            //docu.getRr().get().end();
            //if (args[3].equalsIgnoreCase("true")){
                System.out.println("entro a hacer graficas");
                docu.generarGraficas("trimestral");
            //}
        }
        
        else if ( args[0].equalsIgnoreCase("faltas") ){
            String rutaFaltas = "/home/ineservidor/FaltasJudiciales";
            SesionR r = new SesionR();
            r.get().eval("library(funcionesINE)");
            r.get().eval("library(xlsx)");
            System.out.println(r.get().eval("faltas <- leerLibro('/var/www/html/FaltasJudiciales/Entradas/faltasJudiciales.xlsx')"));
            System.out.println(r.get().eval("faltas <- convertirFechasTodos(faltas)"));
            r.get().eval("escribirCSV(faltas, '/var/www/html/FaltasJudiciales/Entradas/CSV')");
            File faltasTrimestre = new File(rutaFaltas, args[2] + args[1]);
            if ( !faltasTrimestre.exists() ){
                faltasTrimestre.setReadable(true, false);
                faltasTrimestre.setExecutable(true, false);
                faltasTrimestre.setWritable(true, false);
                faltasTrimestre.mkdir();
            }
            FaltasJudiciales docu;
            docu= new FaltasJudiciales("Faltas Judiciales", args[2], args[1],"/var/www/html/FaltasJudiciales/Entradas/CSV");
            docu.setRuta(faltasTrimestre.getAbsolutePath()+"/");
            docu.setTex("faltasJudiciales");
            docu.hacerPortada();
            docu.preambulo();
            docu.preambuloPresentacion();
            docu.iniciarDocumento();
            docu.hacerTitulo();
            docu.juntaDirectiva();
            docu.equipoYPresentacion();
            docu.rellenar();
            System.out.println("Antes ");
            descripciones_faltas_judiciales.Generador descripciones = new descripciones_faltas_judiciales.Generador("/var/www/html/FaltasJudiciales/Entradas/CSV", faltasTrimestre.getAbsolutePath());
            descripciones.run();
            System.out.println("Despues");
            //docu.apendices(faltasTrimestre.getAbsolutePath()+"/");
            docu.terminarDocumento();
            docu.getRr().get().end();
            
            
            //if (args[3].equalsIgnoreCase("true")){
                System.out.println("entro a hacer graficas de faltas");
                docu.generarGraficas("trimestral");
            //}
        }


       
        else if ( args[0].equalsIgnoreCase("hospitalarias") ){
            String rutaHospitalarias = "/home/ineservidor/Hospitalarias";
            SesionR r = new SesionR();
            r.get().eval("library(funcionesINE)");
            r.get().eval("library(xlsx)");
            System.out.println(r.get().eval("hospitalarias <- leerLibro('/var/www/html/Hospitalarias/Entradas/hospitalarias.xlsx')"));
            System.out.println(r.get().eval("hospitalarias <- convertirFechas(hospitalarias)"));
            r.get().eval("escribirCSV(hospitalarias, '/var/www/html/Hospitalarias/Entradas/CSV')");
            File hospitalarias = new File(rutaHospitalarias, args[2] + args[1]);
            if ( !hospitalarias.exists() ){
                hospitalarias.setReadable(true, false);
                hospitalarias.setExecutable(true, false);
                hospitalarias.setWritable(true, false);
                hospitalarias.mkdir();
            }

            Hospitalarias docu;
            docu= new Hospitalarias("Estadísticas Hospitalarias",args[2],args[1],"/var/www/html/Hospitalarias/Entradas/CSV");
            docu.setRuta(hospitalarias.getAbsolutePath()+"/");
            docu.setTex("hospitalarias");
            docu.hacerPortada();
            docu.preambulo();
            docu.preambuloPresentacion();
            docu.iniciarDocumento();
            docu.hacerTitulo();
            docu.juntaDirectiva();
            docu.equipoYPresentacion();
            docu.rellenar();
            docu.apendices(hospitalarias.getAbsolutePath()+"/");
            docu.terminarDocumento();
            docu.getRr().get().end();
            
            System.out.println("Antes ");
            descriptorhospitalarias.Generador descripciones = new descriptorhospitalarias.Generador("/var/www/html/Hospitalarias/Entradas/CSV", hospitalarias.getAbsolutePath(), args[2], Integer.parseInt(args[1]));
            descripciones.run();
            System.out.println("Despues");
            
            //if (args[3].equalsIgnoreCase("true")){
                System.out.println("entro a hacer graficas");
                docu.generarGraficas("trimestral");
            //}
        }
        else if ( args[0].equalsIgnoreCase("comercio") ){
            String rutaComercio = "/home/ineservidor/Comercio";
            SesionR r = new SesionR();
            r.get().eval("library(funcionesINE)");
            r.get().eval("library(xlsx)");
            System.out.println(r.get().eval("comercio <- leerLibro('/var/www/html/Comercio/Entradas/comercio.xlsx')"));
            System.out.println(r.get().eval("comercio <- convertirFechasTodos(comercio)"));
            r.get().eval("escribirCSV(comercio, '/var/www/html/Comercio/Entradas/CSV')");
            File comercioTrimestre = new File(rutaComercio, args[2] + args[1]);
            if ( !comercioTrimestre.exists() ){
                comercioTrimestre.setReadable(true, false);
                comercioTrimestre.setExecutable(true, false);
                comercioTrimestre.setWritable(true, false);
                comercioTrimestre.mkdir();
            }
            System.out.println("Antes de Entrar al constructor");
            ComercioExterior docu;
            docu= new ComercioExterior("Estadísticas de Comercio Exterior", args[2], args[1],"/var/www/html/Comercio/Entradas/CSV");
            System.out.println("Sali del constructor");
            docu.setRuta(comercioTrimestre.getAbsolutePath()+"/");
            docu.setTex("comercio");
            docu.hacerPortada();
            docu.preambulo();
            docu.preambuloPresentacion();
            docu.iniciarDocumento();
            docu.hacerTitulo();
            docu.juntaDirectiva();
            docu.equipoYPresentacion();
            docu.rellenar();
            //docu.apendices(comercioTrimestre.getAbsolutePath()+"/");
            docu.terminarDocumento();
            docu.getRr().get().end();
            
            System.out.println("Antes ");
            descriptorcomercio.Generador descripciones = new descriptorcomercio.Generador("/var/www/html/Comercio/Entradas/CSV", comercioTrimestre.getAbsolutePath(), args[2], Integer.parseInt(args[1]));
            descripciones.run();
            System.out.println("Despues");
            
            //if (args[3].equalsIgnoreCase("true")){
                System.out.println("entro a hacer graficas de comercio");
                docu.generarGraficas("trimestral");
            //}
        }
        else if ( args[0].equalsIgnoreCase("transportes") ){
            String rutaTransporte = "/home/ineservidor/Transportes";
            SesionR r = new SesionR();
            r.get().eval("library(funcionesINE)");
            r.get().eval("library(xlsx)");
            System.out.println(r.get().eval("transporte <- leerLibro('/var/www/html/Transportes/Entradas/transportes.xlsx')"));
            System.out.println(r.get().eval("transporte <- convertirFechasTransporte(transporte)"));
            r.get().eval("escribirCSV(transporte, '/var/www/html/Transportes/Entradas/CSV')");
            File transporteTrimestre = new File(rutaTransporte, args[2] + args[1]);
            if ( !transporteTrimestre.exists() ){
                transporteTrimestre.setReadable(true, false);
                transporteTrimestre.setExecutable(true, false);
                transporteTrimestre.setWritable(true, false);
                transporteTrimestre.mkdir();
            }
            TransporteYServicios docu;
            docu= new TransporteYServicios("Transportes y Servicios", args[2], args[1],"/var/www/html/Transportes/Entradas/CSV");
            docu.setRuta(transporteTrimestre.getAbsolutePath()+"/");
            docu.setTex("transporte");
            docu.hacerPortada();
            docu.preambulo();
            docu.preambuloPresentacion();
            docu.iniciarDocumento();
            docu.hacerTitulo();
            docu.juntaDirectiva();
            docu.equipoYPresentacion();
            docu.rellenar();
            descriptortransporte.Generador descripciones = new descriptortransporte.Generador("/var/www/html/Transportes/Entradas/CSV", transporteTrimestre.getAbsolutePath(), args[2], Integer.parseInt(args[1]));
            descripciones.run();
            docu.apendices(transporteTrimestre.getAbsolutePath()+"/");
            docu.terminarDocumento();
            //docu.getRr().get().end();
            //if (args[3].equalsIgnoreCase("true")){
                System.out.println("entro a hacer graficas");
                docu.generarGraficas("trimestral");
            //}
        }
        else if ( args[0].equalsIgnoreCase("agropecuarias") ){
            String rutaAgropecuarias = "/home/ineservidor/Agropecuarias";
            SesionR r = new SesionR();
            r.get().eval("library(funcionesINE)");
            r.get().eval("library(xlsx)");
            System.out.println(r.get().eval("agropecuarias <- leerLibro('/var/www/html/Agropecuarias/Entradas/agropecuarias.xlsx')"));
            System.out.println(r.get().eval("agropecuarias <- convertirFechasTodos(agropecuarias)"));
            r.get().eval("escribirCSV(agropecuarias, '/var/www/html/Agropecuarias/Entradas/CSV')");
            File agropecuariasTrimestre = new File(rutaAgropecuarias, args[2] + args[1]);
            if ( !agropecuariasTrimestre.exists() ){
                agropecuariasTrimestre.setReadable(true, false);
                agropecuariasTrimestre.setExecutable(true, false);
                agropecuariasTrimestre.setWritable(true, false);
                agropecuariasTrimestre.mkdir();
            }
            Agropecuarias docu;
            docu= new Agropecuarias("Estadísticas Agropecuarias", args[2], args[1],"/var/www/html/Agropecuarias/Entradas/CSV");
            docu.setRuta(agropecuariasTrimestre.getAbsolutePath()+"/");
            docu.setTex("agropecuarias");
            docu.hacerPortada();
            docu.preambulo();
            docu.preambuloPresentacion();
            docu.iniciarDocumento();
            docu.hacerTitulo();
            docu.juntaDirectiva();
            docu.equipoYPresentacion();
            docu.rellenar();
            docu.apendices(agropecuariasTrimestre.getAbsolutePath()+"/");
            docu.terminarDocumento();
            //descripciones
            descriptoragropecuarias.Generador descripciones = new descriptoragropecuarias.Generador("/var/www/html/Agropecuarias/Entradas/CSV", agropecuariasTrimestre.getAbsolutePath(), args[2], Integer.parseInt(args[1]));
            descripciones.run();
            //docu.getRr().get().end();
            //if (args[3].equalsIgnoreCase("true")){
                System.out.println("entro a hacer graficas");
                docu.generarGraficas("trimestral");
            //}
        }
        else if ( args[0].equalsIgnoreCase("violencia") ){
            System.err.println("Generando reporte de Violencia Intrafamiliar");
            String rutaViolencia = "/home/ineservidor/Violencia";
            SesionR r = new SesionR();
            r.get().eval("library(funcionesINE)");
            r.get().eval("library(xlsx)");
            System.out.println(r.get().eval("violencia <- leerLibro('/var/www/html/Violencia/Entradas/violencia.xlsx')"));
            System.out.println(r.get().eval("violencia <- convertirFechasTodos(violencia)"));
            r.get().eval("escribirCSV(violencia, '/var/www/html/Violencia/Entradas/CSV')");
            File violenciaTrimestre = new File(rutaViolencia, args[2] + args[1]);
            if ( !violenciaTrimestre.exists() ){
                violenciaTrimestre.setReadable(true, false);
                violenciaTrimestre.setExecutable(true, false);
                violenciaTrimestre.setWritable(true, false);
                violenciaTrimestre.mkdir();
            }
            VIF docu;
            docu= new VIF("Estadísticas de Violencia Intrafamiliar", args[2], args[1],"/var/www/html/Violencia/Entradas/CSV");
            docu.setRuta(violenciaTrimestre.getAbsolutePath()+"/");
            docu.setTex("vif");
            docu.hacerPortada();
            docu.preambulo();
            docu.preambuloPresentacion();
            docu.iniciarDocumento();
            docu.hacerTitulo();
            docu.juntaDirectiva();
            docu.equipoYPresentacion();
            docu.rellenar();
            //descripciones
            descriptorviolencia.Generador descripciones = new descriptorviolencia.Generador("/var/www/html/Violencia/Entradas/CSV", violenciaTrimestre.getAbsolutePath(), args[2], Integer.parseInt(args[1]));
            descripciones.run();
            
            docu.apendices(violenciaTrimestre.getAbsolutePath()+"/");
            docu.terminarDocumento();
            
            
            //docu.getRr().get().end();
            //if (args[3].equalsIgnoreCase("true")){
                System.out.println("entro a hacer graficas");
                docu.generarGraficas("trimestral");
            //}
        }
        else if ( args[0].equalsIgnoreCase("transito") ){
            String rutaTransito = "/home/ineservidor/Transito";
            SesionR r = new SesionR();
            r.get().eval("library(funcionesINE)");
            r.get().eval("library(xlsx)");
            System.out.println(r.get().eval("transito <- leerLibro('/var/www/html/Transito/Entradas/transito.xlsx')"));
            System.out.println(r.get().eval("transito <- convertirFechasTodos(transito)"));
            r.get().eval("escribirCSV(transito, '/var/www/html/Transito/Entradas/CSV')");
            File transitoTrimestre = new File(rutaTransito, args[2] + args[1]);
            if ( !transitoTrimestre.exists() ){
                transitoTrimestre.setReadable(true, false);
                transitoTrimestre.setExecutable(true, false);
                transitoTrimestre.setWritable(true, false);
                transitoTrimestre.mkdir();
            }
            Transito docu;
            docu= new Transito("Estadísticas de Accidentes de Tránsito", args[2], args[1],"/var/www/html/Transito/Entradas/CSV");
            docu.setRuta(transitoTrimestre.getAbsolutePath()+"/");
            docu.setTex("transito");
            docu.hacerPortada();
            docu.preambulo();
            docu.preambuloPresentacion();
            docu.iniciarDocumento();
            docu.hacerTitulo();
            docu.juntaDirectiva();
            docu.equipoYPresentacion();
            docu.rellenar();
            docu.apendices(transitoTrimestre.getAbsolutePath()+"/");
            docu.terminarDocumento();
            //descripciones
            descriptortransito.Generador descripciones = new descriptortransito.Generador("/var/www/html/Transito/Entradas/CSV", transitoTrimestre.getAbsolutePath(), args[2], Integer.parseInt(args[1]));
            descripciones.run();
            //docu.getRr().get().end();
            //if (args[3].equalsIgnoreCase("true")){
                System.out.println("entro a hacer graficas jojo ");
                docu.generarGraficas("trimestral");
            //}
        }
     else if ( args[0].equalsIgnoreCase("delictivos") ){
            String rutaDelictivos = "/home/ineservidor/Delictivos";
            SesionR r = new SesionR();
            r.get().eval("library(funcionesINE)");
            r.get().eval("library(xlsx)");
            System.out.println(r.get().eval("delictivos <- leerLibro('/var/www/html/Delictivos/Entradas/delictivos.xlsx')"));
            System.out.println(r.get().eval("delictivos <- convertirFechasTodos(delictivos)"));
            r.get().eval("escribirCSV(delictivos, '/var/www/html/Delictivos/Entradas/CSV')");
            File delictivosTrimestre = new File(rutaDelictivos, args[2] + args[1]);
            if ( !delictivosTrimestre.exists() ){
                delictivosTrimestre.setReadable(true, false);
                delictivosTrimestre.setExecutable(true, false);
                delictivosTrimestre.setWritable(true, false);
                delictivosTrimestre.mkdir();
            }
            HechosDelictivos docu;
            docu= new HechosDelictivos("Estadísticas de Hechos Delictivos", args[2], args[1],"/var/www/html/Delictivos/Entradas/CSV");
            docu.setRuta(delictivosTrimestre.getAbsolutePath()+"/");
            docu.setTex("delictivos");
            docu.hacerPortada();
            docu.preambulo();
            docu.preambuloPresentacion();
            docu.iniciarDocumento();
            docu.hacerTitulo();
            docu.juntaDirectiva();
            docu.equipoYPresentacion();
            docu.rellenar();
            //docu.apendices(delictivosTrimestre.getAbsolutePath()+"/");
            docu.terminarDocumento();
            //descripciones
            descriptordelictivos.Generador descripciones = new descriptordelictivos.Generador("/var/www/html/Delictivos/Entradas/CSV", delictivosTrimestre.getAbsolutePath(), args[2], Integer.parseInt(args[1]));
            descripciones.run();
            //docu.getRr().get().end();
            //if (args[3].equalsIgnoreCase("true")){
                System.out.println("entro a hacer graficas de hechos");
                docu.generarGraficas("trimestral");
            //}
        }
        
        else if ( args[0].equalsIgnoreCase("canasta") ){
            String rutaHospitalarias = "/home/ineservidor/Canasta";
            SesionR r = new SesionR();
            r.get().eval("library(funcionesINE)");
            r.get().eval("library(xlsx)");
            File hospitalarias = new File(rutaHospitalarias, args[2] + args[1]);
            if ( !hospitalarias.exists() ){
                hospitalarias.setReadable(true, false);
                hospitalarias.setExecutable(true, false);
                hospitalarias.setWritable(true, false);
                hospitalarias.mkdir();
            }

            Canasta docu;
            docu= new Canasta("Canasta Básica",args[2],args[1],"/var/www/html/Canasta/Entradas/CSV");
            docu.setRuta(hospitalarias.getAbsolutePath()+"/");
            docu.setTex("canasta");
            docu.hacerPortada();
            docu.preambulo();
            docu.preambuloPresentacion();
            docu.iniciarDocumento();
            docu.hacerTitulo();
            docu.juntaDirectiva();
            docu.equipoYPresentacion();
            docu.rellenar();
            docu.apendices(hospitalarias.getAbsolutePath()+"/");
            docu.terminarDocumento();
            docu.getRr().get().end();
            
            
            //if (args[3].equalsIgnoreCase("true")){
                System.out.println("entro a hacer graficas");
                docu.generarGraficas("trimestral");
            //}
        }
      
  }
    
    public static String TASK_QUEUE_NAME = "ipc";
    
    public static void main(String[] args) throws Exception {
            
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    factory.setPassword("test");
    factory.setUsername("test");
    final Connection connection = factory.newConnection();
    
    final Channel channel = connection.createChannel();

    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    channel.basicQos(1);

    final Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");

        System.out.println(" [x] Received '" + message + "'");
        try {
          doWork(message);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
          System.out.println(" [x] Done");
          channel.basicAck(envelope.getDeliveryTag(), false);
        
      }
    };
    channel.basicConsume(TASK_QUEUE_NAME, false, consumer);       
    }
    
     private static String getMesCadena(int mes){
         if(mes == 1)return "Enero";
         else if(mes == 2)return "Febrero";
         else if(mes == 3)return "Marzo";
         else if(mes == 4)return "Abril";
         else if(mes == 5)return "Mayo";
         else if(mes == 6)return "Junio";
         else if(mes == 7)return "Julio";
         else if(mes == 8)return "Agosto";
         else if(mes == 9)return "Septiembre";
         else if(mes == 10)return "Octubre";
         else if(mes == 11)return "Noviembre";
         else if(mes == 12)return "Diciembre";
         return "";    
     }
     
     private static String getTrimestreCadena(int trimestre){
         System.out.println(trimestre);
         if(trimestre==1) return "Primer";
         else if(trimestre==2) return "Segundo";
         else if(trimestre==3) return "Tercer";
         else if(trimestre==4) return "Cuarto";
         return "";
     }
    
}
