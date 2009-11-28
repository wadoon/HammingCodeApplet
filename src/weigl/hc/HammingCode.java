package weigl.hc;

import java.util.Arrays;

public class HammingCode {

	private int parities;
	private int databits;

	private boolean[][] parityTable;
	private boolean[] parityPosition;

	public HammingCode(int databits) {
		this.databits = databits;
		this.parities = getParityBits(databits);
		fillTable();
	}

	public int getParities() {
		return parities;
	}

	public void setParities(int parities) {
		this.parities = parities;
	}

	public int getDatabits() {
		return databits;
	}

	public void setDatabits(int databits) {
		this.databits = databits;
	}

	public boolean[][] getParityTable() {
		return parityTable;
	}

	public void setParityTable(boolean[][] parityTable) {
		this.parityTable = parityTable;
	}

	public boolean[] getParityPosition() {
		return parityPosition;
	}

	public void setParityPosition(boolean[] parityPosition) {
		this.parityPosition = parityPosition;
	}

	private void fillTable() {
		parityTable = new boolean[parities][parities + databits];
		parityPosition = new boolean[parities + databits];

		final int leftPos = parityPosition.length - 1;

		for (int i = leftPos; i >= 0; i--) {
			int bp1 = leftPos - i + 1; // bit position
			if (natural(log2(bp1))) // parity bit
			{
				parityPosition[i] = true;
			} else {
				for (int j = 0; j < parities; ++j) {
					// int bp2 = (int) Math.pow(2, j);
					// if (checkedBy(bp1, bp2)) // is parity
					if ((bp1 & (0x1 << j)) > 0) {
						parityTable[j][i] = true;
						System.out.println(j);
					}
				}
			}
		}

		System.out.println(Arrays.toString(parityPosition));
		System.out.println();
		for (int i = 0; i < parityTable.length; i++)
			System.out.println(Arrays.toString(parityTable[i]));
	}
	
	public int toHammingCode(int codeWord) {
		if (log2(codeWord) > databits)
			throw new IllegalArgumentException("to much databits");
		System.out.println(Integer.toBinaryString(codeWord));
		boolean b[] = new boolean[parityPosition.length];

		for (int i = parityPosition.length - 1; i >= 0; i--) {
			if (parityPosition[i])
				continue;
			b[i] = BitAccess.getb(codeWord, 0);
			codeWord >>= 1;
		}

		calculateParities(b);
		System.out.println(Arrays.toString(b));

		int i = boolArray2int(b);
		System.out.println(Integer.toBinaryString(i));
		return i;
	}

	private void calculateParities(boolean[] b) {
		for (int i = 0, j = parities - 1; i < parityPosition.length; i++) {
			if (!parityPosition[i])
				continue;

			boolean parity = false;
			for (int k = 0; k < parityTable[j].length; k++) {
				if (parityTable[j][k])
					parity = parity ^ b[k];
			}
			b[i] = parity;
			j--;
		}
	}

	public int fromHammingCode(int hammingCode)
			throws HammingCorrectionException {
		boolean b[] = new boolean[parityPosition.length];
		for (int i = b.length - 1; i >= 0; i--) {
			b[i] = BitAccess.getb(hammingCode, 0);
			hammingCode >>= 1;
		}

		boolean[] copy = Arrays.copyOf(b, b.length);
		calculateParities(copy);

		// error position
		int e = 0, d = 0;
		for (int j = 0; j < copy.length; j++) {
			if (parityPosition[j]) {
				e |= b[j] ^ copy[j] ? 1 : 0;
				e <<= 1;
			} else {
				d <<= 1;
				d |= b[j] ? 0x1 : 0x0;
			}
		}
		if (e > 0)
			throw new HammingCorrectionException(e, hammingCode);

		return d;
	}

	public String format(int hammingCode) {
		final String format = "%" + (getDatabits() + getParities()) + "s";
		return String.format(format, Integer.toBinaryString(hammingCode)).replace(' ', '0');
	}

	private int boolArray2int(boolean[] b) {
		int i = 0;
		for (boolean c : b) {
			i <<= 1;
			if (c)
				i |= 1;
		}
		return i;
	}

	public static boolean natural(double n) {
		return (n - (int) n) == 0;
	}

	public static double log2(int n) {
		return Math.log(n) / Math.log(2);
	}

	public static int getParityBits(int dataBits) {
		for (int i = 0; true; i++) {
			int j = 1 + dataBits + i;
			if (j <= Math.pow(2, i))
				return i;
		}
	}

	public class HammingCorrectionException extends Throwable {
		private static final long serialVersionUID = 7464632786524388524L;
		private int hammingWord;
		private int position;

		public HammingCorrectionException(int position, int hammingword) {
			this.position = position;
			this.hammingWord = hammingword;
		}

		public int getCorrectedWord() {
			return BitAccess.toggle(hammingWord, position);
		}

		public int getCorrectedValue() {
			return toHammingCode(getCorrectedWord());
		}

		public int getHammingWord() {
			return hammingWord;
		}

		public void setHammingWord(int hammingWord) {
			this.hammingWord = hammingWord;
		}

		public int getPosition() {
			return position;
		}

		public void setPosition(int position) {
			this.position = position;
		}

		@Override
		public String toString() {
			return getClass().getSimpleName()
					+ " found parity error int word: "
					+ Integer.toBinaryString(hammingWord) + " at " + position
					+ ", fix to:" + Integer.toBinaryString(getCorrectedWord());
		}
	}
}
