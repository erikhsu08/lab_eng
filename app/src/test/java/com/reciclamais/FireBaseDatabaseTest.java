package com.reciclamais;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reciclamais.model.Produto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class FireBaseDatabaseTest {

    private DatabaseReference databaseReference;

    @Before
    public void setUp() {
        // Inicializa o Firebase no contexto de teste
        FirebaseApp.initializeApp(InstrumentationRegistry.getInstrumentation().getTargetContext());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("produtos");
    }

    @Test
    public void adicionarProdutoAoFirebase() {
        Produto produto = new Produto("Foguete", "Facil", R.drawable.foguete);
        databaseReference.push().setValue(produto).addOnCompleteListener(task -> {
            assertTrue("Falha ao adicionar o produto ao Firebase", task.isSuccessful());
        });
    }
}
