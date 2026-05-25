/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
package vista;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para PanelColor")
class PanelColorTest {

    private PanelColor panel;

    @BeforeEach
    void setUp() {
        panel = new PanelColor();
    }

    @Test
    @DisplayName("Panel se crea con dimensión preferida de 500x200")
    void testDimensionPreferida() {
        assertEquals(500, panel.getPreferredSize().width);
        assertEquals(200, panel.getPreferredSize().height);
    }

    @Test
    @DisplayName("actualizarColor no lanza excepción con color válido")
    void testActualizarColor_colorValido() {
        assertDoesNotThrow(() -> panel.actualizarColor(Color.RED, "Rojo"));
    }

    @Test
    @DisplayName("actualizarColor no lanza excepción con nombre largo")
    void testActualizarColor_nombreLargo() {
        assertDoesNotThrow(() ->
                panel.actualizarColor(new Color(30, 185, 185), "Turquesa Brillante Especial"));
    }

    @Test
    @DisplayName("actualizarColor acepta color blanco")
    void testActualizarColor_colorBlanco() {
        assertDoesNotThrow(() -> panel.actualizarColor(Color.WHITE, "Blanco"));
    }

    @Test
    @DisplayName("actualizarColor acepta color negro")
    void testActualizarColor_colorNegro() {
        assertDoesNotThrow(() -> panel.actualizarColor(new Color(15, 15, 15), "Negro"));
    }

    @Test
    @DisplayName("Panel tiene borde definido (no nulo)")
    void testBordeNoNulo() {
        assertNotNull(panel.getBorder());
    }

    @Test
    @DisplayName("actualizarColor puede llamarse múltiples veces sin error")
    void testActualizarColor_multiplesCambios() {
        assertDoesNotThrow(() -> {
            panel.actualizarColor(Color.RED, "Rojo");
            panel.actualizarColor(Color.BLUE, "Azul");
            panel.actualizarColor(Color.GREEN, "Verde");
        });
    }
}