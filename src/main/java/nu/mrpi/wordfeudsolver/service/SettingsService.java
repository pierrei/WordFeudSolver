package nu.mrpi.wordfeudsolver.service;

import java.util.LinkedList;
import java.util.List;

import nu.mrpi.wordfeudsolver.domain.WFAccount;
import nu.mrpi.wordfeudsolver.solver.calculator.WFCalculator;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 *
 */
public class SettingsService {
    public static final String CONFIGURATION_FILE = "solver.xml";

    private final XMLConfiguration config;

    public SettingsService() {
        try {
            config = new XMLConfiguration(CONFIGURATION_FILE);
        } catch (ConfigurationException e) {
            throw new RuntimeException("Could not load configuration from " + CONFIGURATION_FILE);
        }
    }

    public List<WFAccount> getWordFeudAccounts() {
        List<WFAccount> accounts = new LinkedList<>();

        int accountNr = 0;
        String accountEmail;
        while ((accountEmail = getAccountProperty(accountNr, "email")) != null) {
            String accountPassword = getAccountProperty(accountNr, "password");

            accounts.add(new WFAccount(accountEmail, accountPassword));

            accountNr++;
        }

        return accounts;
    }

    private String getAccountProperty(int accountNr, String accountProperty) {
        return config.getString("accounts.account(" + accountNr + ")." + accountProperty);
    }

    public int getSleepTime() {
        return config.getInt("waitTimeInSeconds") * 1000;
    }

    public String getWFCalculatorClassName() {
        return config.getString("calculator");
    }

    public Class getWFCalculatorClass() {
        String className = getWFCalculatorClassName();
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not find calculator class: " + className);
        }
    }

    public boolean shouldSendBragMessages() {
        return config.getBoolean("sendBragMessages");
    }

    public String getRedisHost() {
        return config.getString("redis.host");
    }

    public int getRedisPort() {
        return config.getInt("redis.port");
    }
}
