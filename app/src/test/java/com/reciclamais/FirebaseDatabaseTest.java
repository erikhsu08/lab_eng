package com.reciclamais;

import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.*;  // Importa todas as classes do pacote Firebase Database
import com.reciclamais.model.Produto;

import androidx.test.ext.junit.runners.AndroidJUnit4;  // Importa o runner para testes instrumentados
import org.junit.*;  // Importa todas as classes do pacote JUnit
import org.junit.runner.RunWith;

import static org.junit.Assert.*;  // Importa métodos estáticos de Assert (assertTrue, fail, etc.)

@RunWith(AndroidJUnit4.class)
public class FirebaseDatabaseTest {

    private DatabaseReference databaseReference;

    @Before
    public void setUp() {
        // Inicializa o Firebase Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("produtos_testes");
    }

    @Test
    public void testAddItemToFirebase() {
        // Cria um novo item
        Produto produto = new Produto("Foguete", "Facil", R.drawable.foguete);

        // Adiciona o item no Firebase
        databaseReference.push().setValue(produto);

        // Verifica se o item foi adicionado no banco de dados (usando um listener)
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean itemEncontrado = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Produto p = snapshot.getValue(Produto.class);
                    if (p.getNome().equals("Produto de Teste")) {
                        itemEncontrado = true;
                        break;
                    }
                }
                // Verifica se o item foi realmente encontrado
                assertTrue(itemEncontrado);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fail("Erro ao acessar o Firebase: " + databaseError.getMessage());
            }
        });
    }

    @After
    public void tearDown() {
        // Remove todos os itens do nó de teste para manter o banco limpo
        databaseReference.removeValue();
    }
}

