package it.unibo.mvc;

import java.util.Random;

/**
 *
 */
public final class DrawNumberImpl implements DrawNumber {

    private int choice;
    //private final int min;
    //private final int max;
    //private final int attempts;
    private int remainingAttempts;
    private final Random random = new Random();

    private final Configuration configuration;

    /**
     * @throws IllegalStateException if the configuration is not consistent
     */
    public DrawNumberImpl(final int min, final int max, final int attempts) {
        var configurationBuilder = new Configuration.Builder();
        configurationBuilder.setMin(min);
        configurationBuilder.setMax(max);
        configurationBuilder.setAttempts(attempts);
        configuration = configurationBuilder.build();
        this.reset();
    }

    public DrawNumberImpl(String configurationFile) {
        var configurationBuilder = new Configuration.Builder();
        configuration = configurationBuilder.buildFromFile(configurationFile);
        this.reset();
    }


    @Override
    public void reset() {
        this.remainingAttempts = this.configuration.getAttempts();
        this.choice = this.configuration.getMin() + random.nextInt(this.configuration.getMax() - this.configuration.getMin() + 1);
    }

    @Override
    public DrawResult attempt(final int n) {
        if (this.remainingAttempts <= 0) {
            return DrawResult.YOU_LOST;
        }
        if (n < this.configuration.getMin() || n > this.configuration.getMax()) {
            throw new IllegalArgumentException("The number is outside boundaries");
        }
        remainingAttempts--;
        if (n > this.choice) {
            return DrawResult.YOURS_HIGH;
        }
        if (n < this.choice) {
            return DrawResult.YOURS_LOW;
        }
        return DrawResult.YOU_WON;
    }

}
