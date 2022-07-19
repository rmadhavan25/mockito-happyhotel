package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class BookingServiceTest {
	
	private BookingService bookingService;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private MailSender mailSenderMock;
	private PaymentService paymentServiceMock;
	
	@BeforeEach
	void setup() {
		this.roomServiceMock = mock(RoomService.class);
		this.paymentServiceMock = mock(PaymentService.class);
		this.bookingDAOMock = mock(BookingDAO.class);
		this.mailSenderMock = mock(MailSender.class);
		this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
	}

	@Test
	void when_calculatePrice_then_returnCorrectPrice() {
		//given
		BookingRequest bookingRequest = new BookingRequest("001", LocalDate.of(2022, 07, 10), LocalDate.of(2022,07,15), 2, false);
		double expectedPrice = 5*2*50.0;
		
		//when
		double actualPrice = bookingService.calculatePrice(bookingRequest);
		
		//then
		assertEquals(expectedPrice, actualPrice);
	}
	
	@Test
	void when_getAvailablePlaceCount_return_correctNumberOfCount() {
		//given
		List<Room> rooms = Arrays.asList(new Room("room 1", 2),new Room("room 2",4));
		int expectedNumberOfRooms = 6;
		
		//when
		when(this.roomServiceMock.getAvailableRooms()).thenReturn(rooms);
		int actualNumberOfRooms = this.bookingService.getAvailablePlaceCount();
		
		//then
		assertEquals(expectedNumberOfRooms, actualNumberOfRooms);
	}

}
