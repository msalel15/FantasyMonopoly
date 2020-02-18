package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import domain.MultiPolyController;

class MultiPolyControllerTest {

	@Test
	void testUseCard() {
		MultiPolyController mc = MultiPolyController.getInstance();

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			mc.useCard(13);
		});
		assertTrue(mc.repOk());

		mc.useCard(11);
		assertEquals(1, mc.getGameFlow().size());
		assertTrue(mc.repOk());
	}

}
