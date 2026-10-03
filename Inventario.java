import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Inventario {
    private Map<String, Equipo> equipos;
    private double ingresosAcumulados;

    public Inventario() {
        this.equipos = new LinkedHashMap<>();
        this.ingresosAcumulados = 0;
    }

    private String normalizar(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de inventario no puede estar vacío.");
        }
        return codigo.trim().toUpperCase(Locale.ROOT);
    }

    public void registrarEquipo(Equipo equipo) {
        if (equipo == null) {
            throw new IllegalArgumentException("El equipo no puede ser nulo.");
        }
        String clave = normalizar(equipo.getCodigoInventario());
        if (equipos.containsKey(clave)) {
            throw new IllegalArgumentException("Ya existe un equipo con el código " + equipo.getCodigoInventario() + ".");
        }
        equipos.put(clave, equipo);
    }

    public Equipo buscarPorCodigo(String codigo) {
        String clave = normalizar(codigo);
        Equipo equipo = equipos.get(clave);
        if (equipo == null) {
            throw new IllegalArgumentException("No existe un equipo con el código " + codigo.trim() + ".");
        }
        return equipo;
    }

    public List<Equipo> listarEquipos() {
        return new ArrayList<>(equipos.values());
    }

    public Cotizacion cotizar(String codigo, int dias) {
        Equipo equipo = buscarPorCodigo(codigo);
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días de alquiler deben ser un entero positivo.");
        }
        return new Cotizacion(equipo, dias);
    }

    public double confirmarAlquiler(Cotizacion cotizacion) {
        if (cotizacion == null) {
            throw new IllegalArgumentException("La cotización no puede ser nula.");
        }
        Equipo equipo = cotizacion.getEquipo();
        if (equipos.get(normalizar(equipo.getCodigoInventario())) != equipo) {
            throw new IllegalArgumentException("El equipo no pertenece al inventario.");
        }
        if (!equipo.isDisponible()) {
            throw new IllegalStateException("El equipo " + equipo.getCodigoInventario() + " ya está alquilado.");
        }
        equipo.marcarAlquilado();
        ingresosAcumulados += cotizacion.getCostoTotal();
        return cotizacion.getCostoTotal();
    }

    public void registrarDevolucion(String codigo) {
        Equipo equipo = buscarPorCodigo(codigo);
        if (equipo.isDisponible()) {
            throw new IllegalStateException("El equipo " + equipo.getCodigoInventario() + " no está alquilado.");
        }
        equipo.marcarDevuelto();
    }

    public double getIngresosAcumulados() {
        return ingresosAcumulados;
    }

    public String generarReporte() {
        Map<String, int[]> conteo = new LinkedHashMap<>();
        int total = 0;
        int disponibles = 0;
        int alquilados = 0;
        for (Equipo equipo : equipos.values()) {
            int[] contadores = conteo.computeIfAbsent(equipo.getCategoria(), k -> new int[3]);
            contadores[0]++;
            total++;
            if (equipo.isDisponible()) {
                contadores[1]++;
                disponibles++;
            } else {
                contadores[2]++;
                alquilados++;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("===== Reporte general =====%n"));
        for (Map.Entry<String, int[]> entrada : conteo.entrySet()) {
            int[] c = entrada.getValue();
            sb.append(String.format("%s: registrados %d | disponibles %d | alquilados %d%n",
                    entrada.getKey(), c[0], c[1], c[2]));
        }
        sb.append(String.format("Total: registrados %d | disponibles %d | alquilados %d%n",
                total, disponibles, alquilados));
        sb.append(String.format(Locale.US, "Ingresos acumulados: Q%,.2f", ingresosAcumulados));
        return sb.toString();
    }
}