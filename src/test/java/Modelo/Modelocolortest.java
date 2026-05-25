/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
package modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para ModeloColor")
class ModeloColorTest {

    @Test
    @DisplayName("Constructor asigna correctamente nombre, color y categoría")
    void testConstructorAsignaCampos() {
        Color color = new Color(220, 50, 50);
        ModeloColor modelo = new ModeloColor("Rojo", color, "Primarios");

        assertEquals("Rojo", modelo.getNombre());
        assertEquals(color, modelo.getColor());
        assertEquals("Primarios", modelo.getCategoria());
    }

    @Test
    @DisplayName("getNombre retorna el nombre correcto")
    void testGetNombre() {
        ModeloColor modelo = new ModeloColor("Azul", new Color(50, 80, 200), "Primarios");
        assertEquals("Azul", modelo.getNombre());
    }

    @Test
    @DisplayName("getColor retorna el objeto Color correcto")
    void testGetColor() {
        Color esperado = new Color(255, 210, 0);
        ModeloColor modelo = new ModeloColor("Amarillo", esperado, "Primarios");
        assertEquals(esperado, modelo.getColor());
    }

    @Test
    @DisplayName("getCategoria retorna la categoría correcta")
    void testGetCategoria() {
        ModeloColor modelo = new ModeloColor("Verde", new Color(50, 170, 50), "Secundarios");
        assertEquals("Secundarios", modelo.getCategoria());
    }

    @Test
    @DisplayName("toString retorna formato 'nombre [categoria]'")
    void testToString() {
        ModeloColor modelo = new ModeloColor("Morado", new Color(140, 50, 180), "Secundarios");
        assertEquals("Morado [Secundarios]", modelo.toString());
    }

    @Test
    @DisplayName("Modelo acepta color blanco (255, 255, 255)")
    void testColorBlanco() {
        Color blanco = new Color(255, 255, 255);
        ModeloColor modelo = new ModeloColor("Blanco", blanco, "Neutros");
        assertEquals(255, modelo.getColor().getRed());
        assertEquals(255, modelo.getColor().getGreen());
        assertEquals(255, modelo.getColor().getBlue());
    }

    @Test
    @DisplayName("Modelo acepta color negro (15, 15, 15)")
    void testColorNegro() {
        Color negro = new Color(15, 15, 15);
        ModeloColor modelo = new ModeloColor("Negro", negro, "Neutros");
        assertEquals(15, modelo.getColor().getRed());
        assertEquals(15, modelo.getColor().getGreen());
        assertEquals(15, modelo.getColor().getBlue());
    }

    @Test
    @DisplayName("Modelos con misma categoría son independientes entre sí")
    void testIndependenciaEntreModelos() {
        ModeloColor m1 = new ModeloColor("Rojo", new Color(220, 50, 50), "Primarios");
        ModeloColor m2 = new ModeloColor("Azul", new Color(50, 80, 200), "Primarios");

        assertNotEquals(m1.getNombre(), m2.getNombre());
        assertNotEquals(m1.getColor(), m2.getColor());
        assertEquals(m1.getCategoria(), m2.getCategoria());
    }
}
