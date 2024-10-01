package com.reciclamais;

import static org.junit.Assert.*;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reciclamais.R;
import com.reciclamais.model.Produto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class FireBaseDatabaseTest {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Before
    public void setUp() {
        // Inicializa o Firebase para o ambiente de teste
        FirebaseApp.initializeApp(InstrumentationRegistry.getInstrumentation().getTargetContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("produtos");
    }

    @Test
    public void testAdicionarProdutoAoFirebase() throws InterruptedException {
        // Cria um novo produto
        Produto produto = new Produto("Teste",     "Facil", R.drawable.foguete);

        // Usa CountDownLatch para aguardar a conclusão do Firebase
        CountDownLatch latch = new CountDownLatch(1);

        // Adiciona o produto ao Firebase
        databaseReference.push().setValue(produto).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("FirebaseTest", "Produto adicionado com sucesso.");
                assertTrue("Produto foi adicionado com sucesso", task.isSuccessful());
            } else {
                Log.e("FirebaseTest", "Erro ao adicionar o produto.");
                fail("Falha ao adicionar produto");
            }
            latch.countDown(); // libera o latch
        });

        // Aguarda até 5 segundos para garantir que o Firebase responda
        boolean success = latch.await(5, TimeUnit.SECONDS);
        assertTrue("Firebase não respondeu a tempo", success);
    }
}
