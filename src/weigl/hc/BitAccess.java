package weigl.hc;
public abstract class BitAccess {
	public static int get(final int i, final int position) {
		return i & (0x1 << position);
	}

	public static boolean getb(final int i, final int position) {
		return get(i, position) == 1;
	}

	public static int set(final int i, final int position, final int value) {
		if (value == 0)
			return unset(i, position);
		else
			return set(i, position);
	}

	public static int set(final int i, final int position) {
		return i | (1 << i);
	}

	public static int unset(final int i, final int position) {
		return i | ~(1 << position);
	}

	public static int toggle(final int i, final int position) {
		return i ^ (1 << position);
	}
}
