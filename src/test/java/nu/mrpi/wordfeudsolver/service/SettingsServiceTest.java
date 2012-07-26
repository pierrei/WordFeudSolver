package nu.mrpi.wordfeudsolver.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import nu.mrpi.wordfeudsolver.domain.WFAccount;

import org.junit.Test;

/**
 *
 */
public class SettingsServiceTest {
    @Test
    public void testGetAccounts() throws Exception {
        SettingsService settingsService = new SettingsService();

        List<WFAccount> accounts = settingsService.getWordFeudAccounts();

        assertNotNull(accounts);
        assertEquals(2, accounts.size());
        assertEquals("account1@test.com", accounts.get(0).getEmail());
        assertEquals("password", accounts.get(0).getPassword());
        assertEquals("account2@test.com", accounts.get(1).getEmail());
        assertEquals("anotherPassword", accounts.get(1).getPassword());
    }
}
