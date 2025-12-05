class KontingentOversigt {
    private int medlemsId;
    private double kontingent;
    private double betalt;
    private BetalingsStatus betalingsStatus;

    public KontingentOversigt(int medlemsId, double kontingent, double betalt,
                              BetalingsStatus betalingsStatus) {
        this.medlemsId = medlemsId;
        this.kontingent = kontingent;
        this.betalt = betalt;
        this.betalingsStatus = betalingsStatus;
    }

    public int getMedlemsId() { return medlemsId; }
    public double getKontingent() { return kontingent; }
    public double getBetalt() { return betalt; }
    public BetalingsStatus getBetalingsStatus() { return betalingsStatus; }

    public void setBetalt(double betalt) {
        this.betalt = betalt;
        opdaterStatus();
    }

    public void setBetalingsStatus(BetalingsStatus status) {
        this.betalingsStatus = status;
    }

    private void opdaterStatus() {
        if (betalt >= kontingent) {
            betalingsStatus = BetalingsStatus.BETALT;
        } else if (betalt > 0) {
            betalingsStatus = BetalingsStatus.IKKE_BETALT;
        } else {
            betalingsStatus = BetalingsStatus.RESTANCE;
        }
    }

    @Override
    public String toString() {
        return "Medlem ID: " + medlemsId + " | Kontingent: " + kontingent + " kr" +
                " | Betalt: " + betalt + " kr | Status: " + betalingsStatus;
    }
}
