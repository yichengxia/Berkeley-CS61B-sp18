public class NBody {

    public static double readRadius(String planetsTxtPath) {
        In in = new In(planetsTxtPath);
        in.readInt();
		double R = in.readDouble();
        return R;
    }

    public static Planet[] readPlanets(String planetsTxtPath) {
        In in = new In(planetsTxtPath);
        int N = in.readInt();
        in.readDouble();
        Planet[] p = new Planet[N];
        for (int i = 0; i < N; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            p[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
        }
        return p;
    }

    public static void main(String[] args) {

        /** Collecting All Needed Input */
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] planets = readPlanets(filename);
        int N = new In(filename).readInt();
        double radius = readRadius(filename);
        
        /** Drawing the Background */
        String background = "images/starfield.jpg";
		StdDraw.setScale(-radius, radius);
		StdDraw.clear();
		StdDraw.picture(0, 0, background);

        /** Drawing All of the Planets */
        for (Planet p : planets) {
            p.draw();
        }

        /** Enable double buffering to prevent flickering in the animation. */
        StdDraw.enableDoubleBuffering();

        /** Creating an Animation */
        for (double time = 0; time < T; time += dt) {
            double[] xForces = new double[N];
            double[] yForces = new double[N];
            for (int i = 0; i < N; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < N; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            /** Draw the background image. */
            StdDraw.clear();
            StdDraw.picture(0, 0, background);
            /** Draw all of the planets. */
            for (Planet p : planets) {
                p.draw();
            }
            /** Show the offscreen buffer. */
            StdDraw.show();
            /** Pause the animation for 10 milliseconds. */
		    StdDraw.pause(10);
        }

        /** Printing the Universe */
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
            planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }

    }

}