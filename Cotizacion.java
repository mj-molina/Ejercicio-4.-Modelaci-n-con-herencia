import java.util.Locale;

public class Cotizacion {
    private final Equipo equipo;
    private final int dias;
    private final double costoTotal;
    private final boolean disponibleAlCotizar;

    public Cotizacion(Equipo equipo, int dias) {
        if (equipo == null) {
            throw new IllegalArgumentException("El equipo no puede ser nulo.");
        }
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días de alquiler deben ser un entero positivo.");
        }
        this.equipo = equipo;
        this.dias = dias;
        this.costoTotal = equipo.calcularCostoTotal(dias);
        this.disponibleAlCotizar = equipo.isDisponible();
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public int getDias() {
        return dias;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public boolean isDisponibleAlCotizar() {
        return disponibleAlCotizar;
    }

    @Override
    public String toString() {
        return String.format(Locale.US,
                "Código: %s%nEquipo: %s %s%nCategoría: %s%nCaracterísticas: %s%nDisponibilidad: %s%nDías: %d%nCosto total: Q%,.2f",
                equipo.getCodigoInventario(), equipo.getMarca(), equipo.getModelo(),
                equipo.getCategoria(), equipo.getDetallesEspecificos(),
                disponibleAlCotizar ? "Disponible" : "Alquilado", dias, costoTotal);
    }
}
