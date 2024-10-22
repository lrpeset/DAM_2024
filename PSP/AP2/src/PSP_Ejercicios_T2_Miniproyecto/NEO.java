package PSP_Ejercicios_T2_Miniproyecto;

public class NEO {
    private String name;
    private double position;
    private double speed;

    public NEO(String name, double position, double velocity) {
        this.name = name;
        this.position = position;
        this.speed = velocity;
    }

    public String getName() {
        return name;
    }

    public double getPosition() {
        return position;
    }

    public double getSpeed() {
        return speed;
    }

    public double calculateCollisionProbability(double earthPosition, double earthVelocity) {
        double positionNEO = this.position;
        double speedNEO = this.speed;

        for (int i = 0; i < (50 * 365 * 24 * 60 * 60); i++) {
            positionNEO += speedNEO * i;
            earthPosition += earthVelocity * i;
        }

        return 100 * Math.random() * Math.pow(((positionNEO - earthPosition) / (positionNEO + earthPosition)), 2);
    }
}
