public class Planet {

    public double xxPos; // Its current x position
    public double yyPos; // Its current y position
    public double xxVel; // Its current velocity in the x direction
    public double yyVel; // Its current velocity in the y direction
    public double mass; // Its mass
    public String imgFileName; // The name of the file that corresponds to the image that depicts the planet

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    /** Takes in a Planet object and initialize an identical Planet object. */
    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    /** Takes in a single Planet and should return a double equal to the distance
     * between the supplied planet and the planet that is doing the calculation. */
    public double calcDistance(Planet p) {
        return Math.sqrt((p.xxPos - this.xxPos) * (p.xxPos - this.xxPos)
        + (p.yyPos - this.yyPos) * (p.yyPos - this.yyPos));
    }

    /** Takes in a planet, and returns a double describing the force exerted on this planet by the given planet. */
    public double calcForceExertedBy(Planet p) {
        return 6.67e-11 * p.mass * this.mass  / (this.calcDistance(p) * this.calcDistance(p));
    }

    /** Describes the force exerted in the X and Y directions, respectively.
     * Remember to check your signs! */
    public double calcForceExertedByX(Planet p) {
        return this.calcForceExertedBy(p) * (p.xxPos - this.xxPos) / this.calcDistance(p);
    }
    public double calcForceExertedByY(Planet p) {
        return this.calcForceExertedBy(p) * (p.yyPos - this.yyPos) / this.calcDistance(p);
    }

    /** Each takes in an array of Planets and calculate the net X and net Y force
     * exerted by all planets in that array upon the current Planet. */
    public double calcNetForceExertedByX(Planet[] planets) {
        double xNetForce = 0d;
        for (int i = 0; i < planets.length; i++) {
            if (this.equals(planets[i])) {
                continue;
            }
            xNetForce += this.calcForceExertedByX(planets[i]);
        }
        return xNetForce;
    }
    public double calcNetForceExertedByY(Planet[] planets) {
        double yNetForce = 0d;
        for (int i = 0; i < planets.length; i++) {
            if (this.equals(planets[i])) {
                continue;
            }
            yNetForce += this.calcForceExertedByY(planets[i]);
        }
        return yNetForce;
    }

    /** Updates the planet’s position and velocity instance variables. */
    public void update(double dt, double fX, double fY) {
        double ax = fX / mass;
        double ay = fY / mass;
        xxVel += dt * ax;
        yyVel += dt * ay;
        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }

    /** Drawing One Planet */
    public void draw(){
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }

}