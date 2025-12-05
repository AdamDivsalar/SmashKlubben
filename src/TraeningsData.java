class TraeningsData {
    private Disciplin disciplin;
    private double bedsteResultat;
    private java.util.Date dato;

    public TraeningsData(Disciplin disciplin, double bedsteResultat, java.util.Date dato) {
        this.disciplin = disciplin;
        this.bedsteResultat = bedsteResultat;
        this.dato = dato;
    }

    public Disciplin getDisciplin() { return disciplin; }
    public double getBedsteResultat() { return bedsteResultat; }
    public java.util.Date getDato() { return dato; }

    public void opdaterResultat(double nytResultat) {
        if (nytResultat > this.bedsteResultat) {
            this.bedsteResultat = nytResultat;
            this.dato = new java.util.Date();
        }
    }

    @Override
    public String toString() {
        return "Disciplin: " + disciplin + " | Bedste: " + bedsteResultat + " | Dato: " + dato;
    }
}