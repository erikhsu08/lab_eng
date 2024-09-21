import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinhaClasseTest {

    @Test
    public void testRetornaUm() {
        MinhaClasse MainActivity = new MinhaClasse();
        int resultado = MainActivity.retornaUm();
        assertEquals(1, resultado, "O método retornaUm deve retornar 1");
    }
}