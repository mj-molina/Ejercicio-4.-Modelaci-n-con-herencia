public import java.math.BigDecimal;

public class EquipoSonido extends Equipo {
    private double potenciaKw;
    private static final double CARGO_POR_KW_DIA = 100;

    public EquipoSonido(String codigoInventario, String marca, String modelo, double tarifaDiaria,
                        double potenciaKw) {
        super(codigoInventario, marca, modelo, tarifaDiaria);
        if (!(potenciaKw > 0) || Double.isInfinite(potenciaKw)) {
            throw new IllegalArgumentException("La potencia debe ser mayor que cero.");
        }
        this.potenciaKw = potenciaKw;
    }

    public double getPotenciaKw() {
        return potenciaKw;
    }

    @Override
    protected double calcularRecargo(int dias) {
        return CARGO_POR_KW_DIA * potenciaKw * dias;
    }

    @Override
    public String getCategoria() {
        return "Equipo de sonido";
    }

    @Override
    public String getDetallesEspecificos() {
        return "Potencia nominal: " + BigDecimal.valueOf(potenciaKw).toPlainString() + " kW";
    }
}
