package test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import domain.MultiPolyController;
import domain.elements.SquareFactory;
import domain.square.RailroadSquare;
import domain.square.RealEstateSquare;
import domain.square.Square;
import domain.square.TaxRefundSquare;

class SquareFactoryTest {
	
	MultiPolyController mc =  MultiPolyController.getInstance();

	@Test
	void testGetInstance() {
		Object sf = SquareFactory.getInstance();

		assertNotNull(sf); // build of factory does not let this
		assertTrue(((SquareFactory) sf).repOk());

		assertThat(sf, instanceOf(SquareFactory.class)); // sf supposed to be an instance of SquareFactory
		assertTrue(((SquareFactory) sf).repOk());
	}

	@Test
	void testCreateSquareIllegalExTest() {
		SquareFactory sf = SquareFactory.getInstance();

		String illegalLine = "realestate";
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			sf.createSquare(illegalLine,mc);
		});
		assertTrue(sf.repOk());

		String numberformline = "realestate-def";
		Assertions.assertThrows(NumberFormatException.class, () -> {
			sf.createSquare(numberformline,mc);
		});
		assertTrue(sf.repOk());

		String runtime = "abc-2";
		Assertions.assertThrows(RuntimeException.class, () -> {
			sf.createSquare(runtime,mc);
		});
		assertTrue(sf.repOk());

	}

	@Test
	void testCreateSquare() {
		SquareFactory sf = SquareFactory.getInstance();

		String realEst = "realestate-1-Randolph Street-1-270-23-115-345-825-1010-1180-2180-150";
		Square realest = sf.createSquare(realEst,mc);
		assertThat(realest, instanceOf(RealEstateSquare.class)); // sf supposed to be an instance of SquareFactory
		assertTrue(((SquareFactory) sf).repOk());

		String railRoad = "railroad-35-Reading Railroad-200";
		Square railroad = sf.createSquare(railRoad,mc);
		assertThat(railroad, instanceOf(RailroadSquare.class)); // sf supposed to be an instance of SquareFactory
		assertTrue(((SquareFactory) sf).repOk());

		String taxRef = "taxref-98";
		Square taxref = sf.createSquare(taxRef,mc);
		assertThat(taxref, instanceOf(TaxRefundSquare.class)); // sf supposed to be an instance of SquareFactory
		assertTrue(((SquareFactory) sf).repOk());

	}

}
