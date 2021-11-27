package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.*;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(ReportingExtension.class)
class TriCongruenceTest {

	private static final Logger log = LoggerFactory.getLogger(TriCongruenceTest.class);

	// Line 14 CUTPNFP:
	// f = a + b + c
	// Unique true point -> near false point:
	// a: TFF -> FFF
	// b: FTF -> FFF
	// c: FFT -> FFF

	@NearFalsePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "a",
		clause = 'a',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@NearFalsePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "b",
		clause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@NearFalsePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "c",
		clause = 'c',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void line_14_fff() {
		Triangle t1 = new Triangle(1, 2, 3);
		Triangle t2 = new Triangle(1, 2, 3);

		Assertions.assertTrue(TriCongruence.areCongruent(t1, t2));
	}

	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "a",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void line_14_tff() {
		Triangle t1 = new Triangle(1, 4, 5);
		Triangle t2 = new Triangle(2, 4, 5);

		Assertions.assertFalse(TriCongruence.areCongruent(t1, t2));
	}

	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void line_14_ftf() {
		Triangle t1 = new Triangle(1, 4, 6);
		Triangle t2 = new Triangle(1, 5, 6);

		Assertions.assertFalse(TriCongruence.areCongruent(t1, t2));
	}

	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "c",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = true)
		}
	)
	@Test
	public void line_14_fft() {
		Triangle t1 = new Triangle(1, 4, 6);
		Triangle t2 = new Triangle(1, 4, 7);

		Assertions.assertFalse(TriCongruence.areCongruent(t1, t2));
	}



	// Line 15
	// CC & CACC
	@ClauseCoverage(
		predicate = "a + b",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = true)
		}
	)
	@Test
	public void line15_CC_test_tt() {
		Triangle t1 = new Triangle(-1, 2, 5);
		Triangle t2 = new Triangle(-1, 2, 5);

		Assertions.assertFalse(TriCongruence.areCongruent(t1, t2));
	}

	@ClauseCoverage(
		predicate = "a + b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false)
		}
	)
	@CACC(
		predicate = "a + b",
		majorClause = 'a',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false)
		},
		predicateValue = false
	)
	@CACC(
		predicate = "a + b",
		majorClause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false)
		},
		predicateValue = false
	)
	@Test
	public void line15_CC_test_ff() {
		Triangle t1 = new Triangle(3, 4, 5);
		Triangle t2 = new Triangle(3, 4, 5);

		Assertions.assertTrue(TriCongruence.areCongruent(t1, t2));
	}

	@CACC(
		predicate = "a + b",
		majorClause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true)
		},
		predicateValue = true
	)
	@Test
	public void line15_CC_test_ft() {
		Triangle t1 = new Triangle(1, 3, 5);
		Triangle t2 = new Triangle(1, 3, 5);

		Assertions.assertFalse(TriCongruence.areCongruent(t1, t2));
	}

	/**
	 * f  = ab + cd
	 * ~f = (~a+~b).(~c+~d)=
	 * f  UTPs = [TTFF, FFTT]
	 * ~f UTPs = [FTFF, TFFF, TTFT, TTTF]
	 * UTPC    = [TTFF, FFTT, FTFF, TFFF, TTFT, TTTF]
	 * CUTPNFP = [TTFF, FTFF, TFFF, FFTT, FFFT, FFTF]
	 *
	 * We can see CUTPNFP doesn't have TTFT and TTTF, while UTPC has them. so CUTPNFP doesn't subsume UPTC
	 *
	 */
	private static boolean questionTwo(boolean a, boolean b, boolean c, boolean d, boolean e) {
		boolean predicate = false;
		predicate = (a && b) || (c && d);
		return predicate;
	}
}
