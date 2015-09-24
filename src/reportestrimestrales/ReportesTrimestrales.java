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
            rutaArchivoSubido = "/var/www/archivos/ipc_csv.csv";
            rutaIPC = "/home/ineservidor/IPC";
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

            Consultor.reescribirCSV(rutaArchivoSubido);
            try {
                Conector c = new Conector(rutaArchivoSubido, rutaDestinoCSV, ipcMes.getAbsolutePath(), args[1], args[2], args[4]);
                IPC docu;
                docu = new IPC("IPC", getMesCadena(Integer.parseInt(args[2])), args[1], rutaDestinoCSV);
                docu.setVarAnual(c.getVariacionAnual());
                docu.setVarMensual(c.getVariacionMensual());
                docu.setVarAcumulada(c.getVariacionAcumulada());
                System.out.println("**************************************");
                System.out.println(c.getVariacionAcumulada());
                System.out.println("***************************************");
                docu.setRuta(ipcMes.getAbsolutePath());
                docu.setTex("IPC" + docu.getMes());
                docu.hacerPortada();
                docu.preambuloAnual();
                docu.iniciarDocumentoAnual();
                docu.hacerTituloAnual();
                docu.juntaDirectivaAnual();
                docu.equipoYPresentacion();
                docu.capitulo1();
                docu.capitulo2();
                docu.capitulosRegionales();
                if (args[3].equalsIgnoreCase("true")){
                    docu.generarGraficas("anual");
                }
                docu.terminarDocumento();
            } catch (SQLException ex) {
                Logger.getLogger(ReportesTrimestrales.class.getName()).log(Level.SEVERE, null, ex);
            }


            
        }
        
     
      
  }
    
    public static String TASK_QUEUE_NAME = "ipc";
    public static void main(String[] args) throws Exception {
    
    
         Vitales docu;
         docu= new Vitales("Estadísticas Vitales", "Tercero", "2014","/mnt/Data/CSV_Vitales/CSV_Vitales");
         docu.setRuta("/mnt/Data/Vitales/");
         docu.setTex("vitalesTercero2015");
         docu.hacerPortada();
         docu.preambulo();
         docu.iniciarDocumento();
         docu.hacerTitulo();
         docu.juntaDirectiva();
         docu.equipoYPresentacion();
         docu.capitulo1();
         docu.capitulo2();
         docu.capitulo3();
         docu.capitulo4();
         docu.capitulo5();
         docu.terminarDocumento();
         docu.getRr().get().end();
         //docu.generarGraficas("presentacion");
         docu.compilar(docu.getRr(),"/mnt/Data/vitalesTercero2015.tex","T");
        
        
        
//    ConnectionFactory factory = new ConnectionFactory();
//    factory.setHost("localhost");
//    factory.setPassword("test");
//    factory.setUsername("test");
//    final Connection connection = factory.newConnection();
//    
//    final Channel channel = connection.createChannel();
//
//    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
//    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
//
//    channel.basicQos(1);
//
//    final Consumer consumer = new DefaultConsumer(channel) {
//      @Override
//      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//        String message = new String(body, "UTF-8");
//
//        System.out.println(" [x] Received '" + message + "'");
//        try {
//          doWork(message);
//        } catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//          System.out.println(" [x] Done");
//          channel.basicAck(envelope.getDeliveryTag(), false);
//        
//      }
//    };
//    channel.basicConsume(TASK_QUEUE_NAME, false, consumer);       
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
    
}
