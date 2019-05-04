package stuff_on_earth;

public enum Elements {
	H(1),
	C(12),
	O(16);
	final int mass, number, potential;
	Elements(int number) {
		this.number = number;
		mass= number*2;
		potential= number>2 ? number%8 :number;
	}
}
