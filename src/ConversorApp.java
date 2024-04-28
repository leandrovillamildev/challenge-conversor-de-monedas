/*
 * Elaborar una aplicación que consuma una API de monedas y mostrarle en un menú al usuario que valor de la moneda quiere cambiar para ver a que valor equivale en otra moneda.
 */

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ConversorApp {

    public static void main(String[] args) {
        try {
            ConversorApp comunicacion = new ConversorApp();
            comunicacion.convertCurrency();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void convertCurrency() throws IOException, InterruptedException {
        // Realizar la solicitud a la API
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/81d27f84c7fa8915e8587990/latest/USD"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // System.out.println(response.body()); -> Acá verificamos si estamos consumiendo la API

        // Verificar si la solicitud fue exitosa
        if (response.statusCode() == 200) {
            // Parsear la respuesta JSON
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonObject conversionRates = jsonResponse.getAsJsonObject("conversion_rates");

            // Menú de opciones
            String menu = """
                \n*********************************************
                
                Sea bienvenido/a al conversor de monedas
                
                ** Escriba el número de la opción deseada **
                
                1) Dólar -> Peso argentino
                2) Peso argentino -> Dólar
                3) Dólar -> Real brasileño
                4) Real brasileño -> dólar
                5) Dólar -> Peso colombiano
                6) Peso colombiano -> Dólar
                7) Salir
                
                *********************************************
                """;

            // Leer la opción del usuario
            Scanner keyboard = new Scanner(System.in);
            int option = 0;
            while (option != 7) {
                System.out.println(menu);
                option = keyboard.nextInt();

                // Realizar la conversión basada en la opción seleccionada
                double amountToConvert;
                switch (option) {
                    case 1:
                        System.out.println("\nIngrese el valor en dólares que desea convertir a pesos argentinos:");
                        amountToConvert = keyboard.nextDouble();
                        double convertedAmountToARS = amountToConvert * conversionRates.get("ARS").getAsDouble();
                        System.out.println("\n" + amountToConvert + " USD equivalen a " + convertedAmountToARS + " ARS");
                        break;
                    case 2:
                        System.out.println("\nIngrese el valor en pesos argentinos que desea convertir a dólares:");
                        amountToConvert = keyboard.nextDouble();
                        double convertedAmountToUSD = amountToConvert / conversionRates.get("ARS").getAsDouble();
                        System.out.println("\n" + amountToConvert + " ARS equivalen a " + convertedAmountToUSD + " USD");
                        break;
                    case 3:
                        System.out.println("\nIngrese el valor en dólares que desea convertir a reales brasileños:");
                        amountToConvert = keyboard.nextDouble();
                        double convertedAmountToBRL = amountToConvert * conversionRates.get("BRL").getAsDouble();
                        System.out.println("\n" + amountToConvert + " USD equivalen a " + convertedAmountToBRL + " BRL");
                        break;
                    case 4:
                        System.out.println("\nIngrese el valor en reales brasileños que desea convertir a dólares:");
                        amountToConvert = keyboard.nextDouble();
                        double convertedAmountFromBRLToUSD = amountToConvert / conversionRates.get("BRL").getAsDouble();
                        System.out.println("\n" + amountToConvert + " BRL equivalen a " + convertedAmountFromBRLToUSD + " USD");
                        break;
                    case 5:
                        System.out.println("\nIngrese el valor en dólares que desea convertir a pesos colombianos:");
                        amountToConvert = keyboard.nextDouble();
                        double convertedAmountToCOP = amountToConvert * conversionRates.get("COP").getAsDouble();
                        System.out.println("\n" + amountToConvert + " USD equivalen a " + convertedAmountToCOP + " COP");
                        break;
                    case 6:
                        System.out.println("\nIngrese el valor en pesos colombianos que desea convertir a dólares:");
                        amountToConvert = keyboard.nextDouble();
                        double convertedAmountFromCOPToUSD = amountToConvert / conversionRates.get("COP").getAsDouble();
                        System.out.println("\n" + amountToConvert + " COP equivalen a " + convertedAmountFromCOPToUSD + " USD");
                        break;
                    case 7:
                        System.out.println("\nEl programa ha finalizado, muchas gracias.");
                        break;
                    default:
                        System.out.println("\nOpción no válida.");
                        break;
                }
            }
        } else {
            System.out.println("Error al hacer la solicitud de la API.");
        }
    }
}
