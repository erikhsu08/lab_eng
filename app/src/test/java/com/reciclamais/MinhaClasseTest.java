package com.reciclamais;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.reciclamais.activity.MainActivity;

import org.junit.jupiter.api.Test;

public class MinhaClasseTest {

    @Test
    public void testRetornaUm() {
        MainActivity mainActivity = new MainActivity();
        int resultado = mainActivity.retornaUm();
        assertEquals(1, resultado, "O método retornaUm deve retornar 1");
    }
}