public public class CamaraVideo extends Equipo {
    private int resolucionMaxima;
    private static final int UMBRAL_RESOLUCION = 1080;
    private static final double CARGO_ALTA_RESOLUCION = 75;

    public CamaraVideo(String codigoInventario, String marca, String modelo, double tarifaDiaria,
                       int resolucionMaxima) {
        super(codigoInventario, marca, modelo, tarifaDiaria);
        if (resolucionMaxima <= 0) {
            throw new IllegalArgumentException("La resolución máxima debe ser mayor que cero.");
        }
        this.resolucionMaxima = resolucionMaxima;
    }

    public int getResolucionMaxima() {
        return resolucionMaxima;
    }

    @Override
    protected double calcularRecargo(int dias) {
        if (resolucionMaxima > UMBRAL_RESOLUCION) {
            return CARGO_ALTA_RESOLUCION;
        }
        return 0;
    }

    @Override
    public String getCategoria() {
        return "Cámara de video";
    }

    @Override
    public String getDetallesEspecificos() {
        return "Resolución máxima: " + resolucionMaxima + "p";
    }
}
