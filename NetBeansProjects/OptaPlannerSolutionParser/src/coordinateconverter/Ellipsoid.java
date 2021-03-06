
package coordinateconverter;

public enum Ellipsoid {
    AIRY ("Airy", 6377563.396, 6356256.91), 
    MODIFIED_AIRY ("Modified Airy", 6377340.189, 6356034.45),
    AUSTRAILIAN_NATIONAL ("Australian National", 6378160, 6356774.72),
    BESSEL_1841 ("Bessel 1841", 6377397.16, 6356078.96),
    BESSEL_1841_NAMIBIA("Bessel 1841 (Namibia)", 6377483.87, 6356165.38),
    CLARKE_1866 ("Clarke 1866", 6378206.4, 6356583.80),
    CLARKE_1880 ("Clarke 1880", 6378249.15, 6356514.87),
    EVEREST_1830 ("Everest 1830", 6377276.35, 6356075.41),
    EVEREST_1948 ("Everest 1948", 6377304.06, 6356103.04),
    EVEREST_1956 ("Everest 1956", 6377301.24, 6356100.23),
    EVEREST_1969 ("Everest 1969", 6377295.66, 6356094.67),
    EVEREST_SABAH_SARAWAK ("Everest (Sabah & Sarawak)", 6377298.56, 6356097.55),
    FISCHER_1960 ("Fischer 1960", 6378166, 635678428),
    MODIFIED_FISHER_1960 ("Modified Fischer 1960", 6378155, 6356773.32),
    FISCHER_1968 ("Fischer 1968", 6378150, 6356768.34),
    GRS_1980 ("GRS 1980", 6378137, 6356752.314),
    HELMERT_1906 ("Helmert 1906", 6378200, 6356818.17),
    HEYFORD ("Heyford", 6378388, 6356911.95),
    HOUGH ("Hough", 6378270, 6356794.34),
    INTERNATIONAL_1924 ("International 1924", 6378388, 6356911.9462),
    KRASSOVSKY ("Krassovsky", 6378245, 6356863.02),
    SGS_85 ("SGS 85", 6378136, 6356751.30),
    SOUTH_AMERICA_1969 ("South America 1969", 6378160, 6356774.719),
    WGS_60 ("WGS 60", 6378165, 6356783.29),
    WGS_66 ("WGS 66", 6378145, 6356759.77),
    WGS_72 ("WGS 72", 6378135, 6356750.52),
    WGS_84 ("WGS 84", 6378137, 6356752.314);
    
    private final String name;
    private final double a;
    private final double b;
    private final double flat;
    private final double inverseFlat;
    private final double firstEccent;
    private final double firstEccentSqr;
    private final double secondEccent;
    private final double secondEccentSqr;
    
    Ellipsoid(String name, double major, double minor) {
        this.name = name;
        this.a = major;
        this.b = minor;
        this.flat = (a - b) / a;
        this.inverseFlat = 1 / this.flat;
        this.firstEccent = Math.sqrt(1 - (b / a) * (b / a)); //Math.sqrt(1 - (a / b) * (a / b));
        this.firstEccentSqr = 1 - (b / a) * (b / a);
        this.secondEccent = Math.sqrt((a * a - b * b) / (b * b));
        this.secondEccentSqr = (a * a - b * b) / (b * b);
    }
    
    public String ellipsoidName() {
        return name;
    }
    
    public double majorAxis() {
        return a;
    }
    
    public double minorAxis() {
        return b;
    }
    
    public double flattening() {
        return flat;
    }
    
    public double inverseFlattening() {
        return inverseFlat;
    }
    
    public double firstEccentricity() {
        return firstEccent;
    }
    
    public double firstEccentricitySquared() {
        return firstEccentSqr;
    }
    
    public double secondEccentricity() {
        return secondEccent;
    }
    
    public double secondEccentricitySquared() {
        return secondEccentSqr;
    }
}