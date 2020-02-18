package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import domain.square.RollThreeSquare;

class RollThreeSquareTest {


	@Test
	void testCalculateMoney() {
		RollThreeSquare r3s = new RollThreeSquare(1,"Hey it is Roll3",false);
		
		ArrayList<Integer> dices = new ArrayList<Integer>();
		dices.add(1);
		dices.add(2);
		dices.add(3);
		
		ArrayList<Integer> card = new ArrayList<Integer>();
		card.add(1);
		card.add(2);
		card.add(3);
		
		assertEquals(1500,r3s.calculateMoney(true,dices,card));
		assertTrue(r3s.repOk());
		
		assertEquals(1000,r3s.calculateMoney(false,dices,card));
		assertTrue(r3s.repOk());
		
		card.remove(2);
		card.add(4);
		
		assertEquals(r3s.calculateMoney(false,dices,card),r3s.calculateMoney(true,dices,card));
		assertTrue(r3s.repOk());
		
		assertEquals(200,r3s.calculateMoney(true,dices,card));
		assertTrue(r3s.repOk());
		
		card.remove(1);
		card.add(5);
		
		assertEquals(50,r3s.calculateMoney(true,dices,card));
		assertTrue(r3s.repOk());
	}



}
