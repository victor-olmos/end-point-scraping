/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint_bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * 
 */
public class ConexionBaiky {
    public int[] idCat;
    public String[] credencialesBD={"user","password"}; //credenciales BD: usuario - contraseña
    public String baikyUrl= "jdbc:mysql://111.11.111.111:3306/bd_name"; // jdbc:mysql://IP_SERVIDOR:PUERTO/NOMBRE_BD
    List<String> datos;
    Connection con;
    Statement stmt;
    PreparedStatement preparedStmt;
    PreparedStatement preparedStmtDetalle;
    PreparedStatement preparedStmtInsert;
        
    public Connection ConexionBaiky(){
        con=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=(Connection)DriverManager.getConnection(baikyUrl,credencialesBD[0],credencialesBD[1]);
            System.out.println("Conexión realizada a la base de datos con éxito.");
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Error!, conexión fallida a la base de datos.");
        }
        return con;
    }
    
    public void selectProducto(int id){
        ResultSet rs;
        try{
            con = ConexionBaiky();
            String query="SELECT * FROM PRODUCTO WHERE PRODUCTO_ID ="+id;
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()){
                System.out.println(rs.getInt("PRODUCTO_ID")+" "+ rs.getString("PRODUCTO_NOMBRE"));
            }
            //cierra la conexion a la bd
           con.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void insertarLista(ArrayList<Producto> productos){
        String queryCall="";
        try{
            con = ConexionBaiky();
            for(Producto prod : productos){
                queryCall="CALL insertarProducto("+prod.getMarca()+","+prod.getCategoria()+","+prod.getSubCategoria()+","+prod.getFuente()
                        +",'"+prod.getNombre()+"','"+prod.getDescripcion()+"',"+prod.getStock()+","+prod.getValor() +")";
                Statement st = con.createStatement();
                st.executeUpdate(queryCall);
            }
            System.out.println("Productos insertados correctamente en la base de datos.");
            cerrarConexion();
            
        }catch(Exception e){
            System.out.println("Ha ocurrido un error al intentar insertar los productos a la base de datos.");
            JOptionPane.showMessageDialog(null, e);
            System.out.println(queryCall);
        }
    }
    public void cerrarConexion(){
        try{
            System.out.println("Conexión a la base de datos concluida.");
            con.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void selectAllProducto(){
        Connection con=null;
        ResultSet rs;
        try{
            con = ConexionBaiky();
            String query="SELECT * FROM PRODUCTO";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while(rs.next()){
                System.out.println(rs.getInt("PRODUCTO_ID")+" "+ rs.getString("PRODUCTO_NOMBRE"));
            }
            //cierra la conexion a la bd
           con.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public int getIdProducto(String nombre){
        int id=0;
        ResultSet rs;
        try{
            con = ConexionBaiky();
            String query="SELECT PRODUCTO_ID FROM PRODUCTO WHERE PRODUCTO_NOMBRE =?";
            preparedStmt=con.prepareStatement(query);
            preparedStmt.setString(1, nombre);
            rs=preparedStmt.executeQuery();
            if(rs.next()){
                id=rs.getInt("PRODUCTO_ID");
            }
            //cierra la conexion a la bd
            
            //con.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }
}
