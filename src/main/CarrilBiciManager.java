package main;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 *@version 2.4.0
 *@author CadizTech
 *
 *Esta clase tiene como funcionalidad el control y gestión del carril bici
 *
 *Cuenta con dos mapas constantes que almacenan como valor la longitud de los tramos
 *y su estado respectivamente
 */
public class CarrilBiciManager {

    private final Map<String, Double> tramos; // nombre del tramo -> longitud en km
    private final Map<String, String> estadoTramos; // nombre del tramo -> estado

    /**
     *Objeto de la clase
     *
     *Al crearlo declara los mapas para su posterior uso
     */
    public CarrilBiciManager() {
        this.tramos = new HashMap<>();
        this.estadoTramos = new HashMap<>();
    }

    /**
     *En base a los parámetros introducidos crea un par Clave-Valor para los mapas tramos y estadoTramos
     *
     *@param longitud double // Km del tramo
     *@param nombre String // nombre del tramo
     *@throws IllegalArgumentException Sucede cuando el nombre se encuentra vacio ("") o cuando la longitud es menor que 0.
     */
    public void anadirTramo(String nombre, double longitud) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del tramo no puede estar vacío");
        }
        if (longitud <= 0) {
            throw new IllegalArgumentException("La longitud debe ser mayor que cero");
        }
        tramos.put(nombre, longitud);
        estadoTramos.put(nombre, "En servicio");
    }

    /**
     *Busca a que par clave-valor hace referencia el primer String y lo sustituye por el segundo
     *
     *@param nombre String // anterior nombre del tramo
     *@param nuevoEstado String // nuevo nombre del tramo
     *@throws NoSuchElementException Cuando no se encuentra el tramo
     */
    public void actualizarEstado(String nombre, String nuevoEstado) {
        if (!tramos.containsKey(nombre)) {
            throw new NoSuchElementException("El tramo indicado no existe: " + nombre);
        }
        estadoTramos.put(nombre, nuevoEstado);
    }

    /**
     *Llama al metodo actualizarEstado
     *
     *@param nombre String // clave
     *@param estado String // valor
     *
     *@deprecated Intención de sustituirlo por actualizarEstado
     */
    public void cambiarEstado(String nombre, String estado) {
        actualizarEstado(nombre, estado);
    }

    /**
     *Permite comprobar la existencia de un tramo
     *
     *@param nombre String // clave a buscar
     *@throws NoSuchElementException Cuando no se encuentra el tramo
     *
     *@return String
     */
    public String consultarEstado(String nombre) {
        if (!estadoTramos.containsKey(nombre)) {
            throw new NoSuchElementException("El tramo indicado no existe");
        }
        return estadoTramos.get(nombre);
    }

    /**
     *Suma todos los valores del mapa tramos y devuelve el resultado
     *
     *@return double
     */
    public double longitudTotal() {
        return tramos.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    /**
     *@return Mapa inalterable de los tramos
     */
    public Map<String, Double> obtenerTramos() {
        return Collections.unmodifiableMap(tramos);
    }

    /**
     *Utiliza un stream de Java8 para generar proceduralmento por consola
     *toda la informacion contenida en ambos mapas
     *
     *@return Cadena de caracteres en formato to.String()
     */
    public String generarInforme() {
        StringBuilder sb = new StringBuilder("INFORME DE CARRILES BICI - Bahía de Cádiz\n");
        sb.append("===========================================\n");
        for (String nombre : tramos.keySet()) {
            sb.append("- ").append(nombre).append(" (")
                    .append(tramos.get(nombre)).append(" km): ")
                    .append(estadoTramos.get(nombre)).append("\n");
        }
        sb.append("Longitud total: ").append(longitudTotal()).append(" km\n");
        return sb.toString();
    }
}
