package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti != null);
    }

    @Test
    public void saldoAlussaOikein() {
        assertEquals(10, kortti.saldo());
    }

    @Test
    public void lataaminenToimii() {
        kortti.lataaRahaa(155);
        assertEquals(165, kortti.saldo());
    }

    @Test
    public void rahaVaheneeKunTarpeeksi() {
        boolean otettiinko = kortti.otaRahaa(5);
        assertTrue(otettiinko);
        assertEquals(5, kortti.saldo());
    }

    @Test
    public void rahaEiVaheneKunEiTarpeeksi() {
        boolean otettiinko = kortti.otaRahaa(15);
        assertTrue(!otettiinko);
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void toStringToimii() {
        kortti.lataaRahaa(155);
        assertEquals("saldo: 1.65", kortti.toString());
    }
}
