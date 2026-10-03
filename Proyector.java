public class Proyector extends Equipo {
    private int lumenes;
    private boolean conectividadInalambrica;
    private static final double RECARGO_INALAMBRICO_DIARIO = 50;

    public Proyector(String codigoInventario, String marca, String modelo, double tarifaDiaria,
                     int lumenes, boolean conectividadInalambrica) {
        super(codigoInventario, marca, modelo, tarifaDiaria);
        if (lumenes <= 0) {
            throw new IllegalArgumentException("La luminosidad debe ser mayor que cero.");
        }
        this.lumenes = lumenes;
        this.conectividadInalambrica = conectividadInalambrica;
    }

    public int getLumenes() {
        return lumenes;
    }

    public boolean tieneConectividadInalambrica() {
        return conectividadInalambrica;
    }

    @Override
    protected double calcularRecargo(int dias) {
        if (conectividadInalambrica) {
            return RECARGO_INALAMBRICO_DIARIO * dias;
        }
        return 0;
    }

    @Override
    public String getCategoria() {
        return "Proyector";
    }

    @Override
    public String getDetallesEspecificos() {
        return "Luminosidad: " + lumenes + " lúmenes | Conectividad inalámbrica: "
                + (conectividadInalambrica ? "Sí" : "No");
    }
}
