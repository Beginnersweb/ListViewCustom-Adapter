package com.example.listview_custom_adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.provider.ContactsContract.Contacts
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ContactAdapter(private val context: Context,
                     private val datos: ArrayList<ContactModel>
): BaseAdapter() {

    private  val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private fun getDuplicatePhones(datos: ArrayList<ContactModel>): Set<String> {
        return datos.groupingBy { it.telefono }.eachCount().filter { it.value > 1 }.keys
    }

    // En el constructor o init del adaptador
    private val duplicatePhones = getDuplicatePhones(datos)

    //Infla una vista para un Item de la lista, aquí se describe cómo se va a desplegar la info
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val rowView = inflater.inflate(R.layout.list_item_contact, parent, false)

        //obtenemos las referencias de cada Veiw de nuestro  layout de item_game
        val tvNombre = rowView.findViewById<View>(R.id.tvNombre) as TextView
        val tvTelefono = rowView.findViewById<View>(R.id.tvTelefono) as TextView
        val tvEmail = rowView.findViewById<View>(R.id.tvEmail) as TextView
        val imgPerfil = rowView.findViewById<View>(R.id.imgPerfil) as ImageView

        //Obtenemos la infromación del item por medio de getItem()
        val contact = getItem(position) as ContactModel

        val btnEnviarCorreo = rowView.findViewById<Button>(R.id.btnEnviarCorreo)

        btnEnviarCorreo.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${contact.email}")
                putExtra(Intent.EXTRA_SUBJECT, "Asunto del correo")
                putExtra(Intent.EXTRA_TEXT, "Cuerpo del correo")
            }
            context.startActivity(Intent.createChooser(emailIntent, "Enviar correo a ${contact.email}"))
        }

        //seteamos todos los valores a nuestras vistas para desplegar la información
        tvNombre.text = contact.nombre
        tvTelefono.text = contact.telefono
        tvEmail.text = contact.email
        imgPerfil.setImageResource(contact.idImagenld)

        // Cambiar el color de fondo si hay duplicados
        if (duplicatePhones.contains(contact.telefono)) {
            rowView.setBackgroundColor(Color.YELLOW) // Cambia el color según desees
        } else {
            rowView.setBackgroundColor(Color.WHITE) // Color normal
        }

        // Configurar el botón para enviar correo
        btnEnviarCorreo.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${contact.email}")
                putExtra(Intent.EXTRA_SUBJECT, "Asunto del correo")
                putExtra(Intent.EXTRA_TEXT, "Cuerpo del correo")
            }
            context.startActivity(Intent.createChooser(emailIntent, "Enviar correo a ${contact.email}"))
        }


        return  rowView
    }

    //regresa un item para ser colocado en la posición position del ListView
    override fun getItem(position: Int ): Any {
        return  datos[position]
    }

    //Este método define un id para cada item, decidimos usar el index en el array data
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //Este método le dice al ListView cuantos items mostrar
    override fun getCount(): Int {
        return datos.size
    }

    fun updateContacts(newContacts: List<ContactModel>) {
        datos.clear()
        datos.addAll(newContacts)
        notifyDataSetChanged()
    }

}