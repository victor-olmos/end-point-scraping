/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint_bd;

import com.opencsv.CSVReader;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

/**
 *
 */
public class Funciones {
    ArrayList<Producto> listaProductos=new ArrayList<>();
    ConexionBaiky cn = new ConexionBaiky();
    String urlCSV="https://www.yyy.cl/api/endpoint?token=kzupi5pmfyc43tnkxdzvwkmc5zrsvkfolcerjfxjf68wxmvonj";
    
    public void descarga(){
        String dirName = "C:\\Users\\user_name\\Desktop\\pryts BS\\Endpoint_BD";
        System.out.println("Inicio de la descarga del archivo CSV.");
        try {
            saveFileFromUrlWithJavaIO(
                dirName + "\\archivo.csv", urlCSV);
            System.out.println("Descarga finalizada.");
            lecturaCSV("archivo.csv");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void lecturaCSV(String csv) throws IOException{
        CSVReader lector= null;
        char separador=';';
        char comillas='"';
        try {
            // Abrir el .csv
            lector = new CSVReader(new FileReader(csv),separador,comillas);
            // Definir el string de la línea leída
            String[] linea;
            int i =1;
            while ((linea = lector.readNext()) != null) {
                /*
                 *linea[1]->stock | linea[2]-> precio_mkr | linea[3]-> precio sugerido
                 *linea[5]->nombre | linea[6]->marca |linea[7]->categoria | linea[9]-> descrip | linea[10]-> foto 
                 */
                System.out.println(linea[5]);
                System.out.println(linea[6]);
                System.out.println(linea[2]);
                System.out.println(linea[1]); 
                System.out.println(linea[10]);
                insertarProducto(linea, i);
                i++;
            }
            insertarBaseDatos();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            // Cierro el buffer de lectura
            if (lector != null) {
                try {
                lector.close();
                    System.out.println("Lectura e inserción completada.");
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void insertarProducto(String[] arreglo, int i) throws IOException{
        if(i == 1){
            System.out.println("Inicio llenado de arreglo de productos");
        }else{
            int idMarca=getIdMarca(arreglo[6].replace(" ", "").toLowerCase());
            String costo=arreglo[2].replace("$", "").replace(".", "").replace(" ","");
            int formula = (int) Math.round(Integer.parseInt(costo)*(0.7*0.94));
            Producto producto = new Producto(arreglo[5].replace("\'", " ").replace("\"", ""),idMarca,3,getIdCategoria(arreglo[7].replace(" ", "").toLowerCase()),formula,Integer.parseInt(arreglo[1]),arreglo[10]);    
            listaProductos.add(producto);
        }
    }
    
    public void insertarBaseDatos() {
        try{
            System.out.println("Inicia la inserción de la lista de productos a la base de datos.");
            cn.insertarLista(listaProductos);
            listaProductos.clear();
        }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    private int getIdMarca(String string) {
        int idMarca=0;
        
        switch(string){
            case "phoenix":
                idMarca=28;
                break;
            case "senzo":
                idMarca=29;
                break;
            case "tsghelmets":
                idMarca=30;
                break;
            case "catlike":
                idMarca=31;
                break;
        }
        return idMarca;
    }
    
    private int getIdCategoria(String string) {
        int idCategoria=0;
        switch(string){
            case "bicicletas":
                idCategoria=1;
                break;
            case "accesorios":
                idCategoria=2;
                break;
            case "ropaciclismo":
                idCategoria=3;
                break;
            case "componentes":
                idCategoria=4;
                break;
        }
        return idCategoria;
    }
    
    public static void saveFileFromUrlWithJavaIO(String fileName, String fileUrl)
    throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try { in = new BufferedInputStream(new URL(fileUrl).openStream());
            fout = new FileOutputStream(fileName);
            byte data[] = new byte[1024];
            int count;
            while ((count = in .read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if ( in != null)
                in .close();
            if (fout != null)
                fout.close();
        }
    }
    // Using Commons IO library
    // Available at http://commons.apache.org/io/download_io.cgi
    public static void saveFileFromUrlWithCommonsIO(String fileName,
        String fileUrl) throws MalformedURLException, IOException {
        FileUtils.copyURLToFile(new URL(fileUrl), new File(fileName));
    }
}
