package weigl.hc;

import weigl.hc.HammingCode.HammingCorrectionException;

public class Test {

	public static void main(String[] args) throws HammingCorrectionException {
		HammingCode hc = new HammingCode(2);
		int c = hc.toHammingCode(2);
		System.out.println(c);
		System.out.println(hc.fromHammingCode(c));
		System.out.println(hc.fromHammingCode(c & (~1)));
	}

}
