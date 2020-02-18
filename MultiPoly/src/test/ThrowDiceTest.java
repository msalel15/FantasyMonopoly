package test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import domain.elements.DieFaces;
import domain.elements.SpeedDie;
import domain.elements.ThrowDice;

class ThrowDiceTest {

	@Test
	public void isMyDieSpecialWithSpecial() throws Exception {
		ThrowDice thrw = new ThrowDice();
		thrw.manuallyDiceConfiguration(
				new ArrayList<DieFaces>(Arrays.asList(DieFaces.MRMON, DieFaces.TWO, DieFaces.ONE)));

		assertTrue(thrw.isSpecialDie());
		assertTrue(thrw.repOk());

	}

	@Test
	public void isMyDieSpecialWithoutSpecial() throws Exception {
		ThrowDice thrw = new ThrowDice();
		thrw.manuallyDiceConfiguration(
				new ArrayList<DieFaces>(Arrays.asList(DieFaces.THREE, DieFaces.FIVE, DieFaces.TWO)));
		assertFalse(thrw.isSpecialDie());
		assertTrue(thrw.repOk());
	}

	@Test
	public void isMyDieEmptyWhileEmpty() throws Exception {
		ThrowDice thrw = new ThrowDice();
		thrw.manuallyDiceConfiguration(
				new ArrayList<DieFaces>(Arrays.asList(DieFaces.FOUR, DieFaces.FOUR, DieFaces.TWO)));
		thrw.forgetDie();
		assertTrue(thrw.isMyDiceClear());
		assertTrue(thrw.repOk());
	}

	@Test
	public void isMyDieEmptyWhileNotEmpty() throws Exception {
		ThrowDice thrw = new ThrowDice();
		thrw.manuallyDiceConfiguration(new ArrayList<DieFaces>(Arrays.asList(DieFaces.ONE, DieFaces.FIVE)));

		assertFalse(thrw.isMyDiceClear());
		assertTrue(thrw.repOk());
	}

	@Test
	public void testThrowAndGet() {
		ThrowDice thrw = new ThrowDice();
		ArrayList<DieFaces> faces = thrw.throwAndGet();

		assertNotNull(faces);
		assertTrue(thrw.repOk());

		assertEquals(3, faces.size());
		assertTrue(thrw.repOk());

	}

}
