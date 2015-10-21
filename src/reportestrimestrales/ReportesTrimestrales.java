/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportestrimestrales;
import com.rabbitmq.client.*;
import com.rabbitmq.client.ConnectionFactory;
import consultor.*;
import descripciones_faltas_judiciales.Generador;
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
            docu.setRuta(ipcMes.getAbsolutePath());
            docu.setTex("IPC" + docu.getMes());
            docu.hacerPortada();
            docu.preambuloAnual();
            docu.iniciarDocumentoAnual();
            docu.hacerTituloAnual();
            docu.juntaDirectivaAnual();
            docu.equipoYPresentacion(c.getVariacionAnual(), c.getVariacionMensual(),c.getVariacionAcumulada());
            //docu.capitulo1();
            docu.capitulo2();
            docu.capitulosRegionales();
            docu.generarGraficas("anual");
            docu.terminarDocumento();
            } catch (SQLException ex) {
                Logger.getLogger(ReportesTrimestrales.class.getName()).log(Level.SEVERE, null, ex);
            }

   
        }//ipc
        if ( args[0].equalsIgnoreCase("vitales") ){
            String rutaVitales = "/home/ineservidor/Vitales";
            SesionR r = new SesionR();
            r.get().eval("library(funcionesINE)");
            r.get().eval("library(xlsx)");
            System.out.println(r.get().eval("vitales <- leerLibro('/var/www/html/Vitales/Entradas/vitales.xlsx')"));
            System.out.println(r.get().eval("vitales <- convertirFechas(vitales)"));
            r.get().eval("escribirCSV(vitales, '/var/www/html/Vitales/Entradas/CSV')");
            r.get().end();
            File vitalesTrimestre = new File(rutaVitales, getTrimestreCadena(Integer.parseInt(args[2])) + args[1]);
            if ( !vitalesTrimestre.exists() ){
                vitalesTrimestre.setReadable(true, false);
                vitalesTrimestre.setExecutable(true, false);
                vitalesTrimestre.setWritable(true, false);
                vitalesTrimestre.mkdir();
            }
            System.out.println("Arg 3: " + args[3]);
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
            docu.capitulo2();
            docu.capitulo3();
            docu.capitulo4();
            docu.capitulo5();
            docu.apendices(vitalesTrimestre.getAbsolutePath()+"/");
            docu.terminarDocumento();
            docu.getRr().get().end();
            //if (args[3].equalsIgnoreCase("true")){
                System.out.println("entro a hacer graficas");
                docu.generarGraficas("trimestral");
            //}
        }
        
        if ( args[0].equalsIgnoreCase("faltas") ){
            String rutaFaltas = "/home/ineservidor/FaltasJudiciales";
            File faltasTrimestre = new File(rutaFaltas, getTrimestreCadena(Integer.parseInt(args[2])) + args[1]);
            if ( !faltasTrimestre.exists() ){
                faltasTrimestre.setReadable(true, false);
                faltasTrimestre.setExecutable(true, false);
                faltasTrimestre.setWritable(true, false);
                faltasTrimestre.mkdir();
            }
            System.out.println("Arg 3: " + args[3]);
            FaltasJudiciales docu;
            docu= new FaltasJudiciales("Faltas Judiciales", getTrimestreCadena(Integer.parseInt(args[2])), args[1],"/var/www/html/FaltasJudiciales/Entradas");
            docu.setRuta(faltasTrimestre.getAbsolutePath()+"/");
            docu.setTex("faltasJudiciales");
            docu.hacerPortada();
            docu.preambulo();
            docu.iniciarDocumento();
            docu.hacerTitulo();
            docu.juntaDirectiva();
            docu.equipoYPresentacion();
            docu.rellenar();
            docu.terminarDocumento();
            docu.getRr().get().end();
            Generador descripciones = new Generador("/var/www/html/FaltasJudiciales/Entradas", rutaFaltas);
            descripciones.run();
            
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
         if(trimestre==1) return "Primer";
         else if(trimestre==2) return "Segundo";
         else if(trimestre==3) return "Tercer";
         else if(trimestre==4) return "Cuarto";
         return "";
     }
    
}
