
import com.mycompany.unicafe.Kassapaate;
import com.mycompany.unicafe.Maksukortti;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {

    Kassapaate kassa;

    @Before
    public void setUp() {
        kassa = new Kassapaate();
    }

    @Test
    public void tilaAlussaOikein() {
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void rahamaaraKasvaaKunMyydaanEdullinenKateinen() {
        kassa.syoEdullisesti(240);
        assertEquals(kassa.kassassaRahaa(), 100240);
    }

    @Test
    public void vaihtorahaPalautetaanKunMyydaanEdullinen() {
        int vaihtoraha = kassa.syoEdullisesti(255);
        assertEquals(15, vaihtoraha);
    }

    @Test
    public void myytyjenLounaidenMaaraKasvaaEdullinenKateinen() {
        kassa.syoEdullisesti(240);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void kaikkiRahaPalautetaanKunEiTarpeeksiEdulliseen() {
        int vaihtoraha = kassa.syoEdullisesti(239);
        assertEquals(239, vaihtoraha);
    }

    @Test
    public void rahamaaraEiMuutuKunEiTarpeeksiEdulliseenKateinen() {
        kassa.syoEdullisesti(239);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void myytyjenEdullistenMaaraEiKasvaKunEiTarpeeksiKateista() {
        kassa.syoEdullisesti(239);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void rahamaaraKasvaaKunMyydaanMaukasKateinen() {
        kassa.syoMaukkaasti(400);
        assertEquals(100400, kassa.kassassaRahaa());
    }

    @Test
    public void vaihtorahaPalautetaanKunMyydaanMaukas() {
        int vaihtoraha = kassa.syoMaukkaasti(425);
        assertEquals(25, vaihtoraha);
    }

    @Test
    public void myytyjenLounaidenMaaraKasvaaMaukasKateinen() {
        kassa.syoMaukkaasti(400);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kaikkiRahaPalautetaanKunEiTarpeeksiMaukkaaseen() {
        int vaihtoraha = kassa.syoMaukkaasti(399);
        assertEquals(399, vaihtoraha);
    }

    @Test
    public void rahamaaraEiMuutuKunEiTarpeeksiMaukkaaseenKateinen() {
        kassa.syoMaukkaasti(399);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void myytyjenMaukkaidenMaaraEiKasvaKunEiTarpeeksiKateista() {
        kassa.syoMaukkaasti(399);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void josKortillaTarpeeksiRahaaEdulliseenVeloitetaanSumma() {
        Maksukortti kortti = new Maksukortti(250);
        boolean ostettu = kassa.syoEdullisesti(kortti);
        assertTrue(ostettu);
        assertEquals(10, kortti.saldo());
    }

    @Test
    public void myytyjenLounaidenMaaraKasvaaEdullinenKortti() {
        Maksukortti kortti = new Maksukortti(250);
        kassa.syoEdullisesti(kortti);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void kassanRahamaaraEiMuutuKunOstetaanEdullinenKortilla() {
        Maksukortti kortti = new Maksukortti(250);
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void rahaaEiOtetaJosEiTarpeeksiEdulliseen() {
        Maksukortti kortti = new Maksukortti(239);
        boolean ostettu = kassa.syoEdullisesti(kortti);
        assertTrue(!ostettu);
        assertEquals(239, kortti.saldo());
    }

    @Test
    public void myytyjenEdullistenMaaraEiKasvaKunEiTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(239);
        kassa.syoEdullisesti(kortti);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void josKortillaTarpeeksiRahaaMaukkaaseenVeloitetaanSumma() {
        Maksukortti kortti = new Maksukortti(410);
        boolean ostettu = kassa.syoMaukkaasti(kortti);
        assertTrue(ostettu);
        assertEquals(10, kortti.saldo());
    }

    @Test
    public void myytyjenLounaidenMaaraKasvaaMaukasKortti() {
        Maksukortti kortti = new Maksukortti(410);
        kassa.syoMaukkaasti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kassanRahamaaraEiMuutuKunOstetaanMaukasKortilla() {
        Maksukortti kortti = new Maksukortti(410);
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void rahaaEiOtetaJosEiTarpeeksiMaukkaaseen() {
        Maksukortti kortti = new Maksukortti(399);
        boolean ostettu = kassa.syoMaukkaasti(kortti);
        assertTrue(!ostettu);
        assertEquals(399, kortti.saldo());
    }

    @Test
    public void myytyjenMaukkaidenMaaraEiKasvaKunEiTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(399);
        kassa.syoMaukkaasti(kortti);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kortinSaldoKasvaaKunLadataanRahaa() {
        Maksukortti kortti = new Maksukortti(100);
        kassa.lataaRahaaKortille(kortti, 150);
        assertEquals(250, kortti.saldo());
    }

    @Test
    public void kassanSaldoKasvaaKunLadataanRahaa() {
        Maksukortti kortti = new Maksukortti(100);
        kassa.lataaRahaaKortille(kortti, 150);
        assertEquals(100150, kassa.kassassaRahaa());
    }

    @Test
    public void negatiivinenLatausEiMuutaKortinSaldoa() {
        Maksukortti kortti = new Maksukortti(100);
        kassa.lataaRahaaKortille(kortti, -200);
        assertEquals(100, kortti.saldo());
    }

    @Test
    public void negatiivinenLatausEiMuutaKassanSaldoa() {
        Maksukortti kortti = new Maksukortti(100);
        kassa.lataaRahaaKortille(kortti, -200);
        assertEquals(100000, kassa.kassassaRahaa());
    }
}
