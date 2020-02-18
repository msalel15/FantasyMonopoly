package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.elements.Board;
import domain.elements.Piece;
import domain.elements.Player;
import domain.square.PaydaySquare;
import domain.square.RealEstateSquare;
import domain.square.Square;

class BoardTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testAddToSquareList() {
		Board board = new Board();

		Square sq1 = new PaydaySquare(0, "Payday Square", false);
		board.addToSquareList(sq1);

		Square sq2 = new RealEstateSquare(1, "Randolph Street", true, null, new Color(128, 0, 51), 270,
				new ArrayList<Integer>(Arrays.asList(23, 115, 345, 825, 1010, 1180, 2180)), 150, 0);
		board.addToSquareList(sq2);

		ArrayList<Square> sList = board.getSquares();

		assertEquals(sq1, sList.get(0));
		assertTrue(board.repOk());

		assertEquals(sq2, sList.get(1));
		assertTrue(board.repOk());

		assertNotEquals(sq2, sList.get(0));
		assertTrue(board.repOk());
	}

	@Test
	public void testGetSquareByName() {

		Board board = new Board();

		Square sq1 = new PaydaySquare(0, "Payday Square", false);
		board.addToSquareList(sq1);

		Square sq2 = new RealEstateSquare(1, "Randolph Street", true, null, new Color(128, 0, 51), 270,
				new ArrayList<Integer>(Arrays.asList(23, 115, 345, 825, 1010, 1180, 2180)), 150, 0);
		board.addToSquareList(sq2);

		assertEquals(sq1, board.getSquareByName("Payday Square"));
		assertTrue(board.repOk());

		assertEquals(sq2, board.getSquareByName("Randolph Street"));
		assertTrue(board.repOk());

		assertNotEquals(sq2, board.getSquareByName("Payday Square"));
		assertTrue(board.repOk());

	}

	@Test
	public void testGetSquareById() {
		Board board = new Board();

		Square sq1 = new PaydaySquare(0, "Payday Square", false);
		board.addToSquareList(sq1);

		Square sq2 = /*
						 * new RealEstateSquare(1, "Randolph Street", true,null,new Color(128, 0, 51),
						 * 270, new ArrayList<Integer>(Arrays.asList(23, 115, 345, 825, 1010, 1180,
						 * 2180)), 150,);
						 */null; // This will be changed after factory updatedf
		board.addToSquareList(sq2);

		assertEquals(sq1, board.getSquareByID(0));
		assertTrue(board.repOk());

		assertEquals(sq2, board.getSquareByID(1));
		assertTrue(board.repOk());

		assertNotEquals(sq2, board.getSquareByID(0));
		assertTrue(board.repOk());
	}

	@Test
	public void testBuyRealEstate() {
		Board board = new Board();

		Square sq1 = new RealEstateSquare(0, "Any Street", true, null, new Color(128, 0, 51), 270,
				new ArrayList<Integer>(Arrays.asList(23, 115, 345, 825, 1010, 1180, 2180)), 150, 0);
		board.addToSquareList(sq1);

		Square sq2 = new RealEstateSquare(1, "Other Street", true, null, new Color(128, 0, 51), 270,
				new ArrayList<Integer>(Arrays.asList(23, 115, 345, 825, 1010, 1180, 2180)), 150, 0);
		((RealEstateSquare) sq2).setOwner(new Player("pl2", new Piece(null)));

		board.addToSquareList(sq2);

		Player pl = new Player("pl1", new Piece(null));

		board.buyRealEstate(pl, (RealEstateSquare) sq1);
		board.buyRealEstate(pl, (RealEstateSquare) sq2);

		assertEquals(sq1, pl.getPropertyList().get(0));
		assertTrue(board.repOk());

		assertNotEquals(2, pl.getPropertyList().size());
		assertTrue(board.repOk());

		assertNotEquals(sq2, pl.getPropertyList().get(0));
		assertTrue(board.repOk());

	}

	@Test
	public void testupdatePoolMoney() {
		Board board = new Board();

		board.updatePoolMoney(150);
		assertEquals(board.getPoolMoney(), 150);
		assertTrue(board.repOk());

		board.updatePoolMoney(300);
		assertEquals(board.getPoolMoney(), 450);
		assertTrue(board.repOk());
	}

	// Overall tests for repOk
	@Test
	public void testRepOk() {
		Board board = new Board();

		Square sq1 = new PaydaySquare(0, "Payday Square", false);
		board.addToSquareList(sq1);

		assertTrue(board.repOk());

	}

}
