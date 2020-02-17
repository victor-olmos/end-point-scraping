/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint_bd;

/**
 *
 * 
 */
public class Producto {
    String nombre;
    String descripcion;
    int idMarca;
    int idFuente;
    int idCategoria;
    int idSubCategoria;
    int valor;
    int stock;
    String urlImagen;
    
    public Producto(String nombre, int idMarca,int idFuente,int idCategoria, int valor, int stock,String urlImagen){
        this.nombre=nombre;
        this.descripcion=nombre;
        this.idMarca=idMarca;
        this.idFuente=idFuente;
        this.idCategoria=idCategoria;
        this.idSubCategoria=1;
        this.valor=valor;
        this.stock=stock;
        this.urlImagen=urlImagen;
    }
    
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public String getNombre(){
        return nombre;
    }
    
    public void setDescripcion(String descripcion){
        this.descripcion=descripcion;
    }
    public String getDescripcion(){
        return descripcion;
    }
    
    public void setMarca(int idMarca){
        this.idMarca=idMarca;
    }
    public int getMarca(){
        return idMarca;
    }
    
    public void setFuente(int idFuente){
        this.idFuente=idFuente;
    }
    public int getFuente(){
        return idFuente;
    }
    
    public void setCategoria(int idCategoria){
        this.idCategoria=idCategoria;
    }
    public int getCategoria(){
        return idCategoria;
    }
    
    public void setSubCategoria(int idSubCategoria){
        this.idSubCategoria=idSubCategoria;
    }
    public int getSubCategoria(){
        return idSubCategoria;
    }
    
    public void setValor(int valor){
        this.valor=valor;
    }
    public int getValor(){
        return valor;
    }
    
    public void setStock(int stock){
        this.stock=stock;
    }
    
    public int getStock(){
        return stock;
    }
    public void setUrlImagen(String urlImagen){
        this.urlImagen=urlImagen;
    }
    
    public String getUrlImagen(){
        return urlImagen;
    }
}
