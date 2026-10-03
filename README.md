# EnEscena

Aplicación de consola en Java para administrar el alquiler de proyectores, cámaras de video y equipos de sonido.

Curso: CC2008 - Programación Orientada a Objetos, UVG.

## Requisitos

- JDK 8 o superior (comprobar con `java -version` y `javac -version`)

## Compilar

Desde la carpeta que contiene los archivos .java:

    javac -encoding UTF-8 *.java

## Ejecutar

    java Main

## Archivos

- Equipo.java (clase abstracta)
- Proyector.java
- CamaraVideo.java
- EquipoSonido.java
- Cotizacion.java
- Inventario.java
- Main.java (menú principal)

## Menú

1. Registrar equipo
2. Consultar inventario
3. Cotizar alquiler
4. Confirmar alquiler
5. Registrar devolución
6. Reporte general
7. Salir

Al iniciar, el programa carga 2 equipos de cada categoría, todos disponibles y sin ingresos registrados.
