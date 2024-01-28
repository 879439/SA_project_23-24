package org.example.backend.DBConfigurations;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class FlightRequestSender {

    private static final String[] cities = {"Rome", "Milan", "Naples", "Turin","Florence"};
    private static final String[] companies = {"Ryanair", "EasyJet", "Alitalia"};
    private static final String[] foods = {"Pizza", "Sandwich", "Hamburgher"};
    private static final String[] sizes = {"small", "medium", "big"};
    private static final String[] travelClasses = {"economy", "business", "firstClass"};
    private static final Random random = new Random();

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String json2 = String.format("""
                    {
                        "username": "admin",
                        "password": "admin",
                        "email": "admin@admin.it",
                        "role": "admin",
                        "firstname": "admin",
                        "lastname": "admin",
                        "sex" : "M",
                        "birthdate": "2000-01-01"
                    }""");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/auth/signup"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json2))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response: " + response.body());
        String url = "http://localhost:8081/api/flights";

        for (int i = 0; i < 200; i++) {
            String departureCity = cities[random.nextInt(cities.length)];
            String arrivalCity = cities[random.nextInt(cities.length)];
            while (arrivalCity.equals(departureCity)) {
                arrivalCity = cities[random.nextInt(cities.length)];
            }

            String company = companies[random.nextInt(companies.length)];
            String food = foods[random.nextInt(foods.length)];
            String size = sizes[random.nextInt(sizes.length)];
            String travelClass = travelClasses[random.nextInt(travelClasses.length)];
            LocalDate date = LocalDate.now().plusDays(random.nextInt(30));
            String departureTime = LocalTime.now().plusHours(random.nextInt(12)).format(DateTimeFormatter.ofPattern("HH:mm"));
            String arrivalTime = LocalTime.parse(departureTime).plusHours(random.nextInt(12) + 1).format(DateTimeFormatter.ofPattern("HH:mm"));;

            String discountCode = generateRandomCode(6);

            String json = String.format("""
                    {
                        "company": "%s",
                        "departure": "%s",
                        "arrival": "%s",
                        "date": "%s",
                        "departure_time": "%s",
                        "arrival_time": "%s",
                        "foods": [{"name": "%s", "quantity": %d, "price": %.2f}],
                        "size": "%s",
                        "discount_code": "%s",
                        "travelClass": "%s"
                    }""",
                    company, departureCity, arrivalCity, date, departureTime, arrivalTime, food, random.nextInt(10) + 1, random.nextDouble() * 50, size, discountCode,travelClass);

            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status Code: " + response2.statusCode());
            System.out.println("Response: " + response2.body());
        }
    }

    private static String generateRandomCode(int length) {
        return random.ints(48, 122)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}