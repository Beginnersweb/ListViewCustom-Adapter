package com.example.listview_custom_adapter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ContactAdapter
    private lateinit var contactList: ArrayList<ContactModel>
    private lateinit var originalContactList: ArrayList<ContactModel> // Nueva lista para mantener la original

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listView)
        val searchInput = findViewById<EditText>(R.id.searchInput)

        contactList = getContacts()
        originalContactList = ArrayList(contactList) // Guardamos la lista original
        adapter = ContactAdapter(this, contactList)

        listView.adapter = adapter


        // Implementar búsqueda
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterContacts(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterContacts(query: String) {
        val filteredList = if (query.isEmpty()) {
            ArrayList(originalContactList) // Si está vacío, mostramos la lista original
        } else {
            contactList.filter {
                it.nombre.contains(query, ignoreCase = true) || it.telefono.contains(query)
            } as ArrayList<ContactModel>
        }
        adapter.updateContacts(filteredList)
    }

    private fun getContacts(): ArrayList<ContactModel> {
        return arrayListOf(
            ContactModel("Juan Pérez", "55-5123-4543", "juan.perez@example.com", R.drawable.juan_perez),
            ContactModel("María López", "55-5567-8324", "maria.lopez@example.com", R.drawable.maria_lopez),
            ContactModel("Carlos Gómez", "55-5876-5764", "carlos.gomez@example.com", R.drawable.carlos_gomez),
            ContactModel("Laura Martínez", "55-5432-1236", "laura.martinez@example.com", R.drawable.laura_martinez),
            ContactModel("Pedro Sánchez", "55-5345-6967", "pedro.sanchez@example.com", R.drawable.pedro_sanchez),
            ContactModel("Ana Torres", "55-5123-4543", "ana.torres@example.com", R.drawable.ana_torres)
        )
    }
}
