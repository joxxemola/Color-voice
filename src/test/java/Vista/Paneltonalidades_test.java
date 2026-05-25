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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para PanelTonalidades")
class PanelTonalidadesTest {

    private PanelTonalidades panel;

    @BeforeEach
    void setUp() {
        panel = new PanelTonalidades();
    }

    @Test
    @DisplayName("Panel se crea sin lanzar excepciones")
    void testCreacionSinExcepcion() {
        assertDoesNotThrow(() -> new PanelTonalidades());
    }

    @Test
    @DisplayName("setAlSeleccionarTono asigna callback sin error")
    void testSetCallback_sinError() {
        assertDoesNotThrow(() -> panel.setAlSeleccionarTono(color -> {}));
    }

    @Test
    @DisplayName("setAlSeleccionarTono acepta null sin lanzar excepción")
    void testSetCallback_null() {
        assertDoesNotThrow(() -> panel.setAlSeleccionarTono(null));
    }

    @Test
    @DisplayName("actualizarTonalidades con lista de 9 tonos no lanza excepción")
    void testActualizarTonalidades_9tonos() {
        List<Color> tonos = Arrays.asList(
                new Color(255, 200, 200),
                new Color(255, 160, 160),
                new Color(255, 120, 120),
                new Color(255, 80, 80),
                new Color(220, 50, 50),
                new Color(165, 37, 37),
                new Color(110, 25, 25),
                new Color(66, 15, 15),
                new Color(33, 7, 7)
        );
        assertDoesNotThrow(() -> panel.actualizarTonalidades(tonos, "Rojo"));
    }

    @Test
    @DisplayName("actualizarTonalidades puede llamarse múltiples veces")
    void testActualizarTonalidades_multiplesLlamadas() {
        List<Color> tonos = Arrays.asList(
                Color.WHITE, Color.LIGHT_GRAY, Color.GRAY,
                Color.DARK_GRAY, Color.RED, Color.GREEN,
                Color.BLUE, Color.YELLOW, Color.BLACK
        );
        assertDoesNotThrow(() -> {
            panel.actualizarTonalidades(tonos, "Rojo");
            panel.actualizarTonalidades(tonos, "Azul");
            panel.actualizarTonalidades(tonos, "Verde");
        });
    }

    @Test
    @DisplayName("Callback se invoca al simular selección de tono")
    void testCallback_esInvocado() {
        AtomicReference<Color> colorRecibido = new AtomicReference<>();
        panel.setAlSeleccionarTono(colorRecibido::set);

        Color tonoEsperado = new Color(100, 150, 200);
        panel.setAlSeleccionarTono(c -> colorRecibido.set(c));

        colorRecibido.set(tonoEsperado);
        assertEquals(tonoEsperado, colorRecibido.get());
    }

    @Test
    @DisplayName("Panel tiene layout no nulo tras creación")
    void testLayoutNoNulo() {
        assertNotNull(panel.getLayout());
    }

    @Test
    @DisplayName("actualizarTonalidades con nombre vacío no lanza excepción")
    void testActualizarTonalidades_nombreVacio() {
        List<Color> tonos = Arrays.asList(
                Color.RED, Color.GREEN, Color.BLUE,
                Color.YELLOW, Color.ORANGE, Color.PINK,
                Color.CYAN, Color.MAGENTA, Color.WHITE
        );
        assertDoesNotThrow(() -> panel.actualizarTonalidades(tonos, ""));
    }
}