import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static Inventario inventario = new Inventario();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        cargarDatosIniciales();
        boolean salir = false;
        try {
            while (!salir) {
                mostrarMenu();
                int opcion = leerEntero("Opción: ");
                switch (opcion) {
                    case 1:
                        registrarEquipoMenu();
                        break;
                    case 2:
                        listarInventarioMenu();
                        break;
                    case 3:
                        cotizarMenu();
                        break;
                    case 4:
                        alquilarMenu();
                        break;
                    case 5:
                        devolverMenu();
                        break;
                    case 6:
                        reporteMenu();
                        break;
                    case 7:
                        salir = true;
                        break;
                    default:
                        System.out.println("Error: opción no válida.");
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Entrada finalizada.");
        }
        System.out.println("Fin del programa.");
        scanner.close();
    }

    private static void cargarDatosIniciales() {
        inventario.registrarEquipo(new Proyector("P-001", "Epson", "EB-X06", 150, 3000, true));
        inventario.registrarEquipo(new Proyector("P-002", "BenQ", "MS550", 120, 3600, false));
        inventario.registrarEquipo(new CamaraVideo("C-001", "Canon", "Vixia HF R800", 180, 1080));
        inventario.registrarEquipo(new CamaraVideo("C-002", "Sony", "FDR-AX43", 250, 2160));
        inventario.registrarEquipo(new EquipoSonido("S-001", "JBL", "PRX815W", 200, 1.5));
        inventario.registrarEquipo(new EquipoSonido("S-002", "Mackie", "Thump 215", 300, 0.8));
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== EnEscena =====");
        System.out.println("1. Registrar equipo");
        System.out.println("2. Consultar inventario");
        System.out.println("3. Cotizar alquiler");
        System.out.println("4. Confirmar alquiler");
        System.out.println("5. Registrar devolución");
        System.out.println("6. Reporte general");
        System.out.println("7. Salir");
    }

    private static void registrarEquipoMenu() {
        System.out.println("--- Registrar equipo ---");
        System.out.println("1. Proyector");
        System.out.println("2. Cámara de video");
        System.out.println("3. Equipo de sonido");
        try {
            int categoria = leerEntero("Categoría: ");
            if (categoria < 1 || categoria > 3) {
                System.out.println("Error: categoría no válida.");
                return;
            }
            String codigo = leerTexto("Código de inventario: ");
            String marca = leerTexto("Marca: ");
            String modelo = leerTexto("Modelo: ");
            double tarifa = leerDecimal("Tarifa diaria (Q): ");
            Equipo equipo;
            if (categoria == 1) {
                int lumenes = leerEntero("Luminosidad (lúmenes): ");
                boolean inalambrica = leerConfirmacion("Conectividad inalámbrica (s/n): ");
                equipo = new Proyector(codigo, marca, modelo, tarifa, lumenes, inalambrica);
            } else if (categoria == 2) {
                int resolucion = leerEntero("Resolución máxima (píxeles verticales): ");
                equipo = new CamaraVideo(codigo, marca, modelo, tarifa, resolucion);
            } else {
                double potencia = leerDecimal("Potencia nominal (kW): ");
                equipo = new EquipoSonido(codigo, marca, modelo, tarifa, potencia);
            }
            inventario.registrarEquipo(equipo);
            System.out.println("Equipo registrado.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarInventarioMenu() {
        System.out.println("--- Inventario ---");
        List<Equipo> lista = inventario.listarEquipos();
        if (lista.isEmpty()) {
            System.out.println("No hay equipos registrados.");
            return;
        }
        for (Equipo equipo : lista) {
            System.out.println(equipo);
        }
    }

    private static void cotizarMenu() {
        System.out.println("--- Cotizar alquiler ---");
        try {
            String codigo = leerTexto("Código de inventario: ");
            int dias = leerEntero("Días de alquiler: ");
            Cotizacion cotizacion = inventario.cotizar(codigo, dias);
            System.out.println(cotizacion);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void alquilarMenu() {
        System.out.println("--- Confirmar alquiler ---");
        try {
            String codigo = leerTexto("Código de inventario: ");
            int dias = leerEntero("Días de alquiler: ");
            Cotizacion cotizacion = inventario.cotizar(codigo, dias);
            System.out.println(cotizacion);
            if (!cotizacion.isDisponibleAlCotizar()) {
                System.out.println("Error: el equipo está alquilado. No se realizaron cambios.");
                return;
            }
            if (leerConfirmacion("¿Confirmar alquiler? (s/n): ")) {
                double monto = inventario.confirmarAlquiler(cotizacion);
                System.out.printf(Locale.US, "Alquiler confirmado. Monto cobrado: Q%,.2f%n", monto);
            } else {
                System.out.println("Alquiler cancelado. No se realizaron cambios.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void devolverMenu() {
        System.out.println("--- Registrar devolución ---");
        try {
            String codigo = leerTexto("Código de inventario: ");
            inventario.registrarDevolucion(codigo);
            System.out.println("Devolución registrada.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void reporteMenu() {
        System.out.println(inventario.generarReporte());
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        if (!scanner.hasNextLine()) {
            throw new NoSuchElementException();
        }
        return scanner.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un número entero.");
            }
        }
    }

    private static double leerDecimal(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje).replace(',', '.');
            if (texto.matches("-?[0-9]+([.][0-9]+)?")) {
                return Double.parseDouble(texto);
            }
            System.out.println("Error: ingrese un número válido.");
        }
    }

    private static boolean leerConfirmacion(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje).toLowerCase(Locale.ROOT);
            if (texto.equals("s") || texto.equals("si") || texto.equals("sí")) {
                return true;
            }
            if (texto.equals("n") || texto.equals("no")) {
                return false;
            }
            System.out.println("Error: responda s o n.");
        }
    }
}
