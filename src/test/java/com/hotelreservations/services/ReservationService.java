package com.hotelreservations.services;

import com.hotelreservations.models.Auth;
import com.hotelreservations.models.Booking;
import com.hotelreservations.models.BookingDates;
import com.hotelreservations.models.BookingResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ReservationService extends BaseTest {

    public String generateToken() {
        Auth authBody = new Auth("admin", "password123");

        Response response = given(spec)
                .contentType(ContentType.JSON)
                .when()
                .body(authBody)
                .post("/auth");

        response
                .then()
                .statusCode(200);

        return response.jsonPath().getJsonObject("token");
    }


    public BookingResponse createBooking() {
        BookingDates bookingDates = new BookingDates("2023-04-01", "2023-05-01");
        Booking booking = new Booking("Udemy", "Cucumber", 1000, false, bookingDates, "Kopek yatagi");

        Response response = given(spec)
                .contentType(ContentType.JSON)
                .when()
                .body(booking)
                .post("/booking");

        response
                .then()
                .statusCode(200);

        return response.as(BookingResponse.class);
    }

    public void deleteReservation(String token, int bookingId) {
        Response response = given(spec)
                .contentType(ContentType.JSON)
                .header("Cookie", "token=" + token)
                .when()
                .delete("/booking/" + bookingId);

        response
                .then()
                .statusCode(201);
    }

}
