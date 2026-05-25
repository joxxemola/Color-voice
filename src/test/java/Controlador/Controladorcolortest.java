/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
package controlador;

import modelo.ModeloColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vista.VentanaPrincipal;

import java.awt.Color;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para ControladorColor")
class ControladorColorTest {

    @Mock
    private VentanaPrincipal ventanaMock;

    private ControladorColor controlador;

    @BeforeEach
    void setUp() {
        doNothing().when(ventanaMock).asignarControlador(any());
        doNothing().when(ventanaMock).mostrarListaColores(anyList());
        doNothing().when(ventanaMock).actualizarVisualizacion(any());
        doNothing().when(ventanaMock).mostrarTonalidades(anyList(), anyString());

        controlador = new ControladorColor(ventanaMock);
    }

    @Test
    @DisplayName("generarTonalidades retorna exactamente 9 tonos")
    void testGenerarTonalidades_cantidadCorrecta() {
        List<Color> tonos = controlador.generarTonalidades(new Color(220, 50, 50));
        assertEquals(9, tonos.size());
    }

    @Test
    @DisplayName("El quinto tono (índice 4) es el color base")
    void testGenerarTonalidades_baseEnIndice4() {
        Color base = new Color(50, 80, 200);
        List<Color> tonos = controlador.generarTonalidades(base);
        assertEquals(base, tonos.get(4));
    }

    @Test
    @DisplayName("Los primeros 4 tonos son tintes (más claros que la base)")
    void testGenerarTonalidades_tintesAntesDeBase() {
        Color base = new Color(100, 100, 100);
        List<Color> tonos = controlador.generarTonalidades(base);

        for (int i = 0; i < 4; i++) {
            Color tinte = tonos.get(i);
            assertTrue(tinte.getRed() >= base.getRed(),
                    "Tinte[" + i + "] rojo debería ser >= base");
            assertTrue(tinte.getGreen() >= base.getGreen(),
                    "Tinte[" + i + "] verde debería ser >= base");
            assertTrue(tinte.getBlue() >= base.getBlue(),
                    "Tinte[" + i + "] azul debería ser >= base");
        }
    }

    @Test
    @DisplayName("Los últimos 4 tonos son sombras (más oscuras que la base)")
    void testGenerarTonalidades_sombrasDespuesDeBase() {
        Color base = new Color(100, 100, 100);
        List<Color> tonos = controlador.generarTonalidades(base);

        for (int i = 5; i < 9; i++) {
            Color sombra = tonos.get(i);
            assertTrue(sombra.getRed() <= base.getRed(),
                    "Sombra[" + i + "] rojo debería ser <= base");
            assertTrue(sombra.getGreen() <= base.getGreen(),
                    "Sombra[" + i + "] verde debería ser <= base");
            assertTrue(sombra.getBlue() <= base.getBlue(),
                    "Sombra[" + i + "] azul debería ser <= base");
        }
    }

    @Test
    @DisplayName("generarTonalidades no produce valores fuera de [0, 255]")
    void testGenerarTonalidades_valoresEnRango() {
        Color base = new Color(255, 0, 0);
        List<Color> tonos = controlador.generarTonalidades(base);

        for (Color tono : tonos) {
            assertTrue(tono.getRed() >= 0 && tono.getRed() <= 255);
            assertTrue(tono.getGreen() >= 0 && tono.getGreen() <= 255);
            assertTrue(tono.getBlue() >= 0 && tono.getBlue() <= 255);
        }
    }

    @Test
    @DisplayName("generarTonalidades con blanco produce sólo tonos válidos")
    void testGenerarTonalidades_colorBlanco() {
        Color blanco = new Color(255, 255, 255);
        List<Color> tonos = controlador.generarTonalidades(blanco);
        assertEquals(9, tonos.size());

        for (int i = 0; i < 4; i++) {
            assertEquals(blanco, tonos.get(i));
        }
    }

    @Test
    @DisplayName("generarTonalidades con negro produce sólo negro como sombras")
    void testGenerarTonalidades_colorNegro() {
        Color negro = new Color(0, 0, 0);
        List<Color> tonos = controlador.generarTonalidades(negro);

        for (int i = 5; i < 9; i++) {
            assertEquals(negro, tonos.get(i));
        }
    }

    @Test
    @DisplayName("seleccionarColor actualiza la visualización en la ventana")
    void testSeleccionarColor_actualizaVentana() {
        ModeloColor modelo = new ModeloColor("Verde", new Color(50, 170, 50), "Secundarios");
        controlador.seleccionarColor(modelo);
        verify(ventanaMock, atLeastOnce()).actualizarVisualizacion(modelo);
    }

    @Test
    @DisplayName("seleccionarColor muestra las tonalidades del color elegido")
    void testSeleccionarColor_muestraTonalidades() {
        ModeloColor modelo = new ModeloColor("Naranja", new Color(245, 130, 0), "Secundarios");
        controlador.seleccionarColor(modelo);
        verify(ventanaMock, atLeastOnce()).mostrarTonalidades(anyList(), eq("Naranja"));
    }

    @Test
    @DisplayName("seleccionarTono actualiza visualización con el tono seleccionado")
    void testSeleccionarTono_actualizaVisualizacion() {
        Color tono = new Color(180, 100, 80);
        controlador.seleccionarTono(tono);
        verify(ventanaMock, atLeastOnce()).actualizarVisualizacion(any(ModeloColor.class));
    }

    @Test
    @DisplayName("seleccionarTono crea modelo con nombre en formato HEX")
    void testSeleccionarTono_nombreEnHex() {
        Color tono = new Color(255, 128, 0);
        controlador.seleccionarTono(tono);
        verify(ventanaMock, atLeastOnce()).actualizarVisualizacion(argThat(m
                -> m.getNombre().startsWith("#")
        ));
    }

    @Test
    @DisplayName("filtrarPorCategoria 'Primarios' muestra solo 3 colores")
    void testFiltrarPorCategoria_primarios() {
        controlador.filtrarPorCategoria("Primarios");
        verify(ventanaMock, atLeastOnce()).mostrarListaColores(argThat(lista
                -> lista.size() == 3
        ));
    }

    @Test
    @DisplayName("filtrarPorCategoria 'Todos' muestra los 18 colores")
    void testFiltrarPorCategoria_todos() {
        controlador.filtrarPorCategoria("Todos");
        verify(ventanaMock, atLeastOnce()).mostrarListaColores(argThat(lista
                -> lista.size() == 18
        ));
    }

    @Test
    @DisplayName("filtrarPorCategoria 'Neutros' muestra exactamente 4 colores")
    void testFiltrarPorCategoria_neutros() {
        controlador.filtrarPorCategoria("Neutros");
        verify(ventanaMock, atLeastOnce()).mostrarListaColores(argThat(lista
                -> lista.size() == 4
        ));
    }

    @Test
    @DisplayName("filtrarPorCategoria 'Colores cálidos' muestra 4 colores")
    void testFiltrarPorCategoria_calidos() {
        controlador.filtrarPorCategoria("Colores cálidos");
        verify(ventanaMock, atLeastOnce()).mostrarListaColores(argThat(lista
                -> lista.size() == 4
        ));
    }

    @Test
    @DisplayName("filtrarPorCategoria 'Colores fríos' muestra 4 colores")
    void testFiltrarPorCategoria_frios() {
        controlador.filtrarPorCategoria("Colores fríos");
        verify(ventanaMock, atLeastOnce()).mostrarListaColores(argThat(lista
                -> lista.size() == 4
        ));
    }

    @Test
    @DisplayName("buscarPorNombre con texto exacto retorna resultado único")
    void testBuscarPorNombre_textoExacto() {
        controlador.buscarPorNombre("Rojo");
        verify(ventanaMock, atLeastOnce()).mostrarListaColores(argThat(lista
                -> lista.size() == 1 && lista.get(0).getNombre().equals("Rojo")
        ));
    }

    @Test
    @DisplayName("buscarPorNombre es insensible a mayúsculas")
    void testBuscarPorNombre_insensibleMayusculas() {
        controlador.buscarPorNombre("rojo");
        verify(ventanaMock, atLeastOnce()).mostrarListaColores(argThat(lista
                -> lista.size() == 1
        ));
    }

    @Test
    @DisplayName("buscarPorNombre con texto vacío retorna todos los colores")
    void testBuscarPorNombre_textoVacio() {
        controlador.buscarPorNombre("");
        verify(ventanaMock, atLeastOnce()).mostrarListaColores(argThat(lista
                -> lista.size() == 18
        ));
    }

    @Test
    @DisplayName("buscarPorNombre con texto inexistente retorna lista vacía")
    void testBuscarPorNombre_sinResultados() {
        controlador.buscarPorNombre("XYZInexistente");
        verify(ventanaMock, atLeastOnce()).mostrarListaColores(argThat(List::isEmpty));
    }

    @Test
    @DisplayName("buscarPorNombre respeta filtro de categoría activa")
    void testBuscarPorNombre_conCategoriaActiva() {
        controlador.filtrarPorCategoria("Primarios");
        clearInvocations(ventanaMock);
        controlador.buscarPorNombre("a");
        verify(ventanaMock).mostrarListaColores(argThat(lista
                -> lista.stream().allMatch(c -> c.getCategoria().equals("Primarios"))
        ));
    }

    @Test
    @DisplayName("Constructor llama a asignarControlador en la ventana")
    void testConstructor_asignaControlador() {
        verify(ventanaMock).asignarControlador(controlador);
    }

    @Test
    @DisplayName("Constructor carga todos los colores en la ventana")
    void testConstructor_cargaColores() {
        verify(ventanaMock).mostrarListaColores(argThat(lista
                -> lista.size() == 18
        ));
    }
}
