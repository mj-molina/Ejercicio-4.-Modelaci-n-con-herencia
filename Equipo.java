import java.util.Locale;

public abstract class Equipo {
    private String codigoInventario;
    private String marca;
    private String modelo;
    private double tarifaDiaria;
    private boolean disponible;

    protected Equipo(String codigoInventario, String marca, String modelo, double tarifaDiaria) {
        if (codigoInventario == null || codigoInventario.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de inventario no puede estar vacío.");
        }
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca no puede estar vacía.");
        }
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo no puede estar vacío.");
        }
        if (!(tarifaDiaria > 0) || Double.isInfinite(tarifaDiaria)) {
            throw new IllegalArgumentException("La tarifa diaria debe ser mayor que cero.");
        }
        this.codigoInventario = codigoInventario.trim();
        this.marca = marca.trim();
        this.modelo = modelo.trim();
        this.tarifaDiaria = tarifaDiaria;
        this.disponible = true;
    }

    public String getCodigoInventario() {
        return codigoInventario;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getTarifaDiaria() {
        return tarifaDiaria;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public double calcularCostoTotal(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días de alquiler deben ser un entero positivo.");
        }
        return tarifaDiaria * dias + calcularRecargo(dias);
    }

    protected abstract double calcularRecargo(int dias);

    public abstract String getCategoria();

    public abstract String getDetallesEspecificos();

    void marcarAlquilado() {
        if (!disponible) {
            throw new IllegalStateException("El equipo " + codigoInventario + " ya está alquilado.");
        }
        disponible = false;
    }

    void marcarDevuelto() {
        if (disponible) {
            throw new IllegalStateException("El equipo " + codigoInventario + " no está alquilado.");
        }
        disponible = true;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "[%s] %s %s | %s | %s | Tarifa diaria: Q%,.2f | %s",
                codigoInventario, marca, modelo, getCategoria(), getDetallesEspecificos(),
                tarifaDiaria, disponible ? "Disponible" : "Alquilado");
    }
}