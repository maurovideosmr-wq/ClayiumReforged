/*
 * Decompiled with CFR 0.152.
 */
package mods.clayium.block.laser;

public class ClayLaser {
    static final int colorNum = 3;
    static final int lLifespan = 10;
    static final double[] lBases = new double[]{2.5, 1.8, 1.5};
    static final double[] lMaxEnergys = new double[]{1000.0, 300.0, 100.0};
    static final double[] lDampingRate = new double[]{0.1, 0.1, 0.1};
    public int[] numbers = new int[3];
    public int age = 0;

    public ClayLaser(int age, int ... n) {
        this.age = age;
        for (int i = 0; i < n.length && i < 3; ++i) {
            this.numbers[i] = n[i];
        }
    }

    public static double calculateEnergyPerColor(int number, double base, double maxEnergy, double dampingRate) {
        double r = dampingRate;
        if (number <= 0 || r <= 0.0 || maxEnergy < 0.0 || base < 1.0) {
            return 1.0;
        }
        double c = Math.log(maxEnergy) / (Math.log((1.0 + r) / r) * Math.log(base));
        double a = Math.pow(Math.E, (1.0 + r) / c);
        double e = Math.pow(maxEnergy, Math.log((1.0 + r) / (Math.pow(a, -number) + r)) / Math.log((1.0 + r) / r));
        double m = 1.0 / (1.0 + r * Math.pow(a, number)) + r * Math.pow(a, number) / (1.0 + r * Math.pow(a, number)) * (double)number;
        double u = Math.max(e * m, 1.0);
        return u;
    }

    public static double getEnergy(int ... n) {
        double res = 1.0;
        for (int i = 0; i < n.length && i < 3; ++i) {
            res *= ClayLaser.calculateEnergyPerColor(n[i], lBases[i], lMaxEnergys[i], lDampingRate[i]);
        }
        return res - 1.0;
    }

    public double getEnergy() {
        return ClayLaser.getEnergy(this.numbers);
    }

    public static ClayLaser mergeClayLasers(ClayLaser[] lasers) {
        int[] numbers = new int[3];
        int age = 0;
        for (ClayLaser laser : lasers) {
            age = Math.max(age, laser.age);
        }
        if (age >= 10) {
            for (ClayLaser laser : lasers) {
                for (int i = 0; i < 3; ++i) {
                    numbers[i] = Math.max(numbers[i], laser.numbers[i]);
                }
            }
        } else {
            for (ClayLaser laser : lasers) {
                for (int i = 0; i < 3; ++i) {
                    int n = i;
                    numbers[n] = numbers[n] + laser.numbers[i];
                }
            }
        }
        return new ClayLaser(age, numbers);
    }
}

