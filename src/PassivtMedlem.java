class PassivtMedlem extends Medlem {
    public PassivtMedlem(String navn, String email, int alder) {
        super(navn, email, alder);
        setMedlemstype(Medlemstype.PASSIV);
    }

    @Override
    public String toString() {
        return super.toString() + " | Type: PASSIV";
    }
}